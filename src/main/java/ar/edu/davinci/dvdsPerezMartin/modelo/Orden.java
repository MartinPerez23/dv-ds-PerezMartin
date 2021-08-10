package ar.edu.davinci.dvdsPerezMartin.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;






import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Generador de secuencia
//@SequenceGenerator(name = "S_ORDENES", sequenceName = "S_ORDENES", initialValue = 1, allocationSize = 1)
public class Orden implements Serializable{
	
	
	private static final long serialVersionUID = -7188002965263877395L;

	@Id
	// Usando secuencia
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ORDENES")
	// Usando autoincrement
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ord_id")
	private Long id;
	
	@ManyToOne(targetEntity = Cliente.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="ord_cli_id", referencedColumnName="cli_id")
	@NotNull(message = "Error con el usuario")
	private Cliente client;
	
//    @OneToMany(targetEntity = OrdenItem.class, mappedBy = "orden", fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="odi_ord_id", referencedColumnName="ord_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private List<Carrito> items;
    
	@Column(name = "ord_fecha")
	@NotNull(message = "*La fecha es obligatorio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public void agregarOrdenItem(Carrito ordenItem) {
		if(this.items == null) {
			this.items = new ArrayList<Carrito>();
		}
		this.items.add(ordenItem);
	}
	
	public void removerOrdenItem(Carrito ordenItem) {
		if (contieneItem(ordenItem)) {
			this.items.remove(ordenItem);
		}
	}

	public boolean contieneItem(Carrito item) {
		return items.contains(item);
	}
	
	public Orden(Cliente client,List<Carrito> carrito){
		this.client = client;
		this.items = carrito;
	}

}