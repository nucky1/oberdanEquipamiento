/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Contacto;
import Models.Empleado;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hernan
 */
public class EmpleadosDAO {
    private Empleado empleadoLoggeado;
    private static EmpleadosDAO controller=null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    protected EmpleadosDAO(){
        
    }
    public static EmpleadosDAO getInstance(){
        if (controller == null){
            controller=new EmpleadosDAO();
        }
        return controller;
    }
    public boolean loggearEmpleado(String user, String pass) throws SQLException{
        boolean res = false;
        String SQL = "SELECT empleado.* FROM empleado WHERE empleado.user = '"+user+"' AND empleado.pass ='"+pass+"' AND empleado.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        if(rs.first()){
            //no haria falta esto pero.......
            if(user.equals(rs.getString("user")) && pass.equals(String.valueOf(rs.getString("pass")))) {
                
                Empleado  empleadoLoggeado= new Empleado();
                empleadoLoggeado.setId(rs.getInt("id"));
                empleadoLoggeado.setDni(rs.getInt("numero_doc"));
                empleadoLoggeado.setNombre(rs.getString("nombre"));
                empleadoLoggeado.setEstadoCivil(rs.getString("estado_civil"));
                //direccion
                empleadoLoggeado.setNacionalidad(rs.getString("pais.nombre"));
                empleadoLoggeado.setProvincia(rs.getString("provincia.nombre"));
                empleadoLoggeado.setCiudad(rs.getString("localidad.nombre"));
                empleadoLoggeado.setBarrio(rs.getString("barrio.nombre"));
                empleadoLoggeado.setDireccionId(rs.getInt("direccion.id"));
                empleadoLoggeado.setDireccion(rs.getString("direccion.nombre"));
                empleadoLoggeado.setNro(rs.getString("numero_domicilio"));
                empleadoLoggeado.setCodigoPostal(rs.getString("codPostal"));
                empleadoLoggeado.setReferencia(rs.getString("referencia"));
                //datos sociales
                empleadoLoggeado.setCategoria(rs.getString("categoria"));
                empleadoLoggeado.setConvenio(rs.getString("convenio"));
                empleadoLoggeado.setAporteOSocial(rs.getString("aporte_os"));
                empleadoLoggeado.setCuil(rs.getString("cuil"));
                empleadoLoggeado.setTipo(rs.getString("tipo"));
                //contactos
                SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'PROVEEDOR' AND id_persona = "+empleadoLoggeado.getId();
                ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                List<Contacto> contactos = new ArrayList<>();
                try{
                    while(rc.next()){
                        Contacto c = new Contacto();
                        c.setId(rc.getInt("id"));
                        c.setContacto(rc.getString("contacto"));
                        c.setTipo(rc.getString("tipo"));
                        contactos.add(c);
                    }
                }catch(Exception ex){
                    //no hago nada para que no se trabe
                }
                empleadoLoggeado.setContacto((ArrayList<Contacto>) contactos);
                // info de login:
                empleadoLoggeado.setPass(rs.getString("pass"));
                empleadoLoggeado.setUser(rs.getString("user"));
                res = true;
            }
        }
        return res;
    }
    
     public List<Empleado> buscarEmpleado(String valor,String tipo_busqueda) {
       
       //String  tipo_busqueda = "nombre";
        String SQL = "SELECT empleado.*,barrio.nombre,localidad.nombre,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
          + " FROM empleado,direccion,barrio,localidad,provincia,pais"
          + " WHERE empleado."+tipo_busqueda+" like '%"+valor+"%' AND empleado.state = 'ACTIVO'"
          + " AND direccion.id = empleado.direccion_id"
          + " AND barrio.id = direccion.barrio_id"
          + " AND localidad.id = barrio.localidad_id"
          + " AND localidad.provincia_id = provincia.id"
          + " AND pais.id = provincia.pais_id";
       ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
       List<Empleado> list = new ArrayList<>();
       try{
           while(rs.next()){
               //System.out.println("Entre al while con 2 parametros");
               //--CARGAR DATOS AL cliente
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setDni(rs.getInt("numero_doc"));
                e.setTipoDni(rs.getString("tipo_doc"));
                e.setNombre(rs.getString("nombre"));
                e.setEstadoCivil(rs.getString("estado_civil"));
                e.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                //direccion
                e.setNacionalidad(rs.getString("pais.nombre"));
                e.setProvincia(rs.getString("provincia.nombre"));
                e.setCiudad(rs.getString("localidad.nombre"));
                e.setBarrio(rs.getString("barrio.nombre"));
                e.setDireccionId(rs.getInt("direccion.id"));
                e.setDireccion(rs.getString("direccion.nombre"));
                e.setNro(rs.getString("numero_domicilio"));
                e.setCodigoPostal(rs.getString("codPostal"));
                e.setReferencia(rs.getString("referencia"));
                //datos sociales
                e.setCategoria(rs.getString("categoria"));
                e.setConvenio(rs.getString("convenio"));
                e.setAporteOSocial(rs.getString("aporte_os"));
                e.setCuil(rs.getString("cuil"));
                e.setTipo(rs.getString("tipo"));
                //contactos
                SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'EMPLEADO' AND id_persona = "+e.getId();
                ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                List<Contacto> contactos = new ArrayList<>();
                try{
                    while(rc.next()){
                        Contacto c = new Contacto();
                        c.setId(rc.getInt("id"));
                        c.setContacto(rc.getString("contacto"));
                        c.setTipo(rc.getString("tipo"));
                        contactos.add(c);
                    }
                }catch(Exception ex){
                    //no hago nada para que no se trabe
                }
                e.setContacto((ArrayList<Contacto>) contactos);
                // info de login:
                //no necesario para la busqueda
                
                //--FIN CARGA
               list.add(e);
            }
       }catch(Exception ex){
           ex.printStackTrace();
       }

       return list;
           
    }

    public int eliminarEmpleado(Empleado e) {
        String SQL1 = "DELETE FROM contactos"
                + "WHERE id_persona =" +e.getId()+" AND tipo='EMPLEADO'";
        int res1 = conexion.EjecutarOperacion(SQL1);
        
        String SQL = "DELETE FROM empleado "
                + "WHERE id = " + e.getId();
        int res = conexion.EjecutarOperacion(SQL);
        if (res >0 && res1 >0) {
          return res;  
        }
        
        return res;  
    }
    /**
    * @param  usuario A String with the user fiel to check 
    * @return true if the user doesn't exist already
    **/
    public boolean verificarUsaurioOk(String usuario) throws SQLException{
        boolean res=false;
        String SQL = "SELECT empleado.user FROM `empleado` WHERE empleado.user = '"+usuario+"' AND state = 'ACTIVO'";
        ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
        //public boolean first() throws SQLException
        //Moves the cursor to the first row in this ResultSet object.
        //Returns:
        //true if the cursor is on a valid row; false if there are no rows in the result set
        if(!rc.first()){ res= true; }
        
        return res;
    }
    
    public boolean verificarModificacionContraseña(char[] cadenaActual, char[] cadenaNueva1, String usuario) {
        boolean resultado = false;
        if(usuario.equals(empleadoLoggeado.getUser())){
            if(String.valueOf(cadenaActual).equals(String.valueOf(empleadoLoggeado.getPass()))){
                resultado=true;
        }
        
    }
        return resultado;
    }

    public boolean actualizarEmpleado(Empleado e) {
        boolean exito = false;
        conexion.transaccionCommit("quitarAutoCommint");
        int res =1;
        String SQL = "UPDATE empleado SET nombre = '"+e.getNombre()+"'"
                + ", direccion_id= "+ e.getDireccionId()
                + ", numero_domicilio = "+e.getNro()
                + ",codPostal = "+e.getCodigoPostal()
                + ", referencia = '"+e.getReferencia()
                +"', fechaNacimiento = '"+Statics.Funciones.dateParse(e.getFechaNacimiento())
                +"', dni = "+e.getDni()
                +", tipo_doc ='"+e.getTipoDni()
                +"', estado_civil= '"+e.getEstadoCivil()
                +"', categoria = '"+e.getCategoria()
                +"', aporte_os = '"+e.getAporteOSocial()
                +"', convenio = '"+e.getConvenio()
                +"', tipo = '"+e.getTipo()
                +"', cuil = '"+e.getCuil()+"'"
                +" WHERE id = "+e.getId();
        res = conexion.EjecutarOperacion(SQL);
        if(res == 0){
            exito = false;
        }
        else{
            if (e.getContacto().size()>0){
                SQL = " DELETE FROM contactos WHERE persona_id = "+e.getId()+" AND tipo_persona = 'EMPLEADO' AND state = 'ACTIVO'";
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                for(int i = e.getContacto().size()-1 ; i > 0; i--){
                    SQL += "("+e.getId()+",'"+e.getContacto().get(i).getContacto()+"','"+e.getContacto().get(i).getTipo()+"','EMPLEADO'),";
                }
                SQL += "("+e.getId()+",'"+e.getContacto().get(0).getContacto()+"','"+e.getContacto().get(0).getTipo()+"','EMPLEADO')";
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

    public boolean guardarEmpleado(Empleado e) {
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
         String SQL = "INSERT INTO empleado (nombre,numero_doc,tipo_doc,cuil,fechaNacimiento,estado_civil,tipo,codPostal,referencia,categoria,numero_domicilio,direccion_id,convenio, aporte_os, user, pass) "
                + "VALUES('"+e.getNombre()+"',"+e.getDni()+",'"+e.getTipoDni()+"','"+e.getCuil()+"','"+Statics.Funciones.dateParse(e.getFechaNacimiento())+"','"+e.getEstadoCivil()+"','"+e.getTipo()+"','"+e.getCodigoPostal()+"','"+e.getReferencia()+"','"+e.getCategoria()+"',"+
                e.getNro()+","+e.getDireccionId()+",'"+
               e.getConvenio()+"' , '"+e.getAporteOSocial()+"', '"+e.getUser()+"' , '"+e.getPass()+"' )";
        res = conexion.EjecutarOperacion(SQL); //inserto el empleado el cual ahora sera el empleado con id mas alto
        System.out.println("SQL = " + SQL);
        System.out.println("res = "+res);
        if(res == 0){
            System.out.println("RES salio igual a 0");
            exito = false;
        }else{
            if(e.getContacto().size() > 0){
                System.out.println("tengo e.getContacto().size() > 0 true");
                SQL = "SELECT MAX(id) AS id FROM empleado ";
                ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
                System.out.println("En contacto, rs tiene= "+rs);
                try {
                    while(rs.next()){
                        e.setId(rs.getInt("id"));
                    }
                    SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                    for(int i = e.getContacto().size()-1 ; i > 0; i--){
                        SQL += "("+e.getId()+",'"+e.getContacto().get(i).getContacto()+"','"+e.getContacto().get(i).getTipo()+"','EMPLEADO'),";
                    }
                    SQL += "("+e.getId()+",'"+e.getContacto().get(0).getContacto()+"','"+e.getContacto().get(0).getTipo()+"','EMPLEADO')";
                    res = conexion.EjecutarOperacion(SQL);
                    
                    System.out.println("la consulta para el contacto fie:");
                    System.out.println(""+res);
                } catch (SQLException ex) {
                    System.out.println("me tiro la exception");
                    res = 0;
                }
                if(res == 0){
                    exito = false;
                }
            }
        }
        if(exito){
            System.out.println("Tuve exito");
            conexion.transaccionCommit("commitear"); 
            conexion.transaccionCommit("activarCommit"); 
        }else{
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
        }
        return exito;
    }

    public ArrayList<Empleado> getCobradores() {
        ArrayList<Empleado> emple = new ArrayList();
        try {
            String SQL = "SELECT id,nombre FROM empleado WHERE state = 'ACTIVO' AND tipo = 'COBRADOR'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setNombre(rs.getString("nombre"));
                e.setId(rs.getInt("id"));
                emple.add(e);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener cobradores metodo getCobradores", Main.isProduccion);
        }
        return emple;
    }

    public ArrayList<Empleado> getVendedores() {
        ArrayList<Empleado> emple = new ArrayList();
        try {
            String SQL = "SELECT id,nombre FROM empleado WHERE state = 'ACTIVO' AND tipo = 'VENDEDOR'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setNombre(rs.getString("nombre"));
                e.setId(rs.getInt("id"));
                emple.add(e);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener vendedores metodo getvendedores", Main.isProduccion);
        }
        return emple;
    }
}