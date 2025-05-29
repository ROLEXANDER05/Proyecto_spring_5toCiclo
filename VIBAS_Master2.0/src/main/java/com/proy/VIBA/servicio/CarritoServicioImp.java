package com.proy.VIBA.servicio;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.modelo.ClassDetalleVenta;
import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.modelo.ClassProducto;
import com.proy.VIBA.modelo.ClassVenta;

import jakarta.servlet.http.HttpSession;

@Service
public class CarritoServicioImp implements CarritoServicio {

    @Override
    public void save(ClassProducto producto, Integer cantidadNueva, HttpSession session) {
        ClassVenta venta = traerVenta(session);
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        boolean encontrado = false;

        for (ClassDetalleVenta detalle : carrito) {
            if (detalle.getTbl_producto().getIdproducto().equals(producto.getIdproducto())) {
                encontrado = true;
                int cantidadActualEnCarrito = detalle.getCantidadDetVenta();
                int stockDisponible = producto.getStockproducto();

                if ((cantidadActualEnCarrito + cantidadNueva) > stockDisponible) {
                    throw new InsufficientStockException("La cantidad total en el carrito (" + (cantidadActualEnCarrito + cantidadNueva) + ") excede el stock disponible (" + stockDisponible + ") del producto: " + producto.getNomproducto());
                } else {
                    detalle.setCantidadDetVenta(cantidadActualEnCarrito + cantidadNueva);
                    detalle.setMontoDetVenta(detalle.getCantidadDetVenta() * detalle.getPrecioDetVenta());
                }
                break; // Importante salir del bucle una vez encontrado el producto
            }
        }

        if (!encontrado) {
            if (cantidadNueva > producto.getStockproducto()) {
                throw new InsufficientStockException("La cantidad solicitada (" + cantidadNueva + ") excede el stock disponible (" + producto.getStockproducto() + ") del producto: " + producto.getNomproducto());
            } else {
                ClassDetalleVenta nuevoDetalle = new ClassDetalleVenta(cantidadNueva, producto.getPreproducto(),
                        (cantidadNueva * producto.getPreproducto()), producto);
                nuevoDetalle.setTbl_venta(venta); // Asocia el detalle con la venta
                carrito.add(nuevoDetalle);
            }
        }
        venta.setDetalleVentaList(carrito);
        session.setAttribute("venta", venta);
    }

    @Override
    public void saveCliente(ClassEmpleado empleado, HttpSession session) {
        ClassVenta venta = (ClassVenta) session.getAttribute("venta");
        if (venta != null) {
            venta.setTbl_empleado(empleado);
            session.setAttribute("venta", venta);
        }
    }

    @Override
    public ClassVenta traerVenta(HttpSession session) {
        ClassVenta venta = (ClassVenta) session.getAttribute("venta");
        if (venta == null) {
            venta = new ClassVenta();
            venta.setDetalleVentaList(new ArrayList<>());
            session.setAttribute("venta", venta);
        }
        return venta;
    }

    @Override
    public void delete(Integer id, HttpSession session) {
        ClassVenta venta = (ClassVenta) session.getAttribute("venta");
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();
        carrito.removeIf(detalle -> detalle.getTbl_producto().getIdproducto().equals(id));
        session.setAttribute("venta", venta); // Actualizar la venta en sesión
    }

    public void actualizarCantidad(Integer idProducto, Integer cantidadNueva, HttpSession session) {
        ClassVenta venta = traerVenta(session);
        List<ClassDetalleVenta> carrito = venta.getDetalleVentaList();

        for (ClassDetalleVenta detalle : carrito) {
            if (detalle.getTbl_producto().getIdproducto().equals(idProducto)) {
                ClassProducto producto = detalle.getTbl_producto();
                int stockDisponible = producto.getStockproducto();

                if (cantidadNueva > stockDisponible) {
                    throw new InsufficientStockException("La nueva cantidad (" + cantidadNueva + ") excede el stock disponible (" + stockDisponible + ") del producto: " + producto.getNomproducto());
                }
                detalle.setCantidadDetVenta(cantidadNueva);
                detalle.setMontoDetVenta(detalle.getTbl_producto().getPreproducto() * cantidadNueva);
                session.setAttribute("venta", venta); // Actualizar la venta en sesión
                return;
            }
        }
    }

    @Override
    public void guardarClienteEnVenta(ClassCliente cliente, HttpSession session) {
        ClassVenta venta = (ClassVenta) session.getAttribute("venta");
        if (venta != null) {
            venta.setTbl_Cliente(cliente); // Asumiendo que ClassVenta tiene un atributo 'cliente' de tipo ClassCliente
            session.setAttribute("venta", venta);
        } else {
            // Manejar el caso en que la venta no esté inicializada (opcional, ya que se inicializa en Index)
            ClassVenta nuevaVenta = new ClassVenta();
            nuevaVenta.setTbl_Cliente(cliente);
            nuevaVenta.setDetalleVentaList(new ArrayList<>()); // Inicializar la lista de detalles
            session.setAttribute("venta", nuevaVenta);
        }
    }

    // Clase de excepción personalizada
    @SuppressWarnings("serial")
	public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String message) {
            super(message);
        }
    }
}