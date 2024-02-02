package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblErrorMsg;

    @FXML
    void loginOnAction(ActionEvent event) throws IOException, SQLException {

        if (!txtUsername.getText().isEmpty()){
            lblErrorMsg.setText("");
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/clientForm.fxml"));

            ClientFormController controller = new ClientFormController();
            controller.setClientName(txtUsername.getText());
            fxmlLoader.setController(controller);

            stage.setScene(new Scene(fxmlLoader.load()));
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            txtUsername.clear();
        }else {
            lblErrorMsg.setText("Please enter your username!");
        }
    }
}
