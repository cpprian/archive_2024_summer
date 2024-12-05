module com.example.sosgame {
    requires javafx.controls;
    requires javafx.fxml;


    exports sample;
    opens sample to javafx.fxml;
}