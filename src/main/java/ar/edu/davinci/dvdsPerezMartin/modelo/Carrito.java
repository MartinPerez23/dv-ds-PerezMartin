package ar.edu.davinci.dvdsPerezMartin.modelo;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orden_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Generador de secuencia
//@SequenceGenerator(name = "S_ORDEN_ITEMS", sequenceName = "S_ORDEN_ITEMS", initialValue = 1, allocationSize = 1)
public class Carrito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8324978842883273564L;

	@Id
	// Usando secuencia
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ORDEN_ITEMS")
	// Usando autoincrement
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "odi_id")
	private Long id;
	
	//@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "odi_ord_id")
    //private Orden orden;
	
	@ManyToOne(targetEntity = Producto.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="odi_pro_id", referencedColumnName="pro_id")
	private Producto product;
	
	@Column(name = "odi_cantidad")
	@NotNull(message = "*La cantidad no puede ser nulo")
	@Min(value=0, message = "*La cantidad no puede ser menor a cero")
	@Max(value=100, message = "*La cantidad no puede mas de 100")
	private int quantity;

	public BigDecimal calcularSubtotal() {		
		
		BigDecimal subtotal = BigDecimal.ZERO;		
			
		BigDecimal cantidad = new BigDecimal(getQuantity());
		subtotal = getProduct().getPrice().multiply(cantidad);	
			
		return  subtotal;
	}
	

	public Carrito(Producto product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}
	
}

