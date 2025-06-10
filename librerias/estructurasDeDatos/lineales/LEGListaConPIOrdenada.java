package librerias.estructurasDeDatos.lineales;

public class LEGListaConPIOrdenada<E extends Comparable<E>> extends LEGListaConPI<E> {
    
    /** Inserta el elemento e en la lista de forma ordenada*/
    public void insertar(E e){
        if(!esVacia()) {
            inicio();
            while(!esFin() && e.compareTo(recuperar()) > 0) {
                siguiente();
            }
        }
        super.insertar(e);
    }
}