package com.aluradesafios.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper(); // Objeto Jackson para convertir JSON a objetos Java

    // Método genérico que convierte un String JSON en un objeto del tipo especificado
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Intenta deserializar el JSON a un objeto de la clase indicada
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            // En caso de error en el procesamiento JSON lanza RuntimeException
            throw new RuntimeException(e);
        }
    }
}