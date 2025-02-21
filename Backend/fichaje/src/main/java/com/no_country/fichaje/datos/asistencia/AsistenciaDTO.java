package com.no_country.fichaje.datos.asistencia;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsistenciaDTO {
private String sesionKey;
private Long colaboradorId;
private byte[] imagenCapturada;
}
