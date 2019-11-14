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
public class MetodosManejoUSB {
    
    /**
     * Método que borra los ficheros que se le pasan por una lista y devuelve el total de los que se borran
     * @param lista
     * @return total de ficheros borrados
     */
    public static int borrarFicheros(ArrayList<File> lista) {
        int totalFicherosBorrados = 0;
        for (java.util.Iterator<java.io.File> iterator = lista.iterator(); iterator.hasNext();) {
            File file = iterator.next();
            if (file.delete()) {
                totalFicherosBorrados++;
            }
        }
        return totalFicherosBorrados;
    }

    
    /**
     * Metodo que calcula el espacio disponible de un archivo que se le pase por parámetro
     * @param file
     * @return total de espacio disponible
     */
    public static long espacioTotalDispositivo(File file) {
        long espacioDisponible = file.getFreeSpace();
        return espacioDisponible / 1048576;
    }

    
    /**
     * Método que busca dentro de un directorio si existen archivos duplicados
     * @param directorioEnElQueBuscar
     * @return Lista con los ficheros duplicados
     */
    public static ArrayList<File> buscarFicherosDuplicados(File directorioEnElQueBuscar) {
        ArrayList<File> archivosDuplicados = new ArrayList<>();
        File[] contenidoDirectorioBuscar = directorioEnElQueBuscar.listFiles();

        for (File file : contenidoDirectorioBuscar) {
            if (compararArchivoConDirectorios(file, directorioEnElQueBuscar)) {
                archivosDuplicados.add(file);
            } else if (file.isDirectory()) {
                ArrayList<File> listaRecursiva = buscarFicherosDuplicados(file);
                if (listaRecursiva != null) {
                    for (File file1 : listaRecursiva) {
                        archivosDuplicados.add(file1);
                    }
                }

            }
        }
        return archivosDuplicados;
    }

    
    
    /**
     * Método que comprueba si un archivo en un directorio tiene algún duplicado
     * @param archivoComparar
     * @param directorioBuscar
     * @return devuelve true si el archivo tiene un duplicado
     */
    public static boolean compararArchivoConDirectorios(File archivoComparar, File directorioBuscar) {
        File[] archivosDirectorio = directorioBuscar.listFiles();
        boolean duplicado = false;

        if (directorioBuscar.isDirectory()) {
            for (File file : archivosDirectorio) {
                if (duplicado == true) {
                } else {
                    if (file.getName().equalsIgnoreCase(archivoComparar.getName()) && file.getTotalSpace() == archivoComparar.getTotalSpace() && !file.getAbsolutePath().equalsIgnoreCase(archivoComparar.getAbsolutePath())) {
                        duplicado = true;
                    } else {
                        duplicado = compararArchivoConDirectorios(archivoComparar, file);
                    }
                }
            }
        } else {
            if (directorioBuscar.getName().equalsIgnoreCase(archivoComparar.getName()) && directorioBuscar.getTotalSpace() == archivoComparar.getTotalSpace() && !directorioBuscar.getAbsolutePath().equalsIgnoreCase(archivoComparar.getAbsolutePath())) {
                duplicado = true;
            }
        }
        return duplicado;
    }
}
