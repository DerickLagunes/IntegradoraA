module mx.edu.utez.fxa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires ucp;


    opens mx.edu.utez.fxa to javafx.fxml;
    opens mx.edu.utez.fxa.modelo to javafx.fxml;
    exports mx.edu.utez.fxa;
    exports mx.edu.utez.fxa.modelo;
}