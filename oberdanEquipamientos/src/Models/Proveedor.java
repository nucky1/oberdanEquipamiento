/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author demig
 */
public class Proveedor {
    private int id;
    private String nombre;
    private String contacto;
    private ArrayList<String> contactosAux;


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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public ArrayList<String> getContactosAux() {
        return contactosAux;
    }

    public void setContactosAux(ArrayList<String> contactosAux) {
        this.contactosAux = contactosAux;
    }

    
    
}
