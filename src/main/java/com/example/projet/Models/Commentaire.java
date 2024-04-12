/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.projet.Models;
import javafx.beans.property.*;

import java.sql.Timestamp;

public class Commentaire {
    private final IntegerProperty idComment = new SimpleIntegerProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<Timestamp> dateCom = new SimpleObjectProperty<>();
    private Publication pub ;



    public int getIdComment() {
        return idComment.get();
    }
    public IntegerProperty idCommentProperty() {
        return idComment;
    }
    public void setIdComment(int idComment) {
        this.idComment.set(idComment);
    }

    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    public Timestamp getDateCom() {
        return dateCom.get();
    }
    public ObjectProperty<Timestamp> dateComProperty() {
        return dateCom;
    }
    public void setDateCom(Timestamp dateCom) {this.dateCom.set(dateCom);}

    public Publication getPub() {
        return pub;
    }

    public void setPub(Publication pub) {
        this.pub = pub;
    }

    public Commentaire(){

    }

    public void set (Commentaire commentaire){}

    public Commentaire(int idComment, String description,Timestamp dateCom, Publication pub){
        this.idComment.set(idComment);
        this.description.set(description);
        this.dateCom.set(dateCom);
        this.pub=pub;
    }
    @Override
    public String toString() {
        return "Commentaire{" +
                "idComment=" + idComment +
                ", description='" + description + '\'' +
                ", dateCom=" + dateCom +
                '}';
    }



}