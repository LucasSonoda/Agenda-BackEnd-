package com.asoprofarma.internos.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asoprofarma.internos.dao.SubgrupoDao;
import com.asoprofarma.internos.entity.Subgrupo;
import com.asoprofarma.internos.service.ISubgrupoService;

@Service
public class ISubgrupoServiceImpl implements ISubgrupoService {

	
@Autowired	
private SubgrupoDao subgrupoDao;
	
	@Override
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
