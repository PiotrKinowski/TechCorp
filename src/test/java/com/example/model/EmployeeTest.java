package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;

    @BeforeEach
    void setUp() {
        employee1 = new Employee("Jan", "Kowalski", "j.kowalski@techcorp.pl",
                "TechCorp", Position.PROGRAMISTA, 8000);
        employee2 = new Employee("Anna", "Nowak", "a.nowak@techcorp.pl",
                "TechCorp", Position.MANAGER, 12000);
        employee3 = new Employee("Jan", "Kowalski", "j.kowalski@techcorp.pl",
                "InnaFirma", Position.PROGRAMISTA, 9000);
    }

    @Test
    @DisplayName("equals() - ten sam email")
    void testEquals_Email_True() {
        boolean result = employee1.equals(employee3);

        assertTrue(result, "Pracownicy z tym samym emailem powinni być równi");
    }

    @Test
    @DisplayName("equals() - inny email")
    void testEquals_Email_False() {
        boolean result = employee1.equals(employee2);

        assertFalse(result, "Pracownicy z różnymi emailami nie powinni być równi");
    }

    @Test
    @DisplayName("equals() - ten sam pracownik")
    void testEquals_Same() {
        boolean result = employee1.equals(employee1);

        assertTrue(result, "Ten sam obiekt powinien być równy sobie");
    }

    @Test
    @DisplayName("equals() - null")
    void testEquals_Null() {
        boolean result = employee1.equals(null);

        assertFalse(result, "Porównanie z null powinno zwrócić false");
    }

    @Test
    @DisplayName("equals() - employee i string")
    void testEquals_Class_False() {
        boolean result = employee1.equals("string object");

        assertFalse(result, "Porównanie z inną klasą powinno zwrócić false");
    }

    @Test
    @DisplayName("hashCode() - ten sam email")
    void testHashCode_True() {
        int hashCode1 = employee1.hashCode();
        int hashCode2 = employee3.hashCode();

        assertEquals(hashCode1, hashCode2, "HashCode powinien być taki sam dla tych samych emaili");
    }

    @Test
    @DisplayName("hashCode() - inny email")
    void testHashCode_False() {
        int hashCode1 = employee1.hashCode();
        int hashCode2 = employee2.hashCode();

        assertNotEquals(hashCode1, hashCode2, "HashCode powinien być różny dla różnych emaili");
    }

    @Test
    @DisplayName("toString()")
    void testToString() {
        String result = employee1.toString();

        assertAll(
                () -> assertTrue(result.contains("Jan"), "ToString powinien zawierać imię"),
                () -> assertTrue(result.contains("Kowalski"), "ToString powinien zawierać nazwisko"),
                () -> assertTrue(result.contains("j.kowalski@techcorp.pl"), "ToString powinien zawierać email"),
                () -> assertTrue(result.contains("TechCorp"), "ToString powinien zawierać firmę"),
                () -> assertTrue(result.contains("Programista"), "ToString powinien zawierać stanowisko"),
                () -> assertTrue(result.contains("8000"), "ToString powinien zawierać wynagrodzenie")
        );
    }

    @Test
    @DisplayName("Gettery i Settery")
    void testGetSet() {
        Employee employee = new Employee("Test", "User", "test@test.pl", "TestCorp", Position.STAZYSTA, 3000);

        employee.setFirstName("TestImie");
        employee.setLastName("TestNazwisko");
        employee.setEmail("test@email.pl");
        employee.setCompany("TestFirma");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(15000);

        assertAll(
                () -> assertEquals("TestImie", employee.getFirstName()),
                () -> assertEquals("TestNazwisko", employee.getLastName()),
                () -> assertEquals("test@email.pl", employee.getEmail()),
                () -> assertEquals("TestFirma", employee.getCompany()),
                () -> assertEquals(Position.MANAGER, employee.getPosition()),
                () -> assertEquals(15000, employee.getSalary())
        );
    }
}