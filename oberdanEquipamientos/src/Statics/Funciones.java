package Statics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Models.Barrio;
import Models.Direccion;
import Models.Localidad;
import Models.Provincia;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import java.math.BigInteger; 
import java.util.Comparator;

/**
 *
 * @author Note250
 */
public class Funciones {
    
    static SimpleDateFormat plantilla = new SimpleDateFormat("yyyy-MM-dd");
    public static boolean compareStrings(String s1, String s2){
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        return s1.contains(s2);
        
    }
    /**
     * 
     * @param text
     * @return true if length(text)>0 && !text.equals(' ')
     */
    public static boolean controlText(String text){
        if(text.length() == 0 || text.equals(" ")){
            return false;
        }else{
            return true;
        }
    }
    public static String dateFormat(Date date){
        Locale locale = new Locale("us", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        return plantilla.format(date);
    }
    /**
     * 
     * @param f
     * @return 
     */
    public static Float redondeo2Deci(Float f){
        return  Float.parseFloat(String.format(Locale.US,"%.2f",f));
    }
    /**
     * 
     * @param f
     * @return a string from a float with 2 digits after the point
     */
    public static String redondeo2String(Float f){
        return  String.format(Locale.US,"%.2f",f);
    }
    /**
     * 
     * @param cadena
     * @return true if cadena only has numbers
     */
    public static boolean isNumeric(String cadena){
        try {
		Integer.parseInt(cadena);
               
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    /**
     * 
     * @param cadena
     * @return true if there's no number on cadena
     */
    public static boolean isOnlyChar(String cadena){
        
       if (Pattern.matches("[a-zA-Z]+", cadena) == false && cadena.length() > 1){
             return false;
        }
        else return true;
    }
    /**
     * 
     * @param cadena
     * @return a string with cuit/cuil generated from cadena
     */
    /*
    public String generadorCuit_cuil(String cadena, String sexo){
        String resultado="-";
        int cantDigi;
        int multiplicador=5432765432; 
        
        cantDigi=cadena.length();
        if(Funciones.isNumeric(cadena)){
            int number= Integer.parseInt(cadena);
            switch(sexo){
                case "H": {
                     int aux = 10*cantDigi;
                     aux+=number;
                     
                }
                case "M":{
                    
                }
            }
        }
       
    
        return resultado;
    }
 */    
}
