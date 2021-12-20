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

    public void toAddPartSecondView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartSecondView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("addPartSecondView");
        stage.setScene(scene);
        stage.show();
    }

    public void inhouse(ActionEvent actionEvent) {
        companyName.setText("Machine ID");

        /*How to test if button is selected*/
        if (inhouse.isSelected()) {
            System.out.println(inhouse.isSelected());
        } else
            System.out.println(inhouse.isSelected());
    }

    public void outsourced(ActionEvent actionEvent) {
        companyName.setText("Company Name");
    }


    @FXML
    void addNewPartForm(ActionEvent event) throws IOException {

        int partId = Integer.parseInt(newID.getText());
        String partName = newPartName.getText();
        int partInv = Integer.parseInt(newPartInv.getText());
        int partPrice = Integer.parseInt(newPartPrice.getText());
        int partMin = Integer.parseInt(newPartMin.getText());
        int partMax = Integer.parseInt(newPartMax.getText());
        String partCompName = newPartCompName.getText();
        int machineID = Integer.parseInt(newPartCompName.getText());


        try {
            if (inhouse.isSelected()) {
                System.out.println("Part name: " + partName);
                InhousePart inhousePart = new InhousePart(partId, partName, partInv, partPrice, partMin, partMax, machineID );
                inhousePart.setId(partId);
                inhousePart.setName(partName);
                inhousePart.setPrice(partPrice);
                inhousePart.setStock(partInv);
                inhousePart.setMin(partMin);
                inhousePart.setMax(partMax);
                inhousePart.setMachineID(1);
                Inventory.addPart(inhousePart);
            } else {
                System.out.println("Part name: " + partName);
//                OutsourcedPart outPart = new OutsourcedPart();
//                outPart.setId(1);
//                outPart.setName(partName);
//                outPart.setPrice(Double.parseDouble(partPrice));
//                outPart.setStock(Integer.parseInt(partInv));
//                outPart.setMin(Integer.parseInt(partMin));
//                outPart.setMax(Integer.parseInt(partMax));
//                outPart.setCompanyName(partCompName);
//                Inventory.addPart(outPart);
            }

            Parent addPartSave = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
            Scene scene = new Scene(addPartSave);
            Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

    }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }
}
