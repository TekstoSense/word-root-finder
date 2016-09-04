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
package com.tekstosense.stemmer.wsd;

import com.google.common.collect.Multimap;
import gov.llnl.ontology.wordnet.Lemma;
import gov.llnl.ontology.wordnet.Synset;
import nl.vu.few.SyntacticWikipedia.WordAmbiguity;
import nl.vu.few.SyntacticWikipedia.WordDisambiguation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author TekstoSense
 */
public class WSDPPR implements WSD {
    public static void main(String[] args) throws Exception {
        WSDPPR wsdppr = new WSDPPR();
        wsdppr.disambiguate("The overall infection rate was 83% and of those infected, 88% felt that they had a cold.");
    }

    @Override
    public Multimap<String, String> disambiguate(String text) throws Exception {
        WordDisambiguation wordDisambiguation = new WordDisambiguation();
        wordDisambiguation.intit();
        ArrayList<WordAmbiguity> disambiguate = wordDisambiguation.doMain(text);
        /*disambiguate.stream().forEach(l -> {
            System.out.println("--------------------" + l.getWord() + "--------------------");
            l.getRealSense().getRelations(Synset.Relation.HYPERNYM).stream().forEach(
                    a -> a.getLemmas().stream().forEach(
                            k -> System.out.println(k.getLemmaName()
                            ))
            );
        });*/

        for (WordAmbiguity ambiguity : disambiguate){
            System.out.println("--------------------" + ambiguity.getWord() + "--------------------");
            if(ambiguity.getRealSense() != null) {
                Set<Synset> relations = ambiguity.getRealSense().getRelations(Synset.Relation.HYPERNYM);
                for (Synset synset : relations) {
                    List<Lemma> lemmas = synset.getLemmas();
                    for (Lemma lemma : lemmas) {
                        System.out.println(lemma.getLemmaName());
                    }
                }
            }
        }
        return null;
    }
}
