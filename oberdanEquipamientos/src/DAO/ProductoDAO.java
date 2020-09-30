/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Producto;
import Views.ProductosView;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                list.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
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
