package com.no_country.fichaje.datos.sectores;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@AllArgsConstructor
public class Sectores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sector;
    private String responsabilidades;
    private String tareas;
    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;
}
