/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author demig
 */
public class Main {
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        principal vista1 = new principal();
        vista1.setVisible(true);
        vista1.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
