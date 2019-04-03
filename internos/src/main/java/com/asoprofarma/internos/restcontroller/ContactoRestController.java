package com.asoprofarma.internos.restcontroller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asoprofarma.internos.entity.Contacto;
import com.asoprofarma.internos.service.IContactoService;
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201","http://localhost:4200", "http://10.10.1.119:4200" })
@RestController
@RequestMapping("/api/contacto")
public class ContactoRestController {
	
	@Autowired
	private IContactoService contactoService;
	
	@GetMapping("/{id}")
	private ResponseEntity<?> getContactoById(@PathVariable int id) {
		
		Contacto contacto = contactoService.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(contacto == null) {
			response.put("mensaje", "El contacto con ID: ".concat(String.valueOf(id)).concat(" no existe."));
			return new ResponseEntity(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "Contacto encontrado correctamente.");
		response.put("contacto",contacto);	
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> todos(){
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos = contactoService.ListContactos();	
			response.put("clientes", contactos);
			return new ResponseEntity(contactos, HttpStatus.OK);
		}catch(Exception e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@PostMapping("/save")
	private ResponseEntity<?> save(@RequestBody Contacto contacto){
		Map<String,Object> response = new HashMap<>();
		Contacto contactoNew = null;
		try {
			contactoNew = contactoService.save(contacto);			
			response.put("contacto",contactoNew);
			response.put("mensaje", "Contacto creado correctamente");
			return new ResponseEntity(response, HttpStatus.CREATED);
		}catch(Exception e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/delete/{id}")
	private ResponseEntity<?> delete(@PathVariable int id ) {	
		Map<String, Object> response = new HashMap<>();
		Contacto contactoDel = contactoService.findById(id);
		if(contactoDel != null) {
			response.put("contacto", contactoDel);
			response.put("mensaje", "El contacto"+ contactoDel.getNombre() +"fue eliminado correctamente");
			return new ResponseEntity(response, HttpStatus.OK);
		}else {
			response.put("mensaje", "El contacto que desea eliminar no existe");
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/update")
	private Contacto update(@RequestBody Contacto contacto) {
		if(contactoService.findById(contacto.getId())!=null) {
			Contacto update = contactoService.findById(contacto.getId());
			update.setNombre(contacto.getNombre());
			update.setNumero(contacto.getNumero());
			update.setNumero_2(contacto.getNumero_2());
			update.setSubgrupo(contacto.getSubgrupo());
			
			contactoService.save(update);
		}
		return null;
	}
}
