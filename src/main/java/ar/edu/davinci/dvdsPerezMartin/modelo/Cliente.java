package ar.edu.davinci.dvdsPerezMartin.modelo;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
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
@Table(name="clientes", uniqueConstraints={@UniqueConstraint(columnNames={"cli_email"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Generador de secuencia
//@SequenceGenerator(name = "S_CLIENTES", sequenceName = "S_CLIENTES", initialValue = 1, allocationSize = 1)
public class Cliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3538103782513732738L;

	@Id
	// Usando secuencia
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CLIENTES" )
	// Usando autoincrement
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "cli_id")
	private Long id;
	
	@Column(name = "cli_nombre")
	@NotBlank(message = "*El nombre es obligatorio")
	@NotEmpty(message = "*Por favor ingrese su nombre")
	@NotNull
	@Size(min=2, max=30)
	private String name;
	
	@Column(name = "cli_apellido")
	@NotBlank(message = "*El apellido es obligatorio")
	@NotEmpty(message = "*Por favor ingrese su apellido")
	@NotNull
	@Size(min=2, max=30)
	private String lastName;
	
	@Column(name = "cli_email")
	@NotBlank(message = "*El email es obligatorio")
	@NotEmpty(message = "* Por favor ingrese su email")
	@Email(message = "Por favor ingrese un email válido")
	private String email;
	
	@Column(name = "cli_password")
	@NotNull
	@NotBlank(message = "*La contraseña es obligatoria")
	@NotEmpty(message = "* Por favor ingrese su contraseña")
	private String password;
    @Transient
    
    private String passwordConfirm;

    @ManyToMany
    private Set<Rol> roles;
    
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
    public String getName() {
        return name;
    }
    
}
