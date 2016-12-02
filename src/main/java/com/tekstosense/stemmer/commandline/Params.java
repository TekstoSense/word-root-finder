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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tekstosense.stemmer.namedentity.EntityTaggerType;
import com.tekstosense.stemmer.parser.ParserType;
import com.tekstosense.stemmer.wsd.WSDType;

public class Params {

	@Parameter(names = "-input", description = "Input Text")
	private String input;

	@Parameter(names = "-wsdType", description = "Word Sense Disambiguation algorithm type")
	private WSDType wsdtype;

	@Parameter(names = "-tagger", description = "Entity Tagger")
	private EntityTaggerType tagger;

	@Parameter(names = "-parser", description = "Parser")
	private ParserType parserType;

	@Parameter(names = "-model", description = "Named Entity Model Path(Only for opennlp as tagger")
	private String modelPath ;

	public String getModelPath() {
		return modelPath;
	}

	private Params(){}
	
	public static Params getParams(String[] args) {
		Params cliParams = new Params();
		new JCommander(cliParams, args);
		return cliParams;
	}

	public String getInput() {
		return input;
	}
	public WSDType getWsdtype() {
		return wsdtype;
	}

	public EntityTaggerType getTagger() {
		return tagger;
	}

	public ParserType getParserType() {
		return parserType;
	}

}
