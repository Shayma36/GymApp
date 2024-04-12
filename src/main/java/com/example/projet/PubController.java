package com.example.projet;

import com.example.projet.Models.Publication;

import com.example.projet.Services.ServicePublication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
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
        Publication publication = new Publication();
        publication.setTitle(tTitle.getText());
        publication.setContent(tContent.getText());
        publicationService.ajouter(publication);
        clear();
        showPublications();
    }

    @FXML
    void getData(MouseEvent event) {
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication != null) {
            idPub = publication.getIdPub();
            tContent.setText(publication.getContent());
            tTitle.setText(publication.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(publication.getDatePub());
            tDatePub.setText(formattedDate);
            btnSave.setDisable(true);
        }
    }

    void clear() {
        tContent.setText(null);
        tDatePub.setText(null);
        tTitle.setText(null);
        btnSave.setDisable(false);

    }

    @FXML
    void deletePublication(ActionEvent event) {
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication != null) {
            publicationService.supprimer(publication);
            clear();
            showPublications();
        }
    }


    @FXML
    void updatePublication(ActionEvent event) {
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication != null) {
            publication.setContent(tContent.getText());
            publication.setTitle(tTitle.getText());
            publicationService.modifier(publication);
            clear();
            showPublications();
        }
    }
}

