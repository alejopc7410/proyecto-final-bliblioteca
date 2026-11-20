package com.biblioteca.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.biblioteca.modelo.*;
import com.biblioteca.excepciones.*;

public class App {
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = sc.nextInt();
                sc.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debes escribir un número entero.");
                sc.nextLine();
            }
        }
    }

    private static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static void mostrarMenu() {
        System.out.println("\n========== SISTEMA DE BIBLIOTECA ==========");
        System.out.println("1. Registrar recurso");
        System.out.println("2. Listar recursos registrados");
        System.out.println("3. Ubicar recurso en una sección");
        System.out.println("4. Registrar préstamo");
        System.out.println("5. Listar recursos de una sección");
        System.out.println("6. Ver préstamos de un bibliotecario");
        System.out.println("7. Retirar recurso de una sección");
        System.out.println("8. Buscar recurso por código");
        System.out.println("9. Reporte general");
        System.out.println("10. Crear sección");
        System.out.println("0. Salir");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central Los Libertadores");
        ArrayList<Recurso> recursosRegistrados = new ArrayList<>();

        biblioteca.agregarPersonal(new Bibliotecario("Marta Rojas", "B001", "Circulación"));
        biblioteca.agregarPersonal(new Bibliotecario("Julián Pardo", "B002", "Referencia"));
        biblioteca.agregarPersonal(new Auxiliar("Diana Cruz", "A001", "Catalogación", "Tarde"));

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Selecciona una opción: ");

            try {
                switch (opcion) {
                    case 1: {
                        int tipo = leerEntero(sc, "Tipo (1=Libro, 2=Digital): ");
                        if (tipo != 1 && tipo != 2) {
                            System.out.println("Tipo inválido.");
                            break;
                        }
                        String titulo = leerTexto(sc, "Título: ");
                        String codigo = leerTexto(sc, "Código sin espacios: ");
                        int anio = leerEntero(sc, "Año de publicación (1450-2026): ");
                        Recurso recurso;
                        if (tipo == 1) {
                            String autor = leerTexto(sc, "Autor: ");
                            int paginas = leerEntero(sc, "Número de páginas: ");
                            recurso = new Libro(titulo, codigo, anio, autor, paginas);
                        } else {
                            String formato = leerTexto(sc, "Formato (PDF, EPUB, etc.): ");
                            int tamano = leerEntero(sc, "Tamaño en MB (entero): ");
                            recurso = new RecursoDigital(titulo, codigo, anio, formato, tamano);
                        }
                        for (Recurso registrado : recursosRegistrados) {
                            if (registrado.getCodigo().equalsIgnoreCase(codigo)) {
                                throw new IllegalArgumentException("Ya existe un recurso con ese código.");
                            }
                        }
                        recursosRegistrados.add(recurso);
                        System.out.println("Recurso registrado correctamente. Ahora ubícalo en una sección.");
                        break;
                    }
                    case 2:
                        if (recursosRegistrados.isEmpty()) {
                            System.out.println("No hay recursos registrados.");
                        } else {
                            for (Recurso r : recursosRegistrados) System.out.println(r.obtenerInfo());
                        }
                        break;
                    case 3: {
                        String codigo = leerTexto(sc, "Código del recurso: ");
                        String nombreSeccion = leerTexto(sc, "Nombre de la sección: ");
                        Recurso encontrado = null;
                        for (Recurso r : recursosRegistrados) {
                            if (r.getCodigo().equalsIgnoreCase(codigo)) {
                                encontrado = r;
                                break;
                            }
                        }
                        if (encontrado == null) {
                            System.out.println("Ese recurso no está registrado.");
                            break;
                        }
                        Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
                        if (seccion == null) {
                            System.out.println("La sección no existe.");
                            break;
                        }
                        // Un recurso no debe quedar en dos secciones simultáneamente.
                        for (Seccion otra : biblioteca.getSecciones()) {
                            if (otra.getRecursos().stream().anyMatch(r -> r.getCodigo().equalsIgnoreCase(codigo))) {
                                throw new IllegalArgumentException("El recurso ya está ubicado en una sección.");
                            }
                        }
                        seccion.agregarRecurso(encontrado);
                        break;
                    }
                    case 4: {
                        biblioteca.listarBibliotecarios();
                        String id = leerTexto(sc, "ID del bibliotecario: ");
                        Bibliotecario bibliotecario = biblioteca.buscarBibliotecario(id);
                        String codigo = leerTexto(sc, "Código del recurso ubicado en una sección: ");
                        Recurso recurso = biblioteca.buscarRecurso(codigo);
                        String fecha = leerTexto(sc, "Fecha del préstamo: ");
                        String lector = leerTexto(sc, "Nombre del lector: ");
                        String conObservacion = leerTexto(sc, "¿Agregar observación? (s/n): ");
                        if (conObservacion.equalsIgnoreCase("s")) {
                            String observacion = leerTexto(sc, "Observación: ");
                            bibliotecario.registrarPrestamo(fecha, lector, observacion, recurso);
                        } else if (conObservacion.equalsIgnoreCase("n")) {
                            bibliotecario.registrarPrestamo(fecha, lector, recurso);
                        } else {
                            System.out.println("Respuesta inválida: escribe s o n.");
                        }
                        break;
                    }
                    case 5: {
                        String nombre = leerTexto(sc, "Nombre de la sección: ");
                        Seccion seccion = biblioteca.buscarSeccion(nombre);
                        if (seccion == null) System.out.println("La sección no existe.");
                        else seccion.listarRecursos();
                        break;
                    }
                    case 6: {
                        String id = leerTexto(sc, "ID del bibliotecario: ");
                        Bibliotecario bibliotecario = biblioteca.buscarBibliotecario(id);
                        ArrayList<Prestamo> prestamos = bibliotecario.getPrestamos();
                        if (prestamos.isEmpty()) System.out.println("Este bibliotecario no tiene préstamos.");
                        else for (Prestamo p : prestamos) System.out.println(p.getInfo());
                        break;
                    }
                    case 7: {
                        String nombre = leerTexto(sc, "Nombre de la sección: ");
                        Seccion seccion = biblioteca.buscarSeccion(nombre);
                        if (seccion == null) {
                            System.out.println("La sección no existe.");
                            break;
                        }
                        String codigo = leerTexto(sc, "Código del recurso que deseas retirar: ");
                        Recurso retirado = seccion.retirarRecurso(codigo);
                        System.out.println("Recurso retirado: " + retirado.obtenerInfo());
                        break;
                    }
                    case 8: {
                        String codigo = leerTexto(sc, "Código del recurso: ");
                        System.out.println(biblioteca.buscarRecurso(codigo).obtenerInfo());
                        break;
                    }
                    case 9:
                        biblioteca.generarReporteGeneral();
                        break;
                    case 10: {
                        String nombre = leerTexto(sc, "Nombre de la nueva sección: ");
                        int capacidad = leerEntero(sc, "Capacidad: ");
                        biblioteca.agregarSeccion(nombre, capacidad);
                        System.out.println("Sección creada correctamente.");
                        break;
                    }
                    case 0:
                        System.out.println("Gracias por utilizar el sistema.");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta nuevamente.");
                }
            } catch (SeccionLlenaException | RecursoNoEncontradoException
                    | PersonalNoEncontradoException | PrestamoInvalidoException
                    | IllegalArgumentException e) {
                System.out.println("No se pudo completar la operación: " + e.getMessage());
            }
        } while (opcion != 0);

        sc.close();
    }
}
