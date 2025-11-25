package com.example.testing.doubles;

import com.example.model.Employee;
import java.time.LocalDate;
import java.util.List;

public interface CalendarService {
    List<Employee> getAvailableEmployees(LocalDate startDate, LocalDate endDate);
}