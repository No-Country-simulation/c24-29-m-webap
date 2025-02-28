package com.no_country.fichaje.datos.sesion;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setSesionKey(String sesionKey) {
        this.sesionKey = sesionKey;
    }

    public void setInico(Date inico) {
        this.inico = inico;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
}


