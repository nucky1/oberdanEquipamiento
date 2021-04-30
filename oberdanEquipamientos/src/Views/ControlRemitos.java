/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import DAO.CreditosDAO;
import DAO.DireccionesDAO;
import DAO.RemitosDAO;
import Models.Remito;
import Models.renglonRemito;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aguss2
 */
public class ControlRemitos extends javax.swing.JPanel {
    private CreditosDAO creditoDAO;
    private RemitosDAO remitoDAO;
    private DireccionesDAO direccionesDAO;
    private ArrayList<Remito> listRemitoPend;
    private ArrayList<Remito> listRemitoResult;
    private ArrayList<renglonRemitoComponent> listPanelCargarNroSerie;
    private Remito remitoSelected;
    
    /**
     * Creates new form ControlRemitos
     */
    public ControlRemitos() {
        initComponents();
        creditoDAO = CreditosDAO.getInstance();
        remitoDAO = RemitosDAO.getInstance();
        direccionesDAO = DireccionesDAO.getInstance();
        listRemitoPend = new ArrayList<>();
        listRemitoResult = new ArrayList<>();
        cargarRemitos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ActualizarRenglonRemito = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        table_remitosPendientes = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_buscarCred = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Comentarios = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_NroRemito = new javax.swing.JTextField();
        txt_NombreCliente = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        cbox_entregado = new javax.swing.JCheckBox();
        jLabel40 = new javax.swing.JLabel();
        date_fechaEntrega = new com.toedter.calendar.JDateChooser();
        cbox_anulacion = new javax.swing.JCheckBox();
        cbox_sucursal = new javax.swing.JComboBox<>();
        btn_cancelar = new javax.swing.JButton();
        btn_guardarRemito = new javax.swing.JButton();
        btn_guardarRemito1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_remitosResueltos = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txt_Importe = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txt_CantRemitos = new javax.swing.JTextField();
        txt_NombreSr_es = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        btn_imprimir = new javax.swing.JButton();

        ActualizarRenglonRemito.setBackground(new java.awt.Color(255, 255, 255));
        ActualizarRenglonRemito.setMinimumSize(new java.awt.Dimension(650, 400));

        jScrollPane4.setToolTipText("");
        jScrollPane4.setAutoscrolls(true);
        jScrollPane4.setDoubleBuffered(true);
        jScrollPane4.setMaximumSize(new java.awt.Dimension(600, 350));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(600, 350));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(600, 350));

        jPanel1.setMaximumSize(new java.awt.Dimension(650, 250));
        jPanel1.setMinimumSize(new java.awt.Dimension(650, 250));
        jPanel1.setLayout(new java.awt.GridLayout(1, 1, 5, 5));
        jScrollPane4.setViewportView(jPanel1);

        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(393, 70));
        jPanel2.setMinimumSize(new java.awt.Dimension(393, 70));
        jPanel2.setPreferredSize(new java.awt.Dimension(393, 70));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setText("Cancelar");
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 86, 48));

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 83, 48));

        javax.swing.GroupLayout ActualizarRenglonRemitoLayout = new javax.swing.GroupLayout(ActualizarRenglonRemito.getContentPane());
        ActualizarRenglonRemito.getContentPane().setLayout(ActualizarRenglonRemitoLayout);
        ActualizarRenglonRemitoLayout.setHorizontalGroup(
            ActualizarRenglonRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActualizarRenglonRemitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ActualizarRenglonRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ActualizarRenglonRemitoLayout.setVerticalGroup(
            ActualizarRenglonRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ActualizarRenglonRemitoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        table_remitosPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Remito N°", "Cred N°", "Fecha", "Cliente", "Domicilio", "Entregado", "Fecha Entrega"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_remitosPendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_remitosPendientesMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(table_remitosPendientes);
        if (table_remitosPendientes.getColumnModel().getColumnCount() > 0) {
            table_remitosPendientes.getColumnModel().getColumn(0).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_remitosPendientes.getColumnModel().getColumn(1).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(1).setPreferredWidth(50);
            table_remitosPendientes.getColumnModel().getColumn(2).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(2).setPreferredWidth(70);
            table_remitosPendientes.getColumnModel().getColumn(3).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(3).setPreferredWidth(150);
            table_remitosPendientes.getColumnModel().getColumn(4).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(4).setPreferredWidth(150);
            table_remitosPendientes.getColumnModel().getColumn(5).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(5).setPreferredWidth(50);
            table_remitosPendientes.getColumnModel().getColumn(6).setResizable(false);
            table_remitosPendientes.getColumnModel().getColumn(6).setPreferredWidth(70);
        }

        jLabel3.setText("Remitos pendientes de resolución:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscarCred, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_buscarCred, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(179, 179, 179))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        txt_Comentarios.setColumns(20);
        txt_Comentarios.setRows(5);
        jScrollPane2.setViewportView(txt_Comentarios);

        jLabel24.setText("Sucursal de caja");

        jLabel27.setText("Cliente:");

        txt_NroRemito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_NroRemitoKeyTyped(evt);
            }
        });

        jLabel35.setText("Remito N°");

        jLabel39.setText("Comentarios de la entrega:");

        cbox_entregado.setBackground(new java.awt.Color(255, 255, 255));
        cbox_entregado.setText("Entregado");

        jLabel40.setText("Fecha de Entrega:");

        cbox_anulacion.setBackground(new java.awt.Color(255, 255, 255));
        cbox_anulacion.setText("Anulación del crédito");

        cbox_sucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_guardarRemito.setText("Guardar");
        btn_guardarRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarRemitoActionPerformed(evt);
            }
        });

        btn_guardarRemito1.setText("Agregar datos articulos");
        btn_guardarRemito1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarRemito1ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("* Utilizar para cargar los números de serie o");

        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText(" en caso de no entregar el remito completo.");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(btn_guardarRemito, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(250, 250, 250)
                        .addComponent(btn_guardarRemito1))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_NroRemito, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbox_sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(date_fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(cbox_entregado)
                        .addGap(45, 45, 45)
                        .addComponent(cbox_anulacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(75, 75, 75))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_cancelar, btn_guardarRemito});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NroRemito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbox_sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbox_entregado)
                                .addComponent(cbox_anulacion))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(date_fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_guardarRemito, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btn_guardarRemito1))
                .addContainerGap())
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_cancelar, btn_guardarRemito, btn_guardarRemito1});

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        tabla_remitosResueltos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Remito N°", "Credito N°", "Fecha", "Cliente", "Domicilio", "Entregado", "Fecha Entrega"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_remitosResueltos.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(tabla_remitosResueltos);
        tabla_remitosResueltos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel28.setText("Remitos resueltos:");

        jLabel38.setText("Importe:");

        jLabel37.setText("Cantidad de remitos:");

        jLabel29.setText("Sr/es:");

        btn_imprimir.setText("Imprimir remitos resueltos");
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_NombreSr_es)
                        .addGap(238, 238, 238)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_CantRemitos, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Importe, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_imprimir)
                .addGap(467, 467, 467))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_CantRemitos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_NombreSr_es, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_NroRemitoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NroRemitoKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_NroRemitoKeyTyped

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        limpiarCampos();
        habilitarBotones(false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void table_remitosPendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_remitosPendientesMouseClicked
        int select = table_remitosPendientes.getSelectedRow();
        //select = table_remitosPendientes.convertRowIndexToModel(select);
        cargarDatos(select);
    }//GEN-LAST:event_table_remitosPendientesMouseClicked

    private void btn_guardarRemito1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarRemito1ActionPerformed
        ArrayList<renglonRemito> lista = remitoSelected.getRenglon();
        ActualizarRenglonRemito.setTitle("Actualizar renglon cantidad");
        ActualizarRenglonRemito.getContentPane().setBackground(Color.white);
        jPanel1.setBackground(Color.BLACK);
        GridLayout gl  = new  GridLayout(5, 1, 5, 5);
        jPanel1.setLayout(gl);
        for(int i = 0; i < lista.size(); i++){
        // tamaño de la componente [529, 141]
        renglonRemitoComponent rrc = new renglonRemitoComponent(lista.get(i).getRc().getP().getNombre(),lista.get(i).getId(), lista.get(i).getRc().getCantidad(), lista.get(i).getRc().getNroSerie());
        listPanelCargarNroSerie.add(rrc);
        jPanel1.add(rrc);
    }
        ActualizarRenglonRemito.setVisible(true);
        ActualizarRenglonRemito.setLocationRelativeTo(null);
    }//GEN-LAST:event_btn_guardarRemito1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        for (int i = 0; i < listPanelCargarNroSerie.size(); i++) {
            remitoSelected.updateRenglonById(listPanelCargarNroSerie.get(i).getCantidad(),listPanelCargarNroSerie.get(i).getNroSerie(),listPanelCargarNroSerie.get(i).getRenglonId());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_guardarRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarRemitoActionPerformed
        remitoSelected.setEntregado(cbox_entregado.isSelected());
        remitoSelected.setFecha_entrega(new Timestamp(date_fechaEntrega.getDate().getTime()));
        remitoSelected.setAnulado(cbox_anulacion.isSelected());
        remitoSelected.setComentarios(txt_Comentarios.getText());
        remitoSelected.setSucursal(String.valueOf(cbox_sucursal.getSelectedItem()));
        if(remitoDAO.updateRemito(remitoSelected) > 0){
            principal.lbl_estado.setText("El remito se actualizo con exito.");
            principal.lbl_estado.setForeground(Color.GREEN);
            cargarRemitos();
        }else{
            principal.lbl_estado.setText("Ocurrio un error al actualizar el remito");
            principal.lbl_estado.setForeground(Color.RED);
        }
        limpiarCampos();
    }//GEN-LAST:event_btn_guardarRemitoActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        int[] rows = tabla_remitosResueltos.getSelectedRows();
        if(rows == null || rows.length == 0){
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un articulo para añadir al remito.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    return;
        }
        actualizarFletero(rows);
    }//GEN-LAST:event_btn_imprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ActualizarRenglonRemito;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_guardarRemito;
    private javax.swing.JButton btn_guardarRemito1;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JCheckBox cbox_anulacion;
    private javax.swing.JCheckBox cbox_entregado;
    private javax.swing.JComboBox<String> cbox_sucursal;
    private com.toedter.calendar.JDateChooser date_fechaEntrega;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabla_remitosResueltos;
    private javax.swing.JTable table_remitosPendientes;
    private javax.swing.JTextField txt_CantRemitos;
    private javax.swing.JTextArea txt_Comentarios;
    private javax.swing.JTextField txt_Importe;
    private javax.swing.JTextField txt_NombreCliente;
    private javax.swing.JTextField txt_NombreSr_es;
    private javax.swing.JTextField txt_NroRemito;
    private javax.swing.JTextField txt_buscarCred;
    // End of variables declaration//GEN-END:variables

    private void limpiarCampos() {
        txt_NombreCliente.setText("");
        txt_Comentarios.setText("");
        txt_NroRemito.setText("");
        date_fechaEntrega.setDate(new Date());
        cbox_anulacion.setSelected(false);
        cbox_entregado.setSelected(true);
    }

    private void habilitarBotones(boolean b) {
        btn_cancelar.setEnabled(b);
        btn_guardarRemito.setEnabled(b);
    }

    private void cargarRemitos() {
        ArrayList<Remito> listAll = remitoDAO.getRemitos();
        DefaultTableModel mdResuel = (DefaultTableModel)tabla_remitosResueltos.getModel();
        DefaultTableModel mdPend = (DefaultTableModel)table_remitosPendientes.getModel();
        for(int i = 0 ; i < listAll.size();i++){
            Object[] o = new Object[7];
            o[0] = listAll.get(i).getId();
            o[1] = listAll.get(i).getCredito_id();
            o[2] = listAll.get(i).getC().getFecha_aprobacion();
            o[3] = listAll.get(i).getC().getCliente().getNombre();
            Object[] dir = direccionesDAO.getDireccionCompleta(listAll.get(i).getC().getDireccion_id());
            o[4] = dir[4] +""+ dir[1]; 
            o[6] = listAll.get(i).getFecha_entrega();
            if(listAll.get(i).isEntregado()){
                o[5] = "SI";
                listRemitoResult.add(listAll.get(i));
                mdResuel.addRow(o);
            }else{
                o[5] = "NO";
                listRemitoPend.add(listAll.get(i));
                mdPend.addRow(o);
            }
        }
    }

    private void cargarDatos(int select) {
        remitoSelected = listRemitoPend.get(select);
        txt_NroRemito.setText("");
        txt_NombreCliente.setText("");
        txt_Importe.setText("");
        txt_Comentarios.setText("");
    }

    private void actualizarFletero(int[] rows) {
        String ids = "(";
        for(int i = 0; i < rows.length; i++){
            remitoDAO.updateFletero(listRemitoResult.get(rows[i]));
            ids+=listRemitoResult.get(rows[i]).getId()+",";
        }
        ids.substring(0, rows.length-1);
        ids+=")";
        JasperViewer view =null;
        view = remitoDAO.imprimirEntregados(ids);
        if(view!= null){
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        }
        
    }
}
