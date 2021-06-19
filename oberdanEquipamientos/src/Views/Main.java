/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.Empleado;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author demig
 */
public class Main {
    public static char nivel;
    public static Empleado logueado;
    public static boolean isProduccion = false;
    private static JFrame fr;
    //public static boolean isProduccion = true;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Login l = new Login();
        l.setVisible(true);
        fr = new JFrame();
        fr.add(l);
        fr.setSize(300, 360);
        fr.dispose();
        fr.setUndecorated(true);
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
    }

    static void iniciar() {
        fr.dispose();
        principal vista1 = new principal();
        vista1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista1.setVisible(true);
}
}