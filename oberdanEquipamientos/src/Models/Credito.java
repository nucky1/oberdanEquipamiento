/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Hernan
 */
public class Credito {
    private int id;
    private int solicitud_id;
    private int cant_cuotas;
    private Cliente cliente;
    private int conyugue_id;
    private int direccion_id;
    private Empleado admin = null;
    private Empleado cobrador= null ;
    private Empleado vendedor= null;
    private Empleado gerente= null;
    private String estado= null;
    private Cuota plan;
    private float importe_total;
    private float importe_cuota;
    private float importe_pri_cuota;
    private float importe_deuda;
    private float importe_credito;
    private float anticipo;
    private float importe_ult_cuota;
    private float comision;
    private String tipo;
    private String observacion;
    private int zonaId;
    private boolean mercaderia_entregada;
    private Timestamp venc_pri_cuota;
    private Timestamp fecha_aprobacion;
    private Timestamp fecha_solicitud;
    private Timestamp fecha_credito;
    private Timestamp venc_credito;
    private Comercio comerce;
    private ArrayList<RenglonCredito> renglones;

    public boolean isMercaderia_entregada() {
        return mercaderia_entregada;
    }

    public void setMercaderia_entregada(boolean mercaderia_entregada) {
        this.mercaderia_entregada = mercaderia_entregada;
    }

    
    public int getConyugue_id() {
        return conyugue_id;
    }

    public void setConyugue_id(int conyugue_id) {
        this.conyugue_id = conyugue_id;
    }

    public int getDireccion_id() {
        return direccion_id;
    }

    public void setDireccion_id(int direccion_id) {
        this.direccion_id = direccion_id;
    }
    
    
    public int getCant_cuotas() {
        return cant_cuotas;
    }

    public void setCant_cuotas(int cant_cuotas) {
        this.cant_cuotas = cant_cuotas;
    }

    public int getSolicitud_id() {
        return solicitud_id;
    }

    public void setSolicitud_id(int solicitud_id) {
        this.solicitud_id = solicitud_id;
    }
    
    
    public ArrayList<RenglonCredito> getRenglones() {
        if(this.renglones == null){
            this.renglones = new ArrayList<>();
        }
        return renglones;
    }

    public void setRenglones(ArrayList<RenglonCredito> renglones) {
        this.renglones = renglones;
    }
    
    public void addRenglon(RenglonCredito rp) {
        if(this.renglones == null){
            this.renglones = new ArrayList<>();
        }
        this.renglones.add(rp);
    }

    public Timestamp getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Timestamp fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Timestamp getFecha_credito() {
        return fecha_credito;
    }

    public void setFecha_credito(Timestamp fecha_credito) {
        this.fecha_credito = fecha_credito;
    }

    
    public Timestamp getFecha_aprobacion() {
        return fecha_aprobacion;
    }

    public void setFecha_aprobacion(Timestamp fecha_aprobacion) {
        this.fecha_aprobacion = fecha_aprobacion;
    }
    
    public Comercio getComerce() {
        return comerce;
    }

    public void setComerce(Comercio comerce) {
        this.comerce = comerce;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getAdmin() {
        return admin;
    }

    public void setAdmin(Empleado admin) {
        this.admin = admin;
    }

    public Empleado getCobrador() {
        return cobrador;
    }

    public void setCobrador(Empleado cobrador) {
        this.cobrador = cobrador;
    }

    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
    }

    public Empleado getGerente() {
        return gerente;
    }

    public void setGerente(Empleado gerente) {
        this.gerente = gerente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cuota getPlan() {
        return plan;
    }

    public void setPlan(Cuota plan) {
        this.plan = plan;
    }
    
    public float getImporte_total() {
        return importe_total;
    }

    public void setImporte_total(float importe_total) {
        this.importe_total = importe_total;
    }

    public float getImporte_cuota() {
        return importe_cuota;
    }

    public void setImporte_cuota(float importe_cuota) {
        this.importe_cuota = importe_cuota;
    }

    public float getImporte_pri_cuota() {
        return importe_pri_cuota;
    }

    public void setImporte_pri_cuota(float importe_pri_cuota) {
        this.importe_pri_cuota = importe_pri_cuota;
    }

    public float getImporte_deuda() {
        return importe_deuda;
    }

    public void setImporte_deuda(float importe_deuda) {
        this.importe_deuda = importe_deuda;
    }

    public float getImporte_credito() {
        return importe_credito;
    }

    public void setImporte_credito(float importe_credito) {
        this.importe_credito = importe_credito;
    }

    public float getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(float anticipo) {
        this.anticipo = anticipo;
    }

    public float getImporte_ult_cuota() {
        return importe_ult_cuota;
    }

    public void setImporte_ult_cuota(float importe_ult_cuota) {
        this.importe_ult_cuota = importe_ult_cuota;
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getZonaId() {
        return zonaId;
    }

    public void setZonaId(int zonaId) {
        this.zonaId = zonaId;
    }

    public Timestamp getVenc_pri_cuota() {
        return venc_pri_cuota;
    }

    public void setVenc_pri_cuota(Timestamp venc_pri_cuota) {
        this.venc_pri_cuota = venc_pri_cuota;
    }

    public Timestamp getVenc_credito() {
        return venc_credito;
    }

    public void setVenc_credito(Timestamp venc_credito) {
        this.venc_credito = venc_credito;
    }
    
    
    
    
}
