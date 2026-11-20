package com.biblioteca.modelo;

public final class Validador {
    private Validador() { }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no puede contener números.");
        }
    }

    public static void validarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (codigo.contains(" ")) {
            throw new IllegalArgumentException("El código no puede contener espacios.");
        }
    }
}
