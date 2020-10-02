/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Views.ABMClientesView;
import Models.Cliente;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author demig
 */
public class ClientesDAO {
    private static ClientesDAO ClientesDao=null;
    private ABMClientesView view;
    public ClientesDAO(){}
    public static ClientesDAO getInstance(){
        if (ClientesDao==null) {
            ClientesDao=new ClientesDAO();
        }
        return ClientesDao;
    }
    public void setVista(ABMClientesView view){
        this.view=view;
    }
    public List <Cliente> cargarClientes(ResultSet rs){
        List<Cliente> list = new ArrayList<>();
        try{
            Cliente c = new Cliente ();
            while(rs.next()){
                // p.setId(rs.getInt("id"));
                //p.setNombre(rs.getString("nombre"));
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDni(rs.getInt("dni"));
                c.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                c.setEsSolicitante(rs.getBoolean("esSolicitante"));
                c.setCodPostal(rs.getInt("codPostal"));
                c.setReferencia(rs.getString("referencia"));
                c.setDocumentacion(rs.getString("referencia"));
                c.setNumero(rs.getInt("numero"));
                c.setDireccion_id(rs.getInt("direccion_id"));
                list.add(c);
                
                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    public List<Cliente> buscarProducto(String atributo, String valor) {
        String SQL = "SELECT * FROM `cliente` WHERE cliente.id = "
               + " FROM proveedores, articulo, art_rubro, art_stock"
               + " WHERE articulo." + atributo+" like '%"+valor+"%'"
               + " AND articulo.rubro_id = art_rubro.id"
               + " AND articulo.id = art_stock.producto_id"
               + " AND articulo.proveedor_id = proveedores.id"
               + " AND articulo.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }
    
}
