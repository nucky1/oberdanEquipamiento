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
public class Pais {
    private String nombre;
    private int id = -1;

    public Pais(String nombre) {
        this.nombre = nombre;
    }
@Override
    public boolean equals(Object o){
        if(o instanceof Pais){
            Pais p = (Pais)o;
            return p.getNombre().equalsIgnoreCase(nombre);
        }
        return false;
    }
    public Pais() {
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String toString(){
        return nombre;
    }
}
