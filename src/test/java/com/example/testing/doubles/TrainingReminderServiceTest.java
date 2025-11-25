package com.example.testing.doubles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingReminderServiceTest {

    @Test
    void shouldSendRemindersForExpiringCertificates() {
        // Arrange
        CertificateRepository certificateStub = new CertificateRepositoryStub();
        CommunicationServiceSpy communicationSpy = new CommunicationServiceSpy();
        LoggerDummy loggerDummy = new LoggerDummy(); // Dummy - nieużywany

        TrainingReminderService service = new TrainingReminderService(certificateStub, communicationSpy);

        // Act
        service.sendExpirationReminders();

        // Assert
        assertAll(
                () -> assertEquals(2, communicationSpy.getSentRemindersCount()),
                () -> assertTrue(communicationSpy.wasReminderSentTo("jan@example.com")),
                () -> assertTrue(communicationSpy.wasReminderSentTo("anna@example.com"))
        );
    }

    @Test
    void shouldSendCorrectNumberOfReminders() {
        // Arrange
        CertificateRepository certificateStub = new CertificateRepositoryStub();
        CommunicationServiceMock communicationMock = new CommunicationServiceMock(2); // Oczekujemy 2 wywołania

        TrainingReminderService service = new TrainingReminderService(certificateStub, communicationMock);

        // Act
        service.sendExpirationReminders();

        // Assert
        communicationMock.verify();
    }
}