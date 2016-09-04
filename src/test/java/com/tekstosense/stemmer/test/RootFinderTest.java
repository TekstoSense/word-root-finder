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
package com.tekstosense.stemmer.test;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.tekstosense.stemmer.analyzer.Analyzer;
import com.tekstosense.stemmer.namedentity.EntityTaggerType;
import com.tekstosense.stemmer.parser.ParserType;
import com.tekstosense.stemmer.wsd.WSDType;

public class RootFinderTest {

	/**
	 * Test root finder.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRootFinder() throws Exception {
		String text = "People were screaming and running down the steps to escape the flames. She runs much faster than he does";
		Analyzer analyzer = new Analyzer(WSDType.JIGSAW, EntityTaggerType.Stanford, ParserType.OpenNLP, text);
		analyzer.analyseText();
		Iterator<String> itr = analyzer.getStemedWords().get("screaming").iterator();
		Assert.assertTrue(itr.next().equals("scream"));
	}
}
