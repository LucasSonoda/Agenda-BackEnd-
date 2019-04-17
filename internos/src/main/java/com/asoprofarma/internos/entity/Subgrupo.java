package com.asoprofarma.internos.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subgrupos")
public class Subgrupo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message="no puede estar vacio")
	@Size(min = 2, max = 50, message="tiene que tener una longitud entre 2 a 50 caracteres.")
	@Column(nullable=false, length=50)
	private String nombre;
	
	@NotNull(message="no fue seleccionado.")
	@JsonIgnoreProperties({ "subgrupos", "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Grupo grupo;
	
	@JsonIgnoreProperties({ "subgrupo", "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subgrupo",cascade = CascadeType.ALL)
	private List<Contacto> contactos;

	public Subgrupo() {
		contactos = new ArrayList<Contacto>();
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}

	public static Comparator<Subgrupo> sortByNombreAscend = new Comparator<Subgrupo>() {
		public int compare(Subgrupo o1, Subgrupo o2) {
			String subgrupoName1  = o1.getNombre().toUpperCase();
			String subgrupoName2  = o2.getNombre().toUpperCase();
			return subgrupoName1.compareTo(subgrupoName2);
		}
		
	};
	public static Comparator<Subgrupo> sortByNombreDescend = new Comparator<Subgrupo>() {
		public int compare(Subgrupo o1, Subgrupo o2) {
			String subgrupoName1  = o1.getNombre();
			String subgrupoName2  = o2.getNombre();
			return subgrupoName2.compareTo(subgrupoName1);
		}
		
	};

}
