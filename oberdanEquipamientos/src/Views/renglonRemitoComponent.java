/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javax.swing.JOptionPane;

/**
 *
 * @author Aguss2
 */
public class renglonRemitoComponent extends javax.swing.JPanel {
    private int idRengm;
    private float cantMax;
    /**
     * Creates new form renglonRemitoComponent
     */
    public renglonRemitoComponent(String nombreProd, int idRengR, float cantMax,String nroSerie) {
        initComponents();
        idRengm = idRengR;
        lbl_productoName.setText(nombreProd);
        txt_cantidad.setText(cantMax+"");
        this.cantMax = cantMax;
        txt_nroSerie.setText(nroSerie);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_productoName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_nroSerie = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(530, 170));
        setMinimumSize(new java.awt.Dimension(530, 170));
        setPreferredSize(new java.awt.Dimension(530, 170));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_productoName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_productoName.setText("Producto XX");
        add(lbl_productoName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 509, 22));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cantidad:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 44, -1, -1));
        add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 64, 80, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Numeros de serie:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 102, -1));
        add(txt_nroSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 509, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 255));
        jLabel4.setText(" ( Si es más de uno inserte separado por / ) ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 91, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbl_productoName;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_nroSerie;
    // End of variables declaration//GEN-END:variables

    public float getCantidad(){
        float f = -1;
        if(isFloat(txt_cantidad.getText())){
            f = Float.parseFloat(txt_cantidad.getText());
            if(f > cantMax){
                JOptionPane.showMessageDialog(null, "No puede ingresar una cantidad mayor a la pedida en el credito \n Cantidad del credito: "+cantMax,
                        "Error", JOptionPane.ERROR_MESSAGE);
                f = -1;
            }else{
                return f;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Solo se permiten numeros para la cantidad",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return f;
    }
    public String getNroSerie(){
        return txt_nroSerie.getText();
    }
    public int getRenglonId(){
        return idRengm;
    }
        public static boolean isFloat(String cadena){
        try {
		Float.parseFloat(cadena);
               
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
}