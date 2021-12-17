package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainView implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");


    InhousePart p = new InhousePart(1,"bolt" , 3.00 , 450 , 0, 100, true);
    Inventory.addPart(p);
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
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("addPartFirstView");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyPartFirstView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPartFirstView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("modifyPartFirstView");
        stage.setScene(scene);
        stage.show();
    }
    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }
}
