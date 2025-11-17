package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionServiceTest {

    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        promotionService = new PromotionService();
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

    @ParameterizedTest(name = "Czy powinien zostać awansowany z {0} na {1}: {2}")
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

    @ParameterizedTest
    @CsvSource({
            "STAZYSTA, PROGRAMISTA, 8000",
            "PROGRAMISTA, MANAGER, 12000",
            "MANAGER, WICEPREZES, 18000",
            "WICEPREZES, PREZES, 25000"
    })
    void testPromoteEmployee_Salary(Position position, Position newPosition, double expectedSalary) {
        // Arrange
        Employee employee = new Employee("Imie", "Nazwisko", "inazwisko@test.com",
                "Firma", position, position.getBaseSalary());

        // Act
        promotionService.promoteEmployee(employee, newPosition);
        double result = employee.getSalary();

        // Assert
        assertEquals(expectedSalary, result);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 9000, true",
            "25, 8000, true",
            "40, 8000, true",
            "50, 8000, false",
            "25, 10000, false"
    })
    void testGiveRaise(int percentage, double currentSalary, boolean success) {
        // Arrange
        Employee employee = new Employee("Imie", "Nazwisko", "inazwisko@test.com",
                "Firma", Position.PROGRAMISTA, currentSalary);

        // Act
        promotionService.isValidRaise(employee, percentage);
        double result = employee.getSalary();

        // Assert
        assertEquals(success, result);
    }
}
