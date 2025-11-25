package com.example.testing.doubles;

import java.util.List;

public class TrainingReminderService {
    private final CertificateRepository certificateRepository;
    private final CommunicationService communicationService;

    public TrainingReminderService(CertificateRepository certificateRepository,
                                   CommunicationService communicationService) {
        this.certificateRepository = certificateRepository;
        this.communicationService = communicationService;
    }

    public void sendExpirationReminders() {
        List<Certificate> expiringCertificates = certificateRepository.findCertificatesExpiringInDays(30);

        for (Certificate certificate : expiringCertificates) {
            String message = generateReminderMessage(certificate);
            communicationService.sendReminder(certificate.getEmployee(), message);
        }
    }

    private String generateReminderMessage(Certificate certificate) {
        return String.format("Przypomnienie: Certyfikat '%s' wygasa %s",
                certificate.getType(), certificate.getExpirationDate());
    }
}