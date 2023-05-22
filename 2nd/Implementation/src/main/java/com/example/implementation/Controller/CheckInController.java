package com.example.implementation.Controller;

import com.example.implementation.Main;
import com.example.implementation.Model.CheckIn;
import com.example.implementation.Model.Employee;
import com.example.implementation.Service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckInController {
   private Service service;
   private Employee employee;

    @FXML
    public Label emailLabel;

    @FXML
    public TextField timeTextField;

    @FXML
    public void onRegisterButtonClick() {
        if(timeTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter the time");
            alert.show();
            return;
        }
        String time = timeTextField.getText();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime date=LocalDateTime.now();
        String formatedDate=dateFormatter.format(date);
        String formatedTime=timeFormatter.format(date);
        if(time.compareTo(formatedTime)<=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid time");
            alert.show();
            return;
        }
        String checkinDate=formatedDate+" "+time;
        CheckIn checkIn = new CheckIn(checkinDate, employee);
        service.addCheckIn(checkIn);
        changeScene();


    }
    public void setService(Service service, Employee employee) {
        this.service = service;
        this.employee = employee;
        emailLabel.setText(employee.getEmail());
    }
    private void changeScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("employee_view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            EmployeeController controller = fxmlLoader.getController();
            controller.setService(service,employee);
            Stage currentStage = (Stage) timeTextField.getScene().getWindow();
            currentStage.setScene(scene);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }


}
