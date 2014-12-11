/*
 * File: AdvObject.java
 * Name: Y. de Boer 10786015
 * --------------------
 * This file defines a class that models an object in the
 * Adventure game.
 */

import acm.util.*;
import java.io.*;

/**
 * This class defines an object in the Adventure game.  An object is
 * characterized by the following properties:
 *
 * <ul>
 * <li>Its name, which is the noun used to refer to the object
 * <li>Its description, which is a string giving a short description
 * <li>The room number in which the object initially lives
 * </ul>
 *
 */
public class AdvObject extends Adventure {

	// Add your own instance variables here

	/**
	 * Returns the object name, which is the word used to refer to it.
	 *
	 * @return The name of the object
	 */
	public String getName() {
		return objectname;
	}

	/**
	 * Returns the one-line description of the object.  This description
	 * should start with an article, as in "a set of keys" or "an emerald
	 * the size of a plover's egg."
	 *
	 * @return The description of the object
	 */
	public String getDescription() {
		return objectdescription;
	}

	/**
	 * Returns the initial location of the object.
	 *
	 * @return The room number in which the object initially resides
	 */
	public int getInitialLocation() {
		return objectinitiallocation;
	}

	/**
	 * Creates a new object by reading its data from the specified
	 * reader.  If no data is left in the reader, this method returns
	 * <code>null</code> instead of an <code>AdvObject</code> value.
	 * Note that this is a static method, which means that you need
	 * to call
	 *
	 *<pre><code>
	 *     AdvObject.readObject(rd)
	 *</code></pre>
	 *
	 * @param rd The reader from which the object data is read 
	 */
	public static AdvObject readObject(BufferedReader rd) {
		try {
			AdvObject object = new AdvObject();
			
			String line = rd.readLine();
			// Return null if end of file
			if(line == null) {
				return null;
			}

			// Incase of empty line read the next line
			if(line.length() == 0) {
				line = rd.readLine();
			}

			// Object name
			object.objectname = line;
			 
			// Object description
			line = rd.readLine();
			object.objectdescription = line;
			
			// Object Initial Location
			line = rd.readLine();
			object.objectinitiallocation = Integer.parseInt(line);
			
			return object;
		} catch (IOException e) {
			 throw new ErrorException(e);
		}
	}

	private String objectname;
	private String objectdescription;
	private int objectinitiallocation;
}
