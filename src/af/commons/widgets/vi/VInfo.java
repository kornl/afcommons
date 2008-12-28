package af.commons.widgets.vi;

/**
 * This class only encapsulates its two String fields version and info.  
 */
public class VInfo {
	public String version;
	public String info;
	
	/**
	 * Standard constructor
	 * @param version version number
	 * @param info info text
	 */
	public VInfo(String version, String info) {
		this.version = version;
		this.info = info;
	}
}
