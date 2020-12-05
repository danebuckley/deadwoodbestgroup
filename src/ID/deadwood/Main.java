package ID.deadwood;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Deadwood");
        primaryStage.setScene(new Scene(root, 1200, 720));
        primaryStage.show();

        GameLoop gameLoop = GameLoop.getInstance();
        gameLoop.run();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
