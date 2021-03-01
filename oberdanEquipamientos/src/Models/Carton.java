/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author Hernan
 */
public class Carton {
    private int id;
    private int credito_id;
    private int nro_planilla;
    private float importe_cancelado;
    private String estado;
    private Date vencimiento;
    private float deuda;
    private float importe_cuota;
    private int nro_cuota;

    public Carton() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredito_id() {
        return credito_id;
    }

    public void setCredito_id(int credito_id) {
        this.credito_id = credito_id;
    }

    public int getNro_planilla() {
        return nro_planilla;
    }

    public void setNro_planilla(int nro_planilla) {
        this.nro_planilla = nro_planilla;
    }

    public float getImporte_cancelado() {
        return importe_cancelado;
    }

    public void setImporte_cancelado(float importe_cancelado) {
        this.importe_cancelado = importe_cancelado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public float getDeuda() {
        return deuda;
    }

    public void setDeuda(float deuda) {
        this.deuda = deuda;
    }

    public int getNro_cuota() {
        return nro_cuota;
    }

    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }

    public float getImporte_cuota() {
        return importe_cuota;
    }

    public void setImporte_cuota(float importe_cuota) {
        this.importe_cuota = importe_cuota;
    }
    
            
    }
