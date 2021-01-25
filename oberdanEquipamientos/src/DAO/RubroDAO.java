/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Rubro;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author demig
 */
public class RubroDAO {
    private static RubroDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    protected RubroDAO() {
       
    }
    
    public static RubroDAO getInstance(){
        if (controller == null) {
            controller = new RubroDAO();
        }
        return controller;
    }
    
    public List<Rubro> getRubros() {
        List<Rubro> rubros = new ArrayList<>();
        
        String SQL = "SELECT art_rubro.* FROM art_rubro "
               + "WHERE state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                Rubro p = new Rubro();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                rubros.add(p);
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "Error al recorrer resultset todos los rubros", false);
        }
        return rubros;
    }

    public int InsertRubro(String texto) {
        String SQL = "INSERT INTO art_rubro SET nombre = '"+texto+"'";
        return conexion.EjecutarOperacion(SQL);
    }
    public Rubro getLastInsert(){
        String SQL = "SELECT * FROM `art_rubro` "
                + "WHERE `created_at` = "
                + "(SELECT MAX(`created_at`) FROM `art_rubro` WHERE state = 'ACTIVO')";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        Rubro p = null;
        try{
            while(rs.next()){
                p = new Rubro();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
            }
        }catch(Exception ex){
            new Statics.ExceptionManager().saveDump(ex, "Error al recorrer resultset ultimo rubro", false);
        }
        return p;
    }

}
