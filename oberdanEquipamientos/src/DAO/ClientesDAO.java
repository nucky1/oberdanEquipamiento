/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Views.ABMClientesView;
import Models.Cliente;
import Models.Contacto;
import com.mysql.jdbc.Connection;
import java.io.FileNotFoundException;
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
 * @author 
 */
public class ClientesDAO {
    private static ClientesDAO ClientesDao=null;
    private ABMClientesView view;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    public ClientesDAO(){}
    public static ClientesDAO getInstance(){
        if (ClientesDao==null) {
            ClientesDao=new ClientesDAO();
        }
        return ClientesDao;
    }
    public void setVista(ABMClientesView view){
        this.view=view;
    }
    public List <Cliente> cargarClientes(ResultSet rs){
        List<Cliente> list = new ArrayList<>();
        try{
            Cliente c = new Cliente ();
            while(rs.next()){
                // p.setId(rs.getInt("id"));
                //p.setNombre(rs.getString("nombre"));
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDni(rs.getInt("dni"));
                c.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                c.setEsSolicitante(rs.getBoolean("esSolicitante"));
                c.setCodPostal(rs.getString("codPostal"));
                c.setReferencia(rs.getString("referencia"));
                c.setDocumentacion(rs.getString("referencia"));
                c.setNumero(rs.getString("numero"));
                c.setDireccion_id(rs.getInt("direccion_id"));
                list.add(c);
                
                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    public List<Cliente> buscarCliente(String tipo_busqueda, String valor) {
        if(tipo_busqueda.equalsIgnoreCase("nombre")){
            tipo_busqueda = "nombre";
        }
           String SQL = "SELECT cliente.*,barrio.nombre,localidad.nombre,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
              + " FROM cliente,direccion,barrio,localidad,provincia,pais"
              + " WHERE cliente."+tipo_busqueda+" like '%"+valor+"%' AND cliente.state = 'ACTIVO'"
              + " AND direccion.id = cliente.direccion_id"
              + " AND barrio.id = direccion.barrio_id"
              + " AND localidad.id = barrio.localidad_id"
              + " AND localidad.provincia_id = provincia.id"
              + " AND pais.id = provincia.pais_id";
           ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
           System.out.println("La consulta  buscarCliente doble fue: "+SQL);
           List<Cliente> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL CLIENTE
                    Cliente p = new Cliente();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    //direccion
                    p.setNacionalidad(rs.getString("pais.nombre"));
                    p.setProvincia(rs.getString("provincia.nombre"));
                    p.setCiudad(rs.getString("localidad.nombre"));
                    p.setBarrio(rs.getString("barrio.nombre"));
                    p.setDireccion_id(rs.getInt("direccion.id"));
                    p.setDireccion(rs.getString("direccion.nombre"));
                    p.setNumero(rs.getString("numero"));
                    p.setCodPostal(rs.getString("codPostal"));
                    p.setReferencia(rs.getString("referencia"));
                    p.setEsSolicitante(rs.getBoolean("esSolicitante"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE contactos.state = 'ACTIVO' AND tipo_persona = 'CLIENTE' AND id_persona = "+p.getId();
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
                    p.setContacto((ArrayList<Contacto>) contactos);
                    //--FIN CARGA
                   list.add(p);
                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
           return list;
           
    }

    public List<Cliente> buscarCliente(int id) {
       String SQL = "SELECT cliente.*,barrio.nombre,barrio.id,localidad.nombre,provincia.nombre,pais.nombre,direccion.id,direccion.nombre"
              + " FROM cliente,direccion,barrio,localidad,provincia,pais"
              + " WHERE cliente.id = "+id+" AND cliente.state = 'ACTIVO'"
              + " AND direccion.id = cliente.direccion_id"
              + " AND barrio.id = direccion.barrio_id"
              + " AND localidad.id = barrio.localidad_id"
              + " AND localidad.provincia_id = provincia.id"
              + " AND pais.id = provincia.pais_id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        System.out.println("La consulta  buscarCliente simple fue: "+SQL);
        List<Cliente> list = new ArrayList<>();
           try{
               while(rs.next()){
                   //--CARGAR DATOS AL Cliente
                    Cliente p = new Cliente();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("cliente"));
                    //direccion
                    p.setNacionalidad(rs.getString("pais.nombre"));
                    p.setProvincia(rs.getString("provincia.nombre"));
                    p.setCiudad(rs.getString("localidad.nombre"));
                    p.setBarrio(rs.getString("barrio.nombre"));
                    p.setDireccion_id(rs.getInt("direccion.id"));
                    p.setDireccion(rs.getString("direccion.nombre"));
                    p.setNumero(rs.getString("numero"));
                    p.setCodPostal(rs.getString("codPostal"));
                    p.setReferencia(rs.getString("referencia"));
                    //datos extras
                    p.setEsSolicitante(rs.getBoolean("esSolicitante"));
                    //contactos
                    SQL = "SELECT contactos.contacto,contactos.id,contactos.tipo FROM contactos WHERE tipo_persona = 'CLIENTE' AND id_persona = "+p.getId();
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
                        ex.printStackTrace();
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
    public Cliente recuperarConyugue(int idCliente){
        Cliente c = new Cliente();
        String SQL ="SELECT relacion.estadoCivil,cliente.nombre,"
                + "cliente.fechaDeNacimiento,cliente.dni,cliente.tipoDni, "
                + "FROM relacion,cliente WHERE (cliente1_id="+idCliente
                +" OR cliente2_id="+idCliente+")"
                + " AND cliente.id!="+idCliente+
                " AND relacion.state=ACTIVA";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            if(rs.next()){

                c.setNombre(rs.getString("cliente.nombre"));
                c.setDni(rs.getInt("cliente.dni"));
                c.setDocumentacion(rs.getString("cliente.tipoDni"));
                c.setFechaNacimiento(rs.getDate("cliente.fechaDeNacimiento"));
                c.setEstadoCivil(rs.getString("relacion.estadoCivil"));
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return c;
                
    }

    public boolean actualizarCliente(Cliente c) {
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "UPDATE cliente SET nombre = '"+c.getNombre()+"'"
                + ", direccion_id = "+c.getDireccion_id()
                + ",numero = "+c.getNumero()+"'"
                + ",codPostal = '"+c.getCodPostal()+"'"
                + ",referencia = '"+c.getReferencia()+"'"
                + ",esSolicitante = '"+c.isEsSolicitante()+"'"
                + ",fechaNacimiento = '"+c.getFechaNacimiento()+"'"
                + ",observaciones = '"+c.getObservaciones()+",'"
                +"' WHERE id = "+c.getId();
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        if(res == 0){
            exito = false;
        }else{
            if(c.getContacto().size() > 0){
                SQL = " DELETE FROM contactos WHERE persona_id = "+c.getId()+" AND tipo_persona = 'CLIENTE' AND state = 'ACTIVO'";
                res = conexion.EjecutarOperacion(SQL);
                SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                for(int i = c.getContacto().size()-1 ; i > 0; i--){
                    SQL += "("+c.getId()+",'"+c.getContacto().get(i).getContacto()+"','"+c.getContacto().get(i).getTipo()+"','CLIENTE'),";
                }
                SQL += "("+c.getId()+",'"+c.getContacto().get(0).getContacto()+"','"+c.getContacto().get(0).getTipo()+"','CLIENTE')";
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

    public boolean guardarCliente(Cliente c) {
     conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "INSERT INTO cliente (nombre,dni,fechaNacimiento,esSolicitante,codPostal,referencia,documentacion,numero,direccion_id,observaciones) "
                + "VALUES('"+c.getNombre()+"',"+c.getDni()+",'"+Statics.Funciones.dateParse(c.getFechaNacimiento())+"',"+c.isEsSolicitante()+",'"+c.getCodPostal()+"','"+c.getReferencia()+"','"+c.getDocumentacion()+"','"+
                c.getNumero()+"',"+c.getDireccion_id()+",'"+
                c.getObservaciones()+"')";
        res = conexion.EjecutarOperacion(SQL); //inserto el proveedor el cual ahora sera el proveedor con id mas alto
        System.out.println("SQL = " + SQL);
        if(res == 0){
            exito = false;
        }else{
            if(c.getContacto().size() > 0){
                SQL = "SELECT MAX(id) AS id FROM cliente ";
                ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
                try {
                    while(rs.next()){
                        c.setId(rs.getInt("id"));
                    }
                    SQL = "INSERT INTO contactos (id_persona, contacto, tipo,tipo_persona) VALUES";
                    for(int i = c.getContacto().size()-1 ; i > 0; i--){
                        SQL += "("+c.getId()+",'"+c.getContacto().get(i).getContacto()+"','"+c.getContacto().get(i).getTipo()+"','CLIENTE'),";
                    }
                    SQL += "("+c.getId()+",'"+c.getContacto().get(0).getContacto()+"','"+c.getContacto().get(0).getTipo()+"','CLIENTE')";
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

    public int eliminarCliente(Cliente clienteSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public JasperViewer generarReporteV1() throws FileNotFoundException{
        JasperReport reporte = null;
        JasperViewer view =null;
        try{
            Connection con = (Connection) conexion.getConexion();
            
            String path = "src\\Reportes\\report1.jasper";
            //FileInputStream fileInputStream = new FileInputStream(path);
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint;
            jprint = JasperFillManager.fillReport(reporte, null,con);
           
            
            view = new JasperViewer (jprint, false);
            
        }
        catch (JRException ex ){
           ///aca iria lo de tratamiento de errores
        }
        
        return view;
    }
/**
    public int recuperarZona(String barrio) {
      String SQL = "SELECT zona FROM barrio WHERE nombre ="+barrio;
      ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
    }*/
}
