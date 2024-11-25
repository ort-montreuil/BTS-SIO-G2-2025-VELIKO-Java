package sio.btssiog22025velikojava;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class VelikoController implements Initializable {

    @FXML
    private WebView wvVeliko;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        wvVeliko.getEngine().load("https://openstreetmap.org/");
        //wvVeliko.getEngine().load("https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png");
    }
}
