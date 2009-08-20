package org.af.commons.install;

import org.af.commons.tools.OSTools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;


/**
 * Creates a Windows desktop URL entry.
 */

public class WindowsDesktopShortcut extends DesktopShortcut {

	protected String hotkey = null;
    protected Integer windowsstyle = null;

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut.
     */
    public WindowsDesktopShortcut(File shortcutDir, String name, File exec) {
        super(shortcutDir, name, exec);
    }

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut. You can set an executable on the path here, but in general you should use the constructor with the File argument.
     */
    public WindowsDesktopShortcut(File shortcutDir, String name, String exec) {
        super(shortcutDir, name, exec);
    }


	/**
     * Set HotKey like objSC.HotKey = "CTRL+ALT+SHIFT+X"; 
     */	
	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	/**
	 * Set WindowStyle like objSC.WindowStyle = 1   
	 * @param windowsstyle
	 * 			1 = normal; 3 = maximize window; 7 = minimize
	 */
	public void setWindowsstyle(Integer windowsstyle) {
		this.windowsstyle = windowsstyle;
	}



	/**
	 * Sets the icon for the desktop entry like "notepad.exe, 0".
	 * @param iconpath Absolute path which will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(String iconpath, int nr) {
		this.iconpath = iconpath+", "+nr;
	}
	
    @Override
    public void create() throws IOException {

        List<String> cmds = new Vector<String>();
        cmds.add("set objWSHShell = CreateObject(\"WScript.Shell\")");
        cmds.add("set objSC = objWSHShell.CreateShortcut(\"" + shortcutDir.getAbsolutePath() +
                "\\" + name  + ".lnk\")");
        if (iconpath!=null) {
            cmds.add("objSC.IconLocation = \""+iconpath+"\"");
        }
        if (description!=null) {
            cmds.add("objSC.Description = \""+description+"\"");
        }
        if (hotkey!=null) {
            cmds.add("objSC.HotKey = \""+hotkey+"\"");
        }
        if (paramStr!=null && !(paramStr.length()==0)) {
            paramStr = paramStr.replaceAll("\"", "\"\"");
            cmds.add("objSC.Arguments = \""+paramStr+"\"");
        }
        if (windowsstyle!=null) {
            cmds.add("objSC.WindowStyle = "+windowsstyle);
        }

        //TargetPath = Path to source file or folder
        cmds.add("objSC.TargetPath = \"" + exec + "\"");
        if (workingDir!=null) {
            cmds.add("objSC.WorkingDirectory = \"" + workingDir.getAbsolutePath() + "\"");
        }
        cmds.add("objSC.Save");

        File makeLinkVbsFile = new File(System.getProperty("java.io.tmpdir"), "makeLink.vbs");
        OSTools.makeShellScript(makeLinkVbsFile, cmds);
        OSTools.runShellScript(makeLinkVbsFile, new File(System.getProperty("java.io.tmpdir")));
    }
}
