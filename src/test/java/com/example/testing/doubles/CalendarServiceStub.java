package com.example.testing.doubles;

import com.example.model.Employee;
import com.example.model.Position;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarServiceStub implements CalendarService {
    private List<Employee> availableEmployees;

    public CalendarServiceStub() {
        availableEmployees = new ArrayList<>();
        availableEmployees.add(new Employee("Jan", "Kowalski", "jan@example.com",
                "Company A", Position.PROGRAMISTA, 8000));
        availableEmployees.add(new Employee("Anna", "Nowak", "anna@example.com",
                "Company A", Position.MANAGER, 12000));
    }

    @Override
    public List<Employee> getAvailableEmployees(LocalDate startDate, LocalDate endDate) {
        // Zawsze zwraca tę samą listę pracowników zdefiniowaną powyżej
        return new ArrayList<>(availableEmployees);
    }
}