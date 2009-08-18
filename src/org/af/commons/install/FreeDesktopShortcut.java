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

public class FreeDesktopShortcut extends DesktopShortcut {

	protected String genericname = null;
	protected String comment = "";
	protected boolean terminal = false;
	protected String path = "";
	protected String paramStr = "";
	protected IOException ioex = null;

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     */
    public FreeDesktopShortcut(File shortcutDir, String name, File exec) {
        super(shortcutDir, name, exec);
    }

    public FreeDesktopShortcut(File shortcutDir, String name, String exec) {
        super(shortcutDir, name, exec);
    }


    public void createDesktopEntry() throws IOException {
		String filename = name+".desktop";
        PrintWriter outputStream = null;
		File file = new File(shortcutDir, filename);
		try {
			outputStream = new PrintWriter(new FileWriter(file));
			outputStream.println("[Desktop Entry]");
			outputStream.println("Version=1.0");
			outputStream.println("Name="+name);
			if (genericname != null) outputStream.println("GenericName="+genericname);
			outputStream.println("Type=Application");
			if (iconpath != null) outputStream.println("Icon="+iconpath);
			if (comment != null) outputStream.println("Comment="+comment);
			outputStream.println("Terminal="+(terminal==false?"false":"true"));			
			outputStream.println("Exec="+exec + " " + paramStr );			
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
        /* This is a Wrapper for file.setExecutable(true);
         * that will do that for Java >=6 and nothing for
         * Java 5.         
        Class<?> c = File.class;
	    Class[] argTypes = new Class[] { Boolean.class };
	    Method main;
		try {
			main = c.getDeclaredMethod("setExecutable", argTypes);
			main.invoke(file, (Boolean)true);
		} catch (Exception e) {
			System.out.println("No method setExecutable in Java 5.");
		}
		*/
        
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

    
	public void addParameter(String param) {
        paramStr += " " + param;
//	    param.replaceAll("\\", "\\\\\\\\");
//		param.replaceAll("`", "\\\\`");
//		param.replaceAll("\"", "\\\\\"");
//		param.replaceAll("$", "\\\\$");
//        this.exec += " \""+param+"\"";
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setIoex(IOException ioex) {
		this.ioex = ioex;
	}
	
}
