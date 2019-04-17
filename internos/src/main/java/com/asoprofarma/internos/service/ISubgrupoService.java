package com.asoprofarma.internos.service;

import java.util.List;

import com.asoprofarma.internos.entity.Subgrupo;



public interface ISubgrupoService {
	
	public Subgrupo findById (Integer id);
	
	public Subgrupo save(Subgrupo subgrupo);
	
	public void delete(Subgrupo subgrupo);
	
	public List<Subgrupo> ListSubgrupo();
}
