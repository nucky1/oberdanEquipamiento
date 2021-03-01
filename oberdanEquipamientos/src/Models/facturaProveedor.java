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
 * @author demig
 */
public class facturaProveedor {
    private String numFactura;
    private String numPedido = "-1";
    private String tipo_factura;
    private String forma_pago;
    private int idProv;
    private int id;
    private float flete;
    private float total;
    private Date fecha_factura;
    private Date fecha_pago;
    private String estado;
    private ArrayList<renglonFactura> renglones;

    public String getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(String tipo_factura) {
        this.tipo_factura = tipo_factura;
    }

    
    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFlete() {
        return flete;
    }

    public void setFlete(float flete) {
        this.flete = flete;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(Date fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<renglonFactura> getRenglones() {
        if(this.renglones == null){
            this.renglones = new ArrayList<>();
        }
        return renglones;
    }

    public void setRenglones(ArrayList<renglonFactura> renglones) {
        this.renglones = renglones;
    }
    public void addRenglon(renglonFactura rp) {
        if(this.renglones == null){
            this.renglones = new ArrayList<>();
        }
        this.renglones.add(rp);
    }
}
