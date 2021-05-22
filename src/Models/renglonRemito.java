/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Aguss2
 */
public class renglonRemito {
    private int id;
    private int renglon_id;
    private int remito_id;
    private float cantidad;
    private RenglonCredito rc;

    public renglonRemito(int id) {
        this.id = id;
    }

    public renglonRemito() {
        
    }

    

    public RenglonCredito getRc() {
        return rc;
    }
    @Override
    public boolean equals(Object o){
        if(!( o instanceof renglonRemito))
            return false;
        return ((renglonRemito)o).getId()==this.id;
    }
    public void setRc(int id, String nombreP, float cantidad, String nroSerie) {
        this.rc = new RenglonCredito(id, nombreP, cantidad, nroSerie);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenglon_id() {
        return renglon_id;
    }

    public void setRenglon_id(int renglon_id) {
        this.renglon_id = renglon_id;
    }

    public int getRemito_id() {
        return remito_id;
    }

    public void setRemito_id(int remito_id) {
        this.remito_id = remito_id;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    void setNroSerie(String nroSerie) {
        this.rc.setNroSerie(nroSerie);
    }

   
}
