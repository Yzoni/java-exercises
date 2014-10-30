/*
 * File: MidpointFindingKarel.java
 * Author: Y.J.I. De Boer
 * -------------------------------
 * The MidpointFindingKarel leaves a beeper on the corner closest to 
 * the center of 1st Street (or either of the two central corners if 1st Street 
 * has an even number of corners). The world can be of size >= 1. The class works 
 * by moving between beepers, while the beepers are placed closer to each other 
 * with every move from left to right or right to left. Do this untill only one beeper 
 * is left.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel
{
	public void run() {
		//Setup initial situation
		putBeeper();
		while (frontIsClear()) { 
			move();
		}
		putBeeper();
		turnAround();
		move();

		//Start algorithm
		moveBetweenStones();
		
		//End standing on the beeper
		turnAround();
		move();
	}

	// Algorithm
	private void moveBetweenStones() {
		while (noBeepersPresent()) {
			move(); 
			if (beepersPresent()) {
				pickBeeper();
				turnAround();
				move();
				putBeeper();
				move();
			}
		}
		pickBeeper();
	}
}
