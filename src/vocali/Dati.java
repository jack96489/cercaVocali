/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocali;

/**
 * Classe che memorizza il numero di volta che viene ripetuta una vocale
 *
 * @author orsenigo_giacomo
 */
public class Dati {

    private final char[] VOCALI;
    /**
     * vettore che contiene il numero di volte che si ripete ogni vocale
     *
     * @author Giacomo Orsenigo
     */
    private int[] num = new int[5];

    /**
     * booleane che indicano se i thread sono in esecuzione o no
     *
     * @author Giacomo Orsenigo
     */
    private boolean[] esecuzione = new boolean[5];

    private final Schermo schermo;

    /**
     * @brief costruttore
     *
     * Inizializza il vettore {@link #num}
     * @author Giacomo Orsenigo
     */
    public Dati(Schermo schermo, char[] vocali) {
        for (int i = 0; i < num.length; i++) {
            num[i] = 0;
            esecuzione[i] = true;
        }
        this.schermo = schermo;
        VOCALI = vocali;
    }

    public Schermo getSchermo() {
        return schermo;
    }

    private int getIndex(char vocale) {
        for (int i = 0; i < VOCALI.length; i++) {
            if (vocale == VOCALI[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Vocale non trovata");
    }

    public int getNum(char Vocale) {
        return num[getIndex(Vocale)];
    }

    public void incNum(char Vocale) {
        num[getIndex(Vocale)]++;
    }

    /**
     * @brief calcola il massimo
     *
     * trova la vocale che si ripete più volte
     * @return vocale che si ripete più volte
     * @author Giacomo Orsenigo
     */
    public char getMax() {
        int max = -1;
        int indexMax = 0;
        for (int i = 0; i < num.length; i++) {
            if (num[i] > max) {
                indexMax = i;
                max = num[i];
            }
        }
        return VOCALI[indexMax];
    }
    
    /**
     * @brief controlla se i thread sono terminati
     *
     * @return true se tutti i thread sono terminati
     * @author Giacomo Orsenigo
     */
    public boolean sonoFinitiTutti() {
        boolean ris = true;
        for (int i = 0; i < 5; i++) {
            if (esecuzione[i]) {
                ris = false;
            }
        }
        return ris;
    }

    /**
     * @brief set terminato
     *
     * imposta come terminato il thread corrispondente alla vocale data
     * @param vocale vocale di cui impostare il thread come terminato
     * @author Giacomo Orsenigo
     */
    public void setFinito(char vocale) {
        esecuzione[getIndex(vocale)] = false;
    }

//    public void setIniziato(char vocale) {
//        System.out.println("vocale: " + vocale);
//        switch (vocale) {
//            case 'a':
//                esecuzione[0] = true;
//                break;
//            case 'e':
//                esecuzione[1] = true;
//                break;
//            case 'i':
//                esecuzione[2] = true;
//                break;
//            case 'o':
//                esecuzione[3] = true;
//                break;
//            case 'u':
//                esecuzione[4] = true;
//                break;
//        }
//    }
}
