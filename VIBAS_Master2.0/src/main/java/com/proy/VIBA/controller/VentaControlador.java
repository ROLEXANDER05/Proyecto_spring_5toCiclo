package com.proy.VIBA.controller;

import java.io.IOException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;
import com.proy.VIBA.Util.reportes.DetalleVentaExporterPDF;
import com.proy.VIBA.Util.reportes.VentaExporterPDF;
import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.modelo.ClassDetalleVenta;
import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.modelo.ClassProducto;
import com.proy.VIBA.modelo.ClassVenta;
import com.proy.VIBA.servicio.CarritoServicio;
import com.proy.VIBA.servicio.CarritoServicioImp;
import com.proy.VIBA.servicio.ClienteServicio;
import com.proy.VIBA.servicio.EmpleadoServicio;
import com.proy.VIBA.servicio.ProductoServicio;
import com.proy.VIBA.servicio.VentaServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/venta")
public class VentaControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private VentaServicio ventaServicio;

    @Autowired
    private CarritoServicio carritoServicio;

    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private EmpleadoServicio empleadoServicio;
    
    //Para listas las ventas
    @GetMapping("/listarVentas")
    public String listarVentas(HttpServletRequest request,
                               Model model,
                               @Param("busqueda") String busqueda,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {

        Page<ClassVenta> ventasPage = ventaServicio.listarVentasPaginado(busqueda, PageRequest.of(page, size));

        model.addAttribute("ventasRealizadas", ventasPage.getContent());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("currentUrl", request.getRequestURI());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ventasPage.getTotalPages());
        model.addAttribute("totalItems", ventasPage.getTotalElements());

        return "venta/listarVentas";
    }
    
    //Para Ver el detalle de la Venta
    @GetMapping("/detalles/{codigoVenta}")
    public String verDetallesVenta(@PathVariable("codigoVenta") Long codigoVenta, Model model) {
        ClassVenta venta = ventaServicio.obtenerVentaConDetalles(codigoVenta);
        if (venta != null && venta.getDetalleVentaList() != null && !venta.getDetalleVentaList().isEmpty()) {
            model.addAttribute("venta", venta);
            model.addAttribute("detalle", venta.getDetalleVentaList());
            return "venta/detallesVenta";
        } else {
            // Manejar el caso en que la venta no se encuentra o no tiene detalles
            return "error"; // O redirigir a una página informativa
        }
    }

    //Para dirigirme a la vista de las ventas
    @GetMapping("")
    public String Index(Model model, HttpSession session) {
        // Limpiar la información del cliente buscado y la bandera de búsqueda
        session.removeAttribute("clienteBuscado");
        session.removeAttribute("busquedaRealizada");

        // Forzar un carrito vacío cada vez que se accede al index
        session.setAttribute("venta", new ClassVenta());
        ClassVenta venta = (ClassVenta) session.getAttribute("venta");
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        List<ClassProducto> productos = productoServicio.listarProducto("");

        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", null); // Ya que siempre se limpia
        model.addAttribute("busquedaRealizada", false); // Ya que siempre se limpia
        model.addAttribute("venta", venta);
        model.addAttribute("montoTotal", carrito.stream()
                .mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());

        return "venta/index";
    }
    
    //Para agregar un producto al Carrito
    @GetMapping("/agregar")
    public String agregarCarrito(@RequestParam Integer cantidad,
                                @RequestParam Integer idProducto,
                                Model model,
                                HttpSession session) {
        ClassProducto producto = productoServicio.BuscarProductoId(idProducto);
        String errorStock = null;
        String errorProducto = null;

        if (producto != null) {
            try {
                carritoServicio.save(producto, cantidad, session);
            } catch (CarritoServicioImp.InsufficientStockException e) {
                errorStock = e.getMessage();
            }
        } else {
            errorProducto = "No se encontró el producto con ID: " + idProducto;
        }

        ClassVenta venta = carritoServicio.traerVenta(session);
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        List<ClassProducto> productos = productoServicio.listarProducto("");

        model.addAttribute("venta", venta);
        model.addAttribute("montoTotal", carrito.stream().mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());
        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", session.getAttribute("clienteBuscado"));
        model.addAttribute("busquedaRealizada", session.getAttribute("busquedaRealizada"));
        model.addAttribute("errorStock", errorStock); // Agregamos el mensaje de error al modelo
        model.addAttribute("errorProducto", errorProducto); // Aseguramos que el otro error también se pase

        return "venta/index"; // Redirigimos de vuelta a la página de venta
    }

    //Para eliminar un Producto del Carrito
    @GetMapping("/eliminar")
    public String eliminarProductoCarrito(@RequestParam Integer idProducto,
                                         Model model, HttpSession session) {
        carritoServicio.delete(idProducto, session);
        ClassVenta venta = carritoServicio.traerVenta(session);
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        List<ClassProducto> productos = productoServicio.listarProducto("");

        model.addAttribute("venta", venta);
        model.addAttribute("montoTotal", carrito.stream()
                .mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());
        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", session.getAttribute("clienteBuscado"));
        model.addAttribute("busquedaRealizada", session.getAttribute("busquedaRealizada"));

        return "venta/index";
    }

    //Para buscar un Cliente
    @GetMapping("/buscarCliente")
    public String buscarCliente(String dniCliente, Model model, HttpSession session) {
        // Limpiar el carrito al realizar una nueva búsqueda de cliente
        session.removeAttribute("venta");
        session.setAttribute("venta", new ClassVenta());

        ClassCliente cliente = clienteServicio.findByDni(dniCliente);
        carritoServicio.guardarClienteEnVenta(cliente, session);
        List<ClassProducto> productos = productoServicio.listarProducto("");

        model.addAttribute("clienteBuscado", cliente);
        model.addAttribute("busquedaRealizada", true);
        model.addAttribute("dniBuscado", dniCliente);
        model.addAttribute("productos", productos);
        model.addAttribute("venta", session.getAttribute("venta"));
        model.addAttribute("montoTotal", 0.0);

        session.setAttribute("clienteBuscado", cliente);
        session.setAttribute("busquedaRealizada", true);

        return "venta/index";
    }

    //Para Generar una nueva venta
    @GetMapping("/generarventa")
    public String GenerarVenta(Model model, HttpSession session, Principal principal) {
        ClassCliente cliente = (ClassCliente) session.getAttribute("clienteBuscado");

        if (cliente == null) {
            model.addAttribute("mensaje", "Debe seleccionar un cliente antes de generar la venta.");
            ClassVenta venta = carritoServicio.traerVenta(session);
            List<ClassProducto> productos = productoServicio.listarProducto("");
            model.addAttribute("productos", productos);
            model.addAttribute("clienteBuscado", session.getAttribute("clienteBuscado"));
            model.addAttribute("busquedaRealizada", session.getAttribute("busquedaRealizada"));
            model.addAttribute("venta", venta == null ? new ClassVenta() : venta);
            model.addAttribute("montoTotal", venta == null || venta.getDetalleVentaList().isEmpty() ? 0.0 : venta.getDetalleVentaList().stream().mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());
            return "venta/index";
        }

        ClassVenta venta = carritoServicio.traerVenta(session);
        if (venta == null || venta.getDetalleVentaList().isEmpty()) {
            model.addAttribute("mensaje", "El carrito está vacío. No se puede generar la venta.");
            List<ClassProducto> productos = productoServicio.listarProducto("");
            model.addAttribute("productos", productos);
            model.addAttribute("clienteBuscado", session.getAttribute("clienteBuscado"));
            model.addAttribute("busquedaRealizada", session.getAttribute("busquedaRealizada"));
            model.addAttribute("venta", venta == null ? new ClassVenta() : venta);
            model.addAttribute("montoTotal", venta == null || venta.getDetalleVentaList().isEmpty() ? 0.0 : venta.getDetalleVentaList().stream().mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());
            return "venta/index";
        }

        String username = principal.getName();
        ClassEmpleado empleadoLogueado = empleadoServicio.findByUsername(username);
        
        venta.setTbl_empleado(empleadoLogueado);
        
        List<ClassDetalleVenta> detallesVenta = venta.getDetalleVentaList();
        double montoTotalVenta = detallesVenta.stream()
                .mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum();

        ventaServicio.save(venta, montoTotalVenta, session);

        // Disminuir el stock de los productos vendidos
        for (ClassDetalleVenta detalle : detallesVenta) {
            ClassProducto producto = detalle.getTbl_producto();
            int cantidadVendida = detalle.getCantidadDetVenta();
            int nuevoStock = producto.getStockproducto() - cantidadVendida;

            // Actualizar el stock en la base de datos
            producto.setStockproducto(nuevoStock);
            productoServicio.save(producto);
        }

        // Limpiar el carrito después de generar la venta
        session.removeAttribute("venta");
        model.addAttribute("venta", new ClassVenta());
        model.addAttribute("montoTotal", 0.0);
        model.addAttribute("mensajeExito", "Venta generada con éxito."); // Mensaje de éxito

        List<ClassProducto> productos = productoServicio.listarProducto("");
        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", null);
        model.addAttribute("busquedaRealizada", false);

        return "venta/index";
    }
    
    //Para Actualizar la cantidad de los productos en el carrito
    @PostMapping("/actualizarCantidad")
    public String actualizarCantidadCarrito(@RequestParam Integer idProducto,
                                               @RequestParam Integer cantidad,
                                               Model model, HttpSession session) {
        String errorStock = null;
        try {
            carritoServicio.actualizarCantidad(idProducto, cantidad, session);
        } catch (CarritoServicioImp.InsufficientStockException e) {
            errorStock = e.getMessage();
        }

        ClassVenta venta = carritoServicio.traerVenta(session);
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        List<ClassProducto> productos = productoServicio.listarProducto("");

        model.addAttribute("venta", venta);
        model.addAttribute("montoTotal", carrito.stream()
                .mapToDouble(ClassDetalleVenta::getMontoDetVenta).sum());
        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", session.getAttribute("clienteBuscado"));
        model.addAttribute("busquedaRealizada", session.getAttribute("busquedaRealizada"));
        model.addAttribute("errorStock", errorStock); // Pasar el error al modelo

        return "venta/index"; // Redirigimos de vuelta a la página de venta
    }

    //Para limpiar el carrito
    @GetMapping("/limpiarCarrito")
    public String limpiarCarrito(HttpSession session, Model model) {
        session.removeAttribute("venta");
        model.addAttribute("venta", new ClassVenta());
        model.addAttribute("montoTotal", 0.0);
        List<ClassProducto> productos = productoServicio.listarProducto("");
        model.addAttribute("productos", productos);
        model.addAttribute("clienteBuscado", null);
        model.addAttribute("busquedaRealizada", false);

        return "venta/index";
    }
    
  //Para exportar
    @GetMapping("/pdf/generar-listado")
    public void generarListadoVentasPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=listado_ventas_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<ClassVenta> listaVentas = ventaServicio.listarVentas();

        VentaExporterPDF pdfExporter = new VentaExporterPDF(listaVentas);
        pdfExporter.export(response);
    }
    
    //Exportar el Detalle
    @GetMapping("/pdf/{codigoVenta}")
    public void generarComprobanteVentaPDF(@PathVariable("codigoVenta") Long codigoVenta, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comprobante_venta_" + codigoVenta + "_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ClassVenta venta = ventaServicio.obtenerVentaConDetalles(codigoVenta); // Asegúrate de que este método cargue los detalles
        if (venta != null && venta.getDetalleVentaList() != null && !venta.getDetalleVentaList().isEmpty()) {
            DetalleVentaExporterPDF pdfExporter = new DetalleVentaExporterPDF(venta, venta.getDetalleVentaList());
            pdfExporter.export(response);
        } else {
            // Manejar el caso en que la venta no se encuentra o no tiene detalles
            response.getWriter().write("Error: No se pudo generar el comprobante de venta."); // O redirigir a una página de error
        }
    }
    

    
    
    
}