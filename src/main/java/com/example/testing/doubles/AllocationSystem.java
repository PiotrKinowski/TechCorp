package com.example.testing.doubles;

import com.example.model.Employee;
import java.time.LocalDate;
import java.util.List;

public interface AllocationSystem {
    void allocate(Employee employee, String taskId, LocalDate startDate, LocalDate endDate);
}