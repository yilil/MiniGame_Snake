package view;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameWindow window = new GameWindow();
        window.run();

        primaryStage.setTitle("Snake");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
    }
}
