package com.asoprofarma.internos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asoprofarma.internos.entity.Contacto;

@Repository
public interface IContactoDao extends CrudRepository<Contacto, Integer>{
	
	@Query("SELECT c FROM Contacto c WHERE c.subgrupo.grupo.id = :id ")
	public List<Contacto> getByGrupo(@Param("id") Integer id);
	
	@Query("SELECT c FROM Contacto c WHERE c.nombre LIKE %:busqueda% OR c.subgrupo.nombre LIKE %:busqueda% OR c.numero LIKE %:busqueda%")
	public List<Contacto> searchContacto(@Param("busqueda") String busqueda);
	
	@Query("SELECT c FROM Contacto c WHERE c.subgrupo.id = :id ")
	public List<Contacto> getBysubgrupo(@Param("id") Integer id);
	
}
