/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Carton;
import Models.Planilla;
import Models.TipoPago;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hernan
 */
public class AuxiliarDAO {
    private static AuxiliarDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    
    protected AuxiliarDAO(){
        
    }
    public static AuxiliarDAO getInstance(){
        if (controller == null){
            controller = new AuxiliarDAO();
        }
        return controller;
    }

    
    
    public HashMap<Integer, TipoPago> traerTiposDePago(){
        
        HashMap<Integer,TipoPago> t = new HashMap<>();
        String SQL = "SELECT * FROM tipoPago ORDER BY id";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                TipoPago tipoPago = new TipoPago();
                tipoPago.setId(rs.getInt("id"));
                tipoPago.setNombre(rs.getString("nombre"));
                t.put(tipoPago.getId(),tipoPago);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuxiliarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    public List getPlanillas() throws SQLException {
       List <Planilla> list = new ArrayList<>();
       String SQL ="SELECT planilla.* FROM planilla WHERE planilla.ingresada = 0 AND 'state'= 'ACTIVO'";
       ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
       try{
           while (rs.next()){
               Planilla p = new Planilla();
               p.setId(rs.getInt("id"));
               p.setCobrador_id(rs.getInt("cobrador_id"));
               p.setNro_planilla(rs.getInt("nro_planilla"));
               p.setCant_cuotas_pagadas(rs.getInt("cant_cuotas_pagas"));
               p.setTotal_rendicion(rs.getFloat("total_rendicion"));
               p.setCobranza_s_planilla(rs.getFloat("cobranza_s_planilla"));
               
               p.setDiferencia(rs.getFloat("diferencia"));
               p.setRendicion_s_planilla(rs.getFloat("rendicion_s_planilla"));
              
               p.setSaldo(rs.getFloat("saldo"));
               p.setCuotas_aCobrar(rs.getInt("cuotas_aCobrar"));
               p.setObservacion(rs.getString("obervacion"));
               p.setVenc_pri_cuota(rs.getDate("venc_prim_cuota"));
               p.setIngresada(rs.getBoolean("ingresada"));
               list.add(p);
              
           }
       }catch(Exception ex){
           ex.printStackTrace();
       }
       
       return list;
    }

    public List<Carton> getCartones(int nro_planilla) throws SQLException {
        List <Carton> list = new ArrayList<>();
        String SQL = "SELECT carton.* FROM cartones WHERE carton.nro_planilla ="+nro_planilla+" AND STATE = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
              Carton c = new Carton();
              c.setId(rs.getInt("id"));
              c.setCredito_id(rs.getInt("credito_id"));
              c.setNro_planilla(rs.getInt("nro_planilla"));
              c.setImporte_cancelado(rs.getFloat("importe_cancelado"));  
              c.setEstado(rs.getString("estado"));
              c.setVencimiento(rs.getDate("vencimiento"));
              c.setDeuda(rs.getFloat("deuda"));
              c.setNro_cuota(rs.getInt("nro_cuota"));
              list.add(c);
            }
        }catch(Exception ex){
           ex.printStackTrace();
       }
        return list;
    }

    public void guardarCartones(List<Carton> listaCartones) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
