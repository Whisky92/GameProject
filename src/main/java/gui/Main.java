package gui;

import javafx.application.Application;
import org.tinylog.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.trace("Entering the main method in Main class");
        Application.launch(GameApplication.class, args);

    }
}
