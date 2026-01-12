package sv.gob.cementerios.cementeriosle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.gob.cementerios.cementeriosle.dto.*;
import sv.gob.cementerios.cementeriosle.model.*;
import sv.gob.cementerios.cementeriosle.repository.*;
import sv.gob.cementerios.cementeriosle.service.CementerioService;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CementerioServiceImpl implements CementerioService {

    @Autowired private CementerioRepository cementerioRepository;
    @Autowired private DifuntoRepository difuntoRepository;
    @Autowired private PropietarioRepository propietarioRepository;

    // <--- INYECCIÓN CLAVE: Tu repositorio de permisos
    @Autowired private AccesoCementerioRepository accesoCementerioRepository;

    // ==========================================================
    // 1. MÉTODO DASHBOARD (APLICACIÓN DEL FILTRO DE PERMISOS)
    // ==========================================================
    @Override
    @Transactional(readOnly = true)
    public List<CementerioResponseDTO> obtenerCementeriosPorUsuario(Integer usuarioId, String rolUsuario) {

        // El rol "ADMIN" puede ver todos los cementerios
        if ("ADMIN".equals(rolUsuario)) {
            List<Cementerio> cementerios = cementerioRepository.findAll();
            return cementerios.stream()
                    .map(this::mapearADTO)
                    .collect(Collectors.toList());
        }

        // Si no es ADMIN (OPERADOR/CONSULTA), filtramos por la tabla de acceso

        // 1. Usa tu repositorio para obtener los registros de acceso del usuario
        List<AccesoCementerio> accesos = accesoCementerioRepository.findByUsuarioIdUsuario(usuarioId);

        // 2. Extrae los IDs de los Cementerios a los que tiene acceso y que están activos
        List<Integer> cementerioIds = accesos.stream()
                .filter(acceso -> acceso.getPuedeVer()) // Asegura que solo tome los que tienen permiso
                .map(acceso -> acceso.getCementerio().getIdCementerio())
                .collect(Collectors.toList());

        if (cementerioIds.isEmpty()) {
            return List.of(); // Retorna una lista vacía si no hay permisos
        }

        // 3. Busca las entidades Cementerio usando la lista de IDs obtenida
        List<Cementerio> cementerios = cementerioRepository.findAllById(cementerioIds);

        // 4. Mapea la lista de Entidades a una lista de DTOs
        return cementerios.stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // 2. MÉTODO DETALLE (Mantiene la lógica existente)
    // ==========================================================
    @Override
    @Transactional(readOnly = true)
    public CementerioDetalleDTO obtenerDetallePorId(Integer idCementerio) {
        // ... (El resto del método 'obtenerDetallePorId' se mantiene igual)
        Cementerio cementerio = cementerioRepository.findById(idCementerio)
                .orElseThrow(() -> new EntityNotFoundException("Cementerio no encontrado con ID: " + idCementerio));

        CementerioDetalleDTO detalleDTO = new CementerioDetalleDTO();
        detalleDTO.setIdCementerio(cementerio.getIdCementerio());
        detalleDTO.setNombreCementerio(cementerio.getNombre());
        detalleDTO.setTipoCementerio(cementerio.getTipo());

        // Obtener CONTEO REAL de Espacios
        Long total = cementerioRepository.contarTotalEspaciosPorCementerio(idCementerio);
        Long ocupados = cementerioRepository.contarEspaciosOcupadosPorCementerio(idCementerio);
        Long disponibles = total - ocupados;

        detalleDTO.setTotalEspacios(total);
        detalleDTO.setEspaciosOcupados(ocupados);
        detalleDTO.setEspaciosDisponibles(disponibles);

        // Obtener Difuntos
        List<Difunto> difuntos = difuntoRepository.findByIdCementerio(idCementerio);
        List<DifuntoDTO> difuntoDTOs = difuntos.stream()
                .map(this::mapearDifuntoADTO)
                .collect(Collectors.toList());
        detalleDTO.setDifuntos(difuntoDTOs);

        // Obtener Propietarios
        List<Propietario> propietarios = propietarioRepository.findPropietariosByCementerioId(idCementerio);
        List<PropietarioDTO> propietarioDTOs = propietarios.stream()
                .map(p -> mapearPropietarioADTO(p, 0L))
                .collect(Collectors.toList());
        detalleDTO.setPropietarios(propietarioDTOs);

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

    // ... (Mantén tus otros métodos de mapeo como mapearDifuntoADTO y mapearPropietarioADTO)
    private DifuntoDTO mapearDifuntoADTO(Difunto difunto) {
        DifuntoDTO dto = new DifuntoDTO();
        dto.setIdDifunto(Math.toIntExact(difunto.getIdDifunto()));
        dto.setNombre(difunto.getNombre());
        dto.setFechaDefuncion(difunto.getFechaDefuncion());
        dto.setUbicacion("Ubicación pendiente de detalle (Lote/Fila/Espacio)");
        return dto;
    }

    private PropietarioDTO mapearPropietarioADTO(Propietario propietario, Long totalLotes) {
        PropietarioDTO dto = new PropietarioDTO();
        dto.setIdPropietario(Math.toIntExact(propietario.getIdPropietario()));
        dto.setNombre(propietario.getNombre());
        dto.setTelefono(propietario.getTelefono());
        dto.setCorreo(propietario.getCorreo());
        dto.setTotalLotes(totalLotes);
        return dto;
    }
}