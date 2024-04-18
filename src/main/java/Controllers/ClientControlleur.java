package Controllers;

import com.example.projet.utils.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
//import org.controlsfx.control.Rating;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientControlleur implements Initializable {

    @FXML
    private ImageView imgc1;
    @FXML
    private ImageView imgc11;

    @FXML
    private AnchorPane side;

    @FXML
    private TextField tc1;

    @FXML
    private TextField tc111;

    @FXML
    private TextField tc1111;
    @FXML
    private TextField tc2;

    @FXML
    private TextField tc22;

    @FXML
    private TextField tc2222;
    @FXML
    private TextField tc3;

    @FXML
    private TextField tc33;

    @FXML
    private TextField tc3333;

    @FXML
    private TextField tc4;

    @FXML
    private TextField tc44;

    @FXML
    private TextField tc4444;
    @FXML
    private Rating rating;
    private Connection con = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Appel pour afficher les détails du coach avec l'ID 8 dans tc1, tc11 et tc1111
        afficherNomPrenomCoach(21, tc1, tc111, tc1111);

        // Appel pour afficher les détails du coach avec l'ID 10 dans tc2, tc22 et tc2222
        afficherNomPrenomCoach(22, tc2, tc22, tc2222);
        afficherNomPrenomCoach(23, tc3, tc33, tc3333);
        afficherNomPrenomCoach(24, tc4, tc44, tc4444);

    }

    private void afficherNomPrenomCoach(int coachId, TextField nomField, TextField prenomField, TextField numField) {
        String query = "SELECT nom, prenom, email, num FROM Coach WHERE id = ?";
        con = DataBase.getInstance().getCnx();
        try {
            st = con.prepareStatement(query);
            st.setInt(1, coachId);
            rs = st.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                int num = rs.getInt("num");

                // Affichage des détails du coach dans les TextField spécifiés
                nomField.setText(nom + " " + prenom);
                prenomField.setText(email);
                numField.setText(String.valueOf(num));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
