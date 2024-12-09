module sio.btssiog22025velikojava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.sql;
    requires jdk.jdi;
    requires spring.security.crypto;
    requires javafx.base;


    opens sio.btssiog22025velikojava to javafx.fxml;
    opens sio.btssiog22025velikojava.models to javafx.base;
    exports sio.btssiog22025velikojava;
}