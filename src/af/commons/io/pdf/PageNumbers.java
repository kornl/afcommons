package af.commons.io.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PageNumbers extends PdfPageEventHelper {

    public PdfTemplate tpl;
    public BaseFont helv;	
	
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            tpl = writer.getDirectContent().createTemplate(100, 100);
            tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
            helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
        }
        catch(Exception e) {
            throw new ExceptionConverter(e);
        }
    }    
    
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        String text = "Page " + writer.getPageNumber() + " of ";
        float textSize = helv.getWidthPoint(text, 12);
        float textBase = document.bottom() - 20;
        cb.beginText();
        cb.setFontAndSize(helv, 12);

        float adjust = helv.getWidthPoint("0", 12);
        cb.setTextMatrix((document.right()+document.left())/2 - textSize - adjust, textBase);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(tpl, (document.right()+document.left())/2 - adjust, textBase);
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
       tpl.beginText();
       tpl.setFontAndSize(helv, 12);
       tpl.setTextMatrix(0, 0);
       tpl.showText("" + (writer.getPageNumber() - 1));
       tpl.endText();
    }
}
