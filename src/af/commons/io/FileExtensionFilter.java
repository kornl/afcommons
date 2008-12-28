package af.commons.io;

import java.io.File;

/**
 * Derived FileFilter class. Filters file by a single extension.
 * Extends the FileFilter in javax.swing.filechooser
 * and implements the filter in java.io.
 */

// apparently java has 2 different file filters.... noice.
public class FileExtensionFilter
        extends javax.swing.filechooser.FileFilter
        implements java.io.FileFilter {

    // extension of files to accept
    private String extension;
    // description of extension
    private String description;

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
     * @param description description of extension
     * @param extension extension of files to accept
     */
     public FileExtensionFilter(String description, String extension) {
        this.description = description;
        this.extension = extension;
    }

    /**
     * accept file if it has the correct extension or is a directory
     * @param f
     * @return
     */
    public boolean accept(File f) {
        return f.isDirectory() || f.toString().endsWith("." + extension);
    }

    /**
     * @return description of extension
     */
    public String getDescription() {
        return description;
    }
}
