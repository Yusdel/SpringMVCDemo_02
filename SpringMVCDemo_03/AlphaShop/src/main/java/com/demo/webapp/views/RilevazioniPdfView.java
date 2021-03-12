package com.demo.webapp.views;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.demo.webapp.domain.Rilevazioni;

/**
 * 
 * @author Yusdel Morales
 * 
 * TODO Reading and Processing a file 
 */
public class RilevazioniPdfView extends AbstractView
{
	protected Rectangle pageSize = PageSize.A4;
	protected boolean isLandScape = true;
	
	protected float mrgLeft = 15;
	protected float mrgRight = 15;
	protected float mrgTop = 15;
	protected float mrgBottom = 15;
	
	private String fileName = "Rilevazioni.pdf";
	
	public RilevazioniPdfView()
	{
		setContentType("application/pdf");
	}
 	
	@Override
	protected boolean generatesDownloadContent()
	{
		return true;
	}
	
	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		pageSize = (isLandScape) ? pageSize.rotate() : pageSize;
		
		// Set document
		Document document = new Document(pageSize, mrgLeft, mrgRight, mrgTop, mrgBottom);
		
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Create PDF file
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
	}
	
	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException
	{
		writer.setViewerPreferences(getViewerPreferences());
	}
	
	protected int getViewerPreferences()
	{
		return  PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}
	
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request)
	{
	}
	
	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final String Titolo = "Elenco Rilevazioni";
		 
		//File name
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
           
        List<Rilevazioni> rilevazioni = (List<Rilevazioni>) model.get("Rilevazioni");
      
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);
      
 
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);
 
        // Set header
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
 
        // Set document
        cell.setPhrase(new Phrase("Data", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("IdTerminale", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Barcode", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Descrizione", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Qta", font));
        table.addCell(cell);
 
        for(Rilevazioni rilevazione : rilevazioni)
        {
            table.addCell(rilevazione.getData());
            table.addCell(rilevazione.getIdTerminale());
            table.addCell(rilevazione.getBarcode());
            table.addCell(rilevazione.getDescrizione());
            table.addCell(rilevazione.getQta());
        } 
 
        document.addTitle(Titolo);
        document.add(new Paragraph("Documento Creato il " + LocalDate.now()));
        document.setPageCount(0);
     
        document.add(table);
    } 
}

