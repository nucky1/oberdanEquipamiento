/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;

import Models.Provincia;
import java.util.Comparator;

/**
 *
 * @author demig
 */
public class provinciaCompare implements Comparator<Provincia> { 
    public int compare(Provincia o1, Provincia o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
} 
