package com.example.service;

import java.util.ArrayList;
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
        if (email == null || grade < 1 || grade > 5) {
            return false;
        }

        if (!gradesHistory.containsKey(email)) {
            gradesHistory.put(email, new ArrayList<>());
            gradesHistory.get(email).add(grade);
        }
        else {
            gradesHistory.get(email).add(grade);
        }
        return true;
    }


    public double calculateAverageGrade(String email) {
        List<Double> grades = gradesHistory.get(email);
        double sum = grades.stream().mapToDouble(Double::doubleValue).sum();
        double average = sum / grades.size();
        return average;
    }

    public String bestPerformingEmployee() {
        String bestEmployee = null;
        for (String email : gradesHistory.keySet()) {
            if (bestEmployee == null) {
                bestEmployee = email;
            }

            else if (calculateAverageGrade(email) > calculateAverageGrade(bestEmployee)) {
                bestEmployee = email;
            }
        }
        return bestEmployee;
    }
}
