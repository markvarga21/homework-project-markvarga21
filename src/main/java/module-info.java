module hu.unideb.inf.homeworkproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.unideb.inf.homeworkproject to javafx.fxml;
    exports hu.unideb.inf.homeworkproject;
    opens hu.unideb.inf.homeworkproject.controller to javafx.fxml;

    exports hu.unideb.inf.homeworkproject.controller;
    exports hu.unideb.inf.homeworkproject.model;
    exports hu.unideb.inf.homeworkproject.view;
}