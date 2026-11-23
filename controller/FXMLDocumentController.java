/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeshopmanagementsystem.controller;

import cafeshopmanagementsystem.model.LocalCafeStore;
import cafeshopmanagementsystem.model.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author WINDOWS 10
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane si_loginForm;
    
    @FXML
    private TextField si_username;
    
    @FXML
    private PasswordField si_password;
    
    @FXML
    private Button si_loginBtn;
    
    @FXML
    private Hyperlink si_forgotPass;
    
    @FXML
    private AnchorPane su_signupForm;
    
    @FXML
    private TextField su_username;
    
    @FXML
    private PasswordField su_password;
    
    @FXML
    private ComboBox<?> su_question;
    
    @FXML
    private TextField su_answer;
    
    @FXML
    private Button su_signupBtn;
    
    @FXML
    private TextField fp_username;
    
    @FXML
    private AnchorPane fp_questionForm;
    
    @FXML
    private Button fp_proceedBtn;
    
    @FXML
    private ComboBox<?> fp_question;
    
    @FXML
    private TextField fp_answer;
    
    @FXML
    private Button fp_back;
    
    @FXML
    private AnchorPane np_newPassForm;
    
    @FXML
    private PasswordField np_newPassword;
    
    @FXML
    private PasswordField np_confirmPassword;
    
    @FXML
    private Button np_changePassBtn;
    
    @FXML
    private Button np_back;
    
    @FXML
    private AnchorPane side_form;
    
    @FXML
    private Button side_CreateBtn;
    
    @FXML
    private Button side_alreadyHave;
    
    private Alert alert;
    
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showMessage(AlertType.ERROR, "Enter a username and password.");
            return;
        }
        LocalCafeStore.Account account = LocalCafeStore.accounts.get(si_username.getText().trim());
        if (account == null || !account.password.equals(si_password.getText())) {
            showMessage(AlertType.ERROR, "Incorrect username or password.");
            return;
        }
        data.username = si_username.getText().trim();
        openMainWindow();
    }
    
    public void regBtn() {
        
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            
            String accountName = su_username.getText().trim();
            if (LocalCafeStore.accounts.containsKey(accountName)) {
                showMessage(AlertType.ERROR, accountName + " is already taken.");
            } else if (su_password.getText().length() < 8) {
                showMessage(AlertType.ERROR, "Password must contain at least 8 characters.");
            } else {
                LocalCafeStore.accounts.put(accountName, new LocalCafeStore.Account(
                        su_password.getText(),
                        (String) su_question.getSelectionModel().getSelectedItem(),
                        su_answer.getText()));
                showMessage(AlertType.INFORMATION, "Account registered for this session.");
                su_username.clear();
                su_password.clear();
                su_question.getSelectionModel().clearSelection();
                su_answer.clear();
                TranslateTransition slider = new TranslateTransition(Duration.seconds(.5), side_form);
                slider.setToX(0);
                slider.setOnFinished(e -> {
                    side_alreadyHave.setVisible(false);
                    side_CreateBtn.setVisible(true);
                });
                slider.play();
            }
        }
        
    }
    
    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "what is your birth date?"};
    
    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();
        
        for (String data : questionList) {
            listQ.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }
    
    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        
        forgotPassQuestionList();
    }
    
    public void proceedBtn() {
        
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {
            
            LocalCafeStore.Account account = LocalCafeStore.accounts.get(fp_username.getText().trim());
            if (account != null && account.question.equals(fp_question.getSelectionModel().getSelectedItem())
                    && account.answer.equals(fp_answer.getText())) {
                np_newPassForm.setVisible(true);
                fp_questionForm.setVisible(false);
            } else {
                showMessage(AlertType.ERROR, "Incorrect recovery information.");
            }
            
        }
        
    }
    
    public void changePassBtn() {
        
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            
            if (np_newPassword.getText().length() < 8) {
                showMessage(AlertType.ERROR, "Password must contain at least 8 characters.");
                return;
            }
            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                LocalCafeStore.Account account = LocalCafeStore.accounts.get(fp_username.getText().trim());
                if (account != null) {
                    account.password = np_newPassword.getText();
                    showMessage(AlertType.INFORMATION, "Password changed for this session.");
                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);
                    np_confirmPassword.clear();
                    np_newPassword.clear();
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.clear();
                    fp_username.clear();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }
        }
    }
    
    public void forgotPassQuestionList() {
        
        List<String> listQ = new ArrayList<>();
        
        for (String data : questionList) {
            listQ.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
        
    }
    
    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }
    
    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    private void showMessage(AlertType type, String message) {
        Alert dialog = new Alert(type);
        dialog.setTitle(type == AlertType.ERROR ? "Error" : "Cafe Shop Management System");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    private void openMainWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cafe Shop Management System");
            stage.setMinWidth(1100);
            stage.setMinHeight(600);
            stage.setScene(new Scene(root));
            stage.show();
            si_loginBtn.getScene().getWindow().hide();
        } catch (Exception e) {
            throw new IllegalStateException("Could not open the main window", e);
        }
    }
    
    public void switchForm(ActionEvent event) {
        
        TranslateTransition slider = new TranslateTransition();
        
        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
                
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                
                regLquestionList();
            });
            
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
                
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            
            slider.play();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
