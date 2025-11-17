package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;

public class PromotionService {
    private EmployeeService employeeService;

    public PromotionService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean isValidPromotion(Employee employee, Position position) {
        return true;
    }
}