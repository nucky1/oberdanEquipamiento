/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Models.TipoPago;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
            String SQL = "SELECT * FROM tipo_pago WHERE nombre = '"+nombre+"'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if(rs.first()){
                return null;
            }
            TipoPago tp= null;
            SQL="INSERT INTO tipo_pago(nombre) VALUES ('"+nombre.toUpperCase()+"')";
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
//    public List <TipoPago> cargarTiposPago(){
//        List<TipoPago> lista= new ArrayList<>();
//        lista=null;
//        String SQL = "SELECT * FROM tiposPago WHERE state = 'ACTIVO'";
//        System.out.println("en ingreso cobranza dao en metodo cargar tipos de pago, la consulta fue: \n"+SQL);
//        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
//        try {
//            while(rs.next()){
//                TipoPago tp = new TipoPago();
//                tp.setId(rs.getInt("id"));
//                tp.setNombre(rs.getString("nombre"));
//                tp.setMonto(rs.getFloat("monto"));
//                lista.add(tp);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return lista;
//    }
    public List<TipoPago> traerTiposDePago(){
        
        List<TipoPago> list = new ArrayList<>();
        String SQL = "SELECT * FROM tipo_pago ORDER BY id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                TipoPago tipoPago = new TipoPago();
                tipoPago.setId(rs.getInt("id"));
                tipoPago.setNombre(rs.getString("nombre"));
                list.add(tipoPago);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
