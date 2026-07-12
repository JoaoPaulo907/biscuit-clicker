module com.biscuitclicker {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports com.biscuitclicker;
    exports com.biscuitclicker.model;

    opens com.biscuitclicker to javafx.fxml;
}
