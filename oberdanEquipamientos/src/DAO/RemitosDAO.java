/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Remito;
import Models.renglonRemito;
import Views.Main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aguss2
 */
public class RemitosDAO {
    private static RemitosDAO remitosDAO=null;
    private Statics.Conexion conexion= Statics.Conexion.getInstance();
    protected RemitosDAO(){
        
    }
    public static RemitosDAO getInstance(){
        if(remitosDAO== null){
            remitosDAO = new RemitosDAO();
        }
        return remitosDAO;
    }
    
    public int insertRemitoNuevo(Remito r){
        int res = -1;
        conexion.transaccionCommit("quitarAutoCommit");
        String SQL = "INSERT INTO `remito`( `fecha_emision`,"
                + " `credito_id`, `observacion`) "
                + "VALUES ('"+r.getFecha_emision()+"',"
                + r.getCredito_id()+",\""+r.getComentarios()+"\")";
        res = conexion.EjecutarOperacion(SQL);
        if(r.getRenglon().size() > 0){
            SQL = "INSERT INTO `renglon_remito`(`renglon_id`, `remito_id`, `cantidad`) VALUES ";
            for(int i = r.getRenglon().size()-1 ; i > 0  ; i--){
                renglonRemito rr = r.getRenglon().get(i);
                SQL += "("+rr.getRenglon_id()+","+rr.getRemito_id()+","+rr.getCantidad()+"),";
            }
            SQL += "("+r.getRenglon().get(0).getRenglon_id()+","+r.getRenglon().get(0).getRemito_id()+","+r.getRenglon().get(0).getCantidad()+")";
            System.out.println(SQL);
            res = conexion.EjecutarOperacion(SQL);
        }
        if(res > 0){
            conexion.transaccionCommit("commitear");
        }else{
            conexion.transaccionCommit("rollBack");
        }
        conexion.transaccionCommit("activarCommit");
        return res;
    }

    public int getNextID() {
        int id = -1;
        try {
            String SQL = "SELECT AUTO_INCREMENT\n" +
                    "FROM information_schema.TABLES\n" +
                    "WHERE TABLE_SCHEMA = \"bd-oberdan\"\n" +
                    "AND TABLE_NAME = \"remito\"";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if(rs.first()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al obtener el id Remito nuevo", Main.isProduccion);
        }
        return id;
    }

    public ArrayList<Remito> getRemitos() {
            ArrayList<Remito> rems = new ArrayList<>();
        try {
            String SQL = "SELECT remito.*,cliente.nombre,credito.fecha_aprobacion,credito.direccionActual_id  FROM remito"
                    + " INNER JOIN credito ON remito.credito_id = credito.id "
                    + " INNER JOIN cliente ON cliente.id = credito.cliente_id";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Remito r = new Remito(rs.getInt("remito.id"));
                r.setAnulado(rs.getBoolean("remito.anulado"));
                r.setEntregado(rs.getBoolean("remito.entregado"));
                r.setFecha_emision(rs.getTimestamp("fecha_emision"));
                r.setFecha_entrega(rs.getTimestamp("fecha_entrega"));
                r.setComentarios(rs.getString("observacion"));
                r.setFletero(rs.getString("fletero"));
                r.setCredito_id(rs.getInt("remito.credito_id"));
                // DATOS ESPECIFICOS PARA LA PANTALLA CONTROL REMITOS
                String nombrClient = rs.getString("cliente.nombre");
                int id = r.getCredito_id();
                Timestamp fecha_aprobacion = rs.getTimestamp("credito.fecha_aprobacion");
                int direccion = rs.getInt("credito.direccionActual_id");
                r.insertCredito(nombrClient, id, fecha_aprobacion, direccion);
                // RENGLONES
                SQL = "SELECT * FROM renglon_remito "
                        + "INNER JOIN renglon_credito ON renglon_credito.id = renglon_remito.renglon_id "
                        + "INNER JOIN articulos ON articulos.id = renglon_credito.producto_id "
                        + "WHERE renglon_remito.remito_id = "+r.getId();
                ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
                while (rs2.next()) {
                    renglonRemito rm = new renglonRemito();
                    rm.setId(rs2.getInt("renglon_remito.id"));
                    rm.setCantidad(rs2.getFloat("renglon_remito.cantidad"));
                    rm.setRemito_id(r.getId());
                    rm.setRenglon_id(rs2.getInt("renglon_remito.renglon_id"));
                    int idrc = rm.getRenglon_id();
                    String nombreP = rs2.getString("articulos.nombre");
                    float cantidad = rs2.getFloat("renglon_credito.cantidad");
                    String nroSerie = rs2.getString("renglon_credito.nroSerie");
                    rm.setRc(idrc, nombreP, cantidad, nroSerie);
                    r.addRenglon(rm);
                }
                rems.add(r);
            }
        } catch (SQLException ex) {
           new Statics.ExceptionManager().saveDump(ex, "Error al obtener todos los remitos", Main.isProduccion);
        }
        return rems;
    }
    /**
     * Este metodo devuelve un hash con el id del credito y la cantidad que falta entregar
     * @param cred_id
     * @return 
     */
    public HashMap<Integer,Float> getCantFaltante(int cred_id){
        HashMap<Integer,Float> rems = new HashMap<>();
        try {
            String SQL = "SELECT *,SUM(renglon_remito.cantidad) AS cantDelivered FROM `renglon_credito` " +
                        "INNER JOIN renglon_remito ON renglon_id = renglon_credito.id " +
                        "INNER JOIN articulos ON articulos.id = producto_id " +
                        "WHERE credito_id = "+cred_id;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                int id = rs.getInt("renglon_credito.id");
                float cantE = rs.getFloat("cantDelivered");
                float cantT = rs.getFloat("renglon_credito.cantidad");
                rems.put(id, cantT-cantE);
            }
        } catch (SQLException ex) {
           new Statics.ExceptionManager().saveDump(ex, "Error al obtener todos los remitos", Main.isProduccion);
        }
        return rems;
    }

    public int updateRemito(Remito remitoSelected) {
        //actualizamos remito
        int res = 0;
        String SQL = "UPDATE `remito` SET "
                + "`fecha_entrega`='"+remitoSelected.getFecha_entrega()+"',`entregado`="+remitoSelected.isEntregado()+",`anulado`="+remitoSelected.isAnulado()+","
                + "`observacion`='"+remitoSelected.getComentarios()+"',`fletero`='"+remitoSelected.getFletero()+"' WHERE id = "+remitoSelected.getId();
        res += conexion.EjecutarOperacion(SQL);
        //Actualizamos el renglon remito y el renglon credito
        for(int i = 0 ; i < remitoSelected.getRenglon().size(); i++){
            renglonRemito rm = remitoSelected.getRenglon().get(i);
            if(rm.getCantidad() != rm.getRc().getCantidad()){
                SQL = "UPDATE `renglon_remito` SET `cantidad`="+rm.getCantidad()+" WHERE id ="+rm.getId();
                res += conexion.EjecutarOperacion(SQL);
            }
            if(!"".equals(rm.getRc().getNroSerie())){
                SQL = "UPDATE `renglon_credito` SET `nroSerie`='"+rm.getRc().getNroSerie()+"' WHERE id = "+rm.getRc().getId();
                res += conexion.EjecutarOperacion(SQL);
            }
        }
        return res;
    }
    public void updateFletero(Remito r){
        String SQL = "UPDATE `remito` SET `fletero`="+r.getFletero()+" WHERE id = "+r.getId();
        conexion.EjecutarOperacion(SQL);
    }
//=======================JASPER REPORT METHODS==================================
    public JasperViewer generarReporteRemito(int id) {
        id=4;
        JasperReport reporte = null;
        JasperViewer view = null;
        Connection con = (Connection) conexion.getConexion();
        String path = "src\\Reportes\\RemitoMercaderia.jasper";
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint;
            Map parametros = new HashMap();
            parametros.put("id", id);
            jprint = JasperFillManager.fillReport(reporte, parametros, con);
            view = new JasperViewer (jprint,false);
        } catch (JRException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return view;
    }

    public JasperViewer imprimirEntregados(String ids) {
                JasperReport reporte = null;
        JasperViewer view = null;
        Connection con = (Connection) conexion.getConexion();
        String path = "src\\Reportes\\RemitosEntregados.jasper";
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint;
            Map parametros = new HashMap();
            parametros.put("idRenglones", ids);
            jprint = JasperFillManager.fillReport(reporte, parametros, con);
            view = new JasperViewer (jprint,false);
        } catch (JRException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return view;
    }
}
