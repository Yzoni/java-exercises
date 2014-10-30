/*
 * File: MazeSolvingKarel.java
 * Author: Y.J.I. De Boer
 * 10786015
 * --------------------------
 *
 * The MazeSolvingKarel subclass is able to solve the first two mazes. To solve
 * these mazes the "wall folower" algorithm is used, which in this case means always 
 * try to turn left at a corner. An exception to this rule needs to made for the
 * second maze because otherwise woudl Karel get stuck in a loop returning to his 
 * starting point. So in case of a junction where Karel already went left one time
 * turn right next time. Karel remembers this junction by painting the corner. 
 */

import stanford.karel.*;

public class MazeSolvingKarel extends SuperKarel
{
	public void run() {
		while (noBeepersPresent()) {
			// Two exception rules in case of a junction
			if (rightIsClear() && leftIsBlocked() && cornerColorIs(RED)) {
				turnRight();
				move();
			}
			if (rightIsClear() && leftIsClear() && cornerColorIs(RED)) {
				move();
			}
			// Main maze solver
			if (leftIsClear() && cornerColorIs(null)) {
				if (frontIsClear()) {
					// nescecarry for the exception rule in case of a juntion
					paintCorner(RED);
				}
				turnLeft();
			} else {
				while (frontIsBlocked()) {
				turnRight();
				}
			}
		move();
		}
	}
}
