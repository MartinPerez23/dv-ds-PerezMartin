package ar.edu.davinci.dvdsPerezMartin.controlador.rest.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenResponse {
	
	private Long id;
	
	private OrdenClienteResponse cliente;
	
	private List<CarritoResponse> items;
	
	private Date date;

}