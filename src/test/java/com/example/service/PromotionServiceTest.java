package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionServiceTest {

    private PromotionService promotionService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        promotionService = new PromotionService(employeeService);
    }

    @ParameterizedTest(name = "Czy awans z {0} na {1} powinien być możliwy: {2}")
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
    void testIsValidPromotion_Success(Position position, Position newPosition, boolean success) {
        // Arrange
        Employee employee = new Employee("Imie", "Nazwisko", "inazwisko@test.com",
                "Firma", position, position.getBaseSalary());

        // Act
        boolean result = promotionService.isValidPromotion(employee, newPosition);

        // Assert
        assertEquals(success, result);
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
    void testPromoteEmployee(Position position, Position newPosition, boolean success) {
        // Arrange
        Employee employee = new Employee("Imie", "Nazwisko", "inazwisko@test.com",
                "Firma", position, position.getBaseSalary());

        // Act
        boolean result = promotionService.promoteEmployee(employee, newPosition);

        // Assert
        assertEquals(success, result);
    }
}
