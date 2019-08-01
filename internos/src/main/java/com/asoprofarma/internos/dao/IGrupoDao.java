package com.asoprofarma.internos.dao;

import java.util.List;
import java.util.TreeMap;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asoprofarma.internos.entity.Grupo;

@Repository
public interface IGrupoDao extends CrudRepository<Grupo, Integer> {
	
	
	@Query("SELECT g.id, g.nombre FROM Grupo g")
	public List<Object[]> getGrupos();
	
}
