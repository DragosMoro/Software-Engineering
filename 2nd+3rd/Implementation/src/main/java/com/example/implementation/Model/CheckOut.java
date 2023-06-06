package com.example.implementation.Model;

public class CheckOut extends Entity<Integer>{
    private String checkoutDate;

    private Employee employee;

    public CheckOut(String checkoutDate, Employee employee) {
        this.checkoutDate = checkoutDate;
        this.employee = employee;
    }

    public CheckOut() {
    }
    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
