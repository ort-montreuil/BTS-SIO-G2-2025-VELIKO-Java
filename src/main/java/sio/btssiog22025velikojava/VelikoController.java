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
        appGestionDesUtilisateurs.toFront();
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