package sio.btssiog22025velikojava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VelikoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}