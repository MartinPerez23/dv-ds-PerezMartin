package ar.edu.davinci.dvdsPerezMartin.controlador.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoUpdateRequest {

	private Long id;

	private Long productoId;
	
	private int quantity;

}