# Word Root Finder - A Semantic approach for stemming

Word Root Finder API is based on our paper Personalised Terms Derivative(PETD) selected in a conference "International Conference on Information Technology" December 2016, IIIT Bhubaneswar, India. It will be available in IEEE explore very soon. You can also refer the article from our website [TekstoSense](https://www.tekstosense.com). Once we will get the DOI and IEEE link we will provide the link for the same.

Word Root finder includes existing concept of linguistic and morphological stemming provided by PorterStemmer and K-Stem enriched with Semantic 
approach to find root word. The semantic approach of finding Root word is very useful for many Search engine like Solr and Elastic Search. Refer our 
[PETD](https://www.tekstosense.com) document for complete details.

Release version : 2.0.0 includes wordsense disambiguation based on [JIGSAW](http://aclweb.org/anthology/S/S07/S07-1088.pdf) and [Personalised Page Rank](http://www.aclweb.org/anthology/E09-1005). Our next release 3.0.0 will include Apache licensed Word sense disambiguation.

## How to use Word Root Finder

Download the Source code
Build- mvn clean install

### Dependencies
The project requires JDK 1.8 and is built completely with Maven (althogh some of the dependencies are local). 

We support two algorithm :

1. The underlying WSD algorithm (Personalised Page Rank) requires Wordnet, we include Wordnet 3.0 in the data.dict package and read it from the jar 
   (this is  particularly useful when running in Hadoop). We have used code available at [github] (https://github.com/alexandruasandei89/wsd-at-scale-wikipedia) for PPR.
Download and Build this dependency and include the dependency jar in lib folder.

2. The WSD algorithm [JIGSAW](http://aclweb.org/anthology/S/S07/S07-1088.pdf) jar is included in lib folders.


### Getting Started :
- Command Line
- API

We can use the word-root finder from command line like :

```
java -jar /home/tekstosense/word-root-finder/word-root-finder-2.0.0-jar-with-dependencies.jar -input "People were screaming and running down the steps to escape the flames. She runs much faster than he does" -wsdType "JIGSAW" -tagger "OpenNLP" -parser "OpenNLP" -model "/home/tekstosense/opennlp-enhancer/src/test/resources"

```

1. Input 1 (-input)   : List of input words 
2. Input 2 (-wsdType) : Algorithm for word sense disambiguation. We have used JIGSAW and Personalised Page Rank(PPR). (JIGSAW | PPR)
3. Input 3 (-tagger)  : Framework for tagging Named Entity. We have used Tekstosense [opennlp-enhancer](https://github.com/TekstoSense/opennlp-enhancer) module for entity tagging which is based on OpenNLP. 
                        Stanford can also be used as another options.(Stanford | OpenNLP)
4. Input 4 (-parser)  : OpenNLP parser
5. Input 5 (-model)   : if tagger(Input 3) is OpenNLP then model path is mandatory. Path for opennlp based named entity model files. 

For command line operation copy jars available in lib folder as classpath jars. 

We can also use API approach to call required methods: 

```
		String text = "People were screaming and running down the steps to escape the flames. She runs much faster than he does";
		Analyzer analyzer = new Analyzer(WSDType.PPR, EntityTaggerType.OpenNLP, ParserType.OpenNLP, text);
		try {
			analyzer.analyseText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<String> itr = analyzer.getStemedWords().get("screaming")
				.iterator();
		System.out.println(itr.next());
		
```		


## License

GNU
