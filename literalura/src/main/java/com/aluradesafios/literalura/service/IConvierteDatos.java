package com.aluradesafios.literalura.service;

// Interfaz que define el contrato para convertir un JSON en un objeto Java de tipo genérico T
public interface IConvierteDatos {
        <T> T obtenerDatos(String json, Class<T> clase);
}