package sio.btssiog22025velikojava;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.btssiog22025velikojava.controllers.StationController;
import sio.btssiog22025velikojava.controllers.UserController;
import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.models.User;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VelikoController implements Initializable {

    DataSourceProvider provider;
    StationController stationController;
    UserController userController;

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
    @FXML
    private TableColumn tcPrenom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appGestionParc.toFront();
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        stationController = new StationController();
        userController = new UserController();
        ArrayList<User> lesUser = new ArrayList<>();
        List<Station> stations;
        try {
            stations = stationController.allStation();
            lesUser = userController.allUsers();
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

        // Load the users
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tcPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tcStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

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
            try {
                tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appGestionDesUtilisateurs.toFront();
        } else if (actionEvent.getSource() == menuGestionParc) {
            appGestionParc.toFront();
        } else if (actionEvent.getSource() == menuTableauDeBord) {
            appTableauDeBord.toFront();
        }

    }


    @FXML
    public void deleteClicked(Event event) {
        //Verifier si un utilisateur est selectionné
        if(tvUtilisateurs.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur.");
        }
        else {
            User user = (User) tvUtilisateurs.getSelectionModel().getSelectedItem();

            //Supprimer l'utilisateur
            try {
                userController.deleteUser(user.getEmail());
                tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void blockClicked(Event event) {

        //Verifier si un utilisateur est selectionné
        if(tvUtilisateurs.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur.");
        }
        else {
            User user = (User) tvUtilisateurs.getSelectionModel().getSelectedItem();

            //Bloquer ou débloquer l'utilisateur
            if (!user.getStatut()) {
                try {
                    userController.blockUser(user.getEmail());
                    tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    userController.unblockUser(user.getEmail());
                    tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}