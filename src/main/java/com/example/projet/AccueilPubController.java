package com.example.projet;

import com.example.projet.Models.Commentaire;
import com.example.projet.Models.Publication;
import com.example.projet.Services.ServiceCommentaire;
import com.example.projet.Services.ServicePublication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class AccueilPubController implements Initializable {

    private final ServicePublication publicationService = new ServicePublication();
    private final ServiceCommentaire commentaireService = new ServiceCommentaire();
    private  Publication selectedPublication;
    private Commentaire selectedComment;



    Connection cnx = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private Button btnAddComment;

    @FXML
    private Button btnAddPublication;

    @FXML
    private Button btnDeleteComment;

    @FXML
    private Button btnDeletePublication;

    @FXML
    private Button btnEditComment;

    @FXML
    private Button btnEditPublication;

    @FXML
    private Button btnimprimerPublication;
    @FXML
    private Button btnRefresh;

    @FXML
    private VBox commentBox;

    @FXML
    private VBox publicationBox;

    @FXML
    private RadioButton radioAsc;

    @FXML
    private RadioButton radioDesc;

    @FXML
    private TextArea txtComment;

    @FXML
    private TextArea txtContent;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextArea txtTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPublications();
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPublications(newValue);
        });

        radioAsc.setSelected(true);


        radioAsc.setOnAction(event -> loadPublications());
        radioDesc.setOnAction(event -> loadPublications());

        loadPublications();



    }
@FXML
private void loadPublications() {
    // Votre logique pour charger les publications depuis la base de données
    publicationBox.getChildren().clear(); // Clear existing publications
    List<Publication> publications = publicationService.afficher();

    // Déterminez l'ordre de tri en fonction de la sélection de l'utilisateur
    if (radioAsc.isSelected()) {
        // Tri par date ascendant
        publications.sort(Comparator.comparing(Publication::getDatePub));
    } else {
        // Tri par date descendant
        publications.sort(Comparator.comparing(Publication::getDatePub).reversed());
    }

    for (Publication publication : publications) {
        // Create a new TextArea to display the publication
        TextArea publicationTextArea = new TextArea();
        publicationTextArea.setEditable(false);
        publicationTextArea.setWrapText(true);
        publicationTextArea.setText("Titre: " + publication.getTitle() + "\n\n" +
                "Contenu: " + publication.getContent() + "\n\n" +
                "Date de publication: " + publication.getDatePub());

        // Add an event handler to display the details and comments of the selected publication
        publicationTextArea.setOnMouseClicked(event -> {
            selectedPublication = publication;
            displayPublicationDetails(publication);
            displayPublicationComments(publication);
        });

        // Add the publication TextArea to the VBox
        publicationBox.getChildren().add(publicationTextArea);
    }
}


    private void displayPublicationDetails(Publication publication) {
        // Afficher les détails de la publication sélectionnée dans les champs correspondants
        txtTitle.setText(publication.getTitle());
        txtContent.setText(publication.getContent());
    }

    private void displayPublicationComments(Publication publication) {
        // Charger les commentaires associés à la publication sélectionnée
        commentBox.getChildren().clear(); // Clear existing comments
        for (Commentaire comment : commentaireService.getCommentByPub(publication.getIdPub())) {
            TextArea commentTextArea = new TextArea();
            commentTextArea.setEditable(false);
            commentTextArea.setWrapText(true);
            commentTextArea.setText("Description: " + comment.getDescription() + "\n\n" +
                    "Date du commentaire: " + comment.getDateCom());

            // Ajouter un événement de clic pour mettre à jour le commentaire sélectionné
            commentTextArea.setOnMouseClicked(event -> {
                selectedComment = comment;
            });

            // Ajouter la TextArea au VBox
            commentBox.getChildren().add(commentTextArea);
        }
    }




    @FXML
    private void addComment() {
        if (selectedPublication != null && !txtComment.getText().isEmpty()) {
            // Créer un nouveau commentaire
            Commentaire newComment = new Commentaire();
            newComment.setDescription(txtComment.getText());
            newComment.setDateCom(new Timestamp(System.currentTimeMillis()));
            newComment.setPub(selectedPublication);

            // Ajouter le commentaire à la publication sélectionnée
            commentaireService.ajouter(newComment);

            // Actualiser l'affichage des commentaires
            displayPublicationComments(selectedPublication);

            // Effacer le champ de saisie du commentaire
            txtComment.clear();
            showAlert("Succès", "Le commentaire a été ajouté avec succès.");
        }else {
            // Afficher un message d'erreur si la publication n'est pas sélectionnée ou si le champ de commentaire est vide
            showAlert("Erreur", "Veuillez sélectionner une publication et saisir un commentaire.");
        }
    }

    @FXML
    private void addPublication() {
        String title = txtTitle.getText();
        String content = txtContent.getText();

        // Vérifiez que les champs de titre et de contenu ne sont pas vides
        if (!title.isEmpty() && !content.isEmpty()) {
            // Créez une nouvelle instance de Publication
            Publication newPublication = new Publication();
            newPublication.setTitle(title);
            newPublication.setContent(content);

            // Ajoutez la nouvelle publication en utilisant votre service de publication
            publicationService.ajouter(newPublication);

            // Effacez les champs de texte après l'ajout
            txtTitle.clear();
            txtContent.clear();

            // Actualisez l'affichage des publications pour inclure la nouvelle publication
            loadPublications();
        } else {
            // Affichez un message d'erreur si les champs de titre ou de contenu sont vides
            // Vous pouvez implémenter cette logique selon vos besoins
            System.err.println("Veuillez remplir tous les champs.");
        }
    }
    @FXML
    private void editComment() {
        if (selectedComment != null) {
            // Créer une boîte de dialogue pour modifier le commentaire sélectionné
            TextInputDialog dialog = new TextInputDialog(selectedComment.getDescription());
            dialog.setTitle("Modifier le commentaire");
            dialog.setHeaderText("Modifier le commentaire sélectionné");
            dialog.setContentText("Nouveau contenu du commentaire:");

            // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur
            Optional<String> result = dialog.showAndWait();

            // Si l'utilisateur appuie sur le bouton OK et fournit un nouveau contenu, mettre à jour le commentaire
            result.ifPresent(newContent -> {
                // Mettre à jour le contenu du commentaire
                commentaireService.updateDescription(selectedComment.getIdComment(), newContent);

                // Actualiser l'affichage des commentaires pour refléter les modifications
                displayPublicationComments(selectedPublication);
            });
        } else {
            // Afficher un message d'erreur si aucun commentaire n'est sélectionné
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur de sélection");
            error.setHeaderText(null);
            error.setContentText("Veuillez sélectionner un commentaire à modifier.");
            error.showAndWait();
        }
    }




    @FXML
    private void deleteComment() {
        if (selectedComment != null) {
            // Afficher une boîte de dialogue de confirmation pour confirmer la suppression
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText("Supprimer le commentaire sélectionné ?");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce commentaire ?");

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmation.showAndWait();

            // Si l'utilisateur confirme la suppression, supprimer le commentaire
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer le commentaire sélectionné en utilisant votre service de commentaire
                commentaireService.supprimer(selectedComment);

                // Actualiser l'affichage des commentaires pour exclure le commentaire supprimé
                displayPublicationComments(selectedPublication);

                // Remettre à null le commentaire sélectionné après la suppression
                selectedComment = null;
            }
        } else {
            // Afficher un message d'erreur si aucun commentaire n'est sélectionné
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur de sélection");
            error.setHeaderText(null);
            error.setContentText("Veuillez sélectionner un commentaire à supprimer.");
            error.showAndWait();
        }
    }


    // Méthode utilitaire pour obtenir le commentaire sélectionné
    private Commentaire getSelectedComment() {
        // Parcourir tous les enfants de commentBox
        for (Node node : commentBox.getChildren()) {
            // Vérifier si le nœud est une TextArea
            if (node instanceof TextArea) {
                TextArea textArea = (TextArea) node;
                // Vérifier si la TextArea a une valeur non nulle dans ses données utilisateur
                if (textArea.getUserData() != null) {
                    // Retourner le commentaire associé à cette TextArea
                    return (Commentaire) textArea.getUserData();
                }
            }
        }
        // Si aucun commentaire n'est sélectionné, retourner null
        return null;
    }





    @FXML
    private void editPublication() {
        if (selectedPublication != null) {
            // Créer une boîte de dialogue pour modifier le titre et le contenu de la publication
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText("Modifier la publication sélectionnée");

            // Créer des champs de texte pour le titre et le contenu
            TextField titleField = new TextField(selectedPublication.getTitle());
            TextField contentField = new TextField(selectedPublication.getContent());

            // Créer une grille pour disposer les champs de texte
            GridPane grid = new GridPane();
            grid.add(titleField, 0, 0);
            grid.add(contentField, 0, 1);
            dialog.getDialogPane().setContent(grid);

            // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur
            Optional<ButtonType> result = dialog.showAndWait();

            // Si l'utilisateur appuie sur le bouton OK, mettre à jour la publication
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Récupérer les nouvelles valeurs de titre et de contenu
                String newTitle = titleField.getText();
                String newContent = contentField.getText();

                // Mettre à jour le titre et le contenu de la publication
                selectedPublication.setTitle(newTitle);
                selectedPublication.setContent(newContent);

                // Appeler la méthode de service pour modifier la publication dans la base de données
                publicationService.modifier(selectedPublication);

                // Afficher un message de confirmation à l'utilisateur
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Modification réussie");
                confirmation.setHeaderText(null);
                confirmation.setContentText("La publication a été modifiée avec succès !");
                confirmation.showAndWait();

                // Actualiser l'affichage des publications pour refléter les modifications
                loadPublications();
            }
        } else {
            // Afficher un message d'erreur si aucune publication n'est sélectionnée
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur de sélection");
            error.setHeaderText(null);
            error.setContentText("Veuillez sélectionner une publication à modifier.");
            error.showAndWait();
        }
    }



    @FXML
    private void deletePublication() {
        if (selectedPublication != null) {
            // Supprimez la publication sélectionnée en utilisant votre service de publication
            publicationService.supprimer(selectedPublication);

            // Actualisez l'affichage des publications pour exclure la publication supprimée
            loadPublications();
        }
    }
    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode utilitaire pour afficher une boîte de dialogue de confirmation
    private boolean showConfirmationDialog(String title, String message) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setHeaderText(null);
        confirmation.setContentText(message);

        Optional<ButtonType> result = confirmation.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void filterPublications(String searchText) {
        ObservableList<Publication> filteredPublications = FXCollections.observableArrayList();
        // Parcourez toutes les publications
        for (Publication publication : publicationService.afficher()) {
            // Vérifiez si le titre ou le contenu de la publication contient le texte de recherche
            if (publication.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                    publication.getContent().toLowerCase().contains(searchText.toLowerCase())) {
                // Si c'est le cas, ajoutez cette publication à la liste filtrée
                filteredPublications.add(publication);
            }
        }
        // Effacez la boîte de publication actuelle et ajoutez les publications filtrées
        publicationBox.getChildren().clear();
        for (Publication publication : filteredPublications) {
            TextArea publicationTextArea = createPublicationTextArea(publication);
            publicationBox.getChildren().add(publicationTextArea);
        }
    }

    private TextArea createPublicationTextArea(Publication publication) {
        TextArea publicationTextArea = new TextArea();
        publicationTextArea.setEditable(false);
        publicationTextArea.setWrapText(true);
        publicationTextArea.setText("Titre: " + publication.getTitle() + "\n\n" +
                "Contenu: " + publication.getContent() + "\n\n" +
                "Date de publication: " + publication.getDatePub());
        // Ajoutez un gestionnaire d'événements pour afficher les détails de la publication lorsqu'elle est cliquée
        publicationTextArea.setOnMouseClicked(event -> {
            selectedPublication = publication;
            displayPublicationDetails(publication);
            displayPublicationComments(publication);
        });
        return publicationTextArea;
    }
    @FXML
    private void imprimerPublication() {
        if (selectedPublication != null) {
            // Créez une instance de PrinterJob
            PrinterJob printerJob = PrinterJob.createPrinterJob();

            if (printerJob != null && printerJob.showPrintDialog(null)) {
                // Imprimez le contenu de la publication sélectionnée
                boolean success = printerJob.printPage(getSelectedPublicationNode());

                if (success) {
                    printerJob.endJob(); // Terminez le travail d'impression
                } else {
                    System.out.println("Erreur lors de l'impression");
                }
            }
        } else {
            System.out.println("Aucune publication sélectionnée pour l'impression");
        }
    }

    // Méthode utilitaire pour obtenir le nœud représentant la publication sélectionnée
    private Node getSelectedPublicationNode() {
        // Créez un TextArea pour afficher la publication sélectionnée
        TextArea publicationTextArea = new TextArea();
        publicationTextArea.setEditable(false);
        publicationTextArea.setWrapText(true);
        publicationTextArea.setText("Titre: " + selectedPublication.getTitle() + "\n\n" +
                "Contenu: " + selectedPublication.getContent() + "\n\n" +
                "Date de publication: " + selectedPublication.getDatePub());

        // Retournez le nœud TextArea
        return publicationTextArea;
    }
    @FXML
    private void refresh() {
        loadPublications();
        txtTitle.clear();
        txtContent.clear();
        txtComment.clear();
        commentBox.getChildren().clear();
    }


}
