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
public class renglonFactura {
    private int id;
    private Producto p;
    private int cantidad;
    private float subTotal;
    private float costo;

    public renglonFactura(Producto p, int cantidad, float costo, float subTotal) {
        this.p = p;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.costo = costo;
    }

    public renglonFactura() {
        this.p = null;
        this.cantidad = 0;
        this.subTotal = 0f;
        this.costo = 0f;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
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
