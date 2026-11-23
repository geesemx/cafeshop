/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeshopmanagementsystem.controller;

import cafeshopmanagementsystem.model.LocalCafeStore;
import cafeshopmanagementsystem.model.productData;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author WINDOWS 10
 */
public class cardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Spinner<Integer> prod_spinner;

    @FXML
    private Button prod_addBtn;

    private productData prodData;
    private Image image;


    private SpinnerValueFactory<Integer> spin;

    private mainFormController mainController;


    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_name.setText(prodData.getProductName());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        if (prodData.getImage() != null && !prodData.getImage().isEmpty()) {
            image = new Image(new java.io.File(prodData.getImage()).toURI().toString(), 190, 94, false, true);
            prod_imageView.setImage(image);
        } else {
            prod_imageView.setImage(null);
        }
        pr = prodData.getPrice();

    }

    public void setMainController(mainFormController mainController) {
        this.mainController = mainController;
    }
    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    private double pr;

    public void addBtn() {
        qty = prod_spinner.getValue() == null ? 0 : prod_spinner.getValue();
        if (qty <= 0) { showMessage(AlertType.ERROR, "Choose a quantity greater than zero."); return; }
        if (!"Available".equals(prodData.getStatus())) { showMessage(AlertType.ERROR, "This product is unavailable."); return; }
        if (prodData.getStock() < qty) { showMessage(AlertType.ERROR, "There is not enough stock."); return; }
        prodData.setStock(prodData.getStock() - qty);
        if (prodData.getStock() == 0) prodData.setStatus("Unavailable");
        LocalCafeStore.order.add(new productData(LocalCafeStore.nextId(), prodData.getProductId(),
                prodData.getProductName(), prodData.getType(), qty, qty * pr, prodData.getImage(), prodData.getDate()));
        showMessage(AlertType.INFORMATION, "Added to the order.");
        if (mainController != null) {
            mainController.menuShowOrderData();
            mainController.menuGetTotal();
            mainController.dashboardNSP();
        }
    }

    private void showMessage(AlertType type, String message) {
        Alert dialog = new Alert(type);
        dialog.setTitle(type == AlertType.ERROR ? "Error" : "Cafe Shop Management System");
        dialog.setHeaderText(null); dialog.setContentText(message); dialog.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }

}
