package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.List;

public interface Formatter {
    String format(List<Employee> employees, String format);
}