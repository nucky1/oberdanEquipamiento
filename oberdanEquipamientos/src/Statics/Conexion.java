/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author demig
 */ 
  
public class Conexion {
    private Connection conexion;
    private ResultSet resultado;
    private Statement s;
    private static Conexion db;
    
    private Conexion(){
        resultado = null;
        s = null; 
        String url = "jdbc:mysql://localhost:3306/bd-oberdan"; // 127.0.0.1 tambien anda en vez de localhost
        String password = "";
        String user = "root";
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            conexion = DriverManager.getConnection(url,user,password); 
            if(conexion == null)
            {
                System.out.println("no se conecto");
            }else{
                System.out.println("se conecto");
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage()); 
            //e.printStackTrace();
        }    
    }
    
    public static Conexion getInstance(){    
        if(db == null)
            db = new Conexion();
        return db;
    }
   
    public void Desconectar(){
        try{
            conexion.close();
            conexion= null;
        }catch(Exception e)
        {
            System.out.println("Problema para cerrar la Conexión a la base de datos ");
        }
    }
    

    /**
     * 1) commitear: COMITEA EN BD              
     * 2) quitarAutoCommit: SACA EL AUTOCOMMIT DE BD           
     * 3) activarCommit:  VUELVE A ACTIVAR EL AUTO COMMIT                
     * 4) rollBack:      VUELVE EL ESTADO DE LA BD AL ULTIMO COMMIT 
     * @param accion operacion a realizar 
     */
    public void transaccionCommit(String accion){
        try {
            switch(accion){
                case"commitear":{
                    conexion.commit();
                    break;
                }
                case"quitarAutoCommit":{
                    conexion.setAutoCommit(false);
                    break;
                }
                case"activarCommit":{
                    conexion.setAutoCommit(true);
                    break;
                }
                case"rollBack":{
                    conexion.rollback();
                    break;
                }
            }
        } catch (SQLException ex) {
            System.out.println( "Error: SQLException" );
                while (ex != null) {
                    System.out.println ("SQLState: " + ex.getSQLState ());
                    System.out.println ("Mensaje:  " + ex.getMessage ());
                    System.out.println ("ErrorCode:   " + ex.getErrorCode ());
                    ex = ex.getNextException();
                }
        }
    }

    public java.sql.ResultSet EjecutarConsultaSQL(String sql){        
        try {
            this.s = this.conexion.createStatement();
            this.resultado=this.s.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public int EjecutarOperacion(String sql){
        int respuesta = 0;
        try {           
            respuesta = this.s.executeUpdate(sql);
            } catch(SQLException ex){
                // Mostramos toda la informacion sobre el error disponible
                System.out.println( "Error: SQLException" );
                while (ex != null) {
                    System.out.println ("SQLState: " + ex.getSQLState ());
                    System.out.println ("Mensaje:  " + ex.getMessage ());
                    System.out.println ("ErrorCode:   " + ex.getErrorCode ());
                    ex = ex.getNextException();
                }
            } catch(Exception e) {
                System.out.println("Se produjo un error inesperado:    "+e.getMessage());
            }
        return respuesta;
      }
           
}