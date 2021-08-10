package ar.edu.davinci.dvdsPerezMartin.controlador.view;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.davinci.dvdsPerezMartin.controlador.MyApp;
import ar.edu.davinci.dvdsPerezMartin.modelo.Producto;
import ar.edu.davinci.dvdsPerezMartin.servicio.ProductoServicio;

@Controller
public class ProductoThymeleafControlador extends MyApp{

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoThymeleafControlador.class);


	private final ProductoServicio prodServ;


	@Autowired
	public ProductoThymeleafControlador(final ProductoServicio prodServ) {
		super();
		this.prodServ = prodServ;
	}


	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView productos(){
		
		LOGGER.info("GET - producto: /producto/listar");
		
		ModelAndView modelAndView = new ModelAndView();
		List<Producto> productos = prodServ.listarProductos();
        
		LOGGER.info("productos.size: " + productos.size());

		modelAndView.addObject("selections", productos);
		modelAndView.setViewName("index");
		return modelAndView;
	}
	@RequestMapping(path = "/panel", method = RequestMethod.GET)
	public ModelAndView listaProductos(){
		
		LOGGER.info("GET - listaProductos: /panel");
		
		ModelAndView modelAndView = new ModelAndView();
		List<Producto> productos = prodServ.listarProductos();
        
		LOGGER.info("productos.size: " + productos.size());

		modelAndView.addObject("selections", productos);
		modelAndView.setViewName("panel");
		return modelAndView;
	}

	// Buscar Producto
	/**
    @RequestMapping(value = "/producto/buscar", method = RequestMethod.GET)
    public String initFindForm(Model model) {
    	
		LOGGER.info("GET - initFindForm: /producto/buscar");

    	model.addAttribute("producto", new Producto());
    	
        return "productos/buscarProductos";
    }**/

	/**
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String processFindForm(Producto producto, BindingResult result, Model model) {
    	
		LOGGER.info("GET - processFindForm: /productos");
		LOGGER.info("producto: " + producto.toString());

		Collection<Producto> results = null;
		
        if (!StringUtils.isEmpty(producto.getName())) {
            
        	results = prodServ.listarProductos();

        } 

        LOGGER.info("results.size: " + results.size());

        if (results.size() < 1) {
            // no productos found
            result.rejectValue("name", "notFound", "not found");
            return "productos/buscarClientes";
        }
        if (results.size() > 1) {
            // multiple productos found
            model.addAttribute("selections", results);
            return "productos/listadoClientes";
        } else {
            // 1 producto found
        	producto = results.iterator().next();
            return "redirect:/producto/" + producto.getId();
        }
    }

    /**
     * Custom handler for displaying an cliente.
     *
     * @param clienteId the ID of the cliente to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/producto/{productoId}", method = RequestMethod.GET)
    public ModelAndView showProducto(@PathVariable("productoId") Long productoId) {
    	
		LOGGER.info("GET - showProducto: /producto/{productoId}  - productoId: " + productoId);

    	ModelAndView mav = new ModelAndView("redirect:/productos/detalleProductos");
        Producto producto = prodServ.buscarProductoPorId(productoId);
        
        LOGGER.info("producto: " + producto.toString());

        mav.addObject(producto);
        return mav;
    }


    // Crear producto
    @RequestMapping(value = "/producto/nuevo", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
    	
    	LOGGER.info("GET - initCreationForm: /producto/nuevo");
    	
    	Producto producto = new Producto();
    	
    	LOGGER.info("producto: "+ producto.toString());

        model.addAttribute(producto);
        return "productos/crearOModificarProductosFormulario.html";
    }

    @RequestMapping(value = "/producto/nuevo", method = RequestMethod.POST)
    public String processCreationForm(@Valid Producto producto, BindingResult result, SessionStatus status) {
    	
    	LOGGER.info("POST - processCreationForm: /producto/nuevo");
    	LOGGER.info("producto: "+ producto.toString());

    	LOGGER.info("result.hasErrors(): "+ result.hasErrors());

    	if (result.hasErrors()) {
            return "productos/crearOModificarProductosFormulario";
        } else {
        	prodServ.grabarProducto(producto);
            status.setComplete();
            return "redirect:/producto/" + producto.getId();
        }
    }

    //Modificar producto
    @RequestMapping(value = "/producto/{productoId}/editar", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("productoId") Long productoId, Model model) {
    	
    	LOGGER.info("GET - initUpdateForm: /producto/{productoId}/editar");
    	LOGGER.info("productoId: "+ productoId);

    	Producto producto =  prodServ.buscarProductoPorId(productoId);
        model.addAttribute(producto);
        return "productos/crearOModificarProductosFormulario";
    }

    @RequestMapping(value = "/producto/{productoId}/editar", method = RequestMethod.POST)
    public String processUpdateForm(@Valid Producto producto, BindingResult result, SessionStatus status) {
    	
    	LOGGER.info("POST - processUpdateForm: /producto/{productoId}/editar");
    	LOGGER.info("producto: "+ producto.toString());

    	LOGGER.info("result.hasErrors(): "+ result.hasErrors());

    	if (result.hasErrors()) {
            return "productos/crearOModificarProductosFormulario";
        } else {
            this.prodServ.grabarProducto(producto);
            status.setComplete();
            return "producto/" + producto.getId();
        }
    }

}
