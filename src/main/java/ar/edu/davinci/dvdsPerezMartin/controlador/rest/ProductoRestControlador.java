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
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.ProductoInsertRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.request.ProductoUpdateRequest;
import ar.edu.davinci.dvdsPerezMartin.controlador.rest.response.ProductoResponse;
import ar.edu.davinci.dvdsPerezMartin.modelo.Producto;
import ar.edu.davinci.dvdsPerezMartin.servicio.ProductoServicio;
import ma.glasnost.orika.MapperFacade;

@RestController
public class ProductoRestControlador extends MyRestApp {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoRestControlador.class);

	private final ProductoServicio productoServicio;

	private final MapperFacade mapper;

	@Autowired
	public ProductoRestControlador(final ProductoServicio productoServicio, final MapperFacade mapper) {
		this.productoServicio = productoServicio;
		this.mapper = mapper;
	}
	
	/**
	 * Lista todos los productos.
	 * 
	 * @return
	 */
	@GetMapping(path = "/productos")
	public Page<ProductoResponse> getProductos(Pageable pageable) {
		LOGGER.info("listar todos los productos paginados");

		Page<ProductoResponse> productosResponse = null;
		Page<Producto> productosPage = null;
		try {
			productosPage = productoServicio.listar(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			productosResponse = productosPage.map(producto -> mapper.map(producto, ProductoResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return productosResponse;
	}


	/**
	 * Buscar producto por id
	 * @param id identificador del producto
	 * @return retorna el producto
	 */
	@GetMapping(path = "/productos/{id}")
	public ProductoResponse getProducto(@PathVariable Long id) {
		LOGGER.info("lista al producto solicitado");

		ProductoResponse productosResponse = null;
		Producto producto = null;
		try {
			producto = productoServicio.buscarProductoPorId(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			productosResponse = mapper.map(producto, ProductoResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return productosResponse;
	}

	/**
	 * Grabar a un nuevo producto
	 * 
	 * @param datosProducto son los datos para un nuevo producto
	 * @return un producto nuevo
	 */
	@PostMapping(path = "/productos")
	public ResponseEntity<ProductoResponse> createProducto(@RequestBody ProductoInsertRequest datosProducto) {
		LOGGER.info("Producto a insertar: " + datosProducto.toString());
		
		Producto producto = null;
		ProductoResponse productoResponse = null;

		// Convertir ProductoInsertRequest en Producto
		try {
			producto = mapper.map(datosProducto, Producto.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el nuevo Producto
		try {
			producto = productoServicio.grabarProducto(producto);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Producto en ProductoResponse
		try {
			productoResponse = mapper.map(producto, ProductoResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(productoResponse, HttpStatus.CREATED);
	}

	/**
	 * Modificar los datos de un producto
	 * 
	 * @param id           identificador de un producto
	 * @param datosProducto datos a modificar del producto
	 * @return los datos de un producto modificado
	 */
	@PutMapping("/productos/{id}")
	public ResponseEntity<ProductoResponse> updateProducto(@PathVariable("id") long id,
			@RequestBody ProductoUpdateRequest datosProducto) {

		Producto productoModifar = null;
		Producto productoNuevo = null;
		ProductoResponse productoResponse = null;

		// Convertir ProductoInsertRequest en Producto
		try {
			productoNuevo = mapper.map(datosProducto, Producto.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el Producto Nuevo en Producto a Modificar
		try {

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		productoModifar = productoServicio.buscarProductoPorId(id);

		if (Objects.nonNull(productoModifar)) {
			productoModifar.setName(productoNuevo.getName());
			productoModifar.setPrice(productoNuevo.getPrice());
			productoModifar = productoServicio.grabarProducto(productoModifar);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Convertir Producto en ProductoResponse
		try {
			productoResponse = mapper.map(productoModifar, ProductoResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(productoResponse, HttpStatus.OK);
	}

	/**
	 * Borrado del producto
	 * @param id identificador de un producto
	 * @return 
	 */
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") Long id) {
		try {
			productoServicio.borrarProducto(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping(path = "/productos/listar")
	public @ResponseBody Iterable<Producto> listarProductos() {
		return productoServicio.listarProductos();
	}

	@GetMapping(path = "/productos/listarXPaginas")
	public @ResponseBody Page<Producto> getProductosPaginado() {
		Pageable pageable = PageRequest.of(0, 10);
		return productoServicio.listar(pageable);
	}

	@GetMapping(path = "/productos/buscar")
	public @ResponseBody Iterable<Producto> buscarProductos(@RequestParam String nombre) {
		return productoServicio.buscarProductoPorNombre(nombre);
	}

}

