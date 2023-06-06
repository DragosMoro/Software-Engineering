package com.example.implementation.Model;

public class Task extends Entity<Integer> {
    private String description;
    private Boolean finished;


    private Boss boss;
    private Employee employee;

    public Task(String description, Boolean finished, Boss boss, Employee employee) {
        this.description = description;
        this.finished = finished;
        this.boss = boss;
        this.employee = employee;
    }
    public Task() {
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean isFinished) {
        this.finished = isFinished;
    }

}