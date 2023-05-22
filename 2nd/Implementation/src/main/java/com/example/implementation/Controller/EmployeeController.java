package com.example.implementation.Controller;

import com.example.implementation.Model.Employee;
import com.example.implementation.Service.Service;
import javafx.fxml.FXML;

import java.awt.*;

public class EmployeeController {


    private Service service;
    private Employee employee;

    public void setService(Service service, Employee employee)
    {
        this.service = service;
        this.employee = employee;
    }
}
