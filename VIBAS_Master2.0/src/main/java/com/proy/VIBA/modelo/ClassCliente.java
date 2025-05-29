package com.proy.VIBA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "tbl_Cliente")
@Data
public class ClassCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="DNI", nullable=false, length = 8)
	private String dni;
	
	@Column(name="nombre", nullable=false,length = 100)
	private String nombre;
	
	@Column(name="apellido",nullable=false,length = 100)
	private String apellido;

	@Column(name="email",nullable=false,length = 100, unique = true)
	private String email;
	
	@Column(name="telefono",nullable=false,length = 9, unique = true)
	private String telefono;


	@Override
	public String toString() {
		return "ClassCliente [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
				+ email + ", telefono=" + telefono + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
