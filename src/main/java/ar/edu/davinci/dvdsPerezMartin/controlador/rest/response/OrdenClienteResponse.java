package ar.edu.davinci.dvdsPerezMartin.controlador.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenClienteResponse {
	
	private String name;
	
	private String lastName;
	
}

