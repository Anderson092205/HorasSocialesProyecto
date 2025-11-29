package sv.gob.cementerios.cementeriosle.service;

import sv.gob.cementerios.cementeriosle.dto.CementerioDetalleDTO;
import sv.gob.cementerios.cementeriosle.dto.CementerioResponseDTO;

import java.util.List;

public interface CementerioService {

    /**
     * Obtiene la lista de todos los cementerios para el dashboard.
     * @return Lista de DTOs de respuesta.
     */
    List<CementerioResponseDTO> obtenerTodosLosCementerios();

    /**
     * Obtiene todos los detalles consolidados de un cementerio por su ID.
     * @param idCementerio ID del cementerio a buscar.
     * @return DTO con la información de detalle.
     */
    CementerioDetalleDTO obtenerDetallePorId(Integer idCementerio);

    // Puedes agregar aquí métodos futuros para guardar, actualizar o eliminar.
}