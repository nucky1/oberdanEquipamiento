/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Pedido;
import Models.Producto;
import Models.facturaProveedor;
import Models.renglonFactura;
import Models.renglonPedido;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author demig
 */
public class PedidoDAO {
    private static PedidoDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();

    protected PedidoDAO() {
       
    }
    
    public static PedidoDAO getInstance(){
        if (controller == null) {
            controller = new PedidoDAO();
        }
        return controller;
    }
    
    public ArrayList<Pedido> getPedidos(int provId) {
        HashMap<String,Pedido> pedidos = new HashMap();
        String SQL = "SELECT * FROM `nota_pedido` "
                + "INNER JOIN (SELECT renglon_nota.id as renglon_nota_id,renglon_nota.sub_total,renglon_nota.nota_pedido_id,renglon_nota.costo_prod,renglon_nota.cantidad, renglon_nota.cant_faltante,articulos.* "
                + "             FROM renglon_nota "
                + "             INNER JOIN articulos "
                + "             ON articulos.id = renglon_nota.producto_id "
                + "             ORDER BY renglon_nota.nota_pedido_id) "
                + "AS renglon "
                + "ON nota_pedido.nro_pedido = renglon.nota_pedido_id "
                + "WHERE nota_pedido.proveedor_id = "+provId
                + " ORDER BY nota_pedido.nro_pedido";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while (rs.next()) {
                Pedido p = new Pedido();
                renglonPedido rp = new renglonPedido();
                Producto prod = new Producto();
                if(!pedidos.containsKey(rs.getString("nro_pedido"))){
                    p = new Pedido();
                    p.setEstado(rs.getString("estado"));
                    p.setNumPedido(rs.getString("nro_pedido"));
                    p.setTotal(rs.getFloat("total"));
                    p.setIdProv(rs.getInt("proveedor_id"));
                    p.setFecha(rs.getDate("nota_pedido.created_at"));
                    pedidos.put(p.getNumPedido(), p);
                }
                rp = new renglonPedido();
                rp.setCantidad(rs.getInt("cantidad"));
                rp.setId(rs.getInt("renglon_nota_id"));
                rp.setSubTotal(rs.getFloat("sub_total"));
                rp.setCosto(rs.getFloat("costo_prod"));
                rp.setCantFaltante(rs.getInt("cant_faltante"));
                //prod
                prod.setId(rs.getInt("id"));
                prod.setCod(rs.getInt("cod"));
                prod.setNombre(rs.getString("nombre"));
                rp.setP(prod);
                pedidos.get(p.getNumPedido()).addRenglon(rp); 
            }
        }catch(Exception e){
            new Statics.ExceptionManager().saveDump(e, "", Main.isProduccion);
        }
        System.out.println(pedidos.values());
        return new ArrayList<Pedido>(pedidos.values());
    }

    public Pedido insertPedido(int idProv){
        Pedido p = null;
        try {
            String SQL = "INSERT INTO nota_pedido(proveedor_id,estado) VALUES ("+idProv+",'PENDIENTE')";
            conexion.EjecutarOperacion(SQL);
            SQL = "SELECT * FROM nota_pedido order by nro_pedido desc limit 1";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if(rs.next()){
                p = new Pedido();
                p.setEstado(rs.getString("estado"));
                p.setNumPedido(rs.getString("nro_pedido"));
                p.setTotal(rs.getFloat("total"));
                p.setIdProv(rs.getInt("proveedor_id"));
                p.setFecha(rs.getDate("nota_pedido.updated_at"));
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return p;
    }
    public void eliminarPedido(Pedido p){
        String SQL = "DELETE FROM nota_pedido WHERE id="+p.getId();
        conexion.EjecutarOperacion(SQL);
    }
    public void insertRP(Pedido pedidoNuevo) {
        String SQL = "INSERT INTO renglon_nota(nota_pedido_id,producto_id,costo_prod,cantidad,cant_faltante,sub_total) VALUES ";
        for(int i = pedidoNuevo.getRenglones().size()-1 ; i > 0 ;i--){
            SQL += "("+pedidoNuevo.getNumPedido()+","+pedidoNuevo.getRenglones().get(i).getP().getId()+","+pedidoNuevo.getRenglones().get(i).getCosto()+","+pedidoNuevo.getRenglones().get(i).getCantidad()+","+pedidoNuevo.getRenglones().get(i).getCantidad()+","+pedidoNuevo.getRenglones().get(i).getSubTotal()+"),";
            insertarStockPedido(pedidoNuevo.getRenglones().get(i));
        }
        SQL += "("+pedidoNuevo.getNumPedido()+","+pedidoNuevo.getRenglones().get(0).getP().getId()+","+pedidoNuevo.getRenglones().get(0).getCosto()+","+pedidoNuevo.getRenglones().get(0).getCantidad()+","+pedidoNuevo.getRenglones().get(0).getCantidad()+","+pedidoNuevo.getRenglones().get(0).getSubTotal()+")";
        insertarStockPedido(pedidoNuevo.getRenglones().get(0));
        conexion.EjecutarOperacion(SQL);
        SQL = "UPDATE nota_pedido SET total ="+pedidoNuevo.getTotal()+" WHERE nro_pedido = "+pedidoNuevo.getNumPedido();
        conexion.EjecutarOperacion(SQL);
    }
    /**
     * Este metodo se usa para insertar el stock pedido con el
     * precio de compra correspondiente o crear uno nuevo con este precio
     * servira luego para modulo de balance contable.
     * @param rp 
     */
    public void insertarStockPedido(renglonPedido rp){
        String SQLstock = "UPDATE `art_stock` SET stock_pedido = "+rp.getCantFaltante()+" WHERE producto_id = "+rp.getP().getId()+" AND precio_compra ="+rp.getCosto();
        int filasAfectadas = conexion.EjecutarOperacion(SQLstock);
        if(filasAfectadas == 0){
            SQLstock = "INSERT INTO `art_stock`(`producto_id`,`stock_pedido`,`precio_compra`) "
                    + "VALUES ("+rp.getP().getId()+","+rp.getCantFaltante()+","+rp.getCosto()+")";
        }
    }
    /**
     * Este metodo se usa para insertar el stock que llego
     * precio de compra correspondiente o crear uno nuevo con este precio
     * servira luego para modulo de balance contable.
     * @param rf 
     */
    public void insertarStockFactura (renglonFactura rf){
        String SQLstock = "UPDATE `art_stock` SET stock_pedido = IF(stock_pedido-"+rf.getCantidad()+" < 0, 0, stock_pedido-"+rf.getCantidad()+"),"
                + "stock_actual = stock_actual+"+rf.getCantidad()
                + "WHERE producto_id = "+rf.getP().getId()+" AND precio_compra ="+rf.getCosto();
        int filasAfectadas = conexion.EjecutarOperacion(SQLstock);
        if(filasAfectadas == 0){
            SQLstock = "INSERT INTO `art_stock`(`producto_id`,`stock_actual`,`precio_compra`) "
                    + "VALUES ("+rf.getP().getId()+","+rf.getCantidad()+","+rf.getCosto()+")";
        }
    }
    public void insertarFactura(facturaProveedor factura) {
        String SQL = "INSERT INTO `factura_proveedor`(`nro_pedido`,`tipo_factura`, `nro_factura`, `total`, `costo_flete`, `forma_pago`, `fecha_pago`, `fecha_factura`, `estado`, `proveedor_id`) "
                + "VALUES "
                + "("+factura.getNumPedido()+",'"+factura.getTipo_factura()+"',"+factura.getNumFactura()+","+factura.getTotal()+","+factura.getFlete()+",'"+factura.getForma_pago()+"',"+Statics.Funciones.dateParse(factura.getFecha_pago())+","+Statics.Funciones.dateParse(factura.getFecha_factura())+",'"+factura.getEstado()+"',"+factura.getIdProv()+")";
        conexion.EjecutarOperacion(SQL);
        SQL = "SELECT * FROM factura_proveedor order by nro_factura desc limit 1";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        String nroFact = "";
        try {
            rs.next();
            nroFact = rs.getString("nro_factura");
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        SQL = "INSERT INTO renglon_factura(sub_total,nro_factura,producto_id,costo_prod,cantidad) VALUES ";
        for(int i = factura.getRenglones().size() ; i > 0 ; i--){            
            SQL += "("+factura.getRenglones().get(i).getSubTotal()+","+nroFact+","+factura.getRenglones().get(i).getP().getId()+","+factura.getRenglones().get(i).getCosto()+","+factura.getRenglones().get(i).getCantidad()+"),";
        }
        SQL += "("+factura.getRenglones().get(0).getSubTotal()+","+nroFact+","+factura.getRenglones().get(0).getP().getId()+","+factura.getRenglones().get(0).getCosto()+","+factura.getRenglones().get(0).getCantidad()+")";
        conexion.EjecutarOperacion(SQL);
    }

    public void checkStockPedido(facturaProveedor factura, Pedido pedidoFact) {
        String SQL = "";
        int renCompletos = 0;
        ArrayList<renglonFactura> rfs = factura.getRenglones();
        ArrayList<renglonPedido> rpeds = pedidoFact.getRenglones();
        for(int i = 0 ; i < rpeds.size();i++){
            int res = rfs.indexOf(rpeds.get(i));
            if(res != -1){
                if(rfs.get(res).getCantidad()>= rpeds.get(i).getCantFaltante()){
                    SQL = "UPDATE `renglon_nota` SET `cant_faltante`= 0 WHERE id ="+rpeds.get(i).getId();
                    rpeds.get(i).setCantFaltante(0);
                }else{
                    rpeds.get(i).setCantFaltante(rpeds.get(i).getCantFaltante() - rfs.get(res).getCantidad());
                    SQL = "UPDATE `renglon_nota` SET `cant_faltante`= "+rpeds.get(i).getCantFaltante()+" WHERE id ="+rpeds.get(i).getId();
                }
                conexion.EjecutarOperacion(SQL);
                insertarStockFactura(rfs.get(res));
                insertarStockPedido(rpeds.get(i));
            }
            if(rpeds.get(i).getCantFaltante()<=0){
                renCompletos++;
            }
        }
        if(renCompletos == rpeds.size()){
            SQL = "UPDATE `nota_pedido` SET `estado`='COMPLETADO' WHERE nro_pedido ="+pedidoFact.getNumPedido();
        }else{
            SQL = "UPDATE `nota_pedido` SET `estado`='PARCIAL' WHERE nro_pedido ="+pedidoFact.getNumPedido();
        }
    }

}
