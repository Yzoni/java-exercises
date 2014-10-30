/*
 * File: CollectNewspaperKarel.java
 * Author: Y.J.I. De Boer
 * --------------------------------
 * Three new methods are used for picking up the paper from a specifified position
 * Which are move to the newspaper, pick up the newspaper and returning the newspaper
 * to the starting point.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel
{
	public void run() {
		moveToNewspaper();
		pickUp();
		returnToStartingPoint();
	}

	private void moveToNewspaper() {
		move();
		move();
		turnRight();
		move();
		turnLeft();
		move();
	} 

	private void pickUp() {
		pickBeeper();
	}
		
	private void returnToStartingPoint() {
		turnAround();
		move();
		move();
		move();
		turnRight();
		move();
		turnRight();
	}
}
