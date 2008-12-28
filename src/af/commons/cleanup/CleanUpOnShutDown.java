package af.commons.cleanup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to free all resources and clean up the system when applications exits.
 * Usage: If your class needs to have something done during "destruction", implement the
 * interface NeedsCleanUpAtShutDown and register it with addResource.
 * You can also register temp. files which will be deleted on exit.
 */

public class CleanUpOnShutDown {
    // singelton
    private static CleanUpOnShutDown instance = new CleanUpOnShutDown();
    // all objects which need clean up
    private List<NeedsCleanUpAtShutDown> resources = new ArrayList<NeedsCleanUpAtShutDown>();
    // registered temp files
    private List<File> tempFiles = new ArrayList<File>();

    /**
     * Private constructor because it's singelton
     */
    private CleanUpOnShutDown() {}

    /**
      * @return singelton instance
     */
    public static CleanUpOnShutDown getInstance() {
        return instance;
    }

    /**
     * register object which needs clean up on shutdown
     * @param r resource to register
     */
    public void addResource(NeedsCleanUpAtShutDown r) {
        resources.add(r);
    }

    /**
     * called on exit
     */
    public void cleanUpAll() {
        // clean up all the registered objects
        for (NeedsCleanUpAtShutDown r:resources) {
            r.cleanUpAtShutdown();
        }
        // and remove temp files
        deleteTempFiles();
        System.exit(0);
    }

    /**
     * @param f temp file to delete on exit
     */
    public void registerTempFile(File f) {
        tempFiles.add(f);
    }


    /**
     * delete all registered temp. files on exit
     */
    private void deleteTempFiles() {
        for (File f : tempFiles) {
            f.delete();
        }
    }

}
