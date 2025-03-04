package com.no_country.fichaje.datos.sesion;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity

public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @Column(unique = true, nullable = false)
    private String sesionKey;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inico;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;

    public Sesion() {
    }

    public Sesion(Organizacion organizacion) {
        this.organizacion = organizacion;
        this.inico = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public String getSesionKey() {
        return sesionKey;
    }

    public void setSesionKey(String sesionKey) {
        this.sesionKey = sesionKey;
    }

    public Date getInico() {
        return inico;
    }

    public void setInico(Date inico) {
        this.inico = inico;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sesion)) return false;
        Sesion sesion = (Sesion) o;
        return Objects.equals(id, sesion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PostPersist
    public void asignarSesionKey() {
        if (this.sesionKey == null) {
            this.sesionKey = String.valueOf(this.id);
        }
    }
}


