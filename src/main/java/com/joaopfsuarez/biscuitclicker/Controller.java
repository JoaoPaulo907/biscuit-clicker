package com.joaopfsuarez.biscuitclicker;

import java.text.NumberFormat;
import java.util.Locale;

import com.joaopfsuarez.biscuitclicker.exception.BiscuitClickerException;
import com.joaopfsuarez.biscuitclicker.model.*;
import com.joaopfsuarez.biscuitclicker.util.EffectManager;
import com.joaopfsuarez.biscuitclicker.util.Logger;
import com.joaopfsuarez.biscuitclicker.view.*;

import javafx.fxml.FXML;

import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * Controls the user interface of the application.
 * @since 1.0.0
 */
public class Controller {

    /* --- Attributes --- */

    private Game game = null;
    private EffectManager effectManager = null;

    private final NumberFormat nf = NumberFormat.getNumberInstance(
        Locale.of("en", "US")
    );

    /* --- FXML elements --- */

    @FXML
    private Pane effectLayer;

    @FXML
    private VBox leftBox;

    @FXML
    private VBox rightBox;

    @FXML
    private VBox statusBox;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label gainLabel;

    @FXML
    private StackPane mainButton;

    @FXML
    private VBox upgradesBox;

    @FXML
    private VBox buildingsBox;

    /* --- Start --- */

    @FXML
    public void initialize() {
        // Initializes the attributes

        this.game = new Game();
        this.effectManager = new EffectManager(this.effectLayer);

        // Sets the width of the outer elements

        this.leftBox.setPrefWidth(750);
        this.rightBox.setPrefWidth(750);

        // Sets the width of the inner elements

        this.statusBox.setMaxWidth(Double.MAX_VALUE);

        this.pointsLabel.setMaxWidth(Double.MAX_VALUE);
        this.gainLabel.setMaxWidth(Double.MAX_VALUE);

        this.upgradesBox.setMaxWidth(Double.MAX_VALUE);
        this.buildingsBox.setMaxWidth(Double.MAX_VALUE);

        // Initializes the nodes

        this.initializeMainButton();
        this.initializeUpgradesBox();
        this.initializeBuildingsBox();

        // Shows the initial status

        this.updateStatus();
    }

    /* --- Initializations --- */

    /**
     * Initializes the node {@code mainButton}.
     * @since 1.0.4
     */
    private void initializeMainButton() {
        Circle background = new Circle(75, 75, 75);
        background.setFill(Color.TRANSPARENT);

        ImageView img = new ImageView(
            new Image(getClass().getResource("/images/biscuit.png").toExternalForm())
        );

        img.setFitWidth(150);
        img.setFitHeight(150);

        Circle clip = new Circle(75, 75, 75);
        img.setClip(clip);

        // Adds the nodes and corrects the hitbox

        this.mainButton.getChildren().addAll(background, img);
        this.mainButton.setPickOnBounds(false);

        // Handles clicks in the main button

        this.mainButton.setOnMouseClicked(event -> {
            this.game.click();
            this.updateStatus();

            this.effectManager.risingText(
                "+" + this.game.getClickPower().getGain(),
                event.getSceneX(),
                event.getSceneY()
            );
        });
    }

    /**
     * Initializes the node {@code upgradesBox}.
     * @since 1.0.4
     */
    private void initializeUpgradesBox() {
        UpgradeNode powerNode = new UpgradeNode(this.game.getClickPower(), nf);

        // Handles power upgrades

        powerNode.getUpgradeButton().setOnAction(event -> {
            try {
                this.game.upgrade(powerNode.getModel());
            } catch(BiscuitClickerException e) {
                Logger.error(e.getMessage());
            }

            powerNode.getUpgradeButton().setText(
                powerNode.getUpgradeText(nf)
            );

            this.updateStatus();
        });

        this.upgradesBox.getChildren().add(powerNode.getRootNode());
    }

    /**
     * Initializes the node {@code buildingsBox}.
     * @since 1.0.4
     */
    private void initializeBuildingsBox() {
        for(Building current : game.getBuildings()) {
            BuildingNode buildingNode = new BuildingNode(current, nf);

            // Handles building purchases

            buildingNode.getPurchaseButton().setOnAction(event -> {
                try {
                    this.game.buy(buildingNode.getModel(), 1);
                } catch(BiscuitClickerException e) {
                    Logger.error(e.getMessage());
                }

                buildingNode.getPurchaseButton().setText(
                    buildingNode.getPurchaseText(nf)
                );

                this.updateStatus();
            });

            // Handles building upgrades

            buildingNode.getUpgradeButton().setOnAction(event -> {
                try {
                    this.game.upgrade(buildingNode.getModel());
                } catch(BiscuitClickerException e) {
                    Logger.error(e.getMessage());
                }

                buildingNode.getUpgradeButton().setText(
                    buildingNode.getUpgradeText(nf)
                );

                this.updateStatus();
            });

            buildingsBox.getChildren().add(buildingNode.getRootNode());
        }
    }

    /* --- Out --- */

    public Game getGame() {
        return this.game;
    }

    /* --- Methods --- */

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
        String formattedPPC = nf.format(this.game.getClickPower().getGain());

        this.pointsLabel.setText(formattedPoints + " biscuits");

        this.gainLabel.setText(
            "bps: " + formattedPPS + "\n" +
            "bpc: " + formattedPPC
        );
    }
}
