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
    int partIndex = mainView.partToModifyIndex();

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
            companyText.setText(Integer.toString(((InhousePart) inhouse).getMachineID()));

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

    @FXML
    void saveModPart(ActionEvent event) throws Exception {
        String partiDee = partId.getText();
        int partID = Integer.parseInt(partiDee);
        String newPartName = partName.getText();
        int newPartInv = Integer.parseInt(partInv.getText());
        double newPartPrice = Double.parseDouble(partPrice.getText());
        int newPartMin = Integer.parseInt(partMin.getText());
        int newPartMax = Integer.parseInt(partMax.getText());
        String newPartCompName = companyName.getText();

        if (inhouse.isSelected()) {
            int machineID = Integer.parseInt(newPartCompName);
            Part inhouse = new InhousePart(partID, newPartName, newPartInv, (int) newPartPrice, newPartMin, newPartMax, machineID);
            Inventory.addPart(inhouse);
        } else {
            Part outsourced = new OutsourcedPart(partID, newPartName, newPartInv, (int) newPartPrice, newPartMin, newPartMax, newPartCompName);
            Inventory.addPart(outsourced);
        }
        Parent modifyProductSave = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Scene scene = new Scene(modifyProductSave);
        Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
