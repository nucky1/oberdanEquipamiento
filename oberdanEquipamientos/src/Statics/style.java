/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author demig
 * 
        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher encaja = patron.matcher(fieldNombre.getText());
 */
public class style {
    public static Color colorDeApp = new Color(231, 60, 0); //Naranja
    public static Border getBordePanel(String title){
        return javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(colorDeApp), 
                title, 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new java.awt.Font("Tahoma", 0, 14),colorDeApp);
    }
    
    public static void setPlaceHolder(JTextField field,String texto){
        TextPrompt placeholder = new TextPrompt(texto, field);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
    }
    
    public static void setPlaceHolderArea(JTextArea field,String texto){
        TextPrompt placeholder = new TextPrompt(texto, field);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
    }
}
