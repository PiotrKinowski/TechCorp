package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;

public class PromotionService {

    public PromotionService() {
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

    public boolean isValidRaise(Employee employee, double percentage) {
        if (employee == null || percentage <= 0) {return false;}

        double newSalary = employee.getSalary() + employee.getSalary()*percentage;
        double maxSalary = employee.getPosition().getHierarchyLevel() + 4000;

        if (newSalary > maxSalary) {return false;}
        return true;
    }
}