package af.commons.install;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import af.commons.OSTools;

/**
 * creates windows desktop url entry
 */

public class WindowsDesktop {

    public void createDesktopEntry(File desktopDir, String name, File installDir)
            throws IOException {
        String[] cmds = {

        "set objWSHShell = CreateObject(\"WScript.Shell\")",
        // Pass the path to the shortcut
        "set objSC = objWSHShell.CreateShortcut(\"" +
        desktopDir.getAbsolutePath() +
        "\\" + name  + ".lnk\")",

        //Description - Description of the shortcut
        //objSC.Description = "Shortcut to MyLog file"

//        ' HotKey ? hot key sequence to launch the shortcut
//        objSC.HotKey = "CTRL+ALT+SHIFT+X"

//        ' IconLocation ? Path of icon to use for the shortcut file
//        objSC.IconLocation = "notepad.exe, 0"  ' 0 is the index

        //TargetPath = Path to source file or folder
        "objSC.TargetPath = \"" + installDir + "\\run.exe\"",

//        ' Arguments ? Any additional parameters to pass to TargetPath
//        objSC.Arguments = "c:\mylog.txt"

//        ' WindowStyle ? Type of window to create
//        objSC.WindowStyle = 1   ' 1 = normal; 3 = maximize window; 7 = minimize

        // WorkingDirectory ? Location of the working directory for the source app
        "objSC.WorkingDirectory = \"" + installDir.getAbsolutePath() + "\"",
        "objSC.Save"
        };

        File makeLinkVbsFile = new File(installDir, "makeLink.vbs");
        OSTools.makeShellScript(makeLinkVbsFile, Arrays.asList(cmds));
        OSTools.runShellScript(makeLinkVbsFile, installDir);
    }

}
