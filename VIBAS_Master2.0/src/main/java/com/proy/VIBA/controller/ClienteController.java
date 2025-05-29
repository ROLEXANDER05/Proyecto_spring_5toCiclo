package com.proy.VIBA.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.proy.VIBA.Util.reportes.ClienteExporterPDF;
import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.servicio.ClienteServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vistasCliente")
public class ClienteController {

	@Autowired
	private ClienteServicio servicio;
	
	@GetMapping({"/listarClientes","/"})
	public String listarClientes(HttpServletRequest request,
								Model modelo,
								@Param("busqueda") String busqueda,
								@RequestParam(defaultValue = "0") int page,
								@RequestParam(defaultValue = "5") int size) {
		
		Page<ClassCliente> clientesPage = servicio.listarClientesPaginado(busqueda, PageRequest.of(page, size));
		
		modelo.addAttribute("clientes", clientesPage.getContent());
		modelo.addAttribute("busqueda", busqueda);
		modelo.addAttribute("currentUrl", request.getRequestURI());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", clientesPage.getTotalPages());
        modelo.addAttribute("totalItems", clientesPage.getTotalElements());

		return "/vistasCliente/listarClientes";
	}
	
	@GetMapping("/RegistrarCliente")
	public String RegistrarCliente(Model modelo) {
		ClassCliente cli = new ClassCliente();
		modelo.addAttribute("regiscliente",cli);
		return "/vistasCliente/FrmCliente";
	}
	
	@PostMapping("/GuardarCliente")
	public String GuardarCliente(@ModelAttribute ClassCliente cliente) {
		servicio.CrearCliente(cliente);
		return "redirect:/vistasCliente/listarClientes";
	}
	
	@GetMapping("/editar/{id}")
	public String Editar(@PathVariable("id") Integer idcliente, Model modelo) {
		ClassCliente cli = servicio.BuscarClienteId(idcliente);
		modelo.addAttribute("regiscliente", cli);
		return "/vistasCliente/FrmCliente";
	}
	
	@GetMapping("/eliminar/{id}")
	public String EliminarEstudiante(@PathVariable("id") Integer idcliente, Model modelo, @Param("busqueda") String busqueda) {
		servicio.EliminarCliente(idcliente);
		List<ClassCliente> listado = servicio.listarClientes(busqueda);
		modelo.addAttribute("listado", listado);
		return "redirect:/vistasCliente/listarClientes";
	}

	//BORRAR SI ESTA MAL NO TE OLVIDES ALESSANDRO
			@GetMapping("/exportarPDF")
			public void exportarListadoDeClientePDF(HttpServletResponse response) throws DocumentException, IOException {
			    response.setContentType("application/pdf");

			    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			    String fechaActual = dateFormatter.format(new Date());

			    String cabecera = "Content-Disposition";
			    String valor = "attachment; filename=clientes_" + fechaActual + ".pdf";

			    response.setHeader(cabecera, valor);

			    List<ClassCliente> cliente = servicio.findAll();  // <-- este es el punto clave

			    if (cliente == null || cliente.isEmpty()) {
			        System.out.println("⚠ Lista de Clientes vacía desde el servicio.");
			    }

			    ClienteExporterPDF exporter = new ClienteExporterPDF(cliente);
			    exporter.Export(response);
			}
}















