package com.biblioteca.modelo;

public class Auxiliar extends Personal {
    private String turno;

    public Auxiliar(String nombre, String id, String area, String turno) {
        super(nombre, id, area);
        Validador.validarNombre(turno);
        this.turno = turno;
    }

    public String getTurno() { return turno; }

    @Override
    public String generarReporte() {
        return "Auxiliar: " + getNombre() + " | ID: " + getId()
                + " | Área: " + getArea() + " | Turno: " + turno;
    }
}
