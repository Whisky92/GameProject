package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import jsondatas.JsonMaker;
import jsondatas.PlayerDatas;
import org.tinylog.Logger;

public class ScoreBoardController {

    @FXML
    private TableView<PlayerDatas> dataTable;
    @FXML
    private TableColumn<PlayerDatas,String> playerCol;
    @FXML
    private TableColumn<PlayerDatas, Integer> stepCol;
    @FXML
    private Button backButton;

    JsonMaker builder = new JsonMaker();

    @FXML
    private void initialize() throws IOException {
        Logger.trace("Scoreboard is initializing");
        playerCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepCol.setCellValueFactory(new PropertyValueFactory<>("steps"));
        List<PlayerDatas> players = builder.readFromFile("winners.json");
        ObservableList<PlayerDatas> observableList = FXCollections.observableArrayList();
        observableList.addAll(players);
        dataTable.setItems(observableList);
    }

    @FXML
    private void handleCancelButton(ActionEvent event) throws IOException {
        Logger.trace("Entering the handleCancelButton of ScoreBoardController");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
