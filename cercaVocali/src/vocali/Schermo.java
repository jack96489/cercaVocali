/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocali;

/**
 *
 * @author giaco
 */
public class Schermo {

    /**
     * coda di stringhe usata dai thread per scrivere su schermo
     *
     * @author Giacomo Orsenigo
     */
    private String buffer;

    private final Semaforo semLetto, semScritto;

    public Schermo() {
        this.buffer = new String();
        this.semLetto = new Semaforo(1);
        semScritto= new Semaforo(0);
    }

    public String getBuffer() {
        return buffer;
    }

    public Semaforo getSemLetto() {
        return semLetto;
    }

    public Semaforo getSemScritto() {
        return semScritto;
    }

    public synchronized void setBuffer(String buffer) {
        this.buffer = buffer;
    }    
}
