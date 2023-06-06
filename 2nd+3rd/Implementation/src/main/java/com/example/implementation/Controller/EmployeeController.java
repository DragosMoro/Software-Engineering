package com.example.implementation.Controller;

import com.example.implementation.Main;
import com.example.implementation.Model.CheckOut;
import com.example.implementation.Model.Employee;
import com.example.implementation.Model.Task;
import com.example.implementation.Service.Service;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeController {

    @FXML
    public TableColumn<Task, Boolean> taskCompletionColumn;

    @FXML
    public TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    public TableView<Task> tasksTableView;

    @FXML
    public Label userLabel;



    ObservableList<Task> tasks = FXCollections.observableArrayList();

    private Service service;
    private Employee employee;

    public void setService(Service service, Employee employee)
    {
        this.service = service;
        this.employee = employee;
        userLabel.setText(employee.getEmail());
        initializeTasksTableView();
    }
    private void initializeTasksTableView() {
        ArrayList<Task> tasksList1 = service.getTasksByEmployee(employee);
        tasks.setAll(tasksList1);
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskCompletionColumn.setCellValueFactory(new PropertyValueFactory<>("finished"));
        tasksTableView.setItems(tasks);

    }
    @FXML
    public void onMarkTaskAsCompletedButton() {
        Task task = tasksTableView.getSelectionModel().getSelectedItem();
        if(task==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a task!");
            alert.show();
        }
        else{
            if(task.getFinished()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Task already completed!");
                alert.show();
            }
            else{
               task.setFinished(true);
                service.updateTask(task);
                initializeTasksTableView();
            }
        }

    }
    @FXML
    public void onLogOutButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        LocalDateTime checkOutTime=LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatedTime=timeFormatter.format(checkOutTime);
        CheckOut checkOut = new CheckOut(formatedTime, employee);
        service.addCheckOut(checkOut);


        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            LoginController controller = fxmlLoader.getController();
            controller.setService(service);
            Stage currentStage = (Stage) userLabel.getScene().getWindow();
            currentStage.setScene(scene);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void onRefreshButtonAction(){
        initializeTasksTableView();
    }
}
