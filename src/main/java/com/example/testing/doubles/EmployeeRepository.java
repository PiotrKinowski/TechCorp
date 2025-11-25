package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();

    void addEmployee(Employee employee);
}