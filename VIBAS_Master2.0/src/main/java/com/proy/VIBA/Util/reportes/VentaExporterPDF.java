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
import com.proy.VIBA.modelo.ClassVenta;

import jakarta.servlet.http.HttpServletResponse;

public class VentaExporterPDF {
    private List<ClassVenta> listaVentas;

    public VentaExporterPDF(List<ClassVenta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID Venta", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Cliente", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Empleado", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Monto Total", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (ClassVenta venta : listaVentas) {
            tabla.addCell(String.valueOf(venta.getCodigoVenta()));
            tabla.addCell(dateFormatter.format(venta.getFechaVenta()));
            tabla.addCell(venta.getTbl_Cliente() == null ? "Sin Cliente" : venta.getTbl_Cliente().getNombre() + " " + venta.getTbl_Cliente().getApellido());
            tabla.addCell(venta.getTbl_empleado() == null ? "Sin Empleado" : venta.getTbl_empleado().getNombre() + " " + venta.getTbl_empleado().getApellido());
            tabla.addCell(String.valueOf(venta.getMonto()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTitulo.setColor(Color.BLUE);
        fuenteTitulo.setSize(18);

        Paragraph titulo = new Paragraph("Listado de Ventas", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(5); // 5 columnas
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f, 2f, 3f, 3f, 2f}); // Ajusta anchos según necesidad

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);

        documento.close();
    }
}