package sio.btssiog22025velikojava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sio.btssiog22025velikojava.controllers.UserController;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {

    DataSourceProvider cnx;
    private UserController userController;

    @FXML
    private Label lblTitre;
    @FXML
    private Label lblMotDePasse;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblEmail;
    @FXML
    private Button btnValider;
    @FXML
    private PasswordField txtMotDePasse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {







      /*  cnx = new DataSourceProvider();
        userController = new UserController();

        try {
            System.out.print(userController.allUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/





       try {
            cnx = new DataSourceProvider();
            userController = new UserController();
        }
        catch (ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Impossible d'initialiser la connexion à la base de données : ");
            alert.showAndWait();
        }



    /*    try {
            cnx = new DataSourceProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    @FXML
    public void btnValiderClicked(javafx.scene.input.MouseEvent mouseEvent) {
        String email = txtEmail.getText();
        String motDePasse = txtMotDePasse.getText();

        if (email.isEmpty() || motDePasse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Les données saisies sont invalides");
            alert.showAndWait();
            //   return;
        } else {
            try {
                if (userController.checkCredentials(txtEmail.getText(), txtMotDePasse.getText())) {
                    // Charge la nouvelle vue si les identifiants sont corrects
                    FXMLLoader fxmlLoader = new FXMLLoader(VelikoApplication.class.getResource("veliko-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) btnValider.getScene().getWindow(); // Récupère la fenêtre actuelle
                    stage.setScene(scene);
                    stage.show();
                    //((Stage) btnValider.getScene().getWindow()).close();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Mot de passe ou Email incorrect");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}