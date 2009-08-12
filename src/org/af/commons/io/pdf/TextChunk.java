package org.af.commons.io.pdf;

import com.lowagie.text.Document;

/**
 * This interface provides only one method <code>print(Document document)</code> that prints content to a pdf document.
 */
public interface TextChunk {
	public void print(Document document);
}
