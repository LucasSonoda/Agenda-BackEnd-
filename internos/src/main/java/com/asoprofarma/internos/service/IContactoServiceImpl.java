package com.asoprofarma.internos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoprofarma.internos.dao.IContactoDao;
import com.asoprofarma.internos.entity.Contacto;

@Service
public class IContactoServiceImpl implements IContactoService {

	@Autowired
	private IContactoDao contactoDao;
	
	@Override
	@Transactional
	public Contacto findById(Integer id) {
		return contactoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Contacto save(Contacto contacto) {
		return contactoDao.save(contacto);		
	}

	@Override
	@Transactional
	public Contacto delete(Contacto contacto) {
		contactoDao.delete(contacto);
		return contacto;
	}

	@Override
	@Transactional
	public List<Contacto> ListContactos() {
		return (List<Contacto>) contactoDao.findAll();
	}

}
