/*
 * File: Quadratic.java
 * Author: Yorick de Boer 10786015
 * --------------------
 * This program is a stub for the Quadratic problem, which finds the
 * roots of the quadratic equation.
 */

import acm.program.*;
import java.lang.Math;

public class Quadratic extends ConsoleProgram
{
	public void run()
	{
		// Get a, b and c from user input
		println("The program solves x for equations of the form ax^2 + bx + c = 0");
		double a = readDouble("Give a: ");
		double b = readDouble("Give b: ");
		double c = readDouble("Give c: ");

		// Calculate the discriminant
		double discriminant = calcDiscriminant(a, b, c);

		// If the discriminant is greater than 0, print the solutions
		giveSolutions(a, b, c, discriminant);
	}

	// Method which returns the discriminant
	private double calcDiscriminant(double a, double b, double c) {
		double discriminant = Math.sqrt(Math.pow(b,2) - 4 * a * c);
		return(discriminant);
	}

	// Method that gives the solutions if there are any solutions
	private void giveSolutions(double a, double b, double c, double discriminant) {
		if (discriminant > 0) {
			double negx = (- b - discriminant) / (2 * a);
			double posx = (- b + discriminant) / (2 * a);
			println("The first solution is: " + negx);
			println("The second solution is: " + posx);
		// If the discriminant is exactly 0, there is only 1 solution
		} else if (discriminant == 0) {
			double posx = (- b + discriminant) / (2 * a);
			println("Only one solution: " + posx);
		// If the discriminant is less than 0, there are not solutions
		} else {
			println("No solutions.");
		}
	}
}
