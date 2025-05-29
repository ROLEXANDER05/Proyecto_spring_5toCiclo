package com.proy.VIBA.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proy.VIBA.modelo.ClassDetalleVenta;
import com.proy.VIBA.modelo.ClassVenta;
import com.proy.VIBA.repositorio.VentaRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class VentaServicioImp implements VentaServicio{

	@Autowired
	private VentaRepositorio repositorio;
	
	@Override
	public void save(ClassVenta venta, Double monto, HttpSession session) {
		for(ClassDetalleVenta deta : venta.getDetalleVentaList()) {
			deta.setTbl_venta(venta);
		}
		venta.setMonto(monto);
		venta.setFechaVenta(new Date());
		repositorio.save(venta);
		session.setAttribute("venta", new ClassVenta());
	}

	 @Override
	    public List<ClassVenta> listarVentas() {
	        return repositorio.findAll();
	    }
	 
	 @Override
		public Page<ClassVenta> listarVentasPaginado(String busqueda, Pageable pageable) {
		 if(busqueda != null && !busqueda.isEmpty()) {
				return repositorio.findByCodigoVentaContaining(busqueda, pageable);
			} else {
				return repositorio.findAll(pageable);
			}
		}
	
	 @Override
	    public ClassVenta obtenerVentaConDetalles(Long codigoVenta) {
	        return repositorio.BuscarDetallesVenta(codigoVenta);
	    }
	 //ELIMINAR ESTO TMBN ES DE PRUEBA
	@Override
	public ClassVenta obtenerPorId(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	
	 
}
