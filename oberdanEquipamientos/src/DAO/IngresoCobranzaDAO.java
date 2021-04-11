/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Carton;
import Models.Credito;
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
public class IngresoCobranzaDAO {
    private static IngresoCobranzaDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    
    protected IngresoCobranzaDAO(){
        
    }
    public static IngresoCobranzaDAO getInstance(){
        if (controller == null){
            controller = new IngresoCobranzaDAO();
        }
        return controller;
    }

    
    
    
    
    
    public List<Planilla> getPlanillas() {
       List <Planilla> list = new ArrayList<>();
       
       String SQL ="SELECT planilla.* FROM planilla WHERE planilla.ingresada = 0 AND 'state'= 'ACTIVO'";
       ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
       System.out.println("En ingreso cobranza dao, en getPlanillas la consulta fue= \n"+SQL);
       try{
           while (rs.next()){
               Planilla p = new Planilla();
               p.setId(rs.getInt("id"));
               p.setCobrador_id(rs.getInt("cobrador_id"));
              
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

    public boolean actualizarCarton(Carton carton) {
        conexion.transaccionCommit("quitarAutoCommit");
        boolean exito= false;
        String SQL="UPDATE carton SET importe_cancelado= "+carton.getImporte_cancelado()+
                ", credito_id = "+carton.getCredito_id()+", nro_planila = "+carton.getNro_planilla()
                +" , estado= '"+carton.getEstado()+"', deuda= "+carton.getDeuda()
                +", importe_cuota= "+carton.getImporte_cuota()+", nro_cuota= "
                +carton.getNro_cuota()+", vencimiento = "+carton.getVencimiento()
                +" WHERE id="+carton.getId();
        System.out.println("En Ingreso cobranza dao, en actualizar carton el update es: \n"+SQL);
        
        int res = conexion.EjecutarOperacion(SQL);
        if(res == 0){
            exito = false;
        }
        else{
            exito = true;
        }
        if(exito){
            conexion.transaccionCommit("commitear");
            conexion.transaccionCommit("activarCommit");
        }
       else{
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
       }
       return exito;
    }

    public boolean guardarPlanilla(Planilla planillaSelected) {
       
       conexion.transaccionCommit("quitarAutoCommit");
       boolean exito  = false;
       //planillaSelected.setIngresada(true);
       String SQL = "INSERT INTO planilla (cobrador_id, efectivo,gastos,"
               + "total_rendicion,cobranza_s_planilla,importe_ingresado,diferencia,"
               + "rendicion_s_planilla,valores_entregados,saldo,cuotas_aCobrar,cant_cuotas_pagadas,"
               + "cant_cuotas_adelantadas,observacion,venc_pri_cuota,ingresada)"
               + "VALUES ("+planillaSelected.getCobrador_id()+", "
               + planillaSelected.getEfectivo()+", "+planillaSelected.getGastos()+", "
               + planillaSelected.getTotal_rendicion()+", "+planillaSelected.getCobranza_s_planilla()+""
               + planillaSelected.getRendicion_s_planilla()+" , "+planillaSelected.getDiferencia()+" ,"
               + planillaSelected.getRendicion_s_planilla() +", "+planillaSelected.getTotal_rendicion()+","
               + planillaSelected.getSaldo()+","+planillaSelected.getCuotas_aCobrar()+", "
               + planillaSelected.getCant_cuotas_pagadas()+","+planillaSelected.getCant_cuotas_adelantadas()+", '"
               + planillaSelected.getObservacion()+"',"+planillaSelected.getVenc_pri_cuota()+", "+planillaSelected.isIngresada()+")";
      System.out.println("En Ingreso Cobranza dao, la consulta de grabar planilla es: \n"+SQL);
       int res = conexion.EjecutarOperacion(SQL);
        
        if(res == 0) exito = false;
        else{
            exito = true;
        }
       if(exito){
            conexion.transaccionCommit("commitear");
            conexion.transaccionCommit("activarCommit");
        }
       else{
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
       }
       return exito;
    }
    
    public boolean actualizarPlanilla(Planilla p){
        boolean exito = false;
        conexion.transaccionCommit("quitarAutoCommit"); 
        String SQL = "UPDATE planilla SET "
                + "cobrador_id = "+p.getCobrador_id()+ 
                ", efectivo = "+p.getEfectivo()+", gastos = "+p.getGastos()
                +", total_rendicion = "+p.getTotal_rendicion()+", cobranza_s_planilla ="+p.getCobranza_s_planilla()
                +", importe_ingresado ="+p.getCobranza_s_planilla()+ ", diferencia= "+p.getDiferencia()
                +", rendicion_s_planilla ="+p.getRendicion_s_planilla()+ ", valores_entregados= "+p.getTotal_rendicion()
                +", saldo= "+p.getSaldo()+ ", cuotas_aCobrar = "+p.getCuotas_aCobrar()+", cant_cuotas_pagadas = "
                +p.getCant_cuotas_pagadas()+", cant_cuotas_adelantadas = "+p.getCant_cuotas_adelantadas()
                +", observacion = '"+p.getObservacion()+ "', venc_pri_cuota ="+p.getVenc_pri_cuota()
                +", ingresada =" +p.isIngresada() + ", "
                + "WHERE id= "+p.getId();
        System.out.println("En ingreso cobranza dao, en actualizarPlantilla, sql tiene :\n"+SQL);
        int res = conexion.EjecutarOperacion(SQL);
        if(res == 0){
            exito = false;
             if(exito){
                conexion.transaccionCommit("commitear"); 
                conexion.transaccionCommit("activarCommit"); 
            }else{
                conexion.transaccionCommit("rollBack");
                conexion.transaccionCommit("activarCommit");
             }
            return false;
        }
             
        else{
            // guardo los tipos de pago:
            SQL = "DELETE FROM pagos_planilla WHERE id_planilla= "+p.getId();
            res = conexion.EjecutarOperacion(SQL);
            SQL = "INSERT INTO pagos_planilla (id_planilla, id_tipo_pago , monto)"
                    + "VALUES";
            for(int i = 0; i <p.getPagosPlanilla().size(); i++){
                SQL +="("+p.getId()+", "+p.getPagosPlanilla().get(i).getId_tipo_pago()+
                        ", "+p.getPagosPlanilla().get(i).getMonto()+")";
            }
            res = conexion.EjecutarOperacion(SQL);
            if(res == 0) exito = false;
            else exito = true;
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
    
    public Planilla generarPlanilla(String fecha, String idCobrador) {
         Planilla planilla = null;
        try {
           
            String SQL ="SELECT *" +
                    "FROM `aprobaciones`" +
                    "INNER JOIN credito ON credito.id = aprobaciones.credito_id\n" +
                    "INNER JOIN cuota ON cuota.id = credito.cuota_id\n" +
                    "WHERE empleado_id ="+idCobrador+ "AND credito.estado= 'APROBADO'";
            
            ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
            if(rs.first()){
                //busco la ultima planilla modficada
                SQL = "SELECT * FROM `planilla` WHERE cobrador_id= "+idCobrador+
                        " and state= 'ACTIVO' ORDER BY updated_at DESC";
                ResultSet rs2= conexion.EjecutarConsultaSQL(SQL);
                if(rs2.first()){
                    //debo controlar, que la ultima planilla modificada sea la ultima planilla cargada!
                    //sino rindio la ultima planilla, debo emitir el aviso que la edite
                    if(rs2.getBoolean("ingresada")){
                        planilla = new Planilla();
                        // tengo un metodo getPlanilla, podria llamarlo para copiar los datos de la nueva
                        // modificar los datos que quiero
                        // podria llamar al metodo guardarPlanilla
                        //ahora debo generar los nuevos cartones
                         //
                        
                        
                    }
                    else{
                        //No esta ingresada la ultima, por ende no genero una nueva
                        return planilla;
                    }
                }
                //puede ser la primera planilla que vamos a crear de este cobrador
                //no existe alguna planilla anterior
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planilla;
    }
    
}
