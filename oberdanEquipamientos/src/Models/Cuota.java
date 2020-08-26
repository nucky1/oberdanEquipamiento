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
public class Cuota {
    private int id;
    private String tipo;
    private int cantidad;
    private float porcentajeExtra;
    private boolean activa; //para saber si el producto seleccionado la usa

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        if(activa == 1){
            this.activa = true;
        }else{
            this.activa = false;   
        }
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPorcentajeExtra() {
        return porcentajeExtra;
    }

    public void setPorcentajeExtra(float porcentajeExtra) {
        this.porcentajeExtra = porcentajeExtra;
    }
    
    
}
