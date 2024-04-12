package com.example.projet.interfaces;


import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface IService <T> {
     void ajouter(T t);
     void supprimer(T t);
     void modifier(T t);
     ObservableList<T> afficher();
}
