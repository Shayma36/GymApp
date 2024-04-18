package Controllers;

import com.example.projet.Models.Cours;
import com.example.projet.utils.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class clientController implements Initializable {
    Connection cnx = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    int id = 0;
    @FXML
    private TableColumn<Cours, Integer> colId;

    @FXML
    private TableColumn<Cours, String> colNom;

    @FXML
    private TableColumn<Cours, String> colHeure;

    @FXML
    private TableColumn<Cours, String> colCoach;

    @FXML
    private TableColumn<Cours, Integer> colnbplace;
    @FXML
    private TableView<Cours> tabe;

    @FXML
    private TextField filter;




    @FXML
    void rechercherCours(KeyEvent event) {

    }
    @FXML
    private Button reserver;

    @FXML
    void getData(MouseEvent event) {

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCours();
    }

    public ObservableList<Cours> getCours() {
        ObservableList<Cours> Cours = FXCollections.observableArrayList();
        String query = "select * from Cours";
        cnx = DataBase.getInstance().getCnx();
        try {
            st = cnx.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {

                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setNom(rs.getString("Nom"));
                cours.setHeure(rs.getString("Heure"));
                cours.setCoach(rs.getString("Coach"));
                cours.setPlaces(rs.getInt("Places"));
                Cours.add(cours);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return Cours;

    }
    public void showCours(){
        ObservableList<Cours> list=getCours();
        tabe.setItems(list);
        colId.setCellValueFactory(new PropertyValueFactory<Cours,Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Cours,String>("Nom"));
        colHeure.setCellValueFactory(new PropertyValueFactory<Cours,String>("Heure"));
        colCoach.setCellValueFactory(new PropertyValueFactory<Cours,String>("Coach"));
        colnbplace.setCellValueFactory(new PropertyValueFactory<Cours,Integer>("Places"));

    }

    @FXML
    void handleReservation(ActionEvent event) {
        Cours selectedCours = tabe.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            int currentPlaces = selectedCours.getPlaces();
            if (currentPlaces > 0) {
                openReservationInterface(selectedCours);
            } else {
                System.out.println("Le cours est complet.");
            }
        }
    }

    private void openReservationInterface(Cours cours) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/reserver.fxml"));
            Parent root = loader.load();

            ResrvationController reservationController = loader.getController();
            reservationController.initData(cours, this); // Passer le cours sélectionné et une référence à ce contrôleur

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCoursePlaces(int id, int newPlaces) {
        String updateQuery = "UPDATE Cours SET Places = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(updateQuery);
            ps.setInt(1, newPlaces);
            ps.setInt(2, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Le nombre de places a été mis à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
