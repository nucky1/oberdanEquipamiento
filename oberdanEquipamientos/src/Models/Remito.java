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
    private int credito_id;
    private boolean entregado = false;
    private boolean anulado = false;
    private String comentarios;
    private String sucursal;
    private Timestamp fecha_emision;
    private Timestamp fecha_entrega;
    private ArrayList<renglonRemito> renglon;
    private String fletero;
    private Credito c = null;
    
    public void insertCredito(String nombrClient,int id, Timestamp fecha_aprobacion,int direccion){
        if(c == null)
            c = new Credito();
        c.setId(id);
        c.setCliente(new Cliente(nombrClient));
        c.setFecha_aprobacion(fecha_aprobacion);
        c.setDireccion_id(direccion);
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    
    public Remito(int id) {
        this.id = id;
    }

    public Credito getC() {
        return c;
    }

    public void setC(Credito c) {
        this.c = c;
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

    public void addRenglon(renglonRemito rm) {
        if(this.renglon == null){
            renglon = new ArrayList<>();
        }
        renglon.add(rm);
    }

    public void updateRenglonById(float cantidad, String nroSerie, int renglonId) {
        if(renglon.contains(new renglonRemito(renglonId))){
            int i = renglon.indexOf(new renglonRemito(renglonId));
            renglon.get(i).setCantidad(cantidad);
            renglon.get(i).setNroSerie(nroSerie);
        }else{
            System.out.println("No encontro el renglon");
        }
    }
    
}
