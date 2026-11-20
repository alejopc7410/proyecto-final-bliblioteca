package com.biblioteca.modelo;

import java.util.ArrayList;
import com.biblioteca.excepciones.PrestamoInvalidoException;

public class Bibliotecario extends Personal {
    private ArrayList<Prestamo> prestamos;

    public Bibliotecario(String nombre, String id, String area) {
        super(nombre, id, area);
        prestamos = new ArrayList<>();
    }

    public void registrarPrestamo(String fecha, String lector, Recurso recurso)
            throws PrestamoInvalidoException {
        registrarPrestamo(fecha, lector, "Préstamo estándar", recurso);
    }

    public void registrarPrestamo(String fecha, String lector, String observacion, Recurso recurso)
            throws PrestamoInvalidoException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La fecha no puede estar vacía.");
        }
        if (lector == null || lector.trim().isEmpty()) {
            throw new PrestamoInvalidoException("El nombre del lector no puede estar vacío.");
        }
        if (observacion == null || observacion.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La observación no puede estar vacía.");
        }
        if (recurso == null) {
            throw new PrestamoInvalidoException("No se puede prestar un recurso inexistente.");
        }

        Prestamo prestamo = new Prestamo(fecha, observacion, lector, recurso, this);
        prestamos.add(prestamo);
        System.out.println("Préstamo registrado: " + recurso.getTitulo() + " a " + lector
                + ("Préstamo estándar".equals(observacion) ? "" : " (" + observacion + ")"));
    }

    public ArrayList<Prestamo> getPrestamos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public String generarReporte() {
        return "Bibliotecario: " + getNombre() + " | ID: " + getId()
                + " | Área: " + getArea() + " | Préstamos: " + prestamos.size();
    }
}
