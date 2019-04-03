package com.asoprofarma.internos.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asoprofarma.internos.entity.Grupo;
import com.asoprofarma.internos.entity.Subgrupo;
import com.asoprofarma.internos.service.IGrupoService;
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201","http://localhost:4200", "http://10.10.1.119:4200" })
@RestController
@RequestMapping("/api/grupo")
public class GrupoRestController {

	@Autowired
	private IGrupoService grupoService;
	
	@GetMapping("/all")
	private List<Grupo> todosAscend(){
		ArrayList<Grupo> grupos =(ArrayList<Grupo>) grupoService.ListGrupo();
		grupos.forEach(grupo->{
			Collections.sort(grupo.getSubgrupos(), Subgrupo.sortByNombreAscend);
		});
		return grupos;	
	}
	
	@PostMapping("/save")
	private Grupo save(@RequestBody Grupo grupo){
		return grupoService.save(grupo);
	}
	
	@DeleteMapping("/delete/{id}")
	private Grupo delete(@PathVariable int id) {
		return grupoService.delete(grupoService.findById(id));
	}
	
	@PutMapping("/update")
	private Grupo update(@RequestBody Grupo grupo) {
		return grupoService.save(grupo);
	}
}
