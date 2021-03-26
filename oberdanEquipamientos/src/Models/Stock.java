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
public class Stock {
    private int id;
    private int stock_actual;
    private int stock_ingresado;
    private int stock_reservado;
    private int stock_pedido;
    private float precio_compra;
    private Timestamp fechaCompra;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    public int getStock_ingresado() {
        return stock_ingresado;
    }

    public void setStock_ingresado(int stock_ingresado) {
        this.stock_ingresado = stock_ingresado;
    }

    public int getStock_reservado() {
        return stock_reservado;
    }

    public void setStock_reservado(int stock_reservado) {
        this.stock_reservado = stock_reservado;
    }

    public int getStock_pedido() {
        return stock_pedido;
    }

    public void setStock_pedido(int stock_pedido) {
        this.stock_pedido = stock_pedido;
    }

    public float getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(float precio_compra) {
        this.precio_compra = precio_compra;
    }

    public Timestamp getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Timestamp fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    
}
