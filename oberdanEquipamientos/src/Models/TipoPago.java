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
public class TipoPago {
    private String nombre;
    private int id = -1;
    @Override
    public boolean equals (Object o){
        if(o instanceof TipoPago){
        TipoPago tp = (TipoPago) o;
        return tp.getNombre().equalsIgnoreCase(nombre);
    }
        return false;
    }

    
    public TipoPago(String nombre) {
        this.nombre = nombre;
    }
    public TipoPago() {
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

   
    
}
