package org.af.commons.install;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CreateFreeDesktopStarter is a Class for creating desktop entries according to
 * the "Desktop Entry Specification 1.0" of freedesktop.org, which is implemented  
 * for example by KDE and GNOME.
 * 
 * See http://standards.freedesktop.org/desktop-entry-spec/desktop-entry-spec-1.0.html
 */

public class FreeDesktop {

	protected String application = "";
	protected String name = "";
	protected String genericname = null;
	protected String iconpath = "";
	protected String comment = "";
	protected boolean terminal = false;
	protected String exec = "";
	protected String path = "";
	
	protected IOException ioex = null;
	
    public void createDesktopEntry(File desktopDir, String nameOfEntry)
            throws IOException {
		String filename = nameOfEntry+".desktop";
        PrintWriter outputStream = null;
		File file = new File(desktopDir, filename);		
		try {
			outputStream = new PrintWriter(new FileWriter(file));
			outputStream.println("[Desktop Entry]");
			outputStream.println("Version=1.0");
			outputStream.println("Name="+nameOfEntry);
			if (genericname != null) outputStream.println("GenericName="+genericname);
			outputStream.println("Type=Application");
			outputStream.println("Icon="+iconpath);
			outputStream.println("Comment="+comment);
			outputStream.println("Terminal="+(terminal==false?"false":"true"));			
			outputStream.println("Exec="+exec);			
			outputStream.println("# Desktop Entry created by CreateFreeDesktopStarter");
        } catch (IOException ioex) {
        	this.ioex = ioex;	
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (this.ioex!=null) throw ioex;
        }
        file.setExecutable(true);
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}

	/**
	 * Sets the icon for the desktop entry.
	 * @param icon Icon file which absolute path will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(File icon) {
		this.iconpath = icon.getAbsolutePath();
	}

	/**
	 * Sets the icon for the desktop entry.
	 * @param iconpath Absolute path which will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}
	
	public void addParameter(String param) {
		param.replaceAll("\\", "\\\\\\\\");
		param.replaceAll("`", "\\\\`");
		param.replaceAll("\"", "\\\\\"");
		param.replaceAll("$", "\\\\$");
		this.exec += " \""+param+"\"";
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setIoex(IOException ioex) {
		this.ioex = ioex;
	}
	
}
