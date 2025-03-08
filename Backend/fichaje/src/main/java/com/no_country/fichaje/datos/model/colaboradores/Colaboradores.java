package com.no_country.fichaje.datos.model.colaboradores;

import com.no_country.fichaje.datos.model.asistencia.Asistencias;
import com.no_country.fichaje.datos.model.organizacion.Organizacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    @NotNull(message = "La organización es obligatoria")
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Positive(message = "El DNI debe ser un número positivo")
    @Column(unique = true)
    private int dni;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotBlank(message = "El código postal es obligatorio")
    private String ccodigoPostal;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    private String correoElectronico;
    @NotNull(message = "La fecha de alta es obligatoria")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    private String razonBaja;
    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @Lob
    private String frente;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencias> asistencias = new ArrayList<>();

    public Colaboradores() { }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCcodigoPostal() {
        return ccodigoPostal;
    }

    public void setCcodigoPostal(String ccodigoPostal) {
        this.ccodigoPostal = ccodigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getRazonBaja() {
        return razonBaja;
    }

    public void setRazonBaja(String razonBaja) {
        this.razonBaja = razonBaja;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public List<Asistencias> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencias> asistencias) {
        this.asistencias = asistencias;
    }

    public Colaboradores(Long id, Organizacion organizacion, String nombre, int dni, String direccion, String ccodigoPostal, String telefono, String correoElectronico, Date fechaAlta, Estado estado, Date fechaBaja, String razonBaja, String cargo, String frente, List<Asistencias> asistencias) {
        this.id = id;
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.dni = dni;
        this.direccion = direccion;
        this.ccodigoPostal = ccodigoPostal;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.fechaBaja = fechaBaja;
        this.razonBaja = razonBaja;
        this.cargo = cargo;
        this.frente = frente;
        this.asistencias = asistencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colaboradores that = (Colaboradores) o;
        return dni == that.dni && Objects.equals(id, that.id) && Objects.equals(organizacion, that.organizacion) && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(ccodigoPostal, that.ccodigoPostal) && Objects.equals(telefono, that.telefono) && Objects.equals(correoElectronico, that.correoElectronico) && Objects.equals(fechaAlta, that.fechaAlta) && estado == that.estado && Objects.equals(fechaBaja, that.fechaBaja) && Objects.equals(razonBaja, that.razonBaja) && Objects.equals(cargo, that.cargo) && Objects.equals(frente, that.frente) && Objects.equals(asistencias, that.asistencias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizacion, nombre, dni, direccion, ccodigoPostal, telefono, correoElectronico, fechaAlta, estado, fechaBaja, razonBaja, cargo, frente, asistencias);
    }
}
