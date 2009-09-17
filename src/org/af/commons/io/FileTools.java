package org.af.commons.io;

import java.awt.Component;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;

import org.af.commons.widgets.MyJFileChooser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class provides miscellaneous static methods for handling files.
 */
public class FileTools {
	
	protected static final Log logger = LogFactory.getLog(FileTools.class);
	
	/**
	 * Returns the filename without last suffix.
	 * Example: getBaseName("test.txt") returns "test".
	 * @return filename without last suffix.
	 */
	public static String getBaseName(File file) {
		String name = file.getName();
		if (name.lastIndexOf('.') == -1) return name;
		return name.substring(0, name.lastIndexOf('.'));
	}
	
    /**
     * Opens a JFileChooser for selecting a file or directory.
     * @param comp the parent component of the dialog,
     *        can be <code>null</code>;
     * @param prevFile previously chosen file
     * @param fileSelectionMode from JFileChooser
     * @return the selected file or <code>null</code> if no file was selected
     */
    public static File selectFile(Component comp, String prevFile, int fileSelectionMode) {
        return selectFile(comp, new File(prevFile), fileSelectionMode);
    }

    /**
     * Opens a JFileChooser for selecting a file or directory.
     * @param comp the parent component of the dialog,
     *        can be <code>null</code>;
     * @param fileSelectionMode from JFileChooser
     * @return the selected file or <code>null</code> if no file was selected
     */
    public static File selectFile(Component comp, int fileSelectionMode) {
        return selectFile(comp, "", fileSelectionMode);
    }


    /**
     * Opens a JFileChooser for selecting a file or directory.
     * @param comp the parent component of the dialog,
     *        can be <code>null</code>;
     * @param prevFile previously chosen file
     * @param fileSelectionMode from JFileChooser
     * @return the selected file or <code>null</code> if no file was selected
     */

    public static File selectFile(Component comp, File prevFile, int fileSelectionMode) {
        MyJFileChooser fc;
        if (prevFile.exists()) {
        	if (prevFile.isDirectory()) {
        		fc = MyJFileChooser.makeMyJFileChooser(prevFile);	
        	} else {
        		fc = MyJFileChooser.makeMyJFileChooser(prevFile.getParentFile());
        	}
        } else {
            fc = MyJFileChooser.makeMyJFileChooser();
        }
        fc.setFileSelectionMode(fileSelectionMode);
        int returnVal = fc.showOpenDialog(comp);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
    
    /**
     * Reads in a file and returns the content as a String.
     * @param file file to read
     * @return content of the file
     * @throws java.io.IOException
     */
    public static String readFileAsString(File file) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            fileData.append(buf, 0, numRead);
        }
        reader.close();
        return fileData.toString();
    }
    
    /**
     * Download a single file specified by an URL
     *
     * @param from        URL to file to download
     * @param destination directory to download to
     * @throws IOException 
     */

    public static void downloadFile(URL from, File destination) throws IOException {
        OutputStream out = null;
        URLConnection conn = null;
        InputStream in = null;
        try {
            String fileName = new File(from.getPath()).getName();
            File localFile = new File(destination, fileName);
            out = new BufferedOutputStream(
                    new FileOutputStream(localFile));
            conn = from.openConnection();
            in = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                logger.error("Could not close streams!", ioe);
            }
        }
    }


}
