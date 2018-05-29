package serverita;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static javax.management.remote.JMXConnectorFactory.connect;
import static jdk.nashorn.internal.objects.Global.print;

public class Server extends Thread {

    private ServerSocket Server;

    private Socket client = null;
    private BufferedReader in = null;
    private PrintStream print = null;

    static Gioco gioco;

    public Server() throws Exception {
        Server = new ServerSocket(5555);
        System.out.println("Il Server è in attesa sulla porta 5555.");
        this.attesaConnessione();
        gioco = new Gioco();
    }

    public static Gioco getGioco() {
        return gioco;
    }

    public void impostaNomeServer(String nome) {
        gioco.getGiocatore1().setNome(nome);
        System.out.println("Nome server " + nome);
    }

    public void impostaNomeClient() throws IOException {
        String s = in.readLine();
        gioco.getGiocatore2().setNome(s);
        System.out.println("Nome client " + s);
    }

    public void attesaConnessione() {
        try {
            System.out.println("In attesa di Connessione.");
            Socket client = Server.accept();
            System.out.println("Connessione accettata da: "
                    + client.getInetAddress());
            this.setConnessione(client);
            print.println("Connessione stabilita");
        } catch (Exception e) {
        }

    }

    public void setConnessione(Socket clientSocket) throws IOException {
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
        }
    }

    public void chiudiConnessione() throws IOException {
        print.close();
        in.close();
        client.close();
    }

    public void inviaMessaggio(String text) {
        print.println(text);
        System.out.println("I:" + text);
    }

    public void attendi() throws IOException {
        System.out.println("Aspetto un messaggio");
        String s = in.readLine();
        System.out.println("R:" + s);
    }

    public void stampaNomi() {
        System.out.println("Nome server : " + gioco.getGiocatore1().getNome() + "   Nome client : " + gioco.getGiocatore2().getNome());
    }
    
    public void inviaBriscola(){
        String briscola = gioco.getBriscola().stampaValoriCarta();
        inviaMessaggio(briscola);
    }
}
