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
public class Cuota implements Comparable<Cuota>{
    private int id;
    private String tipo;
    private int cantidad;
    private int mes;
    private int dia;
    private float porcentajeExtra;
    private boolean activa; //para saber si el producto seleccionado la usa

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Cuota(String tipo, float porcentajeExtra, int cantidad, int dia, int mes) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.porcentajeExtra = porcentajeExtra;
        this.mes = mes;
        this.dia = dia;
    }

    public Cuota() {
        
    }
    //este es el comparador usado
    @Override
    public boolean equals(Object o){
        if(o instanceof Cuota){
            return this.tipo.equalsIgnoreCase(((Cuota) o).getTipo()) 
                    && this.cantidad == ((Cuota) o).getCantidad() 
                    && this.porcentajeExtra == ((Cuota) o).getPorcentajeExtra();
        }
        return false;
    }
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
    public void setActiva(boolean activa) {
        this.activa = activa;
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
    public String toString(){
        return this.cantidad + "-" + this.tipo + "-" + this.porcentajeExtra + "%";
    }

    @Override
    public int compareTo(Cuota o) {
        int comp = this.tipo.compareTo(o.getTipo());
        if(comp == 0)
            comp = Integer.compare(this.cantidad, o.getCantidad());
        if(comp == 0)
            comp = Float.compare(porcentajeExtra, o.getPorcentajeExtra());
        return comp;
    }   
    
    
}
