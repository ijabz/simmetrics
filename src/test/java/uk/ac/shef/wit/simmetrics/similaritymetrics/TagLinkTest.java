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



public class TagLinkTest extends InterfaceStringMetricTest {

	@Override
	public InterfaceStringMetric getMetric() {
		return new TagLink();
	}

	@Override
	public T[] getTests() {
		return new T[] {
				new T(0.9097f, "test string1", "test string2"),
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "a b c d", "a b c e"),
				new T(0.7639f, "Healed", "Sealed"),
				new T(0.6429f, "Healed", "Healthy"),
				new T(0.6973f, "Healed", "Heard"),
				new T(0.6111f, "Healed", "Herded"),
				new T(0.5687f, "Healed", "Help"),
				new T(0.1719f, "Healed", "Sold"),
				new T(0.5687f, "Healed", "Help"),
				new T(0.8754f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.9063f, "Sam Chapman", "S Chapman"),
				new T(0.5463f, "John Smith", "Samuel John Chapman"),
				new T(0.2402f, "John Smith", "Sam Chapman"),
				new T(0.3322f, "John Smith", "Sam J Chapman"),
				new T(0.2589f, "John Smith", "S Chapman"),
				new T(0.8273f, "Web Database Applications", "Web Database Applications with PHP & MySQL"),
				new T(0.8062f, "Web Database Applications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.8062f, "Web Database Applications", "Building Database Applications on the Web Using PHP3"),
				new T(0.8062f, "Web Database Applications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.5991f, "Web Database Applications", "Web Application Development With PHP"),
				new T(0.7402f, "Web Database Applications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0920f, "Web Database Applications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0885f, "Web Database Applications", "How to Find a Scholarship Online"),
				new T(0.6506f, "Web Aplications", "Web Database Applications with PHP & MySQL"),
				new T(0.6353f, "Web Aplications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.6353f, "Web Aplications", "Building Database Applications on the Web Using PHP3"),
				new T(0.6353f, "Web Aplications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.6531f, "Web Aplications", "Web Application Development With PHP"),
				new T(0.5874f, "Web Aplications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0922f, "Web Aplications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0587f, "Web Aplications", "How to Find a Scholarship Online"),
		};
	}
}

