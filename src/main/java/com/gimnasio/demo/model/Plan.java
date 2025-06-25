package com.gimnasio.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "planes")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_plan;

    private String nombre;

    private int duracion_meses;

    private double precio;

    private String descripcion;

    // Puedes ignorar los campos `id` y `duracion` si no los usas
    // private int id;
    // private int duracion;

    // Getters y Setters

    public int getId_plan() {
        return id_plan;
    }

    public void setId_plan(int id_plan) {
        this.id_plan = id_plan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion_meses() {
        return duracion_meses;
    }

    public void setDuracion_meses(int duracion_meses) {
        this.duracion_meses = duracion_meses;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
