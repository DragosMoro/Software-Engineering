package com.example.implementation.Controller;

import com.example.implementation.Model.Boss;
import com.example.implementation.Model.CheckIn;
import com.example.implementation.Model.CheckOut;
import com.example.implementation.Model.Employee;
import com.example.implementation.Repository.CheckOutRepository;
import com.example.implementation.Service.Service;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BossController {
    @FXML
    public TableColumn<CheckIn, String> checkInDateColumn;

    @FXML
    public TableView<CheckIn> employeesTableView;

    @FXML
    public TableColumn<CheckIn, String> firstNameColumn;

    @FXML
    public TableColumn<CheckIn, String> lastNameColumn;

    @FXML
    public TextArea taskDescriptionTextArea;



    private Service service;
    private Boss boss;

    public void setService(Service service, Boss boss) {
        this.service = service;
        this.boss = boss;
        NotificationService notificationService = new NotificationService(service.getCheckOutRepository());
        notificationService.start();
        initializeEmployeesTableView();
    }

    private void initializeEmployeesTableView() {
        ArrayList<CheckIn> checkInsList = service.getCheckInsByTheCurrentDay();

        ObservableList<CheckIn> checkIns = FXCollections.observableArrayList(checkInsList);

        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkinDate"));

        firstNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));

        lastNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));

        employeesTableView.setItems(checkIns);
    }


    private static class NotificationService {
        private final CheckOutRepository checkOutRepository;
        private LocalDateTime lastCheckTime;

        public NotificationService(CheckOutRepository checkOutRepository) {
            this.checkOutRepository = checkOutRepository;
            this.lastCheckTime = LocalDateTime.now();
        }

        public void start() {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::checkForNewCheckOuts, 0, 30, TimeUnit.SECONDS);
            System.out.println("Notification service started");
        }

        private void checkForNewCheckOuts() {
            System.out.println("Checking for new checkouts");
            List<CheckOut> newCheckOuts = checkOutRepository.getCheckOutsAfter(this.lastCheckTime);

            for (CheckOut checkOut : newCheckOuts) {
                // aici trimiteți notificări
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("New CheckOut");
                    alert.setHeaderText(null);
                    alert.setContentText("New CheckOut: " + checkOut.getEmployee().getEmail());
                    alert.showAndWait();
                });
            }

            if (!newCheckOuts.isEmpty()) {
                this.lastCheckTime = LocalDateTime.now();
            }
        }
    }
    @FXML
    public void onAddTaskForSelectedEmployeeAction() {
        CheckIn selectedCheckIn = employeesTableView.getSelectionModel().getSelectedItem();
        if (selectedCheckIn == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select an employee");
            alert.showAndWait();
            return;
        }

        String description = taskDescriptionTextArea.getText();
        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a description");
            alert.showAndWait();
            return;
        }

        service.addTask(description, false, boss, selectedCheckIn.getEmployee());
        taskDescriptionTextArea.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");


    }
    @FXML
    public void onRefreshButtonAction(){
        initializeEmployeesTableView();
    }

}
