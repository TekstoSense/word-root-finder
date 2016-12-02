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
			System.out.println("1:Input String(-input)");
			System.out
					.println("2:WSDType(-wsdType): PPR(Personalised Page Rank)/ JIGSAW(SVD based) \n"
							+ "3: Tagger(-tagger) : Stanford /OpenNLP \n"
							+ "4: Parser(-parser) : OpenNLP \n");
			System.out
					.println("5:Named Entity Model Path(-model) :For -tagger=OpenNLP provide model path, for -tagger=Stanford model path not required");
			System.exit(1);
		}
		Params params = Params.getParams(args);

		if (params.getWsdtype() != WSDType.JIGSAW
				&& params.getWsdtype() != WSDType.PPR) {
			System.err.println("Please enter valid WSDType : JIGSAW or PPR");
			System.exit(1);

		}
		if (params.getTagger() != EntityTaggerType.OpenNLP
				&& params.getTagger() != EntityTaggerType.Stanford) {
			System.err
					.println("Please enter valid EntityTagger : OpenNLP or Stanford");
			System.exit(1);

		}
		if (params.getParserType() != ParserType.OpenNLP) {
			System.err.println("Please enter valid Parser Type : OpenNLP");
			System.exit(1);

		}

		Analyzer analyzer = new Analyzer(params.getWsdtype(),
				params.getTagger(), params.getParserType(), params.getInput(),
				params.getModelPath());
		analyzer.analyseText();
		System.out.println(analyzer.getStemedWords());
	}
}
