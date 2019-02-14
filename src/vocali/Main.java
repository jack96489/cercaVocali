/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocali;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe contiene l'entry point del programma
 *
 * @author Giacomo Orsenigo
 */
public class Main {

    public static final char[] VOCALI = {'a', 'e', 'i', 'o', 'u'};

    /**
     * Scanner per leggere da tastiera
     *
     * @author Giacomo Orsenigo
     */
    private static final Scanner scan = new Scanner(System.in);

    /**
     * BufferedReader per leggere una vocale in modo non bloccante
     *
     * @author Giacomo Orsenigo
     */
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @brief entry point
     *
     * Chiede all'utente una frase e la vocale più presente crea 5 tread di
     * classe {@link ThVocali} con le 5 vocali, li avvia e aspetta che abbiano
     * terminato l'esecuzione per vedere se l'utente ha vinto
     * @param args The command line arguments
     * @author Giacomo Orsenigo
     */
    public static void main(String[] args) {

        do {
            Schermo schermo = new Schermo();
            Dati dati = new Dati(schermo, VOCALI);
            String frase = null;
            char vocInserita = ' ';

            try {
                System.out.println("Vuoi utilizzare il delay? [S/N]");
                boolean delay = YesNoInput();
                System.out.println("Vuoi utilizzare lo yield? [S/N]");
                boolean yield = YesNoInput();

                System.out.println("Inserisci la frase");
                frase = scan.nextLine();

                System.out.println("Quale vocale compare più volte? (Hai 3 secondi per rispondere)");
                vocInserita = leggiVocale();

                //avvio il thread che stampa su console i risultati
                final ThVisualizza vis = new ThVisualizza(dati);
                vis.start();

                //creo e avvio i thread
                final ThVocali[] thVocali = new ThVocali[5];
                for (int i = 0; i < thVocali.length; i++) {
                    thVocali[i] = new ThVocali(VOCALI[i], delay, yield, frase, dati);
                    thVocali[i].start();
                }

                //aspetto che tutti abbiano finito
                for (ThVocali th : thVocali) {
                    th.join();
                }
                vis.join();

                //controllo se ha vinto
                char vocVincente = dati.getMax();
                if (vocInserita == vocVincente) {
                    System.out.println("Hai vinto!");
                } else {
                    System.out.println("Hai perso! La vocale giusta era " + vocVincente);
                }

            } catch (TimeoutException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Vuoi ricominciare? [S/N]");
        } while (YesNoInput());
        //dichiaro i 5 thread
        System.out.println("Ciao! Alla prossima.");
    }

    /**
     * @brief legge una vocale
     *
     * legge una vocale da {@link #reader} se viene inserita entro 3 secondi
     * @return vocale letta
     * @throws IllegalArgumentException se non viene inserita una vocale
     * @throws TimeoutException se non viene inserito niente entro 3 secondi
     * @author Giacomo Orsenigo
     */
    private static char leggiVocale() throws TimeoutException {
        char ris = ' ';
        try {
            int i = 0;
            while (reader.ready()) { //svuoto il buffer
                reader.read();
            }
            while (!reader.ready() && i < 3000) {
                Thread.sleep(1);
                i++;
            }
            if (reader.ready()) {
                ris = (char) reader.read();
            } else {
                throw new TimeoutException("Tempo scaduto!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (ris != 'a' && ris != 'e' && ris != 'i' && ris != 'o' && ris != 'u') {
            throw new IllegalArgumentException("Non hai inserito una vocale");
        }

        return ris;
    }

    private static boolean YesNoInput() {
        String s = scan.nextLine().toLowerCase();
        if (s.equals("s") || s.equals("si")) {
            return true;
        } else if (s.equals("n") || s.equals("no")) {
            return false;
        }
        throw new IllegalArgumentException("Input non corretto");
    }
}
