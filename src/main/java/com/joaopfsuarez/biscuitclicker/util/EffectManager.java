package com.joaopfsuarez.biscuitclicker.util;

import javafx.util.Duration;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;

/**
 * Centralizes the effects and animations into one class.
 * @since 1.0.3
 */
public class EffectManager {

    /* --- Attributes --- */

    private Pane effectLayer;

    /* --- Constructor --- */

    public EffectManager(Pane effectLayer) {
        if(effectLayer == null)
            throw new NullPointerException();

        this.effectLayer = effectLayer;
    }

    /* --- Methods --- */

    /**
     * Displays a text rising from the current mouse position.
     * @since 1.0.3
     */
    public void risingText(String text, double mouseX, double mouseY) {
        // Creates the Label

        Label textLabel = new Label(text);

        textLabel.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-effect: dropshadow(gaussian, black, 5, 0, 2, 2);
        """);

        // Puts the text in the mouse position

        textLabel.setLayoutX(mouseX);
        textLabel.setLayoutY(mouseY);

        this.effectLayer.getChildren().add(textLabel);

        // Rises the text

        TranslateTransition rise = new TranslateTransition(Duration.seconds(1), textLabel);
        rise.setByY(-50);

        // Reduces the text opacity

        FadeTransition fade = new FadeTransition(Duration.seconds(1), textLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        // Creates the animation

        ParallelTransition animation = new ParallelTransition(rise, fade);

        animation.setOnFinished(event -> {
            this.effectLayer.getChildren().remove(textLabel);
        });

        animation.play();
    }
}
