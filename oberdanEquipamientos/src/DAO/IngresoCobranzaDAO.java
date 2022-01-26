/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Carton;
import Models.Cliente;
import Models.Comercio;
import Models.Credito;
import Models.Cuota;
import Models.Empleado;
import Models.Planilla;
import Models.Producto;
import Models.RenglonCredito;
import Models.TipoPago;
import Views.Main;
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
        String SQL = "SELECT carton.* FROM cartones WHERE carton.nro_planilla ="
                +nro_planilla+" AND STATE = 'ACTIVO' ";
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
    public boolean controlarCartones(String idCobrador){
        boolean exito = false;
        String SQL ="SELECT *" +
                    "FROM `aprobaciones`" +
                    "INNER JOIN credito ON credito.id = aprobaciones.credito_id\n" +
                    "INNER JOIN cuota ON cuota.id = credito.cuota_id\n" +
                    "WHERE empleado_id ="+idCobrador+ "AND credito.estado= 'APROBADO'";
        ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
        try {
            while (rs.next()){
                SQL ="SELECT * FROM carton WHERE credito_id = "+rs.getInt("credito.id");
                ResultSet  rs2= conexion.EjecutarConsultaSQL(SQL);
                if(rs2.first()){
                    exito=true;
                    
                }else{
                    System.out.println("EN ingreso cobranza dao, controlar cartones, encontro un credito sin carton generado");
                    return false;
                }
                
            }} catch (SQLException ex) {
            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }
    public Planilla generarPlanilla(String fecha, String idCobrador) {
         Planilla planilla = null;
          String SQL ="SELECT *" +
                    "FROM `aprobaciones`" +
                    "INNER JOIN credito ON credito.id = aprobaciones.credito_id\n" +
                    "INNER JOIN cuota ON cuota.id = credito.cuota_id\n" +
                    "WHERE empleado_id ="+idCobrador+ "AND credito.estado= 'APROBADO'";
           
            
            ResultSet rs= conexion.EjecutarConsultaSQL(SQL);
        try {
            if(rs.first()){
                //Hay creditos que todavia se deben cobrar
                //Puedo generar una planilla
                //creo una planilla con la fecha de hoy y recupero su id para 
                //asignarselo a los creditos que no tengan el id de planilla
                SQL = "INSERT INTO planilla VALUES (cobrador_id ) "
                        + "VALUES ("+idCobrador+")";
                int res = conexion.EjecutarOperacion(SQL);
                if (res==0){
                    return planilla;
                }
                else{
                    SQL= "SELECT id FROM planilla WHERE cobrador_id= "
                         +idCobrador+" AND ingresada = 0 AND ORDER BY id DES";
                    ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
                    if(rs2.first()){
                        planilla.setId(rs.getInt("id"));
                        return planilla;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planilla;
    }

    public boolean cargarRenglonesPlanilla(String idCobrador, Planilla p, String date) {
            boolean exito = false;
        //ESTO NECESITA SER MEJOR FILTRADO, XQ NO DIFERENCIA CREDITOS
           //CON O SIN CARTON GENERADO
           /*
           SELECT * FROM `aprobaciones` INNER JOIN credito ON 
           credito.id = aprobaciones.credito_id INNER JOIN cuota 
           ON cuota.id = credito.cuota_id INNER JOIN comercio 
           ON credito.comercio_id = comercio.id INNER JOIN cliente 
           ON cliente.id = credito.cliente_id  WHERE empleado_id = 2  
           AND credito.estado= 'APROBADO'
           */
            String SQL ="SELECT *" +
                    "FROM `aprobaciones`" +
                    "INNER JOIN credito ON credito.id = aprobaciones.credito_id\n" +
                    "INNER JOIN cuota ON cuota.id = credito.cuota_id\n" +
                    "INNER JOIN comercio ON credito.comercio_id= comercio.id "
                    +" INNER JOIN cliente ON cliente.id = credito.cliente_id " +
                    "WHERE empleado_id ="+idCobrador+ "AND credito.estado= 'APROBADO'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            ArrayList<Credito> listCreditos = new ArrayList();
        try {
            if(rs.first()){
                listCreditos = cargarCreditos(rs);
                if(!listCreditos.isEmpty()){
                    for(int i=0; i<listCreditos.size(); i++){
                        
                        exito= generarCarton(listCreditos.get(i));
                        if (exito == false) return false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoCobranzaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return exito;        
    }
    
    
    private ArrayList<Credito> cargarCreditos(ResultSet rs) {
        ArrayList<Credito> list = new ArrayList();
        try{
            while (rs.next()) {
                //insertar datos del cliente
                Cliente client = new Cliente();
                client.setNombre(rs.getString("cliente.nombre"));
                client.setId(rs.getInt("cliente.id"));
                client.setLimite_credito(rs.getFloat("cliente.limite_credito"));
                //insertar datos del comercio
                Comercio com = new Comercio();
                com.setClienteId(rs.getInt("comercio.cliente_id"));
                com.setId(rs.getInt("comercio.id"));
                com.setNombre(rs.getString("comercio.nombre"));
                com.setTipo_iva(rs.getString("comercio.tipo_iva"));
                //insertar datos del credito
                Credito cred = new Credito();
                cred.setComerce(com);
                cred.setCliente(client);
                cred.setId(rs.getInt("credito.id"));
                cred.setSolicitud_id(rs.getInt("credito.nro_solicitud"));
                cred.setZona(rs.getString("credito.zona"));
                cred.setCant_cuotas(rs.getInt("credito.cant_cuotas"));
                cred.setTipo(rs.getString("credito.tipo"));
                cred.setObservacion(rs.getString("credito.observacion"));
                cred.setFecha_aprobacion(rs.getTimestamp("credito.fecha_aprobacion"));
                cred.setFecha_solicitud(rs.getTimestamp("fecha_solicitud"));
                cred.setVenc_pri_cuota(rs.getTimestamp("credito.venc_pri_cuota"));
                cred.setVenc_credito(rs.getTimestamp("venc_credito"));
                cred.setEstado(rs.getString("credito.estado"));
                cred.setImporte_total(rs.getFloat("credito.importe_total"));
                cred.setImporte_cuota(rs.getFloat("credito.importe_cuota"));
                cred.setImporte_pri_cuota(rs.getFloat("credito.importe_pri_cuota"));
                cred.setImporte_deuda(rs.getFloat("credito.importe_deuda"));
                cred.setImporte_credito(rs.getFloat("credito.importe_credito"));
                cred.setAnticipo(rs.getFloat("credito.anticipo"));
                cred.setImporte_ult_cuota(rs.getFloat("credito.importe_ult_cuota"));
                cred.setComision(rs.getFloat("credito.comision"));
                //insertar renglones:
                if(!cred.getTipo().equals("SOLICITUD")){
                    String SQL = "SELECT * FROM renglon_credito "
                            + "INNER JOIN articulos ON articulos.id = producto_id "
                            + "WHERE credito_id = "+cred.getId();
                    ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
                    while (rs2.next()) {                        
                        RenglonCredito ren = new RenglonCredito();
                        ren.setId(rs2.getInt("renglon_credito.id"));
                        ren.setSubTotal(rs2.getFloat("sub_total"));
                        ren.setImporte_cuota(rs2.getFloat("importe_cuota"));
                        ren.setCosto(rs2.getFloat("costo_prod"));
                        ren.setCantidad(rs2.getFloat("cantidad"));
                        Producto p = ProductoDAO.getInstance().buscarProductoById(rs2.getInt("producto_id")).get(0);
                        ren.setP(p);
                        cred.addRenglon(ren);
                    }
                    cred.setFecha_credito(rs.getTimestamp("fecha_credito"));
                    //insertar plan de pago
                    Cuota cuo = new Cuota();
                    cuo.setCantidad(rs.getInt("cuota.cantidad"));
                    cuo.setPorcentajeExtra(rs.getFloat("cuota.porcentaje_extra"));
                    cuo.setTipo(rs.getString("cuota.tipo"));
                    cuo.setId(rs.getInt("cuota.id"));
                    cred.setPlan(cuo);
                    //insertar aprobaciones:
                    ResultSet aprobaciones;
                    SQL = "SELECT aprobaciones.*,empleado.nombre, empleado.tipo FROM aprobaciones "
                            + "LEFT JOIN empleado ON empleado.id = aprobaciones.empleado_id WHERE aprobaciones.credito_id = "+cred.getId();
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    while (aprobaciones.next()) {                        
                        Empleado e = new Empleado();
                        e.setId(aprobaciones.getInt("aprobaciones.empleado_id"));
                        e.setNombre(aprobaciones.getString("empleado.nombre"));
                        e.setTipo(aprobaciones.getString("empleado.tipo"));
                        e.setFechaAprobacion(aprobaciones.getTimestamp("aprobaciones.fecha"));
                        e.setAprobado(aprobaciones.getBoolean("aprobaciones.estado"));
                        switch(e.getTipo()){
                            case "VENDEDOR":{
                                cred.setVendedor(e);
                                break;
                            }
                            case "COBRADOR":{
                                cred.setCobrador(e);
                                break;
                            }
                            case "ADMINISTRADOR":{
                                cred.setAdmin(e);
                                break;
                            }
                            case "GERENTE":{
                                cred.setGerente(e);
                                break;
                            }
                        }
                    }
                }
                list.add(cred);
            }
        }catch(Exception e){
            new Statics.ExceptionManager().saveDump(e, "Error en metodo cargarCreditos", Main.isProduccion);
        }
        return list;
    }
     public boolean generarCarton(Credito creditoSelected) {
        // controlar: las cuotas adeudadas cuando hace un adelanto
        boolean exito= false;
        int idPlanilla;
        // en este caso no lo actualizo al credito, lo hare en ingreso cobranza
        //this.updateCredito(creditoSelected);
        Carton c = new Carton();

        //ojo que el metodo que llamo para guardar esto por primera vez, solo carga
        //algunos campos, los que necesito
        /**
         * carton (credito_id, nro_planilla, cliente_id ,"
    + " cliente_nombre, cuotas_adeudadas, cuotas_aCobrar"
    + ", cant_cuotas_credito, deuda, importe_cuota, nro_cuota, vencimiento )"
         */
        c.setCuotas_adeudadas(0);
        c.setCliente_id(creditoSelected.getCliente().getId());
        c.setCliente_nombre(creditoSelected.getCliente().getNombre());
        c.setCuotas_aCobrar(creditoSelected.getCant_cuotas());
        c.setCant_cuota_credito(creditoSelected.getCant_cuotas());
        c.setDeuda(creditoSelected.getImporte_deuda());
        c.setImporte_cuota(creditoSelected.getImporte_cuota());
        c.setNro_cuota(1);
        c.setVencimiento(creditoSelected.getVenc_pri_cuota());
        exito = guardarCarton(c);
                               
       
         
        return exito;
    }
     public boolean guardarCarton(Carton c){
        
        boolean exito = false;
        String SQL = "INSERT INTO carton VALUES (credito_id, nro_planilla, cliente_id,"
                + "cliente_nombre, ultimo_pago, cuotas_adeudadas, cuotas_aCobrar,"
                + " cant_cuotas_credito, importe_cancelado, importe_ingresado,"
                + " estado, deuda, importe_cuota, nro_cuota, vencimiento) "
                + "VALUES ("+c.getCredito_id()+" , "+c.getNro_planilla()+" , "
                +c.getCliente_id()+" , '"+c.getCliente_nombre()+"' ,"+c.getUltimo_pago()
                +" , "+c.getCuotas_adeudadas()+" , "+c.getCuotas_aCobrar()+" , "
                +c.getCant_cuota_credito()+" , "+c.getImporte_cancelado()+" , '"
                +c.getEstado()+"' , "+c.getDeuda()+" , "+c.getImporte_cuota()+" , "
                +c.getNro_cuota()+" , "+c.getVencimiento()+"  )";
         System.out.println("EN Ingreso cobranza  dao, guardar carton, el insert fue : \n "+SQL);
         int res = conexion.EjecutarOperacion(SQL);
         if (res == 0) return false;
         else{
             exito=true;
         }
        return exito;
    }
    
}
