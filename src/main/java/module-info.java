module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.mail;

    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
    exports com.example.projet.Models;
    exports com.example.projet.utils;
    opens com.example.projet.utils to javafx.fxml;
}