package com.challenge.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DataConversion implements IDataConversion {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public <T> T obtenerDatos(String json, Class<T> clase) {
    try {
      return objectMapper.readValue(json, clase);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public String obtenerTexto(String json)  {
    JsonNode jsonNodeRoot = null;
    try {
      jsonNodeRoot = objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    JsonNode jsonResults = jsonNodeRoot.get("results");
    return jsonResults.asText();
  }
}
