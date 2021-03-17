/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author demig
 */
public class RendererCuotasCliente extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus, 
                                                   int row, 
                                                   int column) {
        String estado = (String) table.getValueAt(row, 3);
        switch(estado){
            case "CUOTA NO PAGADA":{
                setBackground(Color.GRAY);
                setForeground(Color.BLACK);
                break;
            }
            case "AL DIA":{
                setBackground(Color.GREEN);
                setForeground(Color.BLACK);
                break;
            }
            case "PAGO PARCIAL":{
                setBackground(Color.YELLOW);
                setForeground(Color.BLACK);
                break;
            }
            case "PAGO VENCIDA":{
                setBackground(Color.RED);
                setForeground(Color.BLACK);
                break;
            }
            case "CUOTA ADELANTADA":{
                setBackground(Color.ORANGE);
                setForeground(Color.BLACK);
                break;
            }
            default:{
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
}
