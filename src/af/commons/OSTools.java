package af.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import af.commons.io.FilterContains;

/**
 * The Class OSToolsLocal owns static methods to determine the Operating System
 * and to set up an OS-dependent environment.
 */
public class OSTools {
	protected static Log logger = LogFactory.getLog(OSTools.class);
	
	private static Boolean isLinux = null;
	private static Boolean isWindows = null;
	private static Boolean isMac = null;
	
	private static Boolean isVista = null;
	
	/**
	 * Checks whether OS is Vista by parsing the System property os.name.
	 * @return is OS Vista?
	 */
	public static boolean isVista() {
		if (isVista!=null) return isVista;
		String os = System.getProperty("os.name");
		isVista = new StringBuffer(os).indexOf("Vista") != -1;
		if (isVista) {
			OSTools.logger.info("Since os.name='"+os+"' contains substring 'Vista', we set isVista = TRUE.");
		} else {
			OSTools.logger.info("Since os.name='"+os+"' does not contain substring 'Vista', we set isVista = FALSE.");
		}				
		return isVista;		
	}	
	
	/**
	 * Checks whether OS is Linux by parsing the System property os.name.
	 * @return is OS Linux?
	 */
	public static boolean isLinux() {
		if (isLinux!=null) return isLinux;
		String os = System.getProperty("os.name");
		isLinux = new StringBuffer(os).indexOf("Linux") != -1;
		if (isLinux) {
			OSTools.logger.info("Since os.name='"+os+"' contains substring 'Linux', we set isLinux = TRUE.");
			OSTools.isWindows = false;
			OSTools.isMac = false;
		} else {
			OSTools.logger.info("Since os.name='"+os+"' does not contain substring 'Linux', we set isLinux = FALSE.");
		}				
		return isLinux;		
	}
	
	public static boolean isMac() {
		if (isMac!=null) return isMac;
		String os = System.getProperty("os.name");
		isMac = new StringBuffer(os).indexOf("Mac OS") != -1;
		if (isMac) {
			OSTools.logger.info("Since os.name='"+os+"' contains substring 'Mac OS', we set isMac = TRUE.");
			OSTools.isWindows = false;
			OSTools.isLinux = false;
		} else {
			OSTools.logger.info("Since os.name='"+os+"' does not contain substring 'Mac OS', we set isMac = FALSE.");
		}				
		return isMac;
	}
	
	public static boolean isUnix() {
		return isMac() || isLinux();
	}
	
	/**
	 * In case of doubt we assume Windows to be our OS.
	 * Since up to now only Linux, Mac and Windows are supported 
	 * isWindows() just returns !isLinux() && !isMac().
	 * @return is OS Windows? 
	 */
	public static boolean isWindows() {
		if (isWindows!=null) return isWindows;		
		OSTools.isWindows = !isLinux() && !isMac();
		return isWindows;		
	}

    public static void makeShellScript(File target, List<String> commands) throws FileNotFoundException, UnsupportedEncodingException {
    	logger.info("Creating start script: "+target.getAbsolutePath());
        PrintStream p;
        if (OSTools.isWindows()) {
            // make sure to use the write encoding for windows, so we get no problem with special charatcers like umlauts
            p = new PrintStream(new FileOutputStream(target), true, "cp850");
        }
        else {
            p = new PrintStream(new FileOutputStream(target));
        }
        for (String c:commands)
            p.println(c);
        p.close();
    }

    public static Process runShellScript(File script, File workingDir) throws IOException{
       return runShellScript(script, workingDir, new ArrayList<String>());
    }

    public static String getBash() {
    	String[] tests = {"/bin/bash","/usr/bin/bash","/usr/local/bin/bash"};
    	for (String test : tests) {
    		File file = new File(test);
    		if (file.exists()) return test;
    	}
    	return "/bin/sh";
    	// http://www.pathname.com/fhs/pub/fhs-2.3.html tells us, this Bourne command shell exists. 
    }
    
    public static Process runShellScript(File script, File workingDir, List<String> inputStreamLines) throws IOException{
    	ProcessBuilder pb;
    	if (OSTools.isWindows()) {
    		pb = new ProcessBuilder(
    				"cmd", "/c", script.getAbsolutePath());
    	} else {
    		pb = new ProcessBuilder(getBash(), script.getAbsolutePath());
    	}
        pb.directory(workingDir);
        logger.info("Starting process: " + pb.command().toString());
        Process ps = pb.start();

        BufferedWriter psWriter = new BufferedWriter(new OutputStreamWriter(ps.getOutputStream()));

        for (String s : inputStreamLines) {
            logger.info("Piping into process: " + s);
            psWriter.write(s);
            psWriter.newLine();
        }
        psWriter.close();
        return ps;
    }


    public static int runShellScript(File script,
                                     File workingDir,
                                     List<String> inputStreamLines,
                                     List<String> outputStreamLines) throws IOException, InterruptedException{
        Process ps = runShellScript(script, workingDir, inputStreamLines);

        int exitCode = ps.waitFor();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(ps.getInputStream()));
        String s;
        while ((s = reader.readLine()) != null)
            outputStreamLines.add(s);
        reader.close();
        return exitCode;
    }
    
    public static File guessDesktop() {
    	if (OSTools.isWindows()) {
    		File desktop = new File(System.getProperty("user.home")+"/Desktop");
            if (desktop.exists()) return desktop;
        } else {
        	File desktop = new File(System.getProperty("user.home")+"/Desktop");
            if (desktop.exists()) return desktop;
        }
    	return null;
    }
    
    /**
     * Guesses absolute path to PDF viewer.
     * @return Guessed path to PDF viewer.
     */
    public static String guessPDFViewerPath() {
    	if (OSTools.isWindows()) {
    		String[] search = {"C:\\Programme", "C:\\Program Files" };
    		String[] searchDir = {"ADOBE", "ACR", "READER"};
    		String[] searchFile = {"ACRORD32.EXE", "ACRORD64.EXE"};
    		return searchDir(search, searchDir, searchFile, false);
    	} else {
    		String[] candidates = {"/usr/bin/xpdf", "/usr/bin/evince", "/usr/bin/kpdf"};
    		for (String s : candidates) {
    			File pdfviewer = new File(s);
            	if (pdfviewer.exists()) return pdfviewer.getAbsolutePath();
    		}
            return "";
    	}
    }
    

	
    /**
     * Searches for files and returns the first candidate.     
     * @param search Array of paths to the root directories of this search. 
     * @param searchDir Files which contain these Strings as substrings should be searched.
     * @param searchFile Files which contain these Strings as substrings are final candidates.
     * @param directory is the file we are looking for a directory? 
     * @return
     */
	public static String searchDir(String[] search, String[] searchDir, String[] searchFile, boolean directory) {
		String result = "";
		for (String s : search) {
			File dir = new File(s);
			if (dir.exists()) {
				String subresult=searchforFile(dir, searchDir, searchFile, directory);
				if (!subresult.equals("")) result = subresult;
			}
		}
		return result;
	}
	
	public static String searchforFile(File dir, String[] searchDir, String[] searchFile, boolean directory) {
		String result = "";
		logger.debug("We are in the directory: "+dir.getAbsolutePath());
		if(dir.listFiles()==null) {
			logger.warn("This directory returns null as listFiles()!");
			return ""; 
		}
		for (File f : dir.listFiles()) {
			logger.debug(f.getAbsolutePath());
		}
		
		File[] candidates = dir.listFiles(new FilterContains(searchDir));
		if ((candidates == null) || (dir == null)) { 
			logger.warn("Error! Nullpointer!"); 
			return ""; 
			}
		logger.info("Found "+candidates.length+" candidates in "+dir.getAbsolutePath()+".");		
		for (File f : candidates) {
			if(f.isDirectory() == directory) {
				String name = f.getName().toUpperCase();
				for (String s : searchFile) {
					if (name.indexOf(s) != -1) {
						result = f.getAbsolutePath();	
						logger.info("Found: "+f.getAbsolutePath());
					}
				}
			}
			if (f.isDirectory()) {
				String subresult=searchforFile(f, searchDir, searchFile, directory);
				if (!subresult.equals("")) result = subresult;
			} 
		}
		return result;
	}
    
    /**
     * Guesses Options necessary for PDF viewer. So far:
     * Windows + Acrobat :  /s /o
     * @param pdfviewerpath Absolute path to PDF viewer. 
     * @return PDF-Viewer-Options
     */
    
    public static String guessPDFViewerOptions(String pdfviewerpath) {
    	StringBuffer sb = new StringBuffer(pdfviewerpath.toUpperCase());
    	if (OSTools.isWindows() && sb.indexOf("ACRO")!= -1) {
    		logger.info("We are under Windows and the pdfviewerpath contains \"ACRO\",\n" +
    				"so we set the PDF-Viewer-Options to \"/s /o\".");    		 
    		return "/s /o";
    	}    	
    	return "";
    }    
    
    /**
     * Returns system temporary directory.
     * @return Guessed path to R Home.
     */
    public static String getTempDir() {
    	return System.getProperties().getProperty("java.io.tmpdir");
    }
    
}
