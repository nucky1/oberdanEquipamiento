/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author demig
 */
public class Localidad {
    private String nombre;
    private int id;
    private int id_provincia;
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Localidad){
            Localidad l = (Localidad) o;
            return l.getNombre().equalsIgnoreCase(nombre);
        }
        return false;
    }
    public Localidad() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Localidad(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }
    public String toString(){
        return nombre;
    }
}
