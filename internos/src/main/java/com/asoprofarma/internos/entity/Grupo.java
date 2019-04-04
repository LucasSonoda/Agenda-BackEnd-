package com.asoprofarma.internos.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre", length = 50)
	@NotNull
	private String nombre;
	@JsonIgnoreProperties({ "grupo","hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo")
	private List<Subgrupo> subgrupos;

	public Grupo() {
		subgrupos = new ArrayList<Subgrupo>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Subgrupo> getSubgrupos() {
		return subgrupos;
	}

	public void setSubgrupos(List<Subgrupo> subgrupos) {
		this.subgrupos = subgrupos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addSubgrupo(Subgrupo subgrupo) {
		subgrupos.add(subgrupo);
	}

	private static final long serialVersionUID = 1L;

}
