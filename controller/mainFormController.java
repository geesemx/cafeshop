/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeshopmanagementsystem.controller;

import cafeshopmanagementsystem.model.LocalCafeStore;
import cafeshopmanagementsystem.model.customersData;
import cafeshopmanagementsystem.model.data;
import cafeshopmanagementsystem.model.productData;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class mainFormController implements Initializable {
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Label username;
    
    @FXML
    private Button dashboard_btn;
    
    @FXML
    private Button inventory_btn;
    
    @FXML
    private Button menu_btn;
    
    @FXML
    private Button customers_btn;
    
    @FXML
    private Button logout_btn;
    
    @FXML
    private AnchorPane inventory_form;
    
    @FXML
    private TableView<productData> inventory_tableView;
    
    @FXML
    private TableColumn<productData, String> inventory_col_productID;
    
    @FXML
    private TableColumn<productData, String> inventory_col_productName;
    
    @FXML
    private TableColumn<productData, String> inventory_col_type;
    
    @FXML
    private TableColumn<productData, String> inventory_col_stock;
    
    @FXML
    private TableColumn<productData, String> inventory_col_price;
    
    @FXML
    private TableColumn<productData, String> inventory_col_status;
    
    @FXML
    private TableColumn<productData, String> inventory_col_date;
    
    @FXML
    private ImageView inventory_imageView;
    
    @FXML
    private Button inventory_importBtn;
    
    @FXML
    private Button inventory_addBtn;
    
    @FXML
    private Button inventory_updateBtn;
    
    @FXML
    private Button inventory_clearBtn;
    
    @FXML
    private Button inventory_deleteBtn;
    
    @FXML
    private TextField inventory_productID;
    
    @FXML
    private TextField inventory_productName;
    
    @FXML
    private TextField inventory_stock;
    
    @FXML
    private TextField inventory_price;
    
    @FXML
    private ComboBox<?> inventory_status;
    
    @FXML
    private ComboBox<?> inventory_type;
    
    @FXML
    private AnchorPane menu_form;
    
    @FXML
    private ScrollPane menu_scrollPane;
    
    @FXML
    private GridPane menu_gridPane;
    
    @FXML
    private TableView<productData> menu_tableView;
    
    @FXML
    private TableColumn<productData, String> menu_col_productName;
    
    @FXML
    private TableColumn<productData, String> menu_col_quantity;
    
    @FXML
    private TableColumn<productData, String> menu_col_price;
    
    @FXML
    private Label menu_total;
    
    @FXML
    private TextField menu_amount;
    
    @FXML
    private Label menu_change;
    
    @FXML
    private Button menu_payBtn;
    
    @FXML
    private Button menu_removeBtn;
    
    @FXML
    private Button menu_receiptBtn;
    
    @FXML
    private AnchorPane dashboard_form;
    
    @FXML
    private AnchorPane customers_form;
    
    @FXML
    private TableView<customersData> customers_tableView;
    
    @FXML
    private TableColumn<customersData, String> customers_col_customerID;
    
    @FXML
    private TableColumn<customersData, String> customers_col_total;
    
    @FXML
    private TableColumn<customersData, String> customers_col_date;
    
    @FXML
    private TableColumn<customersData, String> customers_col_cashier;
    
    @FXML
    private Label dashboard_NC;
    
    @FXML
    private Label dashboard_TI;
    
    @FXML
    private Label dashboard_TotalI;
    
    @FXML
    private Label dashboard_NSP;
    
    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;
    
    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;
    
    private Alert alert;
    
    
    private Image image;
    
    private ObservableList<productData> cardListData = FXCollections.observableArrayList();
    
    public void dashboardDisplayNC() {
        dashboard_NC.setText(String.valueOf(LocalCafeStore.receipts.size()));
    }

    public void dashboardDisplayTI() {
        double todayTotal = 0;
        Date today = new Date();
        for (customersData receipt : LocalCafeStore.receipts) {
            if (receipt.getDate().equals(today)) todayTotal += receipt.getTotal();
        }
        dashboard_TI.setText("$" + todayTotal);
    }

    public void dashboardTotalI() {
        double total = 0;
        for (customersData receipt : LocalCafeStore.receipts) total += receipt.getTotal();
        dashboard_TotalI.setText("$" + total);
    }

    public void dashboardNSP() {
        int quantity = 0;
        for (productData item : LocalCafeStore.order) quantity += item.getQuantity();
        dashboard_NSP.setText(String.valueOf(quantity));
    }

    public void dashboardIncomeChart() {
        dashboard_incomeChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Ingresos");
        double total = 0;
        for (customersData receipt : LocalCafeStore.receipts) total += receipt.getTotal();
        if (!LocalCafeStore.receipts.isEmpty()) {
            series.getData().add(new XYChart.Data<String, Number>("Ventas", total));
        }
        ((java.util.List) dashboard_incomeChart.getData()).add(series);
    }

    public void dashboardCustomerChart() {
        dashboard_CustomerChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Clientes");
        series.getData().add(new XYChart.Data<String, Number>("Clientes", LocalCafeStore.receipts.size()));
        ((java.util.List) dashboard_CustomerChart.getData()).add(series);
    }

    public void inventoryAddBtn() {
        if (inventory_productID.getText().trim().isEmpty() || inventory_productName.getText().trim().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null) {
            showMessage(AlertType.ERROR, "Please fill all required fields."); return;
        }
        for (productData product : LocalCafeStore.products) {
            if (product.getProductId().equals(inventory_productID.getText().trim())) {
                showMessage(AlertType.ERROR, "That product ID is already in use."); return;
            }
        }
        try {
            int stock = Integer.parseInt(inventory_stock.getText());
            double price = Double.parseDouble(inventory_price.getText());
            LocalCafeStore.products.add(new productData(LocalCafeStore.nextId(), inventory_productID.getText().trim(),
                    inventory_productName.getText().trim(), (String) inventory_type.getSelectionModel().getSelectedItem(),
                    stock, price, (String) inventory_status.getSelectionModel().getSelectedItem(),
                    data.path == null ? "" : data.path, new java.sql.Date(System.currentTimeMillis())));
            inventoryShowData(); inventoryClearBtn(); menuDisplayCard();
            showMessage(AlertType.INFORMATION, "Product added for this session.");
        } catch (NumberFormatException ex) { showMessage(AlertType.ERROR, "Stock and price must be valid numbers."); }
    }

    public void inventoryUpdateBtn() {
        productData selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { showMessage(AlertType.ERROR, "Select a product first."); return; }
        try {
            if (inventory_productID.getText().trim().isEmpty() || inventory_productName.getText().trim().isEmpty()
                    || inventory_type.getSelectionModel().getSelectedItem() == null
                    || inventory_status.getSelectionModel().getSelectedItem() == null) {
                showMessage(AlertType.ERROR, "Please fill all required fields."); return;
            }
            for (productData product : LocalCafeStore.products) {
                if (product != selected && product.getProductId().equals(inventory_productID.getText().trim())) {
                    showMessage(AlertType.ERROR, "That product ID is already in use."); return;
                }
            }
            selected.setProductId(inventory_productID.getText().trim());
            selected.setProductName(inventory_productName.getText().trim());
            selected.setType((String) inventory_type.getSelectionModel().getSelectedItem());
            selected.setStock(Integer.parseInt(inventory_stock.getText()));
            selected.setPrice(Double.parseDouble(inventory_price.getText()));
            selected.setStatus((String) inventory_status.getSelectionModel().getSelectedItem());
            selected.setImage(data.path == null ? "" : data.path);
            if (selected.getStock() > 0 && "Unavailable".equals(selected.getStatus())) selected.setStatus("Available");
            if (selected.getStock() == 0) selected.setStatus("Unavailable");
            inventoryShowData(); inventoryClearBtn(); menuDisplayCard();
            showMessage(AlertType.INFORMATION, "Product updated for this session.");
        } catch (RuntimeException ex) { showMessage(AlertType.ERROR, "Check the product fields and numeric values."); }
    }
    public void inventoryDeleteBtn() {
        productData selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { showMessage(AlertType.ERROR, "Select a product first."); return; }
        Alert confirm = new Alert(AlertType.CONFIRMATION, "Delete " + selected.getProductName() + "?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            LocalCafeStore.products.remove(selected); inventoryShowData(); inventoryClearBtn(); menuDisplayCard();
        }
    }
    public void inventoryClearBtn() {
        
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);
        
    }

    // LETS MAKE A BEHAVIOR FOR IMPORT BTN FIRST
    public void inventoryImportBtn() {
        
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));
        
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        
        if (file != null) {
            
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);
            
            inventory_imageView.setImage(image);
        }
    }

    // MERGE ALL DATAS
    public ObservableList<productData> inventoryDataList() {
        return FXCollections.observableArrayList(LocalCafeStore.products);
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> inventoryListData;
    
    public void inventoryShowData() {
        inventoryListData = inventoryDataList();
        
        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        inventory_tableView.setItems(inventoryListData);
        
    }
    
    public void inventorySelectData() {
        
        productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        if (prodData == null) return;
        
        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        
        data.path = prodData.getImage();
        
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();
        
        if (prodData.getImage() != null && !prodData.getImage().isEmpty()) {
            image = new Image(new File(prodData.getImage()).toURI().toString(), 120, 127, false, true);
            inventory_imageView.setImage(image);
        } else inventory_imageView.setImage(null);
    }
    
    private String[] typeList = {"Meals", "Drinks"};
    
    public void inventoryTypeList() {
        
        List<String> typeL = new ArrayList<>();
        
        for (String data : typeList) {
            typeL.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }
    
    private String[] statusList = {"Available", "Unavailable"};
    
    public void inventoryStatusList() {
        
        List<String> statusL = new ArrayList<>();
        
        for (String data : statusList) {
            statusL.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
        
    }
    
    public ObservableList<productData> menuGetData() {
        return FXCollections.observableArrayList(LocalCafeStore.products);
    }
    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        
        for (int q = 0; q < cardListData.size(); q++) {
            
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("../view/cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                cardC.setMainController(this);
                
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                
                menu_gridPane.add(pane, column++, row);
                
                GridPane.setMargin(pane, new Insets(10));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private int cID;
    private int getid;
    private double totalP;
    private double amount;
    private double change;
    private ObservableList<productData> menuOrderListData;
    private ObservableList<customersData> customersListData;

    public ObservableList<productData> menuGetOrder() {
        customerID();
        return FXCollections.observableArrayList(LocalCafeStore.order);
    }

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<productData, String>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<productData, String>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<productData, String>("price"));
        menu_tableView.setItems(menuOrderListData);
        menuGetTotal();
    }

    public void menuSelectOrder() {
        productData selected = menu_tableView.getSelectionModel().getSelectedItem();
        getid = selected == null ? 0 : selected.getId();
    }

    public void menuGetTotal() {
        totalP = 0;
        for (productData item : LocalCafeStore.order) totalP += item.getPrice();
        menu_total.setText("$" + totalP);
    }

    public void menuDisplayTotal() { menuGetTotal(); }

    public void menuAmount() {
        menuGetTotal();
        try {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                showMessage(AlertType.ERROR, "Amount must cover the order total.");
                menu_change.setText("$0.0");
            } else {
                change = amount - totalP;
                menu_change.setText("$" + change);
            }
        } catch (NumberFormatException ex) { showMessage(AlertType.ERROR, "Enter a valid amount."); }
    }

    public void menuPayBtn() {
        menuGetTotal();
        if (totalP <= 0 || amount < totalP) {
            showMessage(AlertType.ERROR, "Choose an order and enter enough cash first."); return;
        }
        Alert confirm = new Alert(AlertType.CONFIRMATION, "Complete this sale for $" + totalP + "?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            customerID();
            LocalCafeStore.receipts.add(new customersData(LocalCafeStore.receipts.size() + 1, cID, totalP,
                    new java.sql.Date(System.currentTimeMillis()), data.username));
            LocalCafeStore.order.clear();
            menuRestart(); menuShowOrderData();
            dashboardDisplayNC(); dashboardTotalI(); dashboardDisplayTI(); dashboardCustomerChart(); dashboardIncomeChart();
            customersShowData();
            showMessage(AlertType.INFORMATION, "Sale completed for this session.");
        }
    }

    public void menuRemoveBtn() {
        productData selected = menu_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { showMessage(AlertType.ERROR, "Select an order to remove."); return; }
        LocalCafeStore.order.remove(selected); menuShowOrderData(); menuGetTotal();
    }

    public void menuReceiptBtn() {
        menuGetTotal();
        if (LocalCafeStore.order.isEmpty()) { showMessage(AlertType.ERROR, "Please order first."); return; }
        StringBuilder receiptText = new StringBuilder("Receipt\n\n");
        for (productData item : LocalCafeStore.order) {
            receiptText.append(item.getProductName()).append(" x ").append(item.getQuantity())
                    .append("  $ ").append(item.getPrice()).append("\n");
        }
        receiptText.append("\nTotal: $ ").append(totalP);
        Alert receipt = new Alert(AlertType.INFORMATION);
        receipt.setTitle("Receipt"); receipt.setHeaderText(null); receipt.setContentText(receiptText.toString());
        receipt.showAndWait();
    }

    public void menuRestart() {
        totalP = 0; change = 0; amount = 0;
        menu_total.setText("$0.0"); menu_amount.clear(); menu_change.setText("$0.0");
    }

    public void customerID() {
        if (cID == 0) cID = LocalCafeStore.nextCustomerId();
        data.cID = cID;
    }

    public ObservableList<customersData> customersDataList() {
        return FXCollections.observableArrayList(LocalCafeStore.receipts);
    }

    public void customersShowData() {
        customersListData = customersDataList();
        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<customersData, String>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<customersData, String>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<customersData, String>("date"));
        customers_col_cashier.setCellValueFactory(new PropertyValueFactory<customersData, String>("emUsername"));
        customers_tableView.setItems(customersListData);
    }

    private void showMessage(AlertType type, String message) {
        Alert dialog = new Alert(type);
        dialog.setTitle(type == AlertType.ERROR ? "Error" : "Cafe Shop Management System");
        dialog.setHeaderText(null); dialog.setContentText(message); dialog.showAndWait();
    }

    public void switchForm(ActionEvent event) {
        
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            
            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();
            
        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            
            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);
            
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);
            
            customersShowData();
        }
        
    }
// LETS PROCEED TO OUR DASHBOARD FORM : )

    public void logout() {
        
        try {
            
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            if (alert.showAndWait().orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                Parent root = FXMLLoader.load(getClass().getResource("../view/FXMLDocument.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.setTitle("Cafe Shop Management System");
                
                stage.setScene(scene);
                stage.show();
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void displayUsername() {
        String user = data.username == null || data.username.trim().isEmpty() ? "Guest" : data.username.trim();
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        displayUsername();
        
        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();
        
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        
        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        
        customersShowData();
        
    }
    
}


// THATS IT FOR THIS VIDEO, THANKS FOR WATCHING!
// SUBSCRIBE OUR CHANNEL FOR MORE UNIQUE TUTORIALS 
// THANK YOU!
