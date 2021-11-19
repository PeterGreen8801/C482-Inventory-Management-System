package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** The Add Part Controller class allows the user to add parts to the parts table. It is composed of methods that enable the user to save a created part as long as it is valid. */
public class AddPart {
    public Label PartLabelSwitch;
    public RadioButton PartInHouseButton;
    public RadioButton PartOutsourcedButton;
    public TextField PartIDText;
    public TextField PartNameText;
    public TextField PartInvText;
    public TextField PartPriceText;
    public TextField PartMaxText;
    public TextField PartMinText;
    public TextField PartIDNameSwitchText;
    public ToggleGroup PartControlGroup;

    private AlertMessage alerter;

    /** Attached to the cancel button that will take the user back to the Main Screen.*/
    //Cancel button back to main screen
    public void CancelToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** Saves the information the user put in the fields as a new part on the Main Screen. Uses the minValueCheck and inventoryValueCheck methods to verify that fields are correct, and if they are the new part is saved and the user is taken back to the Main Screen.*/
    //Manages the save button
    public void SaveHandler(ActionEvent actionEvent) throws IOException {
        try {
            String newPartName = PartNameText.getText();
            String newPartCompanyName;
            boolean addPartCheck = false;
            int newPartId = 0;
            Double newPartPrice = Double.parseDouble(PartPriceText.getText());
            int newPartStock = Integer.parseInt(PartInvText.getText());
            int newPartMin = Integer.parseInt(PartMinText.getText());
            int newPartMax = Integer.parseInt(PartMaxText.getText());
            int newPartMachineId;

            if (newPartName.isEmpty()) {
                alerter.addPartAlert(4);
            } else {
                if (minValueCheck(newPartMin, newPartMax) && inventoryValueCheck(newPartMin, newPartMax, newPartStock)) {
                    if (PartInHouseButton.isSelected()) {
                        try {
                            newPartMachineId = Integer.parseInt(PartIDNameSwitchText.getText());
                            InHouse newInHousePart = new InHouse(newPartId, newPartName, newPartPrice, newPartStock, newPartMin, newPartMax, newPartMachineId);
                            newInHousePart.setId(Inventory.generatePartId());
                            Inventory.addPart(newInHousePart);
                            addPartCheck = true;
                        } catch (Exception e) {
                            alerter.addPartAlert(2);
                        }
                    }

                    if (PartOutsourcedButton.isSelected()) {
                        newPartCompanyName = PartIDNameSwitchText.getText();
                        Outsourced newOutsourcedPart = new Outsourced(newPartId, newPartName, newPartPrice, newPartStock, newPartMin, newPartMax, newPartCompanyName);
                        newOutsourcedPart.setId(Inventory.generatePartId());
                        Inventory.addPart(newOutsourcedPart);
                        addPartCheck = true;
                    }

                    if (addPartCheck) {
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
        }
        catch (Exception e){
            alerter.addPartAlert(5);
        }
    }

    /** Attached to the InHouse radio button that will change the text to Machine ID.*/
    public void PartInHouseHandler(ActionEvent actionEvent) {
        PartLabelSwitch.setText("Machine ID");
    }

    /** Attached to the Outsourced radio button that will change the text to Company Name.*/
    public void PartOutsourcedHandler(ActionEvent actionEvent) {
        PartLabelSwitch.setText("Company Name");
    }

    /** Checks if inventory value is less than min or if inventory is greater than the max amount.
     * @param min The minimum value of a part.
     * @param max The maximum value of a part.
     * @param stock The stock/inventory of a part.
     * @return True if the inventory value is valid and False if the inventory value is invalid. It is invalid if the stock is less than the minimum or the stock is more than the maximum.*/
    //Checks if inventory value is less than min or if inventory is more than stock
    private boolean inventoryValueCheck(int min, int max, int stock) {
        boolean valueCheck = true;

        if(stock < min || stock > max) {
            valueCheck = false;
            alerter.addPartAlert(3);
        }

        return valueCheck;
    }

    /** Checks if minimum value is less than or equal to 0 or if minimum value is greater than or equal to the maximum value.
     * @param min The minimum value of a part.
     * @param max The maximum value of a part.
     * @return True if the minimum value is valid and False if the minimum value is invalid. It is invalid if the minimum is less than or equal to 0 or if it is greater than or equal to the maximum.*/
    //Checks if min value is below 0 or more than max, returns bool
    private boolean minValueCheck(int min, int max) {
        boolean valueCheck = true;

        if(min <=0 || min >= max) {
            valueCheck = false;
            alerter.addPartAlert(1);
        }

        return valueCheck;
    }

}
