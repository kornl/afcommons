package af.commons.io;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;

import af.commons.widgets.MyJFileChooser;

public class FileTools {

	public static void unpack(File file, File directory) throws IOException {
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				throw new IOException("Could not create directory "+directory.getAbsolutePath()+".");
			}
		}
		int BUFFER = 2048;
		BufferedOutputStream dest = null;
		BufferedInputStream is = null;
		ZipEntry entry;
		ZipFile zipfile = new ZipFile(file);
		Enumeration e = zipfile.entries();
		while(e.hasMoreElements()) {
			entry = (ZipEntry) e.nextElement();
			System.out.println("Extracting: " +entry);
			is = new BufferedInputStream
			(zipfile.getInputStream(entry));
			int count;
			byte data[] = new byte[BUFFER];
			File fo = new File(directory, entry.getName());
			if (fo.getParentFile() != null && !fo.getParentFile().exists()) {
				if (!fo.getParentFile().mkdirs()) {
					throw new IOException("Could not create directory "+fo.getParentFile().getAbsolutePath()+".");
				}
			}
			FileOutputStream fos = new 
			FileOutputStream(fo);
			dest = new 
			BufferedOutputStream(fos, BUFFER);
			while ((count = is.read(data, 0, BUFFER)) 
					!= -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
			is.close();
		}
	}
	
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
	
    public static File selectFile(Component comp, String prevDir, int fileSelectionMode) {
        return selectFile(comp, new File(prevDir), fileSelectionMode);
    }

    /**
     * Opens a JFileChooser for selecting a file or directory.
     * @param prevFile previously chosen file
     * @param fileSelectionMode from JFileChooser
     */

    public static File selectFile(Component comp, File prevFile, int fileSelectionMode) {
        MyJFileChooser fc;
        if (prevFile.exists() && prevFile.isDirectory())
            fc = MyJFileChooser.makeMyJFileChooser(prevFile);
        else
            fc = MyJFileChooser.makeMyJFileChooser();
        fc.setFileSelectionMode(fileSelectionMode);
        int returnVal = fc.showOpenDialog(comp);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        else
            return null;
    }

}
