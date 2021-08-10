package ar.edu.davinci.dvdsPerezMartin.servicio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvdsPerezMartin.modelo.Carrito;
import ar.edu.davinci.dvdsPerezMartin.modelo.Producto;
import ar.edu.davinci.dvdsPerezMartin.repositorio.CarritoRepositorio;
import ar.edu.davinci.dvdsPerezMartin.repositorio.OrdenRepositorio;
import ar.edu.davinci.dvdsPerezMartin.repositorio.ProductoRepositorio;

@Service
public class CarritoServicio {

	private final Logger LOGGER = LoggerFactory.getLogger(CarritoServicio.class);

	private final OrdenRepositorio ordenRepositorio;

	private final CarritoRepositorio carritoRepositorio;
	
	private final ProductoServicio productoServicio;

	@Autowired
	public CarritoServicio(final CarritoRepositorio carritoRepositorio, 
			final OrdenRepositorio ordenRepositorio,
			
			final ProductoServicio productoServicio) {
		this.ordenRepositorio = ordenRepositorio;
		this.carritoRepositorio = carritoRepositorio;		
		this.productoServicio = productoServicio;
	}
	
	
	
public void grabarCarrito(ArrayList<Carrito> carrito) {
		
		LOGGER.info("grabarCarrito: carrito: " +carrito.toString());
		
		try {
			
			for (Carrito item : carrito) {
				Producto producto = productoServicio.buscarProductoPorId(item.getProduct().getId());
				if (Objects.nonNull(producto)) {
					item.setProduct(producto);					
				}	
			}
			for (Carrito item : carrito) {
				
				carritoRepositorio.save(item);
				LOGGER.info("CARRITO A GRABAR: " + item.toString());	
			}
			
			
			
			
		} catch (Exception e) {
			LOGGER.info("Mensaje: " + e.getMessage());
			LOGGER.info("Causa: " + e.getCause());
			e.printStackTrace();
		}		
	}
	
	
	public ArrayList<Carrito> obtenerCarrito(HttpServletRequest request) {
		ArrayList<Carrito> carrito = (ArrayList<Carrito>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<Carrito>();
        }
        return carrito;
    }

    public void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
    }
    
    public void guardarCarrito(ArrayList<Carrito> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }


	public float obtenerTotal(ArrayList<Carrito> carrito) {
		 float total = 0;
		 
		 for (Carrito p: carrito) total += p.getProduct().getPrice().floatValue();
        
		 return total;
		
	}
	
	public ArrayList<Carrito> sacarProductoDelCarrito(ArrayList<Carrito> carrito, Long id){
		
		LOGGER.info("como entra el carrito: " + carrito.size());
		//busca en todo el carrito el item
		for (Carrito c : carrito) {
        	if(c.getProduct().getId() == id) {
        		    //si lo encuentra producto, saca 1
        		c.setQuantity(c.getQuantity() - 1);
        			//si no hay mas cantidad de ese producto en el carrito, se elimina el producto del carrito
        		if(c.getQuantity() == 0) {        			
        			carrito.remove(c);        			
        			LOGGER.info("carrito.sacardelcarrito " + carrito.size());
        		}
        	}        	
        }
		return carrito;
	}


	public ArrayList<Carrito> agregarProductoAlCarrito(Producto pro, ArrayList<Carrito> carrito) {
				
				        
		ArrayList<Carrito> car = new ArrayList<Carrito>();
		car = (ArrayList) carrito.clone();		
			LOGGER.info("agregarProductosAlCarrito: " + pro.toString());
			LOGGER.info("agregarProductosAlCarrito: " + carrito.toString());
			
			
			
			if(carrito.isEmpty()) {
				
				carrito.add(new Carrito(pro,1));
				
			}else {
				
				int item = -1;
				int i = 0;
				
				LOGGER.info("VALOR I: " + i);
				LOGGER.info("VALOR Iten: " + item);
				for  (i = 0; i < car.size(); i++) {
					
					if(car.get(i).getProduct().getId() ==  pro.getId()){
						item = car.get(i).getProduct().getId().intValue();
					}
					
				} 
				
				if(item == -1) {
					//no tiene ese producto
					LOGGER.info("valorItem: " + item);
					carrito.add(new Carrito(pro,1));
				}else {
					//tiene ese producto
					int cantidad = carrito.get(item).getQuantity();		
					int stock = carrito.get(item).getProduct().getStock();
					//compruebo stock
					if(cantidad < stock) {				            			
						LOGGER.info("sumaCantidad: " + cantidad);
						
						carrito.get(item).setQuantity(cantidad + 1);						
							
					}else {
    			//mensaje de no se puede agregar mas del mismo producto, no hay mas stock
					}
					
				}
					i= 0;
					item = 0;
					
			}
		return carrito;
		
	}
}
