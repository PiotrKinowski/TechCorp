package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import com.example.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private Gson gson;

    public ApiService() {
        this.gson = new Gson();
    }

    public List<Employee> fetchEmployeesFromApi() throws ApiException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("Błąd HTTP: " + response.statusCode());
            }

            String responseBody = response.body();
            JsonArray jsonArray = gson.fromJson(responseBody, JsonArray.class);

            List<Employee> employees = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                JsonObject userObject = element.getAsJsonObject();

                String fullName = userObject.get("name").getAsString();
                String email = userObject.get("email").getAsString();
                String companyName = userObject.get("company").getAsJsonObject().get("name").getAsString();

                // Rozdzielenie pełnego imienia na imię i nazwisko
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                // Dla wszystkich użytkowników z API ustawiamy stanowisko PROGRAMISTA i bazową stawkę
                Position position = Position.PROGRAMISTA;
                double salary = position.getBaseSalary();

                Employee employee = new Employee(firstName, lastName, email, companyName, position, salary);
                employees.add(employee);
            }

            return employees;

        } catch (Exception e) {
            throw new ApiException("Błąd podczas komunikacji z API: " + e.getMessage(), e);
        }
    }
}