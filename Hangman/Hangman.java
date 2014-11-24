/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import java.io.*;
import java.util.Arrays;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	
	private static final int AMOUNT_GUESSES = 8;

	public void run()
	{
		setupLexicon();
		setupGuessCounter();
		setupWelcomeMessage();
		mainGameLoop();
	}

	private void mainGameLoop() {
		getWordLexicon();
		for (int i = 0; i < AMOUNT_GUESSES; i++) {
			String precharacter = readLine("Your Guess: ");
			character = precharacter.toUpperCase();
			guesses =- 1;
			checkCharacter();
			displayWord();
			if (wordFound()) break;
		}
		if (wordFound() == false) displayLost();
	}

	private void setupLexicon() {
		lexicon = new HangmanLexicon();
	}

	// Print welcome message
	private void setupWelcomeMessage() {
		println("Welcome to Hangman");
	}

	private void setupGuessCounter() {
		guesses = AMOUNT_GUESSES;
	}

	private void getWordLexicon() {
		RandomGenerator rgen = RandomGenerator.getInstance();
		int randomindex = rgen.nextInt(0, lexicon.getWordCount());
		word = lexicon.getWord(randomindex);
		// Array with found characters
		foundchararr = new String[word.length()];
		// Set dashes for not yet guessed characters
		for (int i = 0; i < word.length(); i++) {
			foundchararr[i] = " - ";
		}
		// Array with characters of lexicon word
		chararr = new String[word.length()];
		// Put every character of the lexicon word in an array
		for (int i = 0; i < word.length(); i++) {
			chararr[i] = Character.toString(word.charAt(i));
		}
	}

	// Print the current found characters of the word
	private void displayWord() {
		String foundword = "";
		for (int i = 0; i < word.length(); i++) {
			foundword = foundword + foundchararr[i];
		}
		println("The word now looks like this: " + foundword);
	}

	// Print amount of guesses left until game over
	private void displayGuessesLeft() {
		println("You have " + guesses + "left.");
	}

	// Check if a input character is in the lexicon
	private void checkCharacter() {
		if (word.contains(character) == true) {
			displayCharacterWin();
			// Set character at right place if found
			for (int i = 0; i < word.length(); i++) {
				// Compare input character with character in lexicon word
				if (character .equals(chararr[i]) ) {
					// Assign character to the found word
					foundchararr[i] = character;
				}
			}
		} else {
			displayCharacterFail();
		}
	}

	private Boolean wordFound() {
		if (Arrays.equals(foundchararr, chararr)) {
			return true;
		} else {
			return false;
		}
	}

	private void displayLost() {
		println("You hang.");
	}

	private void displayCharacterFail() {
		println("There are no " + character + " in the word."); 
	}

	private void displayCharacterWin() {
		println("The guess is correct.");
	}

	private HangmanLexicon lexicon;

	private String[] foundchararr;
	private String[] chararr;
	private String word;
	private String character;
	private int guesses;
}
