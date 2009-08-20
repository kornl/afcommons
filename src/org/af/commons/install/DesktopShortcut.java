package org.af.commons.install;

import java.io.File;
import java.io.IOException;

/**
 * Abstract base class to create a shortcut on the desktop.
 * Subclasses provide implementations for different operating systems.
 */
public abstract class DesktopShortcut {

    // visible shortcut name
    protected String name;
    // dir to place shortcut
    protected File shortcutDir;
    // path to executable or shell command, without parameters
    protected String exec = null;
    // string of parameters for executable
    protected String paramStr = "";
    // absolute path to shortcut icon
    protected String iconpath = null;
    // working directory for shortcut
    protected File workingDir = null;
    // description string for shortcut
    protected String description = null;


    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut.
     */
    protected DesktopShortcut(File shortcutDir, String name, File exec) {
        this(shortcutDir, name, exec.getAbsolutePath());
    }

    /**
     * Constructor
     * @param shortcutDir Directory where shortcut is created.
     * @param name Visible name of the shortcut
     * @param exec The targetted executable for this shortcut. You can set an executable on the path here, but in general you should use the constructor with the File argument.
     */
    protected DesktopShortcut(File shortcutDir, String name, String exec) {
        this.shortcutDir = shortcutDir;
        this.name = name;
        this.exec = exec;
    }

    /**
     * Returns the visble name of the shortcut.
     * @return The visble name of the shortcut.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the directory the shortcut will be created in.
     * @return The directory the shortcut will be created in.
     */
    public File getShortcutDir() {
        return shortcutDir;
    }

    /**
     * Returns the targetted executable.
     * @return The targetted executable.
     */
    public String getExec() {
        return exec;
    }

    /**
     * Add a parameter to the executable.
     * Parameters are automatically separated by whitespaces.
     * Don't forget to enclose file paths in extra quotes.
     * @param param added parameter for executable target.
     */
    public void addParameter(String param) {
        paramStr += " " + param;
    }

    /**
     * Add a file path as parameter to the executable.
     * Parameters are automatically separated by whitespaces.
     * Automatically enclosed by quotes.
     * @param param added parameter for executable target.
     */
    public void addParameter(File param) {
        addParameter("\"" + param.getAbsolutePath() + "\"");
    }

    /**
     * Return the path to the used icon.
     * @return Path to used icon.
     */
    public String getIconpath() {
        return iconpath;
    }

    /**
     * Sets the icon for the desktop shortcut.
     * @param icon Icon file for the desktop entry.
     */
    public void setIconpath(File icon) {
        this.iconpath = icon.getAbsolutePath();
    }

    /**
     * Sets the icon for the desktop entry.
     * @param iconpath Absolute path to specify the icon for the desktop entry.
     */
    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    /**
     * Sets the working directory for the desktop shortcut.
     * @param workingDir The working directory for the desktop shortcut.
     */
    public void setWorkingDir(File workingDir) {
        this.workingDir = workingDir;
    }

    /**
     * Sets the working directory for the desktop shortcut.
     * @param workingDir The working directory for the desktop shortcut.
     */
    public void setWorkingDir(String workingDir) {
        this.workingDir = new File(workingDir);
    }

    /**
     * Returns the working directory for the desktop shortcut.
     * @return  The working directory for the desktop shortcut.
     */
    public File getWorkingDir() {
        return workingDir;
    }


    /**
     * Returns the description for this shortcut.
     * @return The description for this shortcut.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this shortcut.
     * @param description The description for this shortcut.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates the shortcut. Call this once after you have passed all relevant settings.
     * @throws java.io.IOException If something bad happens while writing the link.
     */
    public abstract void create() throws IOException;

}
