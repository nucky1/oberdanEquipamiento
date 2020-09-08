/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Contacto;
import Models.Proveedor;
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
           String SQL = "SELECT proveedores.*,barrio.nombre,localidad.nombre,provincia.nombre,pais.nombre"
              + " FROM proveedores"
              + " WHERE proveedores."+tipo_busqueda+" like '%"+valor+"%'"
              + " INNER JOIN barrio ON barrio.id = proveedores.barrio_id"
              + " INNER JOIN localidad ON localidad.id = barrio.localidad_id"
              + " INNER JOIN provincia ON localidad.provincia_id = provincia.id"
              + " INNER JOIN pais ON pais.id = provincia.pais_id";
           ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
           
           List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL PROVEEDOR
                    Proveedor p = new Proveedor();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("proveedor"));
                    //direccion
                    p.setNacionalidad(rs.getString("pais.nombre"));
                    p.setProvincia(rs.getString("provincia.nombre"));
                    p.setCiudad(rs.getString("localidad.nombre"));
                    p.setBarrio(rs.getString("barrio.nombre"));
                    p.setBarrioId(rs.getInt("barrio.id"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setNro(rs.getString("numero"));
                    p.setCodigoPostal(rs.getString("codPostal"));
                    p.setReferencia(rs.getString("referencia"));
                    //datos bancarios
                    p.setCbu(rs.getString("cbu"));
                    p.setIva(rs.getString("iva"));
                    p.setCuit(rs.getString("cuit"));
                    p.setSaldo(rs.getFloat("saldo"));
                    p.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE tipo_persona = 'PROVEEDOR' AND id_persona = "+p.getId();
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
                    p.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(p);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
           
    }
    
    public List<Proveedor> buscarProveedor(int id) {
      String SQL = "SELECT proveedores.*,barrio.nombre,barrio.id,localidad.nombre,pais.nombre"
              + " FROM proveedores"
              + " WHERE proveedores.id = "+id
              + " INNER JOIN barrio ON barrio.id = proveedores.barrio_id"
              + " INNER JOIN localidad ON localidad.id = barrio.localidad_id"
              + " INNER JOIN provincia ON localidad.provincia_id = provincia.id"
              + " INNER JOIN pais ON pais.id = provincia.pais_id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL PROVEEDOR
                    Proveedor p = new Proveedor();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("proveedor"));
                    //direccion
                    p.setNacionalidad(rs.getString("pais.nombre"));
                    p.setProvincia(rs.getString("provincia.nombre"));
                    p.setCiudad(rs.getString("localidad.nombre"));
                    p.setBarrio(rs.getString("barrio.nombre"));
                    p.setBarrioId(rs.getInt("barrio.id"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setNro(rs.getString("numero"));
                    p.setCodigoPostal(rs.getString("codPostal"));
                    p.setReferencia(rs.getString("referencia"));
                    //datos bancarios
                    p.setCbu(rs.getString("cbu"));
                    p.setIva(rs.getString("iva"));
                    p.setCuit(rs.getString("cuit"));
                    p.setSaldo(rs.getFloat("saldo"));
                    p.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE tipo_persona = 'PROVEEDOR' AND id_persona = "+p.getId();
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
                    p.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(p);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
    }
    
    public boolean actualizarProveedor(Proveedor p){
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "UPDATE proveedores SET proveedor = '"+p.getNombre()+"'"
                + ", barrio_id = "+p.getBarrioId()
                + ",direccion = '"+p.getDireccion()+"'"
                + ",numero = "+p.getNro()
                + ",codPostal = '"+p.getCodigoPostal()+"'"
                + ",referencia = '"+p.getReferencia()+"'"
                + ",cbu = '"+p.getCbu()+"'"
                + ",iva = '"+p.getIva()+"'"
                + ",cuit = '"+p.getCuit()
                + ",saldo = "+p.getSaldo()
                + ",ingreso_bruto = '"+p.getIngresoBruto()+"' WHERE id = "+p.getId();
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        if(res == 0){
            exito = false;
        }else{
            if(p.getContacto().size() > 0){
                SQL = " DELETE FROM contactos WHERE persona_id = "+p.getId()+" AND tipo_persona = 'PROVEEDOR'";
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                for(int i = p.getContacto().size()-1 ; i > 0; i--){
                    SQL += "("+p.getId()+",'"+p.getContacto().get(i).getContacto()+"','"+p.getContacto().get(i).getTipo()+"','PROVEEDOR'),";
                }
                SQL += "("+p.getId()+",'"+p.getContacto().get(0).getContacto()+"','"+p.getContacto().get(0).getTipo()+"','PROVEEDOR')";
                res = conexion.EjecutarOperacion(SQL);
                if(res == 0){
                    exito = false;
                }
            }
        }
        if(exito){
            conexion.transaccionCommit("commitear"); 
            conexion.transaccionCommit("activarCommit"); 
        }else{
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
        }
        return exito;
    }
    
    public boolean guardarProveedor(Proveedor p) {
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "INSERT INTO proveedores (proveedor, barrio_id,direccion,numero,codPostal,referencia,cbu,iva,cuit,saldo,ingreso_bruto) "
                + "VALUES('"+p.getNombre()+"',"+p.getBarrioId()+",'"+p.getDireccion()+"',"+p.getNro()+",'"+p.getCodigoPostal()+"','"+p.getReferencia()+"','"+p.getCbu()+"','"+
        p.getIva()+"',"+
        p.getCuit()+","+
        p.getSaldo()+",'"+
        p.getIngresoBruto()+"')";
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        if(res == 0){
            exito = false;
        }else{
            if(p.getContacto().size() > 0){
                SQL = "SELECT id FROM proveedores WHERE MAX(id)";
                ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
                try {
                    while(rs.next()){
                        p.setId(rs.getInt("id"));
                    }
                    SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                    for(int i = p.getContacto().size()-1 ; i > 0; i--){
                        SQL += "("+p.getId()+",'"+p.getContacto().get(i).getContacto()+"','"+p.getContacto().get(i).getTipo()+"','PROVEEDOR'),";
                    }
                    SQL += "("+p.getId()+",'"+p.getContacto().get(0).getContacto()+"','"+p.getContacto().get(0).getTipo()+"','PROVEEDOR')";
                    res = conexion.EjecutarOperacion(SQL);
                } catch (SQLException ex) {
                    res = 0;
                }
                if(res == 0){
                    exito = false;
                }
            }
        }
        if(exito){
            conexion.transaccionCommit("commitear"); 
            conexion.transaccionCommit("activarCommit"); 
        }else{
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
        }
        return exito;
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
