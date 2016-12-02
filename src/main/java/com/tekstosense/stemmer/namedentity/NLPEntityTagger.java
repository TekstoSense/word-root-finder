package com.tekstosense.stemmer.namedentity;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Multimap;
import com.tekstosense.opennlp.config.ModelLoaderConfig;
import com.tekstosense.opennlp.namefinder.OpenNLPEntityTagger;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NLPEntityTagger implements EntityTagger {

	private static final Log LOGGER = LogFactory.getLog(NLPEntityTagger.class);

	@Override
	public Multimap<String, String> annotateText(String text) {

		return null;

	}

	@Override
	public Multimap<String, String> annotateText(String text, String modelPath) {
		String[] models = ModelLoaderConfig
				.getModels(modelPath);
		OpenNLPEntityTagger entityTagger = new OpenNLPEntityTagger();
		try {
			entityTagger.loadModels(models);
		} catch (IOException e) {
			LOGGER.error(e, e);
		}
		// String result = entityTagger.getTextWithTag(text);
		return entityTagger.getEntityWithText(text);
	}

}
