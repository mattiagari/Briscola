/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverita;

import java.util.*;

/**
 *
 * @author Mattia Gariselli
 */
public class Giocatore {
    
    public String nome;
    int punteggio;
    ArrayList<Carta> punti;
    ArrayList<Carta> mano;
    
    public Giocatore() {
        this.mano = new ArrayList<Carta>();
        punti = new ArrayList<Carta>();
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }    

    public String cambioSeme(int seme) {
        
        if (seme == 1) {
            return "Denari";
        } else if (seme == 2) {
            return "Bastoni";
        } else if (seme == 3) {
            return "Coppe";
        } else {
            return "Spade";
        }
    }

    public String carteInMano() {
        String messaggio = "Le carte che hai in mano" + " " + getNome() + " " + "sono: ";
        for (int i = 0; i < mano.size(); i++) {
            messaggio += (mano.get(i).stampaCarta() + "  ");            
        }        
        return messaggio;
    }

    public void pesco(Carta c) {
        mano.add(c);
    }
    //public void aggiungiPunteggio(Carta c){
    //    punti.add(c);
    //}
    
    public void aggiungiPunteggio(int punti) {        
        int x;
        x = this.getPunteggio();
        x += punti;
        this.setPunteggio(x);
    }

    public void selezioneCarta(Carta c) {
        mano.remove(c);
    }
}
