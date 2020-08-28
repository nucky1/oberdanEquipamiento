/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Producto;
import Views.ProductosView;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author demig
 */
public class ProductoDAO {
    private static ProductoDAO ProductoDAO = null;
    private ProductosView view;
    private Statics.Conexion conexion = Statics.Conexion.getInstance();
    public ProductoDAO(){}
    public static ProductoDAO getInstance(){
        if(ProductoDAO == null) ProductoDAO = new ProductoDAO();
        return ProductoDAO;
    }
    
    public void setVista(ProductosView view){
        this.view = view;
    }
    
    public List<Producto> cargarProductos(ResultSet rs){
        List<Producto> list =  new ArrayList<>();
        try{
            Producto p = new Producto();
            while(rs.next()){   
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCodigoBarra(rs.getString("codigo_barra"));
                p.setObservaciones(rs.getString("observaciones"));
                p.setNombreRubro(rs.getString("nombreRubro"));
                p.setIdProductoRubro(rs.getInt("idRubro"));
                p.setCostoFlete(rs.getFloat("costo_flete"));
                p.setPrecioCosto(rs.getFloat("precioCosto"));
                p.setStock(rs.getInt("stock_existente"));
                p.setStockMin(rs.getInt("stock_minimo"));
                p.setIdProveedorActual(rs.getInt("proveedor_id"));
                list.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return (list);
    }
    public List<Producto> buscarProducto(String atributo, String valor) {
        String SQL = "SELECT articulo.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro, MAX(art_stock.precio_compra),proveedores.proveedor as precioCosto"
               + " FROM proveedores, articulo, art_rubro, art_stock"
               + " WHERE articulo." + atributo+" like '%"+valor+"%'"
               + " AND articulo.rubro_id = art_rubro.id"
               + " AND articulo.id = art_stock.producto_id"
               + " AND articulo.proveedor_id = proveedores.id"
               + " AND articulo.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }

    
    public List<Producto> buscarProducto(int id){
        String SQL = "SELECT articulo.*, art_rubro.id as idRubro,art_rubro.nombre as nombreRubro, MAX(art_stock.precio_compra),proveedores.proveedor as precioCosto"
               + " FROM proveedores, articulo, art_rubro, art_stock"
               + " WHERE articulo.id LIKE '"+id+"%'"
               + " AND articulo.rubro_id = art_rubro.id"
               + " AND articulo.id = art_stock.producto_id"
               + " AND articulo.proveedor_id = proveedores.id"
               + " AND articulo.state = 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        return cargarProductos(rs);
    }

      
    
    
    public Producto getItemInverntarioActivo(int id) {
        Producto p = null;
        try {           
            ResultSet rs = Modelo.GetItemInverntarioActivo(id);
            while(rs.next()){
                p = new Producto();
                p= procesarResultSetProducto(rs);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } return p;
    }    
    
    public List<Producto> searchItemInverntarioActivo(String nombre) {
      Producto p = null;
        List<Producto> list =  new ArrayList<>();
        try {
            ResultSet rs = Modelo.BuscarProductosFact("producto_nombre", nombre);
            if (rs.next()) {
                p= procesarResultSetProducto(rs);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void buscarDevolucion(int id){
        ResultSet rs = Modelo.BuscarDevoluciones(id);
        cargarDevoluciones(rs);
    }
    
    public void buscarDevolucion(String atributo, String valor){
        ResultSet rs = Modelo.BuscarDevoluciones(atributo,valor);
        cargarDevoluciones(rs);
    }
    
    public ResultSet traetDatosStockDevo(int id){
        return Modelo.getDatosStockDevoluciones(id);
    }

     
    public ResultSet traerDatosDevoluciones(int id){
        return Modelo.getDatosDevolucion(id);
    }
    
    public float traerImporteMesPasado(int id){
        ResultSet rs= Modelo.getImporteMesPasado(id);
        float aux=0;
        try{
               if(rs.next()){
                   aux = rs.getFloat("importeTotal");
               }
        }catch(SQLException | NullPointerException ex){
            System.out.println("ups - 111 ProductoDAO");
            
        }
        return aux;
    }
     
    public int calcularStockDevoluciones(int id){
        ResultSet rs = Modelo.getStockDevolucion(id);
        int totalVentas = 0 ;
        try {
            while(rs.next()){
                totalVentas= rs.getInt("totalVentas");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return totalVentas;
    }
    
    
    /**
     * Añade las devoluciones a una lista para llenar la tabla con view.cargarDevoluciones(LISTA_DE_DEVOLUCIONES)
     * @param rs 
     */
    private void cargarDevoluciones(ResultSet rs){
        List<Producto> list =  new ArrayList<>();
        try{
            Producto p;
            while(rs.next()){
                p = procesarResultSetDevolucion(rs);
                list.add(p);
            }
            view.cargarDevoluciones(list);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
    public List<Producto> searchProductName(String name){
        Producto p = null;
        List<Producto> list =  new ArrayList<>();
        try {
            ResultSet rs = Modelo.BuscarProductos("producto_nombre", name);
            if (rs.next()) {
                p= procesarResultSetProducto(rs);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    /**
     * crea un producto con estado "2" que significa que es una devolucion
     * @param rs
     * @return La devolucion traida desde la base de datos los datos (ID, NOMBRE) 
     */
    private Producto procesarResultSetDevolucion(ResultSet rs){
        Producto p = new Producto();
        try {    
            p.setId(rs.getInt("devolucion_id"));
            p.setNombre(rs.getString("producto_nombre"));
            p.setEstado(2);
            p.setCombo(false); 
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    } 
    
    public List<Impuesto> cargarImpuestos() {
        List<Impuesto> list = new ArrayList<>();
        ResultSet rs = Main.Modelo.GetImpuestos();
        try{
            while(rs.next()){
                Impuesto i = new Impuesto();
                i.setId(rs.getInt("impuesto_id"));
                i.setNombre(rs.getString("impuesto_nombre"));
                i.setPorcentaje(rs.getFloat("impuesto_valor"));
                list.add(i);
            }
        }catch(Exception ex){}
        return list;
    }

    
    public void cargarProveedores(int idProd) {
     /*   ResultSet rs = Main.Modelo.GetProveedoresProd(idProd);
        List<Proveedor> list = new ArrayList<>();       
        try{
            while(rs.next()){
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("proveedores_id"));
                p.setNombre(rs.getString("proveedores_nombre"));
                p.setDireccion(rs.getString("proveedores_direccion"));
                list.add(p);
            } 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        view.cargarProveedores(list);
       */ 
    }
    
    /**
     * primero inserta en la 1tabla de productos, si la inserto bien, la inserta en la de 2devoluciones
     * y tambien si inserto bien, inserta el stock en la tabla de 3stockDevolucion
     * @param devo  Devolucion a Insertar
     * @param porcentaje    Porcentaje de devolucion    
     * @param prodAsociado  producto a devolver
     */
     
    public void nuevaDevolucion(Producto devo, int porcentaje, int prodAsociado){
        int res = Main.Modelo.InsertarDevolucion(devo);
        if(res>0){
            res = Main.Modelo.InsertarEnDevoluciones(devo,porcentaje,prodAsociado);
            if(res>0){
                try{
                    ResultSet rs = Main.Modelo.PreventistasActivos();
                    ArrayList<Integer> aux = new ArrayList<>();
                    while(rs.next()){
                        aux.add(rs.getInt("preventista_id"));
                    }
                    System.out.println("entra o tira catch ? ");
                    for(Integer i: aux){
                        res = Main.Modelo.InsertarEnStockDevolucion(i,devo.getId());
                        if (res ==0){
                            System.out.println("fallo insertar en stock devoluciones");
                            break;
                        }
                    }
                }catch(SQLException e){
                    e.getMessage();
                }
                if(res>0){
                    view.devolucionCargada();
                }else{
                    view.productoNoCargado();                                    
                }
            }else{
                System.out.println("fallo en la tabla 'devolucion' ");
                view.productoNoCargado();                
            }   
        }else
            view.productoNoCargado();
    }
    
    public void editarDevosDisponibles(int cant, String nomBprev,int idDevo){
        int res = Main.Modelo.EditarStockADevolver(cant,nomBprev,idDevo);
        if(res >0){
            Vista.lbl_estado.setText("Editado correctamente");
         }else
            Vista.lbl_estado.setText("No editado");
     }
    
    public void actualizarProductoADevolucion(Producto devo,int porcentaje,int prodAsociado) {
        int res = Main.Modelo.ActualizarProductoADevolucion(devo);
        if(res >0){
            res = Main.Modelo.InsertarEnDevoluciones(devo,porcentaje,prodAsociado);
            if(res>0){
                try{
                    ResultSet rs = Main.Modelo.PreventistasActivos();
                    ArrayList<Integer> aux = new ArrayList<>();
                    while(rs.next()){
                        aux.add(rs.getInt("preventista_id"));
                    }
                    System.out.println("entra o tira catch ? ");
                    for(Integer i: aux){
                        res = Main.Modelo.InsertarEnStockDevolucion(i,devo.getId());
                        if (res ==0){
                            System.out.println("fallo insertar en stock devoluciones");
                            break;
                        }
                    }
                }catch(SQLException e){
                    e.getMessage();
                }
                if(res>0){
                    view.devolucionCargada();
                }else{
                    view.productoNoCargado();                                    
                }
            }else{
                System.out.println("fallo en la tabla 'devolucion' ");
                view.productoNoCargado();                
            }   
        }
        else
            view.productoNoActualizado();
    }
    
    public void nuevoProducto(Producto prod, List<Integer> proveedores) {
        int aux;
        int res = Main.Modelo.InsertarProducto(prod);
        //int id = Main.Modelo.ProductosMaxId();
        if(res == 1){
            for(int i = 0; i < proveedores.size(); i++){
                aux = Main.Modelo.InsertarProdCatalogo(prod.getId(), proveedores.get(i));
            }
            view.productoCargado();
        }else{
            view.productoNoCargado();
        }
    }

    
    public void actualizarProducto(Producto p) {
        int res = Main.Modelo.ActualizarProducto(p);
        //actualizarCombo(p);
        //actualizarProductoCombos(p);
        if(res >0){
          //  actualizarCombosRelacionados(p);
            view.productoActualizado();
        }
        else
            view.productoNoActualizado();
    }
    
    
    public int eliminarDevolucion(int id) {
        int res = Main.Modelo.EliminarDevolucion(id);
        if(res > 0){
            return 1;
        }
        else
            return 0;
    }
    
    
    public int eliminarProducto(Producto p) {
        int res = Main.Modelo.EliminarProducto(p);
        //actualizarCombo(p);
        if(res > 0){
            return 1;
        }
        else
            return 0;
    }

    
    public float getStock(int id) {
        ResultSet res = Main.Modelo.GetProductoStock(id);
        try{
            res.next();
            return res.getFloat("producto_stock"); 
        }catch(Exception ex){
            System.out.println("ERROR AL RECUPERAR STOCK DE PRODUCTO ID: "+id);
            ex.printStackTrace();
        }
        return -1f;
    }
    
    
    public float getStockCombo(int id) {
        List<ComboDetalle> listCombo = controller_combos.getComboDetalle(id);
        float stock = 0f;
        if(listCombo.size()>0){
            float aux= getStock(listCombo.get(0).getProd().getId());
            stock = aux/listCombo.get(0).getCantidad();

            for(int i = 1; i < listCombo.size(); i++){
                aux= getStock(listCombo.get(i).getProd().getId());
                float stockI = aux/listCombo.get(i).getCantidad();

                if(stockI < stock) stock = stockI;
            }                
        }
        return stock;
    }
    
    
    
    
    public void setStock(int id_producto, float stock) {
        int res;
        res = Main.Modelo.SetProductStock(id_producto,stock);
        if(res >0)
            view.stockActualizado();
        else
            view.errorActualizarStock();
        if (false){ //controlarDiaIniciado()){
            Main.Modelo.SetProductStockSync(id_producto,stock);
        }
    }
    
    
    public int addStock(int id_producto, float stock) {
        int res;
        res = Main.Modelo.UpdateProductStock(id_producto,stock);
        if (false){ //controlarDiaIniciado()){
            Main.Modelo.UpdateProductStockSync(id_producto,stock);
        }
        return res;
    }
    
    private boolean controlarDiaIniciado() {
        ResultSet rs = Modelo.GetSyncSistemaActivo();
        try {
            return (rs.next()); 
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("pasaron cosas con el control de dia iniciado ");
        return false;
    }
    private int actualizarProductoCombos(Producto p){
        float flete = 1+(float)p.getCostoFlete()/100;
        
        float precio = p.getPrecioCosto();
        
        float margenA = 1+(float)p.getMargenA()/100;
        float margenB = 1+(float)p.getMargenB()/100;
        float margenC = 1+(float)p.getMargenC()/100;
        float margenD = 1+(float)p.getMargenD()/100;
        
        
        
        float auxA = precio*flete*margenA;
        float auxB = precio*flete*margenB;
        float auxC = precio*flete*margenC;
        float auxD = precio*flete*margenD;
        
        p.setPrecioCosto(precio);
        p.setPrecioVentaA(auxA);
        p.setPrecioVentaB(auxB);
        p.setPrecioVentaC(auxC);
        p.setPrecioVentaD(auxD);
        
        
        int res = Main.Modelo.ActualizarProducto(p);
        actualizarCombo(p);
        return res;
    }
    
    private void actualizarCombo(Producto p){
        //BUSCAR SI EL PRODUCTO ESTABA EN UN COMBO
        ResultSet rs = Main.Modelo.BuscarProductoCombo(p.getId());
        List<Integer> combos = new ArrayList<>();
        try{
            while(rs.next()){
                combos.add(rs.getInt("combosDetalle_idCombo"));             
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        for(int i = 0; i < combos.size();i++){
            //modificarCombo(combos.get(i));
        }
        
    }
    
    
    
    private float CargarComboDetalle(int idC) {
        ResultSet rs = Main.Modelo.GetComboDetalle(idC);
        List<ComboDetalle> list = new ArrayList<>();
        float precio = 0;
        try{
            while(rs.next()){
                ComboDetalle cd = new ComboDetalle();
                cd.setId(rs.getInt("combosDetalle_id"));
                cd.setIdCombo(idC);
                cd.setCantidad(rs.getFloat("combosDetalle_cantidad"));
                Producto p = new Producto();
                p.setId(rs.getInt("producto_id"));
                p.setNombre(rs.getString("producto_nombre"));
                p.setPrecioCosto(rs.getFloat("producto_precioCosto"));
                p.setPrecioVentaA(rs.getFloat("producto_precioVentaA"));
                p.setPrecioVentaB(rs.getFloat("producto_precioVentaB"));
                p.setPrecioVentaC(rs.getFloat("producto_precioVentaC"));
                p.setPrecioVentaD(rs.getFloat("producto_precioVentaD"));                
                precio += (p.getPrecioCosto() * cd.getCantidad());
                cd.setProd(p);
                list.add(cd);

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return precio;
    }

    
    
    public float getUnidadesVendidas(int combo, Date fecha_inicio, Date fecha_fin, int id_prod) {
        float unidades = 0f;
        ResultSet rs = Main.Modelo.UnidadesVendidas(combo, fecha_inicio, fecha_fin, id_prod);
        try{
           while(rs.next()){
               unidades = (rs.getFloat("cant1")+rs.getFloat("cant2"));
           }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return unidades;
    }
    
    
    public float getCotizacionStock() {
        float importe = 0f;
        ResultSet rs = Main.Modelo.StockTotalProd();
        
        try{
            while(rs.next()){
                importe += (rs.getFloat("total_producto"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return importe;
    }

    
    public List<ProductoStock> getTopProducto() {
        List<ProductoStock> list = new ArrayList<>();
        ResultSet rs = Main.Modelo.BuscarProdMasComprados(0);
        
        try{
            while(rs.next()){
                ProductoStock p = new ProductoStock();
                p.setId(rs.getInt("facturaDetalle_idProd"));
                p.setNombre(rs.getString("producto_nombre"));
                p.setStock(rs.getFloat(3));
                list.add(p);                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    private void actualizarCombosRelacionados(Producto p) {
       //List<ComboDetalle> listDetalle;
       
    }

    
    public List<Producto> multiSearchProductName(String name) {
        ResultSet rs = Modelo.BuscarProductos("producto_nombre",name);
        List<Producto> list =  new ArrayList<>();
        try{
            Producto p;
            while(rs.next()){
                p = procesarResultSetProducto(rs);
                list.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    
    public List<String> buscarProductoEnCombos(int id) {
        ResultSet rs = Modelo.GetCombosCoincidencia(id);
        List<String> list = new ArrayList<>();
        try {
            while(rs.next()){
               list.add(rs.getString("productos.producto_nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBuscadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    
    public ResultSet productoEliminado(int prod) {
        return Main.Modelo.BuscarProdEliminado(prod);
    }
    public void cargarCatalogo(int idProv) {
        String SQL = "SELECT producto_id, producto_nombre "
               + "FROM productos " 
               + "WHERE producto_estado = 1 AND producto_idProveedorActual =" + idProv;
        ResultSet rs = Main.conexion.EjecutarConsultaSQL(SQL);
        List<Producto> list = new ArrayList<>();
        
        try{
            Producto p;
            while(rs.next()){
                p = new Producto();
                p.setId(rs.getInt("producto_id"));
                p.setNombre(rs.getString("producto_nombre"));
                list.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        view.cargarCatalogo(list);
    }

    
    public void agregarProdCatalogo(int idProv, int idProd) {
        int res = Main.Modelo.InsertarProdCatalogo(idProd, idProv);
        if(res>0)
            view.prodAgregado();
        else
            view.prodNoAgregado();
    }

    
    public void borrarProdCatalogo(int idProv, int idProd) {
        int res = Main.Modelo.BorrarProdCatalogo(idProd);
        if(res>0)
            view.prodBorrado();
        else
            view.prodNoAgregado();
    }
}
