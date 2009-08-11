package org.af.commons.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Zipper {
	
	public static void writeIntoZip(File zipDir, File zipfile) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipfile));		 
		
		zipDir(zipDir, zos, "");
		
		zos.close();
	}
	
	private static void zipDir(File zipDir, ZipOutputStream zos, String path) throws IOException {
		String[] dirList = zipDir.list(); 
		byte[] readBuffer = new byte[2156]; 
		int bytesIn = 0;
		for(int i=0; i<dirList.length; i++)	{ 
			File f = new File(zipDir, dirList[i]); 
			if(f.isDirectory()) {					 
				zipDir(f, zos, path+f.getName()+System.getProperty("file.separator")); 
				continue; 
			} 
			FileInputStream fis = new FileInputStream(f); 
			ZipEntry anEntry = new ZipEntry(path+f.getName()); 
			zos.putNextEntry(anEntry); 
			while((bytesIn = fis.read(readBuffer)) != -1) 
			{ 
				zos.write(readBuffer, 0, bytesIn); 
			} 
			fis.close(); 
		}
	}
	
	  public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0) {
	      out.write(buffer, 0, len);
	    }
	    in.close();
	    out.close();
	  }

	  public static final void unzip(File file, File outputDir) throws IOException {
	    
		  ZipFile zipFile = new ZipFile(file); 
		  Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();

	      while(entries.hasMoreElements()) {
	        ZipEntry entry = entries.nextElement();

	        if(entry.isDirectory()) {
	        	if (!(new File(outputDir, entry.getName())).exists()) {
	        		(new File(outputDir, entry.getName())).mkdirs();
	        	}
	          continue;
	        }
	        
	        // It seems to be possible to store files before there parents.
	        if (!(new File(outputDir, entry.getName())).getParentFile().exists()) {
	        	(new File(outputDir, entry.getName())).getParentFile().mkdirs();
	        }	        

	        copyInputStream(zipFile.getInputStream(entry),
	           new BufferedOutputStream(new FileOutputStream(outputDir+System.getProperty("file.separator")+entry.getName())));
	      }

	      zipFile.close();
	  }

}