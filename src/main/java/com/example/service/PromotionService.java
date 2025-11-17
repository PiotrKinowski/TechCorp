package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;

public class PromotionService {
    private EmployeeService employeeService;

    public PromotionService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean isValidPromotion(Employee employee, Position position) {
        if (employee == null || position == null) {return false;}
        if (employee.getPosition().getHierarchyLevel() != position.getHierarchyLevel() + 1) {return false;}
        return true;
    }

    public boolean promoteEmployee(Employee employee, Position position) {
        if(!isValidPromotion(employee, position)) {return false;}

        employee.setPosition(position);
        employee.setSalary(position.getBaseSalary());
        return true;
    }
}