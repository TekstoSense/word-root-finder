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
package com.tekstosense.stemmer.commandline;

import com.tekstosense.stemmer.analyzer.Analyzer;
import com.tekstosense.stemmer.namedentity.EntityTaggerType;
import com.tekstosense.stemmer.parser.ParserType;
import com.tekstosense.stemmer.wsd.WSDType;

public class CLI {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Input needed...");
			System.exit(1);
		}
		Params params = Params.getParams(args);
		Analyzer analyzer = new Analyzer(WSDType.JIGSAW, EntityTaggerType.Stanford, ParserType.OpenNLP, params.getInput());
		analyzer.analyseText();
		System.out.println(analyzer.getStemedWords());
	}
}
