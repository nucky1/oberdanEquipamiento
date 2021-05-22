/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;

import Models.Localidad;
import java.util.Comparator;

/**
 *
 * @author demig
 */
public class localidadCompare implements Comparator<Localidad> { 
    public int compare(Localidad o1, Localidad o2) {
        String first_Str = o1.getNombre(); 
        String second_Str = o2.getNombre();
        return second_Str.compareTo(first_Str); 
    }
}
