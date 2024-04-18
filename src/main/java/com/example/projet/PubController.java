package com.example.projet;

import com.example.projet.Models.Publication;

import com.example.projet.Services.ServicePublication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class PubController implements Initializable {

    private final ServicePublication publicationService = new ServicePublication();


    Connection cnx = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button gererCommentaires;

    @FXML
    private TextField tTitle;

    @FXML
    private TextField tContent;

    @FXML
    private TextField tDatePub;
    @FXML
    private TableColumn<Publication, String> colContent;

    @FXML
    private TableColumn<Publication, Timestamp> colDatePub;

    @FXML
    private TableColumn<Publication, Integer> colIdPub;

    @FXML
    private TableColumn<Publication, String> colTitle;

    @FXML
    private TableView<Publication> table;
    int idPub = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPublications();
    }


    public void showPublications() {
        ObservableList<Publication> list = publicationService.afficher();
        table.getItems().clear();
        table.setItems(list);
        colIdPub.setCellValueFactory(cellData -> cellData.getValue().idPubProperty().asObject());
        colTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        colContent.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        colDatePub.setCellValueFactory(cellData -> cellData.getValue().datePubProperty());
    }

    @FXML
    void clearField(ActionEvent event) {

        clear();

    }

    @FXML
    void createPublication(ActionEvent event) {
        // Vérifier si les champs obligatoires sont vides
        if (tTitle.getText().isEmpty() || tContent.getText().isEmpty() ) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
        } else {
            // Créer la publication
            Publication publication = new Publication();
            publication.setTitle(tTitle.getText());
            publication.setContent(tContent.getText());
            publicationService.ajouter(publication);
            // Afficher un message de succès à l'utilisateur
            showSuccessAlert("Succès", "La publication a été ajoutée avec succès.");
            // Nettoyer les champs et actualiser la liste des publications
            clear();
            showPublications();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void getData(MouseEvent event) {
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication != null) {
            idPub = publication.getIdPub();
            tContent.setText(publication.getContent());
            tTitle.setText(publication.getTitle());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String formattedDate = dateFormat.format(publication.getDatePub());
//            tDatePub.setText(formattedDate);
            btnSave.setDisable(true);
        }
    }

    void clear() {
        tContent.setText(null);
        tTitle.setText(null);
        btnSave.setDisable(false);

    }

    @FXML
    void deletePublication(ActionEvent event) {
        // Vérifier si une publication est sélectionnée
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication == null) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "Veuillez sélectionner une publication à supprimer.");
        } else {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette publication ?");

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur clique sur "OK", supprimer la publication
                publicationService.supprimer(publication);
                // Afficher un message de succès à l'utilisateur
                showSuccessAlert("Succès", "La publication a été supprimée avec succès.");
                // Nettoyer les champs et actualiser la liste des publications
                clear();
                showPublications();
            }
        }
    }



    @FXML
    void updatePublication(ActionEvent event) {
        // Vérifier si une publication est sélectionnée
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication == null) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "Veuillez sélectionner une publication à mettre à jour.");
        } else {
            // Vérifier si les champs obligatoires sont vides
            if (tTitle.getText().isEmpty() || tContent.getText().isEmpty()) {
                // Afficher un message d'erreur à l'utilisateur
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            } else {
                // Mettre à jour la publication
                publication.setContent(tContent.getText());
                publication.setTitle(tTitle.getText());
                publicationService.modifier(publication);
                // Afficher un message de succès à l'utilisateur
                showSuccessAlert("Succès", "La publication a été mise à jour avec succès.");
                // Nettoyer les champs et actualiser la liste des publications
                clear();
                showPublications();
            }
        }
    }
    @FXML
    private void gererCommentaires(ActionEvent event) {
        try {
            // Charger le fichier FXML Commentaires.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Commentaires.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de Commentaires.fxml
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Afficher la nouvelle scène dans la fenêtre principale
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement du fichier FXML
        }
    }

}

