package com.asoprofarma.internos.restcontroller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
@Secured("ROLE_USER")
@RequestMapping("/subgrupo")
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201", "http://localhost:4200","http://10.10.1.119:4200" })
public class SubgrupoRestController {

	@Autowired
	private ISubgrupoService subgrupoService;
	
	@GetMapping("/{id}")
	private ResponseEntity<?> findById(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();
		Subgrupo subgrupo = null;

		try {
			subgrupo = subgrupoService.findById(id);

			if (subgrupo == null) {
				response.put("mensaje", "El subgrupo con ID: " + id + " no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			response.put("subgrupo", subgrupo);
			response.put("mensaje", "Subgrupo " + subgrupo.getNombre() + " encontrado correctamente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/all")
	private ResponseEntity<?> todos() {
		Map<String, Object> response = new HashMap<>();
		List<Subgrupo> subgrupos = null;
		try {

			subgrupos = subgrupoService.ListSubgrupo();
			Collections.sort(subgrupos, Subgrupo.sortByNombreAscend);
			response.put("subgrupos", subgrupos);
			response.put("mensaje", "Subgrupos obtenidos correctamente.");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/save")
	private ResponseEntity<?> save(@Valid @RequestBody Subgrupo subgrupo, BindingResult result) {

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

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete")
	private ResponseEntity<?> delete(@RequestBody Subgrupo subgrupo) {

		Map<String, Object> response = new HashMap<>();

		if (subgrupoService.findById(subgrupo.getId()) != null) {
			response.put("subgrupo", subgrupoService.delete(subgrupo));
			response.put("mensaje", "El subgrupo " + subgrupo.getNombre() + " fue eliminado exitosamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} else {
			response.put("mensaje", "El subgrupo " + subgrupo.getNombre() + " que intenta eliminar no existe.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/update")
	private ResponseEntity<?> update(@Valid @RequestBody Subgrupo subgrupo, BindingResult result) {

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
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} else {

			response.put("mensaje", "El subgrupo " + subgrupo.getNombre() + " que intenta actualizar no existe.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
	}
	
}
