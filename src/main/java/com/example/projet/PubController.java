package com.example.projet;

import com.example.projet.Models.Publication;

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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class PubController implements Initializable {

    Connection cnx =null;
    PreparedStatement pst =null;
    ResultSet rs =null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

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
    private TableView<Publication> table;
    int idPub=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          showPublications();
    }

    public ObservableList<Publication> getPublications(){
        ObservableList<Publication> publications= FXCollections.observableArrayList();
        String query="select * from Publication";
        cnx= DataBase.getInstance().getCnx();
        try{
            pst=cnx.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){

                Publication publication = new Publication();
                publication.setIdPub(rs.getInt("idPub"));
                publication.setContent(rs.getString("content"));
                publication.setDatePub(rs.getTimestamp("datePub"));
                publications.add(publication);


            }
        }catch(SQLException e){
            throw new RuntimeException(e);

        }
        return publications;

    }

    public void showPublications(){
        ObservableList<Publication> list=getPublications();
        table.getItems().clear();
        table.setItems(list);
        colIdPub.setCellValueFactory(new PropertyValueFactory<Publication,Integer>("idPub"));
        colContent.setCellValueFactory(new PropertyValueFactory<Publication,String>("content"));
        colDatePub.setCellValueFactory(new PropertyValueFactory<Publication,Timestamp>("datePub"));
    }

    @FXML
    void clearField(ActionEvent event) {

        clear();

    }

    @FXML
    void createPublication(ActionEvent event) {
        String insert="insert into publication (content, datePub) values( ?,NOW())";
        cnx=DataBase.getInstance().getCnx();
        try{
            pst=cnx.prepareStatement(insert);
            pst.setString(1,tContent.getText());
//            pst.setString(2,tDatePub.getText());
            pst.executeUpdate();
            clear();
            showPublications();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Publication publication = table.getSelectionModel().getSelectedItem();
        if (publication != null) {
            idPub = publication.getIdPub();
            tContent.setText(publication.getContent());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(publication.getDatePub());
            tDatePub.setText(formattedDate);
            btnSave.setDisable(true);
        }}

    void clear(){
        tContent.setText(null);
        tDatePub.setText(null);
        btnSave.setDisable(false);

    }
    @FXML
    void deletePublication(ActionEvent event) {
        String delete="delete from publication where idPub=?";
        cnx=DataBase.getInstance().getCnx();
        try{
            pst=cnx.prepareStatement(delete);
            pst.setInt(1, idPub);
            pst.executeUpdate();
            clear();
            showPublications();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }


    }

    @FXML
    void updatePublication(ActionEvent event) {
        String update="update Publication set content =? where idPub=?";
        cnx=DataBase.getInstance().getCnx();
        try{
            pst=cnx.prepareStatement(update);
            pst.setString(1,tContent.getText());
            pst.setInt(2, idPub);
            pst.executeUpdate();
            clear();
            showPublications();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }


}

