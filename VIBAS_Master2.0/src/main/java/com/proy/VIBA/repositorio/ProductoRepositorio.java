package com.proy.VIBA.repositorio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proy.VIBA.modelo.ClassProducto;

@Repository
public interface ProductoRepositorio extends JpaRepository<ClassProducto, Integer>{
	@Query("SELECT p FROM ClassProducto p WHERE p.nomproducto LIKE %?1%"
		     + " OR p.desproducto LIKE %?1%"
		     + " OR p.catproducto LIKE %?1%")
		public List<ClassProducto> buscarProducto(String busqueda);

	Page<ClassProducto> findByNomproductoContaining(String nomproducto, Pageable pageable);

}
