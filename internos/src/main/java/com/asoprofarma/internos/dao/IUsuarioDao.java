package com.asoprofarma.internos.dao;

import org.springframework.data.repository.CrudRepository;

import com.asoprofarma.internos.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {
	
	public Usuario findByUsername(String username);
}
