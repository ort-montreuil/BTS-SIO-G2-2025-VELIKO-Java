package sio.btssiog22025velikojava;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.btssiog22025velikojava.controllers.StatController;
import sio.btssiog22025velikojava.controllers.StationController;
import sio.btssiog22025velikojava.controllers.UserController;
import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.models.User;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class VelikoController implements Initializable {

    DataSourceProvider provider;
    StationController stationController;
    UserController userController;
    StatController statController;

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
    private AnchorPane appGestionDesUtilisateurs;
    @FXML
    private Button menuGestionParc;
    @FXML
    private Button menuTableauDeBord;
    @FXML
    private TableColumn tcPrenom;
    @FXML
    private Label lblTotalVelosMecanique;
    @FXML
    private Label lblTotalReservation;
    @FXML
    private Button btnRetourStat;
    @FXML
    private Label lblTotalFavorie;
    @FXML
    private Button btnSuivantStat;
    @FXML
    private Label lblTotalVelosElectrique;
    @FXML
    private TableView<Station> tvStations;
    @FXML
    private Label lblTotalStation;
    @FXML
    private Label lblTotalVelos;
    @FXML
    private TableColumn tcStation;
    @FXML
    private AnchorPane appTableauDeBord2;
    @FXML
    private AnchorPane appTableauDeBord1;
    @FXML
    private Button btnSuivantStat2;
    @FXML
    private Button btnRetourStat2;
    @FXML
    private PieChart pcReservationStationDepart;
    @FXML
    private PieChart pcReservationStationArrive;
    @FXML
    private Button btnSuivantStat3;
    @FXML
    private AnchorPane appTableauDeBord3;
    @FXML
    private Button btnRetourStat3;
    @FXML
    private TableColumn<Station,Integer> tcTauxRemplissage;
    @FXML
    private TableColumn<Station,Integer> tcEmplacement;
    @FXML
    private TableColumn<Station,Integer> tcNombreVelos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appGestionDesUtilisateurs.toFront();
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        stationController = new StationController();
        userController = new UserController();
        statController = new StatController();

        ArrayList<User> lesUsers = new ArrayList<>();
        List<Station> stations;
        try {
            stations = stationController.allStation();
            lesUsers = userController.allUsers();
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

        try {
            tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Statistiques
        try {
            lblTotalStation.setText(String.valueOf(statController.countStation()));
            lblTotalVelos.setText(String.valueOf(statController.countBikes()));
            lblTotalVelosMecanique.setText(String.valueOf(statController.countMechanicalBikes()));
            lblTotalVelosElectrique.setText(String.valueOf(statController.countElectricBikes()));
            lblTotalReservation.setText(String.valueOf(statController.countReservations()));
            lblTotalFavorie.setText(String.valueOf(statController.countFavoriteStation()));


            ArrayList<Station> stationsList = stationController.allStation();
            tcStation.setCellValueFactory(new PropertyValueFactory<>("name"));

            tcEmplacement.setCellValueFactory(cellData -> {
                Station station = cellData.getValue();
                int totalBornes = station.getCapacity();
                return new SimpleIntegerProperty(totalBornes).asObject();
            });
            tcNombreVelos.setCellValueFactory(cellData -> {
                Station station = cellData.getValue();
                int totalBikes = station.getNum_mechanical_bikes_available() + station.getNum_electric_bikes_available();
                return new SimpleIntegerProperty(totalBikes).asObject();
            });
            tcTauxRemplissage.setCellValueFactory(cellData -> {
                Station station = cellData.getValue();
                int capacity = station.getCapacity();
                if (capacity != 0) {
                    int utilisation = (int) Math.round(((double) (station.getNum_mechanical_bikes_available() + station.getNum_electric_bikes_available()) / capacity) * 100);
                    return new SimpleIntegerProperty(utilisation).asObject();
                } else {
                    return new SimpleIntegerProperty(0).asObject();
                }
            });

            // Affichage du pourcentage dans la colonne
            tcTauxRemplissage.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item + "%");
                    }
                }
            });

            tvStations.setItems(FXCollections.observableArrayList(stationsList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            try {
                tvUtilisateurs.setItems(FXCollections.observableArrayList(userController.allUsers()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appGestionDesUtilisateurs.toFront();
        } else if (actionEvent.getSource() == menuGestionParc) {
            appGestionParc.toFront();
        } else if (actionEvent.getSource() == menuTableauDeBord) {
            appTableauDeBord1.toFront();
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

    @FXML
    public void btnChangementDeMotDePasseClicked(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        User user = (User) tvUtilisateurs.getSelectionModel().getSelectedItem();
        if (userController.ChangePassword(user))
        {
            userController.unForceUser(user.getEmail());
        }
        else
        {
            userController.forceUser(user.getEmail());
        }
    }

    @FXML
    public void btnSuivantStatClicked(Event event) throws SQLException {
        if (appTableauDeBord1.isVisible()) {
            appTableauDeBord1.setVisible(false);
            appTableauDeBord2.setVisible(true);

            //Load pie chart data with animation
            loadPieChartData(pcReservationStationDepart, statController.getReservationsByStationDepart());
            loadPieChartData(pcReservationStationArrive, statController.getReservationsByStationArrive());
            appTableauDeBord2.toFront();

        } else if (appTableauDeBord2.isVisible()) {
            appTableauDeBord2.setVisible(false);
            appTableauDeBord3.setVisible(true);
            appTableauDeBord3.toFront();
        }
    }

    @FXML
    public void btnRetourStatClicked(Event event) throws SQLException {
        if (appTableauDeBord2.isVisible()) {
            appTableauDeBord2.setVisible(false);
            appTableauDeBord1.setVisible(true);
            appTableauDeBord1.toFront();
        } else if (appTableauDeBord3.isVisible()) {
            appTableauDeBord3.setVisible(false);
            appTableauDeBord2.setVisible(true);

            //Load pie chart data with animation
            loadPieChartData(pcReservationStationDepart, statController.getReservationsByStationDepart());
            loadPieChartData(pcReservationStationArrive, statController.getReservationsByStationArrive());
            appTableauDeBord2.toFront();
        }
    }

    private void loadPieChartData(PieChart pieChart, Map<String, Integer> reservationData) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : reservationData.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChartData.add(data);
        }

        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(true);

        for (PieChart.Data data : pieChartData) {
            Tooltip tooltip = new Tooltip(data.getName() + ": " + (int) data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);
        }
    }


}