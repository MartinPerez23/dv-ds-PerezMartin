package ar.edu.davinci.dvdsPerezMartin.servicio;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvdsPerezMartin.modelo.Producto;
import ar.edu.davinci.dvdsPerezMartin.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoServicio.class);
	
	private final ProductoRepositorio productoRepositorio;
	
	@Autowired
	public ProductoServicio(final ProductoRepositorio productoRepositorio) {
		this.productoRepositorio = productoRepositorio;
	}
	
	public List<Producto> listarProductos() {
		return productoRepositorio.findAll();
	}
	
	public Page<Producto> listar(Pageable pageable){
		LOGGER.info("Pagegable: offset: " + pageable.getOffset() + " - pageSize:" + pageable.getPageSize());
		return productoRepositorio.findAll(pageable);
	}
	
	public Producto buscarProductoPorId(Long id) {
		Optional<Producto> productoOptional = productoRepositorio.findById(id);
		return productoOptional.orElse(null);
	}
	
	public List<Producto> buscarProductoPorNombre(String nombre) {
		LOGGER.info("buscarProductoPorNombre: nombre: " +nombre);
		List<Producto> productos = productoRepositorio.searchByName(nombre);
		if (CollectionUtils.isNotEmpty(productos)) {
			LOGGER.info("productos.size: " + productos.size());
			return productos;
		} else {
			LOGGER.info("productos empty");
			return (List<Producto>) CollectionUtils.EMPTY_COLLECTION;
		}
	}
	
	public Producto grabarProducto(Producto producto) {
		
		LOGGER.info("grabarProducto: producto: " +producto.toString());
		Producto prod =  null;
		try {
			prod =  productoRepositorio.save(producto);
		} catch (Exception e) {
			LOGGER.info("Mensaje: " + e.getMessage());
			LOGGER.info("Causa: " + e.getCause());
			e.printStackTrace();
		}
		return  prod;
	}

	public void borrarProducto(Long id) {
		productoRepositorio.deleteById(id);
	}

	public void restarStock(Long id,int cantidadComprada) {
		Producto p = this.buscarProductoPorId(id);
		p.setStock(p.getStock() - cantidadComprada);
	}
	
	public boolean sinExistencia(Long id) {
		Producto p = this.buscarProductoPorId(id);
		if (p.getStock() == 0) {
			return true;	
		}else {
			return false;
		}
		
	}

}
