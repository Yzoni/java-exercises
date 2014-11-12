/*
 * File: Subtractor.java
 * -------------------
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