package rwilk.englishWordsGUI.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Language", new Locale("en"));
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"), resources);
        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Button btn = (Button)scene.lookup("#buttonTranslate");
                btn.fire();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("EnglishWordGUI");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
