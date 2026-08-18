package com.joaopfsuarez.biscuitclicker.view;

import java.text.NumberFormat;

import com.joaopfsuarez.biscuitclicker.model.Upgrade;

import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

/**
 * Represents an upgrade node, linking the view to the model.
 * @since 1.0.1
 */
public class UpgradeNode {

    /* --- Attributes --- */

    private HBox rootNode;
    private Button upgradeButton;

    private Upgrade model;

    /* --- Constructor --- */

    public UpgradeNode(Upgrade model, NumberFormat nf) {
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

        this.upgradeButton.setMaxWidth(Double.MAX_VALUE);
        this.upgradeButton.getStyleClass().addAll("button", "upgrade");

        // Expands the button to fill the width

        HBox.setHgrow(upgradeButton, Priority.ALWAYS);

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

    public Upgrade getModel() {
        return this.model;
    }

    public String getUpgradeText(NumberFormat nf) {
        return (
            "Upgrade Clicks - " + nf.format(this.model.getUpgradePrice()) + " b$"
        );
    }
}
