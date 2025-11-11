package com.example.model;

public class CompanyStatistics {
    private int employeeCount;
    private double averageSalary;
    private String highestPaidEmployee;

    public CompanyStatistics(int employeeCount, double averageSalary, String highestPaidEmployee) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.highestPaidEmployee = highestPaidEmployee;
    }

    public int getEmployeeCount() { return employeeCount; }
    public double getAverageSalary() { return averageSalary; }
    public String getHighestPaidEmployee() { return highestPaidEmployee; }

    @Override
    public String toString() {
        return String.format("Pracowników: %d, Średnia pensja: %.2f PLN, Najlepiej zarabia: %s",
                employeeCount, averageSalary, highestPaidEmployee);
    }
}