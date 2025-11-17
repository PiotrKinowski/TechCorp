package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradingService {
    private EmployeeService employeeService;
    private Map<String, List<Double>> gradesHistory;

    public GradingService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.gradesHistory = new HashMap<>();
    }

    public boolean addGrade(String email, double grade) {
        return true;
    }


    public double calculateAverageGrade(String email) {
        return 0;
    }
}
