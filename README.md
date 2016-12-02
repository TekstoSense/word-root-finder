# Word Root Finder

Word Root Finder API is based on our paper Personalised Terms Derivative(PETD) selected in a conference "International Conference on Infformation Technology" December 2016, IIIT Bhubaneswar, India. It will be available in IEEE explore very soon. You can also refer the article from our website [TekstoSense](https://www.tekstosense.com). Once we will get the DOI and IEEE link we will provide the link for the same.




Download the Source code
Build- mvn clean install

We can use the word-root finder fronm command line like :

java -jar /home/tekstosense/word-root-finder/word-root-finder-2.0.0-jar-with-dependencies.jar -input "People were screaming and running down the steps to escape the flames. She runs much faster than he does" -wsdType "JIGSAW" -tagger "OpenNLP" -parser "OpenNLP" -model "/home/tekstosense/opennlp-enhancer/src/test/resources"

Input 1 (-input): List of input words 
Input 2 (-wsdType) : Algorithm for word sense disambiguation. We have used JIGSAW and Personalised Page Rank(PPR). (JIGSAW | PPR)
Input 3 (-tagger) : Framework for tagging Named Entity. We have used Tekstosense opennlp-enhancer module for entity tagging which is based on OpenNLP. 
                    Stanford can also be used as another options.(Stanford | OpenNLP)
Input 4 (-parser) : OpenNLP parser
Input 5 (-model) : if tagger(Input 3) is OpenNLP then model path is mandatory. Path for opennlp based named entity model files. 


We can also use API approach to call required methods: 

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


## License

GNU
