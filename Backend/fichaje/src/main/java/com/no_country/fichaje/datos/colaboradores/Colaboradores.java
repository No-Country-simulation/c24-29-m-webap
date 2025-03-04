package com.no_country.fichaje.datos.colaboradores;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.datos.sectores.Sectores;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity

public class Colaboradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    private String nombre;
    private int dni;
    private String direccion;
    private String codigo_postal;
    private String telefono;
    private String correoElectronico;
    private Date fechaAlta;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Date fechaBaja;
    private String razonBaja;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sectores sector;

    private String cargo;

    @Lob
    private String frente;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencias> asistencias = new ArrayList<>();

    public Colaboradores() { }

    public Colaboradores(Long id, Organizacion organizacion, String nombre, int dni, String direccion, String codigo_postal,
                         String telefono, String correoElectronico, Date fechaAlta, Estado estado, Date fechaBaja,
                         String razonBaja, Sectores sector, String cargo, String frente, List<Asistencias> asistencias) {
        this.id = id;
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.dni = dni;
        this.direccion = direccion;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.fechaBaja = fechaBaja;
        this.razonBaja = razonBaja;
        this.sector = sector;
        this.cargo = cargo;
        this.frente = frente;
        this.asistencias = asistencias;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Organizacion getOrganizacion() { return organizacion; }
    public void setOrganizacion(Organizacion organizacion) { this.organizacion = organizacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCodigo_postal() { return codigo_postal; }
    public void setCodigo_postal(String codigo_postal) { this.codigo_postal = codigo_postal; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public Date getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Date getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getRazonBaja() { return razonBaja; }
    public void setRazonBaja(String razonBaja) { this.razonBaja = razonBaja; }

    public Sectores getSector() { return sector; }
    public void setSector(Sectores sector) { this.sector = sector; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getFrente() { return frente; }
    public void setFrente(String frente) { this.frente = frente; }

    public List<Asistencias> getAsistencias() { return asistencias; }
    public void setAsistencias(List<Asistencias> asistencias) { this.asistencias = asistencias; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colaboradores)) return false;
        Colaboradores that = (Colaboradores) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
