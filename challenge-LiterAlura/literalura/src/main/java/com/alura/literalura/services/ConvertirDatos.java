package com.alura.literalura.services;

import tools.jackson.databind.ObjectMapper;

public class ConvertirDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    public<T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
