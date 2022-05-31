package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jsondatas.JsonMaker;
import model.Cell;
import java.awt.Point;
import model.States;
import model.Table;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.tinylog.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameController {
    @FXML
    private GridPane board;
    @FXML
    private StackPane outerPane;
    @FXML
    private HBox hBoxContainer;
    @FXML
    private VBox vBoxContainer;
    @FXML
    private TextField stepTextField;
    @FXML
    private Label playerLabel = new Label();
    private Image circleImage = new Image("circle.png");
    private Image rectangleImage = new Image("rectangle.png");
    private Image goalImage = new Image("goal.png");
    private Image startImage = new Image("start.png");
    private Image emptyImage = new Image("white.png");
    private Image piece = new Image("piece.png");
    private Image coverRec = new Image("coverRec.png");
    private Table table = new Table();

    JsonMaker jsonMaker = new JsonMaker();

    private static StringProperty playerName = new SimpleStringProperty();

    private ArrayList<StackPane> listOfPossibleStackPanes = new ArrayList();

    private IntegerProperty stepCount = new SimpleIntegerProperty();

    @FXML
    public void initialize() {
        Logger.info("Entering the initialize method in GameController class");
        createBinding();
        setProperties();
        fillTableWithPicturesOfStates();
        StackPane sp = getStackPaneByRowAndColIndex(0, 0);
        sp.getChildren().add(new ImageView(piece));
        table.showPossibleSteps();
        readValuesFromList();
    }

    public void readValuesFromList() {
        Logger.info("Entering the readValuesFromList method in GameController class");
        LinkedList<Cell> listOfPositions = table.getPossibleSteps();
        listOfPossibleStackPanes.clear();
        for (int i = 0; i < listOfPositions.size(); i++) {
            int xPos = listOfPositions.get(i).getPosition().x;
            int yPos = listOfPositions.get(i).getPosition().y;
            StackPane temporaryPane = getStackPaneByRowAndColIndex(xPos, yPos);
            listOfPossibleStackPanes.add(temporaryPane);
            temporaryPane.getChildren().add(createRectangleToCover());
        }
        Logger.info("Reaching the end of readValuesFromList method");
    }

    private void handleMouseClick(MouseEvent e) {
        Logger.info("Entering the handleMouseClick method in GameController class");
        StackPane source = (StackPane) e.getSource();
        if (listOfPossibleStackPanes.contains(source)) {
            Logger.info("The event target is part of the possible steps");
            int xPos = board.getRowIndex(source);
            int yPos = board.getColumnIndex(source);
            Point temporaryPoint = new Point(xPos, yPos);
            clearIllegalCells();
            restoreOpacity();
            table.movePiece(temporaryPoint);
            stepCount.set(table.getStepCounter());
            ifGoalState(xPos, yPos);
            reduceOpacity(source);
            source.getChildren().add(new ImageView(piece));
            readValuesFromList();
        } else
            Logger.warn("You can't step on this cell");
    }

    public void clearIllegalCells() {
        Logger.info("Entering the clearIllegalCells method in GameController class");
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane temporaryPane=getStackPaneByRowAndColIndex(i,j);
                int sizeOfCurrentPane = temporaryPane.getChildren().size();
                if(sizeOfCurrentPane>=1) {
                    Logger.info("The number of children of the current StackPane is greater than or equal to 1");
                    ImageView currentImageView = (ImageView) temporaryPane.getChildren().get(sizeOfCurrentPane - 1);
                    if (sizeOfCurrentPane > 1) {
                        Logger.info("The number of children of the current StackPane is greater than 1");
                        for (int k = 0; k < temporaryPane.getChildren().size(); k++) {
                            if (k > 0)
                                temporaryPane.getChildren().remove(k);
                        }
                    } else if (((sizeOfCurrentPane == 1) && (currentImageView.getImage() == piece || currentImageView.getImage() == coverRec))) {
                        Logger.info("The size of current pane is 1 and the image on the ImageView is a piece or a rectangle used for marking the position of possible steps");
                        temporaryPane.getChildren().remove(0);
                    }
                }
            }
        }
    }

    public void restoreOpacity() {
        Logger.info("Entering the restoreOpacity method of GameController class");
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane p = (StackPane) getStackPaneByRowAndColIndex(i, j);
                if(p.getChildren().size()>0) {
                    Logger.info("The number of children of the current StackPane is greater than 0");
                    ImageView temporaryImageView = (ImageView) p.getChildren().get(p.getChildren().size() - 1);
                    if (temporaryImageView.getOpacity() != 1) {
                        temporaryImageView.setOpacity(1);
                        Logger.info("The opacity of the current ImageView is less than 1");
                    }
                }
            }
        }
    }


    public ImageView createRectangleToCover() {
        Logger.info("Entering the createRectangle method in GameController class");
        ImageView coverImg = new ImageView(coverRec);
        return coverImg;
    }


    public void reduceOpacity(StackPane source)
    {
        Logger.info("Entering the reduceOpacity method of GameController class");
        if(source.getChildren().size()>0) {
            Logger.info("The number of children of the current StackPane is greater than 0");
            ImageView temporaryImageView = (ImageView) source.getChildren().get(source.getChildren().size() - 1);
            temporaryImageView.setOpacity(0.5);
        }

    }
    private void createBinding()
    {
        Logger.info("Entering the createBinding method in GameController class");
        stepTextField.textProperty().bind(stepCount.asString());
        playerLabel.textProperty().bind(playerName);
    }

    public StackPane getStackPaneByRowAndColIndex(int i, int j) {
        Logger.info("Entering the getStackPaneByRowAndColIndex method in GameController class with i: {}, and j: {}",i,j);
        int childrenIndex = i * 8 + (j + 1);
        StackPane returnPane = (StackPane) board.getChildren().get(childrenIndex);
        return returnPane;
    }
    public void setProperties()
    {
        Logger.info("Entering the setProperties method in GameController class");
        setPropertiesOfHBoxContainer();
        setPropertiesOfVBoxContainer();
        setPropertiesOfOuterPane();
    }
    public void setPropertiesOfOuterPane()
    {
        Logger.info("Entering the SetPropertiesOfOuterPane method in GameController class");
        outerPane.setBackground(
                new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        outerPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
    }
    public void setPropertiesOfHBoxContainer()
    {
        Logger.info("Entering the setPropertiesOfHBoxContainer method in GameController class");
        hBoxContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
    }
    public void setPropertiesOfVBoxContainer()
    {
        Logger.info("Entering setPropertiesOfVBoxContainer method in GameController class");
        vBoxContainer.setBackground(
                new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vBoxContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,1,5))));
    }

    public static void setPlayerName(String p)
    {
        Logger.info("Entering the setPlayerName method in GameController class");
        playerName.set(p);
    }

    public void fillTableWithPicturesOfStates() {
        Logger.info("Entering the fillTableWithPicturesOfStates method in GameController class");
        for (int i = 0; i < board.getRowCount(); i++)
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane sp = new StackPane();
                addPictures(sp, i, j);
            }
    }

    public void addPictures(StackPane p, int i, int j) {
        Logger.info("Entering the addPictures method in GameController class");
        if (table.getGameTable()[i][j].getState() == States.CIRCLE) {
            Logger.info("Current state is Circle");
            ImageView temp = new ImageView(circleImage);
            p.getChildren().add(temp);
        }
        else if (table.getGameTable()[i][j].getState() == States.RECTANGLE) {
            Logger.info("Current state is Rectangle");
            ImageView temp = new ImageView(rectangleImage);
            p.getChildren().add(temp);
        }
        else if (table.getGameTable()[i][j].getState() == States.START) {
            Logger.info("Current state is Start");
            ImageView temp = new ImageView(startImage);
            temp.setOpacity(0.5);
            p.getChildren().add(temp);
        }
        else if (table.getGameTable()[i][j].getState() == States.GOAL) {
            Logger.info("Current state is Goal");
            ImageView temp = new ImageView(goalImage);
            p.getChildren().add(temp);
        }
        p.setAlignment(Pos.CENTER);
        p.setOnMouseClicked(this::handleMouseClick);
        board.add(p, j, i);
    }


    public void ifGoalState(int x, int y)
    {
        Logger.info("Entering thew ifGoalState method of GameController class");
        if(table.isGoalState()) {
            try {
                Logger.info("Trying to save the data to a File");
                jsonMaker.saveToFile("winners.json", playerName.get(),(stepCount.get()));
                displayVictory(board);
            } catch (IOException ex) {
                Logger.error(ex);
                Logger.error("An error occured: {}",ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public void displayVictory(GridPane grid) {
        Logger.info("Entering the displayVictory method of GameController class");
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("You Won!");
        Label label = new Label();
        label.setFont(new Font("Arial BLACK",20));
        label.setText("Congratulations! You Won!");
        Button backButton = new Button("Back to main menu");
        Button victoryButton = new Button("Exit");
        backButtonSetOnAction(window, grid, backButton);
        victoryButtonSetOnAction(window, grid, victoryButton);
        setOnWindowCloseRequest(window, grid);
        VBox layout = new VBox(30);
        layout.getChildren().add(label);
        layout.getChildren().add(backButton);
        layout.getChildren().add(victoryButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.showAndWait();
        Logger.info("Reaching the end of displayVictory method");
    }

    public void victoryButtonSetOnAction(Stage window, GridPane grid, Button victoryButton)
    {
        Logger.info("Entering the victoryButtonSetOnAction method of GameController class");
        victoryButton.setOnAction(e->{
            window.close();
            Stage win = (Stage) grid.getParent().getParent().getScene().getWindow();
            win.close();
        });

    }

    public void backButtonSetOnAction(Stage window, GridPane grid, Button backButton)
    {
        Logger.info("Entering the backButtonSetOnAction method of GameController class");
        backButton.setOnAction(e->{
            window.close();
            Logger.trace("Entering the start method of GameApplication class");
            Parent root;
            Stage stage = (Stage) grid.getParent().getParent().getScene().getWindow();
            try {
                Logger.info("Trying to load the FXML file to root from startPage.fxml");
                root = FXMLLoader.load(getClass().getResource("/startPage.fxml"));
            } catch (IOException ex) {
                Logger.error("An error occured: {}",ex);
                throw new RuntimeException(ex);
            }
            stage.setTitle("Table Game");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }

    public void setOnWindowCloseRequest(Stage window, GridPane grid)
    {
        Logger.info("Entering the setOnWindowCloseRequest method of GameController class");
        window.setOnCloseRequest(e ->{
            window.close();
            Stage win = (Stage) grid.getParent().getParent().getScene().getWindow();
            win.close();
        });
    }


}