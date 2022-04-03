module hu.unideb.inf.homeworkproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.unideb.inf.homeworkproject to javafx.fxml;
    exports hu.unideb.inf.homeworkproject;
    exports hu.unideb.inf.homeworkproject.controller;
    opens hu.unideb.inf.homeworkproject.controller to javafx.fxml;
}