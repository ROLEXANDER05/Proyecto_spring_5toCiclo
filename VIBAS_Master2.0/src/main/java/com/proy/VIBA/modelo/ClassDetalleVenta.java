
package com.proy.VIBA.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_detalleVenta")
public class ClassDetalleVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoDetalleVenta;
	
	private Integer cantidadDetVenta;
	private Double precioDetVenta;
	private Double montoDetVenta;
	
	@ManyToOne
	private ClassProducto tbl_producto;
	
	@ManyToOne
	@JsonBackReference
	private ClassVenta tbl_venta;

	
	public ClassDetalleVenta() {
		super();
	}
	
	public ClassDetalleVenta(Integer cantidadDetVenta, Double precioDetVenta, Double montoDetVenta,
			ClassProducto tbl_producto) {
		super();
		this.cantidadDetVenta = cantidadDetVenta;
		this.precioDetVenta = precioDetVenta;
		this.montoDetVenta = montoDetVenta;
		this.tbl_producto = tbl_producto;
	}

	public ClassDetalleVenta(Long codigoDetalleVenta, Integer cantidadDetVenta, Double precioDetVenta,
			Double montoDetVenta, ClassProducto tbl_producto, ClassVenta tbl_venta) {
		super();
		this.codigoDetalleVenta = codigoDetalleVenta;
		this.cantidadDetVenta = cantidadDetVenta;
		this.precioDetVenta = precioDetVenta;
		this.montoDetVenta = montoDetVenta;
		this.tbl_producto = tbl_producto;
		this.tbl_venta = tbl_venta;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long getCodigoDetalleVenta() {
		return codigoDetalleVenta;
	}

	public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
		this.codigoDetalleVenta = codigoDetalleVenta;
	}

	public Integer getCantidadDetVenta() {
		return cantidadDetVenta;
	}

	public void setCantidadDetVenta(Integer cantidadDetVenta) {
		this.cantidadDetVenta = cantidadDetVenta;
	}

	public Double getPrecioDetVenta() {
		return precioDetVenta;
	}

	public void setPrecioDetVenta(Double precioDetVenta) {
		this.precioDetVenta = precioDetVenta;
	}

	public Double getMontoDetVenta() {
		return montoDetVenta;
	}

	public void setMontoDetVenta(Double montoDetVenta) {
		this.montoDetVenta = montoDetVenta;
	}

	public ClassProducto getTbl_producto() {
		return tbl_producto;
	}

	public void setTbl_producto(ClassProducto tbl_producto) {
		this.tbl_producto = tbl_producto;
	}

	public ClassVenta getTbl_venta() {
		return tbl_venta;
	}

	public void setTbl_venta(ClassVenta tbl_venta) {
		this.tbl_venta = tbl_venta;
	}

	

}
