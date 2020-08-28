/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

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
    private String numFactura;
    private int idProv;
    private float flete;
    private Date fechaPago;
    private Date fecha;
    private String formaPago;

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    
}
