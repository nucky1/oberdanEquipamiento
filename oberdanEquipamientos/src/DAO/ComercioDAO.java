/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Comercio;
import Models.Direccion;
import Models.Rubro;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author demig
 */
public class ComercioDAO {
    private static ComercioDAO ComercioDAO=null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    protected ComercioDAO(){}
    public static ComercioDAO getInstance(){
        if (ComercioDAO==null) {
            ComercioDAO=new ComercioDAO();
        }
        return ComercioDAO;
    }

    public ArrayList<Comercio> getComerciosByCliente(int id) {
        ArrayList<Comercio> comerces = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM `comercio` "
                    + "LEFT JOIN art_rubro ON rubro_id = art_rubro.id "
                    + "LEFT JOIN direccion ON comercio.direccion_id = direccion.id "
                    + "WHERE comercio.state = 'ACTIVO' AND art_rubro.state = 'ACTIVO' AND cliente_id = "+id;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Comercio c = new Comercio();
                c.setClienteId(id);
                c.setId(rs.getInt("comercio.id"));
                c.setCuit(rs.getString("comercio.cuit"));
                c.setNombre(rs.getString("comercio.nombre"));
                Direccion d = new Direccion();
                d.setNombre(rs.getString("direccion.nombre"));
                d.setId(rs.getInt("direccion.id"));
                d.setId_barrio(rs.getInt("direccion.barrio_id"));
                c.setDireccion(d);
                Rubro r = new Rubro();
                r.setId(rs.getInt("art_rubro.id"));
                r.setNombre(rs.getString("art_rubro.nombre"));
                c.setRubro(r);
                c.setReferencia(rs.getString("comercio.referencia"));
                c.setNumero(rs.getInt("comercio.numero"));
                c.setPropietario(rs.getInt("comercio.esPropietario"));
                c.setZona(rs.getString("comercio.zona"));
                c.setTipo_iva(rs.getString("comercio.tipo_iva"));
                c.setIncio_actividades(rs.getTimestamp("comercio.inicio_actividades"));
                comerces.add(c);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error al objetener los comercios de un cliente getComerciosByCliente", Main.isProduccion);
        }
        return comerces;
    }
    
    public ArrayList<String> getZona() {
        ArrayList<String> zonas = new ArrayList<>();
        try {
            /**
             * 
            Esta forma funcionaba, la zona atada al comercio.
            String SQL = "SELECT zona FROM comercio WHERE state = 'ACTIVO' GROUP BY zona";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                zonas.add(rs.getString("zona"));
            }
            *  */
            String SQL = "SELECT * FROM zonas WHERE state = 'ACTIVO'";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                zonas.add(rs.getString("nombre"));
            }
            
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "error al obtener zonas clientesDao", Main.isProduccion);
        }
        return zonas;
    }

    public int insertComercio(Comercio c) {
        String SQL = "INSERT INTO `comercio`( `cliente_id`, `direccion_id`, `rubro_id`, "
                + "`nombre`, `referencia`, `numero`, "
                + "`direIdemProp`, `zona`, `cuit`, "
                + "`tipo_iva`, `inicio_actividades`, `esPropietario`) "
                + "VALUES ("+c.getClienteId()+","+c.getDireccion().getId()+","+c.getRubro().getId()+",'"
                +c.getNombre()+"','"+c.getReferencia()+"',"+c.getNumero()+","
                + c.getDireIdemProp()+",'"+c.getZona()+"','"+c.getCuit()+"','"
                + c.getTipo_iva()+"', '"+c.getIncio_actividades()+"' , "+c.getPropietario()+")";
        int resultado=conexion.EjecutarOperacion(SQL);
        System.out.println("insertando comercio tengo sql: \n"+SQL);
        System.out.println("Y en conexion devuelve:"+resultado);
        SQL = "SELECT MAX(id) as idLast FROM comercio WHERE state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if(rs.first()){
                return rs.getInt("idLast");
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "error al obtener el id del ultimo comercio insertado (metodo : insertComercio)", Main.isProduccion);
        }
        return -1;
    }

    public void updateComercio(Comercio c) {
        int idDir = -1, idRub = -1;
        if(c.getDireccion() != null)
            idDir = c.getDireccion().getId();
        if(c.getRubro() != null)
            idRub = c.getRubro().getId();
        String SQL = "UPDATE `comercio` SET `direccion_id`="+idDir+",`rubro_id`="+idRub+","
                + "`nombre`='"+c.getNombre()+"',`referencia`='"+c.getReferencia()+"',`numero`="+c.getNumero()+","
                + "`esPropietario`="+c.getPropietario()+",`zona`='"+c.getZona()+"',`cuit`="+c.getCuit()+","
                + "`tipo_iva`='"+c.getTipo_iva()+"',`inicio_actividades`='"+c.getIncio_actividades()+"'"
                + " WHERE id = "+c.getId();
        conexion.EjecutarOperacion(SQL);
    }

    public boolean insertZona(String text) {
        String SQL = "SELECT * FROM zonas WHERE state = 'ACTIVO' AND zonas.nombre='"+text+"'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if(rs.first()){
               return false; 
            }
            else{
                SQL = "INSERT INTO zonas (nombre) VALUES ('"+text+"')";
                if(conexion.EjecutarOperacion(SQL)>0){
                    return true;
                } 
                }
        } catch (SQLException ex) {
             new Statics.ExceptionManager().saveDump(ex, "error al insertar zonas clientesDao", Main.isProduccion);
        }
        return false;
    }

}
