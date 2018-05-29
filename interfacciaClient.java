package serverita;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static serverita.interfacciaServer.root;

public class interfacciaClient extends Application {

    private static Client client;
    static StackPane root = new StackPane();
    static Image sfondo = new Image(interfacciaServer.class.getResource("immagini/sfondo.png").toString());
    static Image gioca = new Image(interfacciaServer.class.getResource("immagini/gioca.png").toString());
    static Image mazzo = new Image(interfacciaServer.class.getResource("immagini/mazzo.jpg").toString());
    private Carta briscola = null;

    public static void main(String argv[]) {
        launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        client = new Client();
        GridPane grid = new GridPane();
        TextField text = new TextField();
        Button btn = new Button("Imposta");
        Button esci = new Button("Esci");
        Label label = new Label("Imposta il tuo nome");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nome = text.getText();
                client.setNomeClient(nome);
                client.inviaMessaggio(nome);
                try {
                    client.setNomeServer(client.attendi());
                    client.setBriscola(client.riceviCarta(client.attendi()));
                    System.out.println("La briscola è " + client.getBriscola().stampaCarta());
                } catch (IOException ex) {
                    Logger.getLogger(interfacciaClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                schermataGioco(primaryStage);
            }
        });
        esci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    client.chiudiConnessione();
                    primaryStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(interfacciaClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        grid.add(text, 1, 0);
        grid.add(btn, 2, 0);
        grid.add(label, 0, 0);
        grid.add(esci, 2, 3);
        Scene scene = new Scene(grid, 500, 100);
        primaryStage.setTitle("Briscola Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void schermataGioco(Stage primaryStage) {
        Button disconnected = new Button("Disconnetti");
        Label nomeProprio = new Label(client.getNomeClient());
        Label nomeAvversario = new Label(client.getNomeServer());
        StackPane.setAlignment(nomeProprio, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(nomeAvversario, Pos.TOP_LEFT);
        StackPane.setAlignment(disconnected, Pos.BOTTOM_CENTER);
        ImageView ivsfondo = new ImageView(this.sfondo);
        ivsfondo.setScaleX(5);
        ivsfondo.setScaleY(5);
        ImageView ivgioca = new ImageView(this.gioca);
        ivgioca.setScaleX(0.2);
        ivgioca.setScaleY(0.2);
        ImageView ivmazzo = new ImageView(this.mazzo);
        ivmazzo.setScaleX(1.5);
        ivmazzo.setScaleY(1.5);

        ivgioca.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                ivgioca.setVisible(false);
                interfacciaClient.stampoBriscola();
                //interfacciaServer.turnoGrafico(server.getGioco().getMazzo());

            }
        });

        disconnected.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    client.chiudiConnessione();
                } catch (IOException ex) {
                    Logger.getLogger(interfacciaServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                primaryStage.close();
            }
        });

        Scene scene = new Scene(root, 1280, 720);
        StackPane.setAlignment(ivgioca, Pos.CENTER);
        root.getChildren().add(ivsfondo);
        root.getChildren().add(ivgioca);
        root.getChildren().add(nomeProprio);
        root.getChildren().add(nomeAvversario);
        root.getChildren().add(disconnected);
        primaryStage.setTitle("Briscola");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void stampoBriscola() {
        Image a = new Image(interfacciaServer.class.getResource("immagini/" + client.getBriscola().getSeme() + "" + client.getBriscola().getNumero() + ".jpg").toString());
        ImageView ivbriscola = new ImageView(a);
        ivbriscola.setRotate(ivbriscola.getRotate() + 90);
        ivbriscola.setScaleX(1.3);
        ivbriscola.setScaleY(1.3);
        StackPane.setMargin(ivbriscola, new Insets(400, 400, 400, 300));
        StackPane.setAlignment(ivbriscola, Pos.CENTER_RIGHT);
        root.getChildren().add(ivbriscola);
        System.out.println("La briscola è " + client.getBriscola().stampaCarta());
    }
}
