package com.asoprofarma.internos.restcontroller;

import java.util.Collections;
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

import com.asoprofarma.internos.entity.Subgrupo;
import com.asoprofarma.internos.service.ISubgrupoService;


@RestController
@RequestMapping("/subgrupo")
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201", "http://localhost:4200","http://10.10.1.119:4200" })
public class SubgrupoRestController {
	
	private static final Logger logger = LogManager.getLogger(SubgrupoRestController.class);
	
	@Autowired
	private ISubgrupoService subgrupoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		logger.info("Buscando al subgrupo con ID: "+String.valueOf(id));
		Map<String, Object> response = new HashMap<>();
		Subgrupo subgrupo = null;

		try {
			subgrupo = subgrupoService.findById(id);

			if (subgrupo == null) {
				logger.warn("##El subgrupo con ID: "+String.valueOf(id)+" no existe."+ " HttpStatus: "+String.valueOf(HttpStatus.NOT_FOUND));
				response.put("mensaje", "El subgrupo con ID: " + id + " no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			response.put("subgrupo", subgrupo);
			response.put("mensaje", "Subgrupo " + subgrupo.getNombre() + " encontrado correctamente");
			logger.info("Se encontro al subgrupo: "+subgrupo.getNombre()+". HttpStatus: "+ String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/all")
	public ResponseEntity<?> todos() {
		logger.info("Solicitando todos los Subgrupos...");
		Map<String, Object> response = new HashMap<>();
		List<Subgrupo> subgrupos = null;
		try {

			subgrupos = subgrupoService.ListSubgrupo();
			Collections.sort(subgrupos, Subgrupo.sortByNombreAscend);
			response.put("subgrupos", subgrupos);
			response.put("mensaje", "Subgrupos obtenidos correctamente.");
			logger.info("Subgrupos retornados correctamente. HttpStatus: "+String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			logger.warn("##Error al intentar acceder a los subgrupos mensaje: "+e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody Subgrupo subgrupo, BindingResult result) {

		Map<String, Object> response = new HashMap<>();
		Subgrupo subgrupoNew = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			subgrupoNew = subgrupoService.save(subgrupo);
			response.put("subgrupo", subgrupoNew);
			response.put("mensaje", "Subgrupo creado correctamente.");
			logger.info("Contacto '"+ subgrupo.getNombre() +"' creado correctamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			logger.error("Error al intentar grabar el subgrupo: "+ e.getMostSpecificCause());
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {

		Map<String, Object> response = new HashMap<>();
		Subgrupo subgrupoDel = subgrupoService.findById(id);
		if (subgrupoDel != null) {
			try {
				subgrupoService.delete(subgrupoDel);
			}catch(DataAccessException e) {
				response.put("mensaje",e.getMostSpecificCause());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
			response.put("subgrupo",subgrupoDel );
			response.put("mensaje", "El subgrupo " + subgrupoDel.getNombre() + " fue eliminado exitosamente.");
			logger.info("Subgrupo "+subgrupoDel.getNombre()+" eliminado correctamente. HttpStatus: "+ String.valueOf(HttpStatus.OK));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} else {
			logger.error("El subgrupo con ID: "+String.valueOf(id)+" no existe.");
			response.put("mensaje", "El subgrupo que intenta eliminar no existe.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Subgrupo subgrupo, BindingResult result) {

		Map<String, Object> response = new HashMap<>();
		Subgrupo update = subgrupoService.findById(subgrupo.getId());

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (update != null) {
			update.setNombre(subgrupo.getNombre());
			update.setGrupo(subgrupo.getGrupo());

			response.put("subgrupo", update);
			response.put("mensaje", "Subgrupo " + update.getNombre() + " actualizado correctamente.");
			logger.info("Subgrupo "+update.getNombre()+" actualizado correctamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} else {
			logger.error("El subgrupo " + subgrupo.getNombre() + " que intenta actualizar no existe.");
			response.put("mensaje", "El subgrupo " + subgrupo.getNombre() + " que intenta actualizar no existe.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/saveall")
	public ResponseEntity<Map<String,Object>> saveAll(@RequestBody List<Subgrupo> subgrupos) {
		
		Map<String,Object> response = new HashMap<>();
		
		if(subgrupos !=null && !subgrupos.isEmpty()) {
			subgrupoService.saveAll(subgrupos);
			response.put("mensaje", "Subgrupos actualizados correctamente.");
			logger.info("mensaje", "Subgrupos actualizados correctamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
		logger.error("Los subgrupos no se pudieron actualizar correctamente.");
		response.put("mensaje","Los subgrupos no se pudieron actualizar correctamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}
}
