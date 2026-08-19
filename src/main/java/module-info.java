module com.joaopfsuarez.biscuitclicker {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.joaopfsuarez.biscuitclicker;
    exports com.joaopfsuarez.biscuitclicker.model;

    opens com.joaopfsuarez.biscuitclicker to javafx.fxml;
}
