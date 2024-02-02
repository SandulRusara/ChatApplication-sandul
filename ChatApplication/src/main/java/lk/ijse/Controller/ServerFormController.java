package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lk.ijse.Server.Server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    private static VBox staticVbox;
    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staticVbox = vBox;
        receiveMessage("Sever Starting...");

        vBox.heightProperty().addListener(new ChangeListener<Number>() { //Auto scrolling Vbox
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = Server.getInstance();
                    server.makeSocket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        receiveMessage("Server Running...");
        receiveMessage("Waiting for user...");
    }

    public static void receiveMessage(String msg){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5,5,0,10));
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() { //update javaFX UI because use multi thread
            @Override
            public void run() {
                //vBox.getChildren().add(hBox);
                staticVbox.getChildren().add(hBox);
            }
        });
    }
}
