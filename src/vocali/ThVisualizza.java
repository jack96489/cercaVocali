/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocali;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaco
 */
public class ThVisualizza extends Thread {

    private final Dati ptrDati;

    public ThVisualizza(Dati ptrDati) {
        this.ptrDati = ptrDati;
    }

    @Override
    public void run() {
        final Schermo schermo = ptrDati.getSchermo();
        System.out.println("Inizio la stampa...");
        while (!ptrDati.sonoFinitiTutti()) {
                schermo.getSemaforo().Wait();
                String s = schermo.getBuffer().poll();
                if (s != null) {
                    System.out.println(s);
                }
                schermo.getSemaforo().Signal();
                yield();        
                if(isInterrupted())
                    return;
        }
        System.out.println("fine della stampa");
    }

}
