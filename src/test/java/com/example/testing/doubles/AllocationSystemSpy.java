package com.example.testing.doubles;

import com.example.model.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AllocationSystemSpy implements AllocationSystem {
    // Lista zapisująca wszystkie wywołania metody allocate
    private List<AllocationRecord> allocationRecords;

    public AllocationSystemSpy() {
        allocationRecords = new ArrayList<>();
    }

    @Override
    public void allocate(Employee employee, String taskId, LocalDate startDate, LocalDate endDate) {
        allocationRecords.add(new AllocationRecord(employee, taskId, startDate, endDate));
    }

    public int getAllocationCount() {
        return allocationRecords.size();
    }

    public List<AllocationRecord> getAllAllocations() {
        return allocationRecords;
    }

    public static class AllocationRecord {
        public final Employee employee;
        public final String taskId;
        public final LocalDate startDate;
        public final LocalDate endDate;

        public AllocationRecord(Employee employee, String taskId, LocalDate startDate, LocalDate endDate) {
            this.employee = employee;
            this.taskId = taskId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}