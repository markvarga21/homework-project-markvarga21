module hu.unideb.inf.homeworkproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdbi.v3.core;
    requires lombok;
    requires java.sql;
    requires org.apache.logging.log4j;

    exports hu.unideb.inf.homeworkproject;
    exports hu.unideb.inf.homeworkproject.controller;
    exports hu.unideb.inf.homeworkproject.model;
    exports hu.unideb.inf.homeworkproject.view;
    exports hu.unideb.inf.homeworkproject.server;

    opens hu.unideb.inf.homeworkproject.controller to javafx.fxml;
    opens hu.unideb.inf.homeworkproject to javafx.fxml;
}