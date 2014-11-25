/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.

  TO DO
  give error when user input is two times correct letter
  give error when user input is empty
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
			displayWord();
			try {precharacterstring = readLine("Your Guess: ");
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			char precharacter = precharacterstring.charAt(0);
			// Check if input is 1 char and is a letter
			if (precharacterstring.length() > 1 || Character.isLetter(precharacter) == false) {
				println("Input needs to be one letter! Try again.");
			} else {
				character = precharacterstring.toUpperCase();
				checkCharacter();
			}
			if (checkWordFound()) {
				displayWord();
				displayGameWin();
				break;
			}
		}
		if (checkWordFound() == false) displayGameLost();
		String stub = readLine("Press ENTER to play again");
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
	private void checkCharacter() {
		if (word.contains(character) == true) {
			displayCharacterWin();
			// Set character at the right place in the array
			for (int i = 0; i < word.length(); i++) {
				// Compare input character with character in lexicon word
				if (character .equals(chararr[i]) ) {
					// Assign character to the array that keeps track of found chars
					foundchararr[i] = character;
				}
			}
		} else {
			// Increment the wrong guess counter
			wrongguessescount += 1;
			wrongguesses.add(character);
			displayCharacterFail();
			String wrongguesses = getWrongGuesses();
			canvas.noteIncorrectGuess(wrongguesses, wrongguessescount);
		}
	}	

	// Check if the whole word is found by matching the two arrays
	private Boolean checkWordFound() {
		if (Arrays.equals(foundchararr, chararr)) {
			return true;
		} else {
			return false;
		}
	}

	private String foundWord() {
		foundword = "";
		for (int i = 0; i < word.length(); i++) {
			foundword = foundword + foundchararr[i];
		}
		return foundword;
	}

	/*
	 * MESSAGE TO SCREEN METHODS
	 */

	// Print the current found characters of the word
	private void displayWord() {
		foundword = foundWord();
		println("The word now looks like this: " + foundword);
		canvas.displayWord(foundword);
	}

	// Print amount of guesses left until game over
	private void displayGuessesLeft() {
		println("You have " + wrongguessescount + "left.");
	}

	private String getWrongGuesses() {
		String listwrongcharacters = "";
		for (int i = 0; i < wrongguesses.size(); i++) {
			listwrongcharacters = listwrongcharacters + wrongguesses.get(i);
		}
		return listwrongcharacters;
	}

	private void displayGameWin() {
		println("YOU SURVIVED!");
	}

	private void displayGameLost() {
		println("You hang.");
	}

	private void displayCharacterFail() {
		println("There are no " + character + " in the word."); 
	}

	private void displayCharacterWin() {
		println("The guess is correct.");
	}

	private HangmanLexicon lexicon;
	private HangmanCanvas canvas;

	private String[] foundchararr;
	private String[] chararr;
	private ArrayList<String> wrongguesses;

	private String word;
	private String character;
	private String foundword;
	private int wrongguessescount;

	private String precharacterstring;
}
