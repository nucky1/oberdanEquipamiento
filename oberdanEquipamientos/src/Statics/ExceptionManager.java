/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statics;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author demig
 */
public class ExceptionManager extends Thread{
    private Exception e = null;
    private String obs = "";
    public static String rutaPorDefecto = "ArchivosModificados/";
    public boolean crearDir (){
        File directorio = new File("ArchivosModificados/");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                return true;
            } else {
                return false;
            }
        }else{
            return true;
        }
    }
    @Override
    public void run() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("c:/prueba.txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < 10; i++)
                pw.println("Linea " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    /**
     * This method make a file on "Error" directory. 
     * The name of this file is AAAA-MM-DD-ClassNameException
     * This file have stacktrace of the exception and an observation
     * In case type = FALSE then just execute e.printstacktrace().
     * @param e exception thrown
     * @param obs An observation a possible fix, or class where is the Error
     * @param type true if its for produccion false for develop
     */
    public void saveDump(Exception e, String obs, boolean type){
        if(type){
            this.e = e;
            this.obs = obs;
            this.run();
        }else{
            e.printStackTrace();
        }
    }
}
