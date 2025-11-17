package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionServiceTest {
    @BeforeEach
    public void setUp() {
        EmployeeService employeeService = new EmployeeService();
        PromotionService promotionService = new PromotionService(employeeService);
    }

    @ParameterizedTest
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
    void testPromoteEmplyee_Success(Position position, Position newPosition, boolean success) {
        // Arrange
        Employee employee = new Employee("Imie", "Nazwisko", "inazwisko@test.com",
                "Firma", position, position.getBaseSalary());

        // Act
        boolean result = promotionService.isValidPromotion(employee, newPosition);

        // Assert
        assertEquals(success, result);
    }
}
