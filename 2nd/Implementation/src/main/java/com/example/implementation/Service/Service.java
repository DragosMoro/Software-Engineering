package com.example.implementation.Service;

import com.example.implementation.Model.Boss;
import com.example.implementation.Model.CheckIn;
import com.example.implementation.Model.Employee;
import com.example.implementation.Repository.*;
import org.hibernate.SessionFactory;

public class Service {
    private static Service instance;
    private static SessionFactory sessionFactory;
    private final ITaskRepository taskRepository;
    private final ICompanyRepository companyRepository;
    private final IEmployeeRepository employeeRepository;
    private final IBossRepository bossRepository;
    private final ICheckInRepository checkInRepository;

    private Service(ITaskRepository taskRepository, ICompanyRepository companyRepository, IEmployeeRepository employeeRepository, IBossRepository bossRepository, ICheckInRepository checkInRepository) {
        this.taskRepository = taskRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.bossRepository = bossRepository;
        this.checkInRepository = checkInRepository;
    }
    public static Service getInstance(SessionFactory sessionFactory){
        if (instance == null)
        {
            instance = new Service(new TaskRepository(sessionFactory), new CompanyRepository(sessionFactory), new EmployeeRepository(sessionFactory), new BossRepository(sessionFactory), new CheckInRepository(sessionFactory));
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
}
