package sio.btssiog22025velikojava;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.btssiog22025velikojava.controllers.StationController;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.sql.DriverManager.println;

public class VelikoController implements Initializable {

DataSourceProvider provider;
StationController stationController;

    @FXML
    private WebView wvVeliko;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        stationController = new StationController();
        try {
            stationController.allStation().forEach(station -> {
                System.out.println(station.getStation_id() + " " + station.getName());
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Charger la carte
        WebEngine webEngine = wvVeliko.getEngine();
        URL mapUrl = getClass().getResource("/html/map.html");

        if (mapUrl != null) {
            webEngine.load(mapUrl.toExternalForm());
        } else {
            System.err.println("Carte introuvable.");
        }
    }
}