package org.af.commons.install;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.af.commons.OSTools;


/**
 * creates windows desktop url entry
 */

public class WindowsDesktop {

	protected String iconpath = null;
	protected String description = null;	
	protected String hotkey = null;
	protected String arguments = null;
	protected String exec = null;
	protected String workingDir = null;
	
	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * Set Hotkey like objSC.HotKey = "CTRL+ALT+SHIFT+X"; 
     */	
	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * Set WindowStyle like objSC.WindowStyle = 1   
	 * @param windowsstyle
	 * 			1 = normal; 3 = maximize window; 7 = minimize
	 */
	public void setWindowsstyle(Integer windowsstyle) {
		this.windowsstyle = windowsstyle;
	}

	protected Integer windowsstyle = null;
	
	/**
	 * Sets the icon for the desktop entry.
	 * @param iconpath Absolute path which will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}
	
	/**
	 * Sets the icon for the desktop entry like "notepad.exe, 0".
	 * @param iconpath Absolute path which will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(String exepath, int nr) {
		this.iconpath = exepath+", "+nr;
	}
	
	
	
    public void createDesktopEntry(File desktopDir, String name)
            throws IOException {       
    	
        List<String> cmds = new Vector<String>();
        cmds.add("set objWSHShell = CreateObject(\"WScript.Shell\")");
        cmds.add("set objSC = objWSHShell.CreateShortcut(\"" +
        		desktopDir.getAbsolutePath() +
        		"\\" + name  + ".lnk\")");
        if (iconpath!=null) {
        	cmds.add("objSC.IconLocation = \""+iconpath+"\"");
        }
        if (description!=null) {
        	cmds.add("objSC.Description = \""+iconpath+"\"");
        }
        if (hotkey!=null) {
        	cmds.add("objSC.HotKey = \""+iconpath+"\"");
        }
        if (arguments!=null) {
        	cmds.add("objSC.Arguments = \""+iconpath+"\"");
        }
        if (windowsstyle!=null) {
        	cmds.add("objSC.WindowStyle = "+windowsstyle);
        }
        
        //TargetPath = Path to source file or folder
        cmds.add("objSC.TargetPath = \"" + exec + "\"");	
        if (workingDir!=null) {
        	cmds.add("objSC.WorkingDirectory = \"" + workingDir + "\"");
        }
		cmds.add("objSC.Save");
        
        File makeLinkVbsFile = new File(System.getProperty("java.io.tmpdir"), "makeLink.vbs");
        OSTools.makeShellScript(makeLinkVbsFile, cmds);
        OSTools.runShellScript(makeLinkVbsFile, new File(System.getProperty("java.io.tmpdir")));
    }

	public void createDesktopEntry(String string) throws IOException {
		createDesktopEntry(OSTools.guessDesktop(), string);		
	}

}
