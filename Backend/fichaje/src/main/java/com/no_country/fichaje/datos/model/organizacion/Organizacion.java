package com.no_country.fichaje.datos.model.organizacion;

import com.no_country.fichaje.datos.model.usuario.Usuario;
import com.no_country.fichaje.datos.model.colaboradores.Colaboradores;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer numeroRegistro;
    private String razonSocial;
    private String rubro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboradores> colaboradores = new ArrayList<>();

    public Organizacion() { }

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

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
        Organizacion that = (Organizacion) o;
        return numeroRegistro == that.numeroRegistro && Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(razonSocial, that.razonSocial) && Objects.equals(rubro, that.rubro) && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(colaboradores, that.colaboradores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, numeroRegistro, razonSocial, rubro, usuarioId, colaboradores);
    }

    public Organizacion(Long id, String nombre, int numeroRegistro, String razonSocial, String rubro, Usuario usuarioId, List<Colaboradores> colaboradores) {
        this.id = id;
        this.nombre = nombre;
        this.numeroRegistro = numeroRegistro;
        this.razonSocial = razonSocial;
        this.rubro = rubro;
        this.usuarioId = usuarioId;
        this.colaboradores = colaboradores;
    }
}
