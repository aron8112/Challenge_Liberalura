package com.challenge.literalura.service;

public interface IDataConversion {
  <T> T obtenerDatos(String json, Class<T> clase);
}
