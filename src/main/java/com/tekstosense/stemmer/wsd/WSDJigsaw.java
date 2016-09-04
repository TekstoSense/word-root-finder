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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import jigsaw.JIGSAW;
import jigsaw.data.Token;
import jigsaw.data.TokenGroup;
import net.sf.extjwnl.data.PointerType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TekstoSense
 */
public class WSDJigsaw implements WSD{

    private static final String WORDNET_CONFIG = "wordnetProperties.xml";
    private final Log LOG = LogFactory.getLog(WSDJigsaw.class);
    private ClassLoader classLoader = getClass().getClassLoader();

    @Override
    public Multimap<String, String> disambiguate(String text) throws Exception {

        TokenGroup tg = JIGSAW.getWSDSynset(this.classLoader.getResourceAsStream("jigsaw.properties"),
                this.classLoader.getResourceAsStream(WORDNET_CONFIG), text);

        List<Token> tokens = tg.getTokens().stream().filter(s -> s.getSynset() != null).collect(Collectors.toList());

/*
        for (Token token : tokens) {
			if (token.getLemma().equalsIgnoreCase("red")) {
				System.out.print(token.getLemma());
				System.out.print(" ");
				System.out.print(token.getLemma());
				System.out.print(" ");
				System.out.print(token.getSynset());
				System.out.println();
				//System.out.println(token.getSynset().getPointers().stream().forEach(p -> p.getType()));
				token.getSynset().getPointers().stream().forEach(p -> {System.out.println(p.getType());});
				System.out.println();
			}
		}
*/

        Multimap<String, String> rootList = ArrayListMultimap.create();
        tokens.stream().forEach(x -> {
            if (x != null)
                getHypernyms(x, rootList);
        });
        return rootList;
    }

    private void getHypernyms(Token token, Multimap<String, String> rootList) {
        token.getSynset().getPointers(PointerType.HYPERNYM).stream().forEach(p -> {
            try {
                rootList.putAll(token.getLemma(), p.getTarget().getSynset().getWords().stream().map(m -> m.getLemma()).collect(Collectors.toList()));
            } catch (Exception e) {
                LOG.error(e, e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        WSDJigsaw dict = new WSDJigsaw();
        System.out.print(dict.disambiguate("A greater proportion of mesophil microorganisms were to be found during the cold months than in warmer months."));
    }
}
