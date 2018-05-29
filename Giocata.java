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
public class Giocata {
    Carta giocata;
    int chiLaHaGiocata;

    public Carta getGiocata() {
        return giocata;
    }
    public void setGiocata(Carta giocata) {
        this.giocata = giocata;
    }
    public int getChiLaHaGiocata() {
        return chiLaHaGiocata;
    }
    public void setChiLaHaGiocata(int chiLaHaGiocata) {
        this.chiLaHaGiocata = chiLaHaGiocata;
    }   
    public Giocata(Carta giocata,int chiLaHaGiocata){
        this.setGiocata(giocata);
        this.setChiLaHaGiocata(chiLaHaGiocata);
    }
  
}
