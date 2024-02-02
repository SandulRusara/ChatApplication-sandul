package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lk.ijse.EMoji.EmojiBar;
import lk.ijse.Sinhala.SinhalaTyping;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label lblUsername;
    private String username;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    @FXML
    private TextField txtMsg;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    @FXML
    private JFXButton emojiBtn;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnMaxRestore;
    @FXML
    private JFXButton btnMinimize;
    @FXML
    private JFXButton btnSinhala;
    @FXML
    private JFXButton btnRestore;
    @FXML
    private Circle firstCircle;
    @FXML
    private Rectangle rectangleMin;

    private double x = 0;
    private double y = 0;
    EmojiBar emojiBar = new EmojiBar();
    SinhalaTyping characterBar = new SinhalaTyping();

    public ClientFormController(){

    }

    @FXML
    void closeOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        shutdown();
    }

    @FXML
    void maxRestoreOnAction(ActionEvent event) {
        Stage stage = (Stage) btnMaxRestore.getScene().getWindow();
        stage.setMaximized(true);
        if (stage.isMaximized()){
            btnMaxRestore.setVisible(false);
            firstCircle.setVisible(false);
            rectangleMin.setVisible(false);
        }
    }

    @FXML
    void restoreOnAction(ActionEvent event) {
        Stage stage = (Stage) btnRestore.getScene().getWindow();
        stage.setMaximized(false);
        if (!stage.isMaximized()){
            btnMaxRestore.setVisible(true);
            firstCircle.setVisible(true);
            rectangleMin.setVisible(true);
        }
    }

    @FXML
    void minimizeOnAction(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void anchorPaneDraggedOnAction(MouseEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }

    @FXML
    void anchorPanePressedOnAction(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void setUsername() throws SQLException {

             lblUsername.setText(username);

    }

    public void shutdown() {
        ServerFormController.receiveMessage(username + " left.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost" , 3009);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    ServerFormController.receiveMessage(username + " joined...");
                    while (socket.isConnected()){
                        String receiveMsg = dataInputStream.readUTF();
                        receiveMessage(receiveMsg , ClientFormController.this.vBox);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        this.vBox.heightProperty().addListener(new ChangeListener<Number>() { //VBox Auto Scrolling
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
        emoji();
        sinhalaWords();
    }

    private void sinhalaWords(){

        VBox vBox = new VBox(characterBar);
        vBox.setPrefSize(150,300);
        vBox.setStyle("-fx-font-size: 25");
        vBox.setLayoutX(50);
        vBox.setLayoutY(350);

        anchorpane.getChildren().add(vBox);
        characterBar.setVisible(false);

        btnSinhala.setOnAction(event ->{
            if (characterBar.isVisible()){
                characterBar.setVisible(false);
            }else {
                characterBar.setVisible(true);
            }
        });

        characterBar.getSinhalaWordsView().setOnMouseClicked(event ->{
            String selectCharacter = characterBar.getSinhalaWordsView().getSelectionModel().getSelectedItem();
            if (selectCharacter != null){
                txtMsg.setText(txtMsg.getText() + selectCharacter);
            }
          //  characterBar.setVisible(false);
        });
    }

    private void emoji() {

        VBox vBox = new VBox(emojiBar);
        vBox.setPrefSize(150,300);
        vBox.setStyle("-fx-font-size: 25");
        vBox.setLayoutX(0);
        vBox.setLayoutY(350);

        anchorpane.getChildren().add(vBox);

        emojiBar.setVisible(false);

        emojiBtn.setOnAction(event -> {
            if (emojiBar.isVisible()){
                emojiBar.setVisible(false);
            }else {
                emojiBar.setVisible(true);
            }
        });

        emojiBar.getImjoListView().setOnMouseClicked(event ->{
            String selectEmoji = emojiBar.getImjoListView().getSelectionModel().getSelectedItem();
            if (selectEmoji != null){
                txtMsg.setText(txtMsg.getText() + selectEmoji);
            }
            emojiBar.setVisible(false);
        });
    }

    public static void receiveMessage(String receiveMessage , VBox vBox){
        if (receiveMessage.matches(".*\\.(png|jpe?g|gif)$")){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);

            Text textName = new Text(receiveMessage.split("-")[0]);
            TextFlow textFlow = new TextFlow(textName);
            hBox.getChildren().add(textFlow);

            Image image = new Image(receiveMessage.split("-")[1]);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            HBox hBoxImage = new HBox();
            hBoxImage.setAlignment(Pos.CENTER_LEFT);
            hBoxImage.setPadding(new Insets(5,5,5,10));
            hBoxImage.getChildren().add(imageView);

            HBox hBoxTime = setTimeOnMsg("LEFT");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBox);
                    vBox.getChildren().add(hBoxImage);
                    vBox.getChildren().add(hBoxTime);
                }
            });
        }else {

            String username = receiveMessage.split("-")[0];
            String clientMsg = receiveMessage.split("-")[1];

            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            hBoxName.setPadding(new Insets(5, 10, 0, 10));

            Text name = new Text(username);
            TextFlow textFlowName = new TextFlow(name);
            hBoxName.getChildren().add(textFlowName);

            HBox hBoxMsg = new HBox();
            hBoxMsg.setAlignment(Pos.CENTER_LEFT);
            hBoxMsg.setPadding(new Insets(5, 10, 0, 5));

            Text textMsg = new Text(clientMsg);
            textMsg.setFill(Color.color(0, 0, 0));
            TextFlow textFlow = new TextFlow(textMsg);
            textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            hBoxMsg.getChildren().add(textFlow);

            HBox hBoxTime = ClientFormController.setTimeOnMsg("LEFT");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBoxMsg);
                    vBox.getChildren().add(hBoxTime);
                }
            });
        }
    }

    @FXML
    void sendMsgOnAction(ActionEvent event) throws SQLException {
        sendMsg(txtMsg.getText());
    }

    @FXML
    void sendImageOnAction(ActionEvent event) throws SQLException {
        FileDialog fileDialog = new FileDialog((Frame) null,"Select Image");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);
        String file = fileDialog.getDirectory() + fileDialog.getFile();
        fileDialog.dispose();
        sendImage(file);
    }

    public void sendImage(String sendImage) throws SQLException {
        Image image = new Image(sendImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));
        hBox.getChildren().add(imageView);

        HBox hBoxTime = setTimeOnMsg("RIGHT");

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(hBoxTime);
        try {
            dataOutputStream.writeUTF(username + "-" + sendImage);
            dataOutputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (characterBar.isVisible() || emojiBar.isVisible()) {
            characterBar.setVisible(false);
            emojiBar.setVisible(false);
        }
    }

    public void sendMsg(String sendClientMsg) throws SQLException {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 0, 10));

        Text text = new Text(sendClientMsg);
        text.setStyle("-fx-font-size: 14");
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: #904aae; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1));

        hBox.getChildren().add(textFlow);

        HBox hBoxName = new HBox();
        hBoxName.setAlignment(Pos.CENTER_RIGHT);
        hBoxName.setPadding(new Insets(5, 5, 0, 5));

        HBox hBoxTime = setTimeOnMsg("RIGHT");

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(hBoxTime);

            try {
                dataOutputStream.writeUTF(username + "-" + sendClientMsg);
                dataOutputStream.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (characterBar.isVisible() || emojiBar.isVisible()){
                characterBar.setVisible(false);
                emojiBar.setVisible(false);
            }
            txtMsg.clear();
    }

    public static HBox setTimeOnMsg(String alignment){
        HBox hBox = new HBox();
        if (alignment == "RIGHT"){
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(3,12,5,10));
        }else {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(3,10,5,12));
        }

        String setTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Text time = new Text(setTime);
        time.setStyle("-fx-font-size: 8");

        hBox.getChildren().add(time);
        return hBox;
    }

    public void setClientName(String name){
        this.username = name;
    }
}
