package com.asoprofarma.internos.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asoprofarma.internos.entity.Grupo;

@Repository
public interface IGrupoDao extends CrudRepository<Grupo, Integer> {

}
