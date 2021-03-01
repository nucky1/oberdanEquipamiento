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
public class Provincia {
    private String nombre;
    private int id;
    private int id_pais;
    @Override
    public boolean equals(Object o){
        if(o instanceof Provincia){
            Provincia p = (Provincia)o;
            return p.getNombre().equalsIgnoreCase(nombre);
        }
        return false;
    }
    public Provincia(String nombre){
        this.nombre = nombre;
    }

    public Provincia() {
        
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

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }
    public String toString(){
        return nombre;
    }
}
