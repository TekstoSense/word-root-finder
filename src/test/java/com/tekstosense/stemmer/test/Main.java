package com.tekstosense.stemmer.test;

import java.util.Iterator;

import org.junit.Assert;

import com.tekstosense.stemmer.analyzer.Analyzer;
import com.tekstosense.stemmer.namedentity.EntityTaggerType;
import com.tekstosense.stemmer.parser.ParserType;
import com.tekstosense.stemmer.wsd.WSDType;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String text = "People were screaming and running down the steps to escape the flames. She runs much faster than he does";
		Analyzer analyzer = new Analyzer(WSDType.PPR, EntityTaggerType.OpenNLP, ParserType.OpenNLP, text);
		try {
			analyzer.analyseText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> itr = analyzer.getStemedWords().get("screaming")
				.iterator();
		System.out.println(itr.next());
		// Assert.assertTrue(itr.next().equals("scream"));
		// System.out.println(analyzer.getStemedWords());
	}

}
