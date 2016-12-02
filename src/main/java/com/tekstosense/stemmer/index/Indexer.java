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
/*
 * 
 */
package com.tekstosense.stemmer.index;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PositiveScoresOnlyCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import com.tekstosense.stemmer.namedentity.NLPEntityTagger;

/**
 * The Class Indexer.
 *
 * @author TekstoSense
 */
public class Indexer {

	private static final Log LOGGER = LogFactory.getLog(Indexer.class);
	private static final String INDEX_PATH = "/home/tekstosense/luceneIndex";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws QueryNodeException
	 *             the query node exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public static void main(String[] args) throws IOException,
			QueryNodeException, ParseException {

		// indexer();
		searcher();

	}

	/**
	 * Searcher.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws QueryNodeException
	 *             the query node exception
	 * @throws ParseException
	 *             the parse exception
	 */
	private static void searcher() throws IOException, QueryNodeException,
			ParseException {
		Path indexDirectoryPath = new File(INDEX_PATH)
				.toPath();
		FSDirectory indexDirectory = new SimpleFSDirectory(indexDirectoryPath);
		DirectoryReader ireader = DirectoryReader.open(indexDirectory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser("title", new StandardAnalyzer());
		Query query = parser.parse("\"Lucene in Action\"");

		TopScoreDocCollector collector = TopScoreDocCollector.create(10);
		isearcher.search(query, new PositiveScoresOnlyCollector(collector));
		TopDocs topDocs = collector.topDocs();
		Set<String> fields = new HashSet<String>();
		fields.add("title");
		fields.add("isbn");
		for (ScoreDoc result : topDocs.scoreDocs) {
			Document doc = isearcher.doc(result.doc, fields);

			if (LOGGER.isInfoEnabled()) {

				LOGGER.info("--- Title :  "
						+ doc.getField("title").stringValue() + " ---");
				LOGGER.info("--- ISBN : " + doc.getField("isbn").stringValue()
						+ " ---");
				LOGGER.info(isearcher.explain(query, result.doc));
			}

		}

	}

	/**
	 * Indexer.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void indexer() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Path indexDirectoryPath = new File(INDEX_PATH)
				.toPath();
		FSDirectory indexDirectory = new SimpleFSDirectory(indexDirectoryPath);
		IndexWriterConfig conf = new IndexWriterConfig(analyzer);

		IndexWriter w = new IndexWriter(indexDirectory, conf);
		addDoc(w, "Lucene in Action", "193398817");
		addDoc(w, "Lucene for Dummies", "55320055Z");
		addDoc(w, "Managing Gigabytes", "55063554A");
		addDoc(w, "The Art of Computer Science", "9900333X");
		w.close();
	}

	/**
	 * Adds the doc.
	 *
	 * @param w
	 *            the w
	 * @param title
	 *            the title
	 * @param isbn
	 *            the isbn
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addDoc(IndexWriter w, String title, String isbn)
			throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Store.YES));
		doc.add(new StringField("isbn", isbn, Store.YES));
		w.addDocument(doc);
	}
}
