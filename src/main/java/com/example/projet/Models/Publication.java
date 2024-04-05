package com.example.projet.Models;

import java.sql.Timestamp;

public class Publication {
    private int idPub;
//    private String title;
    private String content;
    private Timestamp datePub;

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Timestamp getDatePub() { return datePub;}
    public void setDatePub(Timestamp datePub) { this.datePub = datePub; }

    public Publication() {

    }

    public Publication(String content, Timestamp datePub) {
        this.content=content;
        this.datePub=datePub;
//        this.title=title;
    }

    public Publication(int idPub, String content) {
        this.idPub = idPub;
        this.content = content;
//        this.title=title;

    }




    @Override
    public String toString() {
        return "Publication{" +
                "idPub=" + idPub +
//                "title=" + title +
                ", content='" + content + '\'' +
                ", datePub=" + datePub +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Publication other = (Publication) obj;
        if (this.idPub != other.idPub) {
            return false;
        }
        return true;
    }

    public Publication(String content) {
        this.content = content;

    }
}
