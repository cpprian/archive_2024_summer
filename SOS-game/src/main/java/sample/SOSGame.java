package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SOSGame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        root.getStylesheets().add(String.valueOf(
                Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm()));
        primaryStage.setTitle("SOS game");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(700);
        primaryStage.setHeight(570);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
