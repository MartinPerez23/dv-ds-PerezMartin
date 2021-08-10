package ar.edu.davinci.dvdsPerezMartin.controlador.rest;

import java.util.Objects;

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
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.ClienteInsertRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.ClienteUpdateRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.response.ClienteResponse;
import ar.edu.davinci.dvdsPerezMartin.modelo.Cliente;
import ar.edu.davinci.dvdsPerezMartin.servicio.ClienteServicio;
import ma.glasnost.orika.MapperFacade;

@RestController
public class ClienteRestControlador extends MyRestApp {

	private final Logger LOGGER = LoggerFactory.getLogger(ClienteRestControlador.class);

	private final ClienteServicio clienteServicio;

	private final MapperFacade mapper;

	@Autowired
	public ClienteRestControlador(final ClienteServicio clienteServicio, final MapperFacade mapper) {
		this.clienteServicio = clienteServicio;
		this.mapper = mapper;
	}

	/**
	 * Lista todos los clientes.
	 * 
	 * @return
	 */
	@GetMapping(path = "/clientes")
	public Page<ClienteResponse> getClientes(Pageable pageable) {
		LOGGER.info("listar todos los clientes paginados");

		Page<ClienteResponse> clientesResponse = null;
		Page<Cliente> clientesPage = null;
		try {
			clientesPage = clienteServicio.listar(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			clientesResponse = clientesPage.map(cliente -> mapper.map(cliente, ClienteResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return clientesResponse;
	}


	/**
	 * Buscar cliente por id
	 * @param id identificador del cliente
	 * @return retorna el cliente
	 */
	@GetMapping(path = "/clientes/{id}")
	public ClienteResponse getCliente(@PathVariable Long id) {
		LOGGER.info("lista al cliente solicitado");

		ClienteResponse clientesResponse = null;
		Cliente cliente = null;
		try {
			cliente = clienteServicio.buscarClientePorId(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			clientesResponse = mapper.map(cliente, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return clientesResponse;
	}

	/**
	 * Grabar a un nuevo cliente
	 * 
	 * @param datosCliente son los datos para un nuevo cliente
	 * @return un cliente nuevo
	 */
	@PostMapping(path = "/clientes")
	public ResponseEntity<ClienteResponse> createCliente(@RequestBody ClienteInsertRequest datosCliente) {
		Cliente cliente = null;
		ClienteResponse clienteResponse = null;

		// Convertir ClienteInsertRequest en Cliente
		try {
			cliente = mapper.map(datosCliente, Cliente.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el nuevo Cliente
		try {
			cliente = clienteServicio.grabarCliente(cliente);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Cliente en ClienteResponse
		try {
			clienteResponse = mapper.map(cliente, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
	}

	/**
	 * Modificar los datos de un cliente
	 * 
	 * @param id           identificador de un cliente
	 * @param datosCliente datos a modificar del cliente
	 * @return los datos de un cliente modificado
	 */
	@PutMapping("/clientes/{id}")
	public ResponseEntity<ClienteResponse> updateCliente(@PathVariable("id") long id,
			@RequestBody ClienteUpdateRequest datosCliente) {

		Cliente clienteModifar = null;
		Cliente clienteNuevo = null;
		ClienteResponse clienteResponse = null;

		// Convertir ClienteInsertRequest en Cliente
		try {
			clienteNuevo = mapper.map(datosCliente, Cliente.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el Cliente Nuevo en Cliente a Modificar
		try {

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		clienteModifar = clienteServicio.buscarClientePorId(id);

		if (Objects.nonNull(clienteModifar)) {
			clienteModifar.setName(clienteNuevo.getName());
			clienteModifar.setLastName(clienteNuevo.getLastName());
			clienteModifar.setEmail(clienteNuevo.getEmail());
			clienteModifar = clienteServicio.grabarCliente(clienteModifar);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Convertir Cliente en ClienteResponse
		try {
			clienteResponse = mapper.map(clienteModifar, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(clienteResponse, HttpStatus.OK);
	}

	/**
	 * Borrado del cliente
	 * @param id identificador de un cliente
	 * @return 
	 */
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") Long id) {
		try {
			clienteServicio.borrarCliente(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping(path = "/clientes/listar")
	public @ResponseBody Iterable<Cliente> listarClientes() {
		return clienteServicio.listarClientes();
	}

	@GetMapping(path = "/clientes/listarXPaginas")
	public @ResponseBody Page<Cliente> getClientesPaginado() {
		Pageable pageable = PageRequest.of(0, 10);
		return clienteServicio.listar(pageable);
	}

	@GetMapping(path = "/clientes/buscar")
	public @ResponseBody Iterable<Cliente> buscarClientes(@RequestParam String apellido) {
		return clienteServicio.buscarClienterPorApellido(apellido);
	}

}
