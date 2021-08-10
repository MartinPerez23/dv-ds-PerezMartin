package ar.edu.davinci.dvdsPerezMartin.modelo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rol")

	public class Rol {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;

	    @ManyToMany(mappedBy = "roles")
	    private Set<Cliente> users;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Set<Cliente> getUsers() {
	        return users;
	    }

	    public void setUsers(Set<Cliente> users) {
	        this.users = users;
	    }
	}