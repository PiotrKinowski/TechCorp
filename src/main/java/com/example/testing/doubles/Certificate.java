package com.example.testing.doubles;

import com.example.model.Employee;

import java.time.LocalDate;

public class Certificate {
    private Employee employee;
    private String type;
    private LocalDate expirationDate;

    public Certificate(Employee employee, String type, LocalDate expirationDate) {
        this.employee = employee;
        this.type = type;
        this.expirationDate = expirationDate;
    }

    public Employee getEmployee() { return employee; }
    public String getType() { return type; }
    public LocalDate getExpirationDate() { return expirationDate; }
}