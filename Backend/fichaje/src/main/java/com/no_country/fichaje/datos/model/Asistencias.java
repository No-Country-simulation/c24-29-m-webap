package com.no_country.fichaje.datos.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Asistencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaboradores colaborador;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    private String justificacion;
    private Boolean presente = false;
    private Boolean esExtra = false;

    public Asistencias() { }

    public Asistencias(Long id, Organizacion organizacion, Colaboradores colaborador,                      Date fechaRegistro, Date entrada, Date salida, String justificacion,
                       Boolean presente, Boolean esExtra) {
        this.id = id;
        this.organizacion = organizacion;
        this.colaborador = colaborador;
        this.fechaRegistro = fechaRegistro;
        this.entrada = entrada;
        this.salida = salida;
        this.justificacion = justificacion;
        this.presente = presente;
        this.esExtra = esExtra;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Organizacion getOrganizacion() { return organizacion; }
    public void setOrganizacion(Organizacion organizacion) { this.organizacion = organizacion; }

    public Colaboradores getColaborador() { return colaborador; }
    public void setColaborador(Colaboradores colaborador) { this.colaborador = colaborador; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Date getEntrada() { return entrada; }
    public void setEntrada(Date entrada) { this.entrada = entrada; }

    public Date getSalida() { return salida; }
    public void setSalida(Date salida) { this.salida = salida; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public Boolean getPresente() { return presente; }
    public void setPresente(Boolean presente) { this.presente = presente; }

    public Boolean getEsExtra() { return esExtra; }
    public void setEsExtra(Boolean esExtra) { this.esExtra = esExtra; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asistencias)) return false;
        Asistencias that = (Asistencias) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
