package com.no_country.fichaje.datos.asistencia;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsistenciaDTO {
private String sesionKey;
private Long organizacionId;

    public AsistenciaDTO(String sesionKey, Long organizacionId, String imagenCapturada) {
        this.sesionKey = sesionKey;
        this.organizacionId = organizacionId;
        this.imagenCapturada = imagenCapturada;
    }

    public String getSesionKey() {
        return sesionKey;
    }

    public void setSesionKey(String sesionKey) {
        this.sesionKey = sesionKey;
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

    private String imagenCapturada;
}
