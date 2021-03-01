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
public class Planilla {
    private int id;
    private int cobrador_id;
    private int nro_planilla;
    private float efectivo;
    private float gastos;
    // el total de rendicion esta ligado a otros tipos de pago ingresados!!
    // cruzar con el id 
    private float total_rendicion;
    private float cobranza_s_planilla;
    private float diferencia;
    private float rendicion_s_planilla;
    private float saldo;
    private int cuotas_aCobrar;
    private int cant_cuotas_pagadas;
    private int cant_cuotas_adelantadas;
    private String observacion;
    private Date venc_pri_cuota;
    private boolean ingresada;

    public Planilla() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCobrador_id() {
        return cobrador_id;
    }

    public void setCobrador_id(int cobrador_id) {
        this.cobrador_id = cobrador_id;
    }

    public int getNro_planilla() {
        return nro_planilla;
    }

    public void setNro_planilla(int nro_planilla) {
        this.nro_planilla = nro_planilla;
    }

    public float getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }

    public float getGastos() {
        return gastos;
    }

    public void setGastos(float gastos) {
        this.gastos = gastos;
    }

    public float getTotal_rendicion() {
        return total_rendicion;
    }

    public void setTotal_rendicion(float total_rendicion) {
        this.total_rendicion = total_rendicion;
    }

    public float getCobranza_s_planilla() {
        return cobranza_s_planilla;
    }

    public void setCobranza_s_planilla(float cobranza_s_planilla) {
        this.cobranza_s_planilla = cobranza_s_planilla;
    }


    public float getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(float diferencia) {
        this.diferencia = diferencia;
    }

    public float getRendicion_s_planilla() {
        return rendicion_s_planilla;
    }

    public void setRendicion_s_planilla(float rendicion_s_planilla) {
        this.rendicion_s_planilla = rendicion_s_planilla;
    }

    

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int getCuotas_aCobrar() {
        return cuotas_aCobrar;
    }

    public void setCuotas_aCobrar(int cuotas_aCobrar) {
        this.cuotas_aCobrar = cuotas_aCobrar;
    }

    public int getCant_cuotas_pagadas() {
        return cant_cuotas_pagadas;
    }

    public void setCant_cuotas_pagadas(int cant_cuotas_pagadas) {
        this.cant_cuotas_pagadas = cant_cuotas_pagadas;
    }

    public int getCant_cuotas_adelantadas() {
        return cant_cuotas_adelantadas;
    }

    public void setCant_cuotas_adelantadas(int cant_cuotas_adelantadas) {
        this.cant_cuotas_adelantadas = cant_cuotas_adelantadas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getVenc_pri_cuota() {
        return venc_pri_cuota;
    }

    public void setVenc_pri_cuota(Date venc_pri_cuota) {
        this.venc_pri_cuota = venc_pri_cuota;
    }

    public boolean isIngresada() {
        return ingresada;
    }

    public void setIngresada(boolean ingresada) {
        this.ingresada = ingresada;
    }

  
}
