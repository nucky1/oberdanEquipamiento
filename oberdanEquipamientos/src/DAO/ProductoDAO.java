/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Producto;
import Views.ProductosView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author demig
 */
public class ProductoDAO {
    private static ProductoDAO ProductoDAO = null;
    private ProductosView view;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    public ProductoDAO(){}
    public static ProductoDAO getInstance(){
        if(ProductoDAO == null) ProductoDAO = new ProductoDAO();
        return ProductoDAO;
    }
    
    public void setVista(ProductosView view){
        this.view = view;
    }
    
    public List<Producto> cargarProductos(ResultSet rs){
        List<Producto> list =  new ArrayList<>();
        try{
            Producto p = new Producto();
            while(rs.next()){   
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
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
            ex.printStackTrace();
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
        String SQL = "UPDATE `articulos` SET `nombre`='"+p.getNombre()+"',`codigo_barra`='"+p.getCodigoBarra()+"',`rubro_id`="+p.getIdProductoRubro()+","
                + "`stock_existente`="+p.getStock()+","
                + "`stock_minimo`="+p.getStockMin()+",`proveedor_id`="+p.getIdProveedorActual()+",`iva`="+p.getIva()+",`observaciones`='"+p.getObservaciones()+"',"
                + "`costo_flete`="+p.getCostoFlete()+",`sobretasa_iva`="+p.getSobretasaIva()+",`impuesto_interno`="+p.getImpuesto_interno()+",`impuesto_int_fijo`="+p.getImpuesto_int_fijo()
                + " WHERE id = "+p.getId();
        return conexion.EjecutarOperacion(SQL);
    }
    public List<Producto> buscarProducto(String atributo, String valor) {
        String SQL = "SELECT articulo.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro, MAX(art_stock.precio_compra),proveedores.proveedor as precioCosto"
               + " FROM proveedores, articulo, art_rubro, art_stock"
               + " WHERE articulo." + atributo+" like '%"+valor+"%'"
               + " AND articulo.rubro_id = art_rubro.id"
               + " AND articulo.id = art_stock.producto_id"
               + " AND articulo.proveedor_id = proveedores.id"
               + " AND articulo.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }
    public List<Producto> buscarProducto(int id){
        String SQL = "SELECT articulo.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro, MAX(art_stock.precio_compra),proveedores.proveedor as precioCosto"
               + " FROM proveedores, articulo, art_rubro, art_stock"
               + " WHERE articulo.id LIKE '"+id+"%'"
               + " AND articulo.rubro_id = art_rubro.id"
               + " AND articulo.id = art_stock.producto_id"
               + " AND articulo.proveedor_id = proveedores.id"
               + " AND articulo.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
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
            System.out.println(ex.getMessage());
        }
        return totalVentas;
    }

}
