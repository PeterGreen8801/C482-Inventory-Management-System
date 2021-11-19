package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The Modify Part Controller class allows the user to edit a part on the parts table. It is composed of methods that enable the user to modify an existing part as long as it is valid.*/
public class ModifyPart implements Initializable {
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

    public Part partToModify;

    private AlertMessage alerter;

    /** The initialize method fills in the fields with the correct information from the part that the user selected on the Main Screen. It also checks if the part is InHouse or Outsourced and changes the text accordingly.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partToModify = MainScreen.getModifiedPart();

        if(partToModify instanceof InHouse) {
            PartInHouseButton.setSelected(true);
            PartLabelSwitch.setText("Machine ID");
            PartIDNameSwitchText.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
        }

        if(partToModify instanceof Outsourced) {
            PartOutsourcedButton.setSelected(true);
            PartLabelSwitch.setText("Company Name");
            PartIDNameSwitchText.setText(((Outsourced) partToModify).getCompanyName());
        }

        //Loads info from the part that will be modified into the correct fields
        PartIDText.setText(String.valueOf(partToModify.getId()));
        PartNameText.setText(partToModify.getName());
        PartInvText.setText(String.valueOf(partToModify.getStock()));
        PartPriceText.setText(String.valueOf(partToModify.getPrice()));
        PartMaxText.setText(String.valueOf(partToModify.getMax()));
        PartMinText.setText(String.valueOf(partToModify.getMin()));
    }

    /** Attached to the cancel button that will take the user back to the Main Screen.*/
    public void CancelToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** Attached to the InHouse radio button that will change the text to Machine ID.*/
    public void PartInHouseHandler(ActionEvent actionEvent) {
        PartLabelSwitch.setText("Machine ID");
    }

    /** Attached to the Outsourced radio button that will change the text to Company Name.*/
    public void PartOutsourcedHandler(ActionEvent actionEvent) {
        PartLabelSwitch.setText("Company Name");
    }

    /** Saves the information the user modified in the fields and replaces the original part on the Main Screen. Uses the minValueCheck and inventoryValueCheck methods to verify that fields are correct, and if they are the modified part is saved and the user is taken back to the Main Screen.*/
    public void SaveHandler(ActionEvent actionEvent) {
        try {
            String modifyPartName = PartNameText.getText();
            String modifyPartCompanyName;
            int modifyPartID = partToModify.getId();
            boolean modifyPartCheck = false;
            Double modifyPartPrice = Double.parseDouble(PartPriceText.getText());
            int modifyPartStock = Integer.parseInt(PartInvText.getText());
            int modifyPartMin = Integer.parseInt(PartMinText.getText());
            int modifyPartMax = Integer.parseInt(PartMaxText.getText());
            int modifyPartMachineId;

            if(minValueCheck(modifyPartMin,modifyPartMax) && inventoryValueCheck(modifyPartMin,modifyPartMax,modifyPartStock)) {
                if(PartInHouseButton.isSelected()) {
                    try {
                        modifyPartMachineId = Integer.parseInt(PartIDNameSwitchText.getText());
                        InHouse modifyInHousePart = new InHouse(modifyPartID,modifyPartName,modifyPartPrice,modifyPartStock,modifyPartMin,modifyPartMax,modifyPartMachineId);
                        Inventory.addPart(modifyInHousePart);
                        modifyPartCheck = true;
                    }
                    catch (Exception e) {
                        alerter.modifyPartAlert(2);
                    }
                }

                if(PartOutsourcedButton.isSelected()) {
                    modifyPartCompanyName = PartIDNameSwitchText.getText();
                    Outsourced modifyOutsourcedPart = new Outsourced(modifyPartID,modifyPartName,modifyPartPrice,modifyPartStock,modifyPartMin,modifyPartMax,modifyPartCompanyName);
                    Inventory.addPart(modifyOutsourcedPart);
                    modifyPartCheck = true;
                }

                if(modifyPartCheck) {
                    Inventory.deletePart(partToModify);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch(Exception e) {
            alerter.modifyPartAlert(4);
        }
    }

    /** Checks if inventory value is less than min or if inventory is greater than the max amount.
     * @param min The minimum value of a part.
     * @param max The maximum value of a part.
     * @param stock The stock/inventory of a part.
     * @return True if the inventory value is valid and False if the inventory value is invalid. It is invalid if the stock is less than the minimum or the stock is more than the maximum.*/
    private boolean inventoryValueCheck(int min, int max, int stock) {
        boolean valueCheck = true;

        if(stock < min || stock > max) {
            valueCheck = false;
            alerter.modifyPartAlert(3);
        }

        return valueCheck;
    }

    /** Checks if minimum value is less than or equal to 0 or if minimum value is greater than or equal to the maximum value.
     * @param min The minimum value of a part.
     * @param max The maximum value of a part.
     * @return True if the minimum value is valid and False if the minimum value is invalid. It is invalid if the minimum is less than or equal to 0 or if it is greater than or equal to the maximum.*/
    private boolean minValueCheck(int min, int max) {
        boolean valueCheck = true;

        if(min <=0 || min >= max) {
            valueCheck = false;
            alerter.modifyPartAlert(1);
        }

        return valueCheck;
    }
}
