package Controllers;

import com.example.projet.Models.Cours;
import com.example.projet.utils.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursControlleur  implements Initializable {
    Connection cnx = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    int id = 0;

    @FXML
    private TextField tnom;


    @FXML
    private TextField tHeure;

    @FXML
    private TextField tCoach;

    @FXML
    private TextField tPlace;
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
    private Button btnmodifier;
    @FXML
    private Button btclear;

    @FXML
    private Button btnsupprimer;

    @FXML
    private Button btnsave;
    @FXML




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
    void createcours(ActionEvent event) {
        String insert="insert into Cours (nom, heure, coach,Places) values( ?,?,?,?)";
        cnx = DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(insert);
            st.setString(1,tnom.getText());
            st.setString(2,tHeure.getText());
            st.setString(3,tCoach.getText());
            st.setString(4,tPlace.getText());
            st.executeUpdate();
            showCours();
            clear();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Cours cours =tabe.getSelectionModel().getSelectedItem();
        if (cours != null){
            id=cours.getId();
            tnom.setText(cours.getNom());
            tHeure.setText(cours.getHeure());
            tCoach.setText(cours.getCoach());
            tPlace.setText(String.valueOf(cours.getPlaces()));

            btnsave.setDisable(true);
        }
        else {
            tnom.setText("");
            tHeure.setText("");
            tCoach.setText("");
            tPlace.setText("");
            btnsave.setDisable(true);
        }}

    void clear(){
        tnom.setText(null);
        tHeure.setText(null);
        tCoach.setText(null);
        tPlace.setText(null);
        btnsave.setDisable(false);

    }
    @FXML
    void supprimercours(ActionEvent event) {
        if (id != 0) {
            String delete = "DELETE FROM Cours WHERE id=?";
            cnx = DataBase.getInstance().getCnx();
            try {
                st = cnx.prepareStatement(delete);
                st.setInt(1, id);
                st.executeUpdate();
                showCours();
                clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Aucun cours sélectionné pour la suppression.");
        }
    }



    @FXML
    void modifiercours(ActionEvent event) {
        String update="update Cours set nom =?, heure=?, coach=?, Places=? where id=?";
        cnx=DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(update);
            st.setString(1,tnom.getText());
            st.setString(2,tHeure.getText());
            st.setString(3,tCoach.getText());
            st.setString(4,tPlace.getText());
            st.setInt(5, id);
            st.executeUpdate();
            showCours();
            clear();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }





    }
    @FXML
    void rechercherCours(KeyEvent event) {
        String searchTerm = filter.getText();
        ObservableList<Cours> filteredList = getCours().filtered(cours ->
                cours.getNom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        cours.getHeure().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        cours.getCoach().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        String.valueOf(cours.getPlaces()).toLowerCase().contains(searchTerm.toLowerCase())
        );
        tabe.setItems(filteredList);
    }

    public void btnclear(ActionEvent actionEvent) {
        clear();
    }


}
