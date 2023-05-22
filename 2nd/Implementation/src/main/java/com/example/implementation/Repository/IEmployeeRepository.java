package com.example.implementation.Repository;


import com.example.implementation.Model.Employee;

public interface IEmployeeRepository extends IRepository<Employee>{
    Employee findByEmailAndPassword(String email, String password);
}
