package af.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileTransfer {
	
	private static final Log logger = LogFactory.getLog(FileTransfer.class);
	
	private static FileTransfer ft = null;
	
	public static FileTransfer getFileTransfer() {
		if (ft == null) {
			ft = new FileTransfer();
		}
		return ft;
	} 
	
	public File copyFileToLocalDir(String name, File localDir) throws IOException {
		 return copyFileToLocalDir("", name, localDir);
	}
	
    public File copyFileToLocalDir(String path, String name, File localDir) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        logger.info("Retrieving File:" + path + "/" + name);
        URL resource = getClass().getResource(path + "/" + name);
        if (resource.toString().startsWith("file:")) {
        	logger.info("No need for copying - this is a local file.");
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                logger.error(e);
                throw new IOException(e.getMessage()); 
            }
        }
        File file = new File(localDir, name);
        logger.info("Copying from " + resource + " to " + file.getAbsolutePath() + ".");
        is = resource.openConnection().getInputStream();
        fos = new FileOutputStream(file);
        byte[] buffer = new byte[8];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer))
            fos.write(buffer, 0, len);
        if (is != null) {
            is.close();
        }
        if (fos != null) {
            fos.close();
        }
        logger.info("Copy completed.");
        return file;        
    }

    public void copyFile(File in, File out) throws IOException {
    	if (out.exists()) { out.delete(); }
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        }
        finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }
    
    /**
     * Move a file. If the target exists, it will be overwritten.
     * @param f
     * @param targetDir
     * @param name
     * @throws IOException
     */
    public void moveFile(File f, File targetDir, String name) throws IOException {
    	moveFile(f, targetDir, name, true);
    }

    public void moveFile(File f, File targetDir, String name, boolean overwrite) throws IOException{
        if (!f.exists()) {
            throw new FileNotFoundException("Tried to move file but it does not exist:" + f);
        }
        if (!f.isFile()) {
            throw new IOException("Tried to move file but it's not a file: " + f);
        }
        if (!targetDir.isDirectory()) {
            throw new IOException("Tried to move file but target dir is not a dir: " + targetDir);
        }
        if (!targetDir.canWrite()) {
            throw new IOException("Tried to move file but target dir is not writeable: " + targetDir);
        }
        File target = new File(targetDir, name);
        if (!overwrite && target.exists()) {
            throw new IOException("Tried to move file but target already exists: " + target);
        }
        copyFile(f, target);
        f.delete();
        /*boolean success = f.renameTo(target);
        if (!success) {
            throw new IOException("Could not move file " + f + " to " + target.getAbsolutePath());
        }*/
    }
    
    public void moveFile(File f, File targetDir) throws IOException{
        moveFile(f, targetDir, f.getName());
    }
}
