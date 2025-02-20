package com.no_country.fichaje.datos.asistencia;

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
    private Organizacion id_org;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaboradores colaborador;

    private Date fechaRegistro;
    private Date entrada;
    private Date salida;
    private String justificacion;
    private Boolean presente;

}
