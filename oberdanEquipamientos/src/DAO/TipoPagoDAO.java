/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Models.TipoPago;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hernan
 */
public class TipoPagoDAO {
    private static TipoPagoDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    
    protected TipoPagoDAO(){
        
    }
    public static TipoPagoDAO getInstance(){
        if (controller == null) {
            controller = new TipoPagoDAO();
        }
        return controller;
    }
    
    public TipoPago añadirTp(String nombre){
        try {
            String SQL = "SELECT * FROM tipoPago WHERE nombre = '"+nombre+"'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if(rs.first()){
                return null;
            }
            TipoPago tp= null;
            SQL="INSERT INTO tipoPago(nombre) VALUES ('"+nombre+"')";
            int i = conexion.EjecutarOperacion(SQL);
            if(i<0){
                return null;
            }
            tp = new TipoPago();
            tp.setNombre(nombre);
            return tp;
        } catch (SQLException ex) {
            Logger.getLogger(TipoPagoDAO.class.getName()).log(Level.SEVERE, null, ex);
            new Statics.ExceptionManager().saveDump(ex, "añadirTp", false);
        }
        return null;
    }
}
