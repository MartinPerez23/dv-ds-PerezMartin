package ar.edu.davinci.dvdsPerezMartin.controlador.rest.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenUpdateRequest {
			
	private Long id;

	private Long clienteId;
	
	private List<CarritoUpdateRequest> items;
	
	private BigDecimal total;
}