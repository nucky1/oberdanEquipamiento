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
public class Rubro {
    private String nombre = "";
    private int id = -1;

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
    public String toString(){
        return this.nombre;
    }
    @Override
    public boolean equals(Object o){
        Rubro b = (Rubro) o;
        if( o!= null && this != null && this.nombre != null && b.getNombre() != null)
            return this.id == b.getId() && b.getNombre().equalsIgnoreCase(this.nombre);
        return false;
    }
}
