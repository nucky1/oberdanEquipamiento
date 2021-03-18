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
    public ComercioDAO(){}
    public static ComercioDAO getInstance(){
        if (ComercioDAO==null) {
            ComercioDAO=new ComercioDAO();
        }
        return ComercioDAO;
    }

    public ArrayList<Comercio> getComerciosByCliente(int id) {
        ArrayList<Comercio> comerces = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM `comercio` LEFT JOIN art_rubro ON rubro_id = art_rubro.id LEFT JOIN direccion ON comercio.direccion_id = direccion.id WHERE comercio.state = 'ACTIVO' AND art_rubro.state = 'ACTIVO' AND cliente_id = "+id;
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
                c.setDireccion(d);
                Rubro r = new Rubro();
                r.setId(rs.getInt("art_rubro.id"));
                r.setNombre(rs.getString("art_rubro.nombre"));
                c.setRubro(r);
                c.setReferencia(rs.getString("comercio.referencia"));
                c.setNumero(rs.getInt("comercio.numero"));
                c.setPropietario(rs.getInt("comercio.propietario"));
                c.setZona(rs.getString("comercio.zona"));
                c.setTipo_iva(rs.getString("tipo_iva"));
                c.setIncio_actividades(rs.getTimestamp("inicio_actividades"));
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
            String SQL = "SELECT zona FROM comercio WHERE state = 'ACTIVO' GROUP BY zona";
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                zonas.add(rs.getString("zona"));
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "error al obtener zonas clientesDao", Main.isProduccion);
        }
        return zonas;
    }

}
