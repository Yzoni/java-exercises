/*
 * File: StoneMasonKarel.java
 * Author: Y.J.I. De Boer
 * --------------------------
 * This solves the "repair the quad" problem. Pillars are always 4 columns appart. 
 * Begint with the most left pillar from bottom to top. After completing a pillar
 * move back to the bottom line and move to the next pillar. 
 * 
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel
{
	public void run() {
		while (frontIsClear()) {
			turnLeft();
			doPillar();
			//Check the if there is a top stone if not place a stone
			if (noBeepersPresent()) {
				putBeeper();
			}
			//Go back down after finishing pillar
			turnAround();
			while (frontIsClear()) {
				move();
			}
			turnLeft();
			moveToNextPillar();
		}
		//Build the last most right pillar
		turnLeft();
		doPillar();
	}

//Make a whole pillar
	private void doPillar() {
		while (frontIsClear()){
			if (beepersPresent()) {
				move();
			} else {
				putBeeper();
				move();
			}
		}
	}

//Method to move 4 columns to the next pillar
	private void moveToNextPillar() {
			move();
			move();
			move();
			move();
	}
}
