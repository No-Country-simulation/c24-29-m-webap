package com.no_country.fichaje.service;


import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciasRepository asistenciasRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SesionRepository sesionRepository;


    public Sesion inicioYFinSesion(byte[] imagenCapturada) {

    }
}

