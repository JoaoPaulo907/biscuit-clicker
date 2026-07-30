package com.biscuitclicker;

import com.biscuitclicker.exception.BiscuitClickerException;
import com.biscuitclicker.util.Log;
import com.biscuitclicker.model.*;
import com.biscuitclicker.view.*;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * Controls the user interface of the application.
 * @since 1.0.0
 */
public class Controller {
    private final Game game = new Game();

    private final NumberFormat nf = NumberFormat.getNumberInstance(
        Locale.of("en", "US")
    );

    /* --- FXML left box --- */

    @FXML
    private VBox leftBox;

    @FXML
    private VBox statusBox;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label gainLabel;

    @FXML
    private Button mainButton;

    /* --- FXML right box --- */

    @FXML
    private VBox rightBox;

    @FXML
    private VBox upgradesBox;

    @FXML
    private VBox buildingsBox;

    /* --- Out --- */

    public Game getGame() {
        return this.game;
    }

    /* --- Functions --- */

    @FXML
    public void initialize() {
        // Sets the width of the outer elements

        this.leftBox.setPrefWidth(750);
        this.rightBox.setPrefWidth(750);

        // Sets the size and radius of the main button

        this.mainButton.setPrefSize(150, 150);

        this.mainButton.setStyle(
            "-fx-border-radius: 50%;" +
            "-fx-background-radius: 50%;"
        );

        // Loads the biscuit image

        ImageView img = new ImageView(
            new Image(getClass().getResource("/images/biscuit.png").toExternalForm())
        );

        img.setFitWidth(150);
        img.setFitHeight(150);

        Circle circle = new Circle(75, 75, 75);
        img.setClip(circle);

        this.mainButton.setGraphic(img);

        // Handles clicks in the main button

        this.mainButton.setOnAction(event -> {
            this.game.click();
            this.updateStatus();
        });

        // Sets the width of the inner elements

        this.statusBox.setMaxWidth(Double.MAX_VALUE);

        this.pointsLabel.setMaxWidth(Double.MAX_VALUE);
        this.gainLabel.setMaxWidth(Double.MAX_VALUE);

        this.upgradesBox.setMaxWidth(Double.MAX_VALUE);
        this.buildingsBox.setMaxWidth(Double.MAX_VALUE);

        // Creates the buttons for each building

        for(Building current : game.getBuildings()) {
            BuildingNode buildingNode = new BuildingNode(current, nf);

            buildingNode.getPurchaseButton().setOnAction(event -> {
                try {
                    this.game.buy(buildingNode.getModel(), 1);
                } catch(BiscuitClickerException e) {
                    Log.error(e.getMessage());
                }

                buildingNode.getPurchaseButton().setText(
                    buildingNode.getPurchaseText(nf)
                );

                this.updateStatus();
            });

            buildingNode.getUpgradeButton().setOnAction(event -> {
                try {
                    this.game.upgrade(buildingNode.getModel());
                } catch(BiscuitClickerException e) {
                    Log.error(e.getMessage());
                }

                buildingNode.getUpgradeButton().setText(
                    buildingNode.getUpgradeText(nf)
                );

                this.updateStatus();
            });

            buildingsBox.getChildren().add(buildingNode.getRootNode());
        }

        // Creates the power upgrade button

        PowerNode powerNode = new PowerNode(this.game.getPower(), nf);

        powerNode.getUpgradeButton().setOnAction(event -> {
            try {
                this.game.upgrade(powerNode.getModel());
            } catch(BiscuitClickerException e) {
                Log.error(e.getMessage());
            }

            powerNode.getUpgradeButton().setText(
                powerNode.getUpgradeText(nf)
            );

            this.updateStatus();
        });

        this.upgradesBox.getChildren().add(powerNode.getRootNode());

        // Shows the initial status

        this.updateStatus();
    }

    /**
     * Updates the game UI based on current Game instance state.
     * @since 1.0.0
     */
    public void updateStatus() {
        nf.setMaximumFractionDigits(0);
        String formattedPoints = nf.format(Math.floor(this.game.getPoints()));

        nf.setMaximumFractionDigits(1);
        String formattedPPS = nf.format(this.game.getPointsPerSecond());

        nf.setMaximumFractionDigits(0);
        String formattedPPC = nf.format(this.game.getPower().getGain());

        this.pointsLabel.setText(formattedPoints + " biscuits");

        this.gainLabel.setText(
            "bps: " + formattedPPS + "\n" +
            "bpc: " + formattedPPC
        );
    }
}
