package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Objects;

public class GameRules {
    private final Button[][] buttons;
    private Integer turn;
    private final Button restartButton;
    private final Label resultInfo;
    private final int mapSize;
    private final String S = "S";
    private final String O = "O";


    public GameRules(ArrayList<Button> arButtons, Button reset, Label playerTurnInfo, int mapSize) {
        buttons = new Button[mapSize][mapSize];
        int button = 0;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                buttons[i][j] = arButtons.get(button);
                button++;
            }
        }
        restartButton = reset;
        restartButton.setDisable(true);
        resultInfo = playerTurnInfo;
        turn = 1;
        this.mapSize = mapSize;
    }


    public int searchForNewSOS(Button button) {
        int score = 0;
        int posX = setPositions(button)[1];
        int posY = setPositions(button)[0];

        if (Objects.equals(button.getText(), S))
            score = searchForNewSOSAfterSClicked(button, posX, posY, score);
        else if (Objects.equals(button.getText(), O))
            score = searchForNewSOSAfterOClicked(posX, posY, score);

        return score;
    }

    private int searchForNewSOSAfterOClicked(int posX, int posY, int score) {
        //wyszukiwanie pionowo
        if (checkArrayIndex(posX - 1) && checkArrayIndex(posX + 1))
            if (Objects.equals(buttons[posY][posX - 1].getText(), S) && Objects.equals(buttons[posY][posX + 1].getText(), S))
                score++;
        //wyszukiwanie poziomo
        if (checkArrayIndex(posY - 1) && checkArrayIndex(posY + 1))
            if (Objects.equals(buttons[posY + 1][posX].getText(), S) && Objects.equals(buttons[posY - 1][posX].getText(), S))
                score++;

        if (checkArrayIndex(posY - 1) && checkArrayIndex(posY + 1) && checkArrayIndex(posX - 1) && checkArrayIndex(posX + 1)) {
            //skos \
            if (Objects.equals(buttons[posY - 1][posX - 1].getText(), S) && Objects.equals(buttons[posY + 1][posX + 1].getText(), S))
                score++;
            // skos /
            if (Objects.equals(buttons[posY + 1][posX - 1].getText(), S) && Objects.equals(buttons[posY - 1][posX + 1].getText(), S))
                score++;
        }
        return score;
    }

    private int searchForNewSOSAfterSClicked(Button button, int posX, int posY, int score) {
        int firstYPosition = checkArrayIndex(posY - 1) ? posY - 1 : posY;
        for (; firstYPosition <= (checkArrayIndex(posY + 1) ? posY + 1 : posY); firstYPosition++) {
            int firstXPosition = checkArrayIndex(posX - 1) ? posX - 1 : posX;

            for (; firstXPosition <= (checkArrayIndex(posX + 1) ? posX + 1 : posX); firstXPosition++) {
                if (!Objects.equals(O, buttons[firstYPosition][firstXPosition].getText()))
                    continue;

                int secondXPosition = setSecondPosition(posX, firstXPosition);
                int secondYPosition = setSecondPosition(posY, firstYPosition);

                boolean isChanged = secondXPosition != firstXPosition || secondYPosition != firstYPosition;
                if (posX != firstXPosition && posY != secondYPosition && (secondXPosition == firstXPosition || secondYPosition == firstYPosition))
                    isChanged = false;

                if (Objects.equals(button.getText(), buttons[secondYPosition][secondXPosition].getText()) && isChanged)
                    score++;
            }
        }
        return score;
    }

    private int setSecondPosition(int pos, int firstPosition) {
        int secondPosition = firstPosition;
        if (firstPosition < pos && checkArrayIndex(firstPosition - 1))
            secondPosition--;
        else if (firstPosition > pos && checkArrayIndex(secondPosition + 1))
            secondPosition++;

        return secondPosition;
    }


    private boolean checkArrayIndex(int position) {
        return position >= 0 && position < mapSize;
    }

    private int[] setPositions(Button button) {
        for (int i = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++)
                if (button == buttons[i][j])
                    return new int[]{i, j};
        return null;
    }

    public void endCheck(Player p1, Player p2) {
        turn++;
        if (turn > mapSize * mapSize) {
            restartButton.setDisable(false);
            resultInfo.getStyleClass().remove("infoTextBonus");
            if (p1.getScore() > p2.getScore()) {
                resultInfo.setText("Wygrał Gracz 1!");
            } else if (p2.getScore() > p1.getScore()) {
                resultInfo.setText("Wygrał Gracz 2!");
            } else {
                resultInfo.setText("Remis!");
            }
        }
    }
}
