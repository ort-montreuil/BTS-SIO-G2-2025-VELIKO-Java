package sio.btssiog22025velikojava;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.btssiog22025velikojava.controllers.ReservationController;
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
    ReservationController reservationController;

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
    @FXML
    private ScatterChart<Number, Number> scBikeByMonth;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button btnSuivantStat4;
    @FXML
    private Button btnRetourStat4;
    @FXML
    private Label lblTotalUser;
    @FXML
    private Label lblUserMaxFavori;
    @FXML
    private Label lblUserMaxReservation;
    @FXML
    private AnchorPane appTableauDeBord4;
    @FXML
    private BarChart<String,Number> bcTopUserCity;
    @FXML
    private AnchorPane apSupport;
    @FXML
    private TableColumn tcAdminEmail;
    @FXML
    private TableColumn tcAdminName;
    @FXML
    private TableView tvAdmin;
    @FXML
    private Button btnValidationDemandeAdmin;
    @FXML
    private Button menuSupport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appGestionDesUtilisateurs.toFront();
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        //initialisation of controllers
        stationController = new StationController();
        userController = new UserController();
        statController = new StatController();
        reservationController = new ReservationController();

        //ArrayList<User> lesUsers = new ArrayList<>();
        List<Station> stations;
        try {
            stations = stationController.allStation();
            //lesUsers = userController.allUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Load the map
        WebEngine webEngine = wvVeliko.getEngine();
        URL mapUrl = getClass().getResource("/html/map.html");

        if (mapUrl != null) {
            //Load map
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

            loadTableStation();
            loadScatterChart();

            lblTotalUser.setText(String.valueOf(statController.countUsers()));
            lblUserMaxFavori.setText(statController.getUserwithMostFavorite());
            lblUserMaxReservation.setText(statController.getUserwithMostReservation());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
            appTableauDeBord1.setVisible(true);
            appTableauDeBord1.toFront();
        }
        else if (actionEvent.getSource() == menuSupport)
        {
            apSupport.setVisible(true);
            apSupport.toFront();
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

        }
        else if (appTableauDeBord2.isVisible()) {
            appTableauDeBord2.setVisible(false);
            appTableauDeBord3.setVisible(true);
            appTableauDeBord3.toFront();

            loadScatterChart();
        }
        else if (appTableauDeBord3.isVisible()) {
            appTableauDeBord3.setVisible(false);
            appTableauDeBord4.setVisible(true);
            appTableauDeBord4.toFront();
            loadTopUserCitiesData();
        }
        else if (appTableauDeBord4.isVisible()) {
            appTableauDeBord4.setVisible(false);
            appTableauDeBord1.setVisible(true);
            appTableauDeBord1.toFront();

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
        else if (appTableauDeBord4.isVisible()){
            appTableauDeBord4.setVisible(false);
            appTableauDeBord3.setVisible(true);
            appTableauDeBord3.toFront();
            loadScatterChart();
        }
        else if (appTableauDeBord1.isVisible()) {
            appTableauDeBord1.setVisible(false);
            appTableauDeBord4.setVisible(true);
            appTableauDeBord4.toFront();
            loadTopUserCitiesData();
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

    public void loadTableStation() throws SQLException {
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
    }

    public void loadScatterChart() {

        ArrayList<Map<String, Object>> reservationsByMonthAndType = reservationController.getReservationsByMonthAndType();

        XYChart.Series<Number, Number> classicBikeSeries = new XYChart.Series<>();
        classicBikeSeries.setName("Vélos Mecaniques");

        XYChart.Series<Number, Number> electricBikeSeries = new XYChart.Series<>();
        electricBikeSeries.setName("Vélos Électriques");

        for (Map<String, Object> reservation : reservationsByMonthAndType) {

            int month = (int) reservation.get("month");
            String bikeType = (String) reservation.get("bikeType");
            int reservations = (int) reservation.get("reservations");

            // Ajouter les données selon le type de vélo
            if (bikeType.equals("Mecanique")) {
                classicBikeSeries.getData().add(new XYChart.Data<>(month, reservations));
            } else if (bikeType.equals("Evelo")) {
                electricBikeSeries.getData().add(new XYChart.Data<>(month, reservations));
            }
        }
        scBikeByMonth.getData().clear();

        scBikeByMonth.getData().addAll(classicBikeSeries, electricBikeSeries);

        // Ajouter un tooltip aux points des vélos mécaniques
        for (XYChart.Data<Number, Number> data : classicBikeSeries.getData()) {
            Tooltip tooltip = new Tooltip("Mois: " + data.getXValue() + "\nRéservations: " + data.getYValue()+" Vélos Mecaniques");
            Tooltip.install(data.getNode(), tooltip);
        }

        // Ajouter un tooltip aux points des vélos électriques
        for (XYChart.Data<Number, Number> data : electricBikeSeries.getData()) {
            Tooltip tooltip = new Tooltip("Mois: " + data.getXValue() + "\nRéservations: " + data.getYValue()+" Vélos Electriques");
            Tooltip.install(data.getNode(), tooltip);
        }

    }
    private void loadTopUserCitiesData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Utilisateurs par ville");

        try {
            ArrayList<Map<String, Object>> topCities = statController.getTopUserCities();

            for (Map<String, Object> cityData : topCities) {
                String city = (String) cityData.get("city"); // Ville
                int users = (int) cityData.get("users"); // Nombre d'utilisateurs

                series.getData().add(new XYChart.Data<>(city, users));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bcTopUserCity.getData().clear(); // Nettoie les anciennes données
        bcTopUserCity.getData().add(series); // Ajoute les nouvelles données
    }

}