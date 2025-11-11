package com.example.model;

import java.util.Comparator;

public class LastNameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee emp1, Employee emp2) {
        return emp1.getLastName().compareTo(emp2.getLastName());
    }
}