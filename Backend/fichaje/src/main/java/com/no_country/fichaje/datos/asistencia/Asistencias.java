package com.no_country.fichaje.datos.asistencia;

import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    private Sesion sesion;

    private Date fechaRegistro;
    private Date entrada;
    private Date salida;
    private String justificacion;
    private Boolean presente;
    private Boolean esExtra;

}
