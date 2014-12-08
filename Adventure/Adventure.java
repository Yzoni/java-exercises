/*
 * File: Adventure.java
 * Names: <fill in>
 * Section leader who is grading the assignment: <fill in>
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

public class Adventure extends AdventureMagicSuperclass {

	/**
	 * Runs the Adventure program.
	 */
	public void run() {
		super.run();  // Replace with your code
		
		/*
		initializeGame();
		while(true) {
			doTurn();
		}
		*/
	}

	private void initializeGame() {
		getAdventure();
		initRooms();
		initObjects();
		initSynonyms();
	}

	private void getAdventure() {
		println("Adventure name choices; Crowther or Small");
		String adventurename = readLine("Give adventure name: " );
		if (adventurename .equals("Crowther")) {
			adventureread = "Crowther";
		} else if (adventurename .equals("Small")) {
			adventureread = "Small";
		} else {
			println("Give a proper adventure name!");
		}
		try { 
			roomsreader = new BufferedReader(new FileReader(adventureread + "Rooms.txt"));
			objectsreader = new BufferedReader(new FileReader(adventureread + "Objects.txt"));
			synonymsreader = new BufferedReader(new FileReader(adventureread + "Synonyms.txt"));
		} catch (IOException e) {}
	}

	private void initRooms() {
		rooms = new HashMap<Integer, AdvRoom>();
		while (true) {
			AdvRoom room = AdvRoom.readRoom(roomsreader);
			if(room == null) break;
			int roomnumber = room.getRoomNumber();
			rooms.put(new Integer(roomnumber), room);
		}
	}
 
	private void initObjects() {
		objects = new HashMap<String, AdvObject>();
		while (true) {
			AdvObject object = AdvObject.readObject(objectsreader);
			if(object == null) break;
			String objectname =  object.getName();
			objects.put(objectname, object);
		}
	}

	private void initSynonyms() {
		synonyms = new HashMap<String, String>();
		while (true) {
		}
	}



	private void doTurn() {

	}

	private int doMove(String direction) {
		AdvMotionTableEntry[] motiontable = room.getMotionTable();
		for(int i = 0; i < motiontable.length; i++){
			AdvMotionTableEntry motionentry = motiontable[i];
			if (motionentry.getDirection().equals(direction)) {
				if (motionentry.getKeyName() == null) {
					return motionentry.getDestinationRoom();
				} else {
					if(inventory.contains(objects.get(motionentry.getKeyName()))){
						return motionentry.getDestinationRoom();
					} else {
						println("Key not in inventory");
					}					
				}
			} else {
				println("Invalid direction");
			}
		}
		return -1;
	}

	// Exectute input with 
	private void doInput(String[] input) {
		if (motion.contains(input[0])) {
			doInput(input[0]);
		} else if (input[0].equals("INVENTORY")) {
			displayInventory();
		} else if (input[0].equals("TAKE")) {
			doTake(input[1]);
		} else if (input[0].equals("DROP")) {
			doDrop(input[1]);
		} else if (input[0].equals("HELP")) {
			displayHelp();
		} else if (input[0].equals("QUIT")) {
			doQuit();
		} else {
			print("Command not found!");
		}
	}

	// Get input and return input command with arguments as an array
	// Also check if argument exists
	private String[] getInput() {
		String line = readLine("> ");
		line = line.toUpperCase();
		String[] input = line.split(" ");
		return input;
	}



	private void doTake(String argument) {
		object = objects.get(argument);
		if (object == null) {
			println("Object does not exist in this world!");
		} else {
			// check if current room contains object transfer object to inventory
			if (room.containsObject(object)) {
				room.removeObject(object);
				inventory.add(object);
				println("Took: " + object);				
			} else {
				println("Object not found.");
			}
		}
	}

	private void doDrop(String argument) {
		object = objects.get(argument);
		if (object == null) {
			println("Object does not exist in this world!");
		}
		if (inventory.contains(object)) {
			room.addObject(object);
			inventory.remove(object);
			println("Dropped: " + object);
		} else {
			println("Object not in inventory!");
		}
	}

	private void doQuit() {
		System.exit(0);
	}

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
		if (room.hasBeenVisited()) {
			println(room.getName());
		} else {
			String[] description = room.getDescription();
			for(int i = 0; i < description.length; i++){
				println(description[i]);
			}			
		}
		// Print objects in room
	}

	private void displayInventory() {
		println("Your inventory: ");
		for(int i = 0; i < inventory.size(); i++){
			println(inventory.get(i).getDescription());
		}
	}

	private String[] input;

	private AdvRoom room;
	private AdvObject object;

	private String adventureread;

	private BufferedReader roomsreader;
	private BufferedReader objectsreader;
	private BufferedReader synonymsreader;

	private HashMap<Integer, AdvRoom> rooms;
	private HashMap<String, AdvObject> objects;
	private HashMap<String, String> synonyms;

	private ArrayList<String> motion = new ArrayList<String>();

	private ArrayList<AdvObject> inventory = new ArrayList<AdvObject>();
}
