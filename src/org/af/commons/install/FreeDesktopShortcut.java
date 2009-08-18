package org.af.commons.install;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * CreateFreeDesktopStarter is a Class for creating desktop entries according to
 * the "Desktop Entry Specification 1.0" of freedesktop.org, which is implemented  
 * for example by KDE and GNOME.
 * 
 * See http://standards.freedesktop.org/desktop-entry-spec/desktop-entry-spec-1.0.html
 */

public class FreeDesktopShortcut extends DesktopShortcut {

	protected String genericname = null;
	protected boolean terminal = false;

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut.
     */
    public FreeDesktopShortcut(File shortcutDir, String name, File exec) {
        super(shortcutDir, name, exec);
    }

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut. You can set an executable on the path here, but in general you should use the constructor with the File argument.
     */
    public FreeDesktopShortcut(File shortcutDir, String name, String exec) {
        super(shortcutDir, name, exec);
    }


    @Override
    public void create() throws IOException {
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
			if (description != null) outputStream.println("Comment="+description);
			outputStream.println("Terminal="+(terminal==false?"false":"true"));			
			outputStream.println("Exec="+exec + " " + paramStr );			
			outputStream.println("# Desktop Entry created by CreateFreeDesktopStarter");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        
        /* This is a Wrapper for file.setExecutable(true);
         * that will do that for Java >=6 and nothing for
         * Java 5.
         */         
        Class<?> c = File.class;
	    Class[] argTypes = new Class[] { boolean.class };	    
		try {
			Method main = c.getDeclaredMethod("setExecutable", argTypes);
			main.invoke(file, true);
		} catch (Exception e) {
			System.out.println("No method setExecutable in Java 5.");
		}
		
        
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}
}
