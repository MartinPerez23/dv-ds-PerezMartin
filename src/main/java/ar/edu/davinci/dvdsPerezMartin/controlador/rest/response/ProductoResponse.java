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
public class ProductoResponse {
	
	private Long id;
	
	private String name;
	
	private BigDecimal price;

	private int stock;
}
