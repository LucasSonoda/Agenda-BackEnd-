package com.asoprofarma.internos.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	
	private static final Logger logger = LogManager.getLogger(ContactoRestController.class.getName());
	
	@Autowired
	private IContactoService contactoService;
	
	@GetMapping("/{id}")
	private ResponseEntity<?> getContactoById(@PathVariable int id) {
		
		logger.info("Iniciando Busqueda");
		
		Contacto contacto = contactoService.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(contacto == null) {
			response.put("mensaje", "El contacto con ID: ".concat(String.valueOf(id)).concat(" no existe."));
			return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "Contacto encontrado correctamente.");
		response.put("contacto",contacto);	
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> todos(){
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos = contactoService.ListContactos();	
			response.put("clientes", contactos);
			return new ResponseEntity<Object>(contactos, HttpStatus.OK);
		}catch(DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@PostMapping("/save")
	private ResponseEntity<?> save(@Valid @RequestBody Contacto contacto, BindingResult result){
		
		Map<String,Object> response = new HashMap<>();
		Contacto contactoNew = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err-> "El campo '"+err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);		
		}
		
		
		try {
			contactoNew = contactoService.save(contacto);			
			response.put("contacto",contactoNew);
			response.put("mensaje", "Contacto creado correctamente");
			return new ResponseEntity<Object>(response, HttpStatus.CREATED);
		}catch(DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}
	
	@DeleteMapping("/delete/{id}")
	private ResponseEntity<?> delete(@PathVariable int id ) {	
		Map<String, Object> response = new HashMap<>();
		Contacto contactoDel = contactoService.findById(id);
		if(contactoDel != null) {
			response.put("contacto", contactoDel);
			response.put("mensaje", "El contacto"+ contactoDel.getNombre() +"fue eliminado correctamente");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}else {
			response.put("mensaje", "El contacto que desea eliminar no existe");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/update")
	private ResponseEntity<?> update(@Valid @RequestBody Contacto contacto, BindingResult result) {
		Map<String,Object> response = new HashMap<>();
		Contacto update = null;
		
		if(contactoService.findById(contacto.getId())!=null) {		
			
			if(result.hasErrors()) {
				List<String> errors = result.getFieldErrors()
						.stream()
						.map(err-> "El campo '"+err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());
				response.put("errors", errors);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);		
			}
			
			try {
				update = contactoService.findById(contacto.getId());
				update.setNombre(contacto.getNombre());
				update.setNumero(contacto.getNumero());
				update.setNumero_2(contacto.getNumero_2());
				update.setSubgrupo(contacto.getSubgrupo());
				
				response.put("contacto", contactoService.save(update) );
				response.put("mensaje", "Contacto "+ update.getNombre()+" actualizado correctamente");
	
				return new ResponseEntity<Object>(response,HttpStatus.OK);
			}catch(DataAccessException e) {
				response.put("mensaje", e.getMostSpecificCause());
				return new ResponseEntity<Object>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}else {
			response.put("mensaje", "El contacto que quiere actualizar no existe");
			return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
		}
	
	}
}
