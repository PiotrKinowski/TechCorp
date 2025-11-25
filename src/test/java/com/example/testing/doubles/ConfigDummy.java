
package com.example.testing.doubles;

public class ConfigDummy {
    // Dummy object - nie zawiera żadnej logiki
    // Służy tylko do wypełnienia listy parametrów
    public String getConfigValue() {
        throw new UnsupportedOperationException("Dummy object - method should not be called");
    }
}