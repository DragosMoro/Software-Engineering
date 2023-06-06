package com.example.implementation.Service;

import com.example.implementation.Model.*;
import com.example.implementation.Repository.*;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Service {
    private static Service instance;
    private static SessionFactory sessionFactory;
    private final ITaskRepository taskRepository;
    private final ICompanyRepository companyRepository;
    private final IEmployeeRepository employeeRepository;
    private final IBossRepository bossRepository;
    private final ICheckInRepository checkInRepository;
    private final ICheckOutRepository checkOutRepository;

    private Service(ITaskRepository taskRepository, ICompanyRepository companyRepository, IEmployeeRepository employeeRepository, IBossRepository bossRepository, ICheckInRepository checkInRepository, ICheckOutRepository checkOutRepository) {
        this.taskRepository = taskRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.bossRepository = bossRepository;
        this.checkInRepository = checkInRepository;
        this.checkOutRepository = checkOutRepository;
    }
    public static Service getInstance(SessionFactory sessionFactory){
        if (instance == null)
        {
            instance = new Service(new TaskRepository(sessionFactory), new CompanyRepository(sessionFactory), new EmployeeRepository(sessionFactory), new BossRepository(sessionFactory), new CheckInRepository(sessionFactory), new CheckOutRepository(sessionFactory));
        }
        return instance;
    }

    public Boss getBossByEmailAndPassword(String email, String password) {
        return bossRepository.findByEmailAndPassword(email, password);
    }

    public Employee getEmployeeByEmailAndPassword(String email, String password) {
        return employeeRepository.findByEmailAndPassword(email, password);
    }



    public void addCheckIn(CheckIn checkIn) {
        checkInRepository.add(checkIn);
    }


    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public ArrayList<Task> getTasksByEmployee(Employee employee) {
        return taskRepository.getTasksByEmployee(employee);
    }

    public CheckOutRepository getCheckOutRepository() {
        return (CheckOutRepository) checkOutRepository;
    }
    public void addCheckOut(CheckOut checkOut) {
        checkOutRepository.add(checkOut);
    }

    public ArrayList<CheckIn> getCheckInsByTheCurrentDay() {
        ArrayList<CheckIn> checkIns = (ArrayList<CheckIn>) checkInRepository.getAll();
        Map<Employee, CheckIn> latestCheckInByEmployee = new HashMap<>();

        for (CheckIn checkIn : checkIns) {
            String checkinDate = checkIn.getCheckinDate();
            String[] checkinDateArray = checkinDate.split(" ");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime date = LocalDateTime.now();
            String formattedDate = dateFormatter.format(date);

            if (checkinDateArray[0].equals(formattedDate)) {
                latestCheckInByEmployee.put(checkIn.getEmployee(), checkIn);
            }
        }

        ArrayList<CheckIn> checkInsByTheCurrentDay = new ArrayList<>(latestCheckInByEmployee.values());
        return checkInsByTheCurrentDay;

    }


    public void addTask(String description, boolean b, Boss boss, Employee employee) {
        Task task = new Task(description, b, boss, employee);
        taskRepository.add(task);
    }
}
