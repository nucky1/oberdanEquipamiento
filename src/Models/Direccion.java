/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Hernan
 */
public class Direccion {

    int id;
    int id_barrio;
    String nombre;

    public Direccion() {
        this.id = -1;
        this.id_barrio = -1;
        this.nombre = "";
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Direccion){
            Direccion d = (Direccion) o;
            return d.getNombre().equalsIgnoreCase(nombre);
        }
        return false;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_barrio() {
        return id_barrio;
    }

    public void setId_barrio(int id_barrio) {
        this.id_barrio = id_barrio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Direccion(String nombre) {
        this.nombre = nombre;
    }
    public String toString(){
        return nombre;
    }
}
