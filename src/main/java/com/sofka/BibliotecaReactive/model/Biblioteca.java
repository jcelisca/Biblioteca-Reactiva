package com.sofka.BibliotecaReactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "biblioteca")
public class Biblioteca {

    @Id
    private String id;
    private String name;
    private int disponibilidad;
    private LocalDate fechaPrestamo;
    private String estado;
    private String categoria;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
