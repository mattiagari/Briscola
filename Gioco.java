package serverita;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Gioco {

    private static Giocatore giocatore1 = new Giocatore();
    private static Giocatore giocatore2 = new Giocatore();
    private static Mazzo mazzo = new Mazzo();
    private static Carta briscola = null;

    public Gioco() {
        impostaBriscola();
    }

    public Giocatore getGiocatore1() {
        return giocatore1;
    }

    public void setGiocatore1(Giocatore giocatore1) {
        Gioco.giocatore1 = giocatore1;
    }

    public Giocatore getGiocatore2() {
        return giocatore2;
    }

    public void setGiocatore2(Giocatore giocatore2) {
        Gioco.giocatore2 = giocatore2;
    }

    public Mazzo getMazzo() {
        return mazzo;
    }

    public void setMazzo(Mazzo mazzo) {
        Gioco.mazzo = mazzo;
    }

    public Carta getBriscola() {
        return briscola;
    }

    public void setBriscola(Carta briscola) {
        Gioco.briscola = briscola;
    }

    public void impostaBriscola() {
        Carta CartaPescata = mazzo.getMazzo().pop();
        setBriscola(CartaPescata);
    }

    public static void scegliNome(Giocatore giocatore) {

        System.out.println("Imposta il tuo nickname...");
        Scanner giocatore1 = new Scanner(System.in);
        String nome = giocatore1.nextLine();
        giocatore.setNome(nome);
    }

    public static void pescataTurno(Giocatore server, Giocatore client, boolean ordine, Carta briscola, int numeroTurno) {
        if (ordine == true) {
            if (numeroTurno == 17) {
                Carta cartaPescata = Mazzo.getMazzo().pop();
                server.pesco(cartaPescata);
                cartaPescata = briscola;
                client.pesco(cartaPescata);
            } else {
                Carta cartaPescata = Mazzo.getMazzo().pop();
                server.pesco(cartaPescata);
                cartaPescata = Mazzo.getMazzo().pop();
                client.pesco(cartaPescata);
            }

        } else if (ordine == false) {
            if (numeroTurno == 17) {
                Carta cartaPescata = Mazzo.getMazzo().pop();
                client.pesco(cartaPescata);
                cartaPescata = briscola;
                server.pesco(cartaPescata);
            } else {
                Carta cartaPescata = Mazzo.getMazzo().pop();
                client.pesco(cartaPescata);
                cartaPescata = Mazzo.getMazzo().pop();
                server.pesco(cartaPescata);
            }

        }
    }

    public static Carta turnoServer(Giocatore server, int numeroTurno, Carta briscola, boolean primo, PrintStream p, Socket ss, Scanner sc) {
        System.out.println("Turno numero " + numeroTurno);
        System.out.println(server.getNome() + " è il tuo turno");
        System.out.println(server.carteInMano());
        int x;
        do {
            Scanner a = new Scanner(System.in);
            System.out.println("Quale carta vuoi giocare? (1,2,3)");
            x = a.nextInt();
            if (x != 1 && x != 2 && x != 3) {
                System.out.println("ERRORE: Puoi inserire solo 1 o 2 o 3");
            }

        } while (x != 1 && x != 2 && x != 3);
        x--;
        System.out.println("hai giocato la carta " + server.mano.get(x).stampaCarta() + "\n");
        Carta temp = new Carta(server.mano.get(x));
        server.mano.remove(x);
        return temp;
    }

    public static Carta turnoClient(Giocatore client, int numeroTurno, Carta briscola, boolean primo, PrintStream p, Socket ss, Scanner sc) {
        String messaggio;
        int numeroCarta;
        p.println("2|inizio turno client");
        p.println("Turno numero " + numeroTurno);
        p.println(client.getNome() + " è il tuo turno");
        p.println(client.carteInMano());
        System.out.println("mano client " + client.carteInMano());
        messaggio = sc.nextLine();
        numeroCarta = Integer.parseInt(messaggio);
        Carta c = new Carta(client.mano.get(numeroCarta));
        p.println("hai giocato la carta " + client.mano.get(numeroCarta).stampaCarta());
        client.mano.remove(numeroCarta);
        return c;

    }

    public static Carta controlloCarte(Carta carta1, Carta carta2, int briscolaSeme) {

        if (carta1.getSeme() == briscolaSeme && carta2.getSeme() == briscolaSeme) {
            if (carta1.getSeme() == carta2.getSeme()) {
                if (carta1.getNumero() == 1) {
                    return carta1;
                } else if (carta1.getNumero() == 3 && carta2.getNumero() == 1) {
                    return carta2;
                } else if (carta1.getNumero() == 3 && carta2.getNumero() != 1) {
                    return carta1;
                } else if (carta2.getNumero() == 1 || carta2.getNumero() == 3) {
                    return carta2;
                } else if (carta1.getNumero() > carta2.getNumero()) {
                    return carta1;
                } else if (carta1.getNumero() < carta2.getNumero()) {
                    return carta2;
                }

            }

        } else if (carta1.getSeme() == briscolaSeme && carta2.getSeme() != briscolaSeme) {
            return carta1;
        } else if (carta2.getSeme() == briscolaSeme && carta1.getSeme() != briscolaSeme) {
            return carta2;

        } else if (carta1.getSeme() != carta2.getSeme()) {
            return carta1;
        } else if (carta1.getSeme() == carta2.getSeme()) {
            if (carta1.getNumero() == 1) {
                return carta1;
            } else if (carta1.getNumero() == 3 && carta2.getNumero() == 1) {
                return carta2;
            } else if (carta1.getNumero() == 3 && carta2.getNumero() != 1) {
                return carta1;
            } else if (carta2.getNumero() == 1 || carta2.getNumero() == 3) {
                return carta2;
            } else if (carta1.getNumero() > carta2.getNumero()) {
                return carta1;
            } else if (carta1.getNumero() < carta2.getNumero()) {
                return carta2;
            }

        }

        return null;
    }

    public static void dichiarazioneVincitore(boolean ordine, PrintStream p, Socket ss, Scanner sc) {
        if (ordine == true) {
            System.out.println("Hai vinto questo turno!");
            p.println("1|Hai perso questo turno");
        } else {
            System.out.println("Hai perso questo turno");
            p.println("1|Hai vinto questo turno!");
        }
    }

    public static void assegnaPunti(Giocatore giocatore, Carta carta1, Carta carta2) {
        giocatore.aggiungiPunteggio(carta1.getPunti());
        giocatore.aggiungiPunteggio(carta2.getPunti());
    }

    public static void stampaPunteggio(Giocatore server, Giocatore client, PrintStream p, Socket ss, Scanner sc) {
        System.out.println("La partita è giunta alla fine");
        p.println("1|La partita è giunta alla fine");

        if (server.getPunteggio() > client.getPunteggio()) {
            System.out.println("La partita è stata vinta da " + server.getNome() + "    PUNTEGGI:   " + server.getNome() + " -> " + server.getPunteggio() + "    " + client.getNome() + " -> " + client.getPunteggio());
            p.println("1|La partita è stata vinta da " + server.getNome() + "    PUNTEGGI:   " + server.getNome() + " -> " + server.getPunteggio() + "    " + client.getNome() + " -> " + client.getPunteggio());
        } else {
            System.out.println("La partita è stata vinta da " + client.getNome() + "    PUNTEGGI:   " + server.getNome() + " -> " + server.getPunteggio() + "    " + client.getNome() + " -> " + client.getPunteggio());
            p.println("1|La partita è stata vinta da " + client.getNome() + "    PUNTEGGI:   " + server.getNome() + " -> " + server.getPunteggio() + "    " + client.getNome() + " -> " + client.getPunteggio());
        }
    }

    public static boolean turno(boolean ordine, Giocatore server, Giocatore client, int numeroTurno, Carta briscola, PrintStream p, Socket ss, Scanner sc) {
        if (numeroTurno < 18) {
            Gioco.pescataTurno(server, client, ordine, briscola, numeroTurno);
        }
        Carta cartaServer, cartaClient, cartaVincente;

        if (ordine == true) {   //inizia il server
            cartaServer = new Carta(Gioco.turnoServer(server, numeroTurno, briscola, true, p, ss, sc));
            p.println("1| " + server.getNome() + " ha giocato il " + cartaServer.stampaCarta());
            cartaClient = new Carta(Gioco.turnoClient(client, numeroTurno, briscola, false, p, ss, sc));
            System.out.println(client.getNome() + " ha giocato il " + cartaClient.stampaCarta());

        } else {    //inizia il client
            cartaClient = new Carta(Gioco.turnoClient(client, numeroTurno, briscola, true, p, ss, sc));
            System.out.println(client.getNome() + " ha giocato il " + cartaClient.stampaCarta());
            cartaServer = new Carta(Gioco.turnoServer(server, numeroTurno, briscola, false, p, ss, sc));
            p.println("1| " + server.getNome() + " ha giocato il " + cartaServer.stampaCarta());
        }

        System.out.println(cartaClient.stampaCarta() + " " + cartaServer.stampaCarta());
        cartaVincente = new Carta(controlloCarte(cartaServer, cartaClient, briscola.getSeme()));

        if (cartaVincente.getNumero() == cartaServer.getNumero() && cartaVincente.getSeme() == cartaServer.getSeme()) {
            ordine = true; //VINCE IL SERVER
            Gioco.assegnaPunti(server, cartaServer, cartaClient);
        } else {
            ordine = false;// VINCE IL CLIENT
            Gioco.assegnaPunti(client, cartaServer, cartaClient);
        }
        Gioco.dichiarazioneVincitore(ordine, p, ss, sc);
        return ordine;
    }

    public static void pescataIniziale() {
        Carta cartaPescata = mazzo.getMazzo().pop();
        giocatore1.pesco(cartaPescata);
        cartaPescata = mazzo.getMazzo().pop();
        giocatore2.pesco(cartaPescata);
        cartaPescata = mazzo.getMazzo().pop();
        giocatore1.pesco(cartaPescata);
        cartaPescata = mazzo.getMazzo().pop();
        giocatore2.pesco(cartaPescata);
    }
    // public static void main(String[] args) throws IOException {
//        Giocatore player1 = new Giocatore();                    //CREAZIONE PLAYER1 (Server)
//        Giocatore player2 = new Giocatore();                    //CREAZIONE PLAYER2 (Client)
//        Gioco.scegliNome(player1);                       //IMPOSTAZIONE NOME PLAYER1       
//
//        /////////////////////////////////////////////////////////////NECESSARIO PER LA COMUNICAZIONE (SERVER - CLIENT) 
//        ServerSocket s1 = new ServerSocket(6666);
//        Socket ss = s1.accept();
//        PrintStream p = new PrintStream(ss.getOutputStream());
//        String messaggio;
//        System.out.println("IN ATTESA DI UN ALTRO GIOCATORE");
//        /////////////////////////////////////////////////////////////NECESSARIO PER LA COMUNICAZIONE (SERVER - CLIENT) 
//
//        Scanner sc = new Scanner(ss.getInputStream());                      // IMPOSTAZIONE NOME PLAYER2
//        messaggio = sc.nextLine();                                          //
//        player2.setNome(messaggio);                                         //
//        p.println(player1.getNome());                                       //
//
//        Mazzo Mazzo = new Mazzo();                              //CREAZIONE MAZZO
//        Carta Briscola = new Carta();                           //CREAZIONE BRISCOLA
//
//        System.out.println("BENVENUTO " + player1.getNome() + " IL TUO AVVERSARIO è " + player2.getNome());
//
//        Carta briscola = Mazzo.getMazzo().pop();
//        System.out.println("La briscola è " + briscola.getNumero() + " " + briscola.stampaSeme());
//        p.println("La briscola è " + briscola.getNumero() + " " + briscola.stampaSeme());
//
//        //////////INIZIA IL GIOCO/////////
//        Carta cartaPescata = Mazzo.getMazzo().pop();
//        player1.pesco(cartaPescata);
//        cartaPescata = Mazzo.getMazzo().pop();
//        player2.pesco(cartaPescata);
//        cartaPescata = Mazzo.getMazzo().pop();
//        player1.pesco(cartaPescata);
//        cartaPescata = Mazzo.getMazzo().pop();
//        player2.pesco(cartaPescata);
//
//        ////////INIZIO DEI TURNI///////////
//        boolean ordine = true;
//        for (int i = 1; i < 20; i++) {
//            ordine = Gioco.turno(ordine, player1, player2, i, Briscola, p, ss, sc);
//
//        }
//
//        ///////DICHIARAZIONE VINCITORE///////
//        Gioco.stampaPunteggio(player1, player2, p, ss, sc);
//        
//        while(true){
//            
//        }
//    }
}
