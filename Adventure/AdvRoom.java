/*
 * File: AdvRoom.java
 * ------------------
 * This file defines a class that models a single room in the
 * Adventure game.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

/**
 * This class defines a single room in the Adventure game.  A room
 * is characterized by the following properties:
 *
 * <ul>
 * <li>A room number, which must be greater than zero
 * <li>Its name, which is a one-line string identifying the room
 * <li>Its description, which is a line array describing the room
 * <li>A list of objects contained in the room
 * <li>A flag indicating whether the room has been visited
 * <li>A motion table specifying the exits and where they lead
 * </ul>
 *
 * The external format of the room file is described in the
 * assignment handout.
 */
public class AdvRoom extends Adventure {

	/**
	 * Returns the room number.
	 *
	 * @return The room number
	 */
	public int getRoomNumber() {
		return currentroom;
	}

	/**
	 * Returns the room name, which is its one-line description.
	 *
	 * @return The room name
	 */
	public String getName() {
		return roomname;
	}


	/**
	 * Adds an object to the list of objects in the room.
	 *
	 * @param obj The <code>AdvObject</code> to be added
	 */
	public void addObject(AdvObject obj) {
		objectlist.add(obj);
	}

	/**
	 * Removes an object from the list of objects in the room.
	 *
	 * @param obj The <code>AdvObject</code> to be removed
	 */
	public void removeObject(AdvObject obj) {
		objectlist.remove(obj);
	}

	/**
	 * Checks whether the specified object is in the room.
	 *
	 * @param obj The <code>AdvObject</code> being tested
	 * @return <code>true</code> if the object is in the room
	 */
	public boolean containsObject(AdvObject obj) {
		if (objectlist.contains(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the number of objects in the room.
	 *
	 * @return The number of objects in the room
	 */
	public int getObjectCount() {
		return objectlist.size();
	}

	/**
	 * Returns the specified element from the list of objects in the room.
	 *
	 * @return The <code>AdvObject</code> at the specified index position
	 */
	public AdvObject getObject(int index) {
		return objectlist.get(index);
	}

	/**
	 * Returns a string array giving the long description of the room.
	 *
	 * @return A string array giving the long description of the room
	 */
	public String[] getDescription() {
		return descriptionlist;
	}

	/**
	 * Sets a flag indicating whether this room has been visited.
	 * Calling <code>setVisited(true)</code> means that the room has
	 * been visited; calling <code>setVisited(false)</code> restores
	 * its initial unvisited state.
	 *
	 * @param flag The new state of the "visited" flag
	 */
	public void setVisited(boolean flag) {
		roomflag = flag;
	}

	/**
	 * Returns <code>true</code> if the room has previously been visited.
	 *
	 * @return <code>true</code> if the room has been visited
	 */
	public boolean hasBeenVisited() {
		if (roomflag == null) return false;
		return roomflag;
	}

	/**
	 * Returns the motion table associated with this room, which is an
	 * array of directions, room numbers, and key objects stored
	 * in an <code>AdvMotionTableEntry</code>.
	 *
	 * @return The array of motion table entries associated with this room
	 */
	public AdvMotionTableEntry[] getMotionTable() {
		return motiontable;
	}

	/**
	 * Creates a new room by reading its data from the specified
	 * reader.  If no data is left in the reader, this method returns
	 * <code>null</code> instead of an <code>AdvRoom</code> value.
	 * Note that this is a static method, which means that you need
	 * to call
	 *
	 *<pre><code>
	 *     AdvRoom.readRoom(rd)
	 *</code></pre>
	 *
	 * @param rd The reader from which the room data is read 
	 */
	public static AdvRoom readRoom(BufferedReader rd) {
		try {
			AdvRoom room = new AdvRoom();

			String line = rd.readLine();
			// Return null if end of file
			if(line == null) {
				return null;
			}

			// Room number
			room.currentroom = Integer.parseInt(line);

			// Room name
			line = rd.readLine();
			room.roomname = line;

			// Room description
			ArrayList<String> roomdescriptionarrlist = new ArrayList<String>();
			while (true) {
				// Read a line of the description and put it in an array list
				line = rd.readLine();
				// If the separator is reached then break
				if (line.equals("-----")) break;
				roomdescriptionarrlist.add(line);
			}
			// Convert the array list to a normal array
			room.descriptionlist = new String[roomdescriptionarrlist.size()];
			for (int i = 0; i < roomdescriptionarrlist.size(); i++) {
				room.descriptionlist[i] = (roomdescriptionarrlist.get(i));
			}


			// Read directions
			while (true) {
				// Read a direction line
				line = rd.readLine();
				// If no more directions for this room break loop;
				if(line == null || line.length() == 0 ) {
					break;
				}
				// Space between direction and next room number
				int space = line.indexOf(" ");
				String dir = line.substring(0, space).trim().toUpperCase();
				int nextroom;
				// Get the index of the slash
				int slash = line.indexOf("/");
				// If there is no slash in the string set null for the key
				if(slash == -1){
					nextroom = Integer.parseInt(line.substring(space).trim());
					room.motiontablearrlist.add(new AdvMotionTableEntry(dir, nextroom, null));
				} else {
					nextroom = Integer.parseInt(line.substring(space, slash).trim());
					String key = line.substring(slash + 1).trim();
					room.motiontablearrlist.add(new AdvMotionTableEntry(dir, nextroom, key));
				}
			}

			// Add all the motiontable entries to the motion table of a room
			room.motiontable = new AdvMotionTableEntry[room.motiontablearrlist.size()];
			for (int i = 0; i < room.motiontablearrlist.size(); i++) {
				room.motiontable[i] = (room.motiontablearrlist.get(i));
			}
			
			return room;

		} catch (IOException e) {
			throw new ErrorException(e);
		}
	}

	private int currentroom;

	private String roomname;

	private Boolean roomflag;

	private ArrayList<AdvMotionTableEntry> motiontablearrlist = new ArrayList<AdvMotionTableEntry>();

	private ArrayList<AdvObject> objectlist = new ArrayList<AdvObject>();

	private AdvMotionTableEntry[] motiontable;
	private String[] descriptionlist;
}
