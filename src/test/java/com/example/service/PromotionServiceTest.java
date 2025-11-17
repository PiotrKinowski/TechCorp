package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionServiceTest {

    private PromotionService promotionService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        promotionService = new PromotionService(employeeService);
    }

    @ParameterizedTest(name = "Czy awans z {0} do {1} powinien być możliwy: {2}")
    @CsvSource({
            "STAZYSTA, PROGRAMISTA, true",
            "PROGRAMISTA, MANAGER, true",
            "MANAGER, WICEPREZES, true",
            "WICEPREZES, PREZES, true",
            "STAZYSTA, MANAGER, false",
            "PROGRAMISTA, PREZES, false",
            "PREZES, WICEPREZES, false",
            "MANAGER, PROGRAMISTA, false"
    })
    void isValidPromotion_ShouldValidateHierarchy(Position current, Position target, boolean expected) {
        // Arrange
        Employee employee = new Employee("Test", "User", "test@company.com",
                "Company", current, current.getBaseSalary());

        // Act
        boolean result = promotionService.isValidPromotion(employee, target);

        // Assert
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "Pensja po podwyżce {0}% z {1} powinna wzrosnąć do {2}")
    @CsvSource({
            "10, 8000, 8800",
            "15, 10000, 11500",
            "5, 5000, 5250",
            "0, 7500, 7500",
            "25, 12000, 15000"
    })
    void calculateRaise_ShouldApplyPercentageCorrectly(double percentage, double currentSalary, double expected) {
        // Arrange
        double result = promotionService.calculateRaise(currentSalary, percentage);

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"STAZYSTA", "PROGRAMISTA", "MANAGER", "WICEPREZES"})
    void getNextPosition_ShouldReturnValidNextPosition(Position current) {
        // Arrange
        Position next = promotionService.getNextPosition(current);

        // Assert
        assertEquals(current.getHierarchyLevel() - 1, next.getHierarchyLevel());
    }
}