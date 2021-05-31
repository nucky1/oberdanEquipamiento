/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Contacto;
import Models.CuentaBanco;
import Models.Proveedor;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
        
    public List<Proveedor> buscarProveedorReducido(String tipo_busqueda, String valor) {
        if(tipo_busqueda.equalsIgnoreCase("nombre"))
            tipo_busqueda = "proveedor";
        String SQL = "SELECT proveedores.id, proveedores.proveedor"
                + " FROM proveedores"
                + " WHERE proveedores."+tipo_busqueda+" like '%"+valor+"%' AND proveedores.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Proveedor> list = new ArrayList<>();
        try{
           while(rs.next()){
               //--CARGAR DATOS AL PROVEEDOR
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("proveedor"));
                list.add(p);
           }
        }catch(Exception ex){
           new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
       }
        return list;
    }
    public List<Proveedor> buscarProveedor(String tipo_busqueda, String valor) {
        if(tipo_busqueda.equalsIgnoreCase("nombre"))
            tipo_busqueda = "proveedor";
        String SQL = "SELECT proveedores.*,barrio.nombre,localidad.nombre,localidad.codPostal,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
          + " FROM proveedores,direccion,barrio,localidad,provincia,pais"
          + " WHERE proveedores."+tipo_busqueda+" like '%"+valor+"%' AND proveedores.state = 'ACTIVO'"
          + " AND direccion.id = proveedores.direccion_id"
          + " AND barrio.id = direccion.barrio_id"
          + " AND localidad.id = barrio.localidad_id"
          + " AND localidad.provincia_id = provincia.id"
          + " AND pais.id = provincia.pais_id";
       ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
       List<Proveedor> list = new ArrayList<>();
       try{
           while(rs.next()){
               System.out.println("Entre al while con 2 parametros");
               //--CARGAR DATOS AL PROVEEDOR
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("proveedor"));
                //direccion
                p.setPais(rs.getString("pais.nombre"));
                p.setProvincia(rs.getString("provincia.nombre"));
                p.setCiudad(rs.getString("localidad.nombre"));
                p.setBarrio(rs.getString("barrio.nombre"));
                p.setdireccionId(rs.getInt("direccion.id"));
                p.setDireccion(rs.getString("direccion.nombre"));
                p.setNro(rs.getString("numero"));
                p.setCodigoPostal(rs.getString("localidad.codPostal"));
                p.setReferencia(rs.getString("referencia"));
                //datos bancarios
                p.setIva(rs.getString("iva"));
                p.setCuit(rs.getString("cuit"));
                p.setSaldo(rs.getFloat("saldo"));
                p.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                SQL = "SELECT * FROM bancoProveedor WHERE proveedor_id = "+p.getId();
                ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                ArrayList<CuentaBanco> cuentas = new ArrayList<>();
                try{
                    while(rc.next()){
                        CuentaBanco c = new CuentaBanco();
                        c.setAlias(rc.getString("alias"));
                        c.setTipo_cuenta(rc.getString("tipo_cuenta"));
                        c.setNro_cuenta(rc.getString("nro_cuenta"));
                        c.setCbu(rc.getString("cbu"));
                        c.setBanco(rc.getString("banco"));
                        cuentas.add(c);
                    }
                }catch(Exception ex){
                    new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
                }
                p.setCuentas(cuentas);
                //contactos
                SQL = "SELECT * FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'PROVEEDOR' AND id_persona = "+p.getId();
                rc = conexion.EjecutarConsultaSQL(SQL);
                List<Contacto> contactos = new ArrayList<>();
                try{
                    while(rc.next()){
                        Contacto c = new Contacto();
                        c.setId(rc.getInt("id"));
                        c.setContacto(rc.getString("contacto"));
                        c.setTipo(rc.getString("tipo"));
                        c.setNombre(rc.getString("nombre"));
                        c.setCargo(rc.getString("cargo"));
                        contactos.add(c);
                    }
                }catch(Exception ex){
                    new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
                }
                p.setContacto((ArrayList<Contacto>) contactos);
                //--FIN CARGA
               list.add(p);
            }
       }catch(Exception ex){
           new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
       }

       return list;
           
    }
    
    public List<Proveedor> buscarProveedor(int id) {
      String SQL = "SELECT proveedores.*,barrio.nombre,barrio.id,localidad.nombre,localidad.codPostal,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
              + " FROM proveedores,direccion,barrio,localidad,provincia,pais"
              + " WHERE proveedores.id = "+id+" AND proveedores.state = 'ACTIVO'"
              + " AND direccion.id = proveedores.direccion_id"
              + " AND barrio.id = direccion.barrio_id"
              + " AND localidad.id = barrio.localidad_id"
              + " AND localidad.provincia_id = provincia.id"
              + " AND pais.id = provincia.pais_id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Proveedor> list = new ArrayList<>();
           try{
               while(rs.next()){
                   System.out.println("Entre al while con 1 parametro");
                   //--CARGAR DATOS AL PROVEEDOR
                    Proveedor p = new Proveedor();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("proveedor"));
                    //direccion
                    p.setPais(rs.getString("pais.nombre"));
                    p.setProvincia(rs.getString("provincia.nombre"));
                    p.setCiudad(rs.getString("localidad.nombre"));
                    p.setBarrio(rs.getString("barrio.nombre"));
                    p.setdireccionId(rs.getInt("direccion.id"));
                    p.setDireccion(rs.getString("direccion.nombre"));
                    p.setNro(rs.getString("numero"));
                    p.setCodigoPostal(rs.getString("localidad.codPostal"));
                    p.setReferencia(rs.getString("referencia"));
                    //datos bancarios
                    p.setIva(rs.getString("iva"));
                    p.setCuit(rs.getString("cuit"));
                    p.setSaldo(rs.getFloat("saldo"));
                    p.setIngresoBruto(rs.getFloat("ingreso_bruto"));
                    SQL = "SELECT * FROM bancoProveedor WHERE proveedor_id = "+p.getId();
                    ResultSet rc = conexion.EjecutarConsultaSQL(SQL);
                    ArrayList<CuentaBanco> cuentas = new ArrayList<>();
                    try{
                        while(rc.next()){
                            CuentaBanco c = new CuentaBanco();
                            c.setAlias(rc.getString("alias"));
                            c.setTipo_cuenta(rc.getString("tipo_cuenta"));
                            c.setNro_cuenta(rc.getString("nro_cuenta"));
                            c.setCbu(rc.getString("cbu"));
                            c.setBanco(rc.getString("banco"));
                            cuentas.add(c);
                        }
                    }catch(Exception ex){
                        new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
                    }
                    p.setCuentas(cuentas);
                    //contactos
                    SQL = "SELECT * FROM contactos WHERE tipo_persona = 'PROVEEDOR' AND id_persona = "+p.getId();
                    rc = conexion.EjecutarConsultaSQL(SQL);
                    List<Contacto> contactos = new ArrayList<>();
                    try{
                        while(rc.next()){
                            Contacto c = new Contacto();
                            c.setId(rc.getInt("id"));
                            c.setContacto(rc.getString("contacto"));
                            c.setTipo(rc.getString("tipo"));
                            c.setNombre(rc.getString("nombre"));
                            c.setCargo(rc.getString("cargo"));
                            contactos.add(c);
                        }
                    }catch(Exception ex){
                        new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
                        //no hago nada para que no se trabe
                    }
                    p.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(p);
                }
           }catch(Exception ex){
               new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
           }
           
           return list;
    }
    
    public boolean actualizarProveedor(Proveedor p){
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "UPDATE proveedores SET proveedor = '"+p.getNombre()+"'"
                + ", barrio_id = "+p.getdireccionId()
                + ",direccion = '"+p.getDireccion()+"'"
                + ",numero = "+p.getNro()
                + ",referencia = '"+p.getReferencia()+"'"
                + ",iva = '"+p.getIva()+"'"
                + ",cuit = '"+p.getCuit()
                + ",observaciones = '"+p.getObservaciones()+",'"
                + ",saldo = "+p.getSaldo()
                + ",ingreso_bruto = '"+p.getIngresoBruto()+"' WHERE id = "+p.getId();
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        if(res == 0){
            exito = false;
        }else{
            if(p.getContacto().size() > 0){
                SQL = " DELETE FROM contactos WHERE persona_id = "+p.getId()+" AND tipo_persona = 'PROVEEDOR' AND state = 'ACTIVO'";
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO contactos (id_persona, contacto,nombre,cargo, tipo,tipo_persona) VALUES";
                for(int i = p.getContacto().size()-1 ; i > 0; i--){
                    SQL += "("+p.getId()+",'"+p.getContacto().get(i).getContacto()+"','"+p.getContacto().get(i).getNombre()+"','"+p.getContacto().get(i).getCargo()+"','"+p.getContacto().get(i).getTipo()+"','PROVEEDOR'),";
                }
                SQL += "("+p.getId()+",'"+p.getContacto().get(0).getContacto()+"','"+p.getContacto().get(0).getNombre()+"','"+p.getContacto().get(0).getCargo()+"','"+p.getContacto().get(0).getTipo()+"','PROVEEDOR')";
                res = conexion.EjecutarOperacion(SQL);
                if(res == 0){
                    exito = false;
                }
            }
        }
        if(res == 0){
            exito = false;
        }else{
            if(p.getCuentas().size() > 0){
                SQL = " DELETE FROM bancoProveedor WHERE proveedor_id = "+p.getId();
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO bancoProveedor (proveedor_id, nro_cuenta,banco,alias, cbu,tipo_cuenta) VALUES";
                for(int i = p.getCuentas().size()-1 ; i > 0; i--){
                    SQL += "("+p.getId()+",'"+p.getCuentas().get(i).getNro_cuenta()+"','"+p.getCuentas().get(i).getBanco()+"','"+p.getCuentas().get(i).getAlias()+"','"+p.getCuentas().get(i).getCbu()+"','"+p.getCuentas().get(i).getTipo_cuenta()+"'),";
                }
                SQL += "("+p.getId()+",'"+p.getCuentas().get(0).getNro_cuenta()+"','"+p.getCuentas().get(0).getBanco()+"','"+p.getCuentas().get(0).getAlias()+"','"+p.getCuentas().get(0).getCbu()+"','"+p.getCuentas().get(0).getTipo_cuenta()+"')";
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
        String SQL = "INSERT INTO proveedores (proveedor, direccion_id,numero,referencia,iva,cuit,saldo,ingreso_bruto,observaciones) "
                + "VALUES('"+p.getNombre()+"',"+p.getdireccionId()+",'"+p.getNro()+"','"+p.getReferencia()+"','"+
        p.getIva()+"',"+
        p.getCuit()+","+
        p.getSaldo()+",'"+
        p.getIngresoBruto()+"','"
                + p.getObservaciones()+"')";
        System.out.println(SQL);
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        if(res == 0){
            exito = false;
        }else{
            if(p.getContacto().size() > 0){
                SQL = "SELECT MAX(id) AS id FROM proveedores ";
                ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
                try {
                    while(rs.next()){
                        p.setId(rs.getInt("id"));
                    }
                    SQL = "INSERT INTO contactos (id_persona, contacto,nombre,cargo, tipo,tipo_persona) VALUES";
                    for(int i = p.getContacto().size()-1 ; i > 0; i--){
                        SQL += "("+p.getId()+",'"+p.getContacto().get(i).getContacto()+"','"+p.getContacto().get(i).getNombre()+"','"+p.getContacto().get(i).getCargo()+"','"+p.getContacto().get(i).getTipo()+"','PROVEEDOR'),";
                    }
                    SQL += "("+p.getId()+",'"+p.getContacto().get(0).getContacto()+"','"+p.getContacto().get(0).getNombre()+"','"+p.getContacto().get(0).getCargo()+"','"+p.getContacto().get(0).getTipo()+"','PROVEEDOR')";
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
    
    public Proveedor getProveedor(int id) {
       Proveedor p = new Proveedor();
       String SQL = "SELECT proveedores.proveedor, proveedores.id from proveedores "
               + "WHERE id = "+id+" AND state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                p.setId(id);
                p.setNombre(rs.getString("proveedor"));
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return p;
    }
    
    
    public Proveedor getProveedor(String nombre) {
        Proveedor p = new Proveedor();
        String SQL = "SELECT proveedores.proveedor, proveedores.id from proveedores "
                + "WHERE proveedor LIKE '%"+nombre+"%' AND state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("proveedor"));
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return p;
    }
    
    public int eliminarProveedor(Proveedor p) {
        String SQL = "DELETE FROM proveedores "
               + "WHERE id = " + p.getId();
        int res = conexion.EjecutarOperacion(SQL);
        if(res <= 0){
            return res;
        }
        SQL = "DELETE FROM contactos"
                + "WHERE id_persona = "+p.getId()+" AND tipo = 'PROVEEDOR'";
        return res;
    }

    
}
