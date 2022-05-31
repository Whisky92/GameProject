package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class PlayerNameController {

    @FXML
    Button playerNameButton;

    @FXML
    TextField playerNameTextField;

    @FXML
    private void handlePlayerName(ActionEvent event) throws IOException {
        Logger.trace("Entering the handlePlayerName method of PlayerNameController class");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        gameController.setPlayerName(playerNameTextField.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
