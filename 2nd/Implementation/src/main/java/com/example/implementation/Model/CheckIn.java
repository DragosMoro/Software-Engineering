package com.example.implementation.Model;

public class CheckIn extends Entity<Integer>{
    private String checkinDate;
    private Employee employee;

    public CheckIn(String checkinDate, Employee employee) {
        this.checkinDate = checkinDate;
        this.employee = employee;
    }

    public CheckIn() {
    }
    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
