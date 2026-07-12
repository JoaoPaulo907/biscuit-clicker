package com.biscuitclicker;

import com.biscuitclicker.exception.*;
import com.biscuitclicker.model.*;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controls the user interface of the application.
 * @since 1.0.0
 */
public class Controller {
    private final Game game = Main.getGame();

    private final NumberFormat nf = NumberFormat.getNumberInstance(
        Locale.of("en", "US")
    );

    /* --- FXML left box --- */

    @FXML
    private VBox leftBox;

    @FXML
    private VBox status;

    @FXML
    private Label points;

    @FXML
    private Label gain;

    @FXML
    private Button mainButton;

    /* --- FXML right box --- */

    @FXML
    private VBox rightBox;

    @FXML
    private VBox upgradesBox;

    @FXML
    private VBox buildingsBox;

    @FXML
    private Button powerUpgrade;

    /* --- Functions --- */

    @FXML
    public void initialize() {
        // Sets the width of the outer elements and the main button

        leftBox.setPrefWidth(750);
        rightBox.setPrefWidth(750);

        mainButton.setPrefSize(150, 150);

        mainButton.setStyle(
            "-fx-border-radius: 75;" +
            "-fx-background-radius: 75;"
        );

        // Sets the width of the inner elements

        status.setMaxWidth(Double.MAX_VALUE);
        points.setMaxWidth(Double.MAX_VALUE);
        gain.setMaxWidth(Double.MAX_VALUE);

        upgradesBox.setMaxWidth(Double.MAX_VALUE);
        buildingsBox.setMaxWidth(Double.MAX_VALUE);
        powerUpgrade.setMaxWidth(Double.MAX_VALUE);

        // Creates the buttons for each building

        for(int i = 0; i < game.getBuildings().size(); i++) {
            Building current = this.game.getBuildings().get(i);

            // Creates the elements

            HBox currentBuildingBox = new HBox();
            currentBuildingBox.setMaxWidth(Double.MAX_VALUE);

            Button buildingBtn = new Button(
                current.getName() + " " +
                "("   + nf.format(current.getQuantity()) + ")"  + 
                " - " + nf.format(current.getPrice())    + " b$"
            );

            buildingBtn.getStyleClass().add("buildingButton");

            Button upgradeBtn = new Button(
                "lv. " + current.getLevel() +
                " - "  + nf.format(current.getUpgradePrice()) + " b$"
            );

            upgradeBtn.getStyleClass().add("upgradeButton");

            // Assigns the click events

            final int idx = i;

            buildingBtn.setOnAction(event -> {
                try {
                    this.game.buy(idx, 1);
                } catch(BiscuitClickerException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                buildingBtn.setText(
                    current.getName() + " " +
                    "("   + nf.format(current.getQuantity()) + ")"  + 
                    " - " + nf.format(current.getPrice())    + " b$"
                );

                updateStatus();
            });

            upgradeBtn.setOnAction(event -> {
                try {
                    this.game.upgradeBuilding(idx);
                } catch(BiscuitClickerException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                upgradeBtn.setText(
                    "lv. " + current.getLevel() +
                    " - "  + nf.format(current.getUpgradePrice()) + " b$"
                );

                updateStatus();
            });

            // Puts the elements into the UI

            buildingBtn.prefWidthProperty().bind(
                currentBuildingBox.widthProperty().multiply(0.60)
            );

            upgradeBtn.prefWidthProperty().bind(
                currentBuildingBox.widthProperty().multiply(0.40)
            );

            currentBuildingBox.getChildren().addAll(buildingBtn, upgradeBtn);
            buildingsBox.getChildren().add(currentBuildingBox);
        }

        powerUpgrade.setText(
            "Upgrade Click - " + nf.format(this.game.getPower().getUpgradePrice()) + " b$"
        );

        powerUpgrade.setOnAction(event -> {
            try {
                this.game.upgradePower();
            } catch(BiscuitClickerException e) {
                System.out.println("Error: " + e.getMessage());
            }

            powerUpgrade.setText(
                "Upgrade Click - " + nf.format(this.game.getPower().getUpgradePrice()) + " b$"
            );

            updateStatus();
        });

        updateStatus();
    }

    @FXML
    private void handleMainButtonClick() {
        this.game.click();
        updateStatus();
    }

    /**
     * Updates the game UI based on the Game object state.
     * @since 1.0.0
     */
    public void updateStatus() {
        nf.setMaximumFractionDigits(0);
        String formattedPoints = nf.format(Math.floor(this.game.getPoints()));

        nf.setMaximumFractionDigits(1);
        String formattedPPS = nf.format(this.game.getPointsPerSecond());

        nf.setMaximumFractionDigits(0);
        String formattedPPC = nf.format(this.game.getPower().getGain());

        points.setText(formattedPoints + " biscuits");

        gain.setText(
            "bps: " + formattedPPS + "\n" +
            "bpc: " + formattedPPC
        );
    }
}
