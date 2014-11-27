/*
 * File: Hangman.java
 * Name Y. de Boer 10786015
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import java.io.*;
import java.util.*;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 700;

	private static final int AMOUNT_GUESSES = 8;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		setupLexicon();
	}

	public void run() {
		canvas.reset();
		setupGame();
		mainGameLoop();
	}

	private void setupGame() {
		setupGuessCounter();
		setupWelcomeMessage();
		setupWrongCharacters();
	}

	private void mainGameLoop() {
		// Choose a word from the lexicon
		getWordLexicon();
		// Keep asking for characters untill there are no guesses left
		while (wrongguessescount < AMOUNT_GUESSES) {
			displayCurrentWord();
			String precharacterstring = readLine("Your Guess: ");
			// Transform string to character tu use isLetter funtion
			try {precharacter = precharacterstring.charAt(0);
			// Catch excepetion if input is empty
			} catch (StringIndexOutOfBoundsException e) {}
			// Check if input is 1 char and is a letter
			if (precharacterstring.length() == 1 && Character.isLetter(precharacter)) {
				// Transform string to all uppercase
				character = precharacterstring.toUpperCase();
				// Check if the character is in the word
				if (checkCharacter()) {
					correctCharacter();
				} else {
					wrongCharacter();
				}
			} else {
				println("Input needs to be one letter! Try again.");
			}
			if (checkWordFound()) {
				// Display the completed word
				displayCurrentWord();
				displayGameWin();
				// Stop the game when the whole word is found
				break;
			}
		}
		if (checkWordFound() == false) displayGameLost();
		String stub = readLine("Press ENTER to play again");
		// Restart the game
		run();
	}

	/*
	 * SETUP METHODS
	 */

	private void setupLexicon() {
		lexicon = new HangmanLexicon();
	}	

	private void setupWelcomeMessage() {
		println("WELCOME TO HANGMAN");
	}

	private void setupGuessCounter() {
		wrongguessescount = 0;
	}

	// Setup an array to keep track of wrong guessed characters
	private void setupWrongCharacters() {
		wrongguesses = new ArrayList<String>();
	}

	/*
	 * GAMEPLAY METHODS
	 */

	// Get a new word from the lexicon
	private void getWordLexicon() {
		// Generate random integer, which reperesents the line from which a lexicon word is chosen
		RandomGenerator rgen = RandomGenerator.getInstance();
		int randomindex = rgen.nextInt(0, lexicon.getWordCount());
		word = lexicon.getWord(randomindex);
		// Setup an array where the found characters by the user will be stored
		foundchararr = new String[word.length()];
		// Set dashes for all the characters, they represent not yet found words
		for (int i = 0; i < word.length(); i++) {
			foundchararr[i] = " - ";
		}
		// Setup an array where the characters of the lexicon word will be stored
		chararr = new String[word.length()];
		// Put every character of the lexicon word in this array
		for (int i = 0; i < word.length(); i++) {
			chararr[i] = Character.toString(word.charAt(i));
		}
	}

	// Check if a input character is in the lexicon
	private Boolean checkCharacter() {
		if (word.contains(character)) {
			return true;
		} else {
			return false;
		}
	}

	// When correct character
	private void correctCharacter() {
		displayCharacterWin();
		// Set character at the right place in the array
		for (int i = 0; i < word.length(); i++) {
			// Compare input character with character in lexicon word
			if (character .equals(chararr[i]) ) {
				// Assign character to the array that keeps track of found chars
				foundchararr[i] = character;
			}
		}		
	}

	// When wrong character
	private void wrongCharacter() {
		// Increment the wrong guess counter
		wrongguessescount += 1;
		wrongguesses.add(character);
		displayCharacterFail();
	}

	// Check if the whole word is found by matching the two arrays
	private Boolean checkWordFound() {
		if (Arrays.equals(foundchararr, chararr)) {
			return true;
		} else {
			return false;
		}
	}

	// Create string from correct characters found by the user
	private String foundCharactersToString() {
		foundword = "";
		for (int i = 0; i < word.length(); i++) {
			foundword = foundword + foundchararr[i];
		}
		return foundword;
	}

	// Create string from wrong characters found by the user
	private String foundWrongCharactersToString() {
		String listwrongcharacters = "";
		for (int i = 0; i < wrongguesses.size(); i++) {
			listwrongcharacters = listwrongcharacters + wrongguesses.get(i);
		}
		return listwrongcharacters;
	}

	/*
	 * MESSAGE TO SCREEN METHODS
	 */

	// Print amount of guesses left until game over
	private void displayGuessesLeft() {
		println("You have " + wrongguessescount + "left.");
	}

	private void displayGameWin() {
		println("YOU SURVIVED!");
	}

	private void displayGameLost() {
		println("You hang.");
		println("The word was: " + word);
	}

	// Print the current found characters of the word
	private void displayCurrentWord() {
		foundword = foundCharactersToString();
		println("The word now looks like this: " + foundword);
		canvas.displayWord(foundword);
	}

	private void displayCharacterFail() {
		println("There are no " + character + "'s in the word."); 
		String wrongguesses = foundWrongCharactersToString();
		canvas.noteIncorrectGuess(wrongguesses, wrongguessescount);
	}

	private void displayCharacterWin() {
		println("The guess is correct.");
	}

	// Lexicon and Canvas objects
	private HangmanLexicon lexicon;
	private HangmanCanvas canvas;

	// Currently found characters
	private String[] foundchararr;
	// All characters of the lexicon word
	private String[] chararr;
	// Wrong guessed characters
	private ArrayList<String> wrongguesses;

	// Current lexicon word
	private String word;
	// Current character
	private String character;
	// All currently found characters as string
	private String foundword;
	// Counter which keeps track of the amount of wrong guesses
	private int wrongguessescount;
	// Nescecary for to check if input is a letter
	private char precharacter;
}
