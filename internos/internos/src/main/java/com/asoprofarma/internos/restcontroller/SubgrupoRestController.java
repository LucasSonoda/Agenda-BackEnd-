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

import com.asoprofarma.internos.entity.Subgrupo;
import com.asoprofarma.internos.service.ISubgrupoService;

@CrossOrigin(origins = { "http://localhost:4201", "http://10.10.1.119:4201", "http://localhost:4200",
		"http://10.10.1.119:4200" })
@RestController
@RequestMapping("/api/subgrupo")
public class SubgrupoRestController {

	@Autowired
	private ISubgrupoService subgrupoService;

	@GetMapping("/all")
	private List<Subgrupo> todos() {
		ArrayList<Subgrupo> subgrupos = (ArrayList<Subgrupo>)subgrupoService.ListSubgrupo();
		Collections.sort(subgrupos, Subgrupo.sortByNombreAscend);
		return subgrupos;
	}

	@PostMapping("/save")
	private Subgrupo save(@RequestBody Subgrupo subgrupo) {
		return subgrupoService.save(subgrupo);
	}

	@DeleteMapping("/delete")
	private Subgrupo delete(@RequestBody Subgrupo subgrupo) {
		return subgrupoService.delete(subgrupo);
	}

	@PutMapping("/update")
	private Subgrupo update(@RequestBody Subgrupo subgrupo) {
		return subgrupoService.save(subgrupo);
	}

	@GetMapping("/{id}")
	private Subgrupo findById(@PathVariable Integer id) {
		return subgrupoService.findById(id);
	}
}
