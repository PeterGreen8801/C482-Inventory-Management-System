package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The Modify Product Controller class allows the user to edit a product on the products table if there are no associated parts attached. It is composed of methods that enable the user to modify an existing product as long as it is valid.*/
public class ModifyProduct implements Initializable {
    public TextField ProductIdText;
    public TextField ProductNameText;
    public TextField ProductInvText;
    public TextField ProductPriceText;
    public TextField ProductMaxText;
    public TextField ProductMinText;
    public TextField ProductSearchText;

    public TableView<Part> PartTable;
    public TableColumn<Part, Integer> PartIdCol;
    public TableColumn<Part, String> PartNameCol;
    public TableColumn<Part, Integer> PartInvCol;
    public TableColumn<Part, Double> PartPriceCol;

    public TableView<Part> AssociatedPartTable;
    public TableColumn<Part, Integer> AssociatedPartIdCol;
    public TableColumn<Part, String> AssociatedPartNameCol;
    public TableColumn<Part, Integer> AssociatedPartInvCol;
    public TableColumn<Part, Double> AssociatedPartPriceCol;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Product productToModify;

    AlertMessage alerter;

    /** Sets up the parts table, associated parts table with parts attached to the product, and fills in the fields with the correct information from the product that the user selected on the Main Screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productToModify = MainScreen.getModifiedProduct();
        associatedParts = productToModify.getAllAssociatedParts();

        AssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartTable.setItems(associatedParts);

        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTable.setItems(Inventory.getAllParts());

        ProductIdText.setText(String.valueOf(productToModify.getId()));
        ProductNameText.setText(productToModify.getName());
        ProductInvText.setText(String.valueOf(productToModify.getStock()));
        ProductPriceText.setText(String.valueOf(productToModify.getPrice()));
        ProductMaxText.setText(String.valueOf(productToModify.getMax()));
        ProductMinText.setText(String.valueOf(productToModify.getMin()));
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

    /** Adds a selected part to the associated parts list and table. It then updates the associated parts table with the newly added part.*/
    public void AddHandler(ActionEvent actionEvent) {
        Part partToAdd = PartTable.getSelectionModel().getSelectedItem();

        if(partToAdd == null) {
            alerter.modifyProductAlert(2);
        }
        else {
            associatedParts.add(partToAdd);
            AssociatedPartTable.setItems(associatedParts);
        }
    }

    /** Saves the information the user modified in the fields and replaces the original product on the Main Screen. Uses the minValueCheck and inventoryValueCheck methods to verify that fields are correct, and if they are the modified product is saved and the user is taken back to the Main Screen. It also saves the associated parts to the modified product.*/
    public void SaveHandler(ActionEvent actionEvent) throws IOException {

        try{
            String modifyProductName = ProductNameText.getText();
            int modifyProductID = productToModify.getId();
            Double modifyProductPrice = Double.parseDouble(ProductPriceText.getText());
            int modifyProductStock = Integer.parseInt(ProductInvText.getText());
            int modifyProductMin = Integer.parseInt(ProductMinText.getText());
            int modifyProductMax = Integer.parseInt(ProductMaxText.getText());

            if(modifyProductName.isEmpty()) {
                alerter.modifyProductAlert(4);
            }
            else {
                if(minValueCheck(modifyProductMin,modifyProductMax) && inventoryValueCheck(modifyProductMin,modifyProductMax,modifyProductStock)) {
                    {
                        Product newProduct = new Product(modifyProductID,modifyProductName,modifyProductPrice,modifyProductStock,modifyProductMin,modifyProductMax);

                        for(Part part : associatedParts) {
                            newProduct.addAssociatedPart(part);
                        }
                        Inventory.addProduct(newProduct);
                        Inventory.deleteProduct(productToModify);
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }

        }
        catch (Exception e) {
            alerter.modifyProductAlert(5);
        }

    }

    /** Removes a selected part from the associated parts list and table. Also asks if the user is sure they want to remove the associated part.*/
    public void RemoveHandler(ActionEvent actionEvent) {
        Part partToRemove = AssociatedPartTable.getSelectionModel().getSelectedItem();

        if(partToRemove == null) {
            alerter.modifyProductAlert(2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Remove");
            alert.setContentText("Do you want to remove this part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK) {
                associatedParts.remove(partToRemove);
                AssociatedPartTable.setItems(associatedParts);
            }
        }
    }

    /** Checks if there are parts that share a partial name or id to what is entered in the search box and sets them to the parts table. It uses a created list that is filled by a for loop that walks through every element in the all Parts inventory and adds the elements that contain the partial name or id to the list. The parts table is then populated with the results.*/
    public void SearchHandler(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String query = ProductSearchText.getText();

        for(Part part: allParts) {
            if(String.valueOf(part.getId()).contains(query) || part.getName().contains(query)) {
                foundParts.add(part);
            }
        }

        PartTable.setItems(foundParts);
        ProductSearchText.setText("");

        if(foundParts.size() == 0) {
            alerter.modifyProductAlert(6);
        }
    }

    /** Checks if inventory value is less than min or if inventory is greater than the max amount.
     * @param min The minimum value of a product.
     * @param max The maximum value of a product.
     * @param stock The stock/inventory of a product.
     * @return True if the inventory value is valid and False if the inventory value is invalid. It is invalid if the stock is less than the minimum or the stock is more than the maximum.*/
    private boolean inventoryValueCheck(int min, int max, int stock) {
        boolean valueCheck = true;

        if(stock < min || stock > max) {
            valueCheck = false;
            alerter.modifyProductAlert(3);
        }

        return valueCheck;
    }

    /** Checks if minimum value is less than or equal to 0 or if minimum value is greater than or equal to the maximum value.
     * @param min The minimum value of a product.
     * @param max The maximum value of a product.
     * @return True if the minimum value is valid and False if the minimum value is invalid. It is invalid if the minimum is less than or equal to 0 or if it is greater than or equal to the maximum.*/
    private boolean minValueCheck(int min, int max) {
        boolean valueCheck = true;

        if(min <=0 || min >= max) {
            valueCheck = false;
            alerter.modifyProductAlert(1);
        }

        return valueCheck;
    }
}
