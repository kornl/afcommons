package af.commons.io.pdf;

import java.io.File;
import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PDFHelper {
	
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
}
