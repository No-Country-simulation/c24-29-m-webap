package com.no_country.fichaje.datos.asistencia;

import java.util.Objects;

public class AsistenciaDTO {

private Long organizacionId;
private String imagenCapturada;

    public AsistenciaDTO(Long organizacionId, String imagenCapturada) {
        this.organizacionId = organizacionId;
        this.imagenCapturada = imagenCapturada;
    }

    public Long getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId) {
        this.organizacionId = organizacionId;
    }

    public String getImagenCapturada() {
        return imagenCapturada;
    }

    public void setImagenCapturada(String imagenCapturada) {
        this.imagenCapturada = imagenCapturada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsistenciaDTO that = (AsistenciaDTO) o;
        return Objects.equals(organizacionId, that.organizacionId) && Objects.equals(imagenCapturada, that.imagenCapturada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizacionId, imagenCapturada);
    }
}
