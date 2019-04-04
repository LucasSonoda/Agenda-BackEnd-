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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "contactos")
public class Contacto implements Serializable {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min = 2, max = 50, message="La longitud del nombre tiene que ser de 2 a 50 caracteres")
	@NotNull(message="El campo nombre no puede estar vacio")
	private String nombre;
	@Min(value = 2, message = "La longitud del campo debe ser mayor a un digito")
	@Column(nullable = false)
	@NotNull(message="El campo numero no puede estar vacio")
	private Long numero;
	@Min(value = 2, message = "La longitud del campo debe ser mayor a un digito")
	@Column(nullable=true)
	private Long numero_2;
	@JsonIgnoreProperties({ "contactos", "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Subgrupo subgrupo;

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

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getNumero_2() {
		return numero_2;
	}

	public void setNumero_2(Long numero_2) {
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
