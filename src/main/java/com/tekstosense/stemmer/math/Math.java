/*******************************************************************************
 * Copyright (c) 2016, TekstoSense and/or its affiliates. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package com.tekstosense.stemmer.math;

import static java.lang.Math.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Math.
 * @author TekstoSense
 */
public class Math {

	/** The Constant SIGMA. */
	private static final double SIGMA = 2.0D;

	/**
	 * Gaussian Function {@link https://en.wikipedia.org/wiki/Gaussian_function}
	 *
	 * @param distance1
	 *            the distance 1
	 * @param distance2
	 *            the distance 2
	 * @return the double gause value of two distance
	 */
	public static double gaussian(int distance1, int distance2) {

		// differnce between 2 distances
		int d = distance1 - distance2;

		// Constant "a" of gausian function
		double a = (1 / (SIGMA * sqrt(2 * PI)));

		// Exponential power e^exp
		double exp = (pow(d, 2) / (2 * pow(SIGMA, 2)));

		double x = (1 / (2 * sqrt(2 * PI)));

		// Calculating Gause Value using above values
		//double gauseValue = 4 * a * exp(-exp) + x;
		double gauseValue = a * exp(-exp);

		return gauseValue;

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(gaussian(9, 7));
	}
}
