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
public class RenglonCredito {
    private int id;
    private Producto p;
    private float cantidad;
    private float subTotal;
    private float costo;
    private float importe_cuota;
    private String nroSerie;

    public RenglonCredito(int id, String nombreP, float cantidad, String nroSerie) {
        this.id = id;
        Producto aux = new Producto();
        aux.setNombre(nombreP);
        this.p = aux;
        this.cantidad = cantidad;
        this.nroSerie = nroSerie;
    }
    
    public RenglonCredito(int id, Producto p, int cantidad, float subTotal, float costo, float importe_cuota, float importe_min, String nroSerie) {
        this.id = id;
        this.p = p;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.costo = costo;
        this.importe_cuota = importe_cuota;
        this.nroSerie = nroSerie;
    }
    public RenglonCredito() {
        this.p = null;
        this.cantidad = 0;
        this.subTotal = 0f;
        this.costo = 0f;
        this.importe_cuota = 0f;
        this.nroSerie = "";
        
    }
    
    public float getImporte_cuota() {
        return importe_cuota;
    }

    public void setImporte_cuota(float importe_cuota) {
        this.importe_cuota = importe_cuota;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String anticipo) {
        this.nroSerie = nroSerie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getP() {
        return p;
    }

    public void setP(Producto p) {
        this.p = p;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
