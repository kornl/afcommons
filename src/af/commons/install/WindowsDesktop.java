package af.commons.install;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import af.commons.OSTools;

/**
 * creates windows desktop url entry
 */

public class WindowsDesktop {

	protected String iconpath = null;
	
	/**
	 * Sets the icon for the desktop entry.
	 * @param iconpath Absolute path which will be used to specify the icon for the desktop entry.
	 */
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}
	
    public void createDesktopEntry(File desktopDir, String name, File installDir)
            throws IOException {       
    	
        List<String> cmds = new Vector<String>();
        cmds.add("set objWSHShell = CreateObject(\"WScript.Shell\")");
        cmds.add("set objSC = objWSHShell.CreateShortcut(\"" +
        		desktopDir.getAbsolutePath() +
        		"\\" + name  + ".lnk\")");
        if (iconpath!=null) {
        	cmds.add("objSC.IconLocation = "+iconpath);
        }
        //TargetPath = Path to source file or folder
        cmds.add("objSC.TargetPath = \"" + installDir + "\\run.exe\"");		
        cmds.add("objSC.WorkingDirectory = \"" + installDir.getAbsolutePath() + "\"");
		cmds.add("objSC.Save");

        //Description - Description of the shortcut
        //objSC.Description = "Shortcut to MyLog file"

//        ' HotKey ? hot key sequence to launch the shortcut
//        objSC.HotKey = "CTRL+ALT+SHIFT+X"

//        ' IconLocation ? Path of icon to use for the shortcut file
//        objSC.IconLocation = "notepad.exe, 0"  ' 0 is the index

//        ' Arguments ? Any additional parameters to pass to TargetPath
//        objSC.Arguments = "c:\mylog.txt"

//        ' WindowStyle ? Type of window to create
//        objSC.WindowStyle = 1   ' 1 = normal; 3 = maximize window; 7 = minimize

        // WorkingDirectory ? Location of the working directory for the source app
        
        File makeLinkVbsFile = new File(installDir, "makeLink.vbs");
        OSTools.makeShellScript(makeLinkVbsFile, cmds);
        OSTools.runShellScript(makeLinkVbsFile, installDir);
    }

	public void createDesktopEntry(String string, File installDir) throws IOException {
		createDesktopEntry(OSTools.guessDesktop(), string, installDir);
		
	}

}
