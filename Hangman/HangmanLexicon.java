/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon {

	/** Returns the number of words in the lexicon. */
	public int getWordCount()
	{
		setupWords();
		return words.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index)
	{
		return words.get(index);
	}

	// Read in all the words from a file by using BufferedReader
	private void setupWords() {
		try { in = new BufferedReader(new FileReader("OpenTaalWoordenlijst.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String word = readWord(in);
		words = new ArrayList<String>();
		// Keep reading untill the end of the file
		while(word != null) {
			word = readWord(in);
			words.add(word);
		}
	}

	// Read a line from a bufferedreader, parameter is a BufferedReader object
	private String readWord(BufferedReader input) {
		try { readword = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readword;
	}
	
	private BufferedReader in;
	private String readword;
	private ArrayList<String> words;
}
