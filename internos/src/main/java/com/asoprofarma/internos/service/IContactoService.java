package com.asoprofarma.internos.service;

import java.util.List;

import com.asoprofarma.internos.entity.Contacto;

public interface IContactoService {
	
	public Contacto findById (Integer id);
	
	public Contacto save(Contacto contacto);
	
	public Contacto delete(Contacto contacto);
	
	public List<Contacto> ListContactos();
	
	public List<Contacto> getByGrupo(Integer id);
	
	public List<Contacto> getBySubgrupo(Integer id);
	
	public List<Contacto> searchContacto(String busqueda);
}
