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
import Views.principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import java.math.BigInteger; 
import java.util.Comparator;
import java.sql.Timestamp; 
import java.util.Calendar;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 *
 * @author Note250
 */
public class Funciones {
    private static Pattern pattern;
    public static void limpiarlbl_estado(){
        //limpiar lbl_estado
            long l = 3000;
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            Runnable task1 = () -> principal.lbl_estado.setText("");
            service.scheduleAtFixedRate(task1, 1, 7, TimeUnit.SECONDS);
    }
    public static boolean onlyNumbers(String string, boolean allowEmpty) 
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[\\d]*");
        else
            pattern = Pattern.compile("[\\d]+");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }
    /**
     * 
     * @param fecha
     * @param dias
     * @return a new date adding or deleting the quantity on dias (if dias is negative)
     */
    public static Date sumarRestarDiasFecha(Date fecha, int dias, int mes, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        calendar.add(Calendar.MONTH, mes);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }
    
    public static boolean onlyNumbers(String string, int digitLimit, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[\\d]{0,"+digitLimit+"}");
        else
            pattern = Pattern.compile("[\\d]{1,"+digitLimit+"}");

        if (pattern.matcher(string).matches())
        {
            System.out.println("true");
            ret = true;
        }
        else
        {
            System.out.println("false");
            ret = false;
        }

        return ret;
    }
    public static boolean onlyChars(String string, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[a-zA-Z]*");
        else
            pattern = Pattern.compile("[a-zA-Z]+");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public static boolean onlyCharsWithWhiteSpace(String string, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[a-zA-Z]*[\\s[a-zA-Z]]*");
        else
            pattern = Pattern.compile("[a-zA-Z]+[\\s[a-zA-Z]]*");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public static boolean isEmail(String string, boolean allowEmpty) 
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$|^$)?");
        else
            pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$|^$");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }
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
    static SimpleDateFormat plantilla2 = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat plantilla = new SimpleDateFormat("yyyy-MM-dd");
    public static String dateFormat(Date date){
        Locale locale = new Locale("us", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        return plantilla.format(date);
    }
    
    public static String dateParse(Date date){
         Timestamp ts=new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        return formatter.format(ts);
    }
    public static String dateFormat2(Date date){
         Locale locale = new Locale("us", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        return plantilla2.format(date);
    }
    /**
     * 
     * @param f
     * @return 
     */
    public static Float redondeo2Deci(Float f){
        return  Float.parseFloat(String.format(Locale.US,"%.2f",f));
    }
    public static Float redondeoSinDeci(Float f){
        return  Float.parseFloat(String.format(Locale.US,"%.0f",f));
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
    public static boolean isFloat(String cadena){
        try {
                System.out.println("cadena es "+cadena);
		Float.parseFloat(cadena);
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
 * @param group
 * @return  JRadioButton jrb=getSelection(btgrp1);
 * devuelve el boton seleccionado
 */
 public static JRadioButton getSelection(ButtonGroup group)
{
        for (Enumeration e=group.getElements(); e.hasMoreElements(); )
        {
            JRadioButton b = (JRadioButton)e.nextElement();
            if (b.getModel() == group.getSelection())
            {
                return b;
            }
        }

        return null;
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
