package com.no_country.fichaje.datos.organizacion;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sectores.Sectores;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Organizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int numero;
    private String siglasOrg;
    private String responsable;
    private String rubro;
    private String telefono;
    private String email;
    private String password;

   @Embedded
    private Redes redes;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sectores> sectores;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboradores> colaboradores = new ArrayList<>();
}
