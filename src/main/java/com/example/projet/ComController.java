package com.example.projet;

import com.example.projet.Models.Commentaire;
import com.example.projet.Models.Publication;
import com.example.projet.Services.ServiceCommentaire;
import com.example.projet.Services.ServicePublication;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ComController implements Initializable {

    private final ServiceCommentaire commentaireService = new ServiceCommentaire();
    private final ServicePublication publicationService = new ServicePublication();


    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tDescription;

    @FXML
    private TextField tIdPub;

    @FXML
    private TextField tDateCom;

    @FXML
    private TableColumn<Commentaire, Integer> colIdComment;

    @FXML
    private TableColumn<Commentaire, String> colDescription;

    @FXML
    private TableColumn<Commentaire, Timestamp> colDateCom;

    @FXML
    private TableColumn<Commentaire, Integer> colIdPub;

    @FXML
    private TableView<Commentaire> table;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCommentaires();
    }

    public void showCommentaires() {
        ObservableList<Commentaire> list = commentaireService.afficher();
        table.getItems().clear();
        table.setItems(list);
        colIdComment.setCellValueFactory(cellData -> cellData.getValue().idCommentProperty().asObject());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colDateCom.setCellValueFactory(cellData -> cellData.getValue().dateComProperty());
        colIdPub.setCellValueFactory(cellData -> cellData.getValue().getPub().idPubProperty().asObject());
    }




    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void createCommentaire(ActionEvent event) {
        // Créez un nouveau commentaire
        Commentaire commentaire = new Commentaire();

        // Définissez la description du commentaire à partir du champ de texte
        commentaire.setDescription(tDescription.getText());

        // Définissez la date du commentaire à la date actuelle
        commentaire.setDateCom(new java.sql.Timestamp(System.currentTimeMillis()));

        // Récupérez l'ID de la publication à partir de la source appropriée
        int publicationId = getPublicationIdFromSource(); // Vous devez implémenter cette méthode pour obtenir l'ID de la publication

        // Supposons que vous ayez une méthode dans votre service de publication pour récupérer une publication par ID
        Publication publication = publicationService.getPublicationById(publicationId);

        // Assurez-vous que la publication est associée au commentaire
        commentaire.setPub(publication);

        // Ajoutez le commentaire en appelant la méthode ajouter de votre service de commentaire
        commentaireService.ajouter(commentaire);

        // Effacez les champs de texte après l'ajout
        clear();

        // Mettez à jour l'affichage des commentaires dans la TableView
        showCommentaires();
    }
    private int getPublicationIdFromSource() {
        String publicationIdText = tIdPub.getText();
        try {
            return Integer.parseInt(publicationIdText);
        } catch (NumberFormatException e) {
            // Gérez le cas où l'ID de la publication entré n'est pas un nombre valide
            return -1; // Ou toute autre valeur par défaut appropriée
        }
    }


    @FXML
    void getData(MouseEvent event) {
        Commentaire commentaire = table.getSelectionModel().getSelectedItem();
        if (commentaire != null) {
            tDescription.setText(commentaire.getDescription());
            if (commentaire.getPub() != null) {
                tIdPub.setText(String.valueOf(commentaire.getPub().getIdPub()));
            } else {
                tIdPub.setText(""); // Si la publication est null, définissez le champ à vide
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(commentaire.getDateCom());
            tDateCom.setText(formattedDate);
            btnSave.setDisable(true);
        }
    }



    @FXML
    void deleteCommentaire(ActionEvent event) {
        Commentaire commentaire = table.getSelectionModel().getSelectedItem();
        if (commentaire != null) {
            commentaireService.supprimer(commentaire);
            clear();
            showCommentaires();
        }
    }

    @FXML
    void updateCommentaire(ActionEvent event) {
        Commentaire commentaire = table.getSelectionModel().getSelectedItem();
        if (commentaire != null) {
            commentaire.setDescription(tDescription.getText());
            // Récupérez la nouvelle valeur de idPub à partir du champ de texte
            String newIdPubText = tIdPub.getText();
            if (!newIdPubText.isEmpty()) {
                int newIdPub = Integer.parseInt(newIdPubText);
                commentaire.getPub().setIdPub(newIdPub);
            } else {
                // Gérez le cas où le champ de texte est vide
                // Vous pouvez afficher un message d'erreur à l'utilisateur ou prendre une autre action appropriée
                System.err.println("Le champ IdPub est vide.");
                return;
            }
            commentaireService.modifier(commentaire);
            clear();
            showCommentaires();
        }
    }


    void clear() {
        tIdPub.setText(null);
        tDescription.setText(null);
        tDateCom.setText(null);
        btnSave.setDisable(false);
    }
}
