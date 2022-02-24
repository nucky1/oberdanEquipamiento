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
import Models.Direccion;
import Models.Empleado;
import Models.Producto;
import Models.RenglonCredito;
import Views.Main;
//import com.sun.org.glassfish.gmbal.ParameterNames;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Hernan
 */
public class CreditosDAO {

    private static CreditosDAO CreditosDao = null;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();

    protected CreditosDAO() {

    }

    public static CreditosDAO getInstance() {
        if (CreditosDao == null) {
            CreditosDao = new CreditosDAO();
        }
        return CreditosDao;
    }

    public Credito buscarIdCliente(int id) throws SQLException {
        String SQL = "SELECT credito.* FROM credito"
                + " WHERE credito.id =" + id + "AND credito.state= 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        if (rs.first()) {
            Credito c = new Credito();
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("cliente_id"));
            c.setCliente(cliente);
            c.setImporte_cuota(rs.getFloat("importe_cuota"));
            c.setImporte_deuda(rs.getFloat("importe_deuda"));
            return c;
        }
        return null;
    }

    public ArrayList<Credito> buscarSolicitud(String text, String atributoBusqueda) {
        String condicion = "1";
        if (atributoBusqueda.equals("nombre")) {
            condicion = "cliente.nombre LIKE '%" + text + "%'";
        } else {
            condicion = "credito.nro_solicitud LIKE '%" + text + "%'";
        }
        String SQL = "SELECT * FROM `credito`"
                + "INNER JOIN cliente ON cliente_id = cliente.id "
                + "INNER JOIN comercio ON comercio_id = comercio.id "
                + "WHERE `tipo`= \"SOLICITUD\" AND " + condicion;

//        String SQL = "SELECT * FROM `credito`"
//                + "INNER JOIN cliente ON cliente_id = cliente.id "
//                + "INNER JOIN comercio ON credito.comercio_id = comercio.id "
//                + "LEFT JOIN cuota ON cuota_id = credito.cuota_id "
//                + "WHERE `credito`.`tipo` = \"SOLICITUD\" AND " +condicion;
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarCreditos(rs);
    }

    public ArrayList<Credito> buscarCreditoActivo(String text, String atributoBusqueda) {
        ArrayList<Credito> list = new ArrayList();
        String condicion = "1";
        if (atributoBusqueda.equals("nombre")) {
            condicion = "cliente.nombre LIKE '%" + text + "%'";
        } else {
            condicion = "credito.id LIKE '%" + text + "%'";
        }
        String SQL = "SELECT * FROM `credito`"
                + "INNER JOIN cliente ON cliente_id = cliente.id "
                + "INNER JOIN comercio ON credito.comercio_id = comercio.id "
                + "LEFT JOIN cuota ON cuota_id = credito.cuota_id "
                + "WHERE `credito`.`tipo` != \"SOLICITUD\" AND " + condicion;
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarCreditos(rs);
    }

    private ArrayList<Credito> cargarCreditos(ResultSet rs) {
        ArrayList<Credito> list = new ArrayList();
        Cuota cuo = new Cuota();
        try {
            while (rs.next()) {
                //insertar datos del cliente
                Cliente client = new Cliente();
                client.setNombre(rs.getString("cliente.nombre"));
                client.setId(rs.getInt("cliente.id"));
                client.setDni(rs.getInt("cliente.dni"));
                client.setNumero(rs.getString("cliente.numero"));
                //client.setCodPostal(rs.getString("cliente.codPostal"));
                client.setDireccion_id(rs.getInt("cliente.direccion_id"));
                client.setLimite_credito(rs.getFloat("cliente.limite_credito"));
                //insertar datos del comercio
                Comercio com = new Comercio();
                com.setClienteId(rs.getInt("comercio.cliente_id"));
                com.setId(rs.getInt("comercio.id"));
                com.setNombre(rs.getString("comercio.nombre"));
                com.setTipo_iva(rs.getString("comercio.tipo_iva"));
                com.setNumero(rs.getInt("comercio.numero"));
                ///com.setCodPostal(rs.getInt("comercio.codPostal"));
                Direccion dir = new Direccion();
                dir.setId(rs.getInt("comercio.direccion_id"));
                com.setDireccion(dir);
                //insertar datos del credito
                Credito cred = new Credito();
                cred.setComerce(com);
                cred.setCliente(client);
                cred.setDireccion_id(rs.getInt("direccionActual_id"));
                cred.setId(rs.getInt("credito.id"));
                cred.setSolicitud_id(rs.getInt("credito.nro_solicitud"));
                cred.setZonaId(rs.getInt("credito.zonaId"));
                cred.setCant_cuotas(rs.getInt("credito.cant_cuotas"));
                cred.setTipo(rs.getString("credito.tipo"));
                cred.setObservacion(rs.getString("credito.observacion"));
                cred.setFecha_aprobacion(rs.getTimestamp("credito.fecha_aprobacion"));
                cred.setFecha_solicitud(rs.getTimestamp("fecha_solicitud"));
                cred.setVenc_pri_cuota(rs.getTimestamp("credito.venc_pri_cuota"));
                cred.setVenc_credito(rs.getTimestamp("venc_credito"));
                cred.setEstado(rs.getString("credito.estado"));
                cred.setImporte_total(rs.getFloat("credito.importe_total"));
                cred.setImporte_cuota(rs.getFloat("credito.importe_cuota"));
                cred.setImporte_pri_cuota(rs.getFloat("credito.importe_pri_cuota"));
                cred.setImporte_deuda(rs.getFloat("credito.importe_deuda"));
                cred.setImporte_credito(rs.getFloat("credito.importe_credito"));
                cred.setAnticipo(rs.getFloat("credito.anticipo"));
                cred.setMercaderia_entregada(rs.getBoolean("credito.mercaderia_entregada"));
                cred.setImporte_ult_cuota(rs.getFloat("credito.importe_ult_cuota"));
                cred.setComision(rs.getFloat("credito.comision"));
                cred.setConyugue_id(rs.getInt("conyugue_id"));
                //insertar renglones:
                if (!cred.getTipo().equals("SOLICITUD")) {
                    if (cred.getEstado().equals("EMITIDA")) {
                           cuo= this.getCuotaByCreditoId(cred.getId()); //TODO saca todo de aca desarma este metodo lpm!
                    }
                    String SQL = "SELECT * FROM renglon_credito "
                            + "INNER JOIN articulos ON articulos.id = producto_id "
                            + "WHERE credito_id = " + cred.getId();
                    ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
                    System.out.println("en cargar creditos, la consulta al renglon es:\n" + SQL);
                    while (rs2.next()) {
                        RenglonCredito ren = new RenglonCredito();
                        ren.setId(rs2.getInt("renglon_credito.id"));
                        ren.setSubTotal(rs2.getFloat("sub_total"));
                        ren.setImporte_cuota(rs2.getFloat("importe_cuota"));
                        ren.setCosto(rs2.getFloat("costo_prod"));
                        ren.setCantidad(rs2.getFloat("cantidad"));
                        System.out.println("Trato de recuperar el producto cuyo id es: " + rs2.getInt("renglon_credito.producto_id"));
                        Producto p = ProductoDAO.getInstance().buscarProductoById(rs2.getInt("renglon_credito.producto_id")).get(0);
                        ren.setP(p);
                        cred.addRenglon(ren);
                    }
                    cred.setFecha_credito(rs.getTimestamp("fecha_credito"));
                    //insertar plan de pago
                    
                    cuo.setCantidad(rs.getInt("cuota.cantidad"));
                    cuo.setPorcentajeExtra(rs.getFloat("cuota.porcentaje_extra"));
                    cuo.setTipo(rs.getString("cuota.tipo"));
                    cuo.setId(rs.getInt("cuota.id"));
                    cred.setPlan(cuo);
                    //insertar aprobaciones:
                    ResultSet aprobaciones;
                    SQL = "SELECT aprobaciones.*,empleado.nombre, empleado.tipo FROM aprobaciones "
                            + "LEFT JOIN empleado ON empleado.id = aprobaciones.empleado_id WHERE aprobaciones.credito_id = " + cred.getId();
                    aprobaciones = conexion.EjecutarConsultaSQL(SQL);
                    while (aprobaciones.next()) {
                        Empleado e = new Empleado();
                        e.setId(aprobaciones.getInt("aprobaciones.empleado_id"));
                        e.setNombre(aprobaciones.getString("empleado.nombre"));
                        e.setTipo(aprobaciones.getString("empleado.tipo"));
                        e.setFechaAprobacion(aprobaciones.getTimestamp("aprobaciones.fecha"));
                        e.setAprobado(aprobaciones.getBoolean("aprobaciones.estado"));
                        switch (e.getTipo()) {
                            case "VENDEDOR": {
                                cred.setVendedor(e);
                                break;
                            }
                            case "COBRADOR": {
                                cred.setCobrador(e);
                                break;
                            }
                            case "ADMINISTRADOR": {
                                cred.setAdmin(e);
                                break;
                            }
                            case "GERENTE": {
                                cred.setGerente(e);
                                break;
                            }
                        }
                    }
                }
                list.add(cred);
            }
        } catch (Exception e) {
            new Statics.ExceptionManager().saveDump(e, "Error en metodo cargarCreditos", Main.isProduccion);
        }
        return list;
    }

    public ArrayList<RenglonCredito> getRenglonesCredito(int creditoId) {
        String SQL = "SELECT * FROM renglon_credito "
                + "INNER JOIN articulos ON articulos.id = producto_id "
                + "WHERE credito_id = " + creditoId;
        ResultSet rs2 = conexion.EjecutarConsultaSQL(SQL);
        return null;
    }

    public Cuota getCuotaByCreditoId(int id) {
        Cuota c = new Cuota();
        c=null;
        try {
            String SQL = "SELECT * FROM cuota WHERE cuota.id=" + id;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            
            if (rs.first()) {
                c.setId(rs.getInt("id"));
                c.setCantidad(rs.getInt("cantidad"));
                c.setPorcentajeExtra(rs.getFloat("porcentaje_extra"));
                c.setTipo(rs.getString("tipo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    /**
     *
     * @param creditoSelected
     * @return id of the successful credit inserted- or -1 if failed
     */
    public int insertCreditoNuevo(Credito creditoSelected) {
        String fechaAprob = null;
        int idNuevoCredito = -1;
        //mete fecha de aprobacion
        if (creditoSelected.getFecha_aprobacion() != null) {
            fechaAprob = "'" + creditoSelected.getFecha_aprobacion() + "'";
        }
        //te va a dar null por que no lo guarda todavia
        String SQL = "INSERT INTO `credito`"
                + "(`cliente_id`, `comercio_id`, `fecha_aprobacion`, "
                + "`estado`, `cuota_id`, "
                + "`importe_total`, `importe_cuota`, `importe_pri_cuota`, "
                + "`importe_deuda`, `importe_credito`, `anticipo`, "
                + "`importe_ult_cuota`, `comision`, `tipo`, "
                + "`observacion`, `venc_pri_cuota`, `zonaId`, "
                + "`fecha_solicitud`, `fecha_credito`, `nro_solicitud`, "
                + "`cant_cuotas`,`conyugue_id`,`direccionActual_id`) VALUES "
                + "(" + creditoSelected.getCliente().getId() + "," + creditoSelected.getComerce().getId() + "," + fechaAprob + ",'"
                + creditoSelected.getEstado() + "'," + creditoSelected.getPlan().getId() + ","
                + creditoSelected.getImporte_total() + "," + creditoSelected.getImporte_cuota() + "," + creditoSelected.getImporte_pri_cuota() + ","
                + creditoSelected.getImporte_deuda() + "," + creditoSelected.getImporte_credito() + "," + creditoSelected.getAnticipo() + ","
                + creditoSelected.getImporte_ult_cuota() + "," + creditoSelected.getComision() + ",'" + creditoSelected.getTipo() + "','"
                + creditoSelected.getObservacion() + "'," + creditoSelected.getVenc_pri_cuota() + ", " + creditoSelected.getZonaId() + " ,'"
                + creditoSelected.getFecha_solicitud() + "','" + creditoSelected.getFecha_credito() + "'," + creditoSelected.getSolicitud_id() + ","
                + creditoSelected.getCant_cuotas() + "," + creditoSelected.getConyugue_id() + "," + creditoSelected.getDireccion_id() + ")";

        // si lo dejo asi, le asigna el renglon_credito a la solicitud
        if (creditoSelected.getRenglones().size() > 0) {
            //tiene al menos 1 renglon
            // pero lo esta guardando con el id anterior
            ArrayList<RenglonCredito> rc = creditoSelected.getRenglones();
            SQL = "INSERT INTO `renglon_credito`(`sub_total`, `importe_cuota`, `nroSerie`, `credito_id`, `producto_id`, `costo_prod`, `cantidad`) VALUES";
            for (int i = rc.size() - 1; i > 0; i--) {
                SQL += " (" + rc.get(i).getSubTotal() + "," + rc.get(i).getImporte_cuota() + ",'" + rc.get(i).getNroSerie() + "'," + creditoSelected.getId() + "," + rc.get(i).getP().getId() + "," + rc.get(i).getCosto() + "," + rc.get(i).getCantidad() + "),";
            }
            SQL += " (" + rc.get(0).getSubTotal() + "," + rc.get(0).getImporte_cuota() + ",'" + rc.get(0).getNroSerie() + "'," + creditoSelected.getId() + "," + rc.get(0).getP().getId() + "," + rc.get(0).getCosto() + "," + rc.get(0).getCantidad() + ")";
            conexion.EjecutarOperacion(SQL);
        }

        //actualizamos el estado de la solicitud
        SQL = "UPDATE `credito` SET estado = \"EMITIDA\" WHERE id = " + creditoSelected.getId();
        conexion.EjecutarOperacion(SQL);
        //obtenemos el id del credito insertado
        //ASI ESTABA, CONSIDERO QUE RECUPERA EL ID DE LA SOLICITUD
        //SQL = "SELECT MAX(id) as LastId FROM `credito` WHERE credito.id="+creditoSelected.getId();
        //DEBERIA TRAER EL ID DEL ULTIMO CREDITO
        SQL = "SELECT MAX(id) as LastID FROM credito";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if (rs.first()) {
                //devuelve el id del credito si lo pudo cargar
                if (creditoSelected.getRenglones().size() > 0) {
                    //tiene al menos 1 renglon
                    // ahora lo vamos a guardar con el id del credito nuevo
                    //ESTO PUEDE FALLAR AL TENER MULTIPLES USUARIOS- AGREGAR MAS OPCIONES COMO DNI CLIENTE, ETC ETC
                    idNuevoCredito = rs.getInt("LastId");
                    ArrayList<RenglonCredito> rc = creditoSelected.getRenglones();
                    SQL = "INSERT INTO `renglon_credito`(`sub_total`, `importe_cuota`, `nroSerie`, `credito_id`, `producto_id`, `costo_prod`, `cantidad`) VALUES";
                    for (int i = rc.size() - 1; i > 0; i--) {
                        SQL += " (" + rc.get(i).getSubTotal() + "," + rc.get(i).getImporte_cuota() + ",'" + rc.get(i).getNroSerie() + "'," + creditoSelected.getId() + "," + rc.get(i).getP().getId() + "," + rc.get(i).getCosto() + "," + rc.get(i).getCantidad() + "),";
                    }
                    SQL += " (" + rc.get(0).getSubTotal() + "," + rc.get(0).getImporte_cuota() + ",'" + rc.get(0).getNroSerie() + "'," + idNuevoCredito + "," + rc.get(0).getP().getId() + "," + rc.get(0).getCosto() + "," + rc.get(0).getCantidad() + ")";
                    conexion.EjecutarOperacion(SQL);
                    return idNuevoCredito;
                }
                //return rs.getInt("LastId"); tira error

                //inserto la aprobacion del vendedor al credito
                System.out.println("Estoy metiendo el id de empleado: " + creditoSelected.getVendedor().getId());
                SQL = "INSERT INTO aprobaciones(credito_id, empleado_id) VALUES(" + idNuevoCredito + " , " + creditoSelected.getVendedor().getId() + ")";

            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Error en metodo cargarCreditos", Main.isProduccion);
        }
        return -1;
    }

    public ArrayList<Credito> getCreditosCliente(int id) {
        ArrayList<Credito> creditos = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM credito WHERE state = 'ACTIVO' AND cliente_id = " + id;
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
                cred.setFecha_credito(rs.getTimestamp("fecha_credito"));
                cred.setVenc_credito(rs.getTimestamp("venc_credito"));
                cred.setFecha_aprobacion(rs.getTimestamp("fecha_aprobacion"));
                cred.setAnticipo(rs.getFloat("anticipo"));

                //Si no hay cobrador asignado, viene -1
                // muestra por pantalla un administrador
                if (rs.getInt("credito.cobrador_id") == -1) {
                    String SQL2 = "SELECT id,nombre FROM empleado WHERE tipo = 'ADMINISTRADOR'";
                    ResultSet aprobaciones = conexion.EjecutarConsultaSQL(SQL2);
                    if (aprobaciones.first()) {
                        Empleado e = new Empleado();
                        e.setId(aprobaciones.getInt("id"));
                        //habria que buscar el empleado
                        // e.setNombre(aprobaciones.getInt("empleado_id"));
                        e.setNombre("admin- pendiente");
                        cred.setCobrador(e);
                    }
                }

                //Empleado  = null;
                //cred.setCobrador(empAuxiliar); hice esto por que saque el cobrador
                creditos.add(cred);
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al recuperar creditos de un cliente", Main.isProduccion);
        }
        return creditos;
    }

    public boolean insertarRenglonPlanilla(Carton c) {
        boolean exito;
        conexion.transaccionCommit("quitarAutoCommit");
        String SQL = "INSERT INTO carton (credito_id, nro_planilla, cliente_id ,"
                + " cliente_nombre, cuotas_adeudadas, cuotas_aCobrar"
                + ", cant_cuotas_credito, deuda, importe_cuota, nro_cuota, vencimiento )"
                + " VALUES (" + c.getCredito_id() + ", " + c.getNro_planilla() + " , "
                + c.getCliente_id() + " , '" + c.getCliente_nombre() + "' , " + c.getCuotas_adeudadas()
                + " , " + c.getCuotas_aCobrar() + " , " + c.getCant_cuota_credito()
                + " , " + c.getDeuda() + " ," + c.getImporte_cuota()
                + " , " + c.getNro_cuota() + " , " + c.getVencimiento() + ")";
        int res = conexion.EjecutarOperacion(SQL); //inserto el cliente el cual ahora sera el cliente con id mas alto
        System.out.println("SQL en iNGRESO COBRANZA DAO EN METODO insertarRenglonPlanilla \n = " + SQL);
        if (res == 0) {
            exito = false;
        } else {
            exito = true;
        }
        if (exito) {
            conexion.transaccionCommit("commitear");
            conexion.transaccionCommit("activarCommit");
        } else {
            conexion.transaccionCommit("rollBack");
            conexion.transaccionCommit("activarCommit");
        }
        return exito;
    }

    public ArrayList<Carton> getCartonesCredito(int id) {
        ArrayList<Carton> cartones = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM carton WHERE state = 'ACTIVO' AND credito_id = " + id;
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
        ArrayList<Credito> creds = new ArrayList<>();
        try {
            String SQL = "SELECT credito.* FROM credito WHERE cliente_id = " + id + " AND cuota_id = " + plan.getId();
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
                cred.setFecha_credito(rs.getTimestamp("fecha_credito"));
                cred.setFecha_aprobacion(rs.getTimestamp("fecha_aprobacion"));
                cred.setAnticipo(rs.getFloat("anticipo"));
            }
            return null;
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al recuperar los creditos unificables de un cliente", Main.isProduccion);
        }
        return creds;
    }

    public int getCantCredPorPareja(int client, int conyugue) {
        String SQL = "SELECT COUNT(*) \"total\" FROM credito WHERE (cliente_id = " + client + " OR cliente_id = " + conyugue + ") AND tipo != 'SOLICITUD'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        try {
            if (rs.first()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            new Statics.ExceptionManager().saveDump(ex, "Se rompio al obtener el total de creditos de una pareja", Main.isProduccion);
        }
        return 0;
    }

    public boolean insertarSolicitud(int idConyugue, int idDireccion, int idClient, int idcomerce, String observacion, int nroSoli, int vendedorId, int cobradorId, int zonaId) {
        // si no tiene conyugue, en idConyugue trae un -1}
        String SQL = "INSERT INTO `credito`(`cliente_id`,`direccionActual_id`,"
                + "`conyugue_id`, `comercio_id`, "
                + "`observacion`,"
                + "`fecha_solicitud`,`nro_solicitud`,`tipo`, `zonaId`, `cobrador_id`) "
                + "VALUES (" + idClient + "," + idDireccion + ","
                + idConyugue + "," + idcomerce + ",'"
                + observacion + "','"
                + new Timestamp(System.currentTimeMillis()) + "'," + nroSoli + ", 'SOLICITUD', " + zonaId + "," + cobradorId + ")";
        //si no puede ejecutar la operacion, devuelve -1
        System.out.println("SQL al insertar solicitud tiene: /n" + SQL);
        int EjecutarOperacion = conexion.EjecutarOperacion(SQL); //TODO esto falla! agregar control extra

        if (EjecutarOperacion == -1) {
            return false;
        }

        if (vendedorId > 0) {
            //si se tiene el vendedor, le mando la aprobacion
            System.out.println();
            SQL = "SELECT MAX(id) as LastId FROM `credito`"; //TODO cambiar esto por dios!!!
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            try {
                if (rs.first()) {
                    // carga la aprobacion del ultimo credito
                    SQL = "INSERT INTO `aprobaciones`(`credito_id`, `empleado_id`, `fecha`, `estado`)"
                            + " VALUES (" + rs.getInt("LastId") + "," + vendedorId + ",'" + new Timestamp(System.currentTimeMillis()) + "'," + true + ")";
                    conexion.EjecutarOperacion(SQL);
                    System.out.println("En guardar solici, la aprobacion tiene: \n" + SQL);
                    return true;
                }
            } catch (SQLException ex) {
                new Statics.ExceptionManager().saveDump(ex, "Error al cargar el vendedor en la solicitud", Main.isProduccion);
            }
        }
        return true;
    }

    public void updateAprobado(int idCred, int idEmp, boolean estado, boolean b, Timestamp FechaAprob, Timestamp venc_pri_cuota) {
        String SQL = "INSERT INTO `aprobaciones`(`credito_id`, `empleado_id`, `fecha`, `estado`)"
                + " VALUES (" + idCred + "," + idEmp + ",'" + FechaAprob + "'," + estado + ")";
        conexion.EjecutarOperacion(SQL);
        if (b) {
            SQL = "UPDATE `credito` SET estado = \"APROBADO\", fecha_aprobacion = '" + FechaAprob + "' , venc_pri_cuota = '" + venc_pri_cuota + "' WHERE id =" + idCred;
            conexion.EjecutarOperacion(SQL);
        }
        if (!estado) {
            SQL = "UPDATE `credito` SET estado = \"RECHAZADO\" WHERE id =" + idCred;
            conexion.EjecutarOperacion(SQL);
        }
    }

    public void updateCredito(Credito creditoSelected) {
        String SQL = "UPDATE `credito` SET "
                + "`fecha_aprobacion`=NULL, `estado`=\"PENDIENTE\","
                + "`cant_cuotas`=" + creditoSelected.getCant_cuotas() + ",`importe_total`=" + creditoSelected.getImporte_total() + ",`importe_cuota`=" + creditoSelected.getImporte_cuota() + ","
                + "`importe_pri_cuota`=" + creditoSelected.getImporte_pri_cuota() + ",`importe_deuda`=" + creditoSelected.getImporte_deuda() + ",`importe_credito`=" + creditoSelected.getImporte_credito() + ","
                + "`anticipo`=" + creditoSelected.getAnticipo() + ",`importe_ult_cuota`=" + creditoSelected.getImporte_ult_cuota() + ",`comision`=" + creditoSelected.getComision() + ","
                + "`tipo`=" + creditoSelected.getTipo() + ",`observacion`='" + creditoSelected.getObservacion() + "',`venc_pri_cuota`='" + creditoSelected.getVenc_pri_cuota() + "',"
                + "`zonaId`= " + creditoSelected.getZonaId() + ", `venc_credito` = '"
                + creditoSelected.getVenc_credito() + "', "
                + "`cuota_id`=" + creditoSelected.getPlan().getId() + " WHERE `id`=" + creditoSelected.getId();
        conexion.EjecutarOperacion(SQL);
        if (creditoSelected.getRenglones().size() > 0) {
            ArrayList<RenglonCredito> rc = creditoSelected.getRenglones();
            SQL = "DELETE FROM `renglon_credito` WHERE credito_id = " + creditoSelected.getId();
            conexion.EjecutarOperacion(SQL);
            SQL = "INSERT INTO `renglon_credito`(`sub_total`, `importe_cuota`, `nroSerie`, `credito_id`, `producto_id`, `costo_prod`, `cantidad`) VALUES";
            for (int i = rc.size() - 1; i > 0; i--) {
                SQL += " (" + rc.get(i).getSubTotal() + "," + rc.get(i).getImporte_cuota() + ",'" + rc.get(i).getNroSerie() + "'," + creditoSelected.getId() + "," + rc.get(i).getP().getId() + "," + rc.get(i).getCosto() + "," + rc.get(i).getCantidad() + "),";
            }
            SQL += " (" + rc.get(0).getSubTotal() + "," + rc.get(0).getImporte_cuota() + ",'" + rc.get(0).getNroSerie() + "'," + creditoSelected.getId() + "," + rc.get(0).getP().getId() + "," + rc.get(0).getCosto() + "," + rc.get(0).getCantidad() + ")";
            conexion.EjecutarOperacion(SQL);
        }
    }

    public ArrayList<Credito> getCreditosSinME() {
        //sera sin mercaderia entregada?
        ArrayList<Credito> list = new ArrayList();
        String SQL = "SELECT * FROM `credito`"
                + "INNER JOIN cliente ON cliente_id = cliente.id "
                + "INNER JOIN comercio ON credito.comercio_id = comercio.id "
                + "LEFT JOIN cuota ON cuota_id = credito.cuota_id "
                + "WHERE `credito`.`tipo` != \"SOLICITUD\" AND `credito`.`estado` = \"APROBADO\" AND `credito`.`mercaderia_entregada` = FALSE";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarCreditos(rs);
    }

    public ArrayList<Credito> getCreditosPendienteCobro() {
        ArrayList<Credito> lista = new ArrayList();
        String SQL = "SELECT * FROM `aprobaciones` INNER JOIN credito ON "
                + " credito.id = aprobaciones.credito_id\n credito "
                + " LEFT JOIN carton ON credito.id = carton.credito_id"
                + " INNER JOIN cuota ON cuota.id = credito.cuota_id\n "
                + " INNER JOIN comercio ON credito.comercio_id= comercio.id "
                + " INNER JOIN cliente ON cliente.id = credito.cliente_id "
                + " WHERE carton.id = null AND credito.estado = 'APROBADO'";
        System.out.println("en creditos dao, en recuperarCreditosPEndiente Cobro la consulta fue /n" + SQL);
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return this.cargarCreditos(rs);
    }

    public boolean generarCarton(Credito creditoSelected) {
        // controlar: las cuotas adeudadas cuando hace un adelanto
        boolean exito = false;
        int idPlanilla;
        this.updateCredito(creditoSelected);
        Carton c = new Carton();

        //ojo que el metodo que llamo para guardar esto por primera vez, solo carga
        //algunos campos, los que necesito
        /**
         * carton (credito_id, nro_planilla, cliente_id ," + " cliente_nombre,
         * cuotas_adeudadas, cuotas_aCobrar" + ", cant_cuotas_credito, deuda,
         * importe_cuota, nro_cuota, vencimiento )"
         */
        c.setCuotas_adeudadas(0);
        c.setCliente_id(creditoSelected.getCliente().getId());
        c.setCliente_nombre(creditoSelected.getCliente().getNombre());
        c.setCuotas_aCobrar(creditoSelected.getCant_cuotas());
        c.setCant_cuota_credito(creditoSelected.getCant_cuotas());
        c.setDeuda(creditoSelected.getImporte_deuda());
        c.setImporte_cuota(creditoSelected.getImporte_cuota());
        c.setNro_cuota(1);
        c.setVencimiento(creditoSelected.getVenc_pri_cuota());
        exito = guardarCarton(c);

        return exito;
    }

    public boolean guardarCarton(Carton c) {

        boolean exito = false;
        String SQL = "INSERT INTO carton VALUES (credito_id, nro_planilla, cliente_id,"
                + "cliente_nombre, ultimo_pago, cuotas_adeudadas, cuotas_aCobrar,"
                + " cant_cuotas_credito, importe_cancelado, importe_ingresado,"
                + " estado, deuda, importe_cuota, nro_cuota, vencimiento) "
                + "VALUES (" + c.getCredito_id() + " , " + c.getNro_planilla() + " , "
                + c.getCliente_id() + " , '" + c.getCliente_nombre() + "' ," + c.getUltimo_pago()
                + " , " + c.getCuotas_adeudadas() + " , " + c.getCuotas_aCobrar() + " , "
                + c.getCant_cuota_credito() + " , " + c.getImporte_cancelado() + " , '"
                + c.getEstado() + "' , " + c.getDeuda() + " , " + c.getImporte_cuota() + " , "
                + c.getNro_cuota() + " , " + c.getVencimiento() + "  )";
        System.out.println("EN creditos dao, guardar carton, el insert fue : \n " + SQL);
        int res = conexion.EjecutarOperacion(SQL);
        if (res == 0) {
            return false;
        } else {
            exito = true;
        }
        return exito;
    }

    public JasperViewer generarReporteCarton(int credito_id) {
        JasperReport reporte = null;
        JasperViewer view = null;
        Connection con = (Connection) conexion.getConexion();
        String path = "src\\Reportes\\Carton.jasper";
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint;
            Map parametros = new HashMap();
            parametros.put("credito_id", credito_id);

            jprint = JasperFillManager.fillReport(reporte, parametros, con);
            view = new JasperViewer(jprint, false);
        } catch (JRException ex) {
            new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
        }
        return view;
    }

    /**
     *
     * @param nro
     * @return true if nro solicitud doesn't exist
     */
    public boolean controlNroSolicitud(int nro) {
        try {
            String SQL = "SELECT * FROM credito WHERE tipo='SOLICITUD' AND nro_solicitud = " + nro;
            ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
            if (rs.first()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
