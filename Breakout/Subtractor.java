/*
 * File: Subtractor.java
 * -------------------
 * Name: Y. de Boer 10786015
 *
 * This class subtracts 1 from an integer
 */

public class Subtractor {

	//Constructor
	public Subtractor(int startValue) {
		counter = startValue;
	}
	// Subtract value by 1
	public void nextValue() {
		counter--;
	}

	public int getValue() {
		return (counter);
	}

	private int counter;
}