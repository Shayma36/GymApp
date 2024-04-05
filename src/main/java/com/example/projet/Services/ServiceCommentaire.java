package com.example.projet.Services;

import com.example.projet.Models.Commentaire;
import com.example.projet.interfaces.IService;
import com.example.projet.utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceCommentaire implements IService<Commentaire> {
    Connection cnx = DataBase.getInstance().getCnx();

    @Override
    public void ajouter(Commentaire commentaire) {
        try {
            String requete = "INSERT INTO commentaire (description, dateCom, idPub) VALUES (?, NOW(), ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, commentaire.getDescription());
            pst.setInt(2, commentaire.getPub().getIdPub());
            pst.executeUpdate();
            System.out.println("Commentaire ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void supprimer(Commentaire commentaire) {
        try {
            String requete = "DELETE FROM commentaire WHERE idComment = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, commentaire.getIdComment());
            pst.executeUpdate();
            System.out.println("commentaire supprimée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire commentaire) {
        try {
            String requete = "UPDATE commentaire SET description=?,dateCom=now() WHERE idComment = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, commentaire.getDescription());
            pst.setInt(2, commentaire.getIdComment());
            pst.executeUpdate();
            System.out.println("commentaire modifiée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ArrayList<Commentaire> afficher() {
        ArrayList<Commentaire> commentaires = new ArrayList<>();
        try {
            String requete = "SELECT * FROM commentaire";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setIdComment(rs.getInt("idComment"));
                commentaire.setDescription(rs.getString("description"));
                commentaire.setDateCom(rs.getTimestamp("dateCom"));
            //    commentaire.setPub(rs.getInt("idPub"));
                commentaires.add(commentaire);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println(commentaires);
        return commentaires;

    }
    }

