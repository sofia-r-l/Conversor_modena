package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = " f8b2c683e06859c2568d75d6";

    public static void main(String[] args) {
        EXTERNA:
        while (true) {
            System.out.println("CONVERSOR DE MONEDAS");
            System.out.println("1 - Lempiras a dólares");
            System.out.println("2 - Pesos Mexicanos a dólares");
            System.out.println("3 - Pesos Colombianos a dólares");
            System.out.println("4 - Salir");
            System.out.print("INGRESE UNA OPCIÓN: ");
            Scanner leer = new Scanner(System.in);
            char opcion = leer.next().charAt(0);

            try {
                switch (opcion) {
                    case '1':
                        convertir("HNL", "Lempiras");
                        break;
                    case '2':
                        convertir("MXN", "Peso Mexicano");
                        break;
                    case '3':
                        convertir("COP", "Peso Colombiano");
                        break;
                    case '4':
                        System.out.println("CERRANDO PROGRAMA");
                        break EXTERNA;
                    default:
                        System.out.println("OPCIÓN INCORRECTA");
                        break;
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
            }
        }
    }

    static void convertir(String currencyCode, String pais) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format("https://api.exchangerate-api.com/v4/latest/%s", currencyCode);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        double tasaCambio = jsonResponse.getAsJsonObject("rates").get("USD").getAsDouble();

        Scanner leer = new Scanner(System.in);
        System.out.printf("Ingrese la cantidad de %s: ", pais);
        double cantidadMoneda = leer.nextDouble();

        double dolares = cantidadMoneda * tasaCambio; // Calcula los dólares correctamente
        dolares = (double) Math.round(dolares * 100d) / 100;

        System.out.println("/-------------------------------");
        System.out.println("Tienes $" + dolares + " Dolares");
        System.out.println("/-------------------------------");
    }
}