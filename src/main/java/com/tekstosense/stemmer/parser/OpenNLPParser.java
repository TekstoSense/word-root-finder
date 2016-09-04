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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.AbstractBottomUpParser;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.InvalidFormatException;

public class OpenNLPParser implements com.tekstosense.stemmer.parser.Parser {
	private static Parser parser;
	private static final String PARSER_MODEL = "en-parser-chunking.bin";

	public static void main(String[] args) throws Exception {
		OpenNLPParser parser = new OpenNLPParser();
		String sentence = "Programcreek is a very huge and useful website.";
		System.out.println(parser.parseText(sentence).keySet().stream().filter(s -> s.startsWith("N")).collect(Collectors.toList()));

	}

	public Multimap<String, String> parseText(String text) throws Exception {
		loadResource();
		Multimap<String, String> termTags = ArrayListMultimap.create();
		Parse topParses[] = ParserTool.parseLine(text, parser, 1);
		for (Parse p : topParses) {
			List<Parse> parts = Arrays.asList(p.getChildren());
			for (Parse parse : parts) {
				getTermTags(parse, termTags);
			}
		}
		return termTags;
	}

	private void getTermTags(Parse parse, Multimap<String, String> termTags) {
		String POS = "";
		String text = "";
		if (!parse.getType().equals(AbstractBottomUpParser.TOK_NODE)) {
			POS = parse.getType();
			text = parse.getCoveredText();
		}
		List<Parse> parts = Arrays.asList(parse.getChildren());
		if (!POS.isEmpty() && !text.isEmpty()) {
			termTags.put(POS, text);
		}
		for (Iterator<Parse> i = parts.iterator(); i.hasNext();) {
			Parse c = i.next();
			getTermTags(c, termTags);
		}
	}

	private void loadResource() throws InvalidFormatException, IOException {
		if (parser == null) {
			InputStream is = OpenNLPParser.class.getClassLoader().getResourceAsStream(PARSER_MODEL);
			ParserModel model = new ParserModel(is);
			parser = ParserFactory.create(model);
			is.close();
		}

	}
}
