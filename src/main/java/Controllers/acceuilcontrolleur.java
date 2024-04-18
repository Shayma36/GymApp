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

public class acceuilcontrolleur {
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
    void showcours(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Cours.fxml"));
            Parent showcours = loader.load();

            CoursControlleur CoursController = loader.getController();


            crudContainer.getChildren().setAll(showcours);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void showpub(ActionEvent event) {

    }

    @FXML
    void showuser(ActionEvent event) {

    }


    @FXML
    private void showCrudView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Coach.fxml"));
            Parent coachCrudView = loader.load();

            CoachControlleur coachController = loader.getController();


            crudContainer.getChildren().setAll(coachCrudView);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
