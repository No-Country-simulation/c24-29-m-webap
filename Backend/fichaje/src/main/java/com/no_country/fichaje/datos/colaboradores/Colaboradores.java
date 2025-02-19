package com.no_country.fichaje.datos.colaboradores;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.datos.sectores.Sectores;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of= "id")
public class Colaboradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    private String nombre;
    private int dni;
    private String direccion;
    private String codigo_postal;
    private String telefono;
    private Date fecha_alta;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Date fecha_baja;
    private String razon_baja;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sectores sector;

    private String cargo;

    @Lob
    private byte[] frente;

    @Lob
    private byte[] perfil_derecho;

    @Lob
    private byte[] perfil_izquierdo;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencias> asistencias = new ArrayList<>();


}
