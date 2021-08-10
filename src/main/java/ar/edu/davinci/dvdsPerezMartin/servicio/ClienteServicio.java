package ar.edu.davinci.dvdsPerezMartin.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import ar.edu.davinci.dvdsPerezMartin.modelo.Cliente;
import ar.edu.davinci.dvdsPerezMartin.repositorio.ClienteRepositorio;
import ar.edu.davinci.dvdsPerezMartin.repositorio.RolRepositorio;

@Service
public class ClienteServicio implements ClienteInterfazServicio{

	@Autowired
    private ClienteRepositorio userRepository;
    @Autowired
    private RolRepositorio roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Cliente user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public Cliente findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	
	private final Logger LOGGER = LoggerFactory.getLogger(ClienteServicio.class);
	 
	
	private final ClienteRepositorio clienteRepositorio;

	@Autowired
	public ClienteServicio(final ClienteRepositorio clienteRepositorio) {
		this.clienteRepositorio = clienteRepositorio;
	}

	public List<Cliente> listarClientes() {
		return clienteRepositorio.findAll();
	}

	public Page<Cliente> listar(Pageable pageable) {
		LOGGER.info("Pagegable: offset: " + pageable.getOffset() + " - pageSize:" + pageable.getPageSize());
		return clienteRepositorio.findAll(pageable);
	}
	
	public Cliente buscarClientePorEmail(String email) {

		Optional<Cliente> cliente = Optional.ofNullable(clienteRepositorio.findByEmail(email));
		if (cliente.isPresent()) {
			return cliente.get();
		}
		return null;
	}
	
	public Cliente buscarClientePorId(Long clienteId) {

		Optional<Cliente> cliente = clienteRepositorio.findById(clienteId);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		return null;
	}
	
	public List<Cliente> buscarClienterPorApellido(String apellido) {
		LOGGER.info("buscarClienterPorApellido: apellido: " + apellido);
		List<Cliente> clientes = clienteRepositorio.searchByLastName(apellido);
		if (CollectionUtils.isNotEmpty(clientes)) {
			LOGGER.info("clientes.size: " + clientes.size());
			return clientes;
		} else {
			LOGGER.info("clientes empty");
			return (List<Cliente>) CollectionUtils.EMPTY_COLLECTION;
		}
	}

	public Cliente grabarCliente(Cliente cliente) {
		return clienteRepositorio.save(cliente);
	}


	
	public void borrarCliente(Long clienteId) {
		clienteRepositorio.deleteById(clienteId);
	}

	public boolean noSeRepiteEmail(Cliente cliente) {
		 
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes = this.listarClientes(); 
		
			for (Cliente c : clientes) {
				if(!cliente.getEmail().isEmpty() && c.getEmail().equalsIgnoreCase(cliente.getEmail())) {
				
					return true;
					
				}
				
			}
			
		return false;
		
	}

}


