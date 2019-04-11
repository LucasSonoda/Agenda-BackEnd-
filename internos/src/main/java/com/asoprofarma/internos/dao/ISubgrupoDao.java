package com.asoprofarma.internos.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asoprofarma.internos.entity.Subgrupo;

@Repository
public interface ISubgrupoDao extends CrudRepository<Subgrupo, Integer>{



}
