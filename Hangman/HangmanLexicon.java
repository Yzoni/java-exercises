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

	private void setupWords() {
		try { in = new BufferedReader(new FileReader("OpenTaalWoordenlijst.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String word = readWord();
		words = new ArrayList<String>();
		while(word != null) {
			word = readWord();
			words.add(word);
		}
	}

	// Read a word from a bufferedreader
	private String readWord() {
		try { readword = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readword;
	}
	private BufferedReader in;
	private String readword;
	private ArrayList<String> words;
}
