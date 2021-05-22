/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;

import Models.Producto;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
public class MiRenderer extends DefaultTableCellRenderer {
    private List<Producto> listProdNota;
    public MiRenderer(List<Producto> listProdNota) {
        super();
        this.listProdNota = listProdNota;
    }
 
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus, 
                                                   int row, 
                                                   int column) {
 
        int stock = (Integer) table.getValueAt(row, 2);
        int stockMin = listProdNota.get(row).getStockMin();
        if(stockMin >= stock){
            setBackground(Color.RED);
            setForeground(Color.BLACK);
        }else{
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
 
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
}