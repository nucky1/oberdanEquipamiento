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
 * @author Aguss2
 */
public class Remito {
    private int id;
    private int nroRemito;
    private int credito_id;
    private boolean entregado = false;
    private boolean anulado = false;
    private String comentarios;
    private Timestamp fecha_emision;
    private Timestamp fecha_entrega;
    private ArrayList<renglonRemito> renglon;
    private String fletero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNroRemito() {
        return nroRemito;
    }

    public void setNroRemito(int nroRemito) {
        this.nroRemito = nroRemito;
    }

    public int getCredito_id() {
        return credito_id;
    }

    public void setCredito_id(int credito_id) {
        this.credito_id = credito_id;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Timestamp getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Timestamp fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Timestamp getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Timestamp fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public ArrayList<renglonRemito> getRenglon() {
        return renglon;
    }

    public void setRenglon(ArrayList<renglonRemito> renglon) {
        this.renglon = renglon;
    }

    public String getFletero() {
        return fletero;
    }

    public void setFletero(String fletero) {
        this.fletero = fletero;
    }
    
}
