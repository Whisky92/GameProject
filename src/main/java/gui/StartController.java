package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class StartController {
    @FXML
    Button playButton;
    @FXML
    Button statsButton;


    @FXML
    private void handleStartButton(ActionEvent event) throws IOException {
        Logger.trace("Entering the handleStartButton method of StartController class");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/playerName.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleScoreboardButton(ActionEvent event) throws IOException {
        Logger.trace("Scoreboard button is pressed");
        Logger.info("Scoreboard will appear");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scoreBoard.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
