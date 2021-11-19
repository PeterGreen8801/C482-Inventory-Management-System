package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This is the main class that runs the program **FUTURE ENHANCEMENT example**. A future enhancement to this program can be a quick edit button to allow the user to edit a part while in the Add/Modify Product screens before they decide to add it as an associated part on a product.*/
public class Main extends Application {
    /** The start method that loads the Main Screen.*/
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root,1200,600));
        stage.show();
    }

     /** This method creates several objects to be used as test data.*/
     private static void addTestData() {
        Part p = new InHouse(1,"Brakes",12.99,15,1,2,4) ;
        Part p1 = new InHouse(2,"Tire", 14.99,15,1,20,5);
        Part p2 = new InHouse(3,"Rim",56.99,15,1,20,6);

        Product a = new Product(1,"Giant Bicycle",299.99,15,1,10);
        Product a2 = new Product(2,"Scott Bicycle",199.99,15,1,10);
        Product a3 = new Product(3,"GT Bike",99.99,15,1,10);
        Inventory.addPart(p);
        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Inventory.addProduct(a);
        Inventory.addProduct(a2);
        Inventory.addProduct(a3);
    }

    /** This is the main method in the main class **JavaDoc Location: C482_Inventory_System\src**. This method adds test data and launches the Main Screen.*/
    public static void main(String[] args){
        addTestData();
        launch(args);
    }


}
