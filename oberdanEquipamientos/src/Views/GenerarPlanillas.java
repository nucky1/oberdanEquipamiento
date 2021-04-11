/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Views;
import Models.Empleado;
import DAO.EmpleadosDAO;
import Models.Planilla;
import DAO.IngresoCobranzaDAO;
import Statics.Funciones;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Hernan
 */
public class GenerarPlanillas extends javax.swing.JPanel {
    private List<Empleado> listaEmpleados;
    private EmpleadosDAO empleadoDao;
    private Planilla planilla;
    private IngresoCobranzaDAO ingresoCobranzaDao;
    
    /** Creates new form GenerarPlanillas */
    public GenerarPlanillas() {
        initComponents();
        Date fecha = new Date();
        ingresoCobranzaDao= IngresoCobranzaDAO.getInstance();
        empleadoDao = EmpleadosDAO.getInstance();
        jDateChooser.setDate(fecha);
        jDateChooser.setEnabled(false);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelNumeroPLanilla = new javax.swing.JLabel();
        jComboBoxCobradores = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonGenerarPlanilla = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelInfo = new javax.swing.JLabel();

        jDateChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooserMouseClicked(evt);
            }
        });

        jLabel1.setText("Fecha emision planilla:");

        jLabel2.setText("Planilla de cobranza:");

        jLabelNumeroPLanilla.setText("xxx");

        jComboBoxCobradores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jComboBoxCobradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCobradoresActionPerformed(evt);
            }
        });

        jLabel4.setText("Cobrador:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Emision de planillas");

        jButtonGenerarPlanilla.setText("Generar planilla");
        jButtonGenerarPlanilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerarPlanillaActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });

        jLabelInfo.setText("Info");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(78, 78, 78)
                                .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88)
                                .addComponent(jLabel2)
                                .addGap(32, 32, 32)
                                .addComponent(jLabelNumeroPLanilla))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxCobradores, 0, 288, Short.MAX_VALUE))))
                        .addContainerGap(78, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jButtonGenerarPlanilla)
                        .addGap(52, 52, 52)
                        .addComponent(jButtonSalir)
                        .addGap(19, 19, 19))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonCancelar, jButtonGenerarPlanilla, jButtonSalir});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabelNumeroPLanilla)))
                    .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxCobradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGenerarPlanilla)
                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSalir))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonCancelar, jButtonGenerarPlanilla, jButtonSalir});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxCobradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCobradoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCobradoresActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jDateChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooserMouseClicked
        cargarCobradores();
    }//GEN-LAST:event_jDateChooserMouseClicked

    private void jButtonGenerarPlanillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarPlanillaActionPerformed
       int indice= jComboBoxCobradores.getSelectedIndex();
       
        if(indice != -1){
            Empleado empleado = listaEmpleados.get(indice);
            Date fecha = jDateChooser.getDate();
            planilla=ingresoCobranzaDao.generarPlanilla(Statics.Funciones.dateParse(fecha), String.valueOf(empleado.getId()));
            if(planilla != null){
                jLabelNumeroPLanilla.setText(""+planilla.getId());
                jLabelInfo.setText("La planilla ha sido generada con exito");
                //aca deberias imprimir el reporte
            }
            else{
                jLabelInfo.setText("No se pudo generar la planilla solicitada");
            }
        }
        else {
            jLabelInfo.setText("Debe seleccionar un cobrador");
        }
        
    }//GEN-LAST:event_jButtonGenerarPlanillaActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        jComboBoxCobradores.setSelectedIndex(0);
        jLabelNumeroPLanilla.setText("xxx");
    }//GEN-LAST:event_jButtonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonGenerarPlanilla;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<String> jComboBoxCobradores;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelNumeroPLanilla;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    private void cargarCobradores() {
       listaEmpleados=empleadoDao.getCobradores();
       if(listaEmpleados.isEmpty()){
           jLabelInfo.setText("Error, no se pudieron cargar los cobradores");
       }
       else{
           jLabelInfo.setText("Seleccione un cobrador");
           jComboBoxCobradores.removeAllItems();
           for(int i =0; i<listaEmpleados.size(); i++){
               jComboBoxCobradores.addItem(listaEmpleados.get(i).getNombre());
           }
       }
    }

}
