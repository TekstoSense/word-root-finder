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
package com.tekstosense.stemmer.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {

	private static Parser openNLPParser;

	static Map<ParserType, Parser> parserMap;
	static {
		parserMap = new HashMap<ParserType, Parser>();
		parserMap.put(ParserType.OpenNLP, new OpenNLPParser());
	}

	public static Parser getEntityTagger(ParserType parserType) {
		// return parserMap.get(checkNotNull(parserType, "Type is Null"));
		if (parserType == ParserType.OpenNLP) {
			if (openNLPParser == null)
				openNLPParser = new OpenNLPParser();
			return openNLPParser;
		}
		return null;
	}
}
