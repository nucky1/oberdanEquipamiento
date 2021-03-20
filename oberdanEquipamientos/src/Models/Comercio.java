/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Timestamp;

/**
 *
 * @author demig
 */
public class Comercio {
    private int id = -1;
    private int clienteId;
    private int numero = 0;
    private int codPostal = 0;
    private int propietario = 0;
    private Direccion direccion = null;
    private Rubro rubro= null;
    private String nombre="";
    private String referencia="";
    private String zona="";
    private String cuit="";
    private String tipo_iva="";
    private Timestamp incio_actividades = new Timestamp(System.currentTimeMillis());

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTipo_iva() {
        return tipo_iva;
    }

    public void setTipo_iva(String tipo_iva) {
        this.tipo_iva = tipo_iva;
    }

    public Timestamp getIncio_actividades() {
        return incio_actividades;
    }

    public void setIncio_actividades(Timestamp incio_actividades) {
        this.incio_actividades = incio_actividades;
    }
    
    
}
