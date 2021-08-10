package ar.edu.davinci.dvdsPerezMartin.controlador.rest;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvdsPerezMartin.controlador.MyRestApp;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.OrdenInsertRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.OrdenUpdateRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.response.OrdenResponse;
import ar.edu.davinci.dvdsPerezMartin.modelo.Orden;
import ar.edu.davinci.dvdsPerezMartin.servicio.OrdenServicio;
import ma.glasnost.orika.MapperFacade;

@RestController
public class OrdenRestControlador extends MyRestApp {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrdenRestControlador.class);

	private final OrdenServicio ordenServicio;

	private final MapperFacade mapper;

	@Autowired
	public OrdenRestControlador(final OrdenServicio ordenServicio, final MapperFacade mapper) {
		this.ordenServicio = ordenServicio;
		this.mapper = mapper;
	}

	/**
	 * Lista todos los ordenes.
	 * 
	 * @return
	 */
	@GetMapping(path = "/ordenes")
	public Page<OrdenResponse> getOrdenes(Pageable pageable) {
		LOGGER.info("listar todos los ordenes paginados");

		Page<OrdenResponse> ordenResponse = null;
		Page<Orden> ordenPage = null;
		try {
			ordenPage = ordenServicio.listarOrden(pageable);
			LOGGER.info("ordenPage size:" + ((ordenPage != null) ? ordenPage.getSize() : 0));
			LOGGER.info("ordenPage hasContent:" + ((ordenPage != null) ? ordenPage.getSize() : "false"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		if (ordenPage != null && ordenPage.hasContent()) {
			try {
				ordenResponse = ordenPage.map(orden -> mapper.map(orden, OrdenResponse.class));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return ordenResponse;
	}

	/**
	 * Buscar orden por id
	 * @param id identificador del orden
	 * @return retorna el orden
	 */
	@GetMapping(path = "/ordenes/{id}")
	public OrdenResponse getOrden(@PathVariable Long id) {
		LOGGER.info("lista al orden solicitado");

		OrdenResponse ordenesResponse = null;
		Orden orden = null;
		try {
			orden = ordenServicio.buscarOrdenPorId(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			ordenesResponse = mapper.map(orden, OrdenResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return ordenesResponse;
	}
	
	/**
	 * Grabar a un nuevo orden
	 * 
	 * @param datosOrden son los datos para un nuevo orden
	 * @return un orden nuevo
	 */
	@PostMapping(path = "/ordenes")
	public ResponseEntity<OrdenResponse> createOrden(@RequestBody OrdenInsertRequest datosOrden) {
		LOGGER.info("Orden a insertar: " + datosOrden.toString());
		
		Orden orden = null;
		OrdenResponse ordenResponse = null;

		// Convertir OrdenInsertRequest en Orden
		try {
			orden = mapper.map(datosOrden, Orden.class);
			LOGGER.info("Orden mapeada: " + orden.toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Prepara la Orden a Grabar y la graba
		try {
			orden = ordenServicio.prepararOrdenAGrabar(orden);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Orden en OrdenResponse
		try {
			ordenResponse = mapper.map(orden, OrdenResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ordenResponse, HttpStatus.CREATED);
	}

	/**
	 * Modificar los datos de un orden
	 * 
	 * @param id           identificador de un orden
	 * @param datosOrden datos a modificar del orden
	 * @return los datos de un orden modificado
	 */
	@PutMapping("/ordenes/{id}")
	public ResponseEntity<OrdenResponse> updateOrden(@PathVariable("id") long id,
			@RequestBody OrdenUpdateRequest datosOrden) {

		Orden ordenModifar = null;
		Orden ordenNuevo = null;
		OrdenResponse ordenResponse = null;

		// Convertir OrdenInsertRequest en Orden
		try {
			ordenNuevo = mapper.map(datosOrden, Orden.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el Orden Nuevo en Orden a Modificar
		try {

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		ordenModifar = ordenServicio.buscarOrdenPorId(id);

		if (Objects.nonNull(ordenModifar)) {
			ordenModifar.setClient(ordenNuevo.getClient());
			ordenModifar.setDate(ordenNuevo.getDate());
			ordenModifar = ordenServicio.grabarOrden(ordenModifar);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Convertir Orden en OrdenResponse
		try {
			ordenResponse = mapper.map(ordenModifar, OrdenResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ordenResponse, HttpStatus.OK);
	}

	/**
	 * Borrado del orden
	 * @param id identificador de un orden
	 * @return 
	 */
	@DeleteMapping("/ordenes/{id}")
	public ResponseEntity<HttpStatus> deleteOrden(@PathVariable("id") Long id) {
		try {
			ordenServicio.borrarOrden(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping(path = "/ordenes/listar")
	public @ResponseBody Iterable<Orden> listarOrdens() {
		return ordenServicio.listaOrdenes();
	}

	
}