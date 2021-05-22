/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Hernan
 */
public class Empleado implements Comparable<Empleado>{
    private int id;
    private int dni;
    private String tipoDni;
    private String nombre;
    private int direccionId;
    private ArrayList<Contacto> contacto;
    private String nacionalidad;
    private String provincia;
    private String ciudad;
    private String barrio;
    private String direccion;
    private String Nro;
    private String codigoPostal;
    private String referencia;
    private String estadoCivil;
    private Date fechaNacimiento;
    private String categoria;
    private String convenio;
    private String aporteOSocial;
    private String cuil;
    private String user;
    private String pass;
    private String tipo;
    // este atributo solo es para credito
    private Timestamp fechaAprobacion;
    private boolean aprobado;

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    
    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }
    
    public Empleado(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getTipoDni() {
        return tipoDni;
    }

    public void setTipoDni(String tipoDni) {
        this.tipoDni = tipoDni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(int direccionId) {
        this.direccionId = direccionId;
    }

    public ArrayList<Contacto> getContacto() {
        return contacto;
    }

    public void setContacto(ArrayList<Contacto> contacto) {
        this.contacto = contacto;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNro() {
        return Nro;
    }

    public void setNro(String Nro) {
        this.Nro = Nro;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }



    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getAporteOSocial() {
        return aporteOSocial;
    }

    public void setAporteOSocial(String aporteOSocial) {
        this.aporteOSocial = aporteOSocial;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

   

    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo){
            this.tipo= tipo;
            }
    
    public String toString(){
        return this.nombre;
    }

    @Override
    public int compareTo(Empleado o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
