package com.asoprofarma.internos.service;

import java.util.List;

import com.asoprofarma.internos.entity.Grupo;



public interface IGrupoService {
	
	public Grupo findById (Integer id);
	
	public Grupo save(Grupo grupo);
	
	public Grupo delete(Grupo grupo);
	
	public List<Grupo> ListGrupo();
}
