/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.projet.Models;
import java.sql.Timestamp;


/**
 *
 * @author miral
 */
public class Commentaire {
  private int idComment ;
  private String description ;
  private Timestamp dateCom;
  private Publication pub ;
    public Commentaire(){

    }
  public Commentaire(String description,Publication pub){
      this.description=description;
      this.pub=pub;
  }
    public Commentaire(int idComment){
        this.idComment=idComment;
    }

    public Commentaire(int idComment, String description) {
        this.idComment = idComment;
        this.description = description;
    }



    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateCom() {
        return dateCom;
    }

    public void setDateCom(Timestamp dateCom) {
        this.dateCom = dateCom;
    }

    public Publication getPub() {
        return pub;
    }

    public void setPub(Publication pub) {
        this.pub = pub;
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
