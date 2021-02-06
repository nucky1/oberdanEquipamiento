/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author demigp.setNumFactura(numFac);
                p.setIdProv(prov.getId());
                p.setFlete(flete);
                p.setFormaPago(formaPago);
                p.setFecha(fechaFac);
                p.setFechaPago(fechaPago);
 */
public class Pedido {
    private String numPedido;
    private int idProv;
    private float flete;
    private float total;
    private Date fecha;
    private String estado;
    private ArrayList<renglonPedido> renglones;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<renglonPedido> getRenglones() {
        return renglones;
    }

    public void setRenglones(ArrayList<renglonPedido> renglones) {
        this.renglones = renglones;
    }
    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }
    
    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public float getFlete() {
        return flete;
    }

    public void setFlete(float flete) {
        this.flete = flete;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
