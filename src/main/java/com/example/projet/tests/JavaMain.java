package com.example.projet.tests;

import com.example.projet.Models.Commentaire;
import com.example.projet.Models.Publication;
import com.example.projet.Services.ServiceCommentaire;
import com.example.projet.Services.ServicePublication;
import com.example.projet.utils.DataBase;

import java.sql.Connection;
import java.util.ArrayList;

public class JavaMain {
    public static void main(String[] args) {
        Connection cnx = DataBase.getInstance().getCnx();
        // Créez une instance de ServicePublication
        ServicePublication servicePublication = new ServicePublication();

        // Appelez la méthode afficher() pour récupérer les publications
        ArrayList<Publication> publications = servicePublication.afficher();

        // Parcourez les publications récupérées et affichez-les
        for (Publication publication : publications) {
            System.out.println("ID: " + publication.getIdPub());
            System.out.println("Contenu: " + publication.getContent());
            System.out.println("Date de publication: " + publication.getDatePub());
            System.out.println("------------------------");
        }
//      //  Publication pub =new Publication("alooo");
//        Publication pub =new Publication(2,"ya rabi");
//        ServicePublication servicePublication =new ServicePublication();
//        servicePublication.supprimer(pub);
//       // servicePublication.ajouter(pub);
//        ServiceCommentaire serviceCommentaire=new ServiceCommentaire();
//        Commentaire c =new Commentaire(4,"vrom vrom");
//        Commentaire c2 =new Commentaire("aaaaa",pub);
//        serviceCommentaire.afficher();

    }
}
