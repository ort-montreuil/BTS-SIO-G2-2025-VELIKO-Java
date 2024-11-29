module sio.btssiog22025velikojava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires jdk.jdi;
    requires java.sql;


    opens sio.btssiog22025velikojava to javafx.fxml;
    exports sio.btssiog22025velikojava;
}