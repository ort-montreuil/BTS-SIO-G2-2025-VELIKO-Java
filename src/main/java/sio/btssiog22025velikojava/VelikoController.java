package sio.btssiog22025velikojava;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.btssiog22025velikojava.controllers.StationController;
import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class VelikoController implements Initializable {

    DataSourceProvider provider;
    StationController stationController;

    @FXML
    private WebView wvVeliko;
    @FXML
    private TableColumn tcStatut;
    @FXML
    private Label lblTitre;
    @FXML
    private Button btnChangementDeMotDePasse;
    @FXML
    private TableColumn tcEmail;
    @FXML
    private TableView tvUtilisateurs;
    @FXML
    private TableColumn tcNom;
    @FXML
    private Button btnBlocage;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button menuGestionUtilisateurs;
    @FXML
    private AnchorPane appGestionParc;
    @FXML
    private AnchorPane appTableauDeBord;
    @FXML
    private AnchorPane appGestionDesUtilisateurs;
    @FXML
    private Button menuGestionParc;
    @FXML
    private Button menuTableauDeBord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appGestionParc.toFront();
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        stationController = new StationController();
        List<Station> stations;
        try {
            stations = stationController.allStation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Load the map
        WebEngine webEngine = wvVeliko.getEngine();
        URL mapUrl = getClass().getResource("/html/map.html");

        if (mapUrl != null) {
            //Charger la map
            webEngine.load(mapUrl.toExternalForm());

            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {

                    //Renvoyer liste des stations
                    webEngine.executeScript("window.stations = " + convertStationsToJson(stations) + ";");

                    //Lancer la fonction loadStations()
                    webEngine.executeScript("loadStations();");
                }
            });
        } else {
            System.err.println("Carte introuvable.");
        }

    }

    // convert list of stations to json
    private String convertStationsToJson(List<Station> stations) {
        StringBuilder json = new StringBuilder("[");
        for (Station station : stations) {
            json.append("{")
                    .append("\"name\":\"").append(station.getName()).append("\",")
                    .append("\"lat\":").append(station.getLat()).append(",")
                    .append("\"lon\":").append(station.getLon()).append(",")
                    .append("\"capacity\":").append(station.getCapacity()).append(",")
                    .append("\"mechanical_bikes\":").append(station.getNum_mechanical_bikes_available()).append(",")
                    .append("\"electric_bikes\":").append(station.getNum_electric_bikes_available())
                    .append("},");
        }
        if (json.length() > 1) {
            json.setLength(json.length() - 1); // Remove the last comma
        }
        json.append("]");
        return json.toString();
    }

    @FXML
    public void menuClicked(javafx.event.ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == menuGestionUtilisateurs) {
            appGestionDesUtilisateurs.toFront();
        } else if (actionEvent.getSource() == menuGestionParc) {
            appGestionParc.toFront();
        } else if (actionEvent.getSource() == menuTableauDeBord) {
            appTableauDeBord.toFront();
        }
    }
}