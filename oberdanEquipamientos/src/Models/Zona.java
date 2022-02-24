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
public class Zona implements Comparable<Zona> {

    private int id;
    private String nombre;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    @Override
    public int compareTo(Zona o) {
        return this.nombre.compareTo(o.getNombre());
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
