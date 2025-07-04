package aplicaciones.editor;

import librerias.estructurasDeDatos.jerarquicos.ABB;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

/** Clase EditorPredictivo: 
 *  es un Arbol Binario de Busqueda de elementos de tipo String,   
 *  que representan las palabras de un idioma. 
 *  Esta clase proporciona los metodos necesarios 
 *  para la gestion de un editor predictivo. 
 *  
 *  @author  Profesores EDA
 *  @version Septiembre 2023
 */

public class EditorPredictivo extends ABB<String> {

    /** Constructor de un EditorPredictivo vacio
     */
    public EditorPredictivo() { super(); }
    
    /** Constructor de un Editor Predictivo a partir de las palabras 
     *  que, en orden alfabetico, contiene un fichero de texto a 
     *  partir de su segunda linea, pues en su primera linea aparece 
     *  el numero de palabras que almacena.
     *  @param nombreFichero   Nombre del fichero de texto que contiene 
     *                         las palabras a partir de las que se creara 
     *                         el editor ordenadas alfabeticamente,  
     *                         junto con su numero.
     */
    public EditorPredictivo(String nombreFichero) {   
        super();
        try {         
            Scanner fPalabras = new Scanner(new File(nombreFichero), "UTF-8"); 
            int talla = fPalabras.nextInt(); fPalabras.nextLine();
            String[] palabras = new String[talla];
            int nLinea = 0;
            while (fPalabras.hasNext() && nLinea < talla) {
                palabras[nLinea] = fPalabras.nextLine().toLowerCase().trim(); 
                nLinea++;
            }
            fPalabras.close();
            this.raiz = construirEquilibrado(palabras, 0, talla - 1);            
        } catch (FileNotFoundException eChecked) {
            System.out.println("El fichero " + nombreFichero 
                + " no es accesible para lectura, comprueba "
                + "su correcta ubicación");
        }
    }
        
    /** Anyade la palabra nueva a un Editor Predictivo.
     *  @param  nueva  Palabra a anyadir a las que ya tiene un Editor Predictivo.
     */
    public void incluir(String nueva) { 
        this.insertar(nueva.toLowerCase().trim()); 
    }

    /** Guarda en orden alfabetico las palabras de un Editor Predictivo 
     *  a partir de la segunda linea de un fichero de texto, escribiendo 
     *  su numero en la primera linea.
     *  @param nombreFichero   Nombre del fichero de texto donde se guardaran 
     *                         la talla y palabras en orden alfabetico de un 
     *                         Editor Predictivo.
     */
    public void guardar(String nombreFichero) {
        try { 
            PrintWriter fPalabras = new PrintWriter(nombreFichero, "UTF-8");
            Comparable[] palabras = this.toArrayInOrden();
            fPalabras.println(palabras.length);
            for (int i = 0; i < palabras.length; i++) {
                fPalabras.println((String) palabras[i]);
            }
            fPalabras.close();
        } catch (IOException eChecked) {
            System.out.println("Error guardando el fichero " + nombreFichero 
                + ": " + eChecked);
        }
    }
    
    /** Devuelve una ListaConPI con, como maximo, los n siguientes 
     *  sucesores de prefijo; en el primer lugar de esta lista figurara 
     *  el propio prefijo siempre y cuando sea ya una palabra del editor.
     *  @param  prefijo    Prefijo a partir del que se buscan los "n" 
     *                     siguientes sucesores
     *  @param  n          Numero maximo de sucesores a recuperar
     *  @return            ListaConPI<String>   
     *                     ListaConPI con los sucesores de prefijo obtenidos; 
     *                     su primer elemento es el propio prefijo 
     *                     siempre y cuando sea una palabra del editor.
     */
    public ListaConPI<String> recuperarSucesores(String prefijo, int n) {       
        //COMPLETAR
        ListaConPI<String> sucesores = new LEGListaConPI<String>();
        if(prefijo == null) return sucesores;
        
        String aux = sucesor(prefijo);
        while(n > 0 && aux != null && aux.startsWith(prefijo)) {
            sucesores.insertar(aux);
            aux = sucesor(aux);
            n--;
        }
        
        return sucesores;  
    }
}
