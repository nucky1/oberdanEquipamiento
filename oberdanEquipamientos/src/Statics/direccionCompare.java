/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;

import Models.Direccion;
import java.util.Comparator;

/**
 *
 * @author demig
 */
public class direccionCompare implements Comparator<Direccion> { 
    public int compare(Direccion o1, Direccion o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
}
