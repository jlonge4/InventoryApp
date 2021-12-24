package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import model.InhousePart;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** initializes modify part class controller*/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was being asked to cast my double(price) to an int  */
public class modifyPartFirstView implements Initializable {

    public RadioButton inhouse;
    public RadioButton outsourced;
    public Label companyName;
    public TextField partId;
    public TextField partName;
    public TextField partInv;
    public TextField partPrice;
    public TextField partMax;
    public TextField partMin;
    public TextField companyText;
    public Button toMain;
    /** initializes modify part screen and populates necessary text fields*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        Part part = mainView.getPart();

        if(part instanceof InhousePart) {

            inhouse.setSelected(true);
            outsourced.setSelected(false);
            InhousePart inhouse = (InhousePart) part;
            partId.setText(Integer.toString(inhouse.getId()));
            partName.setText(inhouse.getName());
            partPrice.setText(Double.toString(inhouse.getPrice()));
            partMax.setText(Integer.toString(inhouse.getMax()));
            partMin.setText(Integer.toString(inhouse.getMin()));
            partInv.setText(Integer.toString(inhouse.getStock()));
            companyName.setText("Machine ID");
            companyText.setText(Integer.toString(((InhousePart)inhouse).getMachineID()));

        } else {

            inhouse.setSelected(false);
            outsourced.setSelected(true);
            OutsourcedPart outsourced = (OutsourcedPart) part;
            partId.setText(String.valueOf(outsourced.getId()));
            partName.setText(outsourced.getName());
            partPrice.setText(Double.toString(outsourced.getPrice()));
            partMax.setText(Integer.toString(outsourced.getMax()));
            partMin.setText(Integer.toString(outsourced.getMin()));
            partInv.setText(Integer.toString(outsourced.getStock()));
            companyName.setText("Company Name");
            companyText.setText(outsourced.getCompanyName());
        }
    }
    /** exits to main view*/
    public void toMainView(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cancel to Main Screen");
        alert.setContentText("You are cancelling part modification");
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
    /** sets button text depending on part type*/
    public void inhouse(ActionEvent actionEvent) {
        companyName.setText("Machine ID");
    }
    /** sets button text depending on part type*/
    public void outsourced(ActionEvent actionEvent) {
        companyName.setText("Company Name");
    }
    /** takes text fields and updates part that was modified*/
    @FXML
    void saveModPart(ActionEvent event) throws Exception {
        int partIndex = mainView.partToModifyIndex();
        String partiDee = partId.getText();
        int partID = Integer.parseInt(partiDee);
        String newPartName = partName.getText();
        int newPartInv = Integer.parseInt(partInv.getText());
        double newPartPrice = Double.parseDouble(partPrice.getText());
        int newPartMin = Integer.parseInt(partMin.getText());
        int newPartMax = Integer.parseInt(partMax.getText());

        if (inhouse.isSelected()) {
            int machineIDee = Integer.parseInt(companyText.getText());
            InhousePart inhouse = new InhousePart(partID, newPartName,  newPartPrice, newPartInv, newPartMin, newPartMax, machineIDee);
                if (isItemValid(inhouse)) {
                    Inventory.updatePart(partIndex, inhouse);
                }
        } else {
            String newPartCompName = companyName.getText();
            OutsourcedPart outsourced = new OutsourcedPart(partID, newPartName, newPartPrice, newPartInv, newPartMin, newPartMax, newPartCompName);
            Inventory.updatePart(partIndex,outsourced);
            if (isItemValid(outsourced)) {
                Inventory.updatePart(partIndex, outsourced);
            }
        }
        Parent modifyPartSave = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Scene scene = new Scene(modifyPartSave);
        Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    /** validates modified item*/
    public boolean isItemValid(Part part) throws IOException, NullPointerException, RuntimeException, NumberFormatException{
        if (part.getMin() > part.getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part min is greater than part max");
            alert.showAndWait();
            return false;
        }

        if (part.getStock() < part.getMin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part inventory is less than part min");
            alert.showAndWait();
            return false;
        }
        if (part.getStock() > part.getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part inventory is greater than part max");
            alert.showAndWait();
            return false;
        }
        if (part.getPrice() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part price cannot be less than zero");
            alert.showAndWait();
            return false;
        }
        if (part.getName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            String partiDee = partId.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            String newPartName = partName.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartInv = Integer.parseInt(partInv.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            double newPartPrice = Double.parseDouble(partPrice.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMin = Integer.parseInt(partMin.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the min text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMax = Integer.parseInt(partMax.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the max text field");
            alert.showAndWait();
            return false;
        }
          return true;
    }

}
