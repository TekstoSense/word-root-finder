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
package com.tekstosense.stemmer.analyzer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.tekstosense.stemmer.namedentity.EntityTagger;
import com.tekstosense.stemmer.namedentity.EntityTaggerFactory;
import com.tekstosense.stemmer.namedentity.EntityTaggerType;
import com.tekstosense.stemmer.parser.Parser;
import com.tekstosense.stemmer.parser.ParserFactory;
import com.tekstosense.stemmer.parser.ParserType;
import com.tekstosense.stemmer.stemmers.KrovetzStemmer;
import com.tekstosense.stemmer.stemmers.Stemmer;
import com.tekstosense.stemmer.wsd.WSD;
import com.tekstosense.stemmer.wsd.WSDFactory;
import com.tekstosense.stemmer.wsd.WSDType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

	private static final String NON_ENTITY = "O";
	private static final String NOUN = "NN";
	private final WSD wsd;
	private final EntityTagger entityTagger;
	private Stemmer stemmer;
	private final Parser parser;
	private final String text;
	private Multimap<String, String> stemedWords;

	public Analyzer(WSDType dictionaryType, EntityTaggerType taggerType, ParserType parserType, String text) {
		this.wsd = WSDFactory.getWSD(dictionaryType);
		this.entityTagger = EntityTaggerFactory.getEntityTagger(taggerType);
		this.parser = ParserFactory.getEntityTagger(parserType);
		this.text = text;
	}

	public void analyseText() throws Exception {
		buildStemmer();
		findRootWord();

	}

	public Multimap<String, String> getStemedWords() {
		return stemedWords;
	}

	private void buildStemmer() throws Exception {

		String[] entity = entityTagger.annotateText(text).asMap().entrySet().stream().filter(e -> !e.getKey().equalsIgnoreCase(NON_ENTITY))
				.map(Map.Entry::getValue).flatMap(Collection::stream).toArray(String[]::new);

		String[] parse = parser.parseText(text).asMap().entrySet().stream().filter(e -> e.getKey().startsWith(NOUN)).map(Map.Entry::getValue)
				.flatMap(Collection::stream).toArray(String[]::new);

		this.stemmer = new KrovetzStemmer(entity, parse);
	}

	private void findRootWord() throws Exception {
		Multimap<String, String> rootWords = this.wsd.disambiguate(this.text);
		Set<String> rootwordSet = rootWords.keySet().stream().map(String::toLowerCase).collect(Collectors.toSet());
		List<String> uniqueList = Arrays.stream(text.split("\\s+")).map(String::toLowerCase).filter(l -> !rootwordSet.contains(l)).collect(Collectors.toList());
		for (String term : uniqueList) {
			String stem = this.stemmer.stem(term);
			rootWords.put(term,stem);
		}
		this.stemedWords = rootWords;
	}
}
