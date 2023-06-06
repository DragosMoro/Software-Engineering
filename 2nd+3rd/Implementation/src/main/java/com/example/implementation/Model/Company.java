package com.example.implementation.Model;

public class Company extends Entity<Integer>{
    private String name;

    public Company(String name) {
        this.name = name;
    }
    public Company() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
