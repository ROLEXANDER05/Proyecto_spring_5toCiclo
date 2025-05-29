package com.proy.VIBA.servicio;

import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.modelo.ClassProducto;
import com.proy.VIBA.modelo.ClassVenta;

import jakarta.servlet.http.HttpSession;

public interface CarritoServicio {

	public void save(ClassProducto producto, Integer cantidad,
			HttpSession session);
	public void saveCliente(ClassEmpleado empleado,
			HttpSession session);
	public ClassVenta traerVenta(HttpSession session);
	public void delete(Integer id, HttpSession session);
	public void actualizarCantidad(Integer idProducto, Integer cantidad, HttpSession session);
	
	void guardarClienteEnVenta(ClassCliente cliente, HttpSession session);
	
}
