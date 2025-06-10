package aplicaciones.censo;

import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPIOrdenada;

/**
 * ListaElectores: representa una lista de habitantes, 
 *                 registrados en el censo, y por ello, electores
 * 
 * @author  Profesores EDA 
 * @version Septiembre 2023
 */

public class ListaElectores {
   
    private ListaConPI<Habitante> censo;
    private int talla;
    
    /**
     * Métodos consultores de atributos
     */
    public ListaConPI<Habitante> getCenso() { return censo; }
    public int getTalla() { return talla; }
    
    /**
     * Devuelve el String que representa una ListaElectores 
     * 
     * @return el String con la ListaElectores en el formato texto dado. 
     */
    public String toString() {
        String res = "";
        if (talla == 0) return res;
        censo.inicio();
        for (int pos = 0; pos <= censo.talla() - 2; pos++) {
            res += censo.recuperar() + ", \n";
            censo.siguiente();
        }
        res += censo.recuperar();
        return res;
    }
   
    /**
     * Crea una ListaElectores...
     * 
     * @param orden Un boolean que indica si el censo,  
     *              debe estar ordenada ascendentemente (true) o no (false). 
     *              
     * @param n     Un int que indica la talla, número de elementos, de la lista              
     */
    public ListaElectores(boolean orden, int n) {
        talla = n;
        // COMPLETAR
        if(orden) {
            censo = new LEGListaConPIOrdenada<>();
        } else {
            censo = new LEGListaConPI<>();
        }
        
        while(censo.talla() < n) {
            Habitante h = new Habitante();
            if(indice(h) == -1) {
                censo.insertar(h);
            }
        }
    }
    
    /**
     * Devuelve el índice o posicion del Habitante h en una ListaElectores, 
     * o -1 si h no forma parte de la lista. 
     * 
     * @param h un Habitante
     * @return  el índice de h en un censo, un valor int
     *          0 o positivo si h esta en el censo      
     *          o -1 en caso contrario
     */
    protected int indice(Habitante h) {
        // COMPLETAR
        censo.inicio();
        int indice = 0;
        while(!censo.esFin()) {
            if(censo.recuperar().equals(h)) return indice;
            censo.siguiente();
            indice++;
        }
        return -1;
    }
    
    public int borrarCensoCP (int cp){
        censo.inicio();
        int eliminats = 0;
        while (!censo.esFin()) {
            Habitante h = censo.recuperar();
            if (h.getCp() == cp) {
                censo.eliminar();
                eliminats++;
            } else {
                censo.siguiente();
            }
    
        }
        return eliminats;
    }
    public static void testBorrarCensoCP() { 
        int cp, borrados;
        Habitante primerHab;
        ListaElectores hab;
        ListaElectores le = new ListaElectores(false, 2000); 
        le.censo.inicio();
        primerHab = le.censo.recuperar();
        cp = primerHab.getCp();
        //hab = le.getCensoCP(cp, cp);
        //System.out.println("\nEn el censo hay "+hab.getTalla()+" habitantes con el CP "+ cp); 
        System.out.println("\nBorramos los habitantes del CP..."); 
        borrados = le.borrarCensoCP(cp);
        le.censo.inicio();
       // if (primerHab.equals(le.censo.recuperar()) || borrados != hab.getTalla()) {
          //System.out.println("\n*** Hay errores en borrarCensoCP ***");   
        //}
        //else {
          //System.out.println("\nborrarCensoCP correcto");   
        }
    } 

