package paginaAspe.Service;

import paginaAspe.Model.Reporte;
import paginaAspe.Repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepository;
    
    // Obtener todos los reportes
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }
    
    // Obtener reporte por ID
    public Optional<Reporte> obtenerReportePorId(Long id) {
        return reporteRepository.findById(id);
    }
    
    // Guardar reporte
    public Reporte guardarReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }
    
    // Crear nuevo reporte
    public Reporte crearReporte(Long campañaId, Long generadoPor, String tipo, String storageKey) {
        Reporte reporte = new Reporte();
        reporte.setCampañaId(campañaId);
        reporte.setGeneradoPor(generadoPor);
        reporte.setGeneradoAt(LocalDateTime.now());
        reporte.setTipo(tipo);
        reporte.setStorageKey(storageKey);
        
        return reporteRepository.save(reporte);
    }
    
    // Eliminar reporte
    public boolean eliminarReporte(Long id) {
        if (reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener reportes por campaña
    public List<Reporte> obtenerReportesPorCampaña(Long campañaId) {
        return reporteRepository.findByCampañaId(campañaId);
    }
    
    // Obtener reportes por usuario
    public List<Reporte> obtenerReportesPorUsuario(Long usuarioId) {
        return reporteRepository.findByGeneradoPor(usuarioId);
    }
    
    // Obtener reportes por tipo
    public List<Reporte> obtenerReportesPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }
    
    // Obtener reportes por campaña y tipo
    public List<Reporte> obtenerReportesPorCampañaYTipo(Long campañaId, String tipo) {
        return reporteRepository.findByCampañaIdAndTipo(campañaId, tipo);
    }
    
    // Obtener reportes recientes
    public List<Reporte> obtenerReportesRecientes() {
        return reporteRepository.findByOrderByGeneradoAtDesc();
    }
    
    // Obtener reportes por rango de fechas
    public List<Reporte> obtenerReportesPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return reporteRepository.findByGeneradoAtBetween(inicio, fin);
    }
    
    // Buscar reporte por storageKey
    public Optional<Reporte> obtenerReportePorStorageKey(String storageKey) {
        return reporteRepository.findByStorageKey(storageKey);
    }
    
    // Contar reportes por tipo
    public Long contarReportesPorTipo(String tipo) {
        return reporteRepository.countByTipo(tipo);
    }
    
    // Contar reportes por campaña
    public Long contarReportesPorCampaña(Long campañaId) {
        return reporteRepository.countByCampañaId(campañaId);
    }
    
    // Verificar si existe storageKey
    public boolean existeStorageKey(String storageKey) {
        return reporteRepository.existsByStorageKey(storageKey);
    }
}