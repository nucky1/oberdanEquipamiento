/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Contacto;
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
           String SQL = "SELECT proveedores.*,barrio.nombre,localidad.nombre,pais.nombre"
              + " FROM proveedores"
              + " WHERE proveedores."+tipo_busqueda+" like '%"+valor+"%'"
              + " INNER JOIN barrio ON barrio.id = proveedores.barrio_id"
              + " INNER JOIN barrio ON localidad.id = barrio.localidad_id"
              + " INNER JOIN barrio ON pais.id = localidad.pais_id";
           ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
           
           List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL PROVEEDOR
                    Proveedor proveedor_selected = new Proveedor();
                    proveedor_selected.setId(rs.getInt("id"));
                    proveedor_selected.setNombre(rs.getString("id"));
                    //direccion
                    proveedor_selected.setNacionalidad(rs.getString("nombreNacionalidad"));
                    proveedor_selected.setProvincia(rs.getString("nombreProvincia"));
                    proveedor_selected.setCiudad(rs.getString("nombreCiudad"));
                    proveedor_selected.setBarrio(rs.getString("nombreBarrio"));
                    proveedor_selected.setDireccion(rs.getString("direccion"));
                    proveedor_selected.setNro(rs.getString("numero"));
                    proveedor_selected.setCodigoPostal(rs.getString("codPostal"));
                    proveedor_selected.setReferencia(rs.getString("referencia"));
                    //datos bancarios
                    proveedor_selected.setCbu(rs.getString("cbu"));
                    proveedor_selected.setIva(rs.getString("iva"));
                    proveedor_selected.setCuit(rs.getString("cuit"));
                    proveedor_selected.setSaldo(rs.getFloat("saldo"));
                    proveedor_selected.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE tipo_persona = 'PROVEEDOR' AND id_persona = "+proveedor_selected.getId();
                    ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                    List<Contacto> contactos = new ArrayList<>();
                    try{
                        while(rs.next()){
                            Contacto c = new Contacto();
                            c.setId(rs.getInt("id"));
                            c.setContacto(rs.getString("contacto"));
                            c.setTipo(rs.getString("tipo"));
                            contactos.add(c);
                        }
                    }catch(Exception ex){
                        //no hago nada para que no se trabe
                    }
                    proveedor_selected.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(proveedor_selected);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
           
    }
    
    public List<Proveedor> buscarProveedor(int id) {
      String SQL = "SELECT proveedores.*,barrio.nombre,localidad.nombre,pais.nombre"
              + " FROM proveedores"
              + " WHERE proveedores.id = "+id
              + " INNER JOIN barrio ON barrio.id = proveedores.barrio_id"
              + " INNER JOIN barrio ON localidad.id = barrio.localidad_id"
              + " INNER JOIN barrio ON pais.id = localidad.pais_id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL PROVEEDOR
                    Proveedor proveedor_selected = new Proveedor();
                    proveedor_selected.setId(rs.getInt("id"));
                    proveedor_selected.setNombre(rs.getString("id"));
                    //direccion
                    proveedor_selected.setNacionalidad(rs.getString("nombreNacionalidad"));
                    proveedor_selected.setProvincia(rs.getString("nombreProvincia"));
                    proveedor_selected.setCiudad(rs.getString("nombreCiudad"));
                    proveedor_selected.setBarrio(rs.getString("nombreBarrio"));
                    proveedor_selected.setDireccion(rs.getString("direccion"));
                    proveedor_selected.setNro(rs.getString("numero"));
                    proveedor_selected.setCodigoPostal(rs.getString("codPostal"));
                    proveedor_selected.setReferencia(rs.getString("referencia"));
                    //datos bancarios
                    proveedor_selected.setCbu(rs.getString("cbu"));
                    proveedor_selected.setIva(rs.getString("iva"));
                    proveedor_selected.setCuit(rs.getString("cuit"));
                    proveedor_selected.setSaldo(rs.getFloat("saldo"));
                    proveedor_selected.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE tipo_persona = 'PROVEEDOR' AND id_persona = "+proveedor_selected.getId();
                    ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                    List<Contacto> contactos = new ArrayList<>();
                    try{
                        while(rs.next()){
                            Contacto c = new Contacto();
                            c.setId(rs.getInt("id"));
                            c.setContacto(rs.getString("contacto"));
                            c.setTipo(rs.getString("tipo"));
                            contactos.add(c);
                        }
                    }catch(Exception ex){
                        //no hago nada para que no se trabe
                    }
                    proveedor_selected.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(proveedor_selected);
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
