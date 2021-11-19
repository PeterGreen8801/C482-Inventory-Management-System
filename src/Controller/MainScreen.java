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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The Main Screen Controller class allows the user to view the parts/products tables, search within them, and navigate to the Add Part, Modify Part, Add Product, and Modify Product Screens. It includes methods that are used to delete parts/products, search the tables, navigate to other screens, and exit the program. */
public class MainScreen implements Initializable {
    Inventory inventory;

    //Generic variables
    public TableView<Part> PartsTable;
    public TableColumn<Part, Integer> PartsID;
    public TableColumn<Part, String> PartsName;
    public TableColumn<Part, Integer> PartsInventoryLevel;
    public TableColumn<Part, Double> PartsPrice;

    public TableView<Product> ProductsTable;
    public TableColumn<Product, Integer> ProductsID;
    public TableColumn<Product,String> ProductsName;
    public TableColumn<Product, Integer> ProductsInventoryLevel;
    public TableColumn<Product, Double> ProductsPrice;
    public TextField PartsSearchText;
    public TextField ProductsSearchText;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    private ObservableList<Product> productInventory = FXCollections.observableArrayList();

    public static Part modifiedPart;
    public static Product modifiedProduct;

    private AlertMessage alerter;

    /** The initialize method generates the part and product tables on the Main Screen. It sets a list of parts and products in this class to the list of all parts and products in the inventory.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partInventory.setAll(inventory.getAllParts());
        productInventory.setAll(inventory.getAllProducts());

        PartsTable.setItems(partInventory);
        ProductsTable.setItems(productInventory);

        PartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized right");
    }

    //Handlers that work with fxml to execute code

    /** Checks if there are parts that share a partial name or id to what is entered in the search box and sets them to the table. It uses the searchByPartName and getPartsID methods to check if there are parts that match the search box. If none can be found an alert message tells the user.*/
    //Where the search is actually done, connected to corresponding search button
    public void searchPartsHandler(ActionEvent actionEvent) {
        String query = PartsSearchText.getText();

        ObservableList<Part> parts = searchByPartName(query);

        if(parts.size() == 0) {
            try {
                int id = Integer.parseInt(query);
                Part p = getPartsID(id);
                if (p != null) {
                    parts.add(p);
                }
            }
            catch (NumberFormatException e) {
                //Ignore
            }
        }

        PartsTable.setItems(parts);
        PartsSearchText.setText("");
        if(parts.size() == 0) {
            alerter.mainScreenAlert(1);
        }
    }

    /** Loads the AddPart Screen.*/
    //Add part transfer button
    public void PartsAddHandler(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Loads the ModifyPart Screen if a part is selected.*/
    //Modify part transfer button
    public void PartsModifyHandler(ActionEvent actionEvent) throws IOException {
        modifiedPart = PartsTable.getSelectionModel().getSelectedItem();

        if(modifiedPart == null) {
            alerter.mainScreenAlert(2);
        }
        else {

            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Deletes a part if it is selected.*/
    //Delete part button
    public void PartsDeleteHandler(ActionEvent actionEvent) {
        Part partToDelete = PartsTable.getSelectionModel().getSelectedItem();

        if(partToDelete == null) {
            alerter.mainScreenAlert(2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK) {
                Inventory.deletePart(partToDelete);
                partInventory.remove(partToDelete);
            }
        }

    }

    /** Searches for a partial name match and returns a list of all parts that include that partial name. It uses a created list that is filled by a for loop that walks through every element in the all Parts inventory and adds the elements that contain the partial name to the list.
     * @param partialName The word that the list is comparing to, to be added.
     * @return A list of parts that contain the partial name.*/
    //Method to search through list of Parts like Webinar
    private ObservableList<Part> searchByPartName(String partialName) {

        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts) {
            if(part.getName().contains(partialName)) {
                namedPart.add(part);
            }
        }

        return namedPart;
    }

/** Searches for an id match and returns the Part that has that id. A for loop walks through all the parts in the inventory and returns the Part that has the id if there is one.
 * @param id The id being searched with.
 * @return The part that has the matching id. Null if there is none.*/
    //Method to search through Parts ID
    private Part getPartsID(int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if(part.getId() == id) {
                return part;
            }
        }

        return null;
    }

    /** Checks if there are products that share a partial name or id to what is entered in the search box and sets them to the table. It uses the searchByProductName and getProductsID methods to check if there are products that match the search box. If none can be found an alert message tells the user.*/
    public void searchProductsHandler(ActionEvent actionEvent) {
        String query = ProductsSearchText.getText();

        ObservableList<Product> products = searchByProductName(query);

        if(products.size() == 0){
            try {
                int id = Integer.parseInt(query);
                Product p = getProductsID(id);

                if(p != null) {
                    products.add(p);
                }
            }
            catch (NumberFormatException e) {
                //Ignore
            }
        }

        ProductsTable.setItems(products);
        ProductsSearchText.setText("");
        if(products.size() == 0) {
            alerter.mainScreenAlert(3);
        }
    }

    /** Searches for a partial name match and returns a list of all products that include that partial name. It uses a created list that is filled by a for loop that walks through every element in the all Products inventory and adds the elements that contain the partial name to the list.
     * @param partialName The word that the list is comparing to, to be added.
     * @return A list of products that contain the partial name.*/
    //Method to search through list of Products
    private ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : allProducts) {
            if(product.getName().contains(partialName)){
                namedProduct.add(product);
            }
        }

        return namedProduct;
    }

    /** Searches for an id match and returns the Product that has that id. A for loop walks through all the products in the inventory and returns the Product that has the id if there is one.
     * @param id The id being searched with.
     * @return The product that has the matching id. Null if there is none.*/
    //Method to search through Products ID
    private Product getProductsID(int id) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            if(product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    /** Loads the AddProduct Screen.*/
    //Add product screen transfer button
    public void ProductsAddHandler(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Loads the ModifyProduct Screen if a product is selected **RUNTIME ERROR example**. I corrected a runtime error I encountered by adding a check to see if null would get passed to the Modify Product controller. Instead of erroring by passing null, it now alerts the user that they need to select a product.*/
    //Modify product screen transfer button
    public void ProductsModifyHandler(ActionEvent actionEvent) throws IOException {

        modifiedProduct = ProductsTable.getSelectionModel().getSelectedItem();

        if(modifiedProduct == null) {
            alerter.mainScreenAlert(4);
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Deletes a Product if it is selected and there are no associated parts attached.*/
    //Delete product button
    public void ProductsDeleteHandler(ActionEvent actionEvent) {
        Product productToDelete = ProductsTable.getSelectionModel().getSelectedItem();

        if(productToDelete == null) {
            alerter.mainScreenAlert(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = productToDelete.getAllAssociatedParts();

                if(associatedParts.size() >= 1) {
                    alerter.mainScreenAlert(5);
                }
                else {
                    Inventory.deleteProduct(productToDelete);
                    productInventory.remove(productToDelete);
                }
            }
        }
    }

    /** Getter for a modified part. It is set to the selected part in the ModifyPart method.
     * @return The part being modified.*/
    public static Part getModifiedPart() {
        return modifiedPart;
    }

    /** Getter for a modified product. It is set to the selected product in the ModifyProduct method.
     * @return The product being modified.*/
    public static Product getModifiedProduct() {
        return modifiedProduct;
    }

    /** Exits the program if the user agrees to.*/
    //Asks and exits application
    public void ExitHandler(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> answer = alert.showAndWait();

        if(answer.isPresent() && answer.get() == ButtonType.OK)
        {
            System.exit(0);
        }
    }
}
