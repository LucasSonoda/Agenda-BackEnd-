package com.asoprofarma.internos.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asoprofarma.internos.entity.Contacto;

@Repository
public interface IContactoDao extends CrudRepository<Contacto, Integer>{

}
