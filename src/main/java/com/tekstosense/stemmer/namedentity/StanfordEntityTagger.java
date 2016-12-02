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
package com.tekstosense.stemmer.namedentity;

import java.util.Properties;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordEntityTagger implements EntityTagger {
	static StanfordCoreNLP pipeline;

	private void loadResource() {
		if (pipeline == null) {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
			pipeline = new StanfordCoreNLP(props);
		}
	}

	public Multimap<String, String> annotateText(String text) {
		loadResource();
		Multimap<String, String> taggerTokens = ArrayListMultimap.create();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		for (CoreLabel token : document.get(TokensAnnotation.class)) {
			String ne = token.get(NamedEntityTagAnnotation.class);
			String word = token.get(TextAnnotation.class);
			taggerTokens.put(ne, word);
		}
		return taggerTokens;
	}

	public static void main(String[] args) {
		StanfordEntityTagger tagger = new StanfordEntityTagger();
		tagger.annotateText("Amitabh Harivansh Bachchan is an Indian film actor");
	}

	@Override
	public Multimap<String, String> annotateText(String text, String modelPath) {
		// TODO Auto-generated method stub
		return null;
	}
}
