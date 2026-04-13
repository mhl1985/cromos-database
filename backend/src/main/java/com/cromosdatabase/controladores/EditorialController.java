package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.editorial.EditorialDetalleResponse;
import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;
import com.cromosdatabase.servicios.EditorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de editoriales.
 */
@RestController
@RequestMapping("/editoriales")
@RequiredArgsConstructor
public class EditorialController {

    /**
     * Servicio de lógica de negocio de editoriales.
     */
    private final EditorialService editorialService;

    /**
     * Obtiene el listado de DTO´s de editoriales aplicando filtros opcionales.
     *
     * @param nombre texto a buscar dentro del nombre de la editorial
     * @return DTO de lista de editoriales filtradas en formato resumido
     */
    @GetMapping
    public List<EditorialResumenResponse> obtenerEditoriales(
            @RequestParam(required = false) String nombre) {

        List<EditorialResumenResponse> response =
                editorialService.obtenerEditorialesFiltradas(nombre);

        return response;
    }

    /**
     * Obtiene el DTO del detalle de una editorial a partir de su identificador.
     *
     * @param id identificador de la editorial
     * @return DTO del detalle de la editorial
     */
    @GetMapping("/{id}")
    public EditorialDetalleResponse obtenerEditorialPorId(
            @PathVariable Integer id) {

        EditorialDetalleResponse response =
                editorialService.obtenerEditorialPorId(id);

        return response;
    }
}