package ar.edu.davinci.dvdsPerezMartin.controlador.rest.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoResponse {
	
	private Long id;
	
	private OrdenProductoResponse producto;
	
	private int quantity;

	private BigDecimal subtotal;
}

