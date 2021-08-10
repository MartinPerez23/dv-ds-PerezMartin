package ar.edu.davinci.dvdsPerezMartin.controlador.view;

	import java.text.ParseException;

	import java.util.List;
	import java.util.Locale;

	import javax.validation.Valid;
	
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.support.SessionStatus;
	import org.springframework.web.servlet.ModelAndView;

	import ar.edu.davinci.dvdsPerezMartin.convertion.DateFormatter;
	import ar.edu.davinci.dvdsPerezMartin.modelo.Orden;
	import ar.edu.davinci.dvdsPerezMartin.servicio.OrdenServicio;

	@Controller
	public class OrdenThymeleafControlador {

		private final Logger LOGGER = LoggerFactory.getLogger(OrdenThymeleafControlador.class);


		private final OrdenServicio ordenServicio;
		private final DateFormatter dateFormatter;


		@Autowired
		public OrdenThymeleafControlador(final OrdenServicio ordenServicio, DateFormatter dateFormatter) {
			super();
			this.ordenServicio = ordenServicio;
			this.dateFormatter = dateFormatter;
			
		}

		
		@RequestMapping(path = "/ordenes/listar", method = RequestMethod.GET)
		public ModelAndView ordenes(){
			
			LOGGER.info("GET - ordenes: /lista");
			
			ModelAndView modelAndView = new ModelAndView();
			List<Orden> ordenes = (List<Orden>) ordenServicio.listaOrdenes();
			
			
			
	        LOGGER.info("ordenes.size: " + ordenes.size());
	        LOGGER.info("ordenes: " + ordenes.toString());
	        
			modelAndView.addObject("ordenes", ordenes);
			modelAndView.setViewName("ordenes/listadoOrdenes");
			return modelAndView;
		}

	/**
		// Buscar Clientes
	    @RequestMapping(value = "/cliente/buscar", method = RequestMethod.GET)
	    public String initFindForm(Model model) {
			LOGGER.info("GET - initFindForm: /cliente/buscar");

	    	model.addAttribute("cliente", new Cliente());
	        return "clientes/buscarClientes";
	    }

	    
	    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
	    public String processFindForm(Cliente cliente, BindingResult result, Model model) {
	    	
			LOGGER.info("GET - processFindForm: /clientes");
			LOGGER.info("cliente: " + cliente.toString());

			Collection<Cliente> results = null;
			// allow parameterless GET request for /clientes to return all records
	        if (StringUtils.isEmpty(cliente.getLastName())) {
	            //cliente.setLastName(""); // empty string signifies broadest possible search
	            results = clienteServicio.listarClientes();
	            LOGGER.info(" por lastName null results.size: " + results.size());

	        } else {
	        	// find clientes by last name
	        	results = clienteServicio.buscarClienterPorApellido(cliente.getLastName());
	            LOGGER.info(" por lastName not null results.size: " + results.size());
	        }


	        LOGGER.info("results.size: " + results.size());

	        if (results.size() < 1) {
	            // no clientes found
	            result.rejectValue("lastName", "notFound", "not found");
	            return "clientes/buscarClientes";
	        }
	        if (results.size() > 1) {
	            // multiple clientes found
	            model.addAttribute("selections", results);
	            return "clientes/listadoClientes";
	        } else {
	            // 1 cliente found
	            cliente = results.iterator().next();
	            return "redirect:/myapp/cliente/" + cliente.getId();
	        }
	    }

	    /**
	     * Custom handler for displaying an cliente.
	     *
	     * @param clienteId the ID of the cliente to display
	     * @return a ModelMap with the model attributes for the view
	     */
	
	    @RequestMapping(value = "/ordenes/{ordenId}", method = RequestMethod.GET)
	    public ModelAndView showOrden(@PathVariable("ordenId") Long ordenID) {
	    	
			LOGGER.info("GET - showOrden: /ordenes/{ordenId}  - ordenId: " + ordenID);

	    	ModelAndView mav = new ModelAndView("ordenes/detalleOrdenes");
	        Orden orden = ordenServicio.buscarOrdenPorId(ordenID);
	        
	        LOGGER.info("orden: " + orden.toString());

	        mav.addObject(orden);
	        return mav;
	    }


	    // Crear orden
	    @RequestMapping(value = "/ordenes/nueva", method = RequestMethod.GET)
	    public String initCreationForm(Model model) {
	    	
	    	LOGGER.info("GET - initCreationForm: /ordenes/nueva");
	    		    
	    	Orden orden = new Orden();
	    	
	    	LOGGER.info("orden: "+ orden.toString());	    	
	    	
	    	model.addAttribute(orden);
	        
	        return "ordenes/crearOModificarOrdenesFormulario.html";
	    }

	    @RequestMapping(value = "/ordenes/nueva", method = RequestMethod.POST)
	    public String processCreationForm(@Valid Orden orden, BindingResult result, SessionStatus status) {	    	
	    	LOGGER.info("POST - processCreationForm: /ordenes/nueva");
	    	LOGGER.info("orden: "+ orden.toString());	    

	    	LOGGER.info("result.hasErrors(): "+ result.hasErrors());
	    	LOGGER.info("result.hasErrors(): "+ result.getFieldError().toString());

	    	if (result.hasErrors()) {	    		
	            return "ordenes/crearOModificarOrdenesFormulario";
	            
	        } else {
	            ordenServicio.grabarOrden(orden);
	            status.setComplete();
	            return "redirect:/ordenes/" + orden.getId();
	        }
	    }

	    //Modificar orden
	    @RequestMapping(value = "/ordenes/{ordenId}/editar", method = RequestMethod.GET)
	    public String initUpdateOrdenForm(@PathVariable("ordenId") Long ordenId, Model model) {
	    	
	    	LOGGER.info("GET - initUpdateOrdenForm: /orden/{ordenId}/editar");
	    	LOGGER.info("ordenId: "+ ordenId);

	    	Orden orden =  ordenServicio.buscarOrdenPorId(ordenId);
	        model.addAttribute(orden);
	        return "ordenes/crearOModificarOrdenesFormulario";
	    }

	    @RequestMapping(value = "/ordenes/{ordenId}/editar", method = RequestMethod.POST)
	    public String processUpdateOrdenForm(@Valid Orden orden, BindingResult result, SessionStatus status) {
	    	
	    	
	    	
	    	LOGGER.info("POST - initCreationForm: /orden/{ordenId}/editar");
	    	try {
				LOGGER.info("orden: "+ dateFormatter.parse(orden.toString(), Locale.ROOT));
			} catch (ParseException e) {				
				LOGGER.info(e.getMessage()+"");
			}
	    	LOGGER.info("orden: "+ orden.toString());

	    	LOGGER.info("result.hasErrors(): "+ result.hasErrors());

	    	if (result.hasErrors()) {
	            return "ordenes/crearOModificarOrdenesFormulario";
	        } else {
	            this.ordenServicio.grabarOrden(orden);
	            status.setComplete();
	            return "ordenes/" + orden.getId();
	        }
	    }

}