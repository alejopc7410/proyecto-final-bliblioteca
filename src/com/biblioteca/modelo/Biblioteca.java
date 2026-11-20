package com.biblioteca.modelo;

import java.util.ArrayList;
import com.biblioteca.excepciones.RecursoNoEncontradoException;
import com.biblioteca.excepciones.PersonalNoEncontradoException;

public class Biblioteca {
    private String nombre;
    private ArrayList<Seccion> secciones;
    private ArrayList<Personal> personal;

    public Biblioteca(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la biblioteca no puede estar vacío.");
        }
        this.nombre = nombre;
        this.secciones = new ArrayList<>();
        this.personal = new ArrayList<>();
        agregarSeccion("Referencia", 10);
        agregarSeccion("Literatura", 8);
        agregarSeccion("Ciencias", 6);
    }

    public void agregarSeccion(String nombre, int capacidad) {
        if (buscarSeccion(nombre) != null) {
            throw new IllegalArgumentException("Ya existe una sección con ese nombre.");
        }
        secciones.add(new Seccion(nombre, capacidad));
        System.out.println("Sección '" + nombre + "' creada con capacidad " + capacidad);
    }

    public void agregarPersonal(Personal persona) {
        if (persona == null) throw new IllegalArgumentException("El personal no puede ser null.");
        personal.add(persona);
        System.out.println(persona.getNombre() + " registrado en la biblioteca.");
    }

    public Seccion buscarSeccion(String nombre) {
        for (Seccion seccion : secciones) {
            if (seccion.getNombre().equalsIgnoreCase(nombre)) return seccion;
        }
        return null;
    }

    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {
        for (Seccion seccion : secciones) {
            try {
                return seccion.buscarRecurso(codigo);
            } catch (RecursoNoEncontradoException e) {
                // Continúa buscando en las demás secciones.
            }
        }
        throw new RecursoNoEncontradoException(codigo);
    }

    public Bibliotecario buscarBibliotecario(String id) throws PersonalNoEncontradoException {
        for (Personal persona : personal) {
            if (persona instanceof Bibliotecario && persona.getId().equalsIgnoreCase(id)) {
                return (Bibliotecario) persona;
            }
        }
        throw new PersonalNoEncontradoException(id);
    }

    public void listarBibliotecarios() {
        boolean encontrado = false;
        for (Personal persona : personal) {
            if (persona instanceof Bibliotecario) {
                System.out.println(persona.getId() + " - " + persona.getNombre() + " (" + persona.getArea() + ")");
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay bibliotecarios registrados.");
    }

    public void generarReporteGeneral() {
        System.out.println("\n===== REPORTE GENERAL: " + nombre + " =====");
        for (Seccion seccion : secciones) {
            System.out.println("Sección: " + seccion.getNombre() + " | Ocupación: "
                    + seccion.getCantidadRecursos() + "/" + seccion.getCapacidad());
        }
        System.out.println("Total de personal: " + personal.size());
        for (Personal persona : personal) {
            System.out.println(persona.generarReporte());
        }
    }

    public String getNombre() { return nombre; }
    public ArrayList<Seccion> getSecciones() { return new ArrayList<>(secciones); }
    public ArrayList<Personal> getPersonal() { return new ArrayList<>(personal); }
}
