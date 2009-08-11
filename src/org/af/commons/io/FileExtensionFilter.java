package org.af.commons.io;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Derived FileFilter class. Filters file by a single extension.
 * Extends the FileFilter in javax.swing.filechooser
 * and implements the filter in java.io.
 */

public class FileExtensionFilter
        extends javax.swing.filechooser.FileFilter
        implements java.io.FileFilter {

    /** List of extensions of files to accept */
    private List<String> extension;
    /** description of extension */
    private String description;
    private boolean acceptDirectories;

    /**
     * Constructor
     *
     * @param extension extension of files to accept
     */
    public FileExtensionFilter(String extension) {
        this(extension, extension);
    }
    
    /**
     * Constructor
     *
     * @param extension extension of files to accept
     */
    public FileExtensionFilter(String extension, boolean acceptDirectories) {
        this(extension, extension);
        this.acceptDirectories = acceptDirectories;
    }

    /**
     * Constructor
     *
     * @param description description of extension
     * @param extension extension of files to accept
     */
     public FileExtensionFilter(String description, String extension) {
        this.description = description;
        this.extension = new Vector<String>();
        this.extension.add(extension);
    }

     /**
      * Constructor
      *
      * @param extension extension of files to accept
      */
     public FileExtensionFilter(List<String> extension) {
    	 String descr = "";
    	 for (String s : extension) {
    		 descr = s+" ";
    	 }
         this.description = descr;
         this.extension = extension;
     }
     
     /**
      * Constructor
      *
      * @param description description of extension
      * @param extension extension of files to accept
      */
      public FileExtensionFilter(String description, String extension, boolean acceptDirectories) {
         this.description = description;
         this.extension = new Vector<String>();
         this.extension.add(extension);
         this.acceptDirectories = acceptDirectories;
     }

     /**
      * Constructor
      *
      * @param description description of extension
      * @param extension extension of files to accept
      */
      public FileExtensionFilter(String description, List<String> extension) {
         this.description = description;
         this.extension = extension;
     }
     
     
    /**
     * accept file if it has the correct extension
     * @param f
     * @return
     */
    public boolean accept(File f) {    	
    	for (String ext : extension) {
    		if (f.getName().endsWith("." + ext)) return true; 
    		if (acceptDirectories && f.isDirectory()) return true;
    	}
        return false;
    }

    /**
     * @return description of extension
     */
    public String getDescription() {
        return description;
    }

}
