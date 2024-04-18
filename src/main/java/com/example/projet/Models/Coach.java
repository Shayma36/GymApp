package com.example.projet.Models;

import java.util.ArrayList;
import java.util.List;

public class Coach {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int num;
    private static List<Coach> coachList = new ArrayList<>();

    public Coach(int id ,String nom, String prenom, String email, int num){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num=num;
    }

    public Coach() {

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", num=" + num +
                '}';
    }

    public static void add(Coach coach) {
        coachList.add(coach);
    }
}
