/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Models.Carton;
import Models.Cliente;
import Models.Credito;
import Models.Comercio;
import Models.Cuota;
import Models.Empleado;
import Models.Producto;
import Models.RenglonCredito;
import Views.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hernan
 */
public class CreditosDAO {
    private static CreditosDAO CreditosDao=null;
    private Statics.Conexion conexion= Statics.Conexion.getInstance();
    public CreditosDAO(){
        
    }
    public static CreditosDAO getInstance(){
        if(CreditosDao== null){
            CreditosDao = new CreditosDAO();
        }
        return CreditosDao;
    }
    public List <Credito> buscarCredito (int id) throws SQLException{
        String SQL = "SELECT credito.* FROM credito"
                +" WHERE credito.id ="+id+ "AND credito.state= 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        ArrayList<Credito> list = new ArrayList();
        while(rs.next()){
            Credito c = new Credito();
            
            
        }
        return list;
    }

    public ArrayList<Credito> buscarSolicitud(String text, String atributoBusqueda) {
        String condicion = "1";
        if(atributoBusqueda.equals("nombre")){
            condicion = "cliente.nombre = '%"+text+"%'";
        }else{
            condicion = "credito.nro_solicitud = '%"+text+"%'";
        }
        String SQL = "SELECT * FROM `credito`" +
                    "INNER JOIN cliente ON cliente_id = cliente.id " +
                    "INNER JOIN comercio ON comercio_id = comercio.id " +
                    "WHERE `tipo`= \"SOLICITUD\" AND state = 'ACTIVO' AND "+condicion;
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarCreditos(rs);
    }

    public ArrayList<Credito> buscarCreditoActivo(String text, String atributoBusqueda) {
        ArrayList<Credito> list = new ArrayList();
        String condicion = "1";
        if(atributoBusqueda.equals("nombre")){
            condicion = "cliente.nombre = '%"+text+"%'";
        }else{
            condicion = "credito.id = '%"+text+"%'";
        }
        String SQL = "SELECT * FROM `credito`" +
                    "INNER JOIN cliente ON cliente_id = cliente.id " +
                    "INNER JOIN comercio ON credito.comercio_id = comercio.id "+ 
                    "LEFT JOIN cuota ON cuota_id = credito.cuota_id" +
                    "WHERE `tipo`!= \"SOLICITUD\" AND credito.state = 'ACTIVO' AND "+condicion;
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarCreditos(rs);
    }

    private ArrayList<Credito> cargarCreditos(ResultSet rs) {
        ArrayList<Credito> list = new ArrayList();
        try{
            while (rs.next()) {
                //insertar datos del cliente
                Cliente client = new Cliente();
                client.setNombre(rs.getString("cliente.nombre"));
                client.setId(rs.getInt("cliente.id"));
                client.setLimite_credito(rs.getFloat("cliente.limite_credito"));
                //insertar datos del comercio
                Comercio com = new Comercio();
                com.setClienteId(rs.getInt("comercio.cliente_id"));
                com.setId(rs.getInt("comercio.id"));
                com.setNombre(rs.getString("comercio.nombre"));
                com.setTipo_iva(rs.getString("comercio.tipo_iva"));
                //insertar plan de pago
                Cuota cuo = new Cuota();
                cuo.setCantidad(rs.getInt("cuota.cantidad"));
                cuo.setPorcentajeExtra(rs.getFloat("cuota.procentaje_extra"));
                cuo.setTipo(rs.getString("cuota.tipo"));
                cuo.setId(rs.getInt("cuota.id"));
                //insertar datos del credito
                Credito cred = new Credito();
                cred.setComerce(com);
                cred.setCliente(client);
                cred.setPlan(cuo);
                cred.setId(rs.getInt("credito.id"));
                cred.setSolicitud_id(rs.getInt("credito.nro_solicitud"));
                cred.setZona(rs.getInt("credito.zona"));
                cred.setCant_cuotas(rs.getInt("credito.cant_cuotas"));
                cred.setTipo(rs.getString("credito.tipo"));
                cred.setObservacion(rs.getString("credito.observacion"));
                cred.setFecha_aprobacion(rs.getTimestamp("credito.fecha_aprobacion"));
                cred.setVenc_pri_cuota(rs.getTimestamp("credito.venc_pri_cuota"));
                cred.setEstado(rs.getString("credito.estado"));
                cred.setImporte_total(rs.getFloat("credito.importe_total"));
                cred.setImporte_cuota(rs.getFloat("credito.importe_cuota"));
                cred.setImporte_pri_cuota(rs.getFloat("credito.importe_pri_cuota"));
                cred.setImporte_deuda(rs.getFloat("credito.importe_deuda"));
                cred.setImporte_credito(rs.getFloat("credito.importe_credito"));
                cred.setAnticipo(rs.getFloat("credito.anticipo"));
                cred.setImporte_ult_cuota(rs.getFloat("credito.importe_ult_cuota"));
                cred.setComision(rs.getFloat("credito.comision"));
                //insertar renglones:
                if(cred.getTipo().equals("SOLICITUD")){
                    String SQL = "SELECT * FROM renglon_credito "
                            + "INNER JOIN articulos ON articulos.cod = producto_id"
                            + "WHERE credito_id = "+cred.getId();
                    ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
                    while (rs2.next()) {                        
                        RenglonCredito ren = new RenglonCredito();
                        ren.setId(rs2.getInt("renglon_credito.id"));
                        ren.setSubTotal(rs2.getFloat("sub_total"));
                        ren.setImporte_cuota(rs2.getFloat("importe_cuota"));
                        ren.setCosto(rs2.getFloat("costo_prod"));
                        ren.setCantidad(rs2.getFloat("cantidad"));
                        Producto p = ProductoDAO.getInstance().buscarProducto(rs2.getInt("producto_id")).get(0);
                        ren.setP(p);
                        cred.addRenglon(ren);
                    }
                }
                //insertar aprobaciones:
                ResultSet aprobaciones;
                if(rs.getInt("credito.admin_id") == -1){
                    String SQL = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    Empleado e = new Empleado();
                    e.setId(aprobaciones.getInt("id"));
                    e.setNombre(aprobaciones.getString("nombre"));
                    cred.setAdmin(e);
                }
                if(rs.getInt("credito.gerente_id") == -1){
                    String SQL = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    Empleado e = new Empleado();
                    e.setId(aprobaciones.getInt("id"));
                    e.setNombre(aprobaciones.getString("nombre"));
                    cred.setGerente(e);
                }
                if(rs.getInt("credito.cobrador_id") == -1){
                    String SQL = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    Empleado e = new Empleado();
                    e.setId(aprobaciones.getInt("id"));
                    e.setNombre(aprobaciones.getString("nombre"));
                    cred.setCobrador(e);
                }
                if(rs.getInt("credito.vendedor_id") == -1){
                    String SQL = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    Empleado e = new Empleado();
                    e.setId(aprobaciones.getInt("id"));
                    e.setNombre(aprobaciones.getString("nombre"));
                    cred.setVendedor(e);
                }
                list.add(cred);
            }
        }catch(Exception e){
            new Statics.ExceptionManager().saveDump(e, "Error en metodo cargarCreditos", Main.isProduccion);
        }
        return list;
    }

    public void insertCreditoNuevo(Credito creditoSelected) {
        String SQL ="INSERT INTO `credito`"
                + "(`cliente_id`, `comercio_id`, `fecha_aprobacion`, "
                + "`admin_id`, `cobrador_id`, `vendedor_id`, "
                + "`gerente_id`, `estado`, `cuota_id`, "
                + "`importe_total`, `importe_cuota`, `importe_pri_cuota`, "
                + "`importe_deuda`, `importe_credito`, `anticipo`, "
                + "`importe_ult_cuota`, `comision`, `tipo`, "
                + "`observacion`, `venc_pri_cuota`, `zona`, "
                + "`fecha_solicitud`, `fecha_credito`, `nro_solicitud`, "
                + "`cant_cuotas`) VALUES "
                + "("+creditoSelected.getCliente().getId()+","+creditoSelected.getComerce().getId()+","+creditoSelected.getFecha_aprobacion()+","
                + creditoSelected.getAdmin().getId()+","+creditoSelected.getCobrador().getId()+","+creditoSelected.getVendedor().getId()+","
                + creditoSelected.getGerente().getId()+",'"+creditoSelected.getEstado()+"',"+creditoSelected.getPlan().getId()+","
                + creditoSelected.getImporte_total()+","+creditoSelected.getImporte_cuota()+","+creditoSelected.getImporte_pri_cuota()+","
                + creditoSelected.getImporte_deuda()+","+creditoSelected.getImporte_credito()+","+creditoSelected.getAnticipo()+","
                + creditoSelected.getImporte_ult_cuota()+","+creditoSelected.getComision()+",'"+creditoSelected.getTipo()+"','"
                + creditoSelected.getObservacion()+"',"+creditoSelected.getVenc_pri_cuota()+","+creditoSelected.getZona()+","
                + creditoSelected.getFecha_solicitud()+","+creditoSelected.getFecha_credito()+","+creditoSelected.getSolicitud_id()+","
                + creditoSelected.getCant_cuotas()+")";
        conexion.EjecutarOperacion(SQL);
        if(creditoSelected.getRenglones().size() > 0){
            ArrayList<RenglonCredito> rc = creditoSelected.getRenglones();
            SQL = "INSERT INTO `renglon_credito`(`sub_total`, `importe_cuota`, `nroSerie`, `credito_id`, `producto_id`, `costo_prod`, `cantidad`) VALUES";
            for(int i = rc.size()-1; i > 0; i--){
                SQL += " ("+rc.get(i).getSubTotal()+","+rc.get(i).getImporte_cuota()+",'"+rc.get(i).getNroSerie()+"',"+creditoSelected.getId()+","+rc.get(i).getP().getId()+","+rc.get(i).getCosto()+","+rc.get(i).getCantidad()+"),";
            }
            SQL += " ("+rc.get(0).getSubTotal()+","+rc.get(0).getImporte_cuota()+",'"+rc.get(0).getNroSerie()+"',"+creditoSelected.getId()+","+rc.get(0).getP().getId()+","+rc.get(0).getCosto()+","+rc.get(0).getCantidad()+")";
            conexion.EjecutarOperacion(SQL);
        }
    }

    public ArrayList<Credito> getCreditosCliente(int id) {
        ArrayList<Credito> creditos = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM credito WHERE state = 'ACTIVO' AND cliente_id = "+id;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Credito cred = new Credito();
                cred.setId(rs.getInt("credito.id"));
                cred.setTipo(rs.getString("credito.tipo"));
                cred.setEstado(rs.getString("credito.estado"));
                cred.setImporte_total(rs.getFloat("credito.importe_total"));
                cred.setImporte_cuota(rs.getFloat("credito.importe_cuota"));
                cred.setImporte_deuda(rs.getFloat("credito.importe_deuda"));
                cred.setImporte_credito(rs.getFloat("credito.importe_credito"));
                if(rs.getInt("credito.cobrador_id") == -1){
                    String SQL2 = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    ResultSet aprobaciones = conexion.EjecutarConsultaSQL(SQL2);
                    Empleado e = new Empleado();
                    e.setId(aprobaciones.getInt("id"));
                    e.setNombre(aprobaciones.getString("nombre"));
                    cred.setCobrador(e);
                }
                creditos.add(cred);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al recuperar creditos de un cliente", Main.isProduccion);
        }
        return creditos;
    }
    
    public ArrayList<Carton> getCartonesCredito(int id){
        ArrayList<Carton> cartones = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM carton WHERE state = 'ACTIVO' AND credito_id = "+id;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            while (rs.next()) {
                Carton c = new Carton();
                c.setId(rs.getInt("id"));
                c.setCredito_id(id);
                c.setDeuda(rs.getInt("deuda"));
                c.setEstado(rs.getString("estado"));
                c.setImporte_cuota(rs.getFloat("importe_cuota"));
                c.setImporte_cancelado(rs.getFloat("importe_cancelado"));
                c.setVencimiento(rs.getTimestamp("vencimiento"));
                cartones.add(c);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al recuperar los cartones de un credito", Main.isProduccion);
        }
        return cartones;
    }

    public ArrayList<Credito> getCreditosUnificables(Cuota plan, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
