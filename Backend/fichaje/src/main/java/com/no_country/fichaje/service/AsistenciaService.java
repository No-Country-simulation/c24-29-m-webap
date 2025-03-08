package com.no_country.fichaje.service;
import com.no_country.fichaje.datos.model.asistencia.Asistencias;
import com.no_country.fichaje.datos.model.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciasRepository asistenciasRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    private final CompreFaceService compreFaceService;

    @Autowired
    public AsistenciaService(CompreFaceService compreFaceService) {
        this.compreFaceService = compreFaceService;
    }

    @Value("${api.key}")
    private String apiKey;

    public Optional<Colaboradores> reconocerColaborador(String imagenCapturada, Long organizacionId) {
        List<Colaboradores> colaboradores = colaboradorRepository.findByOrganizacionId(organizacionId);
        List<String> imagenes = colaboradores.stream()
                .map(Colaboradores::getFrente)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (imagenes.isEmpty()) {
            return Optional.empty();
        }

        Colaboradores colaborador = compreFaceService.detectFace("api-key", imagenCapturada, imagenes)
                .map(response -> {
                    if (response.containsKey("result")) {
                        List<Map<String, Object>> resultados = (List<Map<String, Object>>) response.get("result");
                        Optional<Map<String, Object>> mejorCoincidencia = resultados.stream()
                                .filter(r -> r.containsKey("similarity") && (double) r.get("similarity") >= 0.85)
                                .max(Comparator.comparing(r -> (double) r.get("similarity")));
                        if (mejorCoincidencia.isPresent()) {
                            int index = resultados.indexOf(mejorCoincidencia.get());
                            if (index >= 0 && index < colaboradores.size()) {
                                return colaboradores.get(index);
                            }
                        }
                    }
                    return null;
                })
                .block();

        return Optional.ofNullable(colaborador);
    }

    public boolean tieneAsistenciaActiva(Colaboradores colaborador) {
        return asistenciasRepository.findByColaboradorAndSalidaIsNull(colaborador).isPresent();
    }

    @Transactional
    public void registrarEntrada(Colaboradores colaborador) {
        Date fechaRegistro = new Date();
        List<Asistencias> registrosHoy = asistenciasRepository.findRegistroAbiertoByColaboradorAndFecha(colaborador, fechaRegistro);

        Asistencias nuevaAsistencia = new Asistencias();
        nuevaAsistencia.setColaborador(colaborador);
        nuevaAsistencia.setOrganizacion(colaborador.getOrganizacion());
        nuevaAsistencia.setFechaRegistro(fechaRegistro);
        nuevaAsistencia.setEntrada(fechaRegistro);

        if(registrosHoy.isEmpty()){
            nuevaAsistencia.setPresente(true);
            nuevaAsistencia.setEsExtra(false);
        } else{
            nuevaAsistencia.setPresente(false);
            nuevaAsistencia.setEsExtra(true);
        }
        asistenciasRepository.save(nuevaAsistencia);
    }

    @Transactional
    public void registrarSalida(Colaboradores colaborador) {
        Asistencias asistencia = asistenciasRepository.findByColaboradorAndSalidaIsNull(colaborador)
                .orElseThrow(() -> new RuntimeException("No se encontró asistencia activa para el colaborador"));
        asistencia.setSalida(new Date());
        asistencia.setPresente(false);
        asistenciasRepository.save(asistencia);
    }

public List<Asistencias> obtenerAsistencias(Long colaboradorId, String periodo) {
    Colaboradores colaborador = colaboradorRepository.findById(colaboradorId)
            .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));

    Date fechaInicio = calcularFechaInicio(periodo);
    Date fechaFin = new Date();

    if (fechaInicio != null) {
        return asistenciasRepository.findByColaboradorAndFechaRegistroBetween(colaborador, fechaInicio, fechaFin);
    } else {
        return asistenciasRepository.findByColaborador(colaborador);
    }
}

private Date calcularFechaInicio(String periodo) {
    if (periodo == null) {
        return null;
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());

    switch (periodo.toLowerCase()) {
        case "dia":
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            break;
        case "semana":
            cal.add(Calendar.DAY_OF_MONTH, -7);
            break;
        case "mes":
            cal.add(Calendar.MONTH, -1);
            break;
        case "año":
            cal.add(Calendar.YEAR, -1);
            break;
        default:
            return null;
    }

    return cal.getTime();
}

    public List<Map<String, Object>> obtenerEstadisticasPorOrganizacion(Long organizacionId, String periodo) {
        Date fechaInicio = calcularFechaInicio(periodo);
        Date fechaFin = new Date();

        List<Asistencias> asistencias = asistenciasRepository.findByOrganizacionIdAndFechaRegistroBetween(organizacionId, fechaInicio, fechaFin);

        Map<Long, List<Asistencias>> asistenciasPorColaborador = asistencias.stream()
                .collect(Collectors.groupingBy(a -> a.getColaborador().getId()));

        List<Map<String, Object>> estadisticas = new ArrayList<>();

        for (Map.Entry<Long, List<Asistencias>> entry : asistenciasPorColaborador.entrySet()) {
            List<Asistencias> registros = entry.getValue();
            long totalHoras = registros.stream()
                    .filter(a -> a.getSalida() != null)
                    .mapToLong(a -> (a.getSalida().getTime() - a.getEntrada().getTime()) / (1000 * 60 * 60)) // Convertir a horas
                    .sum();

            Map<String, Object> stats = new HashMap<>();
            stats.put("colaboradorId", entry.getKey());
            stats.put("diasTrabajados", registros.size());
            stats.put("totalHoras", totalHoras);
            stats.put("promedioHoras", registros.isEmpty() ? 0 : totalHoras / (double) registros.size());

            estadisticas.add(stats);
        }

        return estadisticas;
    }
    public Map<String, Object> obtenerEstadisticasPorColaborador(Long colaboradorId, String periodo) {
        Colaboradores colaborador = colaboradorRepository.findById(colaboradorId)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));

        Date fechaInicio = calcularFechaInicio(periodo);
        Date fechaFin = new Date();

        List<Asistencias> asistencias = (fechaInicio != null)
                ? asistenciasRepository.findByColaboradorAndFechaRegistroBetween(colaborador, fechaInicio, fechaFin)
                : asistenciasRepository.findByColaborador(colaborador);

        long totalHoras = asistencias.stream()
                .mapToLong(a -> calcularHorasTrabajadas(a.getEntrada(), a.getSalida()))
                .sum();

        double promedioHoras = asistencias.isEmpty() ? 0 : (double) totalHoras / asistencias.size();

        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalAsistencias", asistencias.size());
        estadisticas.put("totalHorasTrabajadas", totalHoras);
        estadisticas.put("promedioHorasPorDia", promedioHoras);

        return estadisticas;
    }
    private long calcularHorasTrabajadas(Date entrada, Date salida) {
        if (entrada == null || salida == null) {
            return 0;
        }
        long diff = salida.getTime() - entrada.getTime();
        return TimeUnit.MILLISECONDS.toHours(diff);
    }

public void registrarJustificacion(Long asistenciaId, String justificacion) {
    Asistencias asistencia = asistenciasRepository.findById(asistenciaId)
            .orElseThrow(() -> new RuntimeException("Registro de asistencia no encontrado"));

    asistencia.setJustificacion(justificacion);
    asistenciasRepository.save(asistencia);
}
}










