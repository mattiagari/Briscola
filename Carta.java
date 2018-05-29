/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverita;

/**
 *
 * @author Mattia Gariselli
 */
public class Carta {

    int numero;
    int seme;
    int valore;
    int punti;

    public Carta() {
        this.numero = 0;
        this.seme = 0;
        this.valore = 0;
        this.punti = 0;
    }

    public Carta(int numero, int seme, int valore, int punti) {
        this.numero = numero;
        this.seme = seme;
        this.valore = valore;
        this.punti = punti;
    }

    public Carta(Carta c) {
        this.numero = c.getNumero();
        this.seme = c.getSeme();
        this.valore = c.getValore();
        this.punti = c.getPunti();
    }

    public int getNumero() {
        return numero;
    }

    public int getSeme() {
        return seme;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSeme(int seme) {
        this.seme = seme;
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public String stampaSeme() {
        if (seme == 1) {
            return "Denari";
        } else if (seme == 2) {
            return "Coppe";
        } else if (seme == 3) {
            return "Spade";
        } else {
            return "Bastoni";
        }
    }

    public String stampaCarta() {
        return numero + " di " + this.stampaSeme();
    }

    public String stampaValoriCarta(){
        return numero + "/" + seme + "/" + valore + "/" + punti;
    }
}
