package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainView implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    public void toAddProductView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProductView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("addProductView");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProductView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProductView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("modifyProductView");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddPartFirstView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartFirstView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("addPartFirstView");
        stage.setScene(scene);
        stage.show();
    }
    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }
}