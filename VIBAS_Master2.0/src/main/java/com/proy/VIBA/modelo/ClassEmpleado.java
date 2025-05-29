package com.proy.VIBA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "tbl_empleado")
@Data
public class ClassEmpleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "idcargo", nullable = false)
    private Cargo cargo;

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	
    */
    
}//FIN DE LA CLASE
