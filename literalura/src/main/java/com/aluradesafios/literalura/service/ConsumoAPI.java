package com.aluradesafios.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    // Método para realizar una petición HTTP GET a la URL especificada y obtener la respuesta como String JSON
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient(); // Crea un cliente HTTP

        // Construye la solicitud HTTP con la URL proporcionada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            // Envía la solicitud y recibe la respuesta en formato String
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Captura errores de entrada/salida y los relanza como RuntimeException
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // Captura interrupciones y las relanza como RuntimeException
            throw new RuntimeException(e);
        }
        String json = response.body(); // Obtiene el cuerpo de la respuesta (JSON)
        return json; // Retorna el JSON obtenido
    }
}