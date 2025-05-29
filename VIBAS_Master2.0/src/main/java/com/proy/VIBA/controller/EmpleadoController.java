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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;
import com.proy.VIBA.Util.reportes.EmpleadoExporterPDF;
import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.repositorio.CargoRepository;
import com.proy.VIBA.modelo.Cargo;
import com.proy.VIBA.servicio.EmpleadoServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vistasEmpleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoServicio servicio;
	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }
	
	@GetMapping({"/listarEmpleados"})
	public String listarEmpleados(
			HttpServletRequest request,
			Model modelo,
			@Param("busqueda") String busqueda,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		Page<ClassEmpleado> empleadoPage = servicio.listarEmpleadoPaginado(busqueda, PageRequest.of(page, size));
		
		modelo.addAttribute("empleados", empleadoPage.getContent());
		modelo.addAttribute("busqueda", busqueda);
		modelo.addAttribute("currentUrl", request.getRequestURI());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", empleadoPage.getTotalPages());
        modelo.addAttribute("totalItems", empleadoPage.getTotalElements());
		
		return "/vistasEmpleado/listarEmpleados";
	}
	
	@GetMapping("/RegistrarEmpleado")
	public String RegistrarEmpleado(Model modelo) {
		ClassEmpleado emple = new ClassEmpleado();
		modelo.addAttribute("empleado",emple);
		return "/vistasEmpleado/FrmCrearEmpleado";
	}
	
	@PostMapping("/GuardarEmpleado")
	public String GuardarEmpleado(@ModelAttribute ClassEmpleado empleado) {
		// Buscar el rol por defecto "USUARIO" (idcargo = 2)
        Cargo defaultCargo = cargoRepository.findById(2)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        empleado.setCargo(defaultCargo);
		servicio.CrearEmpleado(empleado);
		return "redirect:/vistasEmpleado/listarEmpleados";
	}
	
	@GetMapping("/editar/{id}")
	public String Editar(@PathVariable("id") Integer idEmp, Model modelo) {
		ClassEmpleado emp = servicio.BuscarEmpleadoId(idEmp);
		modelo.addAttribute("empleado", emp);
		return "/vistasEmpleado/FrmCrearEmpleado";
	}
	
	@GetMapping("/eliminar/{id}")
	public String EliminarEmpleado(@PathVariable("id") Integer idEmp,  Model modelo, @Param("busqueda") String busqueda) {
		servicio.EliminarEmpleado(idEmp);
		List<ClassEmpleado> listado = servicio.listarEmpleado(busqueda);
		modelo.addAttribute("listado", listado);
		return "redirect:/vistasEmpleado/listarEmpleados";
	}
	//BORRAR SI ESTA MAL NO TE OLVIDES ALESSANDRO
			@GetMapping("/exportarPDF")
			public void exportarListadoDeEmpleadoPDF(HttpServletResponse response) throws DocumentException, IOException {
			    response.setContentType("application/pdf");

			    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			    String fechaActual = dateFormatter.format(new Date());

			    String cabecera = "Content-Disposition";
			    String valor = "attachment; filename=empleados_" + fechaActual + ".pdf";

			    response.setHeader(cabecera, valor);

			    List<ClassEmpleado> empleado = servicio.findAll();  // <-- este es el punto clave

			    if (empleado == null || empleado.isEmpty()) {
			        System.out.println("⚠ Lista de Empleados vacía desde el servicio.");
			    }

			    EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleado);
			    exporter.Export(response);
			}

}












