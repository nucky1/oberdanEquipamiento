/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Proveedor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author demig
 */
public class ProveedoresDAO {
         
    private static ProveedoresDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();

    protected ProveedoresDAO() {
       
    }
    
    public static ProveedoresDAO getInstance(){
        if (controller == null) {
            controller = new ProveedoresDAO();
        }
        return controller;
    }
    
    
    public List<Proveedor> buscarProveedor(String tipo_busqueda, String valor) {
           String SQL = "SELECT ";
           ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
           
           List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   Proveedor p = new Proveedor();
                   p.setId(rs.getInt("proveedores_id"));
                   p.setNombre(rs.getString("proveedores_nombre"));
                   p.setCuit(rs.getString("proveedores_cuit"));
                   p.setCatFiscal(rs.getString("proveedores_categoriaFiscal"));
                   p.setTel(rs.getString("proveedores_telefono"));
                   p.setLocalidad(rs.getString("proveedores_localidad"));
                   p.setDireccion(rs.getString("proveedores_direccion"));
                   p.setCbu(rs.getString("proveedores_cbu"));
                   p.setMail(rs.getString("proveedores_mail"));
                   p.setNombreContacto(rs.getString("proveedores_nombreContacto"));
                   p.setTelContacto(rs.getString("proveedores_telefonoContacto"));
                   p.setObservaciones(rs.getString("proveedores_observaciones"));
                   
                   list.add(p);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
           
    }
    
    public List<Proveedor> buscarProveedor(int id) {
      String SQL = "SELECT ";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   Proveedor p = new Proveedor();
                   p.setId(rs.getInt("proveedores_id"));
                   p.setNombre(rs.getString("proveedores_nombre"));
                   p.setCuit(rs.getString("proveedores_cuit"));
                   p.setCatFiscal(rs.getString("proveedores_categoriaFiscal"));
                   p.setTel(rs.getString("proveedores_telefono"));
                   p.setLocalidad(rs.getString("proveedores_localidad"));
                   p.setDireccion(rs.getString("proveedores_direccion"));
                   p.setCbu(rs.getString("proveedores_cbu"));
                   p.setMail(rs.getString("proveedores_mail"));
                   p.setNombreContacto(rs.getString("proveedores_nombreContacto"));
                   p.setTelContacto(rs.getString("proveedores_telefonoContacto"));
                   p.setObservaciones(rs.getString("proveedores_observaciones"));
                   
                   list.add(p);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
    }
    

    
    public int guardarProveedor(Proveedor p) {
        int res = Modelo.InsertarProveedor(p);
        return res;
    }

    
    public int actualizarProveedor(Proveedor p) {
        int res = Modelo.ActualizarProveedor(p);
        return res;
    }

    
    public Proveedor getProveedor(int id) {
       Proveedor p = new Proveedor();
       String SQL = "SELECT proveedores.proveedor, proveedores.id from proveedores "
               + "WHERE id = "+id+" AND state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                p.setId(id);
                p.setNombre(rs.getString("proveedores_nombre"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return p;
    }
    
    
    public Proveedor getProveedor(String nombre) {
       Proveedor p = new Proveedor();
        ResultSet rs = Modelo.BuscarProveedor("proveedores_nombre",nombre);
        try{
            while(rs.next()){
                p.setId(rs.getInt("proveedores_id"));
                p.setNombre(rs.getString("proveedores_nombre"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return p;
    }

    
    public int eliminarProveedor(Proveedor p) {
        int res = Main.Modelo.EliminarProveedor(p);
            Main.Modelo.actualizarProveedorActual(p.getId());
        return res;
    }
    //-----------------------------------------BUSCADOR
     public ArrayList<Proveedor> buscarProveedorReducido(String tipo_busqueda, String valor) {
        ResultSet rs = Modelo.BuscarProveedor(tipo_busqueda, valor);
        return actualizarTablaProveedores(rs);
    }

    public ArrayList<Proveedor> buscarProveedorReducido(int id) {
        ResultSet rs = Modelo.BuscarProveedor(id);
        return actualizarTablaProveedores(rs);
    }
    
    private ArrayList<Proveedor> actualizarTablaProveedores(ResultSet rs){
        ArrayList<Proveedor> list = new ArrayList<>();
        
        try{
            
            while(rs.next()){
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("proveedores_id"));
                p.setNombre(rs.getString("proveedores_nombre"));
                p.setContacto(rs.getString("proveedores_telefono"));
                list.add(p);
            }
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public void proveedorSeleccionado(int id) {
        view.cerrarBuscador();
    }
    
    

}
