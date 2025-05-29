package com.proy.VIBA.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;
import com.proy.VIBA.Util.reportes.ProductoExporterPDF;
import com.proy.VIBA.modelo.ClassProducto;
import com.proy.VIBA.servicio.ProductoServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vistasProducto")
public class ProductoController {
	@Autowired
	private ProductoServicio servicio;
	
	@GetMapping("/listarProducto")
	public String listarProducto(HttpServletRequest request,
			Model modelo,
			@Param("busqueda") String busqueda,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		Page<ClassProducto> productoPage = servicio.listarProductoPaginado(busqueda, PageRequest.of(page, size));
		
		modelo.addAttribute("productos", productoPage.getContent());
		modelo.addAttribute("busqueda", busqueda);
		modelo.addAttribute("currentUrl", request.getRequestURI());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", productoPage.getTotalPages());
        modelo.addAttribute("totalItems", productoPage.getTotalElements());
		return "/vistasProducto/listarProducto";
	}
	
	@GetMapping("/RegistrarProducto")
	public String RegistrarProducto(Model modelo) {
		ClassProducto pro = new ClassProducto();
		modelo.addAttribute("producto",pro);
		return "/vistasProducto/FrmProducto";
	}
	
	@PostMapping("/GuardarProducto")
	public String CrearProducto(@ModelAttribute ClassProducto producto) {
		servicio.CrearProducto(producto);
		return "redirect:/vistasProducto/listarProducto";
	}
	
	@GetMapping("/editar/{id}")
	public String Editar(@PathVariable("id") Integer id, Model modelo) {
		ClassProducto pro = servicio.BuscarProductoId(id);
		modelo.addAttribute("producto", pro);
		return "/vistasProducto/FrmProducto";
	}
	
	@GetMapping("/eliminar/{id}")
	public String EliminarProducto(@PathVariable("id") Integer id,  Model modelo, @Param("busqueda") String busqueda) {
		servicio.EliminarProducto(id);
		List<ClassProducto> listado = servicio.listarProducto(busqueda);
		modelo.addAttribute("listado",listado);
		return "redirect:/vistasProducto/listarProducto";
	}
	
	//BORRAR SI ESTA MAL NO TE OLVIDES ALESSANDRO
		@GetMapping("/exportarPDF")
		public void exportarListadoDeProductoPDF(HttpServletResponse response) throws DocumentException, IOException {
		    response.setContentType("application/pdf");

		    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		    String fechaActual = dateFormatter.format(new Date());

		    String cabecera = "Content-Disposition";
		    String valor = "attachment; filename=productos_" + fechaActual + ".pdf";

		    response.setHeader(cabecera, valor);

		    List<ClassProducto> producto = servicio.findAll();  // <-- este es el punto clave

		    if (producto == null || producto.isEmpty()) {
		        System.out.println("⚠ Lista de productos vacía desde el servicio.");
		    }

		    ProductoExporterPDF exporter = new ProductoExporterPDF(producto);
		    exporter.Export(response);
		}

		
}
