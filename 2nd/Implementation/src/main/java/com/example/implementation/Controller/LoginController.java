package com.example.implementation.Controller;

import com.example.implementation.Main;
import com.example.implementation.Model.Boss;
import com.example.implementation.Model.Employee;
import com.example.implementation.Service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;

public class LoginController {
    @FXML
    public CheckBox bossCheckbox;


    @FXML
    public AnchorPane bossLoginAnchorPane;

    @FXML
    public AnchorPane bossOrEmployeeAnchorPane;

    
    @FXML
    public CheckBox employeeCheckbox;

 

    @FXML
    public AnchorPane employeeLoginAnchorPane;


    @FXML
    public Button goToLoginButton;
    public TextField bossTextField;
    public TextField bossPasswordField;
    public TextField employeeTextField;
    public TextField employeePasswordField;


    private Service service;

    public void setService(Service service){
        this.service=service;
    }

    public void initialize(){
        bossCheckbox.setSelected(false);
        employeeCheckbox.setSelected(false);
        bossLoginAnchorPane.setVisible(false);
        employeeLoginAnchorPane.setVisible(false);
        bossOrEmployeeAnchorPane.setVisible(true);

    }


    @FXML
    public void onGoToLoginButtonClickAction() {
        if(bossCheckbox.isSelected() && employeeCheckbox.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select only one type of user!");
            alert.show();
        }
        else if(bossCheckbox.isSelected()){
            bossOrEmployeeAnchorPane.setVisible(false);
            employeeLoginAnchorPane.setVisible(false);
            bossLoginAnchorPane.setVisible(true);
        }
        else if(employeeCheckbox.isSelected()){
            bossLoginAnchorPane.setVisible(false);
            bossOrEmployeeAnchorPane.setVisible(false);
            employeeLoginAnchorPane.setVisible(true);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a type of user!");
            alert.show();
        }

    }
    @FXML
    public void onBossLoginButtonAction() {
        if(bossTextField.getText().isEmpty() || bossPasswordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete all fields!");
            alert.show();
        }
        else{
            String email = bossTextField.getText();
            String password = bossPasswordField.getText();
            try{
                Boss boss = service.getBossByEmailAndPassword(email, password);
                changeToBossView(boss);
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        }


    }


    @FXML
    public void onEmployeeLoginButtonAction() {
        if(employeeTextField.getText().isEmpty() || employeePasswordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete all fields!");
            alert.show();
        }
        else{
            String email = employeeTextField.getText();
            String password = employeePasswordField.getText();
            try{
                Employee employee = service.getEmployeeByEmailAndPassword(email, password);
                changeToCheckInView(employee);
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        }

    }
    @FXML
    public void onBossBackLabelClickAction() {
        backAction();
    }
    @FXML
    public void onEmployeeBackLabelClickAction() {

        backAction();
    }
    private void changeToBossView(Boss boss) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("boss_view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            BossController controller = fxmlLoader.getController();
            controller.setService(service,boss);
            Stage currentStage = (Stage) bossLoginAnchorPane.getScene().getWindow();
            currentStage.setScene(scene);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void changeToCheckInView(Employee employee)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("checkin_view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            CheckInController controller = fxmlLoader.getController();
            controller.setService(service,employee);
            Stage currentStage = (Stage) employeeLoginAnchorPane.getScene().getWindow();
            currentStage.setScene(scene);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }
    private void backAction()
    {
        bossCheckbox.setSelected(false);
        employeeCheckbox.setSelected(false);
        bossLoginAnchorPane.setVisible(false);
        employeeLoginAnchorPane.setVisible(false);
        bossOrEmployeeAnchorPane.setVisible(true);
    }
}
