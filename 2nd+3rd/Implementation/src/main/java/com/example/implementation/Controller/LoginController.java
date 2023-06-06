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
    public TextField emailTextField;

    @FXML
    public AnchorPane loginAnchorPane;

    @FXML
    public PasswordField passwordField;

    private Service service;

    public void setService(Service service){
        this.service=service;
    }



    @FXML
    public void onLoginButtonAction() {
        if(emailTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete all fields!");
            alert.show();
        }
        else{
            String email = emailTextField.getText();
            String password = passwordField.getText();
            try{
                Boss boss = service.getBossByEmailAndPassword(email, password);
                Employee employee = service.getEmployeeByEmailAndPassword(email, password);
                if(boss==null && employee==null){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password!");
                    alert.show();
                }
                else if(boss!=null){
                    changeToBossView(boss);
                }
                else{
                    changeToCheckInView(employee);
                }
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        }


    }

    private void changeToBossView(Boss boss) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("boss_view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 400);
            BossController controller = fxmlLoader.getController();
            controller.setService(service,boss);
            Stage currentStage = (Stage) loginAnchorPane.getScene().getWindow();
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
            Stage currentStage = (Stage) loginAnchorPane.getScene().getWindow();
            currentStage.setScene(scene);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }
}
