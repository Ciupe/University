package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent mainWindow = new FXMLLoader(getClass().getResource("window.fxml")).load();
        Scene mainWindowScene = new Scene(mainWindow);
        primaryStage.setScene(mainWindowScene);
        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
