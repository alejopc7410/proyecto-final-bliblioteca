package com.biblioteca.modelo;

public final class RecursoDigital extends Recurso {
    private String formato;
    private int tamanoMB;

    public RecursoDigital(String titulo, String codigo, int anio, String formato, int tamanoMB) {
        super(titulo, codigo, anio);
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("El formato no puede estar vacío.");
        }
        if (tamanoMB <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero.");
        }
        this.formato = formato;
        this.tamanoMB = tamanoMB;
    }

    public String getFormato() { return formato; }
    public int getTamanoMB() { return tamanoMB; }

    @Override
    public String obtenerInfo() {
        return "[RECURSO DIGITAL] " + getTitulo() + " | Código: " + getCodigo()
                + " | Año: " + getAnio() + " | Formato: " + formato
                + " | Tamaño: " + tamanoMB + " MB";
    }
}
