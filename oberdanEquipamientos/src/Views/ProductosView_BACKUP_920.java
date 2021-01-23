/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import DAO.CuotasDAO;
import DAO.ProductoDAO;
import DAO.ProveedoresDAO;
import DAO.RubroDAO;
import Models.*;
<<<<<<< HEAD
import Statics.Comunicacion;
import static Statics.Funciones.getSelection;
import java.awt.TextField;
=======
>>>>>>> fad66b7dfeb252ffa30079e792bca6463fa66307
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.view.JasperViewer;

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
    private ProductoDAO productoDAO;
    private Pestaña3_dinamica pedidos;
    private Pestaña4_dinamica PantallaStock;
    private JD_Producto_Nuevo productoNuevo;
    private JD_Producto_editarStock editarStock;
    /**
     * Creates new form productos
     */
    public ProductosView() {
        initComponents();
        Pestaña1_dinamica = new Pestaña1_dinamica(this);
        PantallaStock = new Pestaña4_dinamica(this);
        proveedoresDAO = ProveedoresDAO.getInstance();
        productoDAO = ProductoDAO.getInstance();
        rubroDAO = RubroDAO.getInstance();        
    }
public void limpiarCamposInventario() {
         txtf_productos_nombre.setText("");
         txtf_productos_costo.setText("");
         txtf_productos_codigo_barra.setText("");
         txtf_productos_costoFlete.setText("");
         txtf_productos_observaciones.setText("");
         stock.setText("");
         fecha_inicio.setCalendar(null);
         fecha_fin.setCalendar(null);
         txtf_productos_stockMinimo.setText("");
         txtf_productos_codigo.setText("");
         Producto_Proveedor = new JComboBox();
         Producto_Proveedor.setEnabled(false);
         Proveedor p = new Proveedor();
         p.setNombre("-No posee un proveedor asignado-");
         Producto_Proveedor.addItem(p);
         producto_Rubro = new JComboBox();
         producto_Rubro.setEnabled(false);
         Rubro r = new Rubro();
         r.setNombre("-No posee un rubro asignado-");
         producto_Rubro.addItem(r);
         chk_venta_iva.setSelected(false);
         btn_Guardar.setEnabled(false);
         btn_Modificar.setEnabled(false);
         btn_producto_editarLote.setEnabled(false);
         btn_consultar_historial.setEnabled(false);
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

        jDialog1 = new javax.swing.JDialog();
        buttonGroupFiltroStock = new javax.swing.ButtonGroup();
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
        jButton14 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
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
        jPanel29 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        Producto_Proveedor = new javax.swing.JComboBox<>();
        jPanel30 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jSeparator32 = new javax.swing.JSeparator();
        producto_Rubro = new javax.swing.JComboBox<>();
        btn_crearRubro = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        btn_proveedores_nuevo1 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        txtf_productos_nombre = new javax.swing.JTextField();
        txtf_productos_stockMinimo = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtf_productos_codigo = new javax.swing.JTextField();
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
        panel_pedidos = new javax.swing.JPanel();
        txtf_lote_prov = new javax.swing.JTextField();
        btn_lotes_prod_buscarprov = new javax.swing.JButton();
        jLabel165 = new javax.swing.JLabel();
        txtf_lote_prod_cod = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        txtf_lote_prod_stock = new javax.swing.JTextField();
        btn_lotes_prod_agregar = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        tabla_productos_pedido = new javax.swing.JTable();
        btn_lotes_prod_eliminar = new javax.swing.JButton();
        btn_lote_aceptar = new javax.swing.JButton();
        txtf_lote_prod_precio = new javax.swing.JTextField();
        jLabel169 = new javax.swing.JLabel();
        txtf_lote_prod_factura = new javax.swing.JTextField();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        combo_forma_pago = new javax.swing.JComboBox();
        date_lote_fecha_pago = new com.toedter.calendar.JDateChooser();
        jLabel172 = new javax.swing.JLabel();
        date_lote_fecha_factura = new com.toedter.calendar.JDateChooser();
        txtf_lote_prod_nombre = new javax.swing.JComboBox();
        rbtn_lote_prod_codigo = new javax.swing.JRadioButton();
        rbtn_lote_prod_prod = new javax.swing.JRadioButton();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jPanel81 = new javax.swing.JPanel();
        txtf_lote_prod_flete = new javax.swing.JTextField();
        jLabel211 = new javax.swing.JLabel();
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

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        txtf_productos_buscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_buscarCaretUpdate(evt);
            }
        });
        txtf_productos_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_productos_buscarActionPerformed(evt);
            }
        });
        txtf_productos_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_productos_buscarKeyTyped(evt);
            }
        });

        rbtn_productos_codigo.setSelected(true);
        rbtn_productos_codigo.setText("Código");

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

        jButton14.setBackground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Eliminar ");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
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
                        .addComponent(rbtn_productos_codigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtn_productos_nombre)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_proveedores_imprimirTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        jPanel22.setBackground(new java.awt.Color(252, 252, 252));
        jPanel22.setMinimumSize(new java.awt.Dimension(934, 590));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setMinimumSize(new java.awt.Dimension(877, 166));

        jLabel42.setText("Stock Mínimo:");

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        tabla_producto_precioVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Cuotas", "Tipo", "Precio Final", "Precio Cuota", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_producto_precioVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_producto_precioVentaMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tabla_producto_precioVenta);

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
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_venta_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel26Layout.createSequentialGroup()
                                    .addComponent(producto_plan, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_crearPago))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53)
                    .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(producto_plan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_crearPago, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chk_venta_iva)
                .addContainerGap())
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
                .addComponent(btn_producto_editarLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator22)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70)
                    .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_producto_editarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addContainerGap(14, Short.MAX_VALUE))
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
                .addContainerGap(18, Short.MAX_VALUE))
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

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel54.setText("Flete $");

        txtf_productos_costoFlete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_costoFlete.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_costoFleteCaretUpdate(evt);
            }
        });

        txtf_productos_codigo_barra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_codigo_barra.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_codigo_barraCaretUpdate(evt);
            }
        });
        txtf_productos_codigo_barra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_productos_codigo_barraActionPerformed(evt);
            }
        });

        txtf_productos_costo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_productos_costo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_productos_costoCaretUpdate(evt);
            }
        });

        jLabel176.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel176.setText("Costo e Impuestos");

        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel237.setText("Codigo de barra");

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel55.setText("Precio Costo $");

        jLabel52.setText("Observaciones:");

        txtf_productos_observaciones.setColumns(20);
        txtf_productos_observaciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtf_productos_observaciones.setRows(2);
        jScrollPane9.setViewportView(txtf_productos_observaciones);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel56.setText("Sobretasa I.V.A");

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel59.setText("Tasa I.V.A");

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel60.setText("Impuesto interno %");

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel61.setText("Imp interno fijo %");

        txtf_iva.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_iva.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_ivaCaretUpdate(evt);
            }
        });

        txtf_sobretasa_iva.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_sobretasa_iva.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_sobretasa_ivaCaretUpdate(evt);
            }
        });

        txtf_impuesto_int.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_impuesto_int.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_impuesto_intCaretUpdate(evt);
            }
        });

        txtf_imp_int_fijo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtf_imp_int_fijo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtf_imp_int_fijoCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel176)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator30))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtf_productos_costo)
                            .addComponent(txtf_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_sobretasa_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtf_productos_costoFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_impuesto_int, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_imp_int_fijo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_productos_codigo_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
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
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_impuesto_int, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_sobretasa_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_imp_int_fijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtf_productos_codigo_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel237))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Proveedores");

        Producto_Proveedor.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Producto_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel57)
                    .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Producto_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Rubro");

        producto_Rubro.setEnabled(false);

        btn_crearRubro.setBackground(new java.awt.Color(255, 255, 255));
        btn_crearRubro.setText("Crear nuevo");
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
                .addContainerGap(24, Short.MAX_VALUE))
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
        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.setEnabled(false);
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_proveedores_nuevo1.setBackground(new java.awt.Color(255, 255, 255));
        btn_proveedores_nuevo1.setText("Nuevo ");
        btn_proveedores_nuevo1.setMaximumSize(new java.awt.Dimension(133, 39));
        btn_proveedores_nuevo1.setMinimumSize(new java.awt.Dimension(133, 39));
        btn_proveedores_nuevo1.setPreferredSize(new java.awt.Dimension(133, 39));
        btn_proveedores_nuevo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_proveedores_nuevo1MouseClicked(evt);
            }
        });
        btn_proveedores_nuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedores_nuevo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(btn_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_proveedores_nuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_proveedores_nuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel44.setText("Nombre:");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_productos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_productos_stockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_productos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel42)
                    .addComponent(txtf_productos_stockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(745, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(245, 245, 245));
        jPanel25.setMinimumSize(new java.awt.Dimension(877, 31));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel62.setText("Producto");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel63.setText("#Código:");

        txtf_productos_codigo.setEditable(false);
        txtf_productos_codigo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtf_productos_codigo.setPreferredSize(new java.awt.Dimension(70, 28));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_productos_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel63)
                    .addComponent(txtf_productos_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout panel_productos_detalleLayout = new javax.swing.GroupLayout(panel_productos_detalle);
        panel_productos_detalle.setLayout(panel_productos_detalleLayout);
        panel_productos_detalleLayout.setHorizontalGroup(
            panel_productos_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productos_detalleLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        panel_productos_detalleLayout.setVerticalGroup(
            panel_productos_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        panel_pedidos.setBackground(new java.awt.Color(245, 245, 245));

        txtf_lote_prov.setEditable(false);
        txtf_lote_prov.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btn_lotes_prod_buscarprov.setBackground(new java.awt.Color(255, 255, 255));
        btn_lotes_prod_buscarprov.setText("Buscar proveedor");
        btn_lotes_prod_buscarprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lotes_prod_buscarprovActionPerformed(evt);
            }
        });

        jLabel165.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel165.setText("Proveedor");

        txtf_lote_prod_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtf_lote_prod_codKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_lote_prod_codKeyTyped(evt);
            }
        });

        jLabel168.setText("Stock:");

        btn_lotes_prod_agregar.setBackground(new java.awt.Color(0, 153, 0));
        btn_lotes_prod_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_lotes_prod_agregar.setText("+ ");
        btn_lotes_prod_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lotes_prod_agregarActionPerformed(evt);
            }
        });

        tabla_productos_pedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Vencimiento", "Stock", "Precio"
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

        btn_lotes_prod_eliminar.setBackground(new java.awt.Color(204, 51, 0));
        btn_lotes_prod_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_lotes_prod_eliminar.setText("- ");
        btn_lotes_prod_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lotes_prod_eliminarActionPerformed(evt);
            }
        });

        btn_lote_aceptar.setBackground(new java.awt.Color(255, 255, 255));
        btn_lote_aceptar.setText("Guardar");
        btn_lote_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lote_aceptarActionPerformed(evt);
            }
        });

        txtf_lote_prod_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtf_lote_prod_precioKeyPressed(evt);
            }
        });

        jLabel169.setText("Costo:");

        jLabel170.setText("Numero de factura");

        jLabel171.setText("Forma de Pago:");

        combo_forma_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo", "Diferido" }));

        jLabel172.setText("Fecha");

        txtf_lote_prod_nombre.setEnabled(false);

        rbtn_lote_prod_codigo.setBackground(new java.awt.Color(245, 245, 245));
        rbtn_lote_prod_codigo.setText("Código");

        rbtn_lote_prod_prod.setBackground(new java.awt.Color(245, 245, 245));
        rbtn_lote_prod_prod.setText("Producto");

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
                .addComponent(txtf_lote_prod_flete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel211)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_lote_prod_flete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel211))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_pedidosLayout = new javax.swing.GroupLayout(panel_pedidos);
        panel_pedidos.setLayout(panel_pedidosLayout);
        panel_pedidosLayout.setHorizontalGroup(
            panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_pedidosLayout.createSequentialGroup()
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_lote_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator14)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                                .addComponent(jLabel171)
                                                .addGap(0, 73, Short.MAX_VALUE))
                                            .addComponent(combo_forma_pago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(date_lote_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel212))
                                        .addGap(61, 61, 61))
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                                .addComponent(jLabel170)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtf_lote_prod_factura))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel172)
                                            .addComponent(date_lote_fecha_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18))
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtf_lote_prov, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                                                .addComponent(btn_lotes_prod_buscarprov, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel165))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtf_lote_prod_cod)
                                            .addComponent(rbtn_lote_prod_codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbtn_lote_prod_prod)
                                            .addComponent(txtf_lote_prod_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel168)
                                            .addComponent(txtf_lote_prod_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                                .addComponent(txtf_lote_prod_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_lotes_prod_agregar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_lotes_prod_eliminar))
                                            .addComponent(jLabel169)))
                                    .addComponent(jSeparator16)
                                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                                        .addComponent(jLabel213)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane27))))))
                .addContainerGap())
        );
        panel_pedidosLayout.setVerticalGroup(
            panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_pedidosLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel213)
                    .addComponent(jLabel165))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel169)
                            .addComponent(jLabel168)
                            .addComponent(rbtn_lote_prod_prod)
                            .addComponent(rbtn_lote_prod_codigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btn_lotes_prod_eliminar)
                            .addComponent(txtf_lote_prod_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_lote_prod_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_lote_prod_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_lote_prod_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lotes_prod_agregar))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(panel_pedidosLayout.createSequentialGroup()
                        .addComponent(txtf_lote_prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_lotes_prod_buscarprov, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(jLabel170)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtf_lote_prod_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_pedidosLayout.createSequentialGroup()
                                .addComponent(jLabel172)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date_lote_fecha_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel171)
                            .addComponent(jLabel212))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(combo_forma_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_lote_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(btn_lote_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        tabla_productos.addTab("Pedidos", panel_pedidos);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addGap(249, 249, 249))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 216, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabla_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabla_productos, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_lote_prod_precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_lote_prod_precioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pedidos.agregarProducto();
        }
    }//GEN-LAST:event_txtf_lote_prod_precioKeyPressed

    private void btn_lote_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lote_aceptarActionPerformed
        pedidos.guardarPedido();
        Pestaña1_dinamica.cambioBusqueda(txtf_productos_buscar.getText().toString());
    }//GEN-LAST:event_btn_lote_aceptarActionPerformed

    private void btn_lotes_prod_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lotes_prod_eliminarActionPerformed
        pedidos.eliminarProducto();
    }//GEN-LAST:event_btn_lotes_prod_eliminarActionPerformed

    private void btn_lotes_prod_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lotes_prod_agregarActionPerformed
        pedidos.agregarProducto();
    }//GEN-LAST:event_btn_lotes_prod_agregarActionPerformed

    private void txtf_lote_prod_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_lote_prod_codKeyTyped
        char c = evt.getKeyChar();
        if (this.rbtn_lote_prod_codigo.isSelected()) {
            if ((c < '0' || c > '9')) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtf_lote_prod_codKeyTyped

    private void txtf_lote_prod_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_lote_prod_codKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pedidos.buscarProducto(txtf_lote_prod_cod.getText());
        }
    }//GEN-LAST:event_txtf_lote_prod_codKeyPressed

    private void btn_lotes_prod_buscarprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lotes_prod_buscarprovActionPerformed
        JD_Proveedor_Buscador provBuscador = new JD_Proveedor_Buscador(new JDialog(), true) {

            
            public void getIDProveedor(int id, String atributo) {
                if (id != -1) {
                    pedidos.prov = new Proveedor();
                    pedidos.prov.setId(id);
                    pedidos.prov.setNombre(atributo);
                    txtf_lote_prov.setText(id + " - " + atributo);
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

    private void txtf_productos_costoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_costoCaretUpdate
        Pestaña1_dinamica.calcularPrecios();
    }//GEN-LAST:event_txtf_productos_costoCaretUpdate

    private void txtf_productos_costoFleteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_costoFleteCaretUpdate
        Pestaña1_dinamica.calcularPrecios();
    }//GEN-LAST:event_txtf_productos_costoFleteCaretUpdate

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        Pestaña1_dinamica.actualizarProduto();
        limpiarCamposInventario();
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_consultar_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultar_historialActionPerformed
        Pestaña1_dinamica.getunidadesVendidas();
    }//GEN-LAST:event_btn_consultar_historialActionPerformed

    private void btn_producto_editarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_editarLoteActionPerformed
        if (Pestaña1_dinamica.prodSeleccionado != null) {
            editarStock = new JD_Producto_editarStock(new JFrame(), true, Pestaña1_dinamica.prodSeleccionado) {
                
                public float getCantidadIgresada() {
                    return 1f;
                }
            };
            editarStock.setLocationRelativeTo(null);
            editarStock.setVisible(true);
        }
    }//GEN-LAST:event_btn_producto_editarLoteActionPerformed

    private void btn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModificarActionPerformed
        btn_Cancelar.setEnabled(true);
        btn_Guardar.setEnabled(true);
        producto_plan.setEnabled(true);
        producto_Rubro.setEnabled(true);
        Producto_Proveedor.setEnabled(true);
        btn_producto_editarLote.setEnabled(true);
        producto_Rubro.removeAllItems();
        Producto_Proveedor.removeAllItems();
        List<Proveedor> p = proveedoresDAO.buscarProveedorReducido("proveedor", "");
        if(p.isEmpty()){
            Proveedor aux = new Proveedor();
            aux.setNombre("No se encontraron proveedores");
            aux.setId(-1);
            Producto_Proveedor.addItem(aux);
        }else{
            p.forEach((t) -> {
                Producto_Proveedor.addItem(t);
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
        
    }//GEN-LAST:event_btn_ModificarActionPerformed

    private void btn_proveedores_nuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedores_nuevo1ActionPerformed
        productoNuevo = new JD_Producto_Nuevo(new JFrame(), true,this);
        productoNuevo.setLocationRelativeTo(null);
        productoNuevo.setModal(true);
        productoNuevo.setVisible(true);
    }//GEN-LAST:event_btn_proveedores_nuevo1ActionPerformed

    private void btn_proveedores_nuevo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_proveedores_nuevo1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_proveedores_nuevo1MouseClicked

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

    private void chk_venta_ivaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_venta_ivaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_venta_ivaItemStateChanged

    private void tabla_producto_precioVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_producto_precioVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_producto_precioVentaMouseClicked

    private void txtf_productos_codigo_barraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_productos_codigo_barraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_productos_codigo_barraActionPerformed

    private void txtf_productos_codigo_barraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_productos_codigo_barraCaretUpdate

    }//GEN-LAST:event_txtf_productos_codigo_barraCaretUpdate

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Pestaña1_dinamica.eliminar();
        limpiarCamposInventario();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        btn_Cancelar.setEnabled(false);
        btn_Guardar.setEnabled(false);
        producto_plan.setEnabled(false);
        producto_Rubro.setEnabled(false);
        btn_producto_editarLote.setEnabled(false);
        limpiarCamposInventario();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void txtf_productos_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_productos_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_productos_buscarActionPerformed

    private void txtf_ivaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_ivaCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_ivaCaretUpdate

    private void txtf_sobretasa_ivaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_sobretasa_ivaCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_sobretasa_ivaCaretUpdate

    private void txtf_impuesto_intCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_impuesto_intCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_impuesto_intCaretUpdate

    private void txtf_imp_int_fijoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtf_imp_int_fijoCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_imp_int_fijoCaretUpdate

    private void btn_crearRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearRubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_crearRubroActionPerformed

    private void btn_crearPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_crearPagoActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Models.Proveedor> Producto_Proveedor;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_Modificar;
    private javax.swing.JButton btn_consultar_historial;
    private javax.swing.JButton btn_crearPago;
    private javax.swing.JButton btn_crearRubro;
    private javax.swing.JButton btn_editDevolucion;
    private javax.swing.JButton btn_eliminar_devolucion;
    private javax.swing.JButton btn_guardar_devolucion;
    private javax.swing.JButton btn_lote_aceptar;
    private javax.swing.JButton btn_lotes_prod_agregar;
    private javax.swing.JButton btn_lotes_prod_buscarprov;
    private javax.swing.JButton btn_lotes_prod_eliminar;
    private javax.swing.JButton btn_nuevaDevolucion;
    private javax.swing.JButton btn_producto_editarLote;
    private javax.swing.JButton btn_proveedores_imprimirTodo;
    private javax.swing.JButton btn_proveedores_nuevo1;
    private javax.swing.ButtonGroup buttonGroupFiltroStock;
    private javax.swing.JCheckBox chk_venta_iva;
    private javax.swing.JComboBox combo_forma_pago;
    private com.toedter.calendar.JDateChooser date_lote_fecha_factura;
    private com.toedter.calendar.JDateChooser date_lote_fecha_pago;
    private com.toedter.calendar.JDateChooser fecha_fin;
    private com.toedter.calendar.JDateChooser fecha_inicio;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonImprimirStock;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel176;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLbl_devo_nuevaDevo;
    private javax.swing.JLabel jLbl_devo_prodAsociado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel39;
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
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane27;
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
    private javax.swing.JPanel panel_devos;
    private javax.swing.JPanel panel_pedidos;
    private javax.swing.JPanel panel_productos_detalle;
    private javax.swing.JComboBox<Models.Rubro> producto_Rubro;
    private javax.swing.JComboBox<Models.Cuota> producto_plan;
    protected javax.swing.JRadioButton rbtn_devolucion_codigo;
    protected javax.swing.JRadioButton rbtn_devolucion_nombre;
    private javax.swing.JRadioButton rbtn_lote_prod_codigo;
    private javax.swing.JRadioButton rbtn_lote_prod_prod;
    protected javax.swing.JRadioButton rbtn_productos_codigo;
    protected javax.swing.JRadioButton rbtn_productos_nombre;
    private javax.swing.JTextField stock;
    private javax.swing.JTable tabla_devo_preventistas;
    private javax.swing.JTable tabla_devos;
    private javax.swing.JTable tabla_producto_precioVenta;
    private javax.swing.JTabbedPane tabla_productos;
    private javax.swing.JTable tabla_productos_busqueda;
    private javax.swing.JTable tabla_productos_pedido;
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
    private javax.swing.JTextField txtf_lote_prod_cod;
    private javax.swing.JTextField txtf_lote_prod_factura;
    private javax.swing.JTextField txtf_lote_prod_flete;
    private javax.swing.JComboBox txtf_lote_prod_nombre;
    private javax.swing.JTextField txtf_lote_prod_precio;
    private javax.swing.JTextField txtf_lote_prod_stock;
    private javax.swing.JTextField txtf_lote_prov;
    private javax.swing.JTextField txtf_nombreDevo;
    private javax.swing.JTextField txtf_porcentaje;
    protected javax.swing.JTextField txtf_productos_buscar;
    protected javax.swing.JTextField txtf_productos_codigo;
    private javax.swing.JTextField txtf_productos_codigo_barra;
    private javax.swing.JTextField txtf_productos_costo;
    private javax.swing.JTextField txtf_productos_costoFlete;
    protected javax.swing.JTextField txtf_productos_nombre;
    private javax.swing.JTextArea txtf_productos_observaciones;
    protected javax.swing.JTextField txtf_productos_stockMinimo;
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
                    stock.setText("" + prodSeleccionado.getStock());
                    System.out.println("prodSeleccionado.getIdProveedorActual() = " + prodSeleccionado.getIdProveedorActual());
                    Producto_Proveedor = new JComboBox();
                    Producto_Proveedor.setEnabled(false);
                    producto_Rubro = new JComboBox();
                    producto_Rubro.setEnabled(false);
                    if (prodSeleccionado.getIdProveedorActual() == -1) {
                        Proveedor p = new Proveedor();
                        p.setNombre("-No posee un proveedor asignado-");
                        Producto_Proveedor.addItem(p);
                    } else {
                        Proveedor p = proveedoresDAO.getProveedor(prodSeleccionado.getIdProveedorActual());
                        Producto_Proveedor.addItem(p);
                    }
                    chk_venta_iva.setSelected(prodSeleccionado.isSinIva());
                }
            }
        }

        private void calcularPrecios() {
            
            if (prodSeleccionado != null) {
                String auxCosto = txtf_productos_costo.getText();
                String auxFlete = txtf_productos_costoFlete.getText();
                DefaultTableModel tablaProdPrecios = (DefaultTableModel) tabla_producto_precioVenta.getModel();
                float costo, flete,precioVenta, precioCuota;

                try {
                    if (auxCosto.isEmpty()) {
                        costo = 0;
                    } else {
                        costo = Float.parseFloat(auxCosto);
                    }
                    if (auxFlete.isEmpty()) {
                        flete = 0;
                    } else {
                        flete = Float.parseFloat(auxFlete);
                    }
                    
                    float precio_parcial = costo + flete;
                    try {
                        tablaProdPrecios.setNumRows(0);
                        Object[] obj = new Object[5];
                        for (int i = 0; i < cuotas.size(); i++) {
                            obj[0] = cuotas.get(i).getCantidad();
                            obj[1] = cuotas.get(i).getTipo();
                            float precio_final = precio_parcial * ((cuotas.get(i).getPorcentajeExtra()/100) + 1);
                            obj[2] = precio_final;
                            obj[3] = precio_final / cuotas.get(i).getCantidad();
                            obj[4] = cuotas.get(i).getActiva();
                            tablaProdPrecios.addRow(obj);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        private void actualizarProduto() {
            System.err.println("entro actualizar producto");
            if (prodSeleccionado != null) {
                prodSeleccionado.setNombre(txtf_productos_nombre.getText());
                prodSeleccionado.setStockMin(Integer.parseInt(txtf_productos_stockMinimo.getText()));
                prodSeleccionado.setObservaciones(txtf_productos_observaciones.getText());
                prodSeleccionado.setPrecioCosto(Float.parseFloat(txtf_productos_costo.getText()));
                prodSeleccionado.setCostoFlete(Integer.parseInt(txtf_productos_costoFlete.getText()));
                
                if(chk_venta_iva.isSelected()){
                    prodSeleccionado.setSinIva(true);
                }else{
                    prodSeleccionado.setSinIva(false);
                }
                productosDAO.actualizarProducto(prodSeleccionado);
            } else {
                System.err.println("Error: prodSeleccionado null");
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
        
        public void cargarProveedores(List<Proveedor> list) {
        }

        
        public void productoCargado() {
            productoNuevo.dispose();
            principal.lbl_estado.setText("Producto agregado");
            cambioBusqueda("");
        }
        
        public void devolucionCargada() {
            habilitarPanelNuevaDevo(false);
            principal.lbl_estado.setText("Devolucion agregada");
            devolucionBusqueda("");
        }
        
        public void productoNoCargado() {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el producto. Es posible que el código de producto ya exista!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        
        public void productoActualizado() {
           principal.lbl_estado.setText("Producto Actualizado");
            Pestaña1_dinamica.cambioBusqueda(txtf_productos_buscar.getText().toString());
        }

        
        public void productoNoActualizado() {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar los datos del producto",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
                    if (productoDAO.eliminarProducto(prodSeleccionado) == 1) {
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

    }

public class Pestaña3_dinamica {

        private DefaultTableModel model;
        private Proveedor prov;
        private List<Producto> listProd;
        private Producto prodSelect;
        private DefaultComboBoxModel modelCombo;
        private ButtonGroup grupo;
        private int posEdit;

        public Pestaña3_dinamica() {
            listProd = new ArrayList<>();
            posEdit = -1;
            prodSelect = null;
            modelCombo = new DefaultComboBoxModel();
            model = (DefaultTableModel) tabla_productos_pedido.getModel();
            grupo = new ButtonGroup();
            grupo.add(rbtn_lote_prod_codigo);
            grupo.add(rbtn_lote_prod_prod);
            rbtn_lote_prod_codigo.setSelected(true);
            rbtn_lote_prod_codigo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        txtf_lote_prod_cod.setEditable(true);
                    } else {
                        txtf_lote_prod_cod.setEditable(false);
                    }
                }
            });
            rbtn_lote_prod_prod.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        txtf_lote_prod_nombre.setEnabled(true);
                        inicializarBusquedaNombre_Pedidos("");
                        txtf_lote_prod_cod.setText("");

                    } else {
                        txtf_lote_prod_nombre.setEnabled(false);
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
            txtf_lote_prod_nombre.getEditor().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String select = (String) txtf_lote_prod_nombre.getSelectedItem();
                    buscarProducto(select);
                }
            });
            Calendar c = Calendar.getInstance();
            Date hoy = c.getTime();
            date_lote_fecha_factura.setDate(hoy);
        }

        private void seleccionarProductoTabla(int pos) {
            prodSelect = listProd.get(pos);
            if (prodSelect != null) {
                posEdit = pos;
                txtf_lote_prod_cod.setText("" + prodSelect.getId());
                txtf_lote_prod_nombre.setSelectedItem(prodSelect.getNombre());
                txtf_lote_prod_cod.setEditable(false);
                txtf_lote_prod_nombre.setEnabled(false);
                txtf_lote_prod_stock.setText("" + prodSelect.getStock());
                txtf_lote_prod_precio.setText("" + prodSelect.getPrecioCosto());
                rbtn_lote_prod_codigo.setEnabled(false);
                rbtn_lote_prod_prod.setEnabled(false);
            }
        }

        private void buscarProducto(String aux_idP) {
            /*if (!aux_idP.isEmpty()) {
                if (rbtn_lote_prod_codigo.isSelected()) {
                    int idP = Integer.parseInt(aux_idP);
                    prodSelect = Pestaña1_dinamica.productosDAO.getItemInverntarioActivo(idP);
                    if (prodSelect != null && prodSelect.getId() == idP) {
                        String[] aux = new String[1];
                        aux[0] = prodSelect.getNombre();
                        modelCombo = new DefaultComboBoxModel(aux);
                        txtf_lote_prod_nombre.setModel(modelCombo);
                    }
                } else if (rbtn_lote_prod_prod.isSelected()) {
                    List<Producto> list = Pestaña1_dinamica.productosDAO.searchProductName(aux_idP);
                    if (list.size() == 1) {
                        txtf_lote_prod_cod.setText("" + list.get(0).getId());
                        prodSelect = list.get(0);
                    }
                }
            }*/
        }

        private void inicializarBusquedaNombre_Pedidos(String valor) {
           /* listProd = Pestaña1_dinamica.productosDAO.searchProductName(valor);
            String[] aux = new String[listProd.size()];
            for (int i = 0; i < listProd.size(); i++) {
                aux[i] = listProd.get(i).getNombre();
            }
            modelCombo = new DefaultComboBoxModel(aux);
            txtf_lote_prod_nombre.setModel(modelCombo);
            AutoCompleteDecorator.decorate(txtf_lote_prod_nombre);*/
        }

        private void agregarProducto() {
            if (pedidos.prov == null) {
                JOptionPane.showMessageDialog(null, "Primero debe seleccionar un proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String auxStock = txtf_lote_prod_stock.getText();
            String auxPrecio = txtf_lote_prod_precio.getText();
            try {
                int stock = Integer.parseInt(auxStock);
                float precio = Float.parseFloat(auxPrecio);
                if (prodSelect != null) {
                    prodSelect.setPrecioCosto(precio);
                    prodSelect.setStock(stock);
                    Object[] obj = new Object[5];
                    if (posEdit != -1) {
                        listProd.remove(posEdit);
                        listProd.add(posEdit, prodSelect);
                        model.setNumRows(0);
                        for (int i = 0; i < listProd.size(); i++) {
                            obj[0] = listProd.get(i).getId();
                            obj[1] = listProd.get(i).getNombre();
                            obj[2] = listProd.get(i).getStock();
                            obj[3] = listProd.get(i).getPrecioCosto();
                            model.addRow(obj);
                        }
                        posEdit = -1;
                    } else {
                        listProd.add(prodSelect);
                        obj[0] = prodSelect.getId();
                        obj[1] = prodSelect.getNombre();
                        obj[2] = prodSelect.getStock();
                        obj[3] = prodSelect.getPrecioCosto();
                        model.addRow(obj);
                    }
                    limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        private void limpiarCampos() {
            prodSelect = null;
            posEdit = -1;
            rbtn_lote_prod_codigo.setEnabled(true);
            rbtn_lote_prod_prod.setEnabled(true);
            txtf_lote_prod_cod.setText("");
            txtf_lote_prod_stock.setText("");
            txtf_lote_prod_precio.setText("");
            if (rbtn_lote_prod_codigo.isSelected()) {
                txtf_lote_prod_cod.setEditable(true);
                txtf_lote_prod_cod.requestFocus();
            } else {
                txtf_lote_prod_nombre.setEnabled(true);
                txtf_lote_prod_nombre.requestFocus();
            }
            listProd = new ArrayList<>();
        }

        private void eliminarProducto() {
            int pos = tabla_productos_pedido.getSelectedRow();
            if (pos != -1 && pos <= listProd.size()) {
                try {
                    listProd.remove(pos);
                    model.removeRow(pos);
                    limpiarCampos();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void guardarPedido() {
            if (txtf_lote_prod_flete.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el flete",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int flete = Integer.parseInt(txtf_lote_prod_flete.getText());
                String formaPago = (String) combo_forma_pago.getSelectedItem();
                Date fechaPago = date_lote_fecha_pago.getDate();
                String numFac = txtf_lote_prod_factura.getText();
                Date fechaFac = date_lote_fecha_factura.getDate();

                Pedido p = new Pedido();
                p.setNumFactura(numFac);
                p.setIdProv(prov.getId());
                p.setFlete(flete);
                p.setFormaPago(formaPago);
                p.setFecha(fechaFac);
                p.setFechaPago(fechaPago);
                for (int i = 0; i < listProd.size(); i++) {
                    Producto prod = listProd.get(i);
                   // Pestaña1_dinamica.productosDAO.addStock(prod.getId(), prod.getStock());
                }
            }
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