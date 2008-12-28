package af.commons.cleanup;

/**
 * Implement this interface if you want to add an "destructor" to your class.
 */
public interface NeedsCleanUpAtShutDown {
    /**
     * Your clean up code goes here. Called by CleanUpOnShutDown on exit. 
     */
    public void cleanUpAtShutdown();
}
