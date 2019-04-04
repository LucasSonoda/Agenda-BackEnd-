package com.asoprofarma.internos.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asoprofarma.internos.dao.GrupoDao;
import com.asoprofarma.internos.entity.Grupo;
import com.asoprofarma.internos.service.IGrupoService;

@Service
public class IGrupoServiceImpl implements IGrupoService {

	
@Autowired
private GrupoDao grupoDao;

	@Override
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
	public List<Grupo> ListGrupo() {
		return (List<Grupo>)grupoDao.findAll();
	}

}
