package com.asoprofarma.internos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "contactos")
public class Contacto implements Serializable {
	
	private static final Logger logger = LogManager.getLogger(Contacto.class.getName());
	
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="no puede estar vacio")
	@Size(min = 2, max = 50, message="tiene que tener una longitud entre 2 a 50 caracteres")
	@Column(nullable=false, length=50)
	private String nombre;
	
	@Size(min= 2, message = "tiene que tener una longitud mayor a un digito")
	@Size(max=25, message="es demasiado largo")
	@Column(nullable = false,length=25)
	@NotEmpty(message="no puede estar vacio")
	private String numero;
	
	@Size(min= 2, message = "tiene que tener una longitud mayor a un digito")
	@Size(max=25, message="es demasiado largo")
	@Column(nullable=true, length=25)
	private String numero_2;
	
	@JsonIgnoreProperties({ "contactos", "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Subgrupo subgrupo;
	
	public Contacto() {
		logger.info("Clase iniciada");
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero_2() {
		return numero_2;
	}

	public void setNumero_2(String numero_2) {
		this.numero_2 = numero_2;
	}

	public Subgrupo getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(Subgrupo subgrupo) {
		this.subgrupo = subgrupo;
	}

	private static final long serialVersionUID = 1L;

}