package com.example.testing.doubles;

import java.util.List;

public interface CertificateRepository {
    List<Certificate> findCertificatesExpiringInDays(int days);
}