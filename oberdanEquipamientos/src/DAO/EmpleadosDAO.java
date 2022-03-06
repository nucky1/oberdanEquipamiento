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
    private static EmpleadosDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();

    protected EmpleadosDAO() {

    }

    public static EmpleadosDAO getInstance() {
        if (controller == null) {
            controller = new EmpleadosDAO();
        }
        return controller;
    }

    public boolean login(String user, String pass) {
        boolean res = false;
        String SQL = "SELECT empleado.* FROM empleado WHERE empleado.user = '" + user + "' AND empleado.pass ='" + pass + "' AND empleado.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if (rs.first()) {
                res = true;
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al intentar loggear un empleado", Main.isProduccion);
        }
        return res;
    }

    public Empleado obtenerEmpleado(String user, String pass) {
        String SQL = "SELECT empleado.* FROM empleado WHERE empleado.user = '" + user + "' AND empleado.pass ='" + pass + "' AND empleado.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if (rs.first()) {
                Empleado empleadoLoggeado = new Empleado();
                empleadoLoggeado.setId(rs.getInt("id"));
                empleadoLoggeado.setDni(rs.getInt("numero_doc"));
                empleadoLoggeado.setNombre(rs.getString("nombre"));
                empleadoLoggeado.setEstadoCivil(rs.getString("estado_civil"));
                //datos sociales
                empleadoLoggeado.setCategoria(rs.getString("categoria"));
                empleadoLoggeado.setConvenio(rs.getString("convenio"));
                empleadoLoggeado.setAporteOSocial(rs.getString("aporte_os"));
                empleadoLoggeado.setCuil(rs.getString("cuil"));
                empleadoLoggeado.setTipo(rs.getString("tipo"));
                //contactos
                SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'EMPLEADO' AND id_persona = " + empleadoLoggeado.getId();
                ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                List<Contacto> contactos = new ArrayList<>();
                try {
                    while (rc.next()) {
                        Contacto c = new Contacto();
                        c.setId(rc.getInt("id"));
                        c.setContacto(rc.getString("contacto"));
                        c.setTipo(rc.getString("tipo"));
                        contactos.add(c);
                    }
                } catch (Exception ex) {
                    //no hago nada para que no se trabe
                }
                empleadoLoggeado.setContacto((ArrayList<Contacto>) contactos);
                // info de login:
                empleadoLoggeado.setPass(rs.getString("pass"));
                empleadoLoggeado.setUser(rs.getString("user"));
                return empleadoLoggeado;
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al intentar obtener un empleado en login", Main.isProduccion);
        }
        return null;
    }

    public List<Empleado> buscarEmpleado(String valor, String tipo_busqueda) {

        //String  tipo_busqueda = "nombre";
        String SQL = "SELECT empleado.*,barrio.nombre,localidad.nombre,localidad.codPostal,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
                + " FROM empleado,direccion,barrio,localidad,provincia,pais"
                + " WHERE empleado." + tipo_busqueda + " like '%" + valor + "%' AND empleado.state = 'ACTIVO'"
                + " AND direccion.id = empleado.direccion_id"
                + " AND barrio.id = direccion.barrio_id"
                + " AND localidad.id = barrio.localidad_id"
                + " AND localidad.provincia_id = provincia.id"
                + " AND pais.id = provincia.pais_id";
        System.out.println("EN empleadosdao, en buscarEmpleado la consulta fue: /n" + SQL);
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Empleado> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Empleado e = this.cargarDatosEmpleado(rs);
                e= this.cargarDireccionEmpleado(e,rs);
                if (e != null) {
                    list.add(e);
                }
            }
        } catch (Exception ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }

        return list;

    }

    private Empleado cargarDatosEmpleado(ResultSet rs) throws SQLException {
        Empleado e = null;
        try {
            //--CARGAR DATOS AL empleado
            e = new Empleado();
            e.setId(rs.getInt("id"));
            e.setDni(rs.getInt("numero_doc"));
            e.setTipoDni(rs.getString("tipo_doc"));
            e.setNombre(rs.getString("nombre"));
            e.setEstadoCivil(rs.getString("estado_civil"));
            e.setFechaNacimiento(rs.getDate("fechaNacimiento"));
            
            //datos sociales
            e.setCategoria(rs.getString("categoria"));
            e.setConvenio(rs.getString("convenio"));
            e.setAporteOSocial(rs.getString("aporte_os"));
            e.setCuil(rs.getString("cuil"));
            e.setTipo(rs.getString("tipo"));
            //contactos
            String SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'EMPLEADO' AND id_persona = " + e.getId();
            ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
            List<Contacto> contactos = new ArrayList<>();
            try {
                while (rc.next()) {
                    Contacto c = new Contacto();
                    c.setId(rc.getInt("id"));
                    c.setContacto(rc.getString("contacto"));
                    c.setTipo(rc.getString("tipo"));
                    contactos.add(c);
                }
            } catch (Exception ex) {
                new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
            }
            e.setContacto((ArrayList<Contacto>) contactos);

        } catch (Exception ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return e;
    }

    public int eliminarEmpleado(Empleado e) {
        String SQL1 = "DELETE FROM contactos"
                + "WHERE id_persona =" + e.getId() + " AND tipo='EMPLEADO'";
        int res1 = conexion.EjecutarOperacion(SQL1);

        String SQL = "DELETE FROM empleado "
                + "WHERE id = " + e.getId();
        int res = conexion.EjecutarOperacion(SQL);
        if (res > 0 && res1 > 0) {
            return res;
        }

        return res;
    }

    /**
     * @param usuario A String with the user fiel to check
     * @return true if the user doesn't exist already
    *
     */
    public boolean verificarUsaurioOk(String usuario) throws SQLException {
        boolean res = false;
        String SQL = "SELECT empleado.user FROM `empleado` WHERE empleado.user = '" + usuario + "' AND state = 'ACTIVO'";
        ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
        //public boolean first() throws SQLException
        //Moves the cursor to the first row in this ResultSet object.
        //Returns:
        //true if the cursor is on a valid row; false if there are no rows in the result set
        if (!rc.first()) {
            res = true;
        }

        return res;
    }

    public boolean verificarModificacionContraseña(char[] cadenaActual, char[] cadenaNueva1, String usuario) {
        boolean resultado = false;
        if (usuario.equals(empleadoLoggeado.getUser())) {
            if (String.valueOf(cadenaActual).equals(String.valueOf(empleadoLoggeado.getPass()))) {
                resultado = true;
            }

        }
        return resultado;
    }

    public boolean actualizarEmpleado(Empleado e) {
        boolean exito = true;
        conexion.transaccionCommit("quitarAutoCommint");
        int res = 1;
        String SQL = "UPDATE empleado SET nombre = '" + e.getNombre() + "'"
                + ", direccion_id= " + e.getDireccionId()
                + ", numero_domicilio = " + e.getNro()
                + ", referencia = '" + e.getReferencia()
                + "', fechaNacimiento = '" + Statics.Funciones.dateParse(e.getFechaNacimiento())
                + "', numero_doc = " + e.getDni()
                + ", tipo_doc ='" + e.getTipoDni()
                + "', estado_civil= '" + e.getEstadoCivil()
                + "', categoria = '" + e.getCategoria()
                + "', aporte_os = '" + e.getAporteOSocial()
                + "', convenio = '" + e.getConvenio()
                + "', tipo = '" + e.getTipo()
                + "', cuil = '" + e.getCuil() + "'"
                + " WHERE id = " + e.getId();
        res = conexion.EjecutarOperacion(SQL);
        if (res == 0) {
            exito = false;
            System.out.println("NO pude actualizar el empleado");
        } else {
            if (e.getContacto().size() > 0) {
                SQL = " DELETE FROM contactos WHERE id_persona = " + e.getId() + " AND tipo_persona = 'EMPLEADO' AND state = 'ACTIVO'";
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                for (int i = e.getContacto().size() - 1; i > 0; i--) {
                    SQL += "(" + e.getId() + ",'" + e.getContacto().get(i).getContacto() + "','" + e.getContacto().get(i).getTipo() + "','EMPLEADO'),";
                }
                SQL += "(" + e.getId() + ",'" + e.getContacto().get(0).getContacto() + "','" + e.getContacto().get(0).getTipo() + "','EMPLEADO')";
                res = conexion.EjecutarOperacion(SQL);
                if (res == 0) {
                    exito = false;
                }
            }
        }
        if (exito) {
            conexion.transaccionCommit("commitear");
            conexion.transaccionCommit("activarCommit");
        } else {
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
        }
        return exito;
    }

    public boolean guardarEmpleado(Empleado e) {
        conexion.transaccionCommit("quitarAutoCommit");
        int res = 1;
        boolean exito = true;
        String SQL = "SELECT * FROM empleado WHERE numero_doc=" + e.getDni() + " AND state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if (rs.first()) {
                return false;
            } else {
                SQL = "INSERT INTO empleado (nombre,numero_doc,tipo_doc,cuil,fechaNacimiento,estado_civil,tipo,referencia,categoria,numero_domicilio,direccion_id,convenio, aporte_os, user, pass) "
                        + "VALUES('" + e.getNombre() + "'," + e.getDni() + ",'" + e.getTipoDni() + "','" + e.getCuil() + "','" + Statics.Funciones.dateParse(e.getFechaNacimiento()) + "','" + e.getEstadoCivil() + "','" + e.getTipo() + "','" + e.getReferencia() + "','" + e.getCategoria() + "',"
                        + e.getNro() + "," + e.getDireccionId() + ",'"
                        + e.getConvenio() + "' , '" + e.getAporteOSocial() + "', '" + e.getUser() + "' , '" + e.getPass() + "' )";
                res = conexion.EjecutarOperacion(SQL); //inserto el empleado el cual ahora sera el empleado con id mas alto
                System.out.println("SQL = " + SQL);
                System.out.println("res = " + res);
                if (res == 0) {
                    System.out.println("RES salio igual a 0");
                    exito = false;
                } else {
                    if (e.getContacto().size() > 0) {
                        System.out.println("tengo e.getContacto().size() > 0 true");
                        SQL = "SELECT MAX(id) AS id FROM empleado ";
                        rs = conexion.EjecutarConsultaSQL(SQL);
                        System.out.println("En contacto, rs tiene= " + rs);
                        try {
                            while (rs.next()) {
                                e.setId(rs.getInt("id"));
                            }
                            //INSERT INTO `contactos`(`id_persona`, `contacto`, `tipo`, `tipo_persona`) VALUES (2 ,142131 ,"TELEFONO" , "EMPLEADO");
                            SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                            for (int i = e.getContacto().size() - 1; i > 0; i--) {
                                SQL += "(" + e.getId() + ",'" + e.getContacto().get(i).getContacto() + "','" + e.getContacto().get(i).getTipo() + "','EMPLEADO'),";
                            }
                            SQL += " (" + e.getId() + ",'" + e.getContacto().get(0).getContacto() + "','" + e.getContacto().get(0).getTipo() + "','EMPLEADO')";
                            res = conexion.EjecutarOperacion(SQL);

                            System.out.println("INSERTS PARA  contacto EMPLEADO fie:");
                            System.out.println("" + res);
                        } catch (SQLException ex) {
                            System.out.println("me tiro la exception");
                            res = 0;
                        }
                        if (res == 0) {
                            exito = false;
                        }
                    }
                }
                if (exito) {
                    System.out.println("Tuve exito");
                    conexion.transaccionCommit("commitear");
                    conexion.transaccionCommit("activarCommit");
                } else {
                    conexion.transaccionCommit("rollBack");
                    conexion.transaccionCommit("activarCommit");
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        ArrayList<Empleado> empleados = new ArrayList();
        try {
            String SQL = "SELECT * FROM empleado WHERE state = 'ACTIVO' AND tipo = 'VENDEDOR'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Empleado e = new Empleado();
                e=this.cargarDatosEmpleado(rs);
                empleados.add(e);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener vendedores metodo getvendedores", Main.isProduccion);
        }
        return empleados;
    }

    public Empleado getVendedorActivoByClienteId(int clienteId) {
        Empleado e = null;
        try {
            String SQL = "SELECT vendedor_id FROM cliente_vendedor WHERE cliente_id = " + clienteId + " AND state = 'ACTIVO' ";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if (rs.first()) {
                e = new Empleado();
                int empleado_id = rs.getInt("vendedor_id");
                e=this.getEmpleadoById(empleado_id);
                

            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener vendedores metodo getVendedorActivoByClienteId", Main.isProduccion);
        }
        return e;
    }
    
    public boolean crearRelacionClienteVendedor(int cliente_id, int vendedor_id){
        try {
            String SQL = "SELECT `id` FROM `cliente_vendedor` WHERE state= 'INACTIVO' AND  cliente_id= "+ cliente_id +" AND vendedor_id= "+vendedor_id;
            ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
            if(rs.first()){
                //existe ya una relacion, la reactivo
                SQL = "UPDATE `cliente_vendedor` SET `state`='ACTIVO' WHERE cliente_id= "+ cliente_id +" AND vendedor_id= "+vendedor_id;
                
            }
            else{
                //no existe relacion, creo nueva
                SQL = "INSERT INTO `cliente_vendedor`(`cliente_id`, `vendedor_id`) VALUES ("+cliente_id+", "+vendedor_id+")";
            }
            
            if(conexion.EjecutarOperacion(SQL)>0) return true;
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
    
    public boolean borrarRelacionClienteVendedor(int cliente_id, int vendedor_id){
        String SQL = "UPDATE `cliente_vendedor` SET `state`='INACTIVO' WHERE cliente_id= "+ cliente_id +" AND vendedor_id= "+vendedor_id;
        if(conexion.EjecutarOperacion(SQL)>0) return true;
        return false;
    }
    
    public Empleado getVendedorById(int id) {
        Empleado empleado = null;
        try {
            String SQL = "SELECT id FROM empleado WHERE state = 'ACTIVO' AND tipo = 'VENDEDOR'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if (rs.first()) {
                empleado=this.getEmpleadoById(id);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener vendedores metodo getEmpleadoMinById", Main.isProduccion);
        }
        return empleado;
    }
      
    public Empleado getEmpleadoById(int id) {
        Empleado empleado = null;
        try {
             String SQL = "SELECT empleado.*,barrio.nombre,localidad.nombre,localidad.codPostal,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
                + " FROM empleado,direccion,barrio,localidad,provincia,pais"
                + " WHERE empleado.id= " +id+ " AND empleado.state = 'ACTIVO'"
                + " AND direccion.id = empleado.direccion_id"
                + " AND barrio.id = direccion.barrio_id"
                + " AND localidad.id = barrio.localidad_id"
                + " AND localidad.provincia_id = provincia.id"
                + " AND pais.id = provincia.pais_id";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            System.out.println("la consulta fue: "+SQL);
            if (rs.first()) {
                empleado=this.cargarDatosEmpleado(rs);
                empleado=this.cargarDireccionEmpleado(empleado, rs);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener vendedores metodo getEmpleadoById", Main.isProduccion);
        }
        return empleado;
    }

    private Empleado cargarDireccionEmpleado(Empleado e, ResultSet rs) throws SQLException {

            e.setNacionalidad(rs.getString("pais.nombre"));
            e.setProvincia(rs.getString("provincia.nombre"));
            e.setCiudad(rs.getString("localidad.nombre"));
            e.setBarrio(rs.getString("barrio.nombre"));
            e.setDireccionId(rs.getInt("direccion.id"));
            e.setDireccion(rs.getString("direccion.nombre"));
            e.setNro(rs.getString("numero_domicilio"));
            e.setCodigoPostal(rs.getString("localidad.codPostal"));
            e.setReferencia(rs.getString("referencia"));
            return e;
    }
}
