
package com.proy.VIBA.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
//import jakarta.persistence.Table;

@Data
@Entity
@Table(name="tbl_venta")
public class ClassVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoVenta;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date fechaVenta;
	
	private Double monto;
	
	@ManyToOne
	private ClassEmpleado tbl_empleado;
	
	@OneToMany(mappedBy = "tbl_venta", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<ClassDetalleVenta> detalleVentaList = 
			new ArrayList<>();

	@ManyToOne // Añade esta relación con ClassCliente
	private ClassCliente tbl_Cliente;

	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(Long codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public ClassEmpleado getTbl_empleado() {
		return tbl_empleado;
	}

	public void setTbl_empleado(ClassEmpleado tbl_empleado) {
		this.tbl_empleado = tbl_empleado;
	}

	public List<ClassDetalleVenta> getDetalleVentaList() {
		return detalleVentaList;
	}

	public void setDetalleVentaList(List<ClassDetalleVenta> detalleVentaList) {
		this.detalleVentaList = detalleVentaList;
	}

	public ClassCliente getTbl_Cliente() {
		return tbl_Cliente;
	}

	public void setTbl_Cliente(ClassCliente tbl_Cliente) {
		this.tbl_Cliente = tbl_Cliente;
	}
	
	
	
	
	
	
	
	
	
}
