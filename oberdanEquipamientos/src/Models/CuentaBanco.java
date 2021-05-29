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
public class CuentaBanco {
    private String alias;
    private String nro_cuenta;
    private String tipo_cuenta;
    private String cbu;
    private String banco;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNro_cuenta() {
        return nro_cuenta;
    }

    public void setNro_cuenta(String nro_cuenta) {
        this.nro_cuenta = nro_cuenta;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
    
}
