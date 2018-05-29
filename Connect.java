/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author mdsma
 */
public class Connect extends Thread {

    private Socket client = null;
    BufferedReader in = null;
    PrintStream print = null;

    public Connect() {
    }

    public Connect(Socket clientSocket) {
        client = clientSocket;
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            print = new PrintStream(client.getOutputStream(), true);
        } catch (Exception e1) {
            try {
                client.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        this.start();
    }
//funzione per comunicare col client

    public void run() {
        try {
            print.println("Connessione stabilita");
            print.flush();

            while (true) {
                String s = in.readLine();
                System.out.println(s);
                print.println(s);

            }
// chiude gli stream e le connessioni
            //print.close();
            //in.close();
            //client.close();
        } catch (Exception e) {
        }
    }

    public void chiudiConnessione() throws IOException {
        System.out.println("serverita.Client.chiudiConnessione()");
        print.close();
        in.close();
    }

    public void attendi() throws IOException {
        String s = in.readLine();
        System.out.println(s);
    }

    public void inviaMessaggio(String text) {
        print.println(text);
        System.out.println("Hai inviato " + text);
    }
}
