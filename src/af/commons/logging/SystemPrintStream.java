package af.commons.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Relocate standard output stream to log file.
 */


public class SystemPrintStream extends PrintStream {
    private static final Log logger = LogFactory.getLog(SystemPrintStream.class);

    private static final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final String  name;
    private boolean printToConsole = true;

    /**
     * Constructor
     *
     * @param name Name of stream, for logging info
     */

    public SystemPrintStream(String name, boolean printToConsole) {
        super(stream, true);
        this.printToConsole = printToConsole;
        logger.info("SystemPrintStream created: " + name);
        this.name = name;
    }

    /**
     * called after each println
     */

    private void logLine() {
        logger.info(name + ": " + stream.toString());
        stream.reset();
    }

    /* ******************println methods *********************/
    public void println(String x) {
        super.println(x);
        logLine();
    }

    public void println(Object x) {
        super.println(x);
        logLine();
    }

    public void println(long x) {
        super.println(x);
        logLine();
    }

    public void println(int x) {
        super.println(x);
        logLine();
    }

    public void println(float x) {
        super.println(x);
        logLine();
    }

    public void println(double x) {
        super.println(x);
        logLine();
    }

    public void println(char x[]) {
        super.println(x);
        logLine();
    }

    public void println(char x) {
        super.println(x);
        logLine();
    }

    public void println(boolean x) {
        super.println(x);
        logLine();
    }

    public void println() {
        super.println();
        logLine();
    }
}


