package af.commons.io.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PDFHelper {
	
    public static Font font = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
	public static Font fontb = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
	public static Font fonth = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	
	/**
	 * Returns a com.lowagie.text.Image from a image or PDF file.
	 * In the second case the first page of the PDF file is returned as image.
	 * @param writer
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws BadElementException
	 */	
	public static Image getImage(PdfWriter writer, File file) throws IOException, BadElementException {
		Image image = null;
		if(isPDF(file)) {
			image = getPdfImage(writer, file);
		} else {
			image = Image.getInstance(file.getAbsolutePath());
		}
		image.setAlignment(Image.MIDDLE);
		writer.setStrictImageSequence(true);
		return image;
	}

	/**
	 * Returns a com.lowagie.text.Image from the first page of a PDF file. 
	 * @param writer
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws BadElementException
	 */
	public static Image getPdfImage(PdfWriter writer, File file) throws IOException, BadElementException {
		PdfReader reader = new PdfReader(file.getAbsolutePath());
		PdfImportedPage importedPage = writer.getImportedPage(reader, 1);
		return Image.getInstance(importedPage);
	}
	
	/**
	 * Determines whether a given file is a PDF file by extension.
	 * Alternatively we could test whether the first 4 byte are "%PDF-".
	 * @param file
	 * @return Has the given file the extension ".pdf"?
	 */
	public static boolean isPDF(File file) {
		String name = file.getName();		
		boolean result = false;
		String extension = name.substring(file.getName().length()-3);		
		if (extension.equalsIgnoreCase("pdf")) {
			result=true;
		}
		return result;
	}
	
	/**
	 * Writes a Result-List and its list of files in a PDF document.
	 * @param document PDF document
	 * @param writer PdfWriter
	 * @param result Result-List
	 * @param plots List of plot files
	 * @throws IOException 
	 * @throws DocumentException
	 */
	public static PdfPTable createTable(List<List<Object>> xtable, List<String> header) throws BadElementException, IOException {		
		PdfPTable table = new PdfPTable(xtable.get(0).size());
		Cell cell;
		
		if (header!=null) {
			for (String s : header) {
				cell = new Cell(new Chunk(s, fontb));
				cell.enableBorderSide(Rectangle.LEFT);
				cell.enableBorderSide(Rectangle.RIGHT);
				cell.enableBorderSide(Rectangle.BOTTOM);
				cell.setBorderWidth(0.5f);
				table.addCell(cell.createPdfPCell());
			}
		}

		for (List<Object> row : xtable) {			
			for (Object e : row) {
				if (e instanceof BufferedImage) {
					cell = new Cell(Image.getInstance((BufferedImage)e, null));
				} else {				
					cell = new Cell(new Chunk(e.toString(), font));
				}
				enable4Borders(cell);
				table.addCell(cell.createPdfPCell());
			}
		}
		return table;
	}
	
	/**
	 * Sets the four borders of a cell to 0.5f.
	 * @param cell Cell which borders are set.
	 */
	public static void enable4Borders(Cell cell) {
		cell.enableBorderSide(Rectangle.LEFT);
		cell.enableBorderSide(Rectangle.RIGHT);
		cell.enableBorderSide(Rectangle.TOP);
		cell.enableBorderSide(Rectangle.BOTTOM);
		cell.setBorderWidth(0.5f);
	}
	
}
