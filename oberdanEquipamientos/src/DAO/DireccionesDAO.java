package DAO;

import Models.Barrio;
import Models.Localidad;
import Models.Mapa;
import Models.Pais;
import Models.Provincia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demig
 */
class provinciaCompare implements Comparator<Provincia> { 
    public int compare(Provincia o1, Provincia o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
} 
class localidadCompare implements Comparator<Localidad> { 
    public int compare(Localidad o1, Localidad o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
} 
class barrioCompare implements Comparator<Barrio> { 
    public int compare(Barrio o1, Barrio o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
} 
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
    public Mapa getMapa(){
        String SQL = "SELECT * FROM pais ORDER BY id";
         ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        Mapa m = new Mapa();
        //variables
        HashMap<Integer,Pais> paises = new HashMap<>();
        HashMap<Integer,Set<Provincia>> p = new HashMap<>();
        HashMap<Integer,Set<Localidad>> provincias_localidades = new HashMap<>();
        HashMap<Integer,Set<Barrio>> localidades_barrio = new HashMap<>();
        Set<Provincia> provincias = new TreeSet<>(new provinciaCompare());
        Set<Localidad> localidades = new TreeSet<>(new localidadCompare());
        Set<Barrio> barrios = new TreeSet<>(new barrioCompare());
        int idPais = -1;
        int idProvincia = -1;
        int idLocalidad = -1;
        try {
            //cargar paises
            while(rs.next()){
                Pais pais = new Pais();
                pais.setId(rs.getInt("pais.id"));
                pais.setNombre(rs.getString("pais.nombre"));
                paises.put(pais.getId(), pais);
                p.put(pais.getId(), null);
            }
            //cargar provincias
            SQL = "SELECT * FROM provincia ORDER BY pais_id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Provincia provincia = new Provincia();
                provincia.setId(rs.getInt("id"));
                provincia.setId_pais(rs.getInt("pais_id"));
                provincia.setNombre(rs.getString("nombre"));
                provincias_localidades.put(provincia.getId(), null);
                if(provincia.getId_pais() != idPais){
                    idPais = provincia.getId_pais();
                    if(idProvincia != -1){
                       provincias_localidades.replace(idProvincia, localidades);
                    }
                    provincias = new TreeSet<>(new provinciaCompare());
                    provincias.add(provincia);
                }else{
                    provincias.add(provincia);
                }                
            }
            //cargar localidades
            SQL = "SELECT * FROM localidad ORDER BY provincia_id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id"));
                localidad.setId_provincia(rs.getInt("provincia_id"));
                localidad.setNombre(rs.getString("nombre"));
                localidades_barrio.put(localidad.getId(), null);
                if(localidad.getId_provincia()!= idProvincia){
                    idProvincia = localidad.getId_provincia();
                    if(idProvincia != -1){
                       provincias_localidades.replace(idProvincia, localidades);
                    }
                    localidades = new TreeSet<>(new localidadCompare());
                    localidades.add(localidad);
                }else{
                    localidades.add(localidad);
                }                
            }
            //cargar barrios
            SQL = "SELECT * FROM barrio ORDER BY localidad_id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Barrio barrio = new Barrio();
                barrio.setId(rs.getInt("id"));
                barrio.setId_localidad(rs.getInt("localidad_id"));
                barrio.setNombre(rs.getString("nombre"));
                if(barrio.getId_localidad()!= idLocalidad){
                    idLocalidad = barrio.getId_localidad();
                    if(idLocalidad != -1){
                       localidades_barrio.replace(idLocalidad, barrios);
                    }
                    barrios = new TreeSet<>(new barrioCompare());
                    barrios.add(barrio);
                }else{
                    barrios.add(barrio);
                }                
            }
            //cargar mapa
            m.setPaises(paises);
            m.setProvincia_Localidad(provincias_localidades);
            m.setPais_Provincia(p);
            m.setLocalidad_Barrio(localidades_barrio);
        } catch (SQLException ex) {
            Logger.getLogger(DireccionesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    /**
     * quedo a medias porque era manso viaje y me parece que puede
     * ser mas eficiente sacar todas las tablas de a una
     * @return 
     */
    public Mapa getDirecciones(){
        String SQL = "SELECT * FROM pais "
                + "INNER JOIN provincia ON pais.id = provincia.pais_id "
                + "INNER JOIN localidad ON provincia.id = localidad.provincia_id "
                + "INNER JOIN barrio ON localidad.id = barrio.localidad_id ";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        Mapa m = new Mapa();
        HashMap<Integer,Pais> paises = new HashMap<>();
        HashMap<Integer,Set<Provincia>> p = new HashMap<>();
        HashMap<Integer,Set<Localidad>> provincias_localidades = new HashMap<>();
        HashMap<Integer,Set<Barrio>> localidades_barrio = new HashMap<>();
        Set<Provincia> provincias = new TreeSet<>(new provinciaCompare());
        Set<Localidad> localidades = new TreeSet<>(new localidadCompare());
        Pais pais = new Pais();
        Provincia provincia = new Provincia();
        try{
            while(rs.next()){
                    if(!paises.containsKey(rs.getInt("pais.id"))){ //si es un nuevo pais, lo creo y lo añado al hashmap
                        if(pais.getId() != -1){ //si es el segundo pais, añado al hashmap de provincia con el set
                            p.put(pais.getId(), provincias);
                            provincias = new TreeSet<>(new provinciaCompare());
                        }
                        pais = new Pais();
                        pais.setId(rs.getInt("pais.id"));
                        pais.setNombre(rs.getString("pais.nombre"));
                        paises.put(pais.getId(), pais);
                    }
                    if(!provincias_localidades.containsKey(rs.getInt("pais.id"))){
                        if(provincia.getId() != -1){ //si es el segundo pais, añado al hashmap de provincia con el set
                            provincias_localidades.put(provincia.getId(), localidades);
                            localidades = new TreeSet<>(new localidadCompare());
                        }
                    }else{
                        
                    }
                    if(!paises.containsKey(pais.getId())){
                        provincia.setId(rs.getInt("provincia.id"));
                        provincia.setId_pais(pais.getId());
                        provincia.setNombre(rs.getString("provincia.nombre"));
                        provincias.add(provincia);
                    }else{
                        
                    }
                        
                    
                    if(!localidades_barrio.containsKey(pais)){
                        Set<Provincia> barrios = new TreeSet<>(new provinciaCompare());
                    }else{
                        
                    }
                    provincia.setId(rs.getInt("provincia.id"));
                    provincia.setNombre(rs.getString("provincia.nombre"));
                    provincia.setId_pais(rs.getInt("provincia.id_pais"));
                    Localidad local = new Localidad();
                    
                    
                    local.setId(rs.getInt("id"));
                    local.setNombre(rs.getString("contacto"));
                    local.setId_provincia(rs.getInt("tipo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return m;
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

    public void getNacionalidad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
