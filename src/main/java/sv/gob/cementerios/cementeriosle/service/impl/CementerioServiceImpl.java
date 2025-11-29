package sv.gob.cementerios.cementeriosle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sv.gob.cementerios.cementeriosle.dto.*;
import sv.gob.cementerios.cementeriosle.model.*;
import sv.gob.cementerios.cementeriosle.repository.*;
import sv.gob.cementerios.cementeriosle.service.CementerioService; // ¡Importación de la Interfaz!

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CementerioServiceImpl implements CementerioService { // Implementa la interfaz

    // Inyección de dependencias
    @Autowired
    private CementerioRepository cementerioRepository;

    @Autowired
    private DifuntoRepository difuntoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    /**
     * Obtiene la lista de todos los cementerios para el dashboard (Implementación de la interfaz).
     */
    @Override
    public List<CementerioResponseDTO> obtenerTodosLosCementerios() {
        // Obtiene todas las entidades de cementerio
        List<Cementerio> cementerios = cementerioRepository.findAll();

        // Mapea la lista de Entidades a una lista de DTOs
        return cementerios.stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los detalles consolidados de un cementerio por su ID (Implementación de la interfaz).
     */
    @Override
    public CementerioDetalleDTO obtenerDetallePorId(Integer idCementerio) {
        // 1. Obtener la información base del cementerio
        Cementerio cementerio = cementerioRepository.findById(idCementerio)
                .orElseThrow(() -> new RuntimeException("Cementerio no encontrado con ID: " + idCementerio));

        CementerioDetalleDTO detalleDTO = new CementerioDetalleDTO();
        detalleDTO.setIdCementerio(cementerio.getIdCementerio());
        detalleDTO.setNombreCementerio(cementerio.getNombre());
        detalleDTO.setTipoCementerio(cementerio.getTipo());

        // 2. Obtener Difuntos
        List<Difunto> difuntos = difuntoRepository.findByIdCementerio(idCementerio);
        List<DifuntoDTO> difuntoDTOs = difuntos.stream()
                .map(this::mapearDifuntoADTO)
                .collect(Collectors.toList());
        detalleDTO.setDifuntos(difuntoDTOs);

        // 3. Obtener Propietarios
        // La lógica de la consulta de propietarios está en el repositorio (PropietarioRepository)
        List<Propietario> propietarios = propietarioRepository.findPropietariosByCementerioId(idCementerio);
        List<PropietarioDTO> propietarioDTOs = propietarios.stream()
                // Nota: Usamos 0L como contador de lotes aquí, idealmente el repositorio debería retornar un objeto
                // que incluya el conteo, pero por ahora simplificamos el mapeo del DTO.
                .map(p -> mapearPropietarioADTO(p, 0L))
                .collect(Collectors.toList());
        detalleDTO.setPropietarios(propietarioDTOs);

        // 4. Lógica de Espacios (Simulación)
        long ocupados = (long) difuntos.size();
        long totalSimulado = ocupados + 500; // Simulación: 500 disponibles

        detalleDTO.setEspaciosOcupados(ocupados);
        detalleDTO.setTotalEspacios(totalSimulado);
        detalleDTO.setEspaciosDisponibles(totalSimulado - ocupados);

        return detalleDTO;
    }

    // --- Métodos de Mapeo (Auxiliares) ---

    private CementerioResponseDTO mapearADTO(Cementerio cementerio) {
        CementerioResponseDTO dto = new CementerioResponseDTO();
        dto.setId(cementerio.getIdCementerio());
        dto.setNombre(cementerio.getNombre());
        dto.setTipo(cementerio.getTipo());
        return dto;
    }

    private DifuntoDTO mapearDifuntoADTO(Difunto difunto) {
        DifuntoDTO dto = new DifuntoDTO();
        dto.setIdDifunto(difunto.getIdDifunto());
        dto.setNombre(difunto.getNombre());
        dto.setFechaDefuncion(difunto.getFechaDefuncion());
        dto.setUbicacion("Ubicación pendiente de detalle (Lote/Fila/Espacio)");
        return dto;
    }

    private PropietarioDTO mapearPropietarioADTO(Propietario propietario, Long totalLotes) {
        PropietarioDTO dto = new PropietarioDTO();
        dto.setIdPropietario(propietario.getIdPropietario());
        dto.setNombre(propietario.getNombre());
        dto.setTelefono(propietario.getTelefono());
        dto.setCorreo(propietario.getCorreo());
        dto.setTotalLotes(totalLotes);
        return dto;
    }
}