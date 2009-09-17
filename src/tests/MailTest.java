package tests;

import java.lang.reflect.Method;
import java.net.URI;

public class MailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 String subject = "Error%20report";
         String body = "Description%20and%20contact%20information:";
         String mailtoURI = "mailto:"+"kornelius.walter@googlemail.com"+"?SUBJECT="+subject+"&BODY="+body;

         /* This is a Wrapper for Desktop.getDesktop().open(outputFile);
          * that will do that for Java >=6 and nothing for
          * Java 5.
          */    
 		try {	
 			URI uriMailTo = new URI(mailtoURI);
 			Method main = Class.forName("java.awt.Desktop").getDeclaredMethod("getDesktop");
 			Object obj = main.invoke(new Object[0]);
 			Method second = obj.getClass().getDeclaredMethod("mail", new Class[] { URI.class }); 
 			second.invoke(obj, uriMailTo);
 		} catch (Exception e) {			
 			e.printStackTrace(); 			
 		}

	}

}
