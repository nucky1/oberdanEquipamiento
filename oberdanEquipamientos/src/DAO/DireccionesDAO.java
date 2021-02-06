package DAO;

import Models.Barrio;
import Models.Direccion;

import Models.Localidad;
import Models.Mapa;
import Models.Pais;
import Models.Provincia;
import Statics.*;
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
        HashMap<Integer,Set<Direccion>> barrio_direcciones = new HashMap<>();
        Set<Provincia> provincias = new TreeSet<>(new provinciaCompare());
        Set<Localidad> localidades = new TreeSet<>(new localidadCompare());
        Set<Barrio> barrios = new TreeSet<>(new barrioCompare());
        Set<Direccion> direcciones = new TreeSet<>(new direccionCompare());
        int idPais = -1;
        int idProvincia = -1;
        int idLocalidad = -1;
        int idBarrio = -1;
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
            SQL = "SELECT * FROM provincia ORDER BY pais_id,id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Provincia provincia = new Provincia();
                provincia.setId(rs.getInt("id"));
                provincia.setId_pais(rs.getInt("pais_id"));
                provincia.setNombre(rs.getString("nombre"));
                provincias_localidades.put(provincia.getId(), null);
                if(provincia.getId_pais() != idPais){
                    if(idPais != -1){
                        p.replace(idPais, provincias);
                    }
                    idPais = provincia.getId_pais();
                    provincias = new TreeSet<>(new provinciaCompare());
                    provincias.add(provincia);
                }else{
                    provincias.add(provincia);
                }                
            }
            p.replace(idPais, provincias);
            idProvincia = -1;
            //cargar localidades
            SQL = "SELECT * FROM localidad ORDER BY provincia_id,id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id"));
                localidad.setId_provincia(rs.getInt("provincia_id"));
                localidad.setNombre(rs.getString("nombre"));
                localidades_barrio.put(localidad.getId(), null);
                if(localidad.getId_provincia()!= idProvincia){
                    if(idProvincia != -1){
                        provincias_localidades.replace(idProvincia, localidades);
                    }
                    idProvincia = localidad.getId_provincia();
                    localidades = new TreeSet<>(new localidadCompare());
                    localidades.add(localidad);
                }else{
                    localidades.add(localidad);
                }                
            }
            provincias_localidades.replace(idProvincia, localidades);
            //cargar barrios
            SQL = "SELECT * FROM barrio ORDER BY localidad_id,id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Barrio barrio = new Barrio();
                barrio.setId(rs.getInt("id"));
                barrio.setId_localidad(rs.getInt("localidad_id"));
                barrio.setNombre(rs.getString("nombre"));
                barrio_direcciones.put(barrio.getId(), null);
                if(barrio.getId_localidad() != idLocalidad){
                    if(idLocalidad != -1){
                       localidades_barrio.replace(idLocalidad, barrios);
                    }
                    idLocalidad = barrio.getId_localidad();
                    barrios = new TreeSet<>(new barrioCompare());
                    barrios.add(barrio);
                }else{
                    barrios.add(barrio);
                }                
            }
            localidades_barrio.replace(idLocalidad, barrios);
            //cargar direcciones
            SQL = "SELECT * FROM direccion ORDER BY barrio_id,id";
            rs = conexion.EjecutarConsultaSQL(SQL);
            while(rs.next()){
                Direccion direccion = new Direccion();
                direccion.setId(rs.getInt("id"));
                direccion.setId_barrio(rs.getInt("barrio_id"));
                direccion.setNombre(rs.getString("nombre"));
                if(direccion.getId_barrio()!= idBarrio){
                    if(idBarrio != -1){
                       barrio_direcciones.replace(idBarrio, direcciones);
                    }
                    idBarrio = direccion.getId_barrio();
                    direcciones = new TreeSet<>(new direccionCompare());
                    direcciones.add(direccion);
                }else{
                    direcciones.add(direccion);
                }
            }
            barrio_direcciones.replace(idBarrio, direcciones);
            //cargar mapa
            m.setPaises(paises);
            m.setProvincia_Localidad(provincias_localidades);
            m.setPais_Provincia(p);
            m.setLocalidad_Barrio(localidades_barrio);
            m.setBarrio_direccion(barrio_direcciones);
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
    public Provincia añadirNacionalidad(String toLowerCase) {
        String SQL = "INSERT INTO pais(nombre) VALUES ('"+toLowerCase+"')";
        int i = conexion.EjecutarOperacion(SQL);
        if(i < 0)
            return null;
        SQL = "SELECT provincia.* FROM (SELECT MAX(id) AS maximo FROM pais) AS derived,provincia WHERE provincia.pais_id = derived.maximo";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            Provincia p = new Provincia();
            if(rs.next()){
                p.setId(rs.getInt("provincia.id"));
                p.setNombre(rs.getString("provincia.nombre"));
                p.setId_pais(rs.getInt("provincia.pais_id"));
                return p;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Localidad añadirProvincia(String toLowerCase, int idPais) {
        String SQL = "INSERT INTO provincia(nombre, pais_id) VALUES ('"+toLowerCase+"',"+idPais+")";
        int i = conexion.EjecutarOperacion(SQL);
        if(i < 0)
            return null;
        SQL = "SELECT localidad.* FROM localidad,(SELECT MAX(id) AS maximo FROM provincia) as derived WHERE localidad.provincia_id = derived.maximo";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            Localidad l = new Localidad();
            if(rs.next()){
                l.setId(rs.getInt("localidad.id"));
                l.setNombre(rs.getString("localidad.nombre"));
                l.setId_provincia(rs.getInt("localidad.provincia_id"));
                return l;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Barrio añadirCiudad(String toLowerCase, int idProvincia) {
        String SQL = "INSERT INTO localidad(nombre,provincia_id) VALUES ('"+toLowerCase+"',"+idProvincia+")";
        int i = conexion.EjecutarOperacion(SQL);
        if(i < 0)
            return null;
        SQL = "SELECT barrio.* FROM barrio,(SELECT MAX(id) AS maximo FROM localidad) AS derived WHERE barrio.localidad_id = derived.maximo";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            Barrio b = new Barrio();
            if(rs.next()){
                b.setId(rs.getInt("barrio.id"));
                b.setNombre(rs.getString("barrio.nombre"));
                b.setId_localidad(rs.getInt("barrio.localidad_id"));
                return b;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Direccion añadirBarrio(String toLowerCase, int idLocalidad) {
        String SQL = "INSERT INTO barrio(nombre,localidad_id) VALUES ('"+toLowerCase+"',"+idLocalidad+",0)";
        int i = conexion.EjecutarOperacion(SQL);
        if(i < 0)
            return null;
        SQL = "SELECT direccion.* FROM direccion,(SELECT MAX(id) AS maximo FROM barrio) AS derived WHERE direccion.barrio_id = derived.maximo";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            Direccion d = new Direccion();
            if(rs.next()){
                d.setId(rs.getInt("direccion.id"));
                d.setId_barrio(rs.getInt("direccion.barrio_id"));
                d.setNombre(rs.getString("direccion.nombre"));
                return d;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public int añadirDireccion(String toLowerCase, int idBarrio) {
        String SQL = "INSERT INTO direccion(nombre,barrio_id) VALUES ('"+toLowerCase+"',"+idBarrio+")";
        int i = conexion.EjecutarOperacion(SQL);
        if(i < 0)
            return 0;
        SQL = "SELECT MAX(id) FROM direccion";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if(rs.next()){
                return rs.getInt("MAX(id)");
            }
        } catch (SQLException ex) {
        }
        return 0;
    }
    
}
