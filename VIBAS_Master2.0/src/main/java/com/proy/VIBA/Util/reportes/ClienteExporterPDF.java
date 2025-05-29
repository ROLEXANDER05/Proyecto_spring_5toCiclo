package com.proy.VIBA.Util.reportes;

import java.awt.Color;

import java.io.IOException;
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

import jakarta.servlet.http.HttpServletResponse;

public class ClienteExporterPDF {
	private List<ClassCliente> listaCliente;

	public ClienteExporterPDF(List<ClassCliente> listaCliente) {
		super();
		this.listaCliente = listaCliente;
	}
	
	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("id", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("dni", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("nombre", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("apellido", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("email", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("telefono", fuente));
		tabla.addCell(celda);
	}	
	
	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for(ClassCliente cliente : listaCliente ) {
			tabla.addCell(String.valueOf(cliente.getId()));
			tabla.addCell(cliente.getDni());
			tabla.addCell(cliente.getNombre());
			tabla.addCell(cliente.getApellido());
			tabla.addCell(cliente.getEmail());
			tabla.addCell(cliente.getTelefono());
		
		}
	}
	public void Export(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.RED);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Clientes", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		
		//elimina en caso este mal
		 if (listaCliente == null || listaCliente.isEmpty()) {
		        Paragraph sinDatos = new Paragraph("No hay Clientes para mostrar.");
		        sinDatos.setSpacingBefore(20);
		        documento.add(sinDatos);
		        documento.close();
		        return;
		    }
		 //hasta aqui no olvidar
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float [] {1f, 2.3f, 2.3f, 2.3f, 2.9f, 2.5f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		
		documento.add(tabla);
		
		documento.close();
	}

}
