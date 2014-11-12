/*
 * File: Subtractor.java
 * -------------------
 * Name: Y. de Boer
 *
 * This class subtracts 1 from an integer
 */

public class Subtractor {

	public Subtractor(int startValue) {
		counter = startValue;
	}

	public void nextValue() {
		counter--;
	}

	public int getValue() {
		return (counter);
	}

	private int counter;
}