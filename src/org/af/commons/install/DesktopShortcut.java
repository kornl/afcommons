package org.af.commons.install;

import java.io.File;
import java.io.IOException;

public abstract class DesktopShortcut {

    protected String name;
    protected File shortcutDir;
    protected String exec = null;
    protected String iconpath = null;

    protected DesktopShortcut(File shortcutDir, String name) {
        this.shortcutDir = shortcutDir;
        this.name = name;
    }

    /**
     * Returns the targetted executable for this shortcut.
     * @return the targetted executable for this shortcut.
     */
    public String getExec() {
        return exec;
    }

    /**
     * Sets the targetted xecutable by for this shortcut.
     * @param exec the targetted xecutable by for this shortcut.
     */
    public void setExec(String exec) {
        this.exec = exec;
    }

    /**
     * Sets the targetted xecutable by for this shortcut.
     * @param exec the targetted xecutable by for this shortcut.
     */
    public void setExec(File exec) {
        this.exec = exec.getAbsolutePath();
    }

    /**
     * Add a parameter to the executable.
     * Paramteters are automatically separated by whitespaces.
     * Don't forget to enclose file paths in extra quotes.
     * @param param added parameter for executable target.
     */
    public abstract void addParameter(String param);

    public void addParameter(File param) {
        addParameter("\"" + param.getAbsolutePath() + "\"");
    }

    /**
     * 
     * @return
     */
    public String getIconpath() {
        return iconpath;
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


    /**
     *
     * @throws java.io.IOException
     */
    public abstract void createDesktopEntry() throws IOException;

}
