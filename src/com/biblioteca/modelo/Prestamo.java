package com.biblioteca.modelo;

public class Prestamo {
    private String fecha;
    private String observacion;
    private String lector;
    private Recurso recurso;
    private Bibliotecario bibliotecario;

    public Prestamo(String fecha, String observacion, String lector,
                    Recurso recurso, Bibliotecario bibliotecario) {
        if (fecha == null || fecha.trim().isEmpty()
                || observacion == null || observacion.trim().isEmpty()
                || lector == null || lector.trim().isEmpty()
                || recurso == null || bibliotecario == null) {
            throw new IllegalArgumentException("Los datos del préstamo son obligatorios.");
        }
        this.fecha = fecha;
        this.observacion = observacion;
        this.lector = lector;
        this.recurso = recurso;
        this.bibliotecario = bibliotecario;
    }

    public String getFecha() { return fecha; }
    public String getObservacion() { return observacion; }
    public String getLector() { return lector; }
    public Recurso getRecurso() { return recurso; }
    public Bibliotecario getBibliotecario() { return bibliotecario; }

    public String getInfo() {
        return "Préstamo: " + fecha + " | Lector: " + lector
                + " | Recurso: " + recurso.getTitulo()
                + " | Bibliotecario: " + bibliotecario.getNombre()
                + " | Nota: " + observacion;
    }
}
