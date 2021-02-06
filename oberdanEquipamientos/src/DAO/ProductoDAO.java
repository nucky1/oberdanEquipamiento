/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Producto;
import Views.Main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author demig
 */
public class ProductoDAO {
    private static ProductoDAO ProductoDAO = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    public ProductoDAO(){}
    public static ProductoDAO getInstance(){
        if(ProductoDAO == null) ProductoDAO = new ProductoDAO();
        return ProductoDAO;
    }

    
    public List<Producto> cargarProductos(ResultSet rs){
        List<Producto> list =  new ArrayList<>();
        try{
            Producto p = new Producto();
            while(rs.next()){   
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCod(rs.getInt("cod"));
                p.setCodigoBarra(rs.getString("codigo_barra"));
                p.setObservaciones(rs.getString("observaciones"));
                p.setNombreRubro(rs.getString("nombreRubro"));
                p.setIdProductoRubro(rs.getInt("idRubro"));
                p.setCostoFlete(rs.getFloat("costo_flete"));
                p.setPrecioCosto(rs.getFloat("precioCosto"));
                p.setStock(rs.getInt("stock_existente"));
                p.setStockMin(rs.getInt("stock_minimo"));
                p.setIdProveedorActual(rs.getInt("proveedor_id"));
                p.setIva(rs.getFloat("iva"));
                p.setSobretasaIva(rs.getFloat("sobretasa_iva"));
                p.setImpuesto_interno(rs.getFloat("impuesto_interno"));
                p.setImpuesto_int_fijo(rs.getFloat("impuesto_int_fijo"));
                list.add(p);
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return list;
    }
    public int insertarProducto(Producto p){
        String SQL = "INSERT INTO `articulos`(`nombre`, `codigo_barra`, `rubro_id`,"
                + " `stock_existente`, `stock_minimo`, `proveedor_id`,"
                + " `impuesto_interno`, `impuesto_int_fijo`, `sobretasa_iva`,"
                + " `iva`, `observaciones`, `costo_flete`)"
                + " VALUES ('"+p.getNombre()+"','"+p.getCodigoBarra()+"',"+p.getIdProductoRubro()+","
                +p.getStock()+","+p.getStockMin()+","+p.getIdProveedorActual()+","
                +p.getImpuesto_interno()+","+p.getImpuesto_int_fijo()+","+p.getSobretasaIva()+","
                +p.getIva()+",'"+p.getObservaciones()+"',"+p.getCostoFlete()+")";        
        return conexion.EjecutarOperacion(SQL);
    }
    public int actualizarProducto(Producto p){
        String SQL = "UPDATE `articulos` SET `nombre`='"+p.getNombre()+"',`cod`='"+p.getCod()+"',`codigo_barra`='"+p.getCodigoBarra()+"',`rubro_id`="+p.getIdProductoRubro()+","
                + "`stock_existente`="+p.getStock()+","
                + "`stock_minimo`="+p.getStockMin()+",`proveedor_id`="+p.getIdProveedorActual()+",`iva`="+p.getIva()+",`observaciones`='"+p.getObservaciones()+"',"
                + "`costo_flete`="+p.getCostoFlete()+",`sobretasa_iva`="+p.getSobretasaIva()+",`impuesto_interno`="+p.getImpuesto_interno()+",`impuesto_int_fijo`="+p.getImpuesto_int_fijo()
                + " WHERE id = "+p.getId();
        return conexion.EjecutarOperacion(SQL);
    }
    public List<Producto> buscarProducto(String atributo, String valor) {
        String SQL = "SELECT articulos.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro,  IFNULL(MAX(art_stock.precio_compra), 0) as precioCosto,proveedores.proveedor"
                + " FROM articulos "
                + "LEFT JOIN art_rubro ON articulos.rubro_id = art_rubro.id "
                + "LEFT JOIN proveedores ON articulos.proveedor_id = proveedores.id "
                + "LEFT JOIN art_stock ON art_stock.producto_id = articulos.id "
                + "WHERE LOWER(articulos." + atributo+") like '%"+valor+"%' "
                + "AND articulos.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }
    public List<Producto> buscarProducto(int id){
        String SQL = "SELECT articulos.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro,  IFNULL(MAX(art_stock.precio_compra), 0) as precioCosto,proveedores.proveedor"
               + " FROM proveedores, articulos, art_rubro, art_stock"
               + "LEFT JOIN art_rubro ON articulos.rubro_id = art_rubro.id "
               + "LEFT JOIN proveedores ON articulos.proveedor_id = proveedores.id "
               + "LEFT JOIN art_stock ON art_stock.producto_id = articulos.id "
               + " WHERE articulos.cod LIKE '"+id+"%'"
               + " AND articulos.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }
    
    public int productoEliminado(int cod){
        String SQL = "SELECT articulos.id,COUNT(*)"
               + " FROM articulos"
               + " WHERE articulos.cod = "+cod;
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            rs.next();
            
            return rs.getInt("articulos.id");
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    
    public void buscarDevolucion(int id){
        //ResultSet rs = Modelo.BuscarDevoluciones(id);
        //cargarDevoluciones(rs);
    }
    
    public void buscarDevolucion(String atributo, String valor){
        //ResultSet rs = Modelo.BuscarDevoluciones(atributo,valor);
        //cargarDevoluciones(rs);
    }


    public int calcularStockDevoluciones(int id){
        ResultSet rs = null; //= Modelo.getStockDevolucion(id);
        int totalVentas = 0 ;
        try {
            while(rs.next()){
                totalVentas= rs.getInt("totalVentas");
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return totalVentas;
    }

    public int revalidarProd(Producto p) {
        String SQL = "UPDATE `articulos` SET `nombre`='"+p.getNombre()+"',`cod`='"+p.getCod()+","
                + ",`proveedor_id`="+p.getIdProveedorActual()+",`observaciones`='"+p.getObservaciones()+"',"
                + "`stock_minimo`="+p.getStockMin()+",`state`='ACTIVO'"
                +" WHERE id = "+p.getId();
        return conexion.EjecutarOperacion(SQL);
    }
    public int eliminarProducto(Producto p) {
        String SQL = "UPDATE `articulos` SET `state`='BAJA'"
                +" WHERE id = "+p.getId();
        return conexion.EjecutarOperacion(SQL);
    }
    public int nuevoProducto(Producto p) {
        String SQL = "INSERT INTO `articulos` SET `nombre`='"+p.getNombre()+"',`cod`="+p.getCod()+","
                + "`proveedor_id`="+p.getIdProveedorActual()+",`observaciones`='"+p.getObservaciones()+"',"
                + "`stock_minimo`="+p.getStockMin()+",`state`='ACTIVO';";
        return conexion.EjecutarOperacion(SQL);
    }

    public List<Producto> getStockFiltrado(String seleccion) {
        String SQL=null;
        
        switch (seleccion){
        case "Existente" :
        {  
            SQL = "SELECT articulos.id, articulos.nombre,articulos.stock_existente AS stock,"
                + "articulos.precio_venta, articulos.tipo FROM articulos "
                + "WHERE articulos.stock_existente >= 1 AND articulos.state= 'ACTIVO'";
            break;
        }
        case "Completo" :
        {   
            SQL = "SELECT articulos.id,articulos.cod, articulos.nombre,articulos.precio_venta, articulos.tipo, stock.stock_completo AS stock FROM articulos LEFT JOIN (SELECT SUM(stock_actual + stock_pedido - stock_reservado)AS stock_completo, producto_id FROM art_stock) AS stock ON producto_id = articulos.id";
            break;
        }
        case "Pedido":{
            
            SQL = "SELECT articulos.id, articulos.nombre,articulos.stock_existente,stock_pedido AS stock"
                + "articulos.precio_venta, articulos.tipo FROM articulos "
                + "LEFT JOIN art_stock ON art_stock.producto_id = articulos.id"
                + "WHERE articulos.state= 'ACTIVO'";
            break;
        }
    }
        List<Producto> list =  new ArrayList<>();
        
       
        ResultSet rs = null;
        rs = conexion.EjecutarConsultaSQL(SQL);
        
        try{
            
            if(rs.next()){
                if(rs.getInt("id") == 0){
                    return list;
                }
                //rs.last();
            }
            rs.beforeFirst();
        
            while(rs.next()){ 
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecioVenta(rs.getFloat("precio_venta"));
                p.setTipo(rs.getInt("tipo"));
                list.add(p);
               
                
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }

        
        
        return list;
                
       
       
    }
    public JasperViewer generarReporteStock1 (){
        JasperReport reporte = null;
        JasperViewer view = null;
        Connection con = (Connection) conexion.getConexion();
        String path = "src\\Reportes\\ReporteControlStock.jasper";
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint;
            jprint = JasperFillManager.fillReport(reporte, null, con);
            view = new JasperViewer (jprint,false);
        } catch (JRException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return view;
    }

    public void setStockActual(int idStock, float stock) {
        String SQL = "INSERT INTO art_Stock SET stock_actual = "+stock;
        conexion.EjecutarOperacion(SQL);
    }
}
