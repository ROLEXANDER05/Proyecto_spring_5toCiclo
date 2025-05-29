package com.proy.VIBA.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_cargo")
public class Cargo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcargo;

  	@Column(nullable = false)
    private String nombrecargo;
  	
  	@OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassEmpleado> tbl_empleado;

  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
	public Long getIdcargo() {
		return idcargo;
	}

	public void setIdcargo(Long idcargo) {
		this.idcargo = idcargo;
	}

	public String getNombrecargo() {
		return nombrecargo;
	}

	public void setNombrecargo(String nombrecargo) {
		this.nombrecargo = nombrecargo;
	}

	public List<ClassEmpleado> getTbl_empleado() {
		return tbl_empleado;
	}

	public void setTbl_empleado(List<ClassEmpleado> tbl_empleado) {
		this.tbl_empleado = tbl_empleado;
	}
  	
  	
  	
  	
}
