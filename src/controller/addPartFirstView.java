package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;
import sun.text.DictionaryBasedBreakIterator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addPartFirstView implements Initializable {


    public RadioButton inhouse;
    public RadioButton outsourced;
    public Label companyName;
    public Button addNewPartForm;
    public TextField newID;
    public TextField newPartName;
    public TextField newPartInv;
    public TextField newPartPrice;
    public TextField newPartCompName;
    public TextField newPartMax;
    public TextField newPartMin;

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

    public boolean inhouseSelected() {
        boolean inhouseSelected = true;
        if (!inhouse.isSelected()) {
            inhouseSelected = false;
            System.out.println("inhouse button selected");
        }
        System.out.println("machine id selected");
        return inhouseSelected;
    }

    @FXML
    void addNewPartForm(ActionEvent event) throws Exception {
//    System.out.println(newPartCompName.getText());
        int partId = Integer.parseInt(newID.getText());
        String partName = newPartName.getText();
        int partInv = Integer.parseInt(newPartInv.getText());
        double partPrice = Double.parseDouble(newPartPrice.getText());
        int partMin = Integer.parseInt(newPartMin.getText());
        int partMax = Integer.parseInt(newPartMax.getText());
        String partCompName = newPartCompName.getText();



        if (inhouse.isSelected()) {
            System.out.println("inhouse button selected");
            int machineID = Integer.parseInt(newPartCompName.getText());
            Part inhouse = new InhousePart(partId, partName, partInv, (int) partPrice, partMin, partMax, machineID);
            Inventory.addPart(inhouse);
        } else {
            Part outsourced = new OutsourcedPart(partId, partName, partInv, (int) partPrice, partMin, partMax, partCompName);
            Inventory.addPart(outsourced);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
}
