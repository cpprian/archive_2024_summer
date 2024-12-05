package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Player {
    private final int playerNumber;
    private final Label playerTurnInfo;
    private final Label playerName;
    private final Pane playerFrame;
    private final Label scoreCounter;
    private int playerScore;
    private boolean isActive;

    Player(boolean active, int pNumber, Label turnInfo, Label name, Label counter, Pane frame){
        playerScore = 0;
        isActive = active;
        playerNumber = pNumber;
        playerTurnInfo = turnInfo;
        playerName = name;
        playerFrame = frame;
        scoreCounter = counter;
    }

    void increaseScore(int score){
        playerScore += score;
        scoreCounter.setText(Integer.toString(playerScore));
    }

    int getScore(){
        return playerScore;
    }

    void toggleActive(int score){
        if(isActive){
            increaseScore(score);
            if(!(isActive = score > 0)){
                deactivateFrame();
            }
            else{
                bonusTurnInfo();
            }
        }
        else{
            if((isActive = (score == 0))){
                activateFrame();
                newTurnInfo();
            }
        }
    }
    private void activateFrame(){
        playerName.getStyleClass().add("activePlayerName");
        playerFrame.getStyleClass().add("frame");
    }
    private void deactivateFrame(){
        playerName.getStyleClass().remove("activePlayerName");
        playerFrame.getStyleClass().remove("frame");
    }
    private void bonusTurnInfo(){
        clearStyleInfo();
        playerTurnInfo.getStyleClass().add("infoTextBonus");
        playerTurnInfo.setText("Bonusowa tura!");
    }
    private void newTurnInfo(){
        clearStyleInfo();
        playerTurnInfo.getStyleClass().add("infoText");
        playerTurnInfo.setText("Tura Gracza " + playerNumber);
    }
    private void clearStyleInfo(){
        playerTurnInfo.getStyleClass().remove("infoText");
        playerTurnInfo.getStyleClass().remove("infoTextBonus");
    }

    boolean getActive(){
        return isActive;
    }

}
