package Views.Articulos;

import DAO.ProductoDAO;
import Models.Producto;
import Models.Stock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTextField;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public abstract class JD_Producto_editarStock extends javax.swing.JDialog  {
    private Producto prod;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private ProductoDAO productoDAO;
    private int idStock;
    private List<Stock> listStock;
    private Map<Integer, Float> cantidades = new HashMap<Integer, Float>();
    private Float stockExistente; 
    
    public Float getStockAgregado(){
        return stockExistente;
    }
    public JTextField getNuevo_stock() {
        return nuevo_stock;
    }
    public HashMap<Integer,Float> getCantidades(){
        return (HashMap<Integer, Float>) cantidades;
    }
    
    public JD_Producto_editarStock(java.awt.Frame parent, boolean modal,Producto p) {
        super(parent, modal);
        initComponents();
        nuevo_stock.requestFocus();
        jLabelUnidades.setVisible(false);
        prod = p;
        if(prod == null)
            dispose();
        productoDAO = ProductoDAO.getInstance();
        cargarDatos();
    }
    
    

        public void cargarTablaProd(List<Stock> list) {
            DefaultTableModel tabla = (DefaultTableModel) tabla_stock.getModel();
            Object[] obj = new Object[4];
            tabla.setNumRows(0);
            for (int i = 0; i < list.size(); i++) {
                Stock s = list.get(i);
                obj[0] = s.getId();
                obj[1] = s.getStock_actual();
                obj[2] = s.getPrecio_compra();
                obj[3] = s.getFechaCompra();
                tabla.addRow(obj);
            }
        }
        
    private void cargarDatos(){     
        lbl_nombre_producto.setText(prod.getNombre());
        stockTotal.setText(""+prod.getStock());
        stockExistente = (float) prod.getStock();
        codigo.setText("# "+prod.getId());
        listStock = productoDAO.getStockProducto(prod.getId());
        cargarTablaProd(listStock);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_stock = new javax.swing.JTable();
        codigo = new javax.swing.JTextField();
        lbl_editarStock = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nuevo_stock = new javax.swing.JTextField();
        btn_guardar_lote = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        stock_actual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btn_regresar = new javax.swing.JButton();
        jLabelStockTotal = new javax.swing.JLabel();
        stockTotal = new javax.swing.JTextField();
        jLabelUnidades = new javax.swing.JLabel();
        lbl_nombre_producto = new javax.swing.JLabel();
        aplicar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Stock");
        setBackground(new java.awt.Color(255, 255, 255));
        setModal(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabla_stock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "stock", "Precio de compra", "Fecha de compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_stockMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_stock);

        codigo.setEditable(false);
        codigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setText("# 1315");

        lbl_editarStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_editarStock.setText("Editar Stock");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_editarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_editarStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nuevo Stock                    ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 160, 20));

        nuevo_stock.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nuevo_stock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nuevo_stock.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nuevo_stockFocusGained(evt);
            }
        });
        nuevo_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nuevo_stockKeyTyped(evt);
            }
        });
        jPanel2.add(nuevo_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 108, 35));

        btn_guardar_lote.setBackground(new java.awt.Color(255, 255, 255));
        btn_guardar_lote.setText("Crear nuevo stock");
        btn_guardar_lote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_loteActionPerformed(evt);
            }
        });
        btn_guardar_lote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_guardar_loteKeyPressed(evt);
            }
        });
        jPanel2.add(btn_guardar_lote, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 140, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Stock Actual");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 77, -1));

        stock_actual.setEditable(false);
        stock_actual.setBackground(new java.awt.Color(255, 255, 255));
        stock_actual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stock_actual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stock_actual.setText("0");
        stock_actual.setBorder(null);
        stock_actual.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        stock_actual.setEnabled(false);
        jPanel2.add(stock_actual, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 67, 22));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("unidades");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 180, -1));

        btn_regresar.setBackground(new java.awt.Color(255, 255, 255));
        btn_regresar.setText("Regresar");
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, 39));

        jLabelStockTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelStockTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelStockTotal.setText("Stock Total:");
        jPanel2.add(jLabelStockTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 77, -1));

        stockTotal.setEditable(false);
        stockTotal.setBackground(new java.awt.Color(255, 255, 255));
        stockTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stockTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stockTotal.setText("0");
        stockTotal.setBorder(null);
        stockTotal.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        stockTotal.setEnabled(false);
        stockTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockTotalActionPerformed(evt);
            }
        });
        jPanel2.add(stockTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 67, 22));

        jLabelUnidades.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelUnidades.setText("unidades");
        jPanel2.add(jLabelUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, -1, -1));

        lbl_nombre_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_nombre_producto.setText("Prod Select");
        jPanel2.add(lbl_nombre_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 15, 136, -1));

        aplicar.setBackground(new java.awt.Color(255, 255, 255));
        aplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Statics/nuevo_producto.png"))); // NOI18N
        aplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarActionPerformed(evt);
            }
        });
        aplicar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                aplicarKeyPressed(evt);
            }
        });
        jPanel2.add(aplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void tabla_stockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_stockMouseClicked
            lbl_nombre_producto.setVisible(true);
            aplicar.setEnabled(true);
            nuevo_stock.setEnabled(true);
            String stock = String.valueOf(tabla_stock.getValueAt(tabla_stock.getSelectedRow(), 1));
            idStock = Integer.parseInt(String.valueOf(tabla_stock.getValueAt(tabla_stock.getSelectedRow(), 0)));
            stock_actual.setText(stock);
    }//GEN-LAST:event_tabla_stockMouseClicked

    private void aplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarActionPerformed
        float stock = 0f;
        try{
            stock = Float.parseFloat(nuevo_stock.getText());
            if (stock<0)
                stock = Float.parseFloat("error numero negativo");
            stockExistente+= stock;
            stock += Float.parseFloat(stock_actual.getText());
            productoDAO.setStockActual(idStock,stock);
            stock_actual.setText("0");
            nuevo_stock.setText("");
            stockTotal.setText((stockExistente+""));
            tabla_stock.setValueAt(stock,tabla_stock.getSelectedRow(), 1);
        }catch(Exception ex){
            nuevo_stock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
        }
    }//GEN-LAST:event_aplicarActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        float st = 0f;
        if(Statics.Funciones.isFloat(stockTotal.getText()))
            st = Float.parseFloat(stockTotal.getText());
        setStock(st);
        dispose();
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void btn_guardar_loteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_loteActionPerformed
        this.setModal(false);
        nuevoStock();
    }//GEN-LAST:event_btn_guardar_loteActionPerformed

    private void nuevo_stockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nuevo_stockKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_nuevo_stockKeyTyped

    private void nuevo_stockFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nuevo_stockFocusGained
        nuevo_stock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
    }//GEN-LAST:event_nuevo_stockFocusGained

    private void stockTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockTotalActionPerformed

    private void aplicarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aplicarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                aplicar.doClick();
                nuevo_stock.requestFocus();
            }
    }//GEN-LAST:event_aplicarKeyPressed

    private void btn_guardar_loteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_guardar_loteKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                btn_guardar_lote.doClick(); 
            }
    }//GEN-LAST:event_btn_guardar_loteKeyPressed
     public abstract void setStock(float st);
     public abstract void nuevoStock();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aplicar;
    private javax.swing.JButton btn_guardar_lote;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelStockTotal;
    private javax.swing.JLabel jLabelUnidades;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_editarStock;
    private javax.swing.JLabel lbl_nombre_producto;
    private javax.swing.JTextField nuevo_stock;
    private javax.swing.JTextField stockTotal;
    private javax.swing.JTextField stock_actual;
    private javax.swing.JTable tabla_stock;
    // End of variables declaration//GEN-END:variables

    void actualizarTotal(int sa) {
        stockTotal.setText(""+sa);
    }

    

}
