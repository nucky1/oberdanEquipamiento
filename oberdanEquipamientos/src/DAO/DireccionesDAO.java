package DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demig
 */
public class DireccionesDAO {
    private static DireccionesDAO controller = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    protected DireccionesDAO() {
       
    }
    
    public static DireccionesDAO getInstance(){
        if (controller == null) {
            controller = new DireccionesDAO();
        }
        return controller;
    }

    public void añadirNacionalidad(String toLowerCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void añadirProvincia(String toLowerCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void añadirCiudad(String toLowerCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void añadirBarrio(String toLowerCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void añadirDireccion(String toLowerCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
