package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class accueilController {
    @FXML
    private Label LabelWELCOME;

    @FXML
    private Button btnhome1;

    @FXML
    private Button btnhome11;

    @FXML
    private Button btnhome111;

    @FXML
    private Button btnhome1111;

    @FXML
    private AnchorPane crudContainer;

    @FXML
    private ImageView image0;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image5;

    @FXML
    private Line ligne;

    @FXML
    private AnchorPane side;

    @FXML
    private void showCrudView(ActionEvent event) {
        try {
            // Charger le fichier FXML du CRUD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/coach.fxml"));
            Parent coachCrudView = loader.load();

//            CoachControlleur coachController = loader.getController();

            // Passer les données ou initialiser le contrôleur si nécessaire
            // coachController.initData(...); // Par exemple

            // Remplacer le contenu actuel par la vue du CRUD de Coach
            crudContainer.getChildren().setAll(coachCrudView);

            // Obtenir la fenêtre principale
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Mettre à jour la scène avec le nouveau contenu
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
