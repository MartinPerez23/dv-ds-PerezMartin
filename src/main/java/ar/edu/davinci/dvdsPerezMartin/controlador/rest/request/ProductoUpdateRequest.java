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
public class ProductoUpdateRequest {
	
	private Long id;
	
	private String name;
	
	private BigDecimal price;

}

