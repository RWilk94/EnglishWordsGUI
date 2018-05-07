package rwilk.englishWordsGUI.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class About {

    public static void display() {
        //Create new stage
        Stage window = new Stage();
        //Allow shows stage as dialog
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("About");
        //Create label with info about author
        Label about = new Label("Created by R. Wilk.");
        //Create new BorderPane and add label
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(about);
        //Create scene and set as main scene
        Scene scene = new Scene(borderPane, 400, 400);
        window.setScene(scene);
        //Set window as dialog
        window.showAndWait();
    }

}