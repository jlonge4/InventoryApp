//Java doc directory:
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** main class for application */
/** FUTURE ENHANCEMENT make students engage more with what exactly is being done in the main method (args)*/
public class Main extends Application{
    @Override
    /** starts the program at mainview */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource( "/view/mainView.fxml"));
        stage.setTitle("mainView");
        stage.setScene(new Scene(root, 800,600));
        stage.show();
    }
    /** main method argument and launch*/
    public static void main(String[] args) {
        launch(args);
    }

}
