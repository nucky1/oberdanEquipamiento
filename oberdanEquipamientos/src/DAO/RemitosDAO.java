/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
    
    
}
