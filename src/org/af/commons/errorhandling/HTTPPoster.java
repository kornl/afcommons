package org.af.commons.errorhandling;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Uploads String values and files via HTTP. 
 */
public class HTTPPoster {

	public static final int maxBufferSize = 1*1024*1024;
	
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";
	
	/**
	 * Uploads String values and files via HTTP.
	 * @param urlString the URL to that we want to send a POST
	 * @param table will be send as form-data
	 * @param files will be attached
	 * @return the response of the server
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

		for (String name : Collections.list(table.keys())) {
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\""+name+"\";"+lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(table.get(name) + lineEnd);					
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		}
		
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
		table.put("input1", "Test formular entry");
		Hashtable<String,File> files = new Hashtable<String,File>();
		files.put("uploadedfile1", new File("/home/kornel/field.R"));
		//files.put("uploadedfile2", new File("/home/kornel/Desktop/quaqua-5.2.1.nested.zip"));
		String result = (new HTTPPoster()).post(urlString, table, files);
		System.out.println(result);
	}
}
