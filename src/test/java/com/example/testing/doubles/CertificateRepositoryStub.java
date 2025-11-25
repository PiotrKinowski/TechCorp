package com.example.testing.doubles;

import com.example.model.Employee;
import com.example.model.Position;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CertificateRepositoryStub implements CertificateRepository {
    private List<Certificate> certificates;

    public CertificateRepositoryStub() {
        // Zawsze korzysta z ustalonej poniżej listy certyfikatów
        certificates = new ArrayList<>();

        Employee employee1 = new Employee("Jan", "Kowalski", "jan@example.com",
                "Company A", Position.PROGRAMISTA, 8000);
        Employee employee2 = new Employee("Anna", "Nowak", "anna@example.com",
                "Company A", Position.MANAGER, 12000);

        certificates.add(new Certificate(employee1, "BHP", LocalDate.now().plusDays(15)));
        certificates.add(new Certificate(employee2, "RODO", LocalDate.now().plusDays(25)));
        certificates.add(new Certificate(employee1, "AWS", LocalDate.now().plusDays(60))); // Nie wyśle przypomnienia
    }

    @Override
    public List<Certificate> findCertificatesExpiringInDays(int days) {
        List<Certificate> result = new ArrayList<>();
        LocalDate threshold = LocalDate.now().plusDays(days);

        for (Certificate cert : certificates) {
            if (cert.getExpirationDate().isBefore(threshold) ||
                    cert.getExpirationDate().isEqual(threshold)) {
                result.add(cert);
            }
        }
        return result;
    }
}