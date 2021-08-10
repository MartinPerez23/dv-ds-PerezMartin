package ar.edu.davinci.dvdsPerezMartin.repositorio;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.dvdsPerezMartin.modelo.Carrito;

@Repository("carritoRepositorio")
public interface CarritoRepositorio extends JpaRepository<Carrito, Long>{

	//public List<Orden> searchByName(@Param("nombre") String name) throws DataAccessException;

}

