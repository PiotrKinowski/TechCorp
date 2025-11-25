package com.example.testing.doubles;

import java.util.ArrayList;
import java.util.List;

public class FileSystemMock implements FileSystem {
    private List<WriteOperation> writeOperations;
    private String expectedFilePath;
    private String expectedContentPrefix;

    // Sztuczny system plików
    public FileSystemMock(String expectedFilePath, String expectedContentPrefix) {
        this.writeOperations = new ArrayList<>();
        this.expectedFilePath = expectedFilePath;
        this.expectedContentPrefix = expectedContentPrefix;
    }

    @Override
    public void write(String filePath, String content) {
        writeOperations.add(new WriteOperation(filePath, content));
    }

    // Weryfikuje czy zapis został wykonany z oczekiwanymi parametrami. Sprawdza ścieżkę i zawartość pliku
    public void verify() {
        if (writeOperations.size() != 1) {
            throw new AssertionError("Oczekiwano dokładnie jednej operacji zapisu");
        }

        WriteOperation operation = writeOperations.get(0);

        if (!expectedFilePath.equals(operation.filePath)) {
            throw new AssertionError(
                    String.format("Oczekiwano ścieżki '%s', a otrzymano '%s'", expectedFilePath, operation.filePath)
            );
        }

        if (!operation.content.startsWith(expectedContentPrefix)) {
            throw new AssertionError(
                    String.format("Zawartość pownna zaczynać się od '%s'", expectedContentPrefix)
            );
        }
    }

    public static class WriteOperation {
        public final String filePath;
        public final String content;

        public WriteOperation(String filePath, String content) {
            this.filePath = filePath;
            this.content = content;
        }
    }
}