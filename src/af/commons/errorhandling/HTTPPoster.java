package af.commons.errorhandling;

import java.net.*;
import java.util.Collections;
import java.util.Hashtable;
import java.io.*;

/**
 * Uploads String values and Files via HTTP. 
 */
public class HTTPPoster {

	public static final int maxBufferSize = 1*1024*1024;
	
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";
	
	/**
	 * 
	 * @param urlString the URL to that we want to send a POST
	 * @param table is ignored up to now
	 * @param files will be attached
	 * @return
	 * @throws IOException
	 */
	
	public String post(String urlString, Hashtable<String,String> table, Hashtable<String,File> files) throws IOException {				
		
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

		DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );

		for (String name : Collections.list(files.keys())) {

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\""+name+"\";"
					+ " filename=\"" + files.get(name).getName() +"\"" + lineEnd);
			dos.writeBytes(lineEnd);		

			// create a buffer of maximum size
			
			FileInputStream fileInputStream = new FileInputStream( files.get(name) );

			int bytesAvailable = fileInputStream.available();
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];

			// read file and write it into form...

			int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necessary after file data...

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
			fileInputStream.close();
		}
		
		dos.flush();
		dos.close();

		BufferedReader inStream = new BufferedReader(new InputStreamReader( conn.getInputStream()));
		String str, returnStr = "";
		while (( str = inStream.readLine()) != null) {
			returnStr += str;
		}
		inStream.close();
		return returnStr;
	}

	public static void main(String[] args)	throws Exception {
		String urlString = "http://www.algorithm-forge.com/test/uploader.php";
		Hashtable<String,String> table = new Hashtable<String,String>();
		Hashtable<String,File> files = new Hashtable<String,File>();
		files.put("uploadedfile1", new File("/home/kornel/Desktop/Bildschirmfoto.png.zip"));
		files.put("uploadedfile2", new File("/home/kornel/Desktop/quaqua-5.2.1.nested.zip"));
		String result = (new HTTPPoster()).post(urlString, table, files);
		System.out.println(result);
	}
}
