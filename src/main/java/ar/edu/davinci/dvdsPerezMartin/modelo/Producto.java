package ar.edu.davinci.dvdsPerezMartin.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Generador de secuencia
//@SequenceGenerator(name = "S_PRODUCTOS", sequenceName = "S_PRODUCTOS", initialValue = 1, allocationSize = 1)
public class Producto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8847257075340594766L;

	@Id
	// Usando secuencia
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PRODUCTOS")
	// Usando autoincrement
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "pro_id")
	private Long id;
	
	@Column(name = "pro_nombre")
	@NotBlank(message = "*El nombre es obligatorio")
	@NotEmpty(message = "*Por favor ingrese el nombre del producto")
	@NotNull(message = "*El nombre no puede ser nulo")
	@Size(min=2, max=45)
	private String name;
	
	@Column(name = "pro_precio")
	@NotNull(message = "*El precio no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=8, fraction=2)
	private BigDecimal price;
	
	@Column(name = "pro_stock")
	@NotNull(message = "*El stock no puede ser nulo")
	@Min(value=0, message = "*La cantidad no puede ser menor a cero")
	@Max(value=100, message = "*La cantidad no puede mas de 100")
	private int stock;
		
}
