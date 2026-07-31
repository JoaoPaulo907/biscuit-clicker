package com.biscuitclicker.view;

import com.biscuitclicker.model.Building;

import java.text.NumberFormat;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

/**
 * Links the model of the Building class to its interface.
 * @since 1.0.1
 */
public class BuildingNode {

    /* --- Attributes --- */

    private HBox rootNode;
    private Button purchaseButton;
    private Button upgradeButton;

    private Building model;

    /* --- Constructor --- */

    public BuildingNode(Building model, NumberFormat nf) {
        if(model == null || nf == null)
            throw new NullPointerException();

        this.model = model;

        // Creates the root node

        this.rootNode = new HBox();
        this.rootNode.setMaxWidth(Double.MAX_VALUE);

        // Creates the purchase button

        this.purchaseButton = new Button(
            this.getPurchaseText(nf)
        );

        this.purchaseButton.getStyleClass().add("purchaseBuilding");

        this.purchaseButton.prefWidthProperty().bind(
            this.rootNode.widthProperty().multiply(0.60)
        );

        // Creates the upgrade button

        this.upgradeButton = new Button(
            this.getUpgradeText(nf)
        );

        this.upgradeButton.getStyleClass().add("upgradeBuilding");

        this.upgradeButton.prefWidthProperty().bind(
            this.rootNode.widthProperty().multiply(0.40)
        );

        // Inserts the buttons into the root

        this.rootNode.getChildren().addAll(this.purchaseButton, this.upgradeButton);
    }

    /* --- Out --- */

    public HBox getRootNode() {
        return this.rootNode;
    }

    public Button getPurchaseButton() {
        return this.purchaseButton;
    }

    public Button getUpgradeButton() {
        return this.upgradeButton;
    }

    public Building getModel() {
        return this.model;
    }

    public String getPurchaseText(NumberFormat nf) {
        return (
            this.model.getName() + " " +
            "("   + nf.format(this.model.getQuantity()) + ")"   + 
            " - " + nf.format(this.model.getPrice())    + " b$"
        );
    }

    public String getUpgradeText(NumberFormat nf) {
        return (
            "lv. " + this.model.getLevel() +
            " - "  + nf.format(this.model.getUpgradePrice()) + " b$"
        );
    }
}
