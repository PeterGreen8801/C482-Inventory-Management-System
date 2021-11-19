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

/** The Add Product Controller class allows the user to add products to the products table. It is composed of methods that enable the user to save a created product as long as it is valid.*/
public class AddProduct implements Initializable {
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

    private AlertMessage alerter;

    /** The initialize method sets up the parts table and the associated parts table. It also fills in the parts table with all the parts corresponding to the Main Screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        PartTable.setItems(Inventory.getAllParts());
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
    //Add button
    public void AddHandler(ActionEvent actionEvent) {
        Part partToAdd = PartTable.getSelectionModel().getSelectedItem();

        if(partToAdd == null) {
            alerter.addProductAlert(2);
        }
        else {
            associatedParts.add(partToAdd);
            AssociatedPartTable.setItems(associatedParts);
        }
    }

    /** Saves the information the user put in the fields as a new product on the Main Screen. Uses the minValueCheck and inventoryValueCheck methods to verify that fields are correct, and if they are the new product is saved and the user is taken back to the Main Screen. It also saves the associated parts to the newly created product.*/
    //Save button
    public void SaveHandler(ActionEvent actionEvent) throws IOException {
        try {
            String newProductName = ProductNameText.getText();
            Double newProductPrice = Double.parseDouble(ProductPriceText.getText());
            int newProductId = 0;
            int newProductStock = Integer.parseInt(ProductInvText.getText());
            int newProductMin = Integer.parseInt(ProductMinText.getText());
            int newProductMax = Integer.parseInt(ProductMaxText.getText());

            if(newProductName.isEmpty()) {
                alerter.addProductAlert(4);
            }
            else {
                if(minValueCheck(newProductMin,newProductMax) && inventoryValueCheck(newProductMin,newProductMax,newProductStock)) {

                    Product newProduct = new Product(newProductId,newProductName,newProductPrice,newProductStock,newProductMin,newProductMax);

                    for(Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    newProduct.setId(Inventory.generateProductId());
                    Inventory.addProduct(newProduct);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch (Exception e) {
            alerter.addProductAlert(5);
        }
    }

    /** Removes a selected part from the associated parts list and table. Also asks if the user is sure they want to remove the associated part.*/
    //Remove button
    public void RemoveHandler(ActionEvent actionEvent) {
        Part partToRemove = AssociatedPartTable.getSelectionModel().getSelectedItem();

        if(partToRemove == null) {
            alerter.addProductAlert(2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove");
            alert.setTitle("Associated Parts");
            alert.setContentText("Do you want to remove this part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK) {
                associatedParts.remove(partToRemove);
                AssociatedPartTable.setItems(associatedParts);
            }
        }
    }

    /** Checks if there are parts that share a partial name or id to what is entered in the search box and sets them to the parts table. It uses a created list that is filled by a for loop that walks through every element in the all Parts inventory and adds the elements that contain the partial name or id to the list. The parts table is then populated with the results.*/
    //Search button
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
            alerter.addProductAlert(6);
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
            alerter.addProductAlert(3);
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
            alerter.addProductAlert(1);
        }

        return valueCheck;
    }

}
