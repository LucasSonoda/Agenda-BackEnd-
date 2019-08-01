package com.asoprofarma.internos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoprofarma.internos.dao.IGrupoDao;
import com.asoprofarma.internos.entity.Grupo;

@Service
public class IGrupoServiceImpl implements IGrupoService {

	
@Autowired
private IGrupoDao grupoDao;

	@Override
	@Transactional
	public Grupo findById(Integer id) {
		return grupoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Grupo save(Grupo grupo) {
		return grupoDao.save(grupo);
	}

	@Override
	@Transactional
	public Grupo delete(Grupo grupo) {
		grupoDao.delete(grupo);
		return grupo;
	}

	@Override
	@Transactional
	public List<Grupo> ListGrupo() {
		return (List<Grupo>)grupoDao.findAll();
	}

	@Override
	public List<Grupo> ListGrupoIdNombre() {
		List<Object[]> rows = grupoDao.getGrupos();
		List<Grupo> grupos= new ArrayList<>();

		rows.forEach(o->{
			Grupo g = new Grupo();
			g.setId((int)o[0]);
			g.setNombre((String) o[1]);
			grupos.add(g);
		});
		
		
		return grupos;
	}

}
