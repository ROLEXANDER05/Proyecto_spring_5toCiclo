package com.proy.VIBA.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proy.VIBA.modelo.ClassVenta;

public interface VentaRepositorio 
extends JpaRepository<ClassVenta, Long>{

	@Query("SELECT v FROM ClassVenta v LEFT JOIN FETCH v.detalleVentaList WHERE v.codigoVenta = :codigoVenta")
    ClassVenta BuscarDetallesVenta(@Param("codigoVenta") Long codigoVenta);
	
	@Query("SELECT v FROM ClassVenta v WHERE CAST(v.codigoVenta AS string) LIKE %:codigoVenta%")
    Page<ClassVenta> findByCodigoVentaContaining(@Param("codigoVenta") String codigoVenta, Pageable pageable);
	
}
