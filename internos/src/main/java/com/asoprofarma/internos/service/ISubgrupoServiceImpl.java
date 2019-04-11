package com.asoprofarma.internos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoprofarma.internos.dao.ISubgrupoDao;
import com.asoprofarma.internos.entity.Subgrupo;

@Service
public class ISubgrupoServiceImpl implements ISubgrupoService {

	
@Autowired	
private ISubgrupoDao subgrupoDao;
	
	@Override
	@Transactional
	public Subgrupo findById(Integer id) {
		return  subgrupoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Subgrupo save(Subgrupo subgrupo) {
		return subgrupoDao.save(subgrupo);
	}

	@Override
	@Transactional
	public Subgrupo delete(Subgrupo subgrupo) {
		subgrupoDao.delete(subgrupo);
		return subgrupo;
	}

	@Override
	public List<Subgrupo> ListSubgrupo() {
		return (List<Subgrupo>) subgrupoDao.findAll();
	}



}
