package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Board implements Initializable {
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button nextTurnButton;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private Label playerTurnInfo;
    @FXML
    private Button restartButton;

    @FXML
    private Pane player1Frame;
    @FXML
    private Pane player2Frame;

    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;

    @FXML
    private Pane instructionPane;


    ArrayList<Button> buttons;
    private Button clickedButton = null;

    Player player1;
    Player player2;
    GameRules rules;
    private int mapSize = 6;
    private final int MAX_MAP_SIZE = 9;
    private final int BUTTON_SIZE = 50;
    private final int FLOW_PANE_X = 41;
    private final int FLOW_PANE_Y = 76;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>();
        flowPane.setPrefHeight(mapSize * BUTTON_SIZE);
        flowPane.setPrefWidth(mapSize * BUTTON_SIZE);
        flowPane.setLayoutX(FLOW_PANE_X + (BUTTON_SIZE / 2 * (MAX_MAP_SIZE - mapSize)));
        flowPane.setLayoutY(FLOW_PANE_Y + (BUTTON_SIZE / 2 * (MAX_MAP_SIZE - mapSize)));

        for (int i = 0; i < mapSize * mapSize; i++) {
            Button b = new Button();
            b.getStyleClass().add("button_standard");
            buttons.add(b);
            flowPane.getChildren().add(buttons.get(i));
        }
        buttons.forEach(button -> {
            setupButton(button);
            button.setFocusTraversable(false);
        });
        resetGame();
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setSymbol(button);
        });
    }

    private void setButtonStyle(Button button, String style) {
        button.getStyleClass().remove("button_S");
        button.getStyleClass().remove("button_O");
        button.getStyleClass().add(style);
    }

    private String getSymbol(Button button) {
        nextTurnButton.setDisable(false);
        if (Objects.equals(button.getText(), " ")) {
            setButtonStyle(button, "button_S");
            return "S";
        } else if (Objects.equals(button.getText(), "S")) {
            setButtonStyle(button, "button_O");
            return "O";
        } else {
            clickedButton = null;
            nextTurnButton.setDisable(true);
            setButtonStyle(button, "button_standard");
            return " ";
        }
    }

    public void setSymbol(Button button) {
        if (clickedButton == null || clickedButton == button) {
            clickedButton = button;
            button.setText(getSymbol(button));
        }
    }

    public void nextTurn() {
        int score = rules.searchForNewSOS(clickedButton);
        player1.toggleActive(score);
        player2.toggleActive(score);
        clickedButton.setDisable(true);
        nextTurnButton.setDisable(true);
        clickedButton = null;
        rules.endCheck(player1, player2);
    }

    public void hideInstruction() {
        instructionPane.setVisible(false);
    }

    public void resetGame() {
        player1 = new Player(true, 1, playerTurnInfo, player1Name, player1Score, player1Frame);
        player2 = new Player(false, 2, playerTurnInfo, player2Name, player2Score, player2Frame);

        playerTurnInfo.setText("Tura Gracza 1");
        player1Score.setText("0");
        player2Score.setText("0");
        player1Frame.getStyleClass().remove("frame");
        player1Name.getStyleClass().remove("activePlayerName");
        player2Frame.getStyleClass().remove("frame");
        player2Name.getStyleClass().remove("activePlayerName");
        player1Frame.getStyleClass().add("frame");
        player1Name.getStyleClass().add("activePlayerName");
        playerTurnInfo.getStyleClass().remove("infoTextBonus");
        nextTurnButton.setDisable(true);
        restartButton.setDisable(true);
        rules = new GameRules(buttons, restartButton, playerTurnInfo, mapSize);
        buttons.forEach(button -> button.setText(" "));
        buttons.forEach(button -> button.setDisable(false));
        buttons.forEach(button -> setButtonStyle(button, "button_standard"));
    }
}
