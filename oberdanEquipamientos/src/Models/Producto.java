/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;


/**
 *
 * @author demig
 */
public class Producto {
    private String observaciones;
    private String nombre;
    private String codigoBarra;
    private String nombreRubro;
    private int Id;
    private int idProveedorActual;
    private int IdProductoRubro;
    private float iva;
    private float sobretasaIva;
    private float impuesto_interno;
    private float impuesto_int_fijo;
    private boolean sinIva;
    private int estado;
    private int StockMin;
    private int Stock;
    private float precioCosto;
    private float costoFlete;

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getSobretasaIva() {
        return sobretasaIva;
    }

    public void setSobretasaIva(float sobretasaIva) {
        this.sobretasaIva = sobretasaIva;
    }

    public float getImpuesto_interno() {
        return impuesto_interno;
    }

    public void setImpuesto_interno(float impuesto_interno) {
        this.impuesto_interno = impuesto_interno;
    }

    public float getImpuesto_int_fijo() {
        return impuesto_int_fijo;
    }

    public void setImpuesto_int_fijo(float impuesto_int_fijo) {
        this.impuesto_int_fijo = impuesto_int_fijo;
    }

    
    public String getNombreRubro() {
        return nombreRubro;
    }

    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }
    
    public int isEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public boolean isSinIva() {
        return sinIva;
    }

    public void setSinIva(boolean sinIva) {
        this.sinIva = sinIva;
    }

    public int getIdProductoRubro() {
        return IdProductoRubro;
    }

    public void setIdProductoRubro(int rubro) {
        this.IdProductoRubro = rubro;
    }

    
    public int getIdProveedorActual() {
        return idProveedorActual;
    }

    public void setIdProveedorActual(int idProveedorActual) {
        this.idProveedorActual = idProveedorActual;
    }
    
    
    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getStockMin() {
        return StockMin;
    }

    public void setStockMin(int StockMin) {
        this.StockMin = StockMin;
    }

    public float getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(float precioCosto) {
        this.precioCosto = precioCosto;
    }

    public float getCostoFlete() {
        return costoFlete;
    }

    public void setCostoFlete(float costoFlete) {
        this.costoFlete = costoFlete;
    }

    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

   
}
