package serverita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import static javafx.application.Application.launch;

public class Client extends Thread {

    private BufferedReader in = null;
    private PrintStream print = null;
    private Socket socket = null;
    private String message;

    private String nomeServer;
    private String nomeClient;
    private static Carta briscola = null;

    public Client() {
        try {
            // open a socket connection
            socket = new Socket("localhost", 5555);
            // Apre i canali I/O
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            print = new PrintStream(socket.getOutputStream(), true);

            // Legge dal server
            message = in.readLine();
            System.out.print("Messaggio Ricevuto : " + message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Carta getBriscola() {
        return briscola;
    }

    public void setBriscola(Carta bri) {
        Client.briscola = bri;
    }

    public String getNomeClient() {
        return nomeClient;
    }

    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }

    public String getNomeServer() {
        return nomeServer;
    }

    public void setNomeServer(String nomeServer) {
        this.nomeServer = nomeServer;
    }

    public String attendi() throws IOException {
        System.out.println("aspetto un messaggio");
        String s = in.readLine();
        System.out.println("R:" + s);
        return s;
    }

    public void inviaMessaggio(String text) {
        print.println(text);
        System.out.println("I:" + text);
    }

    public void chiudiConnessione() throws IOException {
        System.out.println("Connessione terminata");
        print.close();
        in.close();
    }

    public Carta riceviCarta(String carta) {
        int numero, seme, valore, punti;
        String[] parts = carta.split("/");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        String part4 = parts[3];
        numero = Integer.parseInt(part1);
        seme = Integer.parseInt(part2);
        valore = Integer.parseInt(part3);
        punti = Integer.parseInt(part4);
        Carta x = new Carta(numero, seme, valore, punti);
        return x;
    }
}
