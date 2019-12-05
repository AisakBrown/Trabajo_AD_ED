/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author isaac
 */
public class Filtros {
    
    /**
     * Metodo que nos crea una lista con las imagenes dentro del archivo que se le pasa por parametro
     * @param fichero
     * @return lista de imágenes
     */
    public static ArrayList<File> copiarFicherosImagenes(File fichero) {
        ArrayList<File> listaArray = new ArrayList<>();
        File[] lista = fichero.listFiles();
        for (File file : lista) {
            if (file.isDirectory()) {
                for (File imagenesRecursivas : copiarFicherosImagenes(file)) {
                    listaArray.add(imagenesRecursivas);
                }
            } else {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) {
                    listaArray.add(file);
                }
            }
        }
        return listaArray;
    }
    
    
    /**
     * Metodo que nos crea una lista con los documentos dentro del archivo que se le pasa por parametro
     * @param fichero
     * @return lista con los documentos
     */
    public static ArrayList<File> copiarFicherosDocumentos(File fichero) {
        ArrayList<File> listaArray = new ArrayList<>();
        File[] lista = fichero.listFiles();
        for (File file : lista) {
            if (file.isDirectory()) {
                for (File imagenesRecursivas : copiarFicherosImagenes(file)) {
                    listaArray.add(imagenesRecursivas);
                }
            } else {
                if (file.getName().endsWith(".doc") || file.getName().endsWith(".txt") || file.getName().endsWith(".docx")) {
                    listaArray.add(file);
                }
            }
        }
        return listaArray;
    }
    
    
    /**
     * Metodo que nos crea una lista con los videos dentro del archivo que se le pasa por parametro
     * @param fichero
     * @return lista con los videos a copiar
     */
    public static ArrayList<File> copiarFicherosVideos(File fichero) {
        ArrayList<File> listaArray = new ArrayList<>();
        File[] lista = fichero.listFiles();
        for (File file : lista) {
            if (file.isDirectory()) {
                for (File imagenesRecursivas : copiarFicherosImagenes(file)) {
                    listaArray.add(imagenesRecursivas);
                }
            } else {
                if (file.getName().endsWith(".avi") || file.getName().endsWith(".mp4") || file.getName().endsWith(".mkv")) {
                    listaArray.add(file);
                }
            }
        }
        return listaArray;
    }
}
