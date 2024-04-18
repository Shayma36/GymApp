package com.example.projet.Models;


public class Cours {

    private int id;
    private String nom;
    private String heure;
    private String coach;
    private int Places;

    public Cours(int id , String nom, String heure, String coach , int Places){
        this.id = id;
        this.nom = nom;
        this.heure = heure;
        this.coach = coach;
        this.Places = Places;
    }

    public Cours() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public int getPlaces() {
        return Places;
    }

    public void setPlaces(int places) {
        Places = places;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", heure='" + heure + '\'' +
                ", coach='" + coach +'\'' +
                ", Places='" + Places +
                '}';
    }
}
