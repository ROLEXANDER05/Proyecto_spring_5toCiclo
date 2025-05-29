package com.proy.VIBA.Util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.modelo.ClassDetalleVenta;
import com.proy.VIBA.modelo.ClassVenta;

import jakarta.servlet.http.HttpServletResponse;

public class DetalleVentaExporterPDF {

    private ClassVenta venta;
    private List<ClassDetalleVenta> detallesVenta;

    public DetalleVentaExporterPDF(ClassVenta venta, List<ClassDetalleVenta> detallesVenta) {
        this.venta = venta;
        this.detallesVenta = detallesVenta;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // Título Principal "COMPROBANTE DE COMPRA VIBA STORE"
        Font fuenteTituloPrincipal = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTituloPrincipal.setColor(Color.BLUE); // Puedes elegir el color que prefieras
        fuenteTituloPrincipal.setSize(20);
        Paragraph tituloPrincipal = new Paragraph("COMPROBANTE DE COMPRA VIBA STORE", fuenteTituloPrincipal);
        tituloPrincipal.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(tituloPrincipal);

        Font fuenteSubTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteSubTitulo.setColor(Color.RED);
        fuenteSubTitulo.setSize(16);
        Paragraph subTitulo = new Paragraph("\nInformación de la Venta:", fuenteSubTitulo);
        documento.add(subTitulo);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Información de la Venta
        documento.add(new Paragraph("ID Venta: " + venta.getCodigoVenta()));
        documento.add(new Paragraph("Fecha: " + dateFormatter.format(venta.getFechaVenta())));
        documento.add(new Paragraph("Monto Total: " + venta.getMonto()));
        if (venta.getTbl_empleado() != null) {
            documento.add(new Paragraph("Empleado: " + venta.getTbl_empleado().getNombre() + " " + venta.getTbl_empleado().getApellido()));
        } else {
            documento.add(new Paragraph("Empleado: Sin Empleado"));
        }

        // Datos del Empleado
        documento.add(new Paragraph("\nDatos del Empleado:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        if (venta.getTbl_empleado() != null) {
            documento.add(new Paragraph("Nombre: " + venta.getTbl_empleado().getNombre() + " " + venta.getTbl_empleado().getApellido()));
            documento.add(new Paragraph("DNI: " + venta.getTbl_empleado().getDni()));
            documento.add(new Paragraph("Email: " + venta.getTbl_empleado().getEmail()));
            documento.add(new Paragraph("Teléfono: " + venta.getTbl_empleado().getTelefono()));
        } else {
            documento.add(new Paragraph("Empleado: Sin Empleado"));
        }
        
        // Datos del Cliente
        documento.add(new Paragraph("\nDatos del Cliente:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        ClassCliente cliente = venta.getTbl_Cliente();
        if (cliente != null) {
            documento.add(new Paragraph("Nombre: " + cliente.getNombre() + " " + cliente.getApellido()));
            documento.add(new Paragraph("DNI: " + cliente.getDni()));
            documento.add(new Paragraph("Email: " + cliente.getEmail()));
            documento.add(new Paragraph("Teléfono: " + cliente.getTelefono()));
        } else {
            documento.add(new Paragraph("Cliente: Sin Cliente"));
        }

        documento.add(new Paragraph("\nDetalle de Venta:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

        PdfPTable tablaDetalle = new PdfPTable(4); // Producto, Cantidad, Precio Unitario, Monto
        tablaDetalle.setWidthPercentage(100);
        tablaDetalle.setSpacingBefore(10);
        tablaDetalle.setWidths(new float[]{3f, 1.5f, 2f, 2f});

        // Cabecera de la tabla de detalles
        Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteCabecera.setColor(Color.BLACK);

        PdfPCell celdaCabecera = new PdfPCell();
        celdaCabecera.setBackgroundColor(Color.LIGHT_GRAY);
        celdaCabecera.setPadding(5);

        celdaCabecera.setPhrase(new Phrase("Producto", fuenteCabecera));
        tablaDetalle.addCell(celdaCabecera);

        celdaCabecera.setPhrase(new Phrase("Cantidad", fuenteCabecera));
        tablaDetalle.addCell(celdaCabecera);

        celdaCabecera.setPhrase(new Phrase("Precio Unitario", fuenteCabecera));
        tablaDetalle.addCell(celdaCabecera);

        celdaCabecera.setPhrase(new Phrase("Monto", fuenteCabecera));
        tablaDetalle.addCell(celdaCabecera);

        // Datos de los detalles de venta
        for (ClassDetalleVenta detalle : detallesVenta) {
            tablaDetalle.addCell(detalle.getTbl_producto().getNomproducto());
            tablaDetalle.addCell(String.valueOf(detalle.getCantidadDetVenta()));
            tablaDetalle.addCell(String.valueOf(detalle.getPrecioDetVenta()));
            tablaDetalle.addCell(String.valueOf(detalle.getMontoDetVenta()));
        }

        // Total de la venta
        PdfPCell celdaVacia = new PdfPCell(new Phrase(""));
        celdaVacia.setBorder(0);
        tablaDetalle.addCell(celdaVacia);
        tablaDetalle.addCell(celdaVacia);
        tablaDetalle.addCell(new PdfPCell(new Phrase("Total:", fuenteCabecera)));
        tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(venta.getMonto()), fuenteCabecera)));

        documento.add(tablaDetalle);

        documento.close();
    }
}