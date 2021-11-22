package DAO;

import Models.Barrio;
import Models.Direccion;
import Models.Localidad;
import Models.Mapa;
import Models.Pais;
import Models.Provincia;
import Statics.*;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

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
                localidad.setCod_postal(rs.getString("codPostal"));
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
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo getMapa", Main.isProduccion);
        }
        return m;
    }

    public Provincia añadirPais(String toLowerCase) {
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
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo añadirNacionalidad", Main.isProduccion);
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
                l.setCod_postal(rs.getString("localidad.codPostal"));
                return l;
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo añadirProvincia", Main.isProduccion);
        }
        return null;
    }

    public Barrio añadirCiudad(String nombre, int idProvincia, String codPostal) {
        String SQL = "INSERT INTO localidad(nombre,provincia_id,codPostal) VALUES ('"+nombre+"',"+idProvincia+",'"+codPostal+"')";
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
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo añadirCiudad", Main.isProduccion);
        }
        return null;
    }

    public Direccion añadirBarrio(String toLowerCase, int idLocalidad) {
        String SQL = "INSERT INTO barrio(nombre,localidad_id) VALUES ('"+toLowerCase+"',"+idLocalidad+")";
        int i = conexion.EjecutarOperacion(SQL);
        System.out.println("int i tiene: "+i);
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
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo añadirBarrio", Main.isProduccion);
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
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo añadirDireccion", Main.isProduccion);
        }
        return 0;
    }
    /**
     * This method search the whole address from the id of address to country, means down to up.
     * @param id
     * @return if query its ok then return:
     * object[0] = Barrio;
     * object[1] = Localidad;
     * object[2] = Provincia;
     * object[3] = Pais;
     * object[4] = direccion;
     * else return null;
     */
    public Object[] getDireccionCompleta(int id) {
        try {
            String SQL = "SELECT * from direccion "
                    + "INNER JOIN barrio ON barrio.id = direccion.barrio_id "
                    + "INNER JOIN localidad ON localidad.id = barrio.localidad_id "
                    + "INNER JOIN provincia ON localidad.provincia_id = provincia.id "
                    + "INNER JOIN pais ON provincia.pais_id = pais.id "
                    + "WHERE direccion.id = "+id+" AND direccion.state = 'ACTIVO'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            Object[] o = new Object[5];
            if(rs.first()){
                Pais p = new Pais();
                Provincia prov = new Provincia();
                Localidad l = new Localidad();
                Barrio b = new Barrio();
                Direccion d = new Direccion();
                b.setId(rs.getInt("barrio.id"));
                b.setId_localidad(rs.getInt("localidad.id"));
                l.setId(rs.getInt("localidad.id"));
                l.setId_provincia(rs.getInt("provincia.id"));
                //l.setCod_postal(rs.getString("localidad.codPostal"));
                prov.setId(rs.getInt("provincia.id"));
                prov.setId_pais(rs.getInt("pais.id"));
                p.setId(rs.getInt("pais.id"));
                b.setNombre(rs.getString("barrio.nombre"));
                l.setNombre(rs.getString("localidad.nombre"));
                l.setCod_postal(rs.getString("localidad.codPostal"));
                prov.setNombre(rs.getString("provincia.nombre"));
                p.setNombre(rs.getString("pais.nombre"));
                d.setNombre(rs.getString("direccion.nombre"));
                d.setId(rs.getInt("direccion.id"));
                o[0] = b;
                o[1] = l;
                o[2] = prov;
                o[3] = p;
                o[4] = d;
                return o;
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo getDireccionCompleta", Main.isProduccion);
        }
        return null;
    }
    
}
