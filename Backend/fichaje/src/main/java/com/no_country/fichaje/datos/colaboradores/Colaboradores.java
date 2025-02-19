package com.no_country.fichaje.datos.colaboradores;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.awt.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Colaboradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int id_org;
    private String nombre;
    private int dni;
    private String direccion;
    private String codigo_postal;
    private String telefono;
    private Date fecha_alta;
    private Estado estado;
    private Date fecha_baja;
    private String razon_baja;
    private Sector id_sector;
    private String cargo;
    private String tarea;
    private Image frente;
    private Image perfil_derecho;
    private Image perfil_izquierdo;
}
