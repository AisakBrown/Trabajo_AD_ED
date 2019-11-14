/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.VentanaPrincipal;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author isaac
 */
public class BackUp {

    private File directorioBackUp;
    private File archivoACopiar;
    private VentanaPrincipal ventanaPrincipal;
    private File directorioBackUpArreglado;

    public File getArchivoACopiar() {
        return archivoACopiar;
    }

    
    /**
     * Constructor de la clase BackUp
     * @param rutaArchivoCopiar
     * @param rutaArchivoDestino
     * @param ventana
     * @throws IOException 
     */
    public BackUp(String rutaArchivoCopiar, String rutaArchivoDestino, VentanaPrincipal ventana) throws IOException {
        archivoACopiar = new File(rutaArchivoCopiar);
        directorioBackUp = new File(rutaArchivoDestino + "\\BackUp");
        if (!directorioBackUp.exists()) {
            Files.createDirectory(directorioBackUp.toPath());
        }
        directorioBackUpArreglado = new File(directorioBackUp.getAbsolutePath() + archivoACopiar.getAbsolutePath().substring(archivoACopiar.getAbsolutePath().lastIndexOf("\\")));
        ventanaPrincipal = ventana;
    }

    
    /**
     * Este metodo sirve para comprobar que tipo de archivos quiere el usuario que copiemos
     * @param fichero
     * @param seleccion
     * @return Lista con los archivos a copiar
     */
    public ArrayList<File> escogerTipoArchivo(File fichero, String seleccion) {
        ArrayList<File> listaArchivosCopiados = new ArrayList<>();
        if (seleccion.equalsIgnoreCase("Imágenes")) {
            listaArchivosCopiados = Filtros.copiarFicherosImagenes(fichero);
        } else if (seleccion.equalsIgnoreCase("Documentos")) {
            listaArchivosCopiados = Filtros.copiarFicherosDocumentos(fichero);
        } else if (seleccion.equalsIgnoreCase("Videos")) {
            listaArchivosCopiados = Filtros.copiarFicherosVideos(fichero);
        }
        return listaArchivosCopiados;
    }

    
    /**
     * Si hay que copiar todos los archivos este método nos mete en una lista todos los archivos de la ruta seleccionada
     * @param ruta
     * @param seleccion
     * @return lista con los archivos a copiar
     * @throws IOException 
     */
    public ArrayList<File> obtenerArchivosFormaRecursiva(String ruta, String seleccion) throws IOException {
        File directorio = new File(ruta);
        File[] lista = directorio.listFiles();
        ArrayList<File> listaArchivosCopiados = new ArrayList<>();
        if (seleccion.equalsIgnoreCase("Todos los archivos")) {
            if (!directorioBackUpArreglado.exists()) {
                Files.createDirectory(directorioBackUpArreglado.toPath());//Si no existe la carpeta backup la crea
            }

            for (File file : lista) {
                String rutaArreglada = arreglarRutaParaBackUp(file);
                File archivoEnBackUp = new File(rutaArreglada);

                if (!archivoEnBackUp.exists()) {//Si el archivo no existe en el backup nos lo crea y si es un directorio busca de forma recursiva
                    if (file.isDirectory()) {
                        listaArchivosCopiados.add(file);
                        ArrayList<File> ficherosRecursivos = obtenerArchivosFormaRecursiva(file.getAbsolutePath(), seleccion);
                        for (File fileRecursivo : ficherosRecursivos) {
                            listaArchivosCopiados.add(fileRecursivo);
                        }
                    } else {
                        listaArchivosCopiados.add(file);
                    }
                }
            }
        } else {
            listaArchivosCopiados = escogerTipoArchivo(directorio, seleccion);
        }
        return listaArchivosCopiados;
    }
    
    
    /**
     * Este metodo recoge la lista con los archivos a copiar y nos crea una carpeta dentro de backup para los videos
     * @param listaFicheros
     * @return total de videos copiados
     * @throws IOException 
     */
    public int copiarVideosDesdelista(ArrayList<File> listaFicheros) throws IOException {
        int totalArchivosCopiados = listaFicheros.size();
        int progreso = 1;
        File archivoBackUpImagenes = new File(directorioBackUp.getAbsolutePath() + "\\Videos");

        if (!archivoBackUpImagenes.exists()) {
            Files.createDirectory(archivoBackUpImagenes.toPath());
        }

        for (File file : listaFicheros) {
            File rutaNuevaFichero = new File(archivoBackUpImagenes.getAbsolutePath() + "\\" + file.getName());

            Files.copy(file.toPath(), rutaNuevaFichero.toPath(), StandardCopyOption.REPLACE_EXISTING);

            updateProgress(progreso, file.getAbsolutePath());
            progreso++;
        }
        return totalArchivosCopiados;
    }
    
    /**
     * Este metodo recoge la lista con los archivos a copiar y nos crea una carpeta dentro de backup para los documentos
     * @param listaFicheros
     * @return total de documentos copiados
     * @throws IOException 
     */
    public int copiarDocumentosDesdelista(ArrayList<File> listaFicheros) throws IOException {
        int totalArchivosCopiados = listaFicheros.size();
        int progreso = 1;
        File archivoBackUpImagenes = new File(directorioBackUp.getAbsolutePath() + "\\Documentos");

        if (!archivoBackUpImagenes.exists()) {
            Files.createDirectory(archivoBackUpImagenes.toPath());
        }

        for (File file : listaFicheros) {
            File rutaNuevaFichero = new File(archivoBackUpImagenes.getAbsolutePath() + "\\" + file.getName());

            Files.copy(file.toPath(), rutaNuevaFichero.toPath(), StandardCopyOption.REPLACE_EXISTING);

            updateProgress(progreso, file.getAbsolutePath());
            progreso++;
        }
        return totalArchivosCopiados;
    }

    
    /**
     * Este metodo recoge la lista con los archivos a copiar y nos crea una carpeta dentro de backup para las imagenes
     * @param listaFicheros
     * @return total de imagenes copiadas
     * @throws IOException 
     */
    public int copiarImagenesDesdelista(ArrayList<File> listaFicheros) throws IOException {
        int totalArchivosCopiados = listaFicheros.size();
        int progreso = 1;
        File archivoBackUpImagenes = new File(directorioBackUp.getAbsolutePath() + "\\Imágenes");

        if (!archivoBackUpImagenes.exists()) {
            Files.createDirectory(archivoBackUpImagenes.toPath());
        }

        for (File file : listaFicheros) {
            File rutaNuevaFichero = new File(archivoBackUpImagenes.getAbsolutePath() + "\\" + file.getName());

            Files.copy(file.toPath(), rutaNuevaFichero.toPath(), StandardCopyOption.REPLACE_EXISTING);

            updateProgress(progreso, file.getAbsolutePath());
            progreso++;
        }
        return totalArchivosCopiados;
    }
    
    /**
     * Este metodo copia todos los archivos de la lista y los copia en su orden original
     * @param listaFicheros
     * @return el total de archivos copiados
     * @throws IOException 
     */
    public int copiarArchivosDesdeLista(ArrayList<File> listaFicheros) throws IOException {
        int totalArchivosCopiados = listaFicheros.size();
        int progreso = 1;

        for (File file : listaFicheros) {
            File archivoEnBackUp = new File(arreglarRutaParaBackUp(file));//Aqui arreglamos la ruta y le ponemos la nueva dentro de la carpeta backup
            if (file.isDirectory()) {
                Files.createDirectory(archivoEnBackUp.toPath());
            } else {
                Files.copy(file.toPath(), archivoEnBackUp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            updateProgress(progreso, file.getAbsolutePath());
            progreso++;
        }

        return totalArchivosCopiados;
    }

    
    /**
     * Este metodo recoge la ruta anterior del archivo y crea una para meterlo dentro del backup
     * @param finalRuta
     * @return la nueva ruta
     */
    public String arreglarRutaParaBackUp(File finalRuta) {
        String rutaArreglada = directorioBackUpArreglado.getAbsolutePath() + finalRuta.getAbsolutePath().substring(archivoACopiar.getAbsolutePath().indexOf(archivoACopiar.getName()) + archivoACopiar.getName().length());
        return rutaArreglada;
    }

    
    /**
     * Creamos el hilo para que nos actualice la pantalla mientras se copia
     * @param valorBarraProgreso
     * @param fichero 
     */
    private void updateProgress(int valorBarraProgreso, String fichero) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ventanaPrincipal.actualizarInterfazProgreso(fichero, valorBarraProgreso);
                ventanaPrincipal.repaint();
            }
        });
    }
}
