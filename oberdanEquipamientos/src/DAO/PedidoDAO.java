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
        // Pendiente:
        // Añadir un parametro mas para que separa cuando solo quiero ver los parciales / pendientes    
        
        HashMap<String,Pedido> pedidos = new HashMap();
         Pedido p = new Pedido();
        String SQL = "SELECT * FROM `nota_pedido` "
                + "INNER JOIN (SELECT renglon_nota.id as renglon_nota_id, "
                                    + "renglon_nota.sub_total, renglon_nota.nota_pedido_id, "
                                    + "renglon_nota.costo_prod,renglon_nota.cantidad, "
                                    + "renglon_nota.cant_faltante,articulos.* "

                + " FROM renglon_nota "
                + " INNER JOIN articulos "
                + "  ON articulos.id = renglon_nota.producto_id "
                + "  ORDER BY renglon_nota.nota_pedido_id) "
                + "AS renglon "
                + "ON nota_pedido.nro_pedido = renglon.nota_pedido_id "
                + "WHERE nota_pedido.proveedor_id = "+provId
                + " ORDER BY nota_pedido.nro_pedido";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        //System.out.println(" sql en getPedidos:\n "+SQL );
        try{
            while (rs.next()) {
               
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
                rp.setNeto(rs.getFloat("costo_prod"));
                rp.setCantFaltante(rs.getInt("cant_faltante"));
                //prod
                prod.setId(rs.getInt("id"));
                prod.setCod(rs.getInt("cod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setIva(rs.getFloat("iva"));
                rp.setP(prod);
                String numPedido = p.getNumPedido();
                //System.out.println("el num pedido es :"+numPedido);
                if(numPedido!= null){
                    pedidos.get(p.getNumPedido()).addRenglon(rp); 
                }
                
            }
        }catch(Exception e){
            new Statics.ExceptionManager().saveDump(e, "", Main.isProduccion);
        }
        //System.out.println(pedidos.values());
        return new ArrayList<Pedido>(pedidos.values());
    }

    public Pedido insertPedido(int idProv){
     
        Pedido p = null;
        //p.limpiarRenglones();
        try {
            String SQL = "INSERT INTO nota_pedido(proveedor_id,estado) VALUES ("+idProv+",'PENDIENTE')";
            conexion.EjecutarOperacion(SQL);
            /// ojo con esto!
            //nro_pedido tiene auto increment, por eso lo inserta y recupera el numero creado
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
        if(p.getId()>0){
            String SQL = "DELETE FROM nota_pedido WHERE id="+p.getId();
            conexion.EjecutarOperacion(SQL);
        }
        
    }
    public boolean insertRP(Pedido pedidoNuevo) {
        String SQL = "INSERT INTO renglon_nota(nota_pedido_id,producto_id,costo_prod,cantidad,cant_faltante,sub_total) VALUES ";
        for(int i = pedidoNuevo.getRenglones().size()-1; i > 0 ;i--){
            SQL += "("+pedidoNuevo.getNumPedido()+","+pedidoNuevo.getRenglones().get(i).getP().getId()+","+pedidoNuevo.getRenglones().get(i).getNeto()+","+pedidoNuevo.getRenglones().get(i).getCantidad()+","+pedidoNuevo.getRenglones().get(i).getCantidad()+","+pedidoNuevo.getRenglones().get(i).getSubTotal()+"),";
            insertarStockPedido(pedidoNuevo.getRenglones().get(i));
        }
        SQL += "("+pedidoNuevo.getNumPedido()+","+pedidoNuevo.getRenglones().get(0).getP().getId()+","+pedidoNuevo.getRenglones().get(0).getNeto()+","+pedidoNuevo.getRenglones().get(0).getCantidad()+","+pedidoNuevo.getRenglones().get(0).getCantidad()+","+pedidoNuevo.getRenglones().get(0).getSubTotal()+")";
        insertarStockPedido(pedidoNuevo.getRenglones().get(0));
        conexion.EjecutarOperacion(SQL);
        SQL = "UPDATE nota_pedido SET total ="+pedidoNuevo.getTotal()+" WHERE nro_pedido = "+pedidoNuevo.getNumPedido();
        if(conexion.EjecutarOperacion(SQL) > 0 ) return true;
        else return false;
    }
    
    /**
     * Este metodo se usa para insertar el stock pedido con el
     * precio de compra correspondiente o crear uno nuevo con este precio
     * servira luego para modulo de balance contable.
     * @param rp 
     */
    public void insertarStockPedido(renglonPedido rp){
        String SQLstock = "UPDATE `art_stock` SET stock_pedido = "+rp.getCantFaltante()+" WHERE producto_id = "+rp.getP().getId()+" AND precio_compra ="+rp.getNeto();
        int filasAfectadas = conexion.EjecutarOperacion(SQLstock);
        System.out.println(" Al insertar Stock pedido, SQL : \n"+SQLstock);
        if(filasAfectadas == 0){
            SQLstock = "INSERT INTO `art_stock`(`producto_id`,`stock_pedido`,`precio_compra`) "
                    + "VALUES ("+rp.getP().getId()+","+rp.getCantFaltante()+","+rp.getNeto()+")";
              conexion.EjecutarOperacion(SQLstock);
        System.out.println(" Al insertar Stock pedido, luego ejecuta este SQL  : \n"+SQLstock);
        }
      
    }
    /**
     * Este metodo se usa para insertar el stock que llego
     * precio de compra correspondiente o crear uno nuevo con este precio
     * servira luego para modulo de balance contable.
     * @param rf 
     */
    public void insertarStockFactura (renglonFactura rf){
        String SQLstock = "UPDATE `art_stock` SET stock_pedido = IF(stock_pedido-"+rf.getCantidad()+" < 0, 0, stock_pedido - "+rf.getCantidad()+"),"
                + " stock_actual = stock_actual + "+rf.getCantidad()
                + " WHERE producto_id = "+rf.getP().getId()+" AND precio_compra = "+rf.getCosto();
        System.out.println(" Al insertar Stock factura, SQL : \n"+SQLstock);
        int filasAfectadas = conexion.EjecutarOperacion(SQLstock);
        
        if(filasAfectadas == 0){
            System.out.println("filas afectadas == 0");
            SQLstock = "INSERT INTO `art_stock`(`producto_id`,`stock_actual`,`precio_compra`) "
                    + "VALUES ("+rf.getP().getId()+","+rf.getCantidad()+","+rf.getCosto()+")";
            conexion.EjecutarOperacion(SQLstock);
          System.out.println(" Al insertar Stock factura, luego ejecuta este SQL  : \n"+SQLstock);
        
        }
        
    }
    public boolean insertarFactura(facturaProveedor factura) {
        int resultadoSQL= -1;
        String SQL = "INSERT INTO `factura_proveedor`(`nro_pedido`,`tipo_factura`, `nro_factura`, `total`, `costo_flete`, `forma_pago`, `fecha_pago`, `fecha_factura`, `estado`, `proveedor_id`) "
                + "VALUES "
                + "("+factura.getNumPedido()+",'"+factura.getTipo_factura()+"',"+factura.getNumFactura()+","+factura.getTotal()+","+factura.getFlete()+",'"+factura.getForma_pago()+"', '"+Statics.Funciones.dateParse(factura.getFecha_pago())+"' , '"+Statics.Funciones.dateParse(factura.getFecha_factura())+"' ,'"+factura.getEstado()+"',"+factura.getIdProv()+")";
        resultadoSQL =conexion.EjecutarOperacion(SQL);
        // da 1 si ok
        if(resultadoSQL<0){
            return false;
        }
        System.out.println("SQL es : "+SQL);
        System.out.println("al insertar factura tuve resultado: "+resultadoSQL);
        //ojo con esto xq cuando tengas multiples pcs puede fallar
        // ya tengo el numero de factura en factura.getNumFactura()
        // si es un control de carga, entonces modificarlo por que no defiende de errores
        //SQL = "SELECT * FROM factura_proveedor order by nro_factura desc limit 1"; ANDABA MAL ESTA MIERAD!!!
        
        SQL = "SELECT * FROM factura_proveedor WHERE nro_factura= "+factura.getNumFactura();
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        String nroFact = "";
        try {
            rs.next();
            nroFact = rs.getString("nro_factura");
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        
        SQL = "INSERT INTO renglon_factura(sub_total,nro_factura,producto_id,costo_prod,cantidad) VALUES ";
        System.out.println(" el tamaño de los renglones es: " +factura.getRenglones().size());
        for(int i = factura.getRenglones().size()-1 ; i > 0 ; i--){            
            SQL += "("+factura.getRenglones().get(i).getSubTotal()+","+factura.getNumFactura()+","+factura.getRenglones().get(i).getP().getId()+","+factura.getRenglones().get(i).getCosto()+","+factura.getRenglones().get(i).getCantidad()+"),";
        }
        SQL += "("+factura.getRenglones().get(0).getSubTotal()+","+nroFact+","+factura.getRenglones().get(0).getP().getId()+","+factura.getRenglones().get(0).getCosto()+","+factura.getRenglones().get(0).getCantidad()+")";
        resultadoSQL=conexion.EjecutarOperacion(SQL);
        System.out.println("EL insert en renglon factura es:  "+SQL);
        System.out.println("Resultado fue "+resultadoSQL);
        if (resultadoSQL > 0){
            return true;
        }
        else{
            return false;
        }
        
        
    }

    public void checkStockPedido(facturaProveedor factura, Pedido pedidoFact) {
        String SQL = "";
        int renCompletos = 0;
        ArrayList<renglonFactura> renglonesFactura = factura.getRenglones();
        ArrayList<renglonPedido> renglonesPedido = pedidoFact.getRenglones();
        System.out.println("El tamaño de renglones pedido es: "+renglonesPedido.size());
       
        for(int i = 0 ; i <renglonesPedido.size() ;i++){
            int res = renglonesFactura.indexOf(renglonesPedido.get(i));
         
            if(res != -1){
                if(renglonesFactura.get(res).getCantidad()>= renglonesPedido.get(i).getCantFaltante()){
                    //Hay un trigger cuando update----
                    //el problema es que le manda cualquier id al producto
                    SQL = "UPDATE `renglon_nota` SET `cant_faltante`= 0 WHERE id ="+renglonesPedido.get(i).getId();
                    renglonesPedido.get(i).setCantFaltante(0);
                }else{
                    renglonesPedido.get(i).setCantFaltante(renglonesPedido.get(i).getCantFaltante() - renglonesFactura.get(res).getCantidad());
                    SQL = "UPDATE `renglon_nota` SET `cant_faltante`= "+renglonesPedido.get(i).getCantFaltante()+" WHERE id ="+renglonesPedido.get(i).getId();
                }
                conexion.EjecutarOperacion(SQL);
                System.out.println("En check stock pedido, sql es: "+SQL);
                System.out.println("En check stock pedido res es :"+res);
                insertarStockFactura(renglonesFactura.get(res));
                insertarStockPedido(renglonesPedido.get(i));
            }
            if(renglonesPedido.get(i).getCantFaltante()<=0){
                renCompletos++;
            }
        }
        if(renCompletos == renglonesPedido.size()){
            SQL = "UPDATE `nota_pedido` SET `estado`='COMPLETADO' WHERE nro_pedido ="+pedidoFact.getNumPedido();
        }else{
            SQL = "UPDATE `nota_pedido` SET `estado`='PARCIAL' WHERE nro_pedido ="+pedidoFact.getNumPedido();
        }
        conexion.EjecutarOperacion(SQL);
        System.out.println("SQL en checkStockPedido "+SQL);
    }

}
