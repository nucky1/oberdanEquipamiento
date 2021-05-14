/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Cuota;
import Views.Main;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author demig
 */
public class CuotasDAO {
    private static CuotasDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    protected CuotasDAO() {
       
    }
    
    public static CuotasDAO getInstance(){
        if (controller == null) {
            controller = new CuotasDAO();
        }
        return controller;
    }

    public List<Cuota> getCuotasProd(int idProd) {
        List<Cuota> cuotas = new ArrayList<>();
        String SQL = "SELECT cuota.*,artC.*,IF(artC.id is null,0,1) AS cuota_activa "
                + "FROM cuota "
                + "LEFT JOIN ("
                + "             SELECT * "
                + "             FROM articulo_cuota "
                + "             WHERE articulo_cuota.articulos_id = "+idProd+") "
                + "AS artC "
                + "ON artC.cuota_id = cuota.id "
                + "WHERE cuota.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                Cuota p = new Cuota();
                p.setId(rs.getInt("id"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setMes(rs.getInt("mes"));
                p.setDia(rs.getInt("dia"));
                p.setPorcentajeExtra(rs.getFloat("porcentaje_extra"));
                p.setActiva(rs.getInt("cuota_activa"));
                p.setTipo(rs.getString("tipo"));
                cuotas.add(p);
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return cuotas;
    }

    public void insertCuota(Cuota c) {
        String SQL = "INSERT INTO cuota SET tipo = '"+c.getTipo().toUpperCase()+"', cantidad = "+c.getCantidad()+
                        ", porcentaje_extra ="+c.getPorcentajeExtra()+", dia="+c.getDia()+
                ", mes ="+c.getMes();
        conexion.EjecutarOperacion(SQL);
    }

    public void setCuotasProd(List<Cuota> cuotasProd, int idProd) {
        if(cuotasProd == null ||cuotasProd.size()==0) //no hay cuotas aun
            return;
        String SQL = "DELETE FROM articulo_cuota WHERE articulos_id = "+idProd;
        conexion.EjecutarOperacion(SQL);
        SQL = "INSERT INTO articulo_cuota (cuota_id,articulos_id) VALUES";
        for (int i = 0; i < cuotasProd.size(); i++) {
            if(cuotasProd.get(i).getActiva()){
                SQL += " ("+cuotasProd.get(i).getId()+","+idProd+"),";
            }
        }       
        //Aca verificamos que entro al menos una vez al if del loop y por lo tanto hay algo que insertar. Además borramos la ultima coma.
        if(SQL.charAt(SQL.length()-1)== ','){
            SQL = SQL.substring(0,SQL.length()-1);
            SQL += " ON DUPLICATE KEY UPDATE cuota_id = values(cuota_id),articulos_id = values(articulos_id)";
            int res = conexion.EjecutarOperacion(SQL);
            System.out.println(res);
        }
    }

    public ArrayList<Cuota> getCuotas() {
        ArrayList<Cuota> cuotas = new ArrayList<>();
        String SQL = "SELECT *"
                + "FROM cuota "
                + "WHERE cuota.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                Cuota p = new Cuota();
                p.setId(rs.getInt("id"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setPorcentajeExtra(rs.getFloat("porcentaje_extra"));
                p.setTipo(rs.getString("tipo"));
                cuotas.add(p);
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return cuotas;
    }

    public Cuota getUltimoAñadido() {
        Cuota p = new Cuota();
        String SQL = "SELECT * "
                + "FROM cuota "
                + "WHERE created_at = (SELECT MAX(created_at) FROM cuota WHERE cuota.state = 'ACTIVO')";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            if(rs.next()){
                p.setId(rs.getInt("id"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setPorcentajeExtra(rs.getFloat("porcentaje_extra"));
                p.setTipo(rs.getString("tipo"));
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return p;
    }
    
}
