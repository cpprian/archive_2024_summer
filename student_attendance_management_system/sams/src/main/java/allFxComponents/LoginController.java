package allFxComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField loginid;
    @FXML
    Parent srodek;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainPrograme(ActionEvent actionEvent) throws IOException {

        String username = loginid.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        root = loader.load();

        MainController controller2 = loader.getController();
        controller2.displayName(username);
       // Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }
}
