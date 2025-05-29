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
import com.proy.VIBA.modelo.ClassProducto;

import jakarta.servlet.http.HttpServletResponse;

public class ProductoExporterPDF {
	
	private List<ClassProducto> listaProducto;

	public ProductoExporterPDF(List<ClassProducto> listaProducto) {
		super();
		this.listaProducto = listaProducto;
	}
	
	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("idproducto", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("nomproducto", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("desproducto", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("preproducto", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("stockproducto", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("catproducto", fuente));
		tabla.addCell(celda);
	}	
	
	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for(ClassProducto producto : listaProducto ) {
			tabla.addCell(String.valueOf(producto.getIdproducto()));
			tabla.addCell(producto.getNomproducto());
			tabla.addCell(producto.getDesproducto());
			tabla.addCell(String.valueOf(producto.getPreproducto()));
			tabla.addCell(String.valueOf(producto.getStockproducto()));
			tabla.addCell(producto.getCatproducto());
		
		}
	}
	public void Export(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.RED);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Producto", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		
		//elimina en caso este mal
		 if (listaProducto == null || listaProducto.isEmpty()) {
		        Paragraph sinDatos = new Paragraph("No hay productos para mostrar.");
		        sinDatos.setSpacingBefore(20);
		        documento.add(sinDatos);
		        documento.close();
		        return;
		    }
		 //hasta aqui no olvidar
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float [] {1f, 2.3f, 2.3f, 2.9f, 2.5f, 2f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		
		documento.add(tabla);
		
		documento.close();
	}

}
