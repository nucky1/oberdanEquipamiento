/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Cobranzas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hernan
 */
public class ColorFilasCobranza extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // if funcion(algo){
        //this.setBackGround(Color.red);
        //this.setForeGround(Color.white);}
        //
        //this.setBackground(Color.red);
        //this.setForeground(Color.black);
        /**
        switch (){
            case "green": {
                //cuota al dia
                this.setBackGround(Color.green);
            }
            case "grey" :{
                //cuota no pagada
                //darkGray
            }
            case "yellow":{
                //pago parcial
            }
            case "orange":{
                //cuota adelantadad
            }
            case "magenta":{
                
            }
            
        }
        * * **/
        return this;
        
    }
}
