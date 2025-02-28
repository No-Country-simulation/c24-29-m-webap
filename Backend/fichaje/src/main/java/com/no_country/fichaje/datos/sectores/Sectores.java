package com.no_country.fichaje.datos.sectores;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private String nombre;
    private String tareas;
    @ManyToOne
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboradores> colaboradores = new ArrayList<>();
}
