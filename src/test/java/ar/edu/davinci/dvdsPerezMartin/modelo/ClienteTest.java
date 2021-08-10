package ar.edu.davinci.dvdsPerezMartin.modelo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ar.edu.davinci.dvdsPerezMartin.repositorio.ClienteRepositorio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ClienteTest {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	

	@Test
	public void listarClientes() {
		List<Cliente> clientes = clienteRepositorio.findAll();
		listarTodosClientes(clientes);
	}

	private void listarTodosClientes(List<Cliente> clientes) {
		System.out.println("Clientes");
		clientes.forEach(System.out::println);
	}
	
	@Test
	public void modificarCliente() {
		Optional<Cliente> clienteOptional = clienteRepositorio.findById(1L);
		String actualName = null;
		if (clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.get();
			actualName = cliente.getName();
			String newName = actualName + "-" + actualName;
			cliente.setName(newName);
			System.out.println("Cliente Modificado:" + cliente.toString());
			clienteRepositorio.save(cliente);
			assertThat(cliente.getName(), equalTo(newName));
		}
	}

	@Test
	public void modificarUKCliente() {
		Optional<Cliente> clienteOptional = clienteRepositorio.findById(1L);
		if (clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.get();
			cliente.setEmail("xyz@email.com");
			System.out.println("Cliente Modificado:" + cliente.toString());
			Exception exception = assertThrows(Exception.class, 
					() -> {
						clienteRepositorio.save(cliente);
						});

			String actualCause = exception.getCause().toString();
			String actualMessage = exception.getMessage();
			
			String expectedMessage = "ON PUBLIC.CLIENTES(CLI_EMAIL)";
			String expectedCause = "org.hibernate.exception.ConstraintViolationException";
			
			assertTrue(actualMessage.contains(expectedMessage));
			assertTrue(actualCause.getClass().getName().contains(expectedCause.getClass().getName()));
		}
	}
	
}

