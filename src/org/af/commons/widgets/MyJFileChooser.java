package org.af.commons.widgets;

import java.io.File;

import javax.swing.JFileChooser;

public class MyJFileChooser extends JFileChooser {
    protected static File initialCurrentDir;

    private MyJFileChooser() {
        super(initialCurrentDir);
    }

    public static void setInitialCurrentDir(File currentDir) {
        MyJFileChooser.initialCurrentDir = currentDir;
    }

    public static MyJFileChooser makeMyJFileChooser() {
        if (    initialCurrentDir == null ||
                !initialCurrentDir.exists() ||
                !initialCurrentDir.isDirectory()) {
            initialCurrentDir = new File(System.getProperty("user.home"));
        }
        return new MyJFileChooser();
    }

    public static MyJFileChooser makeMyJFileChooser(File currentDir) {
        initialCurrentDir = currentDir;
        return new MyJFileChooser();
    }

    public void setSelectedFile(File file) {
        super.setSelectedFile(file);
        setInitialCurrentDir(getCurrentDirectory());
    }
}
