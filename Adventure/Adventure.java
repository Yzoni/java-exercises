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
	}

	// Add your own private methods and instance variables here
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
		rooms = new ArrayList<String>();
		AdvRoom room = AdvRoom.readRoom(roomsreader);
		//while (room != null) {
		//	room = AdvRoom.readRoom(roomsreader);
		//}
	}
 
	private void initObjects() {
		objects = new ArrayList<String>();
		//AdvRoom object = AdvRoom.readObject(objectsreader);
		//while (object != null) {
		//	object = AdvRoom.readObject(objectsreader);
		//}
	}

	private void initSynonyms() {

	}

	private void getInput() {
		String input = readLine("> ");

	}

	private void doInput(String input) {
		if (input.equals("INVENTORY")) {
			doQuit();
		} else if (input.equals("TAKE")) {
			//doTake();
		} else if (input.equals("DROP")) {
			//doDrop();
		} else if (input.equals("HELP")) {
			doQuit();
		} else if (input.equals("QUIT")) {
			doQuit();
		} else {
			displayIncorrectInput();
		}
	}

	private void doTurn() {

	}

	private void doQuit() {

	}

	private void displayDescription() {

	}

	private void displayInventory() {

	}

	private void displayIncorrectInput() {

	}

	ArrayList<String> rooms;
	ArrayList<String> objects;

	private String adventureread;

	private BufferedReader roomsreader;
	private BufferedReader objectsreader;
	private BufferedReader synonymsreader;
}
