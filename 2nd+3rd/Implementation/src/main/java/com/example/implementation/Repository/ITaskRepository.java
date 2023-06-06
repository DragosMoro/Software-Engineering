package com.example.implementation.Repository;

import com.example.implementation.Model.Employee;
import com.example.implementation.Model.Task;

import java.util.ArrayList;

public interface ITaskRepository extends IRepository<Task>{

    ArrayList<Task> getTasksByEmployee(Employee employee);
}
