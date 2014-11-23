/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import java.io.*;

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
		}
	}

	private void setupLexicon() {
		lexicon = new HangmanLexicon();
	}

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
		// Create array with characters of string
		foundchararr = new String[word.length()];

		chararr = new String[word.length()];
		for (int i = 0; i < word.length(); i++) {
			chararr[i] = Character.toString(word.charAt(i));
		}
	}

	private void displayWord() {
		String foundword = ":";
		for (int i = 0; i < word.length(); i++) {
			foundword = foundword + foundchararr[i];
		}
		println("The word now looks like this: " + foundword);
	}

	private void displayGuessesLeft() {
		println("You have " + guesses + "left.");
	}

	private void checkCharacter() {
		if (word.contains(character) == true) {
			displayCharacterWin();
			// Set character at right place if found
			for (int i = 0; i < word.length(); i++) {
				if (character == chararr[i]) {
					foundchararr[i] = character;
				}
			}
		} else {
			displayCharacterFail();
		}
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
