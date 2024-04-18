package com.example.projet.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Publication {
    private final IntegerProperty idPub = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty content = new SimpleStringProperty();
    private final ObjectProperty<Timestamp> datePub = new SimpleObjectProperty<>();

    public Publication(int id, String title, String content) {

    }
    public int getIdPub() {
        return idPub.get();
    }

    public IntegerProperty idPubProperty() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub.set(idPub);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public Timestamp getDatePub() {
        return datePub.get();
    }

    public ObjectProperty<Timestamp> datePubProperty() {
        return datePub;
    }

    public void setDatePub(Timestamp datePub) {
        this.datePub.set(datePub);
    }

    public Publication() {

    }
    public void set(Publication pub) {
    }


    public Publication(int idPub, String title, String content, Timestamp datePub) {
        this.idPub.set(idPub);
        this.title.set(title);
        this.content.set(content);
        this.datePub.set(datePub);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "idPub=" + idPub +
                ", title=" + title +
                ", content='" + content + '\'' +
                ", datePub=" + datePub +
                '}';
    }

}
