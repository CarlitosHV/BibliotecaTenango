module com.hardbug.bibliotecatenango {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires mail;
    requires undertow.core;


    opens com.hardbug.bibliotecatenango to javafx.fxml;
    exports com.hardbug.bibliotecatenango;
    exports com.hardbug.bibliotecatenango.Models;
    opens com.hardbug.bibliotecatenango.Models to javafx.fxml;
}