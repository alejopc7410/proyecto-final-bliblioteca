package com.biblioteca.modelo;

public class Libro extends Recurso {
    private String autor;
    private int paginas;

    public Libro(String titulo, String codigo, int anio, String autor, int paginas) {
        super(titulo, codigo, anio);
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        if (paginas <= 0) {
            throw new IllegalArgumentException("El número de páginas debe ser mayor que cero.");
        }
        this.autor = autor;
        this.paginas = paginas;
    }

    public String getAutor() { return autor; }
    public int getPaginas() { return paginas; }

    @Override
    public String obtenerInfo() {
        return "[LIBRO] " + getTitulo() + " | Código: " + getCodigo()
                + " | Año: " + getAnio() + " | Autor: " + autor + " | Páginas: " + paginas;
    }
}
