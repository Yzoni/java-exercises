/*
 * File: Adventure.java
 * Names: Y. de Boer 10786015
 * -------------------------------------------------------
 * This program plays the Adventure game from Assignment #6.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

/**
 * This class is the main program class for the Adventure game.
 * In the final version, <code>Adventure</code> should extend
 * <code>ConsoleProgram</code> directly.
 */

public class Adventure extends ConsoleProgram {

	/**
	 * Runs the Adventure program.
	 */
	public void run() {
		initializeGame();
		while(currentroomnumber != 0) {
			doTurn();
		}
		readLine("_Press enter to exit_");
		doQuit();
	}


	/*
	 * INITIALIZATION
	 */

	private void initializeGame() {
		currentroomnumber = 1;
		getAdventure();
		initRooms();
		if (tiny == false) {
			initObjects();
			initSynonyms();
		}
		initPossibleMoves();
		initInventory();
	}

	private void getAdventure() {
		println("Adventure name choices; Crowther, Small or Tiny");
		String adventurename = readLine("Enter adventure name (case sensitive): " );
		String adventureread = "";
		if (adventurename .equals("Crowther")) {
			adventureread = "Crowther";
		} else if (adventurename .equals("Small")) {
			adventureread = "Small";
		} else if (adventurename .equals("Tiny")) {
			adventureread = "Tiny";
			tiny = true;
		} else {
			println("Give a proper adventure name!");
		}
		try {
			roomsreader = new BufferedReader(new FileReader(adventureread + "Rooms.txt"));
			// Tinyy does not have an objects and synonyms file
			if (tiny == false) {
				objectsreader = new BufferedReader(new FileReader(adventureread + "Objects.txt"));
				synonymsreader = new BufferedReader(new FileReader(adventureread + "Synonyms.txt"));
			}
		} catch (IOException e) {
			getAdventure();
		}
	}

	// Read all rooms and add them to an arraylist
	private void initRooms() {
		roomslist = new ArrayList<AdvRoom>();
		while (true) {
			AdvRoom room = AdvRoom.readRoom(roomsreader);
			if (room == null) break;
			int roomnumber = room.getRoomNumber();
			roomslist.add(room);
		}
		try {
			roomsreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	// Read all objects and add them to an arraylist
	private void initObjects() {
		objectslist = new ArrayList<AdvObject>();
		while (true) {
			AdvObject object = AdvObject.readObject(objectsreader);
			if(object == null) break;
			objectslist.add(object);

			// Get objects in room and add the objects from the objects file to them
			int initialobjectlocation = object.getInitialLocation();
			for (int i = 0; i < roomslist.size(); i++) {
				AdvRoom objectroom = roomslist.get(i);
				if (objectroom.getRoomNumber() == initialobjectlocation) {
					objectroom.addObject(object);
				}
			}
		}
		try { 
			roomsreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Read synonyms and add them to a hashmap
	private void initSynonyms() {
		synonyms = new HashMap<String, String>();
		try {
			while (true) {
				String line = synonymsreader.readLine();
				if(line == null) break;
				String[] splitline = line.split("=");
				synonyms.put(splitline[0], splitline[1]);
			}
			synonymsreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initInventory() {
		inventory = new ArrayList<AdvObject>();
	}

	private void initPossibleMoves() {
		motion = new ArrayList<String>();
			motion.add("UP");
			motion.add("DOWN");
			motion.add("NORTH");
			motion.add("SOUTH");
			motion.add("WEST");
			motion.add("EAST");
			motion.add("IN");
			motion.add("OUT");
	}


	/*
	 * GAME PLAY
	 */

	private void doTurn() {
		// Get the current room object from the current roomnumber
		for (int i = 0; i < roomslist.size(); i++) {
			room = roomslist.get(i);
			if (room.getRoomNumber() == currentroomnumber) break;
		}
		// Do not display description if room has been visited befores
		if (room.hasBeenVisited() == false) {
			displayDescription();
		}
		displayObjects();
		String[] input = getInput();
		// Check if input has a synonym if so, make input the synonym
		if (tiny == false) {
			if (synonyms.containsKey(input[0]) == true) {
				input[0] = synonyms.get(input[0]);
			}
		}
		doInput(input);
		room.setVisited(true);
	}

	// Get input and return input command with arguments as an array
	private String[] getInput() {
		String line = readLine("> ");
		line = line.toUpperCase();
		String[] input = line.split(" ");
		return input;
	}

	// Exectute input with 
	private void doInput(String[] input) {
		// For move input, check if legal direction
		if (motion.contains(input[0])) {
			doMove(input[0]);
		} else if (input[0].equals("INVENTORY")) {
			displayInventory();
		} else if (input[0].equals("TAKE")) {
			try {
				doTake(input[1]);
			} catch (Exception e) {
				println("No object specified");
			}
		} else if (input[0].equals("DROP")) {
			try {
				doDrop(input[1]);
			} catch (Exception e) {
				println("No object specified");
			}
		} else if (input[0].equals("LOOK")) {
			displayDescription();
		} else if (input[0].equals("HELP")) {
			displayHelp();
		} else if (input[0].equals("QUIT")) {
			doQuit();
		} else {
			println("Command not found!");
		}
	}

	private void doMove(String direction) {
		// Get the motion table from current room
		AdvMotionTableEntry[] motiontable = room.getMotionTable();
		int oldroom = currentroomnumber;
		for(int i = 0; i < motiontable.length; i++){
			AdvMotionTableEntry motionentry = motiontable[i];
			// Check if motion entry is user input direction
			if (motionentry.getDirection().equals(direction)) {
				// If nextroom does not require a key
				if (motionentry.getKeyName() == null) {
					currentroomnumber = motionentry.getDestinationRoom();
					break;
				// If nextroom requires a key
				} else {
					String neededkeyname = motionentry.getKeyName();
					// Check if key is in inventory
					for (int j = 0; j < inventory.size(); j++) {
						AdvObject object = inventory.get(j);
						String inventorykeyname = object.getName();
						if(neededkeyname .equals(inventorykeyname)) {
							currentroomnumber = motionentry.getDestinationRoom();
							break;
						} 
					}
					println(neededkeyname + " not in inventory");
					break;
				}
			}
		}
		// If no move has been done, assume it was not possible to move to input direction
		if (oldroom == currentroomnumber) {
			println("Can't move in that direction!");
		}
	}

	private void doTake(String argument) {
		AdvObject object = getObjectByName(argument);
		// check if current room contains object transfer object to inventory arraylist
		if (room.containsObject(object)) {
			room.removeObject(object);
			inventory.add(object);
			println("Took: " + object.getDescription());				
		} else {
			println("Object not found.");
		}
	}

	private void doDrop(String argument) {
		AdvObject object = getObjectByName(argument);
		// If object is in the inventory, transfer it to the room object
		if (inventory.contains(object)) {
			room.addObject(object);
			inventory.remove(object);
			println("Dropped: " + object.getDescription());
		} else {
			println("Object not in inventory!");
		}
	}

	private AdvObject getObjectByName(String objectname) {
		for (int i = 0; i < objectslist.size(); i++) {
			AdvObject object = objectslist.get(i);
			if (objectname .equals(object.getName())) {
				return object;
			}
		}
		// Return null if object not found
		return null;
	}	

	private void doQuit() {
		System.exit(0);
	}


	/*
	 * STANDALONE MESSAGES
	 */

	private void displayHelp() {
		println("Somewhere nearby is Colossal Cave, where others have found fortunes in");
		println("treasure and gold, though it is rumored that some who enter are never");
		println("seen again.  Magic is said to work in the cave.  I will be your eyes");
		println("all of the English language, but I do a pretty good job.");
		println("");
		println("It's important to remember that cave passages turn a lot, and that");
		println("leaving a room to the north does not guarantee entering the next from");
		println("the south, although it often works out that way.  You'd best make yourself");
		println("a map as you go along.");
		println("");
		println("Much of my vocabulary describes places and is used to move you there.");
		println("To move, try words like IN, OUT, EAST, WEST, NORTH, SOUTH, UP, or DOWN.");
		println("I also know about a number of objects hidden within the cave which you");
		println("can TAKE or DROP.  To see what objects you're carrying, say INVENTORY.");
		println("To reprint the detailed description of where you are, say LOOK.  If you");
		println("want to end your adventure, say QUIT.");
		println("");
	}

	private void displayDescription() {
		String[] description = room.getDescription();
		for(int i = 0; i < description.length; i++){
			println(description[i]);
		}
	}

	private void displayObjects() {
		if (room.getObjectCount() > 0) {
			println("Objects laying around: ");
		}
		for(int i = 0; i < room.getObjectCount(); i++){
				AdvObject thisobject = room.getObject(i);
				print(" - " + thisobject.getDescription());
				println("");
		}		
	}

	private void displayInventory() {
		println("Your inventory: ");
		for(int i = 0; i < inventory.size(); i++){
			println(" - " + inventory.get(i).getDescription());
		}
	}

	// Keeps track of the current room in
	private int currentroomnumber;

	// Arralist with valid direction commands
	private ArrayList<String> motion;

	private AdvRoom room;

	private Boolean tiny = false;

	private BufferedReader roomsreader;
	private BufferedReader objectsreader;
	private BufferedReader synonymsreader;

	private ArrayList<AdvRoom> roomslist;
	private ArrayList<AdvObject> objectslist;
	private HashMap<String, String> synonyms;

	private ArrayList<AdvObject> inventory;
}
