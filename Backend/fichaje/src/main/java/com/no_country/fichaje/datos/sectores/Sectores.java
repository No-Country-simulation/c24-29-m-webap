package com.no_country.fichaje.datos.sectores;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity

public class Sectores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tareas;
    @ManyToOne
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboradores> colaboradores = new ArrayList<>();

    public Sectores(){}
    public Sectores(Long id, String nombre, String tareas, Organizacion organizacion, List<Colaboradores> colaboradores) {
        this.id = id;
        this.nombre = nombre;
        this.tareas = tareas;
        this.organizacion = organizacion;
        this.colaboradores = colaboradores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTareas() {
        return tareas;
    }

    public void setTareas(String tareas) {
        this.tareas = tareas;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public List<Colaboradores> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaboradores> colaboradores) {
        this.colaboradores = colaboradores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sectores sectores = (Sectores) o;
        return Objects.equals(id, sectores.id) && Objects.equals(nombre, sectores.nombre) && Objects.equals(tareas, sectores.tareas) && Objects.equals(organizacion, sectores.organizacion) && Objects.equals(colaboradores, sectores.colaboradores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, tareas, organizacion, colaboradores);
    }

}
