package ar.edu.davinci.dvdsPerezMartin.servicio;

import ar.edu.davinci.dvdsPerezMartin.modelo.Cliente;

public interface ClienteInterfazServicio {
	void save(Cliente user);

	Cliente findByEmail(String email);
}