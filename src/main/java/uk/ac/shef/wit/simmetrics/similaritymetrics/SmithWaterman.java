/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.SubCost1_Minus2;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import static uk.ac.shef.wit.simmetrics.utils.Math.max3;
import static uk.ac.shef.wit.simmetrics.utils.Math.max4;

/**
 * Implements the Smith-Waterman edit distance function
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class SmithWaterman extends AbstractStringMetric 
	 {

	private final float ESTIMATEDTIMINGCONST = 1.61e-4f;

	private AbstractSubstitutionCost dCostFunc;

	private float gapCost;

	/**
	 * constructor - default (empty).
	 */
	public SmithWaterman() {
		// set the gapCost to a default value
		gapCost = 0.5f;
		// set the default cost func
		dCostFunc = new SubCost1_Minus2();
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 */
	public SmithWaterman(final float costG) {
		// set the gapCost to a given value
		gapCost = costG;
		// set the cost func to a default function
		dCostFunc = new SubCost1_Minus2();
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWaterman(final float costG,
			final AbstractSubstitutionCost costFunc) {
		// set the gapCost to the given value
		gapCost = costG;
		// set the cost func
		dCostFunc = costFunc;
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWaterman(final AbstractSubstitutionCost costFunc) {
		// set the gapCost to a default value
		gapCost = 0.5f;
		// set the cost func
		dCostFunc = costFunc;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Smith-Waterman algorithm providing a similarity measure between two string";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return ((str1Length * str2Length) + str1Length + str2Length)
				* ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		final float smithWaterman = getUnNormalisedSimilarity(string1, string2);

		// normalise into zero to one region from min max possible
		float maxValue = Math.min(string1.length(), string2.length());
		if (dCostFunc.getMaxCost() > -gapCost) {
			maxValue *= dCostFunc.getMaxCost();
		} else {
			maxValue *= -gapCost;
		}

		// check for 0 maxLen
		if (maxValue == 0) {
			return 1.0f; // as both strings identically zero length
		} else {
			// return actual / possible NeedlemanWunch distance to get 0-1 range
			return (smithWaterman / maxValue);
		}
	}

	/**
	 * Implements the Smith-Waterman distance function
	 * 
	 * @see http://www.gen.tcd.ie/molevol/nwswat.html for details .
	 *
	 * @param s
	 * @param t
	 * @return the Smith-Waterman distance for the given strings
	 */
	public float getUnNormalisedSimilarity(final String s, final String t) {
		final float[][] d; // matrix
		final int n; // length of s
		final int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		float cost; // cost

		// check for zero length input
		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}

		// create matrix (n)x(m)
		d = new float[n][m];

		// process first row and column first as no need to consider previous
		// rows/columns
		float maxSoFar = 0.0f;
		for (i = 0; i < n; i++) {
			// get the substution cost
			cost = dCostFunc.getCost(s, i, t, 0);

			if (i == 0) {
				d[0][0] = max3(0, -gapCost, cost);
			} else {
				d[i][0] = max3(0, d[i - 1][0] - gapCost, cost);
			}
			// update max possible if available
			if (d[i][0] > maxSoFar) {
				maxSoFar = d[i][0];
			}
		}
		for (j = 0; j < m; j++) {
			// get the substution cost
			cost = dCostFunc.getCost(s, 0, t, j);

			if (j == 0) {
				d[0][0] = max3(0, -gapCost, cost);
			} else {
				d[0][j] = max3(0, d[0][j - 1] - gapCost, cost);
			}
			// update max possible if available
			if (d[0][j] > maxSoFar) {
				maxSoFar = d[0][j];
			}
		}

		// cycle through rest of table filling values from the lowest cost value
		// of the three part cost function
		for (i = 1; i < n; i++) {
			for (j = 1; j < m; j++) {
				// get the substution cost
				cost = dCostFunc.getCost(s, i, t, j);

				// find lowest cost at point from three possible
				d[i][j] = max4(0, d[i - 1][j] - gapCost, d[i][j - 1]
						- gapCost, d[i - 1][j - 1] + cost);
				// update max possible if available
				if (d[i][j] > maxSoFar) {
					maxSoFar = d[i][j];
				}
			}
		}

		// return max value within matrix as holds the maximum edit score
		return maxSoFar;
	}
}
