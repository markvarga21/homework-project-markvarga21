module hu.unideb.inf.homeworkproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.unideb.inf.homeworkproject to javafx.fxml;
    exports hu.unideb.inf.homeworkproject;
}