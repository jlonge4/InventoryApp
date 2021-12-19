package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyPartFirstView implements Initializable {


    public RadioButton inhouse;
    public RadioButton outsourced;
    public Label companyName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    public void toMainView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }

    public void inhouse(ActionEvent actionEvent) {
        companyName.setText("Machine ID");
    }

    public void outsourced(ActionEvent actionEvent) {
        companyName.setText("Company Name");
    }
}
