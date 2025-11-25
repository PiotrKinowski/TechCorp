package com.example.testing.doubles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeExportServiceTest {

    @Test
    void shouldExportEmployeesToFile() {
        // Arrange
        EmployeeRepositoryFake repositoryFake = new EmployeeRepositoryFake();
        Formatter formatterStub = new FormatterStub();
        FileSystemSpy fileSystemSpy = new FileSystemSpy();

        EmployeeExportService service = new EmployeeExportService(repositoryFake, formatterStub, fileSystemSpy);

        // Act
        boolean result = service.exportEmployees("/tmp/employees.csv", "CSV");

        // Assert
        assertAll(
                () -> assertTrue(result),
                () -> assertEquals(1, fileSystemSpy.getWriteOperationCount()),
                () -> assertEquals("PREDEFINED_DATA", fileSystemSpy.getLastWrittenContent())
        );
    }

    @Test
    void shouldExportWithCorrectParameters() {
        // Arrange
        EmployeeRepositoryFake repositoryFake = new EmployeeRepositoryFake();
        Formatter formatterStub = new FormatterStub();
        FileSystemMock fileSystemMock = new FileSystemMock("/tmp/employees.json", "PREDEFINED");

        EmployeeExportService service = new EmployeeExportService(repositoryFake, formatterStub, fileSystemMock);

        // Act
        boolean result = service.exportEmployees("/tmp/employees.json", "JSON");

        // Assert
        assertTrue(result);
        fileSystemMock.verify();
    }
}