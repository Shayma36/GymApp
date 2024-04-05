package com.example.projet.utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static DataBase instance;
    private Connection cnx;
    private final String URL = "jdbc:mysql://localhost:3306/gym_box";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    private DataBase() {
        try {
            cnx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connecting !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

    public static class App extends Application {

        @Override
        public void start(Stage stage) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource("../../../../../resources/Fxml/accueilPub.fxml"));
            Scene scene=new Scene(parent);
            stage.setTitle("CRUD");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
        }
}


