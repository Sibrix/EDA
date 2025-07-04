package librerias.estructurasDeDatos.jerarquicos;

import librerias.estructurasDeDatos.modelos.Cola;
import librerias.estructurasDeDatos.lineales.ArrayCola;
import librerias.util.Ordenacion;

/** Clase ABB<E> que representa un Arbol Binario de Busqueda
 *  mediante un enlace a su raiz. Sus caracteristicas son las siguientes: 
 *  1.- El tipo de sus elementos es E extends Comparable<E>
 *  2.- ATRIBUTOS (protected para la herencia): 
 *      TIENE UN NodoABB<E> raiz
 *
 *  @author  Profesores EDA
 *  @version Septiembre 2023
 **/
 
public class ABB<E extends Comparable<E>> {
    // Atributo de la clase ABB
    protected NodoABB<E> raiz;

    /** Constructor de un ABB vacio 
     */
    public ABB() { raiz = null; }
    
    /** Constructor de un ABB con los elementos del vector dado.
     *  El ABB resultante debe estar equilibrado.
     *  @param v    Array con los elementos a insertar en el ABB
     */
    public ABB(E[] v) {        
        // COMPLETAR   
        raiz = construirEquilibrado(v, 0, v.length - 1);
    }
    
    /** Construye un ABB equilibrado con los elementos del vector dado.
     *  @param v     Array con los elementos a insertar en el ABB
     *  @param ini   Inicio del intervalo a considerar en el vector
     *  @param fin   Fin del intervalo a considerar en el vector
     *  @return      Raiz del subárbol
     */
    protected NodoABB<E> construirEquilibrado(E[] v, int ini, int fin) {
        // COMPLETAR
        int m = (ini + fin) / 2;
        NodoABB<E> raiz = new NodoABB<E>(v[m]);
        if(ini < fin) {
            raiz.izq = construirEquilibrado(v, ini, m- 1);
            raiz.der = construirEquilibrado(v, m + 1, fin);
        }
        return raiz;
    }
    
    /** Reconstruye el ABB, con los mismos datos, de forma que quede equilibrado        
     */
    public void reconstruirEquilibrado() {        
        // COMPLETAR
        E[] v = toArrayInOrden();
        raiz = construirEquilibrado(v, 1, v.length - 1);
    }
    
    /** Devuelve el sucesor de un elemento en el ABB
     *  @param e   Elemento cuyo sucesor se va a buscar
     *  @return    Sucesor de "e", o null si no hay sucesor
     */
    public E sucesor(E e) {
        NodoABB<E> res = sucesor(e, raiz);
        if (res == null) return null;  
        return res.dato; 
    }
    
    /** SII actual != null: devuelve el nodo de actual que contiene 
     *  al sucesor de "e", o null si no existe
     *  @param e         Elemento cuyo sucesor se va a buscar
     *  @param actual    Nodo actual en la busqueda
     *  @return          Sucesor de "e" en el nodo actual, o null si no existe
     */
    protected NodoABB<E> sucesor(E e, NodoABB<E> actual) {
        NodoABB<E> res = null;
        if (actual != null) {
            int resC = actual.dato.compareTo(e);
            if (resC > 0) {
                res = sucesor(e, actual.izq);
                if (res == null) res = actual;
            } else {
                res = sucesor(e, actual.der);
            }
        }
        return res;
    }
    
    /** Busca el valor dado en el ABB
     *  @param e   Elemento a buscar
     *  @return    Dato en el ABB que coincide con e, null si no hay          
     */
    public E recuperar(E e) {
        NodoABB<E> res = recuperar(e, raiz);
        if (res == null) return null;
        return res.dato;
    }
    
    /** Busca el valor dado a partir del nodo actual
     *  @param e        Elemento a buscar
     *  @param actual   Nodo actual en la busqueda
     *  @return         Nodo en el ABB que contiene e, null si no hay              
     */
    protected NodoABB<E> recuperar(E e, NodoABB<E> actual) {
        if (actual == null) return null;
        int cmp = e.compareTo(actual.dato);
        if (cmp < 0) return recuperar(e, actual.izq); 
        if (cmp > 0) return recuperar(e, actual.der);
        return actual;
    }
    
    /** Actualiza el dato e en el ABB, si no esta lo inserta 
     *  @param e   Elemento a insertar/actualizar
     */
    public void insertar(E e) { raiz = insertar(e, raiz); }
    
    /** Actualiza el dato e a partir del nodo actual. Si no esta lo inserta 
     *  @param e        Elemento a insertar/actualizar
     *  @param actual   Nodo actual en la busqueda
     *  @return         Nodo raiz del subarbol cuya raiz actual es el nodo actual 
     */
    protected NodoABB<E> insertar(E e, NodoABB<E> actual) {
        if (actual == null) return new NodoABB<E>(e); 
        int cmp = e.compareTo(actual.dato);
        if (cmp < 0)      actual.izq = insertar(e, actual.izq); 
        else if (cmp > 0) actual.der = insertar(e, actual.der); 
        else              actual.dato = e; 
        actual.talla = 1 + talla(actual.izq) + talla(actual.der);
        return actual;
    }
    
    /** Devuelve el numero de elementos del ABB
     *  @return   Talla del ABB
     */
    public int talla() { return talla(raiz); }

    /** Devuelve el tamanyo del nodo actual
     *  @param actual   Nodo actual
     *  @return         Tamanyo del nodo actual
     */
    protected int talla(NodoABB<E> actual) {
        if (actual == null) return 0; 
        return actual.talla;
    }
  
    /** SII !esVacio(): devuelve el elemento minimo del ABB
      * @return   Elemento minimo    
      */
    public E recuperarMin() { return recuperarMin(raiz).dato; }
    
    /** Devuelve el elemento minimo a partir del nodo actual 
     *  @param actual   Nodo actual en la busqueda
     *  @return         Nodo que contiene el elemento mínimo 
     */
    protected NodoABB<E> recuperarMin(NodoABB<E> actual) {
        if (actual.izq == null) return actual; 
        return recuperarMin(actual.izq); 
    }

    /** SII !esVacio(): elimina el minimo del ABB
     *  @return   Elemento minimo del ABB (null si esta vacio)
     */
    public E eliminarMin() {
        E min = recuperarMin();
        raiz = eliminarMin(raiz);
        return min;
    }
 
    /** Elimina el minimo a partir del nodo actual
     *  @param actual   Nodo actual en la busqueda
     *  @return         Nodo raiz del subarbol cuya raiz actual es el nodo actual
     */
    protected NodoABB<E> eliminarMin(NodoABB<E> actual) {
        if (actual.izq == null) return actual.der;
        actual.izq = eliminarMin(actual.izq);
        actual.talla--;
        return actual;
    }
  
    /** Elimina el nodo que contiene el dato e 
     *  @param e   Dato a eliminar
     */
    public void eliminar(E e) { raiz = eliminar(e, raiz); }
    
    /** Elimina el nodo que contiene el dato e a partir del nodo actual 
     *  @param e         Dato a eliminar
     *  @param actual    Nodo actual en la busqueda
     *  @return          Nodo raiz del subarbol cuya raiz actual es el nodo actual
     */
    protected NodoABB<E> eliminar(E e, NodoABB<E> actual) {
        if (actual == null) return actual; 
        int cmp = e.compareTo(actual.dato);
        if (cmp < 0)      actual.izq = eliminar(e, actual.izq); 
        else if (cmp > 0) actual.der = eliminar(e, actual.der); 
        else {
            if (actual.der == null)  return actual.izq; 
            if (actual.izq  == null) return actual.der; 
            actual.dato = recuperarMin(actual.der).dato;
            actual.der = eliminarMin(actual.der);
        }
        actual.talla = 1 + talla(actual.izq) + talla(actual.der);
        return actual;
    }
  
    /** Devuelve true si el ABB esta vacio
     *  @return   true si esta vacio, false en caso contrario
     */
    public boolean esVacio() { return raiz == null; }
  
    /** Recorrido inOrden del ABB
     *  @return   String con los elementos segun el recorrido inOrden
     */
    public String toStringInOrden() {
        StringBuilder sb = new StringBuilder().append("[");
        if (raiz != null) toStringInOrden(sb, raiz);
        return sb.append("]").toString();
    }
    
    /** Recorrido inOrden a partir del nodo actual
     *  @param sb       StringBuilder para ir construyendo la cadena de texto 
     *  @param actual   Nodo actual en la busqueda
     */
    protected void toStringInOrden(StringBuilder sb, NodoABB<E> actual) {
        if (actual.izq != null) {
            toStringInOrden(sb, actual.izq);
            sb.append(",");
        }
        sb.append(actual.dato.toString());
        if (actual.der != null) { 
            sb.append(",");
            toStringInOrden(sb, actual.der); 
        }
    }

    /** Recorrido en preOrden del ABB
     *  @return   String con los elementos segun el recorrido preOrden
     */
    public String toStringPreOrden() {
        StringBuilder sb = new StringBuilder().append("[");
        if (raiz != null) toStringPreOrden(sb, raiz);
        return sb.append("]").toString();
    }
    
    /** Recorrido preOrden a partir del nodo actual
     *  @param sb       StringBuilder para ir construyendo la cadena de texto 
     *  @param actual   Nodo actual en la busqueda
     */
    protected void toStringPreOrden(StringBuilder sb, NodoABB<E> actual) {
        sb.append(actual.dato.toString());
        if (actual.izq != null) {
            sb.append(",");
            toStringPreOrden(sb, actual.izq);
        }
        if (actual.der != null) {
            sb.append(",");
            toStringPreOrden(sb, actual.der);
        }
    }

    /** Recorrido en postOrden del ABB
     *  @return   String con los elementos segun el recorrido postOrden
     */
    public String toStringPostOrden() {
        StringBuilder sb = new StringBuilder().append("[");
        if (raiz != null) toStringPostOrden(sb, raiz);
        return sb.append("]").toString();
    }
    
    /** Recorrido postOrden a partir del nodo actual
     *  @param sb       StringBuilder para ir construyendo la cadena de texto 
     *  @param actual   Nodo actual en la busqueda
     */
    protected void toStringPostOrden(StringBuilder sb, NodoABB<E> actual) {
        if (actual.izq != null) {
            toStringPostOrden(sb, actual.izq);
            sb.append(",");
        }
        if (actual.der != null) {
            toStringPostOrden(sb, actual.der);
            sb.append(",");
        }
        sb.append(actual.dato.toString());
    }
    
    /** Recorrido por niveles del ABB
     *  @return   String con los elementos segun el recorrido por niveles
     */
    public String toStringPorNiveles() {
        if (raiz == null) return "[]";
        StringBuilder res = new StringBuilder().append("[");
        Cola<NodoABB<E>> q = new ArrayCola<NodoABB<E>>();
        q.encolar(raiz);
        while (!q.esVacia()) {
            NodoABB<E> actual = q.desencolar();
            res.append(actual.dato.toString());
            res.append(", ");
            if (actual.izq != null) q.encolar(actual.izq);
            if (actual.der != null) q.encolar(actual.der); 
        }
        // Por eficiencia, para borrar la ultima ", "
        // de res se resta 2 a su longitud actual
        res.setLength(res.length() - 2);
        return res.append("]").toString();
    }
    
    /** Construye un array ordenado de forma creciente con todos los
     *  elementos del ABB, resultado del recorrido en InOrden del mismo
     *  @return   Array con los valores del ABB segun el recorrido en InOrden
     */
    @SuppressWarnings("unchecked")
    public E[] toArrayInOrden() {
        E[] v = (E[]) new Comparable[talla()];
        toArrayInOrden(v, raiz, 0);
        return v;
    }
    
    /** Construye un array ordenado de forma creciente con todos los
     *  elementos a partir del nodo actual, siguiendo el recorrido en InOrden
     *  @param v        Array con los elementos segun el recorrido en InOrden
     *  @param actual   Nodo actual en el recorrido
     *  @param pos      Posición en el array v
     */ 
    protected void toArrayInOrden(E[] v, NodoABB<E> actual, int pos) {
        if (actual != null) {
            toArrayInOrden(v, actual.izq, pos);
            int auxPos = pos + talla(actual.izq);
            v[auxPos++] = actual.dato;
            toArrayInOrden(v, actual.der, auxPos);    
        }
    }
}
