package com.biscuitclicker;

import com.biscuitclicker.model.Game;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

/**
 * <h3>Biscuit Clicker</h3>
 * 
 * See detais about the application in the README.md file.
 */
public class Main extends Application {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private Game game = null;
    private Controller controller = null;

    /* --- Program launch --- */

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Loads the FXML and creates the scene

        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/fxml/view.fxml")
        );

        double standardWidth = 1000.0;
        double standardHeight = 650.0;

        Scene scene = new Scene(fxmlLoader.load(), standardWidth, standardHeight);

        // Gets the controller and game instances

        this.controller = fxmlLoader.getController();
        this.game = this.controller.getGame();

        // Loads the CSS in the scene

        scene.getStylesheets().add(
            getClass().getResource("/css/style.css").toExternalForm()
        );

        // Sets the title and the scene in the stage

        stage.setTitle("Biscuit Clicker");
        stage.setScene(scene);

        // Returns to the standard dimentions when demaximizated

        stage.maximizedProperty().addListener((obs, wasMaximized, isMaximized) -> {
            if(!isMaximized) {
                stage.setWidth(standardWidth);
                stage.setHeight(standardHeight);
                stage.centerOnScreen();
            }
        });

        // Shuts down the scheduler at the end

        stage.setOnCloseRequest(event -> {
            this.scheduler.shutdown();
        });

        // Starts the application

        stage.show();
        this.startAutoGain();
    }

    /* --- Autogain --- */

    /**
     * Runs every second to increment Game's points by its pps.
     * @since 1.0.0
     */
    public void startAutoGain() {
        this.scheduler.scheduleAtFixedRate(() -> {
            this.game.gainAutoPoints();

            javafx.application.Platform.runLater(() -> {
                this.controller.updateStatus();
            });
        }, 0, 1, TimeUnit.SECONDS);
    }
}
