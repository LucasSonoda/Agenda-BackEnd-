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
import org.springframework.security.access.annotation.Secured;
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




@RestController
@RequestMapping("/contacto")
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201","http://localhost:4200", "http://10.10.1.119:4200" })
public class ContactoRestController {
	
	private static final Logger logger = LogManager.getLogger(ContactoRestController.class);
	
	@Autowired
	private IContactoService contactoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getContactoById(@PathVariable int id) {
		
		logger.info("Buscando al contacto con ID: "+String.valueOf(id));
		
		Contacto contacto = contactoService.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(contacto == null) {
			
			logger.warn("##El contacto con ID: "+String.valueOf(id)+" no existe."+ " HttpStatus: "+String.valueOf(HttpStatus.NOT_FOUND));
			response.put("mensaje", "El contacto con ID: ".concat(String.valueOf(id)).concat(" no existe."));
			return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
		}
		
		response.put("mensaje", "Contacto encontrado correctamente.");
		response.put("contacto",contacto);
		logger.info("Se encontro al contacto: "+contacto.getNombre()+". HttpStatus: "+ String.valueOf(HttpStatus.OK));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> todos(){
		logger.info("Solicitando todos los Contactos...");
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos =  contactoService.ListContactos();	
			response.put("contactos", contactos);
			response.put("mensaje", "Contactos retornados correctamente.");
			logger.info("Contactos retornados correctamente. HttpStatus: "+String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}catch(DataAccessException e) {
			logger.warn("##Error al intentar acceder a los contactos mensaje: "+e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("/search/{busqueda}")
	public ResponseEntity<?> search(@PathVariable String busqueda){
		logger.info("Buscando "+busqueda+" entre los Contactos...");
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos =  contactoService.searchContacto(busqueda);
			response.put("contactos", contactos);
			response.put("mensaje", "Contactos retornados correctamente.");
			logger.info("Contactos retornados correctamente. HttpStatus: "+String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}catch(DataAccessException e) {
			logger.warn("##Error al intentar buscar los contactos mensaje: "+e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/bysubgrupo/{id}")
	public ResponseEntity<?> bySubGrupo(@PathVariable Integer id){
		logger.info("Buscando los contactos del subgrupo con ID = "+ id);
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos = contactoService.getBySubgrupo(id);
			response.put("contactos",contactos);
			response.put("mensaje","Contactos retornados correctamente.");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}catch(DataAccessException e) {
			logger.warn("##Error al intentar acceder a los contactos mensaje: "+e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/bygrupo/{id}")
	public ResponseEntity<?> byGrupo(@PathVariable Integer id){
		logger.info("Buscando los contactos del grupo con ID = "+ id);
		List<Contacto> contactos = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			contactos = contactoService.getByGrupo(id);
			response.put("contactos",contactos);
			response.put("mensaje","Contactos retornados correctamente.");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}catch(DataAccessException e) {
			logger.warn("##Error al intentar acceder a los contactos mensaje: "+e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody Contacto contacto, BindingResult result){
		
		Map<String,Object> response = new HashMap<>();
		Contacto contactoNew = null;
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err-> "El campo '"+err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			logger.warn("##Error en los datos del Contacto.");
			errors.forEach(error->{
				logger.warn(error);
			});
			logger.warn("##"+"\n ##HttpStatus: "+String.valueOf(HttpStatus.BAD_REQUEST));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);		
		}
		
		
		try {
			contactoNew = contactoService.save(contacto);			
			response.put("contacto",contactoNew);
			response.put("mensaje", "Contacto creado correctamente.");
			logger.info("Contacto '"+ contacto.getNombre() +"' creado correctamente.");
			return new ResponseEntity<Object>(response, HttpStatus.CREATED);
		}catch(DataAccessException e) {
			logger.error("Error al intentar grabar el contacto: "+ e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}
	
	@DeleteMapping("/delete/{id}")
	//@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> delete(@PathVariable int id ) {	
		Map<String, Object> response = new HashMap<>();
		Contacto contactoDel = contactoService.findById(id);
		if(contactoDel != null) {
			contactoService.delete(contactoDel);
			response.put("contacto", contactoDel);
			response.put("mensaje", "El contacto"+ contactoDel.getNombre() +"fue eliminado correctamente.");
			logger.info("Contacto "+contactoDel.getNombre()+" eliminado correctamente. HttpStatus: "+ String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}else {
			logger.error("El contacto con ID: "+String.valueOf(id)+" no existe.");
			response.put("mensaje", "El contacto que desea eliminar no existe.");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/update")
	//@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> update(@Valid @RequestBody Contacto contacto, BindingResult result) {
		Map<String,Object> response = new HashMap<>();
		Contacto update = null;
		
		if(contactoService.findById(contacto.getId())!=null) {		
			
			if(result.hasErrors()) {
				List<String> errors = result.getFieldErrors()
						.stream()
						.map(err-> "El campo '"+err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());
				response.put("errors", errors);
				logger.warn("##Error en los datos del contacto que intenta actualizar.");
				errors.forEach(error->{
					logger.warn(error);
				});
				logger.warn("##"+"\n ##HttpStatus: "+String.valueOf(HttpStatus.BAD_REQUEST));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);		
			}
			
			try {
				update = contactoService.findById(contacto.getId());
				update.setNombre(contacto.getNombre());
				update.setNumero(contacto.getNumero());
				update.setNumero_2(contacto.getNumero_2());
				update.setSubgrupo(contacto.getSubgrupo());
				
				response.put("contacto", contactoService.save(update) );
				response.put("mensaje", "Contacto "+ update.getNombre()+" actualizado correctamente.");
				logger.info("Contacto "+update.getNombre()+" actualizado correctamente.");
				return new ResponseEntity<Object>(response,HttpStatus.OK);
			}catch(DataAccessException e) {
				logger.error("Error al intentar grabar la actualizacion del contacto: "+ e.getMostSpecificCause());
				response.put("mensaje", e.getMostSpecificCause());
				return new ResponseEntity<Object>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}else {
			logger.warn("El contacto con ID: "+ contacto.getId()+" que intenta actualizar no existe.");
			response.put("mensaje", "El contacto que quiere actualizar no existe");
			return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
		}
	
	}
}
