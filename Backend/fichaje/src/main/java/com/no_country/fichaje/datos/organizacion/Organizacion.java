package com.no_country.fichaje.datos.organizacion;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sectores.Sectores;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
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

    public Organizacion() { }

    public Organizacion(Long id, String nombre, int numero, String siglasOrg, String responsable, String rubro,
                        String telefono, String email, String password, Redes redes,
                        List<Sectores> sectores, List<Colaboradores> colaboradores) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.siglasOrg = siglasOrg;
        this.responsable = responsable;
        this.rubro = rubro;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.redes = redes;
        this.sectores = sectores;
        this.colaboradores = colaboradores;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getSiglasOrg() { return siglasOrg; }
    public void setSiglasOrg(String siglasOrg) { this.siglasOrg = siglasOrg; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getRubro() { return rubro; }
    public void setRubro(String rubro) { this.rubro = rubro; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Redes getRedes() { return redes; }
    public void setRedes(Redes redes) { this.redes = redes; }

    public List<Sectores> getSectores() { return sectores; }
    public void setSectores(List<Sectores> sectores) { this.sectores = sectores; }

    public List<Colaboradores> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<Colaboradores> colaboradores) { this.colaboradores = colaboradores; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organizacion)) return false;
        Organizacion that = (Organizacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
