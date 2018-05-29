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
public class Mazzo {
    static Stack<Carta>  mazzo = new Stack<Carta>();    
    public static int numeroCarte = 40;
    
    public static Stack<Carta> getMazzo() {
        return mazzo;
    }
    
    public static int getNumeroCarte() {
        return numeroCarte;
    }
    
    public static void setNumeroCarte(int numeroCarte) {
        numeroCarte = numeroCarte;
    }
    
    public Mazzo() {
        this.numeroCarte = getNumeroCarte();
        
        genera();
        
    }
    
    public void genera() {
        for (int i = 1; i <= 10; i++) {
            int punti, valore;
            if(i==1){
                punti=11;
            }
            else if(i==3){
                punti = 10;
            }
            else if(i==10){
                punti = 4;
            }
            else if(i==9){
                punti = 3;
            }
            else if(i==8){
                punti = 2;
            }
            else{
                punti = 0;
            }
            if(i==1){
                valore=11;
            }
            else if(i==3){
                valore = 10;
            }
            else {
                valore = i;
            }
            Carta tmp = new Carta(i, 1, valore, punti); //denari
            this.mazzo.push(tmp);
            
        }
        
        for (int i = 1; i <= 10; i++) {
            int punti, valore;
            if(i==1){
                punti=11;
            }
            else if(i==3){
                punti = 10;
            }
            else if(i==10){
                punti = 4;
            }
            else if(i==9){
                punti = 3;
            }
            else if(i==8){
                punti = 2;
            }
            else{
                punti = 0;
            }
            if(i==1){
                valore=11;
            }
            else if(i==3){
                valore = 10;
            }
            else {
                valore = i;
            }
            Carta tmp = new Carta(i, 2, valore,punti); //bastoni
            this.mazzo.push(tmp);
        }
        
        for (int i = 1; i <= 10; i++) {
            int punti, valore;
            if(i==1){
                punti=11;
            }
            else if(i==3){
                punti = 10;
            }
            else if(i==10){
                punti = 4;
            }
            else if(i==9){
                punti = 3;
            }
            else if(i==8){
                punti = 2;
            }
            else{
                punti = 0;
            }
            if(i==1){
                valore=11;
            }
            else if(i==3){
                valore = 10;
            }
            else {
                valore = i;
            }
            Carta tmp = new Carta(i, 3, valore,punti); //coppe
            this.mazzo.push(tmp);
        }
        
        for (int i = 1; i <= 10; i++) {
            int punti, valore;
            if(i==1){
                punti=11;
            }
            else if(i==3){
                punti = 10;
            }
            else if(i==10){
                punti = 4;
            }
            else if(i==9){
                punti = 3;
            }
            else if(i==8){
                punti = 2;
            }
            else{
                punti = 0;
            }
            if(i==1){
                valore=11;
            }
            else if(i==3){
                valore = 10;
            }
            else {
                valore = i;
            }
            Carta tmp = new Carta(i, 4, valore, punti); //spade
            this.mazzo.push(tmp);
        }
        
        Collections.shuffle(mazzo);
    
    }   
}
