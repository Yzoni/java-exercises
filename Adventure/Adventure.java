/*
 * File: Adventure.java
 * Name: Y. de Boer 10786015
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

	// Open specified game files based on user input
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
		setCurrentRoomObject();
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
				// Keep reading lines untill end of file
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

	// Get the current room object from the current roomnumber
	private void setCurrentRoomObject() {
		for (int i = 0; i < roomslist.size(); i++) {
			room = roomslist.get(i);
			if (room.getRoomNumber() == currentroomnumber) break;
		}
	}

	private void doTurn() {
		displayName();
		// Do not display description if room has been visited befores
		if (room.hasBeenVisited() == false) {
			displayDescription();
		}
		displayObjects();
		room.setVisited(true);
		String[] input = getInput();
		// Check if input has a synonym if so, make input the synonym
		if (tiny == false) {
			if (synonyms.containsKey(input[0]) == true) {
				input[0] = synonyms.get(input[0]);
			}
		}
		doInput(input);
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
			doForced();
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

	// Checks if current room is forced, if so, execute force
	// Move to forced room with item if item is in inventory
	private void doForced() {
		AdvMotionTableEntry[] motiontable = room.getMotionTable();
		for (int i = 0; i < motiontable.length; i++) {
			AdvMotionTableEntry motionentry = motiontable[i];
			String checkforced = motionentry.getDirection();
			if (checkforced .equals("FORCED")) {
					doMove("FORCED");	
			}
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
				// If room is forced
				if (direction .equals("FORCED")) {
					// If two forced options are available where one needs a key, go into that one 
					// when key object is in inventory
					Boolean keyrequired = checkKeyRequired(motionentry);
					Boolean keyininventory = checkItemInInventory(motionentry.getKeyName());
					if (keyrequired == true) {
						if (keyininventory == true) {
							displayName();
							displayDescription();	
							currentroomnumber = motionentry.getDestinationRoom();
							break;
						} else {
							// Execute the second forced when key is not in inventory
							motionentry = motiontable[i + 1];
							displayName();
							displayDescription();
							currentroomnumber = motionentry.getDestinationRoom();
							break;							
						}
					// In case no key is required for the forced room	
					} else {
						displayName();
						displayDescription();
						currentroomnumber = motionentry.getDestinationRoom();						
					}
				// If room is not forced
				} else {
					Boolean keyrequired = checkKeyRequired(motionentry);
					if (keyrequired == true) {
						String neededkeyname = motionentry.getKeyName();
						// Check if key is in inventory
						if (checkItemInInventory(neededkeyname) == true) {
						currentroomnumber = motionentry.getDestinationRoom();
						} else {
						println("Key object not found!");
						}
					// No key required		
					} else {
						currentroomnumber = motionentry.getDestinationRoom();
					}
				}
			}
		}
		// If no move has been done, assume it was not possible to move to input direction
		if (oldroom == currentroomnumber) {
			println("Can't move in that direction!");
		}
		setCurrentRoomObject();
	}

	// Return true if a room requires a key
	private Boolean checkKeyRequired(AdvMotionTableEntry motionentry) {
		// If nextroom does not require a key
		if (motionentry.getKeyName() == null) return false;
		// If nextroom requires a key
		return true;
	}

	// Return true if object is in the inventory
	private Boolean checkItemInInventory(String object) {
		for (int j = 0; j < inventory.size(); j++) {
			AdvObject invobject = inventory.get(j);
			String inventorykeyname = invobject.getName();
			if (object .equals(inventorykeyname)) {
				return true;
			}
		}
		return false;
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

	// Iterate trough arraylist of objects untill object is found by name
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

	private void displayName() {
		String name = room.getName();
		println(name);
	}

	private void displayDescription() {
		String[] description = room.getDescription();
		for(int i = 0; i < description.length; i++) {
			println(description[i]);
		}
	}

	private void displayObjects() {
		if (room.getObjectCount() > 0) {
			println("Objects laying around: ");
		}
		// Iterate trough room objects and print every item on a new line
		for (int i = 0; i < room.getObjectCount(); i++) {
				AdvObject thisobject = room.getObject(i);
				print(" - " + thisobject.getDescription());
				println("");
		}		
	}

	private void displayInventory() {
		println("Your inventory: ");
		if (inventory.size() == 0) {
			println(" - Inventory is empty");
		} else {
			// Iterate trough inventory and print every item on a new line
			for (int i = 0; i < inventory.size(); i++) {
				println(" - " + inventory.get(i).getDescription());
			}
		}
	}

	// Keeps track of the current room in
	private int currentroomnumber;

	// Arralist with valid direction commands
	private ArrayList<String> motion;

	// Current room object
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
