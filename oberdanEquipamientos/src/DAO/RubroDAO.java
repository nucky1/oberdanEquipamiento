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
        
        String SQL = "SELECT * from art_rubro"
               + "WHERE state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try{
            while(rs.next()){
                Rubro p = new Rubro();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("proveedores_nombre"));
                rubros.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rubros;
    }

}
