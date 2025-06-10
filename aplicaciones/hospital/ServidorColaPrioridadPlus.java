package aplicaciones.hospital;

import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.MonticuloBinario;

/**
 * Write a description of class ServidorColaPrioridadPlus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ServidorColaPrioridadPlus extends ServidorColaPrioridad implements ServidorQuirofanoPlus{
 
    protected ColaPrioridad<Paciente> cP;
    private int talla;

    public ServidorColaPrioridadPlus() {
        cP = new MonticuloBinario<Paciente>();
        talla = 0;
    }
    
    public void insertarEnEspera(Paciente p) { 
        talla++;
        super.insertarEnEspera(p);
    }
    
    public Paciente operarPaciente(int h) {
        talla--;
        return super.operarPaciente(h);
    }
    
    public int numPacientes() {
        return talla;
    }
    
    public Paciente transferirPaciente() {
        talla--;
        return cP.eliminarMin();
    }
    
    public void distribuirPacientes(ServidorQuirofanoPlus s) {
        Paciente[] pacientes = new Paciente[talla];
        int i = 0;
        while(!cP.esVacia()) {
            pacientes[i] = this.transferirPaciente();
            i++;
        }
        i = 0;
        while(i < pacientes.length) {
            if(i % 2 == 0) this.insertarEnEspera(pacientes[i]);
            else s.insertarEnEspera(pacientes[i]);
            i++;
        }
    }
}
