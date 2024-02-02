package lk.ijse.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServerLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent rootNode2 = FXMLLoader.load(getClass().getResource("/view/serverForm.fxml"));
        Scene scene1 = new Scene(rootNode2);
        Stage stage1 = new Stage();
        stage1.setScene(scene1);
        stage1.initModality(Modality.WINDOW_MODAL);
        stage1.centerOnScreen();
        stage1.show();

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
