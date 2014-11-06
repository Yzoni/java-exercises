/*
 * File: FindRange.java
 * Author: Yorick de Boer 10786015
 * --------------------
 * This program solves the FindRange problem, which finds the
 * smallest and largest values in a list of integers.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram
{	
	// Number that plays the sentinel
	private static final int SENTINEL = 0;

	public void run() {
		println("This program finds the smallest and largest integers");
		println("in a list. Enter values, one per line, using a 0");
		println("to signal the end of the list");

		// Get the first number from user input	
		int first = getFirst();

		// Initialize min and max from the first number
		int max = first;
		int min = first;

		// Get a number from input and look if it is a new max or min value
		findMinMax(min, max);
	}

	// Get the first number from user input, when the first number is the sentinel, 
	// ask for new number
	private int getFirst() {
		int first = readInt("? ");
		if (first == SENTINEL) {
			while (first == 0) {
				println("First number can't be the sentinel!");
				int first2 = readInt("? ");
				if (first2 != SENTINEL) {
					first = first2;
				}
			}
		}
		return(first);
	}

	// Continue to ask for the new numbers while the sentinel is not yet called
	private void findMinMax(int min, int max) {
		while (true) {
			int x = readInt("? ");
			// If the sential is called, print the current min an max values
			if (x == SENTINEL) {
				println("The smallest value is: " + min); 
				println("The largest value is : " + max); 
				break;
			}
			// Set the new mininmal and maximal value
			max = max(x, max);
			min = min(x, min);
		}
	}

	// Method which sets a new highest number if x is greater
	private int max(int x, int max) {
		if (x > max) {
			return (x);
		} else {
			return (max);
		}
	}

	// Method which sets a new lowest number if x is smaller
	private int min(int x, int min) {
		if (x > min) {
			return (min);
		} else {
			return (x);
		}
	}
}
