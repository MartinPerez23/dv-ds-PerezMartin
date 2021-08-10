package ar.edu.davinci.dvdsPerezMartin.controlador.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.davinci.dvdsPerezMartin.controlador.MyApp;
import ar.edu.davinci.dvdsPerezMartin.modelo.Carrito;
import ar.edu.davinci.dvdsPerezMartin.modelo.Cliente;
import ar.edu.davinci.dvdsPerezMartin.modelo.Orden;
import ar.edu.davinci.dvdsPerezMartin.modelo.Producto;
import ar.edu.davinci.dvdsPerezMartin.servicio.CarritoServicio;
import ar.edu.davinci.dvdsPerezMartin.servicio.ClienteServicio;
import ar.edu.davinci.dvdsPerezMartin.servicio.OrdenServicio;
import ar.edu.davinci.dvdsPerezMartin.servicio.ProductoServicio;



@Controller
public class CarritoThymeleafControlador extends MyApp{

	private final Logger LOGGER = LoggerFactory.getLogger(CarritoThymeleafControlador.class);
        
	private final ProductoServicio productoServicio;	
	private final CarritoServicio carritoServicio;
	private final OrdenServicio ordenServicio;
	private final ClienteServicio clienteServicio;
		
	
	@Autowired
	public CarritoThymeleafControlador(final ProductoServicio productoServicio, final CarritoServicio carritoServicio, final OrdenServicio ordenServicio, final ClienteServicio clienteServicio) {
		super();
		this.productoServicio = productoServicio;	
		this.carritoServicio = carritoServicio;
		this.ordenServicio = ordenServicio;
		this.clienteServicio = clienteServicio;
		
	}
        
	@RequestMapping(path = "/carrito", method = RequestMethod.GET)
    public ModelAndView carrito(HttpServletRequest request) {
		LOGGER.info("GET - interfazCarrito: /carrito");
                
        ArrayList<Carrito> carrito = carritoServicio.obtenerCarrito(request);
        LOGGER.info("carrito " + carrito.size());
        ModelAndView modelAndView = new ModelAndView();
        
        float total = carritoServicio.obtenerTotal(carrito);
        LOGGER.info("total =" + total);
        modelAndView.addObject("total", total);
        modelAndView.addObject("carrito", carrito);
        modelAndView.setViewName("carrito");
		return modelAndView;
        		
    }    
	
	@RequestMapping(path = "/agregarCarrito/{productoId}/", method = RequestMethod.GET)
	
    public String agregarAlCarrito(@PathVariable Long productoId, HttpServletRequest request, RedirectAttributes redirectAttrs) {		
		
		LOGGER.info("GET - agregarAlCarrito: /agregarCarrito");
		
		ArrayList<Carrito> carrito = carritoServicio.obtenerCarrito(request);
        LOGGER.info("carrito.size: " + carrito.size());
        Producto pro = productoServicio.buscarProductoPorId(productoId);
        LOGGER.info("producto: " + pro.toString());
        
        ArrayList<Carrito> car = new ArrayList<Carrito>();
        
        car = carritoServicio.agregarProductoAlCarrito(pro,carrito);
        
        
        LOGGER.info("CARRITO FINAL.size: " + car.size());
        
        carritoServicio.guardarCarrito(car, request);
        return "redirect:/";
    }
	
	
	@RequestMapping(path = "/quitarCarrito/{productoId}/", method = RequestMethod.GET)	
    public String quitarDelCarrito(@PathVariable Long productoId, HttpServletRequest request) {
		
		LOGGER.info("GET - quitarDelCarrito: /quitarDelCarrito/productoId");
        ArrayList<Carrito> carrito = carritoServicio.obtenerCarrito(request);
        LOGGER.info("carrito.size: " + carrito.size());
        ArrayList<Carrito> car = carritoServicio.sacarProductoDelCarrito(carrito,productoId);
        LOGGER.info("car.size: " + car.size());        
        carritoServicio.guardarCarrito(car, request);
        
        return "redirect:/carrito";
    }

	@RequestMapping(path = "/limpiar", method = RequestMethod.GET)
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {		
		carritoServicio.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta cancelada")
                .addFlashAttribute("clase", "info");
        LOGGER.info("cancelarVenta: " + request.getSession().getAttribute("Carrito"));
        return "redirect:/carrito";
    }
    
	@RequestMapping(path = "/comprar", method = RequestMethod.GET)
	public String processComprarForm(HttpServletRequest request) {
		
		LOGGER.info("POST - processComprarForm: /comprar" );
		
        ArrayList<Carrito> car = carritoServicio.obtenerCarrito(request);
        
        LOGGER.info("carrito " + car.size());
              
        if (car == null || car.size() <= 0) {
            return "redirect:/carrito";
        }else {
        	
        	carritoServicio.grabarCarrito(car);
        	LOGGER.info("carrito: " + car.toString());
        	        	
			        	
        	LOGGER.info("Compra realizada");        	
            String user = request.getUserPrincipal().getName();
            LOGGER.info("user: " + user.toString());
            
            Cliente cliente = clienteServicio.buscarClientePorEmail(user);
            LOGGER.info("cliente: " + cliente.toString());
            
            Orden orden = new Orden(cliente,car);
            LOGGER.info("orden: " + orden.toString());
            
            ordenServicio.prepararOrdenAGrabar(orden);
            
            carritoServicio.limpiarCarrito(request);
        	return "redirect:/carrito";
        }

        
        
        //prepararOrdenAGrabar(Cliente);
        //ordenServicio.grabarOrden(new Orden(ClientID,));        
        
        //carritoServicio.grabarCarrito(new Carrito(car));     
               
        //return "redirect:/carrito";
    }

	
}




