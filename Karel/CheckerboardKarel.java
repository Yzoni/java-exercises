/*
 * File: CheckerboardKarel.java
 * Author: Y.J.I. De Boer
 * ----------------------------
 * This class draws a checkerboard using beepers. Moving from the bottom row to the top row
 * doing row after row. 
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel
{
	public void run() {
		//exception in case world is only 1 high
		if (leftIsBlocked() && rightIsBlocked()) {
			doRow();
		} else {

		// First draw the bottom line and move to the second line,
		putBeeper();
		doRow();
		doBend();
		// Draw all the rows between the bottom row and the top one. 
		while(leftIsClear() && rightIsClear()) {
			doRow();
			doBend();
		}
		// Draw the top row. 
		doRow();
	}
	}

	// Method that does the move to the line above the current line
	private void doBend() {
		if (facingEast()) {
			turnLeft();
			buildBeeper();
			turnLeft();
		} else if (facingWest()) {
			turnRight();
			buildBeeper();
			turnRight();
		}
	}

	// Fill a row with with beepers
	private void doRow() {
		while (frontIsClear()) {
			buildBeeper();
			}
	}

	// Methed which actually drops the beepers
	private void buildBeeper() {
		if (noBeepersPresent()) {
			move();
			putBeeper();
		} else {
			move();
		}
	}
}