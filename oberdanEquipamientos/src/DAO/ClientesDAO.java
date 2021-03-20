 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Views.ABMClientesView;
import Models.Cliente;
import Models.Contacto;
import Models.Rubro;
import Views.Main;
import com.mysql.jdbc.Connection;
import com.sun.org.glassfish.gmbal.ParameterNames;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    /**
     * @param rs
     * This method is incomplete and doesn't work properly
    */
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
                    p.setDni(rs.getInt("dni"));
                    p.setTipoDni(rs.getString("tipo_dni"));
                    p.setEstadoCivil(rs.getString("estadoCivil"));
                    p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
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
                    p.setDocumentacion(rs.getString("documentacion"));
                    p.setEsSolicitante(rs.getBoolean("esSolicitante"));
                    p.setObservaciones(rs.getString("observaciones"));
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

    public Cliente buscarCliente(int id) {
       Cliente p = new Cliente();
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
        
           try{
               if(rs.first()){
                     //--CARGAR DATOS AL Cliente
                    
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    p.setDni(rs.getInt("dni"));
                    p.setTipoDni(rs.getString("tipo_dni"));
                    p.setEstadoCivil(rs.getString("estadoCivil"));
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
                    p.setDocumentacion(rs.getString("documentacion"));
                    p.setEsSolicitante(rs.getBoolean("esSolicitante"));
                    p.setObservaciones(rs.getString("observaciones"));
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
                    return p;

                }
           }catch(Exception ex){
               ex.printStackTrace();
           }
           

            return null;

    }
    public boolean insertarClientes(Cliente c1, Cliente c2, String tipo){
      
        
        int res = 1;
        boolean exito = true;
        //primero doy alta al c1 (solicitante)
        if(guardarCliente(c1)){
            //si el solicitante se pudo dar de alta  (supuestamente es uno nuevo, si o si debe ser nuevo!)
           
            exito=this.casar(c1, c2, tipo);
            System.out.println("En casar, exito tiene "+exito);
        } 
        else{
            exito=false;
            System.out.println("En insertar clientes, no pude guardar c1");
        }
        return exito;
    }
    public boolean actualizarClientes(Cliente c1, Cliente c2, String tipo) {
        int idCliente2 = -1;
        int res = 1;
        int res2= 1;
        boolean exito = true;
        String SQL;
        exito=this.actualizarCliente(c1);
                
        //que pasa si  el cliente estaba casado y pasa a divorciarse..
        if(c1.getEstadoCivil().equalsIgnoreCase("DIVORCIADO")){
            //estoy en modificar, asi que si lo voy a divorciar, el c2 ya existia
            //lo recupero completo
            SQL= "SELECT * FROM cliente WHERE dni= "+c2.getDni(); 
            ResultSet rs= conexion.EjecutarConsultaSQL(SQL);

            try{
                if(rs.first()){
                    idCliente2=rs.getInt("id");
                    SQL= "SELECT * FROM relacion WHERE cliente1_id= "+c1.getId()+
                            "OR cliente2_id= "+c1.getId()+" AND state = 'ACTIVO'";
                    rs = conexion.EjecutarConsultaSQL(SQL);
                    if(rs.first()){
                        // a divorciar() lo debo llamar si o si ordenado!
                        if(c1.getId()== rs.getInt("cliente1_id") && idCliente2 == rs.getInt("cliente2_id")){
                            exito= this.divorciar(c1,buscarCliente(idCliente2));
                        }
                        if(c1.getId()== rs.getInt("cliente2_id") && idCliente2 == rs.getInt("cliente1_id")){
                            exito= this.divorciar(buscarCliente(idCliente2),c1);
                        }
                        
                    }
                    
                  

            }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }
        //el cliente se casa
        if(c1.getEstadoCivil().equalsIgnoreCase("CASADO")){
            // asumo tres casos. Estaba casado con X, se sapara y se casa con Y
            // el otro caso es que estaba soltero y pasa a estar casado
            // esa persona con quien se casa, puede o no existir (2 y 3 caso)
            //Analizo si el conyugue  a agregar existe o no
            SQL = "SELECT * FROM cliente WHERE dni="+c2.getDni();
            ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
            try {
                if(rs.first()){
                    //El cliente existe
                    exito= this.casar(c1,buscarCliente(rs.getInt("id")),tipo);
                }
                else{
                   //el cliente no existe
                   c2.setEstadoCivil("CASADO");
                   c2.setDireccion_id(c1.getDireccion_id());
                   c2.setCodPostal(c1.getCodPostal());
                   c2.setNumero(c1.getNumero());
                   c2.setObservaciones("Es un conyugue");
                   exito = this.guardarCliente(c2);
                   exito= this.casar(c1, c2, tipo);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return exito;
    }
    

    public List recuperarConyugues(int idCliente){
        String SQL ="SELECT relacion.estadoCivil,cliente.nombre,"
                + "cliente.fechaDeNacimiento,cliente.dni,cliente.tipoDni, "
                + "FROM relacion"
                + "INNER JOIN cliente ON cliente1_id = cliente.id OR cliente2_id = cliente.id"
                + " WHERE (cliente1_id="+idCliente
                +" OR cliente2_id="+idCliente+")"
                + " AND cliente.id!="+idCliente+
                " AND relacion.state= 'ACTIVO' "
                + " ORDER BY relacion.created_at DESC";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        System.out.println("La consulta en recuperar Conyugue fue: ");
        System.out.println(""+SQL);
        List<Cliente> list = new ArrayList<>();
        try{
            while(rs.next()){
                Cliente c = new Cliente();
                c.setNombre(rs.getString("cliente.nombre"));
                c.setDni(rs.getInt("cliente.dni"));
                c.setDocumentacion(rs.getString("cliente.tipoDni"));
                c.setFechaNacimiento(rs.getDate("cliente.fechaDeNacimiento"));
                //una pequeña trampa, uso el campo estado civil para guardar el tipo de relacion
                c.setEstadoCivil(rs.getString("relacion.tipo"));
                list.add(c);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
                
    }

    public Cliente recuperarConyugue(int idCliente){
        String SQL ="SELECT relacion.estadoCivil,cliente.nombre,"
                + "cliente.fechaNacimiento,cliente.dni,cliente.tipo_dni "
                + "FROM relacion "
                + "INNER JOIN cliente ON cliente1_id = cliente.id OR cliente2_id = cliente.id"
                + " WHERE (cliente1_id="+idCliente
                +" OR cliente2_id="+idCliente+")"
                + " AND cliente.id!="+idCliente+
                " AND relacion.state= 'ACTIVO' "
                + " ORDER BY relacion.created_at DESC";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        System.out.println("La consulta en recuperar Conyugue fue: ");
        System.out.println(""+SQL);
        Cliente c = new Cliente();
        try{
            if(rs.first()){
                c.setNombre(rs.getString("cliente.nombre"));
                c.setDni(rs.getInt("cliente.dni"));
                c.setDocumentacion(rs.getString("cliente.tipo_dni"));
                c.setFechaNacimiento(rs.getDate("cliente.fechaNacimiento"));
                //una pequeña trampa, uso el campo estado civil para guardar el tipo de relacion
                c.setEstadoCivil(rs.getString("relacion.tipo"));
                return c;
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
                
    }
    
    public boolean actualizarCliente(Cliente c) {
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "UPDATE cliente SET nombre = '"+c.getNombre()+"'"
                + ", direccion_id = "+c.getDireccion_id()
                + ",numero = "+c.getNumero()+""
                + ",codPostal = '"+c.getCodPostal()+"'"
                + ",referencia = '"+c.getReferencia()+"'"
                + ",esSolicitante = "+c.isEsSolicitante()+""
                + ",fechaNacimiento = '"+Statics.Funciones.dateParse(c.getFechaNacimiento())+"'"
                + ",observaciones = '"+c.getObservaciones()+"',"
                + "documentacion = '"+c.getDocumentacion()+"'"
                + ",estadoCivil  = '"+ c.getEstadoCivil()+"'"
                +", tipo_dni = '"+c.getTipoDni()+"'"
                +" WHERE id = "+c.getId();
        res = conexion.EjecutarOperacion(SQL); //inserto el cliente el cual ahora sera el cliente con id mas alto
        System.out.println("En Actualizar cliente, SQL tiene: ");
        System.out.println(""+SQL);
        if(res == 0){
            exito = false;
        }else{
            if(c.getContacto()!=null){
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
                
                //que pasa si  el cliente estaba casado y pasa a divorciarse..
                if(c.getEstadoCivil().equalsIgnoreCase("DIVORCIADO")){
                    //busco la relacion que ya tenia, 
                }
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
    public boolean pruebas(Cliente c){
        boolean exito = true;
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        
        String SQL = "SELECT * FROM cliente WHERE dni= "+c.getDni();
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        
        return exito;
    }
    public boolean guardarCliente(Cliente c) {
        conexion.transaccionCommit("quitarAutoCommit"); 
        int res = 1;
        boolean exito = true;
        String SQL = "SELECT * FROM cliente WHERE dni= "+c.getDni();
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if(rs.first()){
               System.out.println("Lo encontre al cliente y no lo voy a guardar!");
                exito = false;
                
                
                 
            }
            else{
                System.out.println("NO Lo encontre al cliente y lo voy a guardar!");
                SQL = "INSERT INTO cliente (nombre,dni,tipo_dni,estadoCivil,fechaNacimiento,esSolicitante,codPostal,referencia,documentacion,numero,direccion_id,observaciones) "
                        + "VALUES('"+c.getNombre()+"',"+c.getDni()+",'"+c.getTipoDni()+"','"+c.getEstadoCivil()+"','"+Statics.Funciones.dateParse(c.getFechaNacimiento())+"',"+c.isEsSolicitante()+",'"+c.getCodPostal()+"','"+c.getReferencia()+"','"+c.getDocumentacion()+"',"+
                        c.getNumero()+","+c.getDireccion_id()+",'"+
                        c.getObservaciones()+"')";
                res = conexion.EjecutarOperacion(SQL); //inserto el cliente el cual ahora sera el cliente con id mas alto
                System.out.println("SQL = " + SQL);
                if(res == 0){
                    exito = false;
                }else{
                    if(c.getContacto()!=null){
                        if(c.getContacto().size() > 0){
                            System.out.println("Estoy entrando a guardar los contactos");
                        SQL = "SELECT MAX(id) AS id FROM cliente ";
                        rs = conexion.EjecutarConsultaSQL(SQL);
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
                }
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public int eliminarCliente(Cliente c) {
        String SQL1 = "DELETE FROM contactos"
                + "WHERE id_persona =" +c.getId()+" AND tipo='CLIENTE'";
        int res1 = conexion.EjecutarOperacion(SQL1);
        
        String SQL = "DELETE FROM cliente "
                + "WHERE id = " + c.getId();
        int res = conexion.EjecutarOperacion(SQL);
        if (res >0 && res1 >0) {
          return res;  
        }
        
        return res;
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
     * 
     * @param c1 no lo guarda
     * @param c2 lo controla y lo guarda si fuera necesario
     * @param tipo de relacion (Solicitante/conyugue, socio, etc)
     * @return 
     */
    public boolean casar(Cliente c1, Cliente c2, String tipo){
        int res=-1;
        boolean exito = true;
        //no voy a controlar en que estado viene c1, 
        //ya asumido como que estaba cargado
        List <Cliente> list = buscarCliente("dni", String.valueOf(c2.getDni()));
        if(!list.isEmpty()){
            Cliente c = new Cliente();
            c=list.get(0);
            if(c.getEstadoCivil().equalsIgnoreCase("CASADO")){
                
                //c2 ya existia y encima ya estaba casado
                //tengo que buscar su ultima relacion, divorciarlo
                //Insertar una nueva relacion y casado
                String SQL = "SELECT * FROM `relacion` WHERE "
                        + "(cliente1_id = "+c.getId()+" OR cliente2_id ="+c.getId()+") "
                        + "AND estadoCivil= 'CASADO'";
                ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
                try {
                    if(rs.first()){
                        //esto es para buscar la relacion justita
                        int cliente2_id=-1;
                        int cliente1_id= rs.getInt("cliente1_id");
                        if(c.getId() == cliente1_id){
                            cliente2_id = rs.getInt("cliente2_id");
                            
                        }
                        else{
                            cliente1_id= rs.getInt("cliente2_id");
                            cliente2_id = c.getId();
                        }
                        this.divorciar(this.buscarCliente(cliente1_id),this.buscarCliente(cliente2_id));
                        // el metodo divorciar por defecto modifica los estadoC, por eso lo vuelvo a cargar
                        c.setEstadoCivil("CASADO");
                        exito=this.actualizarCliente(c);
                    }
                    // ahora ya el c2 divorciado, lo puedo casar haciendo una nueva relacion
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                       
                
            }
            else{
                //c2 existia, pero no estaba casado:
                //en c busque a c2 y lo traje completo, o con los campos que ya tenia
                //le hago un update cambiando su estadocivil
                c.setEstadoCivil("CASADO");
                exito=this.actualizarCliente(c);
                //listo para hacer la nueva relacionn
            }
            conexion.transaccionCommit("quitarAutoCommit");
            String SQL= "INSERT INTO relacion (cliente1_id, cliente2_id, estadoCivil, tipo) VALUES ("+
                            +c1.getId()+","+c.getId()+","+"'CASADO' , '"+tipo+"')";
                    
                    res= conexion.EjecutarOperacion(SQL);
                    if(res==0) {
                        exito= false;
                        conexion.transaccionCommit("rollBack");
                        conexion.transaccionCommit("activarCommit");
                    }else{
                        conexion.transaccionCommit("commitear");
                        conexion.transaccionCommit("activarCommit");
                    }
        }
        else{
            //c2 no existe, por ende lo guardo como un cliente nuevo
            //como no existe, copio el id de la direcccion de c1, es clave foranea
            c2.setDireccion_id(c1.getDireccion_id());
            c2.setCodPostal(c1.getCodPostal());
            c2.setNumero(c1.getNumero());
            c2.setObservaciones("Es un conyugue");
            System.out.println("En casar, c2 no existia");
            c2.setEstadoCivil("CASADO");
            if(guardarCliente(c2)){
                //Lo guardo a c2, pero debo buscarlo para recuperar su id
                list = buscarCliente("dni", String.valueOf(c2.getDni()));
                for(int i = 0; i<list.size(); i++){
                    if(list.get(i).getDni()==c2.getDni()){
                        c2.setId(list.get(0).getId());
                        System.out.println("LO ENCONTREEEE GATITOO");
                        exito=true;
                        break;
                    }
                    exito=false;
                }
                  
                
                System.out.println("en casar, pude guardar al c2");
                conexion.transaccionCommit("quitarAutoCommit");
                String SQL= "INSERT INTO relacion (cliente1_id, cliente2_id, estadoCivil, tipo)VALUES ("+
                            +c1.getId()+","+c2.getId()+","+"'CASADO' , '"+tipo+"')";
                  
                res= conexion.EjecutarOperacion(SQL);
                System.out.println("EN CASAR, SQL tiene: "+SQL);  
                System.out.println("EN CASAR, RES tiene: "+res);  
                if(res==0)  {
                    
                    exito= false;
                    conexion.transaccionCommit("rollBack");
                    conexion.transaccionCommit("activarCommit");
                }
                else{
                    System.out.println("En casar, PUDE hacer la relacion");
                    conexion.transaccionCommit("commitear");
                    conexion.transaccionCommit("activarCommit");
                    exito=true;
                }
            }
            else{
                exito= false;
                System.out.println("En casar, no pude guardar C2");
            }
            
            
            
        }
        
        return exito;
    }
    /**
     * 
     * @param c1
     * @param c2
     * @return true if succes. Is really important bring c1 and c2 in the same order as they appeair in the "relacion"
     */
    public boolean divorciar(Cliente c1, Cliente c2){
        boolean exito = true;
        //NECESITO QUE SI O SI VENGAN EN EL ORDEN QUE APARECEN EN LA RELACION!
        //no voy a controlar en que estado viene c1, 
        //ya asumido como que estaba cargado
        //divorcio a cada cliente individualmente
        c1.setEstadoCivil("DIVORCIADO");
        c2.setEstadoCivil("DIVORCIADO");
        exito= this.actualizarCliente(c1);
        exito=this.actualizarCliente(c2);
        //ahora busco la relacion entre estos dos y los separo
        String SQL = "UPDATE relacion SET estadoCivil = 'DIVORCIADO' WHERE cliente1_id ="+c1.getId()
                +", cliente2_id = "+c2.getId()+ "AND state = 'ACTIVO'";
        return exito;
    }
/**
    public int recuperarZona(String barrio) {
      String SQL = "SELECT zona FROM barrio WHERE nombre ="+barrio;
      ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
    }*/
}
