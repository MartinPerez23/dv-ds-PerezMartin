package ar.edu.davinci.dvdsPerezMartin.controlador.rest.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoInsertRequest {

	private Long productoId;
	
	private int quantity;
	
	private BigDecimal subtotal;
}

