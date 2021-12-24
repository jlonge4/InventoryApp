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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
/**new class (controller) for adding parts ***/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was failing to Integer.parseInt a company name  */
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

    /**Initializes addPart screen ***/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

    }

    /**Upon cancel, opens MainView screen ***/
    public void toMainView(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cancel to Main Screen");
        alert.setContentText("You are cancelling part add");
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }

    /**based on radio selection, sets text to machine Id or Company Name ***/
    public void inhouse(ActionEvent actionEvent) {
        companyName.setText("Machine ID");
    }
    /**based on radio selection, sets text to machine Id or Company Name ***/
    public void outsourced(ActionEvent actionEvent) {
        companyName.setText("Company Name");
    }
    /**a way to know if part is outsourced or inhouse based on radio selector ***/
    public boolean inhouseSelected() {
        boolean inhouseSelected = true;
        if (!inhouse.isSelected()) {
            inhouseSelected = false;
            System.out.println("inhouse button selected");
        }
        System.out.println("machine id selected");
        return inhouseSelected;
    }
    /**increases count each time it is called for random ID***/
    public int increaseCount(int count) {
        count ++;
        return count;
    }
    /**generates a random ID ***/
    public int randomId() {
        AtomicInteger randomId = new AtomicInteger(increaseCount(Inventory.getAllParts().size()));
        Inventory.getAllParts().forEach((item) -> {
            if (item.getId() == randomId.get()) {
                randomId.addAndGet(1);
            };

        });
        return randomId.get();
    }

    /**takes in all values and adds new part to allParts list ***/
    @FXML
    void addNewPartForm(ActionEvent event) throws NumberFormatException, NullPointerException, IOException {
        int partId = randomId();
        String partName = newPartName.getText();
        int partInv = Integer.parseInt(newPartInv.getText());
        double partPrice = Double.parseDouble(newPartPrice.getText());
        int partMin = Integer.parseInt(newPartMin.getText());
        int partMax = Integer.parseInt(newPartMax.getText());
        String partCompName = newPartCompName.getText();



        if (inhouse.isSelected()) {
            System.out.println("inhouse button selected");
            int machineID = Integer.parseInt(newPartCompName.getText());
            Part inhouse = new InhousePart(partId, partName, partPrice, partInv, partMin, partMax, machineID);
                mainView.isItemValid(inhouse);

        } else {
            Part outsourced = new OutsourcedPart(partId, partName, partPrice, partInv, partMin, partMax, partCompName);
                mainView.isItemValid(outsourced);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
}
