/*
  PublicPixelMatrix library for Processing
  
  (c) Patrick Borgeat 2009
  
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
*/

package de.cappelnord.ppm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.net.*;

/**
 * PublicPixelMatrix acts as a factory class for {@link PPMatrix} or {@link PPMatrixList} objects.
 * It provides only static methods for loading matrices out of files and URLs. You can also load
 * matrices directly from the PublicPixelMatrix site. It also acts as storage for
 * some global variables and provides some global methods. At this time there is only support for one format,
 * the "raw" format. As this is quite inefficient there may be other formats in the future.
 * 
 * @author Patrick Borgeat
 * 
 */
public class PublicPixelMatrix {
	
	private static SimpleDateFormat dateFormat;
	private static String dateFormatPattern ="dd.MM.yyyy HH:mm:ss";
	private final static String currentURL = "http://matrix.cappel-nord.de/data/current";
	
	public final static int DIM = 17;
	public final static String VERSION = "0.1.0";

	/**
	 * Return the version of the library.
	 * 
	 * @return String
	 */
	
	public static String version() {
		return VERSION;
	}
	
	
	/**
	 * Central method to format {@link java.util.Date} objects into Strings. This hides {@link java.util.Date} objects from the Processing user.
	 * 
	 * @param date The {@link java.util.Date} object to format as {@link String}
	 * @return Formatted date as {@link String}
	 */
	
	public static String formatDate(Date date)
	{		
		if(dateFormat == null)
			dateFormat = new SimpleDateFormat(dateFormatPattern);
		
		return dateFormat.format(date);
	}
	
	/**
	 * Sets a date format pattern. For more information look for the class {@link java.text.SimpleDateFormat} in the Java API.
	 * 
	 * @param s A new date format pattern. (default: "dd.MM.yyyy HH:mm:ss")
	 */
	
	public static void setDateFormatPattern(String s)
	{
		dateFormatPattern = s;
		dateFormat = new SimpleDateFormat(dateFormatPattern);
	}
	
	@SuppressWarnings("unused")
	private static PPMatrix createPPMatrixFromRawString(String s)
	{
		return createPPMatrixFromRawStringArray(s.split("\n"));
	}
	
	private static PPMatrix createPPMatrixFromRawStringArray(String[] s) 
	{
		if(s.length != DIM + 1)
		{
			throw new IndexOutOfBoundsException("Size of String Array does not match PPMatrix Dimension");
		}
		
		boolean[][] matrix = new boolean[DIM][DIM];
		long timestamp = Long.parseLong(s[0]);
		
		String currentLine = null;
		char currentChar;
		
		for(int i = 0; i < DIM; i++)
		{
			currentLine = s[i+1];
			if(currentLine.length() != DIM)
			{
				throw new IndexOutOfBoundsException("Size of String Lines does not match PPMatrix Dimension");
			}
			
			for(int e = 0; e < DIM; e++)
			{
				currentChar = currentLine.charAt(e);
				if (currentChar == '0')
					matrix[i][e] = false;
				else if (currentChar == '1')
					matrix[i][e] = true;
				else
					throw new RuntimeException("Matrix contains other characters than 0 and 1: maybe file is corrupted.");
			}
		}
		
		return new PPMatrix(timestamp,matrix);
	}
	
	// TODO: Resolve Files as Processing does it (looks in data folder, etc)

	/**
	 * Reads a {@link PPMatrix} in raw format from a local file.
	 * 
	 * @param s Location of the file containing a matrix in raw format
	 * @return {@link PPMatrix}
	 */
	
	public static PPMatrix readPPMatrixFromRawFile(String s)
	{
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(s));
			PPMatrix ret = readPPMatrixFromBufferedReader(in);
			in.close();
			return ret;
		}
		catch(Exception e)
		{
			throw new RuntimeException("File " + s+ " not found!");
		}
	}
	
	/**
	 * Reads a {@link PPMatrixList} in raw format from a local file.
	 * 
	 * @param s Location of the file containing one or more matrices in raw format
	 * @return {@link PPMatrixList}
	 */
	
	public static PPMatrixList readPPMatrixListFromRawFile(String s)
	{
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(s));
			PPMatrixList ret = readPPMatrixListFromBufferedReader(in);
			in.close();
			return ret;
		}
		catch(Exception e)
		{
			throw new RuntimeException("File " + s + " not found!");
		}
	}
		
	private static PPMatrixList readPPMatrixListFromBufferedReader(BufferedReader in)
	{
		try {
			PPMatrixList ret = new PPMatrixList();
			String s;
			String[] arr = new String[DIM + 1];
			int i = 0;
		
			while((s = in.readLine()) != null)
			{
				arr[i] = s;
				i++;
				if(i >= DIM  + 1)
				{
					i = 0;
					ret.add(createPPMatrixFromRawStringArray(arr));
				}
			}
			
			return ret;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Some Error ...");
			// TODO: sinnvoll. 
		}
	}
		
	private static PPMatrix readPPMatrixFromBufferedReader(BufferedReader in)
	{
		
		String[] s = new String[DIM + 1];
		
		for(int i = 0; i < DIM + 1; i++)
		{
			try {
				s[i] = in.readLine();
			} catch (IOException e) {
				throw new RuntimeException("File needs to have at least " + (DIM + 1) + " lines.");
			}
		}

		return createPPMatrixFromRawStringArray(s);
	}
	
	private static BufferedReader createBufferedReaderFromStringURL(String s)
	{
		  try {
			    URL                url; 
			    URLConnection      urlConn; 

			    url = new URL(s);

			    urlConn = url.openConnection(); 
			    urlConn.setDoInput(true); 
			    urlConn.setUseCaches(false);

			    return new BufferedReader( new InputStreamReader(urlConn.getInputStream()));
			    
			    } 
		  		catch (MalformedURLException mue) { throw new RuntimeException("Malformed URL");} 
		  		catch (IOException ioe) { throw new RuntimeException("Could not connect to Server ...");}
	}
	
	/**
	 * Reads the latest state of the PublicPixelMatrix from the web server.
	 * 
	 * @return Latest {@link PPMatrix}
	 */
	
	public static PPMatrix readCurrentPPMatrix()
	{
	    return readPPMatrixFromURL(currentURL); 
	}
	
	/**
	 * Reads a {@link PPMatrix} in raw format from an URL.
	 * 
	 * @param s URL of the file containing a matrix in raw format
	 * @return {@link PPMatrix}
	 */
	
	public static PPMatrix readPPMatrixFromURL(String s)
	{
	    BufferedReader in  = createBufferedReaderFromStringURL(s); 
	    PPMatrix ret = readPPMatrixFromBufferedReader(in);
	    try {in.close();} catch(Exception e){};
	    return ret;
	}
	
	/**
	 * Reads a {@link PPMatrixList} in raw format from an URL.
	 * 
	 * @param s URL of the file containing one or more matrices in raw format
	 * @return {@link PPMatrixList}
	 */
	
	public static PPMatrixList readPPMatrixListFromURL(String s)
	{
	    BufferedReader in  = createBufferedReaderFromStringURL(s); 
	    PPMatrixList ret = readPPMatrixListFromBufferedReader(in);
	    try {in.close();} catch(Exception e){};
	    return ret;
	}

}
