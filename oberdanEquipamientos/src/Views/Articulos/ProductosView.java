/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Articulos;

import DAO.CuotasDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.ProveedoresDAO;
import DAO.RubroDAO;
import Models.*;
import static Statics.Funciones.getSelection;
import Statics.MiRenderer;
import Views.Proveedores.JD_Proveedor_Buscador;
import Views.Main;
import Views.principal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author demig
 */
public class ProductosView extends javax.swing.JPanel {
    private Pestaña1_dinamica Pestaña1_dinamica;
    private List<Producto> listProductos; // lista setteada en buscarproductos...
    private List<Producto> listDevoluciones; // lista setteada en buscarDevoluciones ...
    private ProveedoresDAO proveedoresDAO;
    private RubroDAO rubroDAO;
    private CuotasDAO cuotasDAO;
    private ProductoDAO productoDAO;
    private Pestaña3_dinamica pedidos;
    private Pestaña4_dinamica PantallaStock;
    private JD_Producto_Nuevo productoNuevo;
    private JD_Producto_editarStock editarStock;
    private boolean modificarTrue = false;
    /**
     * Creates new form productos
     */
    public ProductosView() {
        initComponents();
        Pestaña1_dinamica = new Pestaña1_dinamica(this);
        pedidos = new Pestaña3_dinamica();
        PantallaStock = new Pestaña4_dinamica(this);
        proveedoresDAO = ProveedoresDAO.getInstance();
        productoDAO = ProductoDAO.getInstance();
        cuotasDAO = CuotasDAO.getInstance();
        rubroDAO = RubroDAO.getInstance();      
        new Statics.TextPrompt("Nombre..", txtf_productos_nombre);
        new Statics.TextPrompt("Buscar..", txtf_productos_buscar);
        Pestaña1_dinamica.cambioBusqueda("");
    }
public void limpiarCamposInventario() {
        txtf_productos_nombre.setEnabled(false);
        txtf_productos_codigo.setEnabled(false);
        txtf_productos_costo.setEnabled(false);
        txtf_productos_codigo_barra.setEnabled(false);
        txtf_productos_costoFlete.setEnabled(false);
        txtf_productos_observaciones.setEnabled(false);
        txtf_productos_stockMinimo.setEnabled(false);
        txtf_iva.setEnabled(false);
        txtf_imp_int_fijo.setEnabled(false);
        txtf_impuesto_int.setEnabled(false);
        txtf_sobretasa_iva.setEnabled(false);
        txtf_productos_venta.setEnabled(false);
        txtf_productos_codigo_ean.setEnabled(false);
         txtf_productos_nombre.setText("");
         txtf_productos_costo.setText("");
         txtf_productos_codigo_barra.setText("");
         txtf_productos_costoFlete.setText("");
         txtf_productos_observaciones.setText("");
         txtf_iva.setText("");
         txtf_imp_int_fijo.setText("");
         txtf_impuesto_int.setText("");
         txtf_sobretasa_iva.setText("");
         txtf_productos_venta.setText("");
         txtf_productos_codigo_ean.setText("");
         stock.setText("");
         fecha_inicio.setCalendar(null);
         fecha_fin.setCalendar(null);
         txtf_productos_stockMinimo.setText("");
         txtf_productos_codigo.setText("");
         producto_Proveedor.removeAllItems();
         producto_Proveedor.setEnabled(false);
         Proveedor p = new Proveedor();
         p.setNombre("-No posee un proveedor asignado-");
         producto_Proveedor.addItem(p);
         producto_Rubro.removeAllItems();
         producto_Rubro.setEnabled(false);
         Rubro r = new Rubro();
         r.setNombre("-No posee un rubro asignado-");
         producto_Rubro.addItem(r);
         chk_venta_iva.setSelected(false);
         btn_Guardar.setEnabled(false);
         btn_Cancelar.setEnabled(false);
         btn_Modificar.setEnabled(true);
         btn_productoNuevo.setEnabled(true);
         btn_eliminar.setEnabled(true);
         btn_crearRubro.setEnabled(false);
         btn_crearPago.setEnabled(false);
         btn_producto_editarLote.setEnabled(false);
         btn_consultar_historial.setEnabled(false);
        DefaultTableModel aux =(DefaultTableModel) tabla_producto_precioVenta.getModel();
        aux.setNumRows(0);
        tabla_producto_precioVenta.setEnabled(false);
        Pestaña1_dinamica.cambioBusqueda(txtf_productos_buscar.getText());
     }
    public void limpiarCamposDevo(){
        Pestaña1_dinamica.devoSelect = null;
        jLbl_devo_prodAsociado.setText("Producto a devolver...");
        txtf_devo_codigo.setText("");
        txtf_devo_unidadesVendidas.setText("");
        txtf_devo_precioTotal.setText("");
        txtf_devo_porcentajeAdevolver.setText("");
        txtf_devo_devolucionesDisponibles.setText("");
        DefaultTableModel aux =(DefaultTableModel) tabla_devo_preventistas.getModel();
        aux.setNumRows(0);
        habilitarPanelNuevaDevo(false);
    }
    private boolean control(){
        if(txtf_nombreDevo.getText().isEmpty() || txtf_porcentaje.getText().isEmpty() ||txtf_codDevolucion.getText().isEmpty() || txtf_codigoProd.getText().isEmpty() ){
            return false;
        }System.out.println("asdhudsd");
        try {
            Integer.parseInt(txtf_porcentaje.getText());
            Integer.parseInt(txtf_codigoProd.getText());
            Integer.parseInt(txtf_codDevolucion.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void habilitarPanelNuevaDevo(boolean flag){
        txtf_codDevolucion.setEditable(true);
        txtf_codigoProd.setEditable(true);
        txtf_codigoProd.setText("");
        txtf_porcentaje.setText("");
        txtf_codDevolucion.setText("");
        txtf_nombreDevo.setText("");
        txtf_nombreDevo.setEnabled(flag);
        txtf_codDevolucion.setEnabled(flag); 
        txtf_porcentaje.setEnabled(flag);
        txtf_codigoProd.setEnabled(flag);
        btn_guardar_devolucion.setEnabled(flag);
        btn_eliminar_devolucion.setEnabled(!flag);
       // btn_editDevolucion.setEnabled(!flag);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupFiltroStock = new javax.swing.ButtonGroup();
        jDialogAñadirRubro = new javax.swing.JDialog();
        txtf_nuevo_rubro = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jDialogCrearNota = new javax.swing.JDialog();
        jScrollPane28 = new javax.swing.JScrollPane();
        tabla_nota_prodPedir = new javax.swing.JTable();
        rbtn_productos_nombre1 = new javax.swing.JRadioButton();
        rbtn_productos_codigo1 = new javax.swing.JRadioButton();
        txtf_nota_buscar = new javax.swing.JTextField();
        lbl_nota_proveedor = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jScrollPane29 = new javax.swing.JScrollPane();
        tabla_nota_prodPedido = new javax.swing.JTable();
        jLabel173 = new javax.swing.JLabel();
        txtf_nota_cantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtf_nota_total = new javax.swing.JTextField();
        btn_nota_agregar = new javax.swing.JButton();
        btn_nota_eliminar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        generarNota = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtf_nota_costo = new javax.swing.JTextField();
        jLabel175 = new javax.swing.JLabel();
        txtf_nota_nro = new javax.swing.JTextField();
        jDialogAñadirPlan = new javax.swing.JDialog();
        jLabel36 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        tipo_cuota = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        cantidad_cuota = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        porcentaje_cuota = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        dia_vencimiento = new javax.swing.JTextField();
        mes_vencimiento = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jDialogNuevoStock = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_stockPedido = new javax.swing.JTextField();
        txt_StockActual = new javax.swing.JTextField();
        txt_stockReservado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_stockIngresado = new javax.swing.JTextField();
        txt_precioCompra = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        date_fechaCompra = new com.toedter.calendar.JDateChooser();
        btn_cancelar = new javax.swing.JButton();
        btn_GuardarNuevoStock = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        tabla_productos = new javax.swing.JTabbedPane();
        panel_productos_detalle = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabla_productos_busqueda = new javax.swing.JTable();
        txtf_productos_buscar = new javax.swing.JTextField();
        rbtn_productos_codigo = new javax.swing.JRadioButton();
        rbtn_productos_nombre = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        btn_proveedores_imprimirTodo = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtf_productos_codigo = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtf_productos_nombre = new javax.swing.JTextField();
        jPanel84 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tabla_producto_precioVenta = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        chk_venta_iva = new javax.swing.JCheckBox();
        btn_crearPago = new javax.swing.JButton();
        producto_plan = new javax.swing.JComboBox<>();
        btn_Modificar = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        btn_producto_editarLote = new javax.swing.JButton();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel79 = new javax.swing.JLabel();
        stock = new javax.swing.JTextField();
        jPanel85 = new javax.swing.JPanel();
        fecha_inicio = new com.toedter.calendar.JDateChooser();
        fecha_fin = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        unidades_vendidas = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        btn_consultar_historial = new javax.swing.JButton();
        jLabel174 = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        btn_Guardar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        txtf_productos_costoFlete = new javax.swing.JTextField();
        txtf_productos_codigo_barra = new javax.swing.JTextField();
        txtf_productos_costo = new javax.swing.JTextField();
        jLabel176 = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel237 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtf_productos_observaciones = new javax.swing.JTextArea();
        jLabel56 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txtf_iva = new javax.swing.JTextField();
        txtf_sobretasa_iva = new javax.swing.JTextField();
        txtf_impuesto_int = new javax.swing.JTextField();
        txtf_imp_int_fijo = new javax.swing.JTextField();
        jLabel238 = new javax.swing.JLabel();
        txtf_productos_codigo_ean = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtf_productos_venta = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txtf_productos_stockMinimo = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jSeparator32 = new javax.swing.JSeparator();
        producto_Rubro = new javax.swing.JComboBox<>();
        btn_crearRubro = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        btn_productoNuevo = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        producto_Proveedor = new javax.swing.JComboBox<>();
        panel_devos = new javax.swing.JPanel();
        jPanelDevoluciones = new javax.swing.JPanel();
        txtf_devolucion_buscar = new javax.swing.JTextField();
        rbtn_devolucion_codigo = new javax.swing.JRadioButton();
        rbtn_devolucion_nombre = new javax.swing.JRadioButton();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabla_devos = new javax.swing.JTable();
        jPanelDevoluciones2 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLbl_devo_prodAsociado = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtf_devo_codigo = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jSeparator35 = new javax.swing.JSeparator();
        jScrollPane21 = new javax.swing.JScrollPane();
        tabla_devo_preventistas = new javax.swing.JTable();
        jLabel188 = new javax.swing.JLabel();
        jSeparator36 = new javax.swing.JSeparator();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        txtf_devo_unidadesVendidas = new javax.swing.JTextField();
        txtf_devo_precioTotal = new javax.swing.JTextField();
        txtf_devo_porcentajeAdevolver = new javax.swing.JTextField();
        txtf_devo_devolucionesDisponibles = new javax.swing.JTextField();
        jlbl_editDevo_disp = new javax.swing.JLabel();
        btn_nuevaDevolucion = new javax.swing.JButton();
        jPanelDevoluciones3 = new javax.swing.JPanel();
        btn_guardar_devolucion = new javax.swing.JButton();
        txtf_nombreDevo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        txtf_codDevolucion = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        txtf_porcentaje = new javax.swing.JTextField();
        jLbl_devo_nuevaDevo = new javax.swing.JLabel();
        txtf_codigoProd = new javax.swing.JTextField();
        btn_editDevolucion = new javax.swing.JButton();
        btn_eliminar_devolucion = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonNegativo = new javax.swing.JRadioButton();
        jRadioButtonExistente = new javax.swing.JRadioButton();
        jRadioButtonCompleto = new javax.swing.JRadioButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonImprimirStock = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTablaStock = new javax.swing.JTable();
        panel_pedidos = new javax.swing.JPanel();
        txtf_nota_proveedor = new javax.swing.JTextField();
        btn_lotes_prod_buscarprov = new javax.swing.JButton();
        jLabel165 = new javax.swing.JLabel();
        txtf_nota_prod_cod = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        txtf_nota_prod_stock = new javax.swing.JTextField();
        btn_factura_agregar = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        tabla_productos_pedido = new javax.swing.JTable();
        btn_factura_eliminar = new javax.swing.JButton();
        btn_lote_aceptar = new javax.swing.JButton();
        txtf_nota_prod_precio = new javax.swing.JTextField();
        jLabel169 = new javax.swing.JLabel();
        txtf_nota_prod_factura = new javax.swing.JTextField();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        combo_forma_pago = new javax.swing.JComboBox();
        date_nota_fecha_pago = new com.toedter.calendar.JDateChooser();
        jLabel172 = new javax.swing.JLabel();
        date_nota_fecha_factura = new com.toedter.calendar.JDateChooser();
        txtf_nota_prod_nombre = new javax.swing.JComboBox();
        rbtn_nota_prod_codigo = new javax.swing.JRadioButton();
        rbtn_nota_prod_prod = new javax.swing.JRadioButton();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jPanel81 = new javax.swing.JPanel();
        txtf_lote_prod_flete = new javax.swing.JTextField();
        jLabel211 = new javax.swing.JLabel();
        btn_nota_nueva = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabla_nota_PedidossProveedor = new javax.swing.JTable();
        jLabel177 = new javax.swing.JLabel();
        cbox_tipo_factura = new javax.swing.JComboBox();

        jDialogAñadirRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAñadirRubro.setBackground(new java.awt.Color(255, 255, 255));
        jDialogAñadirRubro.setMinimumSize(new java.awt.Dimension(343, 134));
        jDialogAñadirRubro.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtf_nuevo_rubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_nuevo_rubroActionPerformed(evt);
            }
        });
        jDialogAñadirRubro.getContentPane().add(txtf_nuevo_rubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 233, -1));

        jLabel38.setText("Nuevo:");
        jDialogAñadirRubro.getContentPane().add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jButton13.setText("Cancelar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jDialogAñadirRubro.getContentPane().add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jButton12.setText("OK");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jDialogAñadirRubro.getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 70, -1));

        jDialogCrearNota.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogCrearNota.setBackground(new java.awt.Color(255, 255, 255));

        tabla_nota_prodPedir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Stock", "Precio compra actual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_nota_prodPedir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_nota_prodPedirMouseClicked(evt);
            }
        });
        jScrollPane28.setViewportView(tabla_nota_prodPedir);

        rbtn_productos_nombre1.setText("Nombre");

        rbtn_productos_codigo1.setSelected(true);
        rbtn_productos_codigo1.setText("Código");

        txtf_nota_buscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_nota_buscarCaretUpdate(evt);
            }
        });
        txtf_nota_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_nota_buscarKeyTyped(evt);
            }
        });

        lbl_nota_proveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_nota_proveedor.setText("Nombre de Proveedor");

        jLabel167.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel167.setText("Seleccionar Productos");

        tabla_nota_prodPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Cantidad", "Costo", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_nota_prodPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_nota_prodPedidoMouseClicked(evt);
            }
        });
        jScrollPane29.setViewportView(tabla_nota_prodPedido);

        jLabel173.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel173.setText("Nota de pedido");

        txtf_nota_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_nota_cantidadKeyTyped(evt);
            }
        });

        jLabel7.setText("Cantidad");

        jLabel8.setText("Total $");

        btn_nota_agregar.setBackground(new java.awt.Color(0, 153, 0));
        btn_nota_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_nota_agregar.setText("+ ");
        btn_nota_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nota_agregarActionPerformed(evt);
            }
        });

        btn_nota_eliminar.setBackground(new java.awt.Color(204, 51, 0));
        btn_nota_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_nota_eliminar.setText("- ");
        btn_nota_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nota_eliminarActionPerformed(evt);
            }
        });

        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        generarNota.setText("Generar Nota");
        generarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarNotaActionPerformed(evt);
            }
        });

        jLabel9.setText("Precio costo $");

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel175.setText("N°");

        txtf_nota_nro.setEnabled(false);

        javax.swing.GroupLayout jDialogCrearNotaLayout = new javax.swing.GroupLayout(jDialogCrearNota.getContentPane());
        jDialogCrearNota.getContentPane().setLayout(jDialogCrearNotaLayout);
        jDialogCrearNotaLayout.setHorizontalGroup(
            jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_nota_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                        .addComponent(jLabel167)
                        .addGap(374, 374, 374)
                        .addComponent(jLabel173)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel175)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_nota_nro, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_nota_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                        .addComponent(rbtn_productos_codigo1)
                                        .addGap(2, 2, 2)
                                        .addComponent(rbtn_productos_nombre1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtf_nota_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtf_nota_costo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8)
                                .addComponent(btn_nota_agregar))
                            .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel8)
                                .addGap(10, 10, 10)
                                .addComponent(txtf_nota_total, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(316, 316, 316)
                                .addComponent(btn_nota_eliminar)))))
                .addGap(0, 17, Short.MAX_VALUE))
            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generarNota)
                .addContainerGap())
        );
        jDialogCrearNotaLayout.setVerticalGroup(
            jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lbl_nota_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel167, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf_nota_nro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addComponent(txtf_nota_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbtn_productos_codigo1)
                                            .addComponent(rbtn_productos_nombre1)
                                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel8))
                                            .addComponent(btn_nota_eliminar)))
                                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(txtf_nota_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(11, 11, 11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogCrearNotaLayout.createSequentialGroup()
                                .addComponent(btn_nota_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane29, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(generarNota)))
                    .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel7))
                            .addComponent(txtf_nota_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDialogCrearNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialogCrearNotaLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel9))
                            .addComponent(txtf_nota_costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jDialogAñadirPlan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAñadirPlan.setBackground(new java.awt.Color(255, 255, 255));
        jDialogAñadirPlan.setLocationByPlatform(true);
        jDialogAñadirPlan.setMinimumSize(new java.awt.Dimension(262, 305));
        jDialogAñadirPlan.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setText("Tipo");
        jDialogAñadirPlan.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 34, 110, -1));

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jDialogAñadirPlan.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 251, -1, -1));
        jDialogAñadirPlan.getContentPane().add(tipo_cuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 31, 126, -1));

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jDialogAñadirPlan.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 251, -1, -1));

        cantidad_cuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantidad_cuotaKeyTyped(evt);
            }
        });
        jDialogAñadirPlan.getContentPane().add(cantidad_cuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 69, 126, -1));

        jLabel37.setText("Cantidad de cuotas");
        jDialogAñadirPlan.getContentPane().add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, 110, -1));
        jDialogAñadirPlan.getContentPane().add(porcentaje_cuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 107, 126, -1));

        jLabel39.setText("Porcentaje extra");
        jDialogAñadirPlan.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 109, 110, 17));

        dia_vencimiento.setText("0");
        dia_vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dia_vencimientoKeyTyped(evt);
            }
        });
        jDialogAñadirPlan.getContentPane().add(dia_vencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 200, 126, -1));

        mes_vencimiento.setText("0");
        mes_vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mes_vencimientoKeyTyped(evt);
            }
        });
        jDialogAñadirPlan.getContentPane().add(mes_vencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 174, 126, -1));

        jLabel40.setText("Dias");
        jDialogAñadirPlan.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 203, 93, -1));

        jLabel45.setText("Mes");
        jDialogAñadirPlan.getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 176, 93, 17));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel46.setText("Intervalo de vencimiento");
        jDialogAñadirPlan.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, -1, -1));

        jDialogNuevoStock.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogNuevoStock.setBackground(new java.awt.Color(255, 255, 255));
        jDialogNuevoStock.setMinimumSize(new java.awt.Dimension(400, 400));
        jDialogNuevoStock.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("No se encontro stock del producto.");
        jDialogNuevoStock.getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 211, 22));

        jLabel5.setText("Stock actual");
        jDialogNuevoStock.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, 157, 22));

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel6.setText("Complete los siguientes campos para generar el stock en inventario.");
        jDialogNuevoStock.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 39, 368, 22));

        jLabel11.setText("Stock pedido");
        jDialogNuevoStock.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 166, 157, 22));
        jDialogNuevoStock.getContentPane().add(txt_stockPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 194, 157, -1));
        jDialogNuevoStock.getContentPane().add(txt_StockActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 119, 157, -1));
        jDialogNuevoStock.getContentPane().add(txt_stockReservado, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 119, 157, -1));

        jLabel12.setText("Stock reservado");
        jDialogNuevoStock.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 91, 157, 22));

        jLabel13.setText("Stock ingresado");
        jDialogNuevoStock.getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 166, 157, 22));
        jDialogNuevoStock.getContentPane().add(txt_stockIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 194, 157, -1));
        jDialogNuevoStock.getContentPane().add(txt_precioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 266, 157, -1));

        jLabel14.setText("Fecha compra");
        jDialogNuevoStock.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 238, 157, 22));

        jLabel15.setText("Precio compra");
        jDialogNuevoStock.getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 238, 157, 22));
        jDialogNuevoStock.getContentPane().add(date_fechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 266, 157, -1));

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jDialogNuevoStock.getContentPane().add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 311, -1, -1));

        btn_GuardarNuevoStock.setText("Guardar");
        btn_GuardarNuevoStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarNuevoStockActionPerformed(evt);
            }
        });
        jDialogNuevoStock.getContentPane().add(btn_GuardarNuevoStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, -1, -1));

        setMaximumSize(new java.awt.Dimension(1366, 735));
        setPreferredSize(new java.awt.Dimension(1200, 627));

        tabla_productos.setMinimumSize(new java.awt.Dimension(1200, 590));
        tabla_productos.setPreferredSize(new java.awt.Dimension(1200, 590));
        tabla_productos.setRequestFocusEnabled(false);

        jPanel21.setMaximumSize(new java.awt.Dimension(344, 32767));
        jPanel21.setMinimumSize(new java.awt.Dimension(344, 588));
        jPanel21.setName(""); // NOI18N

        tabla_productos_busqueda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_productos_busqueda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_productos_busquedaMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tabla_productos_busqueda);
        if (tabla_productos_busqueda.getColumnModel().getColumnCount() > 0) {
            tabla_productos_busqueda.getColumnModel().getColumn(0).setResizable(false);
            tabla_productos_busqueda.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabla_productos_busqueda.getColumnModel().getColumn(1).setResizable(false);
            tabla_productos_busqueda.getColumnModel().getColumn(1).setPreferredWidth(100);
        }

        txtf_productos_buscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_buscarCaretUpdate(evt);
            }
        });
        txtf_productos_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_productos_buscarKeyTyped(evt);
            }
        });

        buttonGroup1.add(rbtn_productos_codigo);
        rbtn_productos_codigo.setText("Código");

        buttonGroup1.add(rbtn_productos_nombre);
        rbtn_productos_nombre.setSelected(true);
        rbtn_productos_nombre.setText("Nombre");

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setText("Buscar Producto");

        btn_proveedores_imprimirTodo.setBackground(new java.awt.Color(255, 255, 255));
        btn_proveedores_imprimirTodo.setText(" Todos");
        btn_proveedores_imprimirTodo.setEnabled(false);
        btn_proveedores_imprimirTodo.setPreferredSize(new java.awt.Dimension(137, 40));
        btn_proveedores_imprimirTodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_proveedores_imprimirTodoMouseClicked(evt);
            }
        });
        btn_proveedores_imprimirTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedores_imprimirTodoActionPerformed(evt);
            }
        });

        btn_eliminar.setBackground(new java.awt.Color(255, 255, 255));
        btn_eliminar.setText("Eliminar ");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_proveedores_imprimirTodo, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtf_productos_buscar))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(rbtn_productos_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtn_productos_nombre)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_productos_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_productos_codigo)
                    .addComponent(rbtn_productos_nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_proveedores_imprimirTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(252, 252, 252));
        jPanel22.setMinimumSize(new java.awt.Dimension(934, 590));
        jPanel22.setLayout(null);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setMinimumSize(new java.awt.Dimension(877, 166));

        jLabel42.setText("Stock Mínimo:");

        jLabel44.setText("Nombre:");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(837, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(617, 617, 617)
                .addComponent(jLabel42)
                .addGap(104, 104, 104))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel42))
                .addContainerGap(578, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel24);
        jPanel24.setBounds(871, 50, 1689, 611);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtf_productos_codigo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtf_productos_codigo.setEnabled(false);
        txtf_productos_codigo.setPreferredSize(new java.awt.Dimension(70, 28));
        txtf_productos_codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_productos_codigoKeyTyped(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel62.setText("Producto");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel63.setText("#Código:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_productos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_productos_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel63)
                    .addComponent(txtf_productos_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(txtf_productos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel22.add(jPanel4);
        jPanel4.setBounds(10, 0, 820, 44);

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        tabla_producto_precioVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "#Cuotas", "Tipo", "Precio Final", "Precio Cuota", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_producto_precioVenta.setEnabled(false);
        tabla_producto_precioVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_producto_precioVentaMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tabla_producto_precioVenta);
        if (tabla_producto_precioVenta.getColumnModel().getColumnCount() > 0) {
            tabla_producto_precioVenta.getColumnModel().getColumn(2).setHeaderValue("Tipo");
            tabla_producto_precioVenta.getColumnModel().getColumn(3).setHeaderValue("Precio Final");
            tabla_producto_precioVenta.getColumnModel().getColumn(4).setHeaderValue("Precio Cuota");
        }

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setText("Plan de pago");

        chk_venta_iva.setBackground(new java.awt.Color(255, 255, 255));
        chk_venta_iva.setText("Producto sin IVA ");
        chk_venta_iva.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_venta_ivaItemStateChanged(evt);
            }
        });

        btn_crearPago.setBackground(new java.awt.Color(255, 255, 255));
        btn_crearPago.setText("Crear nuevo");
        btn_crearPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearPagoActionPerformed(evt);
            }
        });

        producto_plan.setEnabled(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator28))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chk_venta_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(producto_plan, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_crearPago))
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 41, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53)
                    .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(producto_plan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_crearPago, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chk_venta_iva)
                .addGap(82, 82, 82))
        );

        btn_Modificar.setBackground(new java.awt.Color(255, 255, 255));
        btn_Modificar.setText("Modificar");
        btn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModificarActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setPreferredSize(new java.awt.Dimension(900, 156));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setText("Inventario");

        btn_producto_editarLote.setBackground(new java.awt.Color(255, 255, 255));
        btn_producto_editarLote.setText("Editar Stock");
        btn_producto_editarLote.setEnabled(false);
        btn_producto_editarLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_editarLoteActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setText("Unidades");

        stock.setEditable(false);
        stock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel79)
                .addGap(35, 35, 35)
                .addComponent(btn_producto_editarLote, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70)
                    .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_producto_editarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel85.setBackground(new java.awt.Color(255, 255, 255));

        fecha_inicio.setEnabled(false);

        fecha_fin.setEnabled(false);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Inicio");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Fin");

        unidades_vendidas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        unidades_vendidas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unidades_vendidas.setText("----");

        jLabel223.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel223.setText("unid.");

        btn_consultar_historial.setBackground(new java.awt.Color(255, 255, 255));
        btn_consultar_historial.setText("Consultar");
        btn_consultar_historial.setEnabled(false);
        btn_consultar_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultar_historialActionPerformed(evt);
            }
        });

        jLabel174.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel174.setText("Historial de Ventas");

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fecha_fin, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(fecha_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel85Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btn_consultar_historial, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel85Layout.createSequentialGroup()
                                .addComponent(unidades_vendidas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel223)))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(jLabel174)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator26))))
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel174)
                    .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(btn_consultar_historial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(unidades_vendidas, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel223, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha_inicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Guardar.setBackground(new java.awt.Color(255, 255, 255));
        btn_Guardar.setText("Guardar");
        btn_Guardar.setEnabled(false);
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Flete $");

        txtf_productos_costoFlete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_costoFlete.setEnabled(false);
        txtf_productos_costoFlete.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_costoFleteCaretUpdate(evt);
            }
        });

        txtf_productos_codigo_barra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_codigo_barra.setEnabled(false);
        txtf_productos_codigo_barra.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_codigo_barraCaretUpdate(evt);
            }
        });

        txtf_productos_costo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_costo.setEnabled(false);
        txtf_productos_costo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_costoCaretUpdate(evt);
            }
        });

        jLabel176.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel176.setText("Costo e Impuestos");

        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel237.setText("Codigo de barra");

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Precio Costo $");

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Observaciones:");

        txtf_productos_observaciones.setColumns(20);
        txtf_productos_observaciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtf_productos_observaciones.setRows(2);
        txtf_productos_observaciones.setEnabled(false);
        jScrollPane9.setViewportView(txtf_productos_observaciones);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Sobretasa I.V.A");

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Tasa I.V.A");

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Impuesto interno %");

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Imp interno fijo %");

        txtf_iva.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_iva.setEnabled(false);

        txtf_sobretasa_iva.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_sobretasa_iva.setEnabled(false);

        txtf_impuesto_int.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_impuesto_int.setEnabled(false);

        txtf_imp_int_fijo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_imp_int_fijo.setEnabled(false);

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel238.setText("Codigo EAN");

        txtf_productos_codigo_ean.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_codigo_ean.setEnabled(false);
        txtf_productos_codigo_ean.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_codigo_eanCaretUpdate(evt);
            }
        });

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Precio venta $");

        txtf_productos_venta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_venta.setEnabled(false);
        txtf_productos_venta.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_ventaCaretUpdate(evt);
            }
        });

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Stock minimo");

        txtf_productos_stockMinimo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_stockMinimo.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel176)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator30))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel55)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel64)
                            .addComponent(jLabel52)
                            .addComponent(jLabel237, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel238, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtf_productos_codigo_ean, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_productos_codigo_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_productos_costo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_sobretasa_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_productos_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_productos_costoFlete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_impuesto_int, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_productos_stockMinimo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_imp_int_fijo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtf_iva, txtf_productos_costo, txtf_productos_venta, txtf_sobretasa_iva});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel176, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_productos_costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(txtf_productos_costoFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_impuesto_int, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_sobretasa_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_imp_int_fijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_productos_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_productos_stockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_productos_codigo_barra)
                    .addComponent(jLabel237, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_productos_codigo_ean)
                    .addComponent(jLabel238, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtf_iva, txtf_productos_costo, txtf_productos_venta, txtf_sobretasa_iva});

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Rubro");

        producto_Rubro.setEnabled(false);

        btn_crearRubro.setBackground(new java.awt.Color(255, 255, 255));
        btn_crearRubro.setText("Crear nuevo");
        btn_crearRubro.setEnabled(false);
        btn_crearRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(producto_Rubro, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_crearRubro)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel58)
                    .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(producto_Rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_crearRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Cancelar.setBackground(new java.awt.Color(255, 255, 255));
        btn_Cancelar.setText("Limpiar campos");
        btn_Cancelar.setEnabled(false);
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_productoNuevo.setBackground(new java.awt.Color(255, 255, 255));
        btn_productoNuevo.setText("Nuevo ");
        btn_productoNuevo.setMaximumSize(new java.awt.Dimension(133, 39));
        btn_productoNuevo.setMinimumSize(new java.awt.Dimension(133, 39));
        btn_productoNuevo.setPreferredSize(new java.awt.Dimension(133, 39));
        btn_productoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productoNuevoActionPerformed(evt);
            }
        });

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Proveedores");

        producto_Proveedor.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(producto_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel57)
                    .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(producto_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(btn_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_productoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel84Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel84Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel84Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_productoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel84);
        jPanel84.setBounds(10, 50, 850, 611);

        javax.swing.GroupLayout panel_productos_detalleLayout = new javax.swing.GroupLayout(panel_productos_detalle);
        panel_productos_detalle.setLayout(panel_productos_detalleLayout);
        panel_productos_detalleLayout.setHorizontalGroup(
            panel_productos_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productos_detalleLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 841, Short.MAX_VALUE))
        );
        panel_productos_detalleLayout.setVerticalGroup(
            panel_productos_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productos_detalleLayout.createSequentialGroup()
                .addGroup(panel_productos_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabla_productos.addTab("Productos", panel_productos_detalle);

        jPanelDevoluciones.setMaximumSize(new java.awt.Dimension(344, 32767));
        jPanelDevoluciones.setMinimumSize(new java.awt.Dimension(344, 588));
        jPanelDevoluciones.setName(""); // NOI18N

        txtf_devolucion_buscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_devolucion_buscarCaretUpdate(evt);
            }
        });
        txtf_devolucion_buscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtf_devolucion_buscarFocusGained(evt);
            }
        });

        rbtn_devolucion_codigo.setText("Código");

        rbtn_devolucion_nombre.setText("Nombre");

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel72.setText("Buscar Devolución de Producto");

        tabla_devos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_devos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_devosMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tabla_devos);

        javax.swing.GroupLayout jPanelDevolucionesLayout = new javax.swing.GroupLayout(jPanelDevoluciones);
        jPanelDevoluciones.setLayout(jPanelDevolucionesLayout);
        jPanelDevolucionesLayout.setHorizontalGroup(
            jPanelDevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevolucionesLayout.createSequentialGroup()
                .addGroup(jPanelDevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDevolucionesLayout.createSequentialGroup()
                        .addGroup(jPanelDevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDevolucionesLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(rbtn_devolucion_codigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbtn_devolucion_nombre))
                            .addGroup(jPanelDevolucionesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtf_devolucion_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDevolucionesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDevolucionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelDevolucionesLayout.setVerticalGroup(
            jPanelDevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDevolucionesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_devolucion_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_devolucion_codigo)
                    .addComponent(rbtn_devolucion_nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelDevoluciones2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel40.setBackground(new java.awt.Color(245, 245, 245));
        jPanel40.setMinimumSize(new java.awt.Dimension(877, 31));

        jLbl_devo_prodAsociado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLbl_devo_prodAsociado.setText("Producto a devolver...");

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel86.setText("#Código:");

        txtf_devo_codigo.setEditable(false);
        txtf_devo_codigo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtf_devo_codigo.setPreferredSize(new java.awt.Dimension(70, 28));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(jLbl_devo_prodAsociado, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_devo_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_devo_prodAsociado)
                    .addComponent(jLabel86)
                    .addComponent(txtf_devo_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel39.setPreferredSize(new java.awt.Dimension(486, 516));

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel115.setText("Unidades Vendidas :");

        jLabel137.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel137.setText("Cantidad de devoluciones disponibles en el Mes");

        jLabel141.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel141.setText("Total de Devoluciones disponibles :");

        jLabel142.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel142.setText("$");

        jLabel187.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel187.setText("Devoluciones por preventistas : ");

        tabla_devo_preventistas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Nombre Prev", "Ventas Mes Anterior", "Dev. Asignadas", "Dev. Disponibles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_devo_preventistas.getTableHeader().setReorderingAllowed(false);
        tabla_devo_preventistas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabla_devo_preventistasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_devo_preventistasKeyReleased(evt);
            }
        });
        jScrollPane21.setViewportView(tabla_devo_preventistas);

        jLabel188.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel188.setText("Importe total del mes anterior: ");

        jLabel189.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel189.setText("Precio total :");

        jLabel190.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel190.setText("Porcentaje a devolver :");

        jLabel196.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel196.setText("%");

        txtf_devo_unidadesVendidas.setEditable(false);
        txtf_devo_unidadesVendidas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtf_devo_precioTotal.setEditable(false);
        txtf_devo_precioTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtf_devo_porcentajeAdevolver.setEditable(false);
        txtf_devo_porcentajeAdevolver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_devo_porcentajeAdevolver.setEnabled(true);

        txtf_devo_devolucionesDisponibles.setEditable(false);
        txtf_devo_devolucionesDisponibles.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jlbl_editDevo_disp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlbl_editDevo_disp.setForeground(new java.awt.Color(0, 0, 204));
        jlbl_editDevo_disp.setText("Escriba el nro. de devoluciones disponibles y presione enter para editar.*");
        jlbl_editDevo_disp.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel39Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel39Layout.createSequentialGroup()
                                        .addComponent(jLabel141)
                                        .addGap(37, 37, 37))
                                    .addGroup(jPanel39Layout.createSequentialGroup()
                                        .addComponent(jLabel190)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel196)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_devo_porcentajeAdevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_devo_devolucionesDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel39Layout.createSequentialGroup()
                                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel137)
                                    .addComponent(jLabel142))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_devo_precioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator34, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_devo_unidadesVendidas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel39Layout.createSequentialGroup()
                                .addComponent(jLabel187)
                                .addGap(3, 3, 3)
                                .addComponent(jSeparator35, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel39Layout.createSequentialGroup()
                                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel188)
                                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel189)
                                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator36, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jlbl_editDevo_disp)
                                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel188)
                            .addComponent(jSeparator36, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_devo_unidadesVendidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel189)
                            .addComponent(jLabel142)
                            .addComponent(txtf_devo_precioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel137))
                    .addComponent(jSeparator34, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel190)
                    .addComponent(jLabel196)
                    .addComponent(txtf_devo_porcentajeAdevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel141)
                    .addComponent(txtf_devo_devolucionesDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel187)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator35, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbl_editDevo_disp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelDevoluciones2Layout = new javax.swing.GroupLayout(jPanelDevoluciones2);
        jPanelDevoluciones2.setLayout(jPanelDevoluciones2Layout);
        jPanelDevoluciones2Layout.setHorizontalGroup(
            jPanelDevoluciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevoluciones2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelDevoluciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelDevoluciones2Layout.setVerticalGroup(
            jPanelDevoluciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDevoluciones2Layout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 516, Short.MAX_VALUE)
                .addGap(51, 51, 51))
        );

        btn_nuevaDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        btn_nuevaDevolucion.setText("Nueva Devolución");
        btn_nuevaDevolucion.setMaximumSize(new java.awt.Dimension(133, 39));
        btn_nuevaDevolucion.setMinimumSize(new java.awt.Dimension(133, 39));
        btn_nuevaDevolucion.setPreferredSize(new java.awt.Dimension(133, 39));
        btn_nuevaDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nuevaDevolucionMouseClicked(evt);
            }
        });
        btn_nuevaDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevaDevolucionActionPerformed(evt);
            }
        });

        btn_guardar_devolucion.setBackground(new java.awt.Color(255, 255, 255));
        btn_guardar_devolucion.setText("Guardar Devolucion del producto");
        btn_guardar_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_devolucionActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre:");

        jLabel76.setText("Codigo de Devolucion: ");

        jLabel77.setText("Codigo de Producto Asociado:");

        txtf_codDevolucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_codDevolucionKeyTyped(evt);
            }
        });

        jLabel78.setText("Porcentaje de la Devolución:");

        txtf_porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_porcentajeActionPerformed(evt);
            }
        });

        jLbl_devo_nuevaDevo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLbl_devo_nuevaDevo.setText("Nueva Devolución :");

        javax.swing.GroupLayout jPanelDevoluciones3Layout = new javax.swing.GroupLayout(jPanelDevoluciones3);
        jPanelDevoluciones3.setLayout(jPanelDevoluciones3Layout);
        jPanelDevoluciones3Layout.setHorizontalGroup(
            jPanelDevoluciones3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDevoluciones3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDevoluciones3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtf_nombreDevo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_devo_nuevaDevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDevoluciones3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtf_codDevolucion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtf_porcentaje, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                    .addComponent(txtf_codigoProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanelDevoluciones3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btn_guardar_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDevoluciones3Layout.setVerticalGroup(
            jPanelDevoluciones3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevoluciones3Layout.createSequentialGroup()
                .addComponent(jLbl_devo_nuevaDevo)
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_nombreDevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_codDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_codigoProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(btn_guardar_devolucion)
                .addGap(44, 44, 44))
        );

        btn_editDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        btn_editDevolucion.setText("Editar Devolución");
        btn_editDevolucion.setMaximumSize(new java.awt.Dimension(133, 39));
        btn_editDevolucion.setMinimumSize(new java.awt.Dimension(133, 39));
        btn_editDevolucion.setPreferredSize(new java.awt.Dimension(133, 39));
        btn_editDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_editDevolucionMouseClicked(evt);
            }
        });
        btn_editDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editDevolucionActionPerformed(evt);
            }
        });

        btn_eliminar_devolucion.setBackground(new java.awt.Color(255, 255, 255));
        btn_eliminar_devolucion.setText("Eliminar Devolución");
        btn_eliminar_devolucion.setActionCommand("Eliminar devolucion");
        btn_eliminar_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_devolucionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_devosLayout = new javax.swing.GroupLayout(panel_devos);
        panel_devos.setLayout(panel_devosLayout);
        panel_devosLayout.setHorizontalGroup(
            panel_devosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_devosLayout.createSequentialGroup()
                .addComponent(jPanelDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDevoluciones2, javax.swing.GroupLayout.PREFERRED_SIZE, 486, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_devosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelDevoluciones3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_devosLayout.createSequentialGroup()
                        .addComponent(btn_editDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_eliminar_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addComponent(btn_nuevaDevolucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(157, 157, 157))
        );
        panel_devosLayout.setVerticalGroup(
            panel_devosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDevoluciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_devosLayout.createSequentialGroup()
                .addGroup(panel_devosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_devosLayout.createSequentialGroup()
                        .addComponent(jPanelDevoluciones3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btn_nuevaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel_devosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_editDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminar_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelDevoluciones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla_productos.addTab("Devoluciones", panel_devos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Listado de productos");

        jLabel2.setText("Filtrar por:");

        buttonGroupFiltroStock.add(jRadioButtonNegativo);
        jRadioButtonNegativo.setText("Pedido");
        jRadioButtonNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNegativoActionPerformed(evt);
            }
        });

        buttonGroupFiltroStock.add(jRadioButtonExistente);
        jRadioButtonExistente.setText("Existente");
        jRadioButtonExistente.setSelected(true);

        buttonGroupFiltroStock.add(jRadioButtonCompleto);
        jRadioButtonCompleto.setText("Completo");

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jButtonImprimirStock.setText("Imprimir listado");
        jButtonImprimirStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel2)
                        .addGap(60, 60, 60)
                        .addComponent(jRadioButtonExistente)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonNegativo)
                        .addGap(28, 28, 28)
                        .addComponent(jRadioButtonCompleto)
                        .addGap(57, 57, 57)
                        .addComponent(jButtonBuscar)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonImprimirStock)))
                .addContainerGap(461, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButtonNegativo)
                    .addComponent(jRadioButtonExistente)
                    .addComponent(jRadioButtonCompleto)
                    .addComponent(jButtonBuscar)
                    .addComponent(jButtonImprimirStock))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTableTablaStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "ARTICULO", "CANTIDAD", "TIPO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableTablaStock);
        if (jTableTablaStock.getColumnModel().getColumnCount() > 0) {
            jTableTablaStock.getColumnModel().getColumn(0).setResizable(false);
            jTableTablaStock.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTableTablaStock.getColumnModel().getColumn(1).setResizable(false);
            jTableTablaStock.getColumnModel().getColumn(1).setPreferredWidth(160);
            jTableTablaStock.getColumnModel().getColumn(2).setResizable(false);
            jTableTablaStock.getColumnModel().getColumn(2).setPreferredWidth(20);
            jTableTablaStock.getColumnModel().getColumn(3).setResizable(false);
            jTableTablaStock.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        tabla_productos.addTab("Stock", jPanel1);

        panel_pedidos.setBackground(new java.awt.Color(245, 245, 245));

        txtf_nota_proveedor.setEditable(false);
        txtf_nota_proveedor.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btn_lotes_prod_buscarprov.setBackground(new java.awt.Color(255, 255, 255));
        btn_lotes_prod_buscarprov.setText("Buscar proveedor");
        btn_lotes_prod_buscarprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lotes_prod_buscarprovActionPerformed(evt);
            }
        });

        jLabel165.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel165.setText("Proveedor");

        txtf_nota_prod_cod.setEnabled(false);
        txtf_nota_prod_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtf_nota_prod_codKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_nota_prod_codKeyTyped(evt);
            }
        });

        jLabel168.setText("Cantidad:");

        txtf_nota_prod_stock.setEnabled(false);

        btn_factura_agregar.setBackground(new java.awt.Color(0, 153, 0));
        btn_factura_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_factura_agregar.setText("+ ");
        btn_factura_agregar.setEnabled(false);
        btn_factura_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_factura_agregarActionPerformed(evt);
            }
        });

        tabla_productos_pedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Stock", "Precio unitario", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane27.setViewportView(tabla_productos_pedido);

        btn_factura_eliminar.setBackground(new java.awt.Color(204, 51, 0));
        btn_factura_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_factura_eliminar.setText("- ");
        btn_factura_eliminar.setEnabled(false);
        btn_factura_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_factura_eliminarActionPerformed(evt);
            }
        });

        btn_lote_aceptar.setBackground(new java.awt.Color(255, 255, 255));
        btn_lote_aceptar.setText("Guardar");
        btn_lote_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lote_aceptarActionPerformed(evt);
            }
        });

        txtf_nota_prod_precio.setEnabled(false);
        txtf_nota_prod_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtf_nota_prod_precioKeyPressed(evt);
            }
        });

        jLabel169.setText("Subtotal:");

        jLabel170.setText("Numero de factura");

        jLabel171.setText("Forma de Pago:");

        combo_forma_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo", "Diferido" }));

        jLabel172.setText("Fecha");

        txtf_nota_prod_nombre.setEnabled(false);
        txtf_nota_prod_nombre.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtf_nota_prod_nombreItemStateChanged(evt);
            }
        });

        rbtn_nota_prod_codigo.setBackground(new java.awt.Color(245, 245, 245));
        rbtn_nota_prod_codigo.setText("Código");
        rbtn_nota_prod_codigo.setEnabled(false);

        rbtn_nota_prod_prod.setBackground(new java.awt.Color(245, 245, 245));
        rbtn_nota_prod_prod.setText("Producto");
        rbtn_nota_prod_prod.setEnabled(false);

        jLabel212.setText("Fecha de Pago:");

        jLabel213.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel213.setText("Ingresar Productos");

        jPanel81.setBackground(new java.awt.Color(245, 245, 245));
        jPanel81.setBorder(javax.swing.BorderFactory.createTitledBorder("Costo Flete"));

        txtf_lote_prod_flete.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel211.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel211.setText("%");

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(txtf_lote_prod_flete, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel211)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_lote_prod_flete, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel211, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_nota_nueva.setBackground(new java.awt.Color(255, 255, 255));
        btn_nota_nueva.setText("Realizar Nota de Pedido");
        btn_nota_nueva.setEnabled(false);
        btn_nota_nueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nota_nuevaActionPerformed(evt);
            }
        });

        tabla_nota_PedidossProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Pedido", "Fecha", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_nota_PedidossProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_nota_PedidossProveedorMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tabla_nota_PedidossProveedor);

        jLabel177.setText("Tipo factura");

        cbox_tipo_factura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Factura A", "Factura B", "Remito" }));

        javax.swing.GroupLayout panel_pedidosLayout = new javax.swing.GroupLayout(panel_pedidos);
        panel_pedidos.setLayout(panel_pedidosLayout);
        panel_pedidosLayout.setHorizontalGroup(
            panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_pedidosLayout.createSequentialGroup()
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_nota_nueva, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_lote_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator14))
                .addContainerGap())
            .addGroup(panel_pedidosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtf_nota_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lotes_prod_buscarprov, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(jLabel170)
                                .addGap(106, 106, 106)
                                .addComponent(jLabel172))
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(txtf_nota_prod_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(date_nota_fecha_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jPanel81, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(combo_forma_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(date_nota_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(jLabel171)
                                .addGap(79, 79, 79)
                                .addComponent(jLabel212))
                            .addComponent(jLabel177)
                            .addComponent(cbox_tipo_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator16)
                            .addComponent(jScrollPane27)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addComponent(txtf_nota_prod_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(txtf_nota_prod_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtf_nota_prod_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addComponent(rbtn_nota_prod_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(rbtn_nota_prod_prod)
                                        .addGap(371, 371, 371)
                                        .addComponent(jLabel168)))
                                .addGap(6, 6, 6)
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel169)
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addComponent(txtf_nota_prod_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(btn_factura_agregar)
                                        .addGap(6, 6, 6)
                                        .addComponent(btn_factura_eliminar))))))
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addComponent(jLabel165)
                        .addGap(263, 263, 263)
                        .addComponent(jLabel213)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_pedidosLayout.setVerticalGroup(
            panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_pedidosLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel165))
                    .addComponent(jLabel213))
                .addGap(6, 6, 6)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addComponent(txtf_nota_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(btn_lotes_prod_buscarprov, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel170)
                            .addComponent(jLabel172))
                        .addGap(6, 6, 6)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtf_nota_prod_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_nota_fecha_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel177)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbox_tipo_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel171)
                            .addComponent(jLabel212))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_forma_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_nota_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtn_nota_prod_codigo)
                            .addComponent(rbtn_nota_prod_prod)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel168)
                                    .addComponent(jLabel169))))
                        .addGap(2, 2, 2)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_factura_agregar)
                            .addComponent(btn_factura_eliminar)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_nota_prod_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_nota_prod_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_nota_prod_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtf_nota_prod_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_nota_nueva, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lote_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla_productos.addTab("Pedidos", panel_pedidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabla_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabla_productos, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_nota_prod_precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nota_prod_precioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pedidos.agregarProducto();
        }
    }//GEN-LAST:event_txtf_nota_prod_precioKeyPressed

    private void btn_lote_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lote_aceptarActionPerformed
        if(JOptionPane.showConfirmDialog(productoNuevo, "¿Esta seguro que desea generar esta factura?", "Generar Nota", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            pedidos.guardarFactura();
        }
        Pestaña1_dinamica.cambioBusqueda(txtf_productos_buscar.getText().toString());
    }//GEN-LAST:event_btn_lote_aceptarActionPerformed

    private void btn_factura_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_factura_eliminarActionPerformed
        pedidos.eliminarProducto();
    }//GEN-LAST:event_btn_factura_eliminarActionPerformed

    private void btn_factura_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_factura_agregarActionPerformed
        pedidos.agregarProducto();
    }//GEN-LAST:event_btn_factura_agregarActionPerformed

    private void txtf_nota_prod_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nota_prod_codKeyTyped
        char c = evt.getKeyChar();
        if (this.rbtn_nota_prod_codigo.isSelected()) {
            if ((c < '0' || c > '9')) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtf_nota_prod_codKeyTyped

    private void txtf_nota_prod_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nota_prod_codKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pedidos.buscarProducto(txtf_nota_prod_cod.getText());
        }
    }//GEN-LAST:event_txtf_nota_prod_codKeyPressed

    private void btn_lotes_prod_buscarprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lotes_prod_buscarprovActionPerformed
        JD_Proveedor_Buscador provBuscador = new JD_Proveedor_Buscador(new JDialog(), true) {
            public void getIDProveedor(int id, String atributo) {
                if (id != -1) {
                    pedidos.prov = new Proveedor();
                    pedidos.prov.setId(id);
                    pedidos.prov.setNombre(atributo);
                    txtf_nota_proveedor.setText(id + " - " + atributo);
                    pedidos.activarCampos(true);
                    pedidos.cargarTabla();
                    pedidos.factura = new facturaProveedor();
                }

            }
        };
        provBuscador.setLocationRelativeTo(null);
        provBuscador.setVisible(true);
    }//GEN-LAST:event_btn_lotes_prod_buscarprovActionPerformed

    private void btn_eliminar_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_devolucionActionPerformed
        Pestaña1_dinamica.eliminarDevolucion();
        limpiarCamposDevo();
    }//GEN-LAST:event_btn_eliminar_devolucionActionPerformed

    private void btn_editDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editDevolucionActionPerformed
        Pestaña1_dinamica.editarDevolucion();
    }//GEN-LAST:event_btn_editDevolucionActionPerformed

    private void btn_editDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editDevolucionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editDevolucionMouseClicked

    private void txtf_porcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_porcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_porcentajeActionPerformed

    private void txtf_codDevolucionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_codDevolucionKeyTyped
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&(c!='.'))evt.consume();
    }//GEN-LAST:event_txtf_codDevolucionKeyTyped

    private void btn_guardar_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_devolucionActionPerformed
       /* if (control()) {
            Producto p = new Producto();
            if (jLbl_devo_nuevaDevo.getText().equals("Nueva Devolución:")) {
                p.setNombre(txtf_nombreDevo.getText());
                p.setEstado(2);
                p.setId(Integer.parseInt(txtf_codDevolucion.getText()));
                ResultSet rs = productoDAO.productoEliminado(p.getId());
                try {
                    rs.next();
                    int i = 0;
                    int coincidencia = rs.getInt("producto_id");  // esto esta al pedo
                    System.out.println("coincidencia = " + coincidencia);
                    int resp = JOptionPane.showConfirmDialog(null, "El codigo " + p.getId() + " pertenece a un producto ingresado.\n ¿ esta seguro que desea reactivarlo como una devolucion ?", "Importante", JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == resp) {
                        productoDAO.actualizarProductoADevolucion(p, Integer.parseInt(txtf_porcentaje.getText()), Integer.parseInt(txtf_codigoProd.getText()));
                    }
                } catch (SQLException ex) {
                    productoDAO.nuevaDevolucion(p, Integer.parseInt(txtf_porcentaje.getText()), Integer.parseInt(txtf_codigoProd.getText()));
                }
            } else {
                p.setNombre(txtf_nombreDevo.getText());
                p.setId(Integer.parseInt(txtf_codDevolucion.getText()));
                productoDAO.actualizarProductoADevolucion(p, Integer.parseInt(txtf_porcentaje.getText()), Integer.parseInt(txtf_codigoProd.getText()));
            }
            limpiarCamposDevo();
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre y código del producto (valor numérico)",
                "Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }//GEN-LAST:event_btn_guardar_devolucionActionPerformed

    private void btn_nuevaDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevaDevolucionActionPerformed
        jLbl_devo_nuevaDevo.setText("Nueva Devolución:");
        limpiarCamposDevo();
        habilitarPanelNuevaDevo(true);
    }//GEN-LAST:event_btn_nuevaDevolucionActionPerformed

    private void btn_nuevaDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nuevaDevolucionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_nuevaDevolucionMouseClicked

    private void tabla_devo_preventistasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_devo_preventistasKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            int pos = tabla_devo_preventistas.getSelectedRow();
            DefaultTableModel mod = (DefaultTableModel)tabla_devo_preventistas.getModel();
            try{
               // productoDAO.editarDevosDisponibles(Integer.parseInt(String.valueOf(mod.getValueAt(pos,3))), String.valueOf(mod.getValueAt(pos,0)) ,Pestaña1_dinamica.devoSelect.getId());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero para editar correctamente.","Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_tabla_devo_preventistasKeyReleased

    private void tabla_devo_preventistasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_devo_preventistasKeyPressed

    }//GEN-LAST:event_tabla_devo_preventistasKeyPressed

    private void tabla_devosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_devosMouseClicked
        int pos = tabla_devos.getSelectedRow();
        habilitarPanelNuevaDevo(false);
        if (pos != -1) {
            Pestaña1_dinamica.cargarDevolucionDeTabla(pos);
        }
    }//GEN-LAST:event_tabla_devosMouseClicked

    private void txtf_devolucion_buscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtf_devolucion_buscarFocusGained
        limpiarCamposDevo();
    }//GEN-LAST:event_txtf_devolucion_buscarFocusGained

    private void txtf_devolucion_buscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_devolucion_buscarCaretUpdate
        Pestaña1_dinamica.devolucionBusqueda(txtf_devolucion_buscar.getText().toString());
    }//GEN-LAST:event_txtf_devolucion_buscarCaretUpdate

    private void btn_proveedores_imprimirTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedores_imprimirTodoActionPerformed
    /*    String archivo = System.getProperty("user.dir") + System.getProperty("file.separator") + path + "src\\Reportes\\Listado_Productos.jasper";
        JasperReport reporte = null;

        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(archivo);
        } catch (JRException ex) {
            Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map param = new HashMap();

        param.put("DIR_REPORT", System.getProperty("user.dir") + System.getProperty("file.separator") + path+ "src\\");
            JasperPrint jasperPrint = null;

            try {
                jasperPrint = JasperFillManager.fillReport(reporte, param, Main.conexion.conexion);
            } catch (JRException ex) {
                Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
            }

            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle("Listado de Productos");
            jv.setVisible(true);*/
    }//GEN-LAST:event_btn_proveedores_imprimirTodoActionPerformed

    private void btn_proveedores_imprimirTodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_proveedores_imprimirTodoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_proveedores_imprimirTodoMouseClicked

    private void txtf_productos_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_productos_buscarKeyTyped
        char c = evt.getKeyChar();
        if (this.rbtn_productos_codigo.isSelected()) {
            if ((c < '0' || c > '9')) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtf_productos_buscarKeyTyped

    private void txtf_productos_buscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_buscarCaretUpdate
        Pestaña1_dinamica.cambioBusqueda(txtf_productos_buscar.getText());
    }//GEN-LAST:event_txtf_productos_buscarCaretUpdate

    private void tabla_productos_busquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_productos_busquedaMouseClicked
        int pos = tabla_productos_busqueda.getSelectedRow();
        if (pos != -1) {
            Pestaña1_dinamica.cargarProducto(pos);
        }
    }//GEN-LAST:event_tabla_productos_busquedaMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        Pestaña1_dinamica.eliminar();
        limpiarCamposInventario();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void jRadioButtonNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNegativoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jRadioButtonNegativoActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        // TODO add your handling code here:
        JRadioButton jrb=getSelection(buttonGroupFiltroStock);
       
        PantallaStock.cargarTablaStock(jrb.getText());
        //System.out.println("el jrb seleccionado es: "+jrb.getText());
        
        
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonImprimirStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirStockActionPerformed
        JasperViewer view =null;
        view = productoDAO.generarReporteStock1();
        if(view!= null){
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        }
    }//GEN-LAST:event_jButtonImprimirStockActionPerformed

    private void txtf_nuevo_rubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_nuevo_rubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_nuevo_rubroActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String texto = txtf_nuevo_rubro.getText().toLowerCase();
        if(Statics.Funciones.controlText(texto)){
            int i = rubroDAO.InsertRubro(texto);
            Pestaña1_dinamica.actualizarRubro();
        }else{
            
        }
        jDialogAñadirRubro.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        txtf_nuevo_rubro.setText("");
        jDialogAñadirRubro.dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btn_nota_nuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nota_nuevaActionPerformed
        jDialogCrearNota.setTitle("Crear nota de pedido");
        jDialogCrearNota.setVisible(true);
        jDialogCrearNota.setModal(true);
        jDialogCrearNota.setSize(1050,600);
        jDialogCrearNota.setLocationRelativeTo(this);
        pedidos.cambioBusqueda("");
        pedidos.CrearPedido();
    }//GEN-LAST:event_btn_nota_nuevaActionPerformed

    private void tabla_nota_PedidossProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_nota_PedidossProveedorMouseClicked
        pedidos.cargarTablaFactura();
    }//GEN-LAST:event_tabla_nota_PedidossProveedorMouseClicked

    private void txtf_nota_buscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_nota_buscarCaretUpdate
        pedidos.cambioBusqueda(txtf_productos_buscar.getText());
    }//GEN-LAST:event_txtf_nota_buscarCaretUpdate

    private void txtf_nota_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nota_buscarKeyTyped
        char c = evt.getKeyChar();
        if (this.rbtn_productos_codigo.isSelected()) {
            if ((c < '0' || c > '9')) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtf_nota_buscarKeyTyped

    private void txtf_nota_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nota_cantidadKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
                evt.consume();
        }
    }//GEN-LAST:event_txtf_nota_cantidadKeyTyped

    private void btn_nota_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nota_agregarActionPerformed
        if(!Statics.Funciones.isNumeric(txtf_nota_cantidad.getText())){
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor valido en cantidad.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!Statics.Funciones.isFloat(txtf_nota_costo.getText())){
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor valido en costo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        pedidos.agregarRenglonPedido(Float.parseFloat(txtf_nota_costo.getText()),Integer.parseInt(txtf_nota_cantidad.getText()));
    }//GEN-LAST:event_btn_nota_agregarActionPerformed

    private void btn_nota_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nota_eliminarActionPerformed
        pedidos.eliminarRP();
    }//GEN-LAST:event_btn_nota_eliminarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        pedidos.cancelarNota();        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tabla_nota_prodPedirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_nota_prodPedirMouseClicked
        if(tabla_nota_prodPedir.getSelectedRow() != -1){
            pedidos.selectProducto(tabla_nota_prodPedir.getSelectedRow());
        }
    }//GEN-LAST:event_tabla_nota_prodPedirMouseClicked

    private void tabla_nota_prodPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_nota_prodPedidoMouseClicked
        pedidos.rpSelected  = tabla_nota_prodPedido.getSelectedRow();
    }//GEN-LAST:event_tabla_nota_prodPedidoMouseClicked

    private void generarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarNotaActionPerformed
        if(JOptionPane.showConfirmDialog(productoNuevo, "¿Esta seguro que desea generar esta nota de pedido?", "Generar Nota", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            pedidos.terminarPedido();
        }else{
            pedidos.cancelarNota();
        }
    }//GEN-LAST:event_generarNotaActionPerformed

    private void txtf_nota_prod_nombreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtf_nota_prod_nombreItemStateChanged
        
    }//GEN-LAST:event_txtf_nota_prod_nombreItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String tipo = tipo_cuota.getText();
        String porcentaje = porcentaje_cuota.getText();
        String cantidad = cantidad_cuota.getText();
        String mes = mes_vencimiento.getText();
        String dia = dia_vencimiento.getText();
        if(!Statics.Funciones.isNumeric(mes) || !Statics.Funciones.isNumeric(dia) || !(Integer.parseInt(mes) >0 || Integer.parseInt(dia) > 0 )){
            JOptionPane.showMessageDialog(null, "Debe completar el intervalo de vencimiento entre cuotas. \n Es decir cuantos dias y/o meses deben pasar entre el vencimiento cada cuota",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(Statics.Funciones.controlText(tipo) && Statics.Funciones.isNumeric(cantidad) && Statics.Funciones.isFloat(porcentaje)){
            Cuota c = new Cuota(tipo,Float.parseFloat(porcentaje),Integer.parseInt(cantidad),Integer.parseInt(dia),Integer.parseInt(mes));
            cuotasDAO.insertCuota(c);
            if(Pestaña1_dinamica.prodSeleccionado != null){
                Pestaña1_dinamica.actualizarPrecios();
            }
            jDialogAñadirPlan.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos correctamente.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tipo_cuota.setText("");
        porcentaje_cuota.setText("");
        cantidad_cuota.setText("");
        jDialogAñadirPlan.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cantidad_cuotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidad_cuotaKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_cantidad_cuotaKeyTyped

    private void dia_vencimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dia_vencimientoKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_dia_vencimientoKeyTyped

    private void mes_vencimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mes_vencimientoKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_mes_vencimientoKeyTyped

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        Pestaña1_dinamica.limpiarCamposStockNuevo();
        jDialogNuevoStock.dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_GuardarNuevoStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarNuevoStockActionPerformed
        Pestaña1_dinamica.insertarStockNuevo();
        jDialogNuevoStock.dispose();
    }//GEN-LAST:event_btn_GuardarNuevoStockActionPerformed

    private void btn_productoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productoNuevoActionPerformed
        modificarTrue = false;
        Pestaña1_dinamica.habilitarCampos(true, "NUEVO");
        
        Pestaña1_dinamica.prodSeleccionado = new Producto(); 
        Pestaña1_dinamica.prodSeleccionado.setId(productoDAO.getNextID());
    }//GEN-LAST:event_btn_productoNuevoActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        limpiarCamposInventario();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_crearRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearRubroActionPerformed
        jDialogAñadirRubro.setTitle("Añadir rubro");
        jDialogAñadirRubro.setVisible(true);
        jDialogAñadirRubro.setModal(true);
        jDialogAñadirRubro.setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_crearRubroActionPerformed

    private void txtf_productos_ventaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_ventaCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_productos_ventaCaretUpdate

    private void txtf_productos_codigo_eanCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_codigo_eanCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_productos_codigo_eanCaretUpdate

    private void txtf_productos_costoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_costoCaretUpdate
        if(Pestaña1_dinamica.cuotas != null && Pestaña1_dinamica.cuotas.size() >= 0){
            Pestaña1_dinamica.calcularPrecios();
        }
    }//GEN-LAST:event_txtf_productos_costoCaretUpdate

    private void txtf_productos_codigo_barraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_codigo_barraCaretUpdate

    }//GEN-LAST:event_txtf_productos_codigo_barraCaretUpdate

    private void txtf_productos_costoFleteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_costoFleteCaretUpdate
        if(Pestaña1_dinamica.cuotas != null && Pestaña1_dinamica.cuotas.size() >= 0){
            Pestaña1_dinamica.calcularPrecios();
        }
    }//GEN-LAST:event_txtf_productos_costoFleteCaretUpdate

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        if(!modificarTrue){ // esta guardando un producto nuevo
            if(!Pestaña1_dinamica.guardarNuevoProd()){ //si no se guardo el producto corta el metodo
                return;
            }
            //, si se guardo actualiza el resto de los campos 
            principal.lbl_estado.setText("El producto se creo con exito.");
            principal.lbl_estado.setForeground(Color.GREEN);
            modificarTrue = false;
            //(se hace en dos metodos porque es un fix y aprovecho lo que esta hecho)
        }
        if(Pestaña1_dinamica.actualizarProduto()){
            if(modificarTrue)
                principal.lbl_estado.setText("El producto se actualizo con exito.");  
        }else{
            principal.lbl_estado.setText("Ocurrio un error al actualizar el producto.");
        }
        Statics.Funciones.limpiarlbl_estado();
        limpiarCamposInventario();
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_consultar_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultar_historialActionPerformed
        Pestaña1_dinamica.getunidadesVendidas();
    }//GEN-LAST:event_btn_consultar_historialActionPerformed

    private void btn_producto_editarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_editarLoteActionPerformed
        if(Pestaña1_dinamica.prodSeleccionado == null)
            return;
        if(!modificarTrue){
             JOptionPane.showMessageDialog(null, "Primero debe guardar el producto nuevo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!productoDAO.getStockProducto(Pestaña1_dinamica.prodSeleccionado.getId()).isEmpty()) {
            editarStock = new JD_Producto_editarStock(new JFrame(), true, Pestaña1_dinamica.prodSeleccionado) {

                public void setStock(float st){
                    stock.setText(st+"");
                }
                public void nuevoStock(){
                    jDialogNuevoStock.setTitle("Añadir stock nuevo");
                    jDialogNuevoStock.setVisible(true);
                    jDialogNuevoStock.setModal(true);
                    jDialogNuevoStock.setLocationRelativeTo(this);
                    Pestaña1_dinamica.limpiarCamposStockNuevo();
                }
            };
            editarStock.setLocationRelativeTo(null);
            editarStock.setVisible(true);
        }else{
            jDialogNuevoStock.setTitle("Añadir stock nuevo");
            jDialogNuevoStock.setVisible(true);
            jDialogNuevoStock.setModal(true);
            jDialogNuevoStock.setLocationRelativeTo(this);
            Pestaña1_dinamica.limpiarCamposStockNuevo();
        }
    }//GEN-LAST:event_btn_producto_editarLoteActionPerformed

    private void btn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModificarActionPerformed
        modificarTrue = true;
        Pestaña1_dinamica.habilitarCampos(true,"MODIFICAR");
        if(Pestaña1_dinamica.prodSeleccionado != null){
            Proveedor aux;
            Rubro aux1;
            if(Pestaña1_dinamica.prodSeleccionado.getIdProveedorActual() == -1){
                aux = new Proveedor();
                aux.setNombre("No se encontraron proveedores");
                aux.setId(-1);

            }else{
                aux = proveedoresDAO.getProveedor(Pestaña1_dinamica.prodSeleccionado.getIdProveedorActual());
                producto_Proveedor.setSelectedItem(aux);
            }
            if(Pestaña1_dinamica.prodSeleccionado.getIdProductoRubro() == -1){
                aux1 = new Rubro();
                aux.setNombre("No se encontraron rubros");
                aux.setId(-1);
                producto_Rubro.setSelectedItem(aux);
            }else{
                aux1 = new Rubro();
                aux1.setId(Pestaña1_dinamica.prodSeleccionado.getIdProductoRubro());
                aux1.setNombre(Pestaña1_dinamica.prodSeleccionado.getNombreRubro());
                producto_Rubro.setSelectedItem(aux1);
            }
        }
    }//GEN-LAST:event_btn_ModificarActionPerformed

    private void btn_crearPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearPagoActionPerformed
        jDialogAñadirPlan.setTitle("Añadir Plan de pago");
        jDialogAñadirPlan.setVisible(true);
        jDialogAñadirPlan.setSize(295, 345);
        jDialogAñadirPlan.setModal(true);
        jDialogAñadirPlan.setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_crearPagoActionPerformed

    private void chk_venta_ivaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_venta_ivaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_venta_ivaItemStateChanged

    private void tabla_producto_precioVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_producto_precioVentaMouseClicked
        if(evt.getClickCount() == 2){
            int row = tabla_producto_precioVenta.getSelectedRow();
            boolean activa = (boolean) tabla_producto_precioVenta.getValueAt(row, 5);
            tabla_producto_precioVenta.setValueAt(!activa,row, 5);
            Pestaña1_dinamica.cuotas.get(row).setActiva(!activa);
        }
    }//GEN-LAST:event_tabla_producto_precioVentaMouseClicked

    private void txtf_productos_codigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_productos_codigoKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }   
    }//GEN-LAST:event_txtf_productos_codigoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_GuardarNuevoStock;
    private javax.swing.JButton btn_Modificar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consultar_historial;
    private javax.swing.JButton btn_crearPago;
    private javax.swing.JButton btn_crearRubro;
    private javax.swing.JButton btn_editDevolucion;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_eliminar_devolucion;
    private javax.swing.JButton btn_factura_agregar;
    private javax.swing.JButton btn_factura_eliminar;
    private javax.swing.JButton btn_guardar_devolucion;
    private javax.swing.JButton btn_lote_aceptar;
    private javax.swing.JButton btn_lotes_prod_buscarprov;
    private javax.swing.JButton btn_nota_agregar;
    private javax.swing.JButton btn_nota_eliminar;
    private javax.swing.JButton btn_nota_nueva;
    private javax.swing.JButton btn_nuevaDevolucion;
    private javax.swing.JButton btn_productoNuevo;
    private javax.swing.JButton btn_producto_editarLote;
    private javax.swing.JButton btn_proveedores_imprimirTodo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupFiltroStock;
    private javax.swing.JTextField cantidad_cuota;
    private javax.swing.JComboBox cbox_tipo_factura;
    private javax.swing.JCheckBox chk_venta_iva;
    private javax.swing.JComboBox combo_forma_pago;
    private com.toedter.calendar.JDateChooser date_fechaCompra;
    private com.toedter.calendar.JDateChooser date_nota_fecha_factura;
    private com.toedter.calendar.JDateChooser date_nota_fecha_pago;
    private javax.swing.JTextField dia_vencimiento;
    private com.toedter.calendar.JDateChooser fecha_fin;
    private com.toedter.calendar.JDateChooser fecha_inicio;
    private javax.swing.JButton generarNota;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonImprimirStock;
    private javax.swing.JDialog jDialogAñadirPlan;
    private javax.swing.JDialog jDialogAñadirRubro;
    private javax.swing.JDialog jDialogCrearNota;
    private javax.swing.JDialog jDialogNuevoStock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLbl_devo_nuevaDevo;
    private javax.swing.JLabel jLbl_devo_prodAsociado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanelDevoluciones;
    private javax.swing.JPanel jPanelDevoluciones2;
    private javax.swing.JPanel jPanelDevoluciones3;
    private javax.swing.JRadioButton jRadioButtonCompleto;
    private javax.swing.JRadioButton jRadioButtonExistente;
    private javax.swing.JRadioButton jRadioButtonNegativo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JTable jTableTablaStock;
    private javax.swing.JLabel jlbl_editDevo_disp;
    private javax.swing.JLabel lbl_nota_proveedor;
    private javax.swing.JTextField mes_vencimiento;
    private javax.swing.JPanel panel_devos;
    private javax.swing.JPanel panel_pedidos;
    private javax.swing.JPanel panel_productos_detalle;
    private javax.swing.JTextField porcentaje_cuota;
    private javax.swing.JComboBox<Models.Proveedor> producto_Proveedor;
    private javax.swing.JComboBox<Models.Rubro> producto_Rubro;
    private javax.swing.JComboBox<Models.Cuota> producto_plan;
    protected javax.swing.JRadioButton rbtn_devolucion_codigo;
    protected javax.swing.JRadioButton rbtn_devolucion_nombre;
    private javax.swing.JRadioButton rbtn_nota_prod_codigo;
    private javax.swing.JRadioButton rbtn_nota_prod_prod;
    protected javax.swing.JRadioButton rbtn_productos_codigo;
    protected javax.swing.JRadioButton rbtn_productos_codigo1;
    protected javax.swing.JRadioButton rbtn_productos_nombre;
    protected javax.swing.JRadioButton rbtn_productos_nombre1;
    private javax.swing.JTextField stock;
    private javax.swing.JTable tabla_devo_preventistas;
    private javax.swing.JTable tabla_devos;
    private javax.swing.JTable tabla_nota_PedidossProveedor;
    private javax.swing.JTable tabla_nota_prodPedido;
    private javax.swing.JTable tabla_nota_prodPedir;
    private javax.swing.JTable tabla_producto_precioVenta;
    private javax.swing.JTabbedPane tabla_productos;
    private javax.swing.JTable tabla_productos_busqueda;
    private javax.swing.JTable tabla_productos_pedido;
    private javax.swing.JTextField tipo_cuota;
    private javax.swing.JTextField txt_StockActual;
    private javax.swing.JTextField txt_precioCompra;
    private javax.swing.JTextField txt_stockIngresado;
    private javax.swing.JTextField txt_stockPedido;
    private javax.swing.JTextField txt_stockReservado;
    private javax.swing.JTextField txtf_codDevolucion;
    private javax.swing.JTextField txtf_codigoProd;
    protected javax.swing.JTextField txtf_devo_codigo;
    private javax.swing.JTextField txtf_devo_devolucionesDisponibles;
    private javax.swing.JTextField txtf_devo_porcentajeAdevolver;
    private javax.swing.JTextField txtf_devo_precioTotal;
    private javax.swing.JTextField txtf_devo_unidadesVendidas;
    protected javax.swing.JTextField txtf_devolucion_buscar;
    private javax.swing.JTextField txtf_imp_int_fijo;
    private javax.swing.JTextField txtf_impuesto_int;
    private javax.swing.JTextField txtf_iva;
    private javax.swing.JTextField txtf_lote_prod_flete;
    private javax.swing.JTextField txtf_nombreDevo;
    protected javax.swing.JTextField txtf_nota_buscar;
    protected javax.swing.JTextField txtf_nota_cantidad;
    protected javax.swing.JTextField txtf_nota_costo;
    protected javax.swing.JTextField txtf_nota_nro;
    private javax.swing.JTextField txtf_nota_prod_cod;
    private javax.swing.JTextField txtf_nota_prod_factura;
    private javax.swing.JComboBox txtf_nota_prod_nombre;
    private javax.swing.JTextField txtf_nota_prod_precio;
    private javax.swing.JTextField txtf_nota_prod_stock;
    private javax.swing.JTextField txtf_nota_proveedor;
    protected javax.swing.JTextField txtf_nota_total;
    private javax.swing.JTextField txtf_nuevo_rubro;
    private javax.swing.JTextField txtf_porcentaje;
    protected javax.swing.JTextField txtf_productos_buscar;
    protected javax.swing.JTextField txtf_productos_codigo;
    private javax.swing.JTextField txtf_productos_codigo_barra;
    private javax.swing.JTextField txtf_productos_codigo_ean;
    private javax.swing.JTextField txtf_productos_costo;
    private javax.swing.JTextField txtf_productos_costoFlete;
    private javax.swing.JTextField txtf_productos_nombre;
    private javax.swing.JTextArea txtf_productos_observaciones;
    private javax.swing.JTextField txtf_productos_stockMinimo;
    private javax.swing.JTextField txtf_productos_venta;
    private javax.swing.JTextField txtf_sobretasa_iva;
    private javax.swing.JLabel unidades_vendidas;
    // End of variables declaration//GEN-END:variables

    public class Pestaña1_dinamica extends Thread{
        private Producto prodSeleccionado = null;
        private Producto devoSelect = null;
        private ProductoDAO productosDAO;
        private ProductosView view;
        private List<Cuota> cuotas;
        private DefaultTableModel modelProducto, modelProv, modelInv, modelTop,modelDevo;
        
        public Pestaña1_dinamica(ProductosView view) {
            productosDAO = ProductoDAO.getInstance();
            this.view = view;
            //impuestos = productosDAO.cargarImpuestos();
            ButtonGroup grupo = new ButtonGroup();
            grupo.add(rbtn_productos_codigo);
            grupo.add(rbtn_productos_nombre);
            rbtn_productos_codigo.setSelected(true);
                
            modelProducto = (DefaultTableModel) tabla_productos_busqueda.getModel();
            //modelInv = (DefaultTableModel) tabla_productos_inventarioLotes.getModel();

            modelDevo =(DefaultTableModel) tabla_devos.getModel();    
            tabla_productos_busqueda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            limpiarCampos();
        }
         private void limpiarCampos() {
            txtf_productos_costo.setText("");
            txtf_productos_costoFlete.setText("");
            //txtf_productos_costoIVA.setText("");
            //txtf_productos_costoIngBruto.setText("");
            modelProducto.setNumRows(0);
            //modelInv.setNumRows(0);
        }
        public void devolucionBusqueda(String txt) {
            if (txt.isEmpty()) {
                 productosDAO.buscarDevolucion("producto_nombre", "");
            } else if (rbtn_devolucion_codigo.isSelected()) {
                try {
                    int cod = Integer.parseInt(txt);
                    productosDAO.buscarDevolucion(cod);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un c�digo",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    txtf_devolucion_buscar.setText("");
                }
            } else if (rbtn_devolucion_nombre.isSelected()) {
                productosDAO.buscarDevolucion("producto_nombre",
                        txt.toLowerCase());
            } else {
                System.out.println("Error no selecciono tipo de busqueda");
            }
        }
        
        public void cambioBusqueda(String txt) {
            if (txt.isEmpty()) {
                cargarProductos(productosDAO.buscarProducto("nombre", ""));
            } else if (rbtn_productos_codigo.isSelected()) {
                try {
                    int cod = Integer.parseInt(txt);
                    cargarProductos(productosDAO.buscarProducto(cod));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un c�digo",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    txtf_productos_buscar.setText("");
                }
            } else if (rbtn_productos_nombre.isSelected()) {
                cargarProductos(productosDAO.buscarProducto("nombre",txt.toLowerCase()));
            } else {
                System.out.println("Error no selecciono tipo de busqueda");
            }
        }

        private void cargarProducto(int pos) {
            if (pos != -1) {
                unidades_vendidas.setText("----");
                btn_producto_editarLote.setEnabled(true);
                //btn_consultar_historial.setEnabled(true);
                prodSeleccionado = listProductos.get(pos);
                //Consultas extras
                cuotas = CuotasDAO.getInstance().getCuotasProd(prodSeleccionado.getId());
                calcularPrecios();
                //-------
                if (prodSeleccionado != null) {
                    if (prodSeleccionado.getObservaciones().equals("null")) {
                        txtf_productos_observaciones.setText("");
                    } else {
                        txtf_productos_observaciones.setText(prodSeleccionado.getObservaciones());
                    }
                    txtf_productos_codigo.setText("" + prodSeleccionado.getCod());
                    txtf_productos_nombre.setText(prodSeleccionado.getNombre());
                    txtf_productos_codigo_barra.setText(prodSeleccionado.getCodigoBarra());
                    txtf_productos_stockMinimo.setText("" + prodSeleccionado.getStockMin());
                    txtf_productos_costo.setText("" + prodSeleccionado.getPrecioCosto());
                    txtf_productos_costoFlete.setText("" + prodSeleccionado.getCostoFlete());
                    txtf_iva.setText("" + prodSeleccionado.getIva());
                    txtf_sobretasa_iva.setText(""+prodSeleccionado.getSobretasaIva());
                    txtf_imp_int_fijo.setText(""+prodSeleccionado.getImpuesto_int_fijo());
                    txtf_impuesto_int.setText(""+prodSeleccionado.getImpuesto_interno());
                    txtf_productos_venta.setText(""+prodSeleccionado.getPrecioVenta());
                    txtf_productos_codigo_ean.setText(prodSeleccionado.getCodigoEAN());
                    stock.setText("" + prodSeleccionado.getStock());
                    producto_Proveedor.removeAllItems();
                    producto_Proveedor.setEnabled(false);
                    producto_Rubro.removeAllItems();
                    producto_Rubro.setEnabled(false);
                    if (prodSeleccionado.getIdProveedorActual() == -1) {
                        Proveedor p = new Proveedor();
                        p.setNombre("-No posee un proveedor asignado-");
                        producto_Proveedor.addItem(p);
                    } else {
                        Proveedor p = proveedoresDAO.getProveedor(prodSeleccionado.getIdProveedorActual());
                        producto_Proveedor.addItem(p);
                    }
                    if(prodSeleccionado.getIdProductoRubro() != -1){
                        Rubro r = new Rubro();
                        r.setId(prodSeleccionado.getIdProductoRubro());
                        r.setNombre(prodSeleccionado.getNombreRubro());
                        producto_Rubro.addItem(r);
                    }
                    chk_venta_iva.setSelected(prodSeleccionado.isSinIva());
                }
            }
        }

        private void calcularPrecios() {
            
            String auxCosto = txtf_productos_costo.getText();
            String auxFlete = txtf_productos_costoFlete.getText();
            DefaultTableModel tablaProdPrecios = (DefaultTableModel) tabla_producto_precioVenta.getModel();
            float costo, flete,precioVenta, precioCuota;
            try {
                if (auxCosto.isEmpty() || !Statics.Funciones.isFloat(auxCosto)) {
                    costo = 0;
                } else {
                    costo = Float.parseFloat(auxCosto);
                }
                if (auxFlete.isEmpty() || !Statics.Funciones.isFloat(auxFlete)) {
                    flete = 0;
                } else {
                    flete = Float.parseFloat(auxFlete);
                }
                float precio_parcial = costo + flete;
                try {
                    tablaProdPrecios.setNumRows(0);
                    Object[] obj = new Object[6];
                    for (int i = 0; i < cuotas.size(); i++) {
                        obj[0] = cuotas.get(i).getId();
                        obj[1] = cuotas.get(i).getCantidad();
                        obj[2] = cuotas.get(i).getTipo();
                        float precio_final = precio_parcial * ((cuotas.get(i).getPorcentajeExtra()/100) + 1);
                        obj[3] = Statics.Funciones.redondeo2Deci(precio_final);
                        float precio_cuota = precio_final / cuotas.get(i).getCantidad();;
                        obj[4] = Statics.Funciones.redondeo2Deci(precio_cuota);
                        obj[5] = cuotas.get(i).getActiva();
                        tablaProdPrecios.addRow(obj);
                    }
                } catch (Exception ex) {
                    new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
                }

            } catch (Exception ex) {
                new Statics.ExceptionManager().saveDump(ex, "", Main.isProduccion);
            }

        }

        private boolean actualizarProduto() {
            if (prodSeleccionado != null) {
                prodSeleccionado.setNombre(txtf_productos_nombre.getText());
                prodSeleccionado.setObservaciones(txtf_productos_observaciones.getText());
                prodSeleccionado.setCodigoBarra(txtf_productos_codigo_barra.getText());
                prodSeleccionado.setCodigoEAN(txtf_productos_codigo_ean.getText());
                prodSeleccionado.setIdProductoRubro(((Rubro)producto_Rubro.getSelectedItem()).getId());
                prodSeleccionado.setIdProveedorActual(((Proveedor)producto_Proveedor.getSelectedItem()).getId());
                if(Statics.Funciones.isFloat(txtf_sobretasa_iva.getText()))
                    prodSeleccionado.setSobretasaIva(Float.parseFloat(txtf_sobretasa_iva.getText()));
                else
                    prodSeleccionado.setSobretasaIva(0f);
                if(Statics.Funciones.isFloat(txtf_productos_stockMinimo.getText()))
                    prodSeleccionado.setStockMin(Float.parseFloat(txtf_productos_stockMinimo.getText()));
                else
                    prodSeleccionado.setStockMin(0f);
                if(Statics.Funciones.isFloat(txtf_productos_venta.getText()))
                    prodSeleccionado.setPrecioVenta(Float.parseFloat(txtf_productos_venta.getText()));
                else
                    prodSeleccionado.setPrecioVenta(0f);
                if(Statics.Funciones.isFloat(txtf_iva.getText()))
                    prodSeleccionado.setIva(Float.parseFloat(txtf_iva.getText()));
                else
                    prodSeleccionado.setIva(0f);
                if(Statics.Funciones.isFloat(txtf_productos_costo.getText()))
                    prodSeleccionado.setPrecioCosto(Float.parseFloat(txtf_productos_costo.getText()));
                else
                    prodSeleccionado.setPrecioCosto(0f);
                if(Statics.Funciones.isFloat(txtf_productos_costoFlete.getText()))
                    prodSeleccionado.setCostoFlete(Float.parseFloat(txtf_productos_costoFlete.getText()));
                else
                    prodSeleccionado.setCostoFlete(0f);
                if(Statics.Funciones.isFloat(txtf_imp_int_fijo.getText()))
                    prodSeleccionado.setImpuesto_int_fijo(Float.parseFloat(txtf_imp_int_fijo.getText()));
                else
                    prodSeleccionado.setImpuesto_int_fijo(0f);
                if(Statics.Funciones.isFloat(txtf_impuesto_int.getText()))
                    prodSeleccionado.setImpuesto_interno(Float.parseFloat(txtf_impuesto_int.getText()));
                else
                    prodSeleccionado.setImpuesto_interno(0f);
                if(chk_venta_iva.isSelected()){
                    prodSeleccionado.setSinIva(true);
                }else{
                    prodSeleccionado.setSinIva(false);
                }
                for (int i = 0; i < tabla_producto_precioVenta.getRowCount(); i++) {
                    cuotas.get(i).setActiva((boolean)tabla_producto_precioVenta.getValueAt(i, 5));
                }
                cuotasDAO.setCuotasProd(cuotas,prodSeleccionado.getId());
                if(productosDAO.actualizarProducto(prodSeleccionado) > 0)
                    return true;                  
                else
                    return false;
            } else {
                return false;
            }
        }
        
        /**
         * Actualizar el rubro del producto cuando se crea uno nuevo.
         */
        private void actualizarRubro() {
            if(prodSeleccionado != null){
                Rubro p = rubroDAO.getLastInsert();
                producto_Rubro.addItem(p);
                producto_Rubro.setSelectedItem(p);
            }
        }
     /**
     * Carga los datos de la devolucion
     *
     * @param pos
     */
    private void cargarDevolucionDeTabla(int pos) {
        /*int porcentaje = 1, totalVtas;
        if (pos != -1) {
            devoSelect = listDevoluciones.get(pos);
            if (devoSelect != null) {
                ResultSet rs = productosDAO.traerDatosDevoluciones(devoSelect.getId());
                try {
                    if (rs.next()) {
                        jLbl_devo_prodAsociado.setText("" + rs.getString("producto_nombre"));
                        txtf_devo_codigo.setText("" + rs.getInt("devolucion_prodAsociado_id"));
                        porcentaje = rs.getInt("devolucion_porcentajeAsociado");
                        txtf_devo_porcentajeAdevolver.setText("" + porcentaje);
                    }
                    
                } catch (Exception e) {
                    System.out.println("" + e.getMessage());;
                }
                totalVtas = productosDAO.calcularStockDevoluciones(devoSelect.getId());
                txtf_devo_unidadesVendidas.setText("" + totalVtas);
                txtf_devo_devolucionesDisponibles.setText("" + Integer.parseInt( String.valueOf( (totalVtas * porcentaje) / 100) ) );
            }
            //CARGAR tabla_devo_preventistas -----------------------------------
            DefaultTableModel tablaDevoPrev = (DefaultTableModel) tabla_devo_preventistas.getModel();
            ResultSet rs = productosDAO.traetDatosStockDevo(devoSelect.getId());
            try {
                Object[] fila = new Object[4];
                tablaDevoPrev.setNumRows(0);
                while (rs.next()) {
                    fila[0] = (rs.getString("preventista_nombre"));
                    fila[1] = (rs.getString("stock_devoluciones_ventasMesAnterior"));
                    fila[2] = (rs.getInt("stock_devoluciones_stockInicial"));
                    fila[3] = (rs.getInt("stock_devoluciones_stockDisponible"));
                    tablaDevoPrev.addRow(fila);
                }
            } catch (Exception e) {

            }
            //CALCULAR IMPORTE DE LA VENTA DEL PRODUCTO EN EL MES PASADO
            Thread hilo = new Thread () { 
                        public void run () { 
                        txtf_devo_precioTotal.setText(String.format("$ %.2f",productosDAO.traerImporteMesPasado(devoSelect.getId())));
                        } 
                      };  
                hilo.start();
        }*/
    }
    public void eliminarDevolucion(){
        /*if(devoSelect!=null){
            int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar la devolucion seleccionada?", "Importante", JOptionPane.YES_NO_OPTION);
            if(opcion == JOptionPane.YES_OPTION){
                if (productoDAO.eliminarDevolucion(devoSelect.getId()) == 1) {
                    principal.lbl_estado.setText("Devolucion Eliminada");
                    Pestaña1_dinamica.devolucionBusqueda("");
                    devoSelect=null;
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al eliminar la devolución. " + devoSelect.getNombre(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }*/
    }
    public void editarDevolucion(){
        if(devoSelect!=null){
            jLbl_devo_nuevaDevo.setText("Editar Devolución : ");
            habilitarPanelNuevaDevo(true);
            txtf_nombreDevo.setText(devoSelect.getNombre());
            txtf_codDevolucion.setText(String.valueOf(devoSelect.getId()));
            txtf_codDevolucion.setEditable(false);
            txtf_porcentaje.setText(txtf_devo_porcentajeAdevolver.getText());
            txtf_codigoProd.setText(txtf_devo_codigo.getText()); 
            txtf_codigoProd.setEditable(false);
        }
    }
        
        /**
         * llena la tabla con las devoluciones de la LIST 
         * @param list lista de devoluciones 
         */
        
        public void cargarDevoluciones(List<Producto> list) {
            listDevoluciones = list;
            try {
                modelDevo.setNumRows(0);
                Object[] obj = new Object[2];
                for (int i = 0; i < list.size(); i++) {
                    obj[0] = list.get(i).getId();
                    obj[1] = list.get(i).getNombre();
                    modelDevo.addRow(obj);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        public void cargarProductos(List<Producto> list) {
            listProductos = list;          
            try {
                modelProducto.setNumRows(0);
                Object[] obj = new Object[2];
                for (int i = 0; i < list.size(); i++) {
                    obj[0] = list.get(i).getId();
                    obj[1] = list.get(i).getNombre();
                    modelProducto.addRow(obj);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
 
        private void getunidadesVendidas() {
            if (prodSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Primero debe seleccionar un producto.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.util.Date fecha_java;
            java.sql.Date fecha_inicio_sql, fecha_fin_sql;
            fecha_java = fecha_inicio.getDate();
            fecha_inicio_sql = new java.sql.Date(fecha_java.getTime());
            fecha_java = fecha_fin.getDate();
            fecha_fin_sql = new java.sql.Date(fecha_java.getTime());
            
            //unidades_vendidas.setText("" + productosDAO.getUnidadesVendidas(0, fecha_inicio_sql, fecha_fin_sql, prodSeleccionado.getId()));
        }

        private void eliminar() {
            if (prodSeleccionado != null) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int opcion = JOptionPane.showConfirmDialog(null, "¿Confirma que desea eliminar el producto? \n ", "Importante", dialogButton);
                if (opcion == JOptionPane.YES_OPTION) { //The ISSUE is here
                    if (productoDAO.eliminarProducto(prodSeleccionado) >= 1) {
                        principal.lbl_estado.setText("Producto Eliminado");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto",
                                "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la tabla para eliminar.",
                                "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public void errorActualizarStock() {
            JOptionPane.showMessageDialog(null, "No se pudo editar el lote", "Error", JOptionPane.ERROR_MESSAGE);
        }

        private void actualizarPrecios() {
            cuotas = CuotasDAO.getInstance().getCuotasProd(prodSeleccionado.getId());
            calcularPrecios();
        }

        private void limpiarCamposStockNuevo() {
            txt_StockActual.setText("0");
            txt_stockIngresado.setText("0");
            txt_stockPedido.setText("0");
            txt_stockReservado.setText("0");
            txt_precioCompra.setText("0");
            date_fechaCompra.setDate(new Date());
        }

        private void insertarStockNuevo() {
            Stock s = new Stock();
            int sa = 0;
            int si = 0;
            int sp = 0;
            int sr = 0;
            float pc = 0f;
            if(Statics.Funciones.isNumeric(txt_StockActual.getText()))
                sa = Integer.parseInt(txt_StockActual.getText());
            if(Statics.Funciones.isNumeric(txt_stockIngresado.getText()))
                si = Integer.parseInt(txt_stockIngresado.getText());
            if(Statics.Funciones.isNumeric(txt_stockPedido.getText()))
                sp = Integer.parseInt(txt_stockPedido.getText());
            if(Statics.Funciones.isNumeric(txt_stockReservado.getText()))
                sr = Integer.parseInt(txt_stockReservado.getText());
            if(Statics.Funciones.isFloat(txt_precioCompra.getText()))
                pc = Float.parseFloat(txt_precioCompra.getText());
            s.setFechaCompra(new Timestamp(date_fechaCompra.getDate().getTime()));
            s.setStock_actual(sa);
            s.setStock_ingresado(si);
            s.setStock_pedido(sp);
            s.setStock_reservado(sr);
            s.setPrecio_compra(pc);
            productoDAO.insertarStock(s,prodSeleccionado.getId());
            limpiarCamposStockNuevo();
            if(editarStock != null && editarStock.isVisible()){
                sa+=prodSeleccionado.getStock()+si+sp;
                prodSeleccionado.setStock(sa);
                editarStock.actualizarTotal(sa);
                editarStock.cargarTablaProd(productoDAO.getStockProducto(prodSeleccionado.getId()));
            }else{
                stock.setText(sa+si+sp+"");
            }
        }

        private void habilitarCampos(boolean b, String boton) {
            btn_Guardar.setEnabled(b);
            btn_Cancelar.setEnabled(b);
            btn_Modificar.setEnabled(!b);
            btn_productoNuevo.setEnabled(!b);
            btn_eliminar.setEnabled(!b);
            btn_crearRubro.setEnabled(b);
            btn_producto_editarLote.setEnabled(b);
            txtf_productos_stockMinimo.setEnabled(b);
            btn_consultar_historial.setEnabled(b);
            tabla_producto_precioVenta.setEnabled(b);
            txtf_productos_nombre.setEnabled(b);
            if(boton.equals("NUEVO")){
                cuotas = CuotasDAO.getInstance().getCuotasProd(-1);
                calcularPrecios();
                txtf_productos_codigo.setEnabled(b);
            }
            txtf_productos_costo.setEnabled(b);
            txtf_productos_codigo_barra.setEnabled(b);
            txtf_productos_costoFlete.setEnabled(b);
            txtf_productos_observaciones.setEnabled(b);
            txtf_iva.setEnabled(b);
            txtf_imp_int_fijo.setEnabled(b);
            txtf_impuesto_int.setEnabled(b);
            txtf_sobretasa_iva.setEnabled(b);
            txtf_productos_venta.setEnabled(b);
            txtf_productos_codigo_ean.setEnabled(b);
            producto_plan.setEnabled(b);
            producto_Rubro.setEnabled(b);
            producto_Proveedor.setEnabled(b);
            producto_Rubro.removeAllItems();
            producto_Proveedor.removeAllItems();
            List<Proveedor> p = proveedoresDAO.buscarProveedorReducido("proveedor", "");
            if(p.isEmpty()){
                Proveedor aux = new Proveedor();
                aux.setNombre("No se encontraron proveedores");
                aux.setId(-1);
                producto_Proveedor.addItem(aux);
            }else{
                p.forEach((t) -> {
                    producto_Proveedor.addItem(t);
                });
            }
            List<Rubro> r = rubroDAO.getRubros();
            if(r.isEmpty()){
                Rubro aux = new Rubro();
                aux.setNombre("No se encontraron rubros");
                aux.setId(-1);
                producto_Rubro.addItem(aux);
            }else{
               r.forEach((t) -> {
                    producto_Rubro.addItem(t);
                });
            }
        }
        private boolean controlProductoNuevo(){
            if(txtf_productos_nombre.getText().isEmpty() || txtf_productos_codigo.getText().isEmpty()){
                return false;
            }
            try {
                Integer.parseInt(txtf_productos_codigo.getText());
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        private boolean guardarNuevoProd() {
            if(controlProductoNuevo()){
                prodSeleccionado.setNombre(txtf_productos_nombre.getText());
                prodSeleccionado.setEstado(1);
                prodSeleccionado.setCod(Integer.parseInt(txtf_productos_codigo.getText()));
                prodSeleccionado.setIdProveedorActual(((Proveedor)producto_Proveedor.getSelectedItem()).getId());
                int rs = productoDAO.productoEliminado(prodSeleccionado.getCod());
                    if(rs==-1 || rs == 0 ){
                        productoDAO.nuevoProducto(prodSeleccionado);
                        return true;
                    }else{
                        JOptionPane.showMessageDialog(null,"El codigo "+prodSeleccionado.getCod()+" ya pertenece a un producto ingresado.\n", 
                            "Error",  JOptionPane.ERROR_MESSAGE);
                        /* Esto es para reactivar codigos en caso de ser necesario descomentar y revisar DAO
                        int i = 0;
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int coincidencia = rs;
                        int resp = JOptionPane.showConfirmDialog(null,"El codigo "+prodSeleccionado.getCod()+" pertenece a un producto ingresado.\n ¿ esta seguro que desea sobreescribirlo?","Importante",dialogButton);
                        if(JOptionPane.YES_OPTION == resp){
                            productoDAO.eliminarProducto(prodSeleccionado);
                            productoDAO.actualizarProducto(prodSeleccionado);
                        }*/
                    }   
            }else
                JOptionPane.showMessageDialog(null,"Debe ingresar un nombre, código del producto (valor numérico) y asociar un proveedor", 
                            "Error",  JOptionPane.ERROR_MESSAGE);
            return false;
        }

        

    }

public class Pestaña3_dinamica {

        private DefaultTableModel model;
        private DefaultTableModel modelNotas;
        private DefaultTableModel modelNotaprodPedido;
        private DefaultTableModel modelNotaprodPedir;
        private Proveedor prov;
        private ProveedoresDAO proveedoresDAO;
        private PedidoDAO pedidoDAO;
        private ProductoDAO productosDAO;
        private List<Producto> listProd;
        private List<Producto> listProdNota;
        private List<Pedido> listPedidos;
        private facturaProveedor factura;
        private Pedido pedidoNuevo;
        private Pedido pedidoFact;
        private int rpSelected = -1;
        private int rfSelected = -1;
        private Producto prodSelect;
        private DefaultComboBoxModel modelCombo;
        private ButtonGroup grupo;
        private int posEdit;

        public Pestaña3_dinamica() {
            listProd = new ArrayList<>();
            listProdNota = new ArrayList<>();
            proveedoresDAO = ProveedoresDAO.getInstance();
            productosDAO = ProductoDAO.getInstance();
            pedidoDAO = PedidoDAO.getInstance();
            posEdit = -1;
            prodSelect = null;
            modelCombo = new DefaultComboBoxModel();
            model = (DefaultTableModel) tabla_productos_pedido.getModel();
            modelNotaprodPedido = (DefaultTableModel) tabla_nota_prodPedido.getModel();
            modelNotaprodPedir = (DefaultTableModel) tabla_nota_prodPedir.getModel();
            modelNotas = (DefaultTableModel) tabla_nota_PedidossProveedor.getModel();
            grupo = new ButtonGroup();
            grupo.add(rbtn_nota_prod_codigo);
            grupo.add(rbtn_nota_prod_prod);
            rbtn_nota_prod_codigo.setSelected(true);
            rbtn_nota_prod_codigo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        txtf_nota_prod_cod.setEditable(true);
                    } else {
                        txtf_nota_prod_cod.setEditable(false);
                    }
                }
            });
            rbtn_nota_prod_prod.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        txtf_nota_prod_nombre.setEnabled(true);
                        inicializarBusquedaNombre_Pedidos("");
                        txtf_nota_prod_cod.setText("");
                    }
                }
            });
            tabla_productos_pedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ListSelectionModel selectionModel = tabla_productos_pedido.getSelectionModel();
            selectionModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    try {
                        int pos = tabla_productos_pedido.getSelectedRow();
                        if (pos != -1) {
                            seleccionarProductoTabla(pos);
                        }
                    } catch (Exception ex) {
                    }
                }
            });
            txtf_nota_prod_nombre.getEditor().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String select = (String) txtf_nota_prod_nombre.getSelectedItem();
                    buscarProducto(select);
                }
            });
            Calendar c = Calendar.getInstance();
            Date hoy = c.getTime();
            date_nota_fecha_factura.setDate(hoy);
            date_nota_fecha_pago.setDate(hoy);
        }

        private void seleccionarProductoTabla(int pos) {
            renglonFactura rf = factura.getRenglones().get(pos);
            if (rf != null) {
                posEdit = pos;
                txtf_nota_prod_cod.setText("" + rf.getP().getCod());
                txtf_nota_prod_nombre.setSelectedItem(rf.getP().getNombre());
                txtf_nota_prod_cod.setEditable(false);
                txtf_nota_prod_nombre.setEnabled(false);
                txtf_nota_prod_stock.setText("" + rf.getCantidad());
                txtf_nota_prod_precio.setText("" + rf.getSubTotal());
                rbtn_nota_prod_codigo.setEnabled(false);
                rbtn_nota_prod_prod.setEnabled(false);
            }
        }

        private void buscarProducto(String aux_idP) {
            if (!aux_idP.isEmpty()) {
                if (rbtn_nota_prod_codigo.isSelected()) {
                    int idP = Integer.parseInt(aux_idP);
                    listProd = Pestaña1_dinamica.productosDAO.buscarProducto(idP);
                    if (listProd.size() == 1) {
                        txtf_nota_prod_cod.setText("" + listProd.get(0).getCod());
                        prodSelect = listProd.get(0);
                    }
                    if (prodSelect != null && prodSelect.getId() == idP) {
                        String[] aux = new String[1];
                        aux[0] = prodSelect.getNombre();
                        modelCombo = new DefaultComboBoxModel(aux);
                        txtf_nota_prod_nombre.setModel(modelCombo);
                    }
                } else if (rbtn_nota_prod_prod.isSelected()) {
                    listProd = Pestaña1_dinamica.productosDAO.buscarProducto("nombre", aux_idP);
                    if (listProd.size() == 1) {
                        txtf_nota_prod_cod.setText("" + listProd.get(0).getCod());
                        prodSelect = listProd.get(0);
                    }
                }
            }
        }

        private void inicializarBusquedaNombre_Pedidos(String valor) {
            listProd = Pestaña1_dinamica.productosDAO.buscarProducto("nombre",valor);
            String[] aux = new String[listProd.size()];
            for (int i = 0; i < listProd.size(); i++) {
                aux[i] = listProd.get(i).getNombre();
            }
            modelCombo = new DefaultComboBoxModel(aux);
            txtf_nota_prod_nombre.setModel(modelCombo);
            AutoCompleteDecorator.decorate(txtf_nota_prod_nombre);
        }

        private void agregarProducto() {
            if (pedidos.prov == null) {
                JOptionPane.showMessageDialog(null, "Primero debe seleccionar un proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String auxStock = txtf_nota_prod_stock.getText();
            String auxPrecio = txtf_nota_prod_precio.getText();
            try {
                int stock = Integer.parseInt(auxStock);
                float precio = Float.parseFloat(auxPrecio);
                if (prodSelect != null) {
                    renglonFactura rf = new renglonFactura();
                    rf.setCantidad(stock);
                    rf.setCosto(precio);
                    rf.setSubTotal(precio*stock);
                    Object[] obj = new Object[5];
                    if (posEdit != -1) {
                        rf.setP(factura.getRenglones().get(posEdit).getP());
                        factura.getRenglones().remove(posEdit);
                        factura.getRenglones().add(posEdit, rf);
                        model.setNumRows(0);
                        for (int i = 0; i < factura.getRenglones().size(); i++) {
                            obj[0] = factura.getRenglones().get(i).getP().getCod();
                            obj[1] = factura.getRenglones().get(i).getP().getNombre();
                            obj[2] = factura.getRenglones().get(i).getCantidad();
                            obj[3] = factura.getRenglones().get(i).getCosto();
                            obj[4] = factura.getRenglones().get(i).getSubTotal();
                            model.addRow(obj);
                        }
                        posEdit = -1;
                    } else {
                        rf.setP(prodSelect);
                        factura.getRenglones().add(rf);
                        obj[0] = rf.getP().getCod();
                        obj[1] = rf.getP().getNombre();
                        obj[2] = rf.getCantidad();
                        obj[3] = rf.getCosto();
                        obj[4] = rf.getSubTotal();
                        model.addRow(obj);
                    }
                    limpiarCampos();
                    
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El formato de stock o precio no es correcto.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void limpiarCampos() {
            prodSelect = null;
            posEdit = -1;
            rbtn_nota_prod_codigo.setEnabled(true);
            rbtn_nota_prod_prod.setEnabled(true);
            btn_nota_nueva.setEnabled(true);
            txtf_nota_prod_cod.setText("");
            txtf_nota_prod_stock.setText("");
            txtf_nota_prod_precio.setText("");
            modelNotas.setNumRows(0);
            if (rbtn_nota_prod_codigo.isSelected()) {
                txtf_nota_prod_cod.setEditable(true);
                txtf_nota_prod_cod.requestFocus();
            } else {
                txtf_nota_prod_nombre.setEnabled(true);
                txtf_nota_prod_nombre.requestFocus();
            }
            listProd = new ArrayList<>();
        }

        private void eliminarProducto() {
            int pos = tabla_productos_pedido.getSelectedRow();
            if (pos != -1 && pos <= factura.getRenglones().size()) {
                try {
                    factura.getRenglones().remove(pos);
                    model.removeRow(pos);
                    limpiarCampos();
                } catch (Exception ex) {
                    new Statics.ExceptionManager().saveDump(ex, "Eliminar producto en tabla de pedidos", Main.isProduccion);
                }
            }
        }

        private void guardarFactura() {
            int flete = 0;
            String numFac = "";
            String formaPago = (String) combo_forma_pago.getSelectedItem();
            Date fechaPago = date_nota_fecha_pago.getDate();
            Date fechaFac = date_nota_fecha_factura.getDate();
            //Controles
            if (Statics.Funciones.isNumeric(txtf_lote_prod_flete.getText())) {
                flete = Integer.parseInt(txtf_lote_prod_flete.getText());
            }
            if (Statics.Funciones.controlText(txtf_nota_prod_factura.getText())) {
                numFac = txtf_nota_prod_factura.getText();
            }else{
                JOptionPane.showMessageDialog(null, "Debe poner el numero de factura que figura en la misma.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(fechaFac.after(fechaPago) || fechaFac.equals(fechaPago)){
                factura.setEstado("PAGADA");
            }else{
                factura.setEstado("PENDIENTE");
            }
            if(factura.getRenglones().isEmpty()){
                JOptionPane.showMessageDialog(null, "No se puede generar una factura sin renglones.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //FIN CONTROLES
            factura.setNumFactura(numFac);
            factura.setIdProv(prov.getId());
            factura.setFlete(flete);
            factura.setTipo_factura((String) cbox_tipo_factura.getSelectedItem());
            factura.setFecha_factura(fechaFac);
            factura.setFecha_pago(fechaPago);
            factura.setForma_pago(formaPago);
            pedidoDAO.insertarFactura(factura);
            if(!pedidoFact.getNumPedido().equals(factura.getNumPedido())){
                pedidoDAO.checkStockPedido(factura,pedidoFact);
            }
            pedidoFact = null;
        }

        private void cargarTabla() {
            listPedidos = pedidoDAO.getPedidos(prov.getId());
            modelNotas.setNumRows(0);
            model.setRowCount(0);
            for(int i = 0; i < listPedidos.size();i++){
                Object[] c = new Object[3];
                c[0] = listPedidos.get(i).getNumPedido();
                c[1] = listPedidos.get(i).getFecha();
                c[2] = listPedidos.get(i).getEstado();
                modelNotas.addRow(c);
            }
        }

        private void cargarTablaFactura() {
            int pos = tabla_nota_PedidossProveedor.getSelectedRow();
            pedidoFact = listPedidos.get(pos);
            model.setNumRows(0);
            factura = new facturaProveedor();
            factura.setNumPedido(pedidoFact.getNumPedido());
            for(int i = 0 ; i < pedidoFact.getRenglones().size(); i++){
                renglonPedido rp = pedidoFact.getRenglones().get(i);
                renglonFactura rf = new renglonFactura(rp.getP(),rp.getCantidad(),rp.getCosto(),rp.getSubTotal());
                Object[] obj = new Object[5];
                obj[0] = rp.getP().getCod();
                obj[1] = rp.getP().getNombre();
                obj[2] = rp.getCantidad();
                obj[3] = rp.getCosto();
                obj[4] = rp.getSubTotal();
                factura.addRenglon(rf);
                model.addRow(obj);
            }
        }



        private void selectProducto(int pos) {
            prodSelect = listProdNota.get(pos);
            txtf_nota_costo.setText(prodSelect.getPrecioCosto()+"");
            txtf_nota_buscar.setText(prodSelect.getNombre());
            txtf_nota_cantidad.requestFocus();
            txtf_nota_cantidad.selectAll();
        }

        private void cambioBusqueda(String text) {
            if (text.isEmpty()) {
                cargarProductos(productosDAO.buscarProducto("nombre", ""));
            } else if (rbtn_productos_codigo.isSelected()) {
                try {
                    int cod = Integer.parseInt(text);
                    cargarProductos(productosDAO.buscarProducto(cod));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un c�digo",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    txtf_productos_buscar.setText("");
                }
            } else if (rbtn_productos_nombre.isSelected()) {
                cargarProductos(productosDAO.buscarProducto("nombre",text.toLowerCase()));
            } else {
                System.out.println("Error no selecciono tipo de busqueda");
            }
        }
        
        public void cargarProductos(List<Producto> list) {
            listProdNota = list;          
            try {
                
                modelNotaprodPedir.setNumRows(0);
                Object[] obj = new Object[4];
                for (int i = 0; i < list.size(); i++) {
                    obj[0] = list.get(i).getCod();
                    obj[1] = list.get(i).getNombre();
                    obj[2] = list.get(i).getStock();
                    obj[3] = list.get(i).getPrecioCosto();
                    modelNotaprodPedir.addRow(obj);
                }
                tabla_nota_prodPedido.setDefaultRenderer(Object.class, new MiRenderer(listProdNota));
            } catch (Exception ex) {
                new Statics.ExceptionManager().saveDump(ex, "Error al cargar producto en la tabla de nota nueva.", Main.isProduccion);
            }
        }

        private void agregarRenglonPedido(float parseFloat, int parseInt) {
            if(prodSelect == null){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            renglonPedido rp = new renglonPedido();
            rp.setCantidad(parseInt);
            rp.setCantFaltante(parseInt);
            rp.setCosto(parseFloat);
            rp.setP(prodSelect);
            rp.setSubTotal(parseFloat*parseInt);
            float rest = 0f;
            if(pedidoNuevo.getRenglones().contains(rp)){
                for(int i = 0; i< pedidoNuevo.getRenglones().size() ; i++){
                    if(pedidoNuevo.getRenglones().get(i).getP().getId() == prodSelect.getId()){
                        rest = pedidoNuevo.getRenglones().get(i).getSubTotal();
                        pedidoNuevo.getRenglones().get(i).setCantidad(pedidoNuevo.getRenglones().get(i).getCantidad()+rp.getCantidad());//suma cantidad del txt mas la que ya estaba
                        pedidoNuevo.getRenglones().get(i).setCantFaltante(pedidoNuevo.getRenglones().get(i).getCantidad()+rp.getCantidad());//suma cantidad del txt mas la que ya estaba
                        // pedidoNuevo.getRenglones().get(i).setCantidad(rp.getCantidad());//reemplaza la cantidad actual por la del txt
                        pedidoNuevo.getRenglones().get(i).setSubTotal(pedidoNuevo.getRenglones().get(i).getCantidad()*rp.getCosto());
                        pedidoNuevo.getRenglones().get(i).setCosto(rp.getCosto());
                        rp.setCantidad(pedidoNuevo.getRenglones().get(i).getCantidad());
                        rp.setCantFaltante(pedidoNuevo.getRenglones().get(i).getCantidad());
                        rp.setSubTotal(pedidoNuevo.getRenglones().get(i).getSubTotal());
                        modelNotaprodPedido.setValueAt(rp.getCantidad(), i, 2);
                        modelNotaprodPedido.setValueAt(rp.getCosto(), i, 3);
                        modelNotaprodPedido.setValueAt(rp.getSubTotal(), i, 4);
                    }
                }
            }else{
                pedidoNuevo.addRenglon(rp);
                Object[] o = new Object [5];
                o[0] = rp.getP().getCod();
                o[1] = rp.getP().getNombre();
                o[2] = rp.getCantidad();
                o[3] = rp.getCosto();
                o[4] = rp.getSubTotal();
                modelNotaprodPedido.addRow(o);
            }
            txtf_nota_buscar.setText("");
            txtf_nota_cantidad.setText("");
            txtf_nota_costo.setText("");
            if(Statics.Funciones.isFloat(txtf_nota_total.getText())){
                float total = Float.parseFloat(txtf_nota_total.getText());
                total += rp.getSubTotal() - rest;
                txtf_nota_total.setText(""+Statics.Funciones.redondeo2String(total));
            }else{
                float total = rp.getSubTotal();
                txtf_nota_total.setText(""+Statics.Funciones.redondeo2String(total));
            }
        }

        private void CrearPedido() {
            pedidoNuevo = pedidoDAO.insertPedido(prov.getId());
            if(pedidoNuevo != null){
                txtf_nota_nro.setText(pedidoNuevo.getNumPedido());
                lbl_nota_proveedor.setText(prov.getNombre());
            }else{
                JOptionPane.showMessageDialog(null, "Error al crear pedido",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }

        private void eliminarRP() {
            if(rpSelected != -1){
                pedidos.pedidoNuevo.getRenglones().remove(rpSelected);
            }else{
                JOptionPane.showMessageDialog(null, "Error no seleccionó ningun producto.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void terminarPedido() {
            if(Statics.Funciones.isFloat(txtf_nota_total.getText())){
                float total = Float.parseFloat(txtf_nota_total.getText());
                pedidoNuevo.setTotal(total);
                pedidoNuevo.setFecha(new Date());
            }
            System.out.println("va a insertar pedido");
            pedidoDAO.insertRP(pedidoNuevo);
            System.out.println("inserto el pedido");
            txtf_nota_buscar.setText("");
            txtf_nota_cantidad.setText("");
            txtf_nota_costo.setText("");
            modelNotaprodPedido.setRowCount(0);
            jDialogCrearNota.dispose();
            cargarTabla();
        }

        private void activarCampos(boolean flag) {
            btn_nota_nueva.setEnabled(flag);
            btn_factura_agregar.setEnabled(flag);
            btn_factura_eliminar.setEnabled(flag);
            txtf_nota_prod_cod.setEnabled(flag);
            txtf_nota_prod_nombre.setEnabled(flag);
            txtf_nota_prod_stock.setEnabled(flag);
            txtf_nota_prod_precio.setEnabled(flag);
            rbtn_nota_prod_codigo.setEnabled(flag);
            rbtn_nota_prod_prod.setEnabled(flag);
        }

        private void cancelarNota() {
            pedidoDAO.eliminarPedido(pedidoNuevo);
            jDialogCrearNota.dispose();
        }

    }
public class Pestaña4_dinamica extends Thread
{
  private Statics.Funciones funciones;
  private DAO.ProductoDAO productosDAO;
  private DefaultTableModel modeloStock;
  private List<Producto> listaStock;
  private ProductosView view;
  public Pestaña4_dinamica(ProductosView view){
      modeloStock=(DefaultTableModel) jTableTablaStock.getModel();
      modeloStock.setNumRows(0);
      listaStock= new ArrayList();
      productosDAO = ProductoDAO.getInstance();
      this.view=view;
              
  }
  public void cargarTablaStock(String seleccion) {
            
            listaStock.clear();
            listaStock=productosDAO.getStockFiltrado(seleccion);
            try {
                modeloStock.setNumRows(0);
                Object[] obj = new Object[4];
                
                for (int i = 0; i < listaStock.size(); i++) {
                    obj[0] = listaStock.get(i).getId();
                    obj[1] = listaStock.get(i).getNombre();
                    obj[2] = listaStock.get(i).getStock();
                    //obj[2] = listaStock.get(i).getPrecioVenta();
                    if(listaStock.get(i).getTipo()==1){
                        obj[3]="EXISTENTE";
                                }
                    else if(listaStock.get(i).getTipo()==2){
                        obj[3]="PEDIDO";
                    }
                   
                    modeloStock.addRow(obj);
                  
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listaStock.clear();
        }
 }

}