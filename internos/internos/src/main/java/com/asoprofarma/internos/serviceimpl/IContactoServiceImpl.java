package com.asoprofarma.internos.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asoprofarma.internos.dao.ContactoDao;
import com.asoprofarma.internos.entity.Contacto;
import com.asoprofarma.internos.service.IContactoService;

@Service
public class IContactoServiceImpl implements IContactoService {

	@Autowired
	private ContactoDao contactoDao;
	@Override
	public Contacto findById(Integer id) {
		return contactoDao.findById(id).orElse(null);
	}

	@Override
	public Contacto save(Contacto contacto) {
		return contactoDao.save(contacto);		
	}

	@Override
	public Contacto delete(Contacto contacto) {
		contactoDao.delete(contacto);
		return contacto;
	}

	@Override
	public List<Contacto> ListContactos() {
		return (List<Contacto>) contactoDao.findAll();
	}

}
