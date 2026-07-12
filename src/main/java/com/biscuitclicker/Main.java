package com.biscuitclicker;

import com.biscuitclicker.model.Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * <h3>--- Biscuit Clicker ---</h3>
 * See detais about the application in the README.md file.
 * 
 * @version 1.0.0
 */
public class Main extends Application {
    private static final Game GAME = new Game();

    private Controller controller = null;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /* --- Program launch --- */

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/fxml/main.fxml")
        );

        double standardWidth = 1000.0;
        double standardHeight = 650.0;

        Scene scene = new Scene(fxmlLoader.load(), standardWidth, standardHeight);
        this.controller = fxmlLoader.getController();

        scene.getStylesheets().add(
            getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setTitle("Biscuit Clicker");
        stage.setScene(scene);

        stage.maximizedProperty().addListener((obs, wasMaximized, isMaximized) -> {
            if(!isMaximized) {
                stage.setWidth(standardWidth);
                stage.setHeight(standardHeight);
                stage.centerOnScreen();
            }
        });

        stage.setOnCloseRequest(event -> {
            this.scheduler.shutdown();
        });

        stage.show();

        startAutoGain();
    }

    /* --- Out --- */

    public static Game getGame() {
        return GAME;
    }

    /* --- Autogain --- */

    /**
     * Runs every second to increment Game's points by its pps.
     * @since 1.0.0
     */
    public void startAutoGain() {
        this.scheduler.scheduleAtFixedRate(() -> {
            GAME.gainAutoPoints();

            if(this.controller != null) {
                javafx.application.Platform.runLater(() -> {
                    this.controller.updateStatus();
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
