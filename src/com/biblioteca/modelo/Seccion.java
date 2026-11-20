package com.biblioteca.modelo;

import java.util.ArrayList;
import com.biblioteca.excepciones.SeccionLlenaException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;

public class Seccion {
    private String nombre;
    private int capacidad;
    private ArrayList<Recurso> recursos;

    public Seccion(String nombre, int capacidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sección no puede estar vacío.");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.recursos = new ArrayList<>();
    }

    public void agregarRecurso(Recurso recurso) throws SeccionLlenaException {
        if (recurso == null) {
            throw new IllegalArgumentException("El recurso no puede ser null.");
        }
        if (recursos.size() >= capacidad) {
            throw new SeccionLlenaException("Sección " + nombre + " sin espacio disponible.");
        }
        if (buscarPorCodigoInterno(recurso.getCodigo()) != null) {
            throw new IllegalArgumentException("Ese recurso ya está en esta sección.");
        }
        recursos.add(recurso);
        System.out.println("Recurso " + recurso.getTitulo() + " ubicado en sección " + nombre);
    }

    public Recurso retirarRecurso(String codigo) throws RecursoNoEncontradoException {
        for (int i = 0; i < recursos.size(); i++) {
            if (recursos.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                Recurso retirado = recursos.remove(i);
                System.out.println("Recurso dado de baja.");
                return retirado;
            }
        }
        throw new RecursoNoEncontradoException(codigo);
    }

    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {
        Recurso recurso = buscarPorCodigoInterno(codigo);
        if (recurso == null) {
            throw new RecursoNoEncontradoException(codigo);
        }
        return recurso;
    }

    private Recurso buscarPorCodigoInterno(String codigo) {
        for (Recurso recurso : recursos) {
            if (recurso.getCodigo().equalsIgnoreCase(codigo)) return recurso;
        }
        return null;
    }

    public void listarRecursos() {
        if (recursos.isEmpty()) {
            System.out.println("La sección no tiene recursos.");
            return;
        }
        for (Recurso recurso : recursos) {
            System.out.println(recurso.obtenerInfo());
        }
    }

    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public ArrayList<Recurso> getRecursos() { return new ArrayList<>(recursos); }
    public int getCantidadRecursos() { return recursos.size(); }
}
