/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Objects;

/**
 *
 * @author demig
 */
public class renglonPedido {
    private int id;
    private Producto p;
    private int cantidad=0;
    private int cantFaltante=0;
    private float subTotal;
    private float costo;
    private float porcentajeIva=1;

    public int getCantFaltante() {
        return cantFaltante;
    }

    public void setCantFaltante(int cantFaltante) {
        this.cantFaltante = cantFaltante;
    }

    
    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof renglonPedido){
            renglonPedido rp = (renglonPedido) o;
            int idProd = rp.getP().getId();
            int thisIdProd = this.p.getId();
            return thisIdProd == idProd;
        }else{
            renglonFactura rp = (renglonFactura) o;
            int idProd = rp.getP().getId();
            int thisIdProd = this.p.getId();
            return thisIdProd == idProd;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,p,cantidad,subTotal,costo);
    }

    
    
}
