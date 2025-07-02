module at.bif.swen.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    requires org.kordamp.bootstrapfx.core;

    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.boot;
    requires spring.core;
    requires spring.beans;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires org.apache.logging.log4j;
    requires java.net.http;
    requires json.path;
    requires com.fasterxml.jackson.databind;
    requires kernel;
    requires layout;


    opens at.bif.swen.tourplanner to javafx.fxml;
    opens at.bif.swen.tourplanner.model to javafx.fxml, org.hibernate.orm.core, spring.core;
    opens at.bif.swen.tourplanner.view to javafx.fxml;
    opens at.bif.swen.tourplanner.viewmodel to javafx.fxml;
    opens at.bif.swen.tourplanner.service to spring.core, spring.beans;
    opens at.bif.swen.tourplanner.openRouteService to spring.beans, spring.core;




    exports at.bif.swen.tourplanner;
    exports at.bif.swen.tourplanner.model;
    exports at.bif.swen.tourplanner.view;
    exports at.bif.swen.tourplanner.viewmodel;
    exports at.bif.swen.tourplanner.service;




}