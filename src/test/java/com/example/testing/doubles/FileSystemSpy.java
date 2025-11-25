package com.example.testing.doubles;

import java.util.ArrayList;
import java.util.List;

public class FileSystemSpy implements FileSystem {
    private List<WriteOperation> writeOperations;

    // Lista zawierająca wszystkie operacje zapisu razem ze ścieżką i zawartością
    public FileSystemSpy() {
        writeOperations = new ArrayList<>();
    }

    @Override
    public void write(String filePath, String content) {
        writeOperations.add(new WriteOperation(filePath, content));
    }

    public int getWriteOperationCount() {
        return writeOperations.size();
    }

    public List<WriteOperation> getWriteOperations() {
        return new ArrayList<>(writeOperations);
    }

    public String getLastWrittenContent() {
        if (writeOperations.isEmpty()) {
            return null;
        }
        return writeOperations.get(writeOperations.size() - 1).content;
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