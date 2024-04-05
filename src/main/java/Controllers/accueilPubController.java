package Controllers;

import com.example.projet.Models.Commentaire;
import com.example.projet.Models.Publication;
import com.example.projet.Services.ServiceCommentaire;
import com.example.projet.Services.ServicePublication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class accueilPubController {

    @FXML
    private TextArea publicationTextArea;

    @FXML
    private Button publicationButton;

    @FXML
    private ListView<String> publicationListView;

    @FXML
    private TextField commentTextField;

    @FXML
    private Button commentButton;

    @FXML
    private ListView<String> commentListView;

    // Méthode appelée lors du clic sur le bouton de publication
    @FXML
    private void handlePublicationButton() {
        // Récupérer le contenu de la publication depuis la zone de texte
        String content = publicationTextArea.getText();

        // Ajouter la publication à votre logique métier
        // Par exemple :
         //ServicePublication.ajouter(new Publication(content));

        // Mettre à jour la liste des publications
        // Par exemple :
        // publicationListView.getItems().addAll(content);

        // Effacer le contenu de la zone de texte après la publication
        publicationTextArea.clear();
    }

    // Méthode appelée lors du clic sur le bouton de commentaire
    @FXML
    private void handleCommentButton() {
        // Récupérer le contenu du commentaire depuis la zone de texte
        String comment = commentTextField.getText();

        // Ajouter le commentaire à votre logique métier
        // Par exemple :
         //ServiceCommentaire.ajouter(new Commentaire(comment));

        // Mettre à jour la liste des commentaires
        // Par exemple :
        // commentListView.getItems().addAll(comment);

        // Effacer le contenu de la zone de texte après le commentaire
        commentTextField.clear();
    }
}
