package serverita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javafx.application.Application.launch;

public class interfacciaServer extends Application {

    private Server server;
    static StackPane root = new StackPane();
    static Image sfondo = new Image(interfacciaServer.class.getResource("immagini/sfondo.png").toString());
    static Image gioca = new Image(interfacciaServer.class.getResource("immagini/gioca.png").toString());
    static Image mazzo = new Image(interfacciaServer.class.getResource("immagini/mazzo.jpg").toString());

    public static void main(String argv[]) throws Exception {
        launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("preparazione");
        server = new Server();
        server.impostaNomeClient();
        GridPane grid = new GridPane();
        TextField text = new TextField();
        Label label = new Label("Imposta il tuo nome");
        Button esci = new Button("Esci");
        Button btn = new Button("Imposta");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nome = text.getText();
                server.getGioco().getGiocatore1().setNome(nome);
                server.stampaNomi();
                server.inviaMessaggio(server.gioco.getGiocatore1().getNome());
                server.inviaBriscola();
                schermataGioco(primaryStage);

            }
        });
        esci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    server.chiudiConnessione();
                    primaryStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(interfacciaClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        grid.add(label, 0, 0);
        grid.add(text, 1, 0);
        grid.add(btn, 2, 0);
        grid.add(esci, 2, 3);
        Scene scene = new Scene(grid, 500, 100);
        primaryStage.setTitle("Briscola Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void schermataGioco(Stage primaryStage) {
        Button disconnected = new Button("Disconnetti");
        Label nomeProprio = new Label(server.getGioco().getGiocatore1().getNome());
        Label nomeAvversario = new Label(server.getGioco().getGiocatore2().getNome());
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
                interfacciaServer.stampoBriscola();
                //interfacciaServer.turnoGrafico(server.getGioco().getMazzo());

            }
        });

        disconnected.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    server.chiudiConnessione();
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
        Image a = new Image(interfacciaServer.class.getResource("immagini/" + Server.getGioco().getBriscola().getSeme() + "" + Server.getGioco().getBriscola().getNumero() + ".jpg").toString());;
        ImageView ivbriscola = new ImageView(a);
        ivbriscola.setRotate(ivbriscola.getRotate() + 90);
        ivbriscola.setScaleX(1.3);
        ivbriscola.setScaleY(1.3);
        StackPane.setMargin(ivbriscola, new Insets(400, 400, 400, 300));
        StackPane.setAlignment(ivbriscola, Pos.CENTER_RIGHT);
        root.getChildren().add(ivbriscola);
        System.out.println("La briscola è " + Server.getGioco().getBriscola().stampaCarta());
    }

    public static void stampaMano(ImageView ivcarta1, ImageView ivcarta2, ImageView ivcarta3) {
        StackPane.setAlignment(ivcarta1, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(ivcarta2, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(ivcarta3, Pos.BOTTOM_CENTER);
        ivcarta1.setScaleX(1.3);
        ivcarta1.setScaleY(1.3);
        ivcarta2.setScaleX(1.3);
        ivcarta2.setScaleY(1.3);
        ivcarta3.setScaleX(1.3);
        ivcarta3.setScaleY(1.3);
        StackPane.setMargin(ivcarta1, new Insets(0, 0, 100, 0));

        StackPane.setMargin(ivcarta2, new Insets(0, 0, 100, 300));
        StackPane.setMargin(ivcarta3, new Insets(0, 300, 100, 0));
        root.getChildren().add(ivcarta1);
        root.getChildren().add(ivcarta2);
        root.getChildren().add(ivcarta3);
    }

    public static Image manoAvversaria() {
        Image ImageCarta = new Image(interfacciaServer.class.getResource("immagini/mazzo.jpg").toString());
        return ImageCarta;
    }

    public static void Tavolo(ImageView cartaPrimo, ImageView cartaSecondo) {

        //fare qui animazione,togliere le carte dalla mano e spostarle al tavolo
        StackPane.setAlignment(cartaPrimo, Pos.CENTER);
        StackPane.setAlignment(cartaSecondo, Pos.CENTER);
        cartaPrimo.setScaleX(1.3);
        cartaPrimo.setScaleY(1.3);
        cartaSecondo.setScaleX(1.3);
        cartaSecondo.setScaleY(1.3);
        cartaSecondo.setRotate(30);
        StackPane.setMargin(cartaPrimo, new Insets(100, 0, 0, 0));
        StackPane.setMargin(cartaSecondo, new Insets(100, 0, 0, 150));
        //top right bottom left
        root.getChildren().add(cartaPrimo);
        root.getChildren().add(cartaSecondo);

    }

    public static void stampaManoAvversario(ImageView ivcarta4, ImageView ivcarta5, ImageView ivcarta6) {
        StackPane.setAlignment(ivcarta4, Pos.TOP_CENTER);
        StackPane.setAlignment(ivcarta5, Pos.TOP_CENTER);
        StackPane.setAlignment(ivcarta6, Pos.TOP_CENTER);
        ivcarta4.setScaleX(1.5);
        ivcarta4.setScaleY(1.5);
        ivcarta5.setScaleX(1.5);
        ivcarta5.setScaleY(1.5);
        ivcarta6.setScaleX(1.5);
        ivcarta6.setScaleY(1.5);
        StackPane.setMargin(ivcarta4, new Insets(100, 0, 0, 0));
        StackPane.setMargin(ivcarta5, new Insets(100, 0, 0, 300));
        StackPane.setMargin(ivcarta6, new Insets(100, 300, 0, 0));
        root.getChildren().add(ivcarta4);
        root.getChildren().add(ivcarta5);
        root.getChildren().add(ivcarta6);
    }

//    public static void turnoGrafico(Mazzo mazzo, ImageView cartaAvversario) {
//        //qua genero ma devono arrivarmi in input
//        Image carta1, carta2, carta3, carta4, carta5, carta6;
//        carta1 = pescata(mazzo);
//        carta2 = pescata(mazzo);
//        carta3 = pescata(mazzo);
//        carta4 = manoAvversaria();
//        carta5 = manoAvversaria();
//        carta6 = manoAvversaria();
//        ImageView ivcarta1 = new ImageView(carta1);
//        ImageView ivcarta2 = new ImageView(carta2);
//        ImageView ivcarta3 = new ImageView(carta3);
//        ImageView ivcarta4 = new ImageView(carta4);
//        ImageView ivcarta5 = new ImageView(carta5);
//        ImageView ivcarta6 = new ImageView(carta6);
//        ivcarta1.setScaleX(1.5);
//        ivcarta1.setScaleY(1.5);
//        ivcarta2.setScaleX(1.5);
//        ivcarta2.setScaleY(1.5);
//        ivcarta3.setScaleX(1.5);
//        ivcarta3.setScaleY(1.5);
//        interfacciaServer.stampaManoAvversario(ivcarta4, ivcarta5, ivcarta6);
//        Image mazzo2 = new Image(interfacciaServer.class.getResource("mazzo.jpg").toString());
//        ImageView ivmazzo = new ImageView(mazzo2);
//        ivmazzo.setScaleX(1.5);
//        ivmazzo.setScaleY(1.5);
////            String num =
//        Label a = new Label(Integer.toString(numeroCarte));
//        StackPane.setAlignment(ivmazzo, Pos.CENTER_RIGHT);
//        StackPane.setAlignment(a, Pos.CENTER_RIGHT);
//        StackPane.setMargin(a, new Insets(400, 300, 300, 300));
//        StackPane.setMargin(ivmazzo, new Insets(400, 300, 300, 300));
//        //-----
//        StackPane.setAlignment(ivcarta1, Pos.BOTTOM_CENTER);
//        StackPane.setAlignment(ivcarta2, Pos.BOTTOM_CENTER);
//        StackPane.setAlignment(ivcarta3, Pos.BOTTOM_CENTER);
//
//        StackPane.setMargin(ivcarta1, new Insets(0, 0, 100, 0));
//
//        StackPane.setMargin(ivcarta2, new Insets(0, 0, 100, 300));
//        StackPane.setMargin(ivcarta3, new Insets(0, 300, 100, 0));
//        root.getChildren().add(ivcarta1);
//        root.getChildren().add(ivcarta2);
//        root.getChildren().add(ivcarta3);
//        root.getChildren().add(ivmazzo);
//        root.getChildren().add(a);
//
//        //-------------
//        ivcarta3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                DoubleProperty xValue = new SimpleDoubleProperty();
//                DoubleProperty yValue = new SimpleDoubleProperty();
//
//                Path p = new Path();
//                p.getElements().add(new MoveTo(15, 50));
//                p.getElements().add(new LineTo(0, -300));
//                PathTransition pt = new PathTransition(Duration.millis(2500), p);
//                pt.setNode(ivcarta3);
//
//                xValue.bind(ivcarta3.translateXProperty());
//
//                yValue.bind(ivcarta3.translateYProperty());
//
//                pt.play();
//                //graficaAvversario(cartaAvversario);
//                //qua bisogna far partire un'altra funzione
//            }
//        });
//
//        ivcarta2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                DoubleProperty xValue = new SimpleDoubleProperty();
//                DoubleProperty yValue = new SimpleDoubleProperty();
//
//                Path p2 = new Path();
//                p2.getElements().add(new MoveTo(100, 50));
//                p2.getElements().add(new LineTo(-295, -300));
//                PathTransition pt2 = new PathTransition(Duration.millis(2500), p2);
//                pt2.setNode(ivcarta2);
//
//                xValue.bind(ivcarta2.translateXProperty());
//
//                yValue.bind(ivcarta2.translateYProperty());
//
//                pt2.play();
//                graficaAvversario(cartaAvversario);
//                //qua bisogna far partire un'altra funzione
//            }
//        });
//        ivcarta1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                DoubleProperty xValue = new SimpleDoubleProperty();
//                DoubleProperty yValue = new SimpleDoubleProperty();
//
//                Path p3 = new Path();
//                p3.getElements().add(new MoveTo(50, 50));
//                p3.getElements().add(new LineTo(-145, -300));
//                PathTransition pt3 = new PathTransition(Duration.millis(2500), p3);
//                pt3.setNode(ivcarta1);
//
//                xValue.bind(ivcarta1.translateXProperty());
//
//                yValue.bind(ivcarta1.translateYProperty());
//
//                pt3.play();
//                graficaAvversario(cartaAvversario);
//                //qua bisogna far partire un'altra funzione
//            }
//        });
//
//    }
//
//    public static void graficaAvversario(ImageView cartaAvversario) {
//        root.getChildren().add(cartaAvversario);
//        cartaAvversario.setScaleX(1.5);
//        cartaAvversario.setScaleY(1.5);
//        StackPane.setAlignment(cartaAvversario, Pos.TOP_CENTER);
//
//        DoubleProperty xValue = new SimpleDoubleProperty();
//        DoubleProperty yValue = new SimpleDoubleProperty();
//
//        Path p4 = new Path();
//        p4.getElements().add(new MoveTo(15, 50));
//        p4.getElements().add(new LineTo(0, 536));
//        PathTransition pt4 = new PathTransition(Duration.millis(2500), p4);
//        pt4.setNode(cartaAvversario);
//
//        xValue.bind(cartaAvversario.translateXProperty());
//
//        yValue.bind(cartaAvversario.translateYProperty());
//
//        pt4.play();
//        //qua bisogna far partire un'altra funzione
//
//    }
}
