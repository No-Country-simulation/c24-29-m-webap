package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.repository.OrganizacionRepository;
import com.no_country.fichaje.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class OrganizacionService {
    @Autowired
   private SesionRepository sesionRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    public Sesion iniciarSesionOrganizacion(String numero, String password) {

        Organizacion org = organizacionRepository.findByNumeroAndPassword(numero, password);
        if (org == null) {
            throw new RuntimeException("Credenciales inválidas");
        }

        Sesion sesion = new Sesion();
        sesion.setOrganizacion(org.getId());
        sesion.setSesionKey(UUID.randomUUID().toString());
        sesion.setInico(new Date());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 8);
        sesion.setFin(cal.getTime());

        return sesionRepository.save(sesion);
    }
}
