package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;


public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Logger.trace("Entering the start method of GameApplication class");
        Parent root = FXMLLoader.load(getClass().getResource("/startPage.fxml"));
        stage.setTitle("Table Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}