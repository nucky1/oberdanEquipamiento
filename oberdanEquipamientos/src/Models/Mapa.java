/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author demig
 */
public class Mapa {
    private HashMap<Integer,Pais> paises;
    private HashMap<Integer,Set<Provincia>> pais_Provincia;
    private HashMap<Integer,Set<Localidad>> provincia_Localidad;
    private HashMap<Integer,Set<Barrio>> localidad_Barrio;
    private HashMap<Integer,Set<Direccion>> barrio_direccion;
    
    public boolean controlPais(String nombre){
        Pais p = new Pais();
        p.setNombre(nombre);
        paises.values().contains(p);
        return false;
    }
    public boolean controlProvincia(int idPais, String nombre){
        if(pais_Provincia.containsKey(idPais)){
            Provincia p = new Provincia();
            p.setNombre(nombre);
            Set<Provincia> setProvincia = pais_Provincia.get(idPais);
            if(setProvincia.contains(p)){
                return true;
            }
            
        }
        return false;
        
        
        
                
    }
    public HashMap<Integer, Set<Direccion>> getBarrio_direccion() {
        return barrio_direccion;
    }

    public void setBarrio_direccion(HashMap<Integer, Set<Direccion>> barrio_direccion) {
        this.barrio_direccion = barrio_direccion;
    }
    
    public HashMap<Integer, Pais> getPaises() {
        return paises;
    }

    public void setPaises(HashMap<Integer, Pais> paises) {
        this.paises = paises;
    }

    public HashMap<Integer, Set<Provincia>> getPais_Provincia() {
        return pais_Provincia;
    }

    public void setPais_Provincia(HashMap<Integer, Set<Provincia>> pais_Provincia) {
        this.pais_Provincia = pais_Provincia;
    }

    public HashMap<Integer, Set<Localidad>> getProvincia_Localidad() {
        return provincia_Localidad;
    }

    public void setProvincia_Localidad(HashMap<Integer, Set<Localidad>> provincia_Localidad) {
        this.provincia_Localidad = provincia_Localidad;
    }

    public HashMap<Integer, Set<Barrio>> getLocalidad_Barrio() {
        return localidad_Barrio;
    }

    public void setLocalidad_Barrio(HashMap<Integer, Set<Barrio>> localidad_Barrio) {
        this.localidad_Barrio = localidad_Barrio;
    }
    
        
}
