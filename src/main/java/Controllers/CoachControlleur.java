package Controllers;

import com.example.projet.Models.Coach;
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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoachControlleur implements Initializable {
    Connection cnx = null;
    PreparedStatement st =null;
    ResultSet rs =null;
    int id=0;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tNom;

    @FXML
    private TextField tNum;

    @FXML
    private TextField tPrenom;

    @FXML
    private Button btnadd;

    @FXML
    private Button btnclear;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;


    @FXML
    private TableColumn<Coach, String> colEmail;
    @FXML
    private TableColumn<Coach, String> colNom;

    @FXML
    private TableColumn<Coach, Integer> colNum;

    @FXML
    private TableColumn<Coach, String> colPrenom;

    @FXML
    private TableColumn<Coach, Integer> colid;
    @FXML
    private TableView<Coach> table;
    @FXML
    private AnchorPane side;
    @FXML
    private TextField trecherche;

    public CoachControlleur() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCoachs();
    }
    public ObservableList<Coach> getCoachs(){
        ObservableList<Coach> Coach= FXCollections.observableArrayList();
        String query="select * from Coach";
        cnx= DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(query);
            rs=st.executeQuery();
            while(rs.next()){

                Coach coach = new Coach();
                coach.setId(rs.getInt("id"));
                coach.setNom(rs.getString("Nom"));
                coach.setPrenom(rs.getString("Prenom"));
                coach.setEmail(rs.getString("Email"));
                coach.setNum(rs.getInt("Num"));
                Coach.add(coach);


            }
        }catch(SQLException e){
            throw new RuntimeException(e);

        }
        return Coach;

    }
    public void showCoachs(){
        ObservableList<Coach> list=getCoachs();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Coach,Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Coach,String>("Nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Coach,String>("Prenom"));
        colNum.setCellValueFactory(new PropertyValueFactory<Coach,Integer>("Num"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Coach,String>("Email"));

    }
    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void createCoach(ActionEvent event) {
        String insert="insert into Coach (nom, prenom, num,email) values( ?,?,?,?)";
        cnx=DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(insert);
            st.setString(1,tNom.getText());
            st.setString(2,tPrenom.getText());
            st.setString(3,tNum.getText());
            st.setString(4,tEmail.getText());
            st.executeUpdate();
            clear();
            showCoachs();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Coach coach = table.getSelectionModel().getSelectedItem();
        if (coach != null) {
            id = coach.getId();
            tNom.setText(coach.getNom());
            tPrenom.setText(coach.getPrenom());
            tNum.setText(String.valueOf(coach.getNum()));
            tEmail.setText(coach.getEmail());
            btnadd.setDisable(true);
        } else {
            // Gérer le cas où aucun élément n'est sélectionné dans la table
            // Vous pouvez par exemple effacer les champs de texte ou désactiver les boutons
            tNom.setText("");
            tPrenom.setText("");
            tNum.setText("");
            tEmail.setText("");
            btnadd.setDisable(true);
        }
    }

    void clear(){
        tNom.setText(null);
        tPrenom.setText(null);
        tEmail.setText(null);
        tNum.setText(null);
        btnadd.setDisable(false);

    }

    @FXML
    void deleteCoach(ActionEvent event) {
        String delete="delete from coach where id=?";
        cnx=DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showCoachs();
            clear();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }


    }

    @FXML
    void updateCoach(ActionEvent event) {
        String update="update Coach set nom =?, prenom=?, num=?, email=? where id=?";
        cnx=DataBase.getInstance().getCnx();
        try{
            st=cnx.prepareStatement(update);
            st.setString(1,tNom.getText());
            st.setString(2,tPrenom.getText());
            st.setString(3,tNum.getText());
            st.setString(4,tEmail.getText());
            st.setInt(5, id);
            st.executeUpdate();
            showCoachs();
            clear();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
    @FXML
    void rechercherCoach(KeyEvent event) {
        String searchTerm = trecherche.getText();
        ObservableList<Coach> filteredList = getCoachs().filtered(Coach ->
                Coach.getNom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        Coach.getPrenom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        Coach.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        String.valueOf(Coach.getNum()).toLowerCase().contains(searchTerm.toLowerCase()) ||
                        String.valueOf(Coach.getId()).toLowerCase().contains(searchTerm.toLowerCase())
        );
        table.setItems(filteredList);
    }
}
