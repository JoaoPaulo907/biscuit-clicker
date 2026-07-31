package com.biscuitclicker.view;

import com.biscuitclicker.model.Power;

import java.text.NumberFormat;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

/**
 * Links the model of the Power class to its interface.
 * @since 1.0.1
 */
public class PowerNode {

    /* --- Attributes --- */

    private HBox rootNode;
    private Button upgradeButton;

    private Power model;

    /* --- Constructor --- */

    public PowerNode(Power model, NumberFormat nf) {
        if(model == null || nf == null)
            throw new NullPointerException();

        this.model = model;

        // Creates the root node

        this.rootNode = new HBox();
        this.rootNode.setMaxWidth(Double.MAX_VALUE);

        // Creates the upgrade button

        this.upgradeButton = new Button(
            this.getUpgradeText(nf)
        );

        this.upgradeButton.getStyleClass().add("upgradePower");

        // Inserts the button into the root

        this.rootNode.getChildren().add(this.upgradeButton);
    }

    /* --- Out --- */

    public HBox getRootNode() {
        return this.rootNode;
    }

    public Button getUpgradeButton() {
        return this.upgradeButton;
    }

    public Power getModel() {
        return this.model;
    }

    public String getUpgradeText(NumberFormat nf) {
        return (
            "Upgrade Clicks - " + nf.format(this.model.getUpgradePrice()) + " b$"
        );
    }
}
