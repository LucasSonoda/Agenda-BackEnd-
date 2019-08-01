package com.asoprofarma.internos.restcontroller;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import com.asoprofarma.internos.entity.Grupo;
import com.asoprofarma.internos.entity.Subgrupo;
import com.asoprofarma.internos.service.IGrupoService;


@RestController
@RequestMapping("/grupo")
//@Secured({"ROLE_USER"})
@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201","http://localhost:4200", "http://10.10.1.119:4200" })
public class GrupoRestController {

	@Autowired
	private IGrupoService grupoService;
	
	@GetMapping("/{id}")
	public Grupo findById(@PathVariable int id) {
		Grupo grupo = grupoService.findById(id);
		Collections.sort(grupo.getSubgrupos(), Subgrupo.sortByNombreAscend);
		grupo.getSubgrupos().forEach(subgrupo->{
			Collections.sort(subgrupo.getContactos(), Contacto.sortByNombreAscend);
		});
		return grupo;
	}
	
	@GetMapping("/all")
	public List<Grupo> todosAscend(){
		ArrayList<Grupo> grupos =(ArrayList<Grupo>) grupoService.ListGrupo();
		grupos.forEach(grupo->{
			Collections.sort(grupo.getSubgrupos(), Subgrupo.sortByNombreAscend);
		});
		return grupos;	
	}
	
	@GetMapping("/all/id_nombre")
	public List<Grupo> allwithIdName(){
		return grupoService.ListGrupoIdNombre();
	}
	
	@PostMapping("/save")
	@Secured({"ROLE_ADMIN"})
	public Grupo save(@RequestBody Grupo grupo){
		return grupoService.save(grupo);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/delete/{id}")
	public Grupo delete(@PathVariable int id) {
		return grupoService.delete(grupoService.findById(id));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/update")
	public Grupo update(@RequestBody Grupo grupo) {
		return grupoService.save(grupo);
	}
}
