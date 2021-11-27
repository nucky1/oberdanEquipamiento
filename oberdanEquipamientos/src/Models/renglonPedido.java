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
    private float neto;
    private float descuentoUnitario;
    private float porcentajeIva=0;
    private float ivaValor=0;

    public float getIvaValor() {
        return ivaValor;
    }

    public void setIvaValor(float ivaValor) {
        this.ivaValor = ivaValor;
    }
    
    
    public int getCantFaltante() {
        return cantFaltante;
    }
  
    public void setCantFaltante(int cantFaltante) {
        this.cantFaltante = cantFaltante;
    }

    public float getDescuentoUnitario() {
        return descuentoUnitario;
    }

    public void setDescuentoUnitario(float descuentoUnitario) {
        this.descuentoUnitario = descuentoUnitario;
    }

    public float getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(float porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }
  
    
    public float getNeto() {
        return neto;
    }

    public void setNeto(float neto) {
        this.neto = neto;
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
        return Objects.hash(id,p,cantidad,subTotal,neto);
    }

    
    
}
