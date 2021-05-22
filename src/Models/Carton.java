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
    private int cliente_id;
    private String cliente_nombre;
    private Date ultimo_pago;
    private int cuotas_adeudadas;
    private int cuotas_aCobrar;
    private float importe_cancelado;
    private float importe_ingresado;
    private String estado;
    private Date vencimiento;
    private float deuda;
    private float importe_cuota;
    private int cant_cuota_credito;
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

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public Date getUltimo_pago() {
        return ultimo_pago;
    }

    public void setUltimo_pago(Date ultimo_pago) {
        this.ultimo_pago = ultimo_pago;
    }

    public int getCuotas_adeudadas() {
        return cuotas_adeudadas;
    }

    public void setCuotas_adeudadas(int cuotas_adeudadas) {
        this.cuotas_adeudadas = cuotas_adeudadas;
    }

    public int getCuotas_aCobrar() {
        return cuotas_aCobrar;
    }

    public void setCuotas_aCobrar(int cuotas_aCobrar) {
        this.cuotas_aCobrar = cuotas_aCobrar;
    }

    public float getImporte_ingresado() {
        return importe_ingresado;
    }

    public void setImporte_ingresado(float importe_ingresado) {
        this.importe_ingresado = importe_ingresado;
    }

    public int getCant_cuota_credito() {
        return cant_cuota_credito;
    }

    public void setCant_cuota_credito(int cant_cuota_credito) {
        this.cant_cuota_credito = cant_cuota_credito;
    }
    
    public int cantCuotasXPagar(){
        float totalCredito=this.cant_cuota_credito*this.importe_cuota;
        return this.cant_cuota_credito-(int)((totalCredito-this.importe_cancelado)/this.importe_cuota);
    }
    public int cantCuotasAdeudas(){
        return (int) (((this.cant_cuota_credito*this.importe_cuota)-this.importe_cancelado)/this.importe_cuota);
    }       
    }
