package com.example.projet.Services;

import com.example.projet.interfaces.IService;
import com.example.projet.Models.Publication;
import com.example.projet.utils.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicePublication implements IService<Publication> {
    Connection cnx = DataBase.getInstance().getCnx();

    @Override
    public void ajouter(Publication publication) {
        try {
            String requete = "INSERT INTO publication (content, datePub) VALUES (?, NOW())";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, publication.getContent());
            pst.executeUpdate();
            System.out.println("Publication ajoutée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Publication publication) {
        try {
            String requete = "DELETE FROM publication WHERE idPub = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, publication.getIdPub());
                pst.executeUpdate();
            System.out.println("Publication supprimée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
//
@Override
public void modifier(Publication publication) {
    try {
        String requete = "UPDATE publication SET content=? WHERE idPub = ?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, publication.getContent());
        pst.setInt(2, publication.getIdPub());
        pst.executeUpdate();
        System.out.println("Publication modifiée !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
//
    @Override
    public ArrayList<Publication> afficher() {
        ArrayList<Publication> publications = new ArrayList<>();
        try {
            String requete = "SELECT * FROM publication";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Publication publication = new Publication();
                publication.setIdPub(rs.getInt("idPub"));
                publication.setContent(rs.getString("content"));
                publication.setDatePub(rs.getTimestamp("datePub"));
                publications.add(publication);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return publications;

    }


}

