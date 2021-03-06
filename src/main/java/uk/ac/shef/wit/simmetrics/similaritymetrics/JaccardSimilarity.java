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

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

/**
 * Implements the Jaccard Similarity algorithm providing a similarity measure
 * between two strings
 * 
 * Each instance is represented as a Jaccard vector similarity function. The
 * Jaccard between two vectors X and Y is (X*Y) / (|X||Y|-(X*Y))
 * 
 * where (X*Y) is the inner product of X and Y, and |X| = (X*X)^1/2, i.e. the
 * Euclidean norm of X.
 * 
 * This can more easily be described as ( |X & Y| ) / ( | X or Y | )
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class JaccardSimilarity extends AbstractStringMetric 
		 {

	private final float ESTIMATEDTIMINGCONST = 1.4e-4f;

	private final InterfaceTokeniser tokenizer;

	/**
	 * Constructs a JaccardSimilarity metric with a {@link TokeniserWhitespace}.
	 */
	public JaccardSimilarity() {
		this.tokenizer = new TokeniserWhitespace();
	}

	/**
	 * Constructs a CosineSimilarity metric with the given tokenizer.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public JaccardSimilarity(final InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Jaccard Similarity algorithm providing a similarity measure between two strings";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		final float str1Tokens = tokenizer.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokenizer.tokenizeToArrayList(string2).size();
		return (str1Tokens * str2Tokens) * ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		/*
		 * Each instance is represented as a Jaccard vector similarity function.
		 * The Jaccard between two vectors X and Y is
		 * 
		 * (X*Y) / (|X||Y|-(X*Y))
		 * 
		 * where (X*Y) is the inner product of X and Y, and |X| = (X*X)^1/2,
		 * i.e. the Euclidean norm of X.
		 * 
		 * This can more easily be described as ( |X & Y| ) / ( | X or Y | )
		 */
		// todo this needs checking
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);

		final Set<String> allTokens = new HashSet<String>();
		allTokens.addAll(str1Tokens);
		final int termsInString1 = allTokens.size();
		final Set<String> secondStringTokens = new HashSet<String>();
		secondStringTokens.addAll(str2Tokens);
		final int termsInString2 = secondStringTokens.size();

		// now combine the sets
		allTokens.addAll(secondStringTokens);
		final int commonTerms = (termsInString1 + termsInString2)
				- allTokens.size();

		// return JaccardSimilarity
		return (float) (commonTerms) / (float) (allTokens.size());
	}

}
