package com.example.projet.tests;

import com.example.projet.Models.Commentaire;
import com.example.projet.Models.Publication;
import com.example.projet.Services.ServiceCommentaire;
import com.example.projet.Services.ServicePublication;
import com.example.projet.utils.DataBase;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.util.ArrayList;

public class JavaMain {
    public static void main(String[] args) {
        Connection cnx = DataBase.getInstance().getCnx();

        // Créez une instance de ServiceCommentaire
        ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

        // Créez une instance de Commentaire
        Commentaire commentaire = new Commentaire();

        // Initialisez les données du commentaire
        commentaire.setDescription("shaymaaa ");

        // Pour tester, vous devez également avoir une instance de Publication.
        // Vous pouvez le créer de la même manière.
        Publication publication = new Publication();
        publication.setIdPub(18); // Remplacez 1 par l'ID réel de la publication à laquelle ce commentaire est associé

        // Associez la publication au commentaire
        commentaire.setPub(publication);

        // Appelez la méthode ajouter du service de commentaire
        serviceCommentaire.ajouter(commentaire);
    }

}
