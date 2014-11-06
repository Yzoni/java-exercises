/*
 * File: Hailstone.java
 * Author: Yorick de Boer 10786015
 * --------------------
 * This program is a stub for the Hailstone problem, which computes
 * Hailstone sequence described in Assignment #2.

1. Pick some positive integer and call it n.
2. If n is even, divide it by two.
3. f n is odd, multiply it by three and add one.
4. Continue this process until n is equal to one.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram
{
	public void run()
	{
		println("This program computes Hailstone sequences.");
		int n = readInt("Enter a number: ");
		
		// Initialize the step counter
		int count = 1;

		// While n is not 1 check do either 3n + 1 or divede by 2
		while (n != 1) {
			int even = checkEven(n);

			// If even then divide n by 2 and print new number
			if (even == 1) {
				int tempn = n;
				n = n / 2;
				println(tempn + " is even, so I take half = " + n);
			// If not even do n * 3 + 1 and print new number
			} else {
				int tempn = n;
				n = n * 3 + 1;
				println(tempn + " is odd, so I make 3n + 1 = " + n);
			}
			// Add 1 to the counter after a step
			count += 1;
		}
		// If n equals to 1, break loop and print the amount of ste
		println("The process took " + count + " steps to reach 1.");
	}

	// Retunr 1 if n is even 
	private int checkEven(int n) {
		if (n % 2 == 0) {
			return (1);
		} else {
			return (0);
		}
	}
}
