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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

public class EntityTaggerFactory {

	static Map<EntityTaggerType, EntityTagger> taggerMap;
	static {
		taggerMap = new HashMap<EntityTaggerType, EntityTagger>();
		taggerMap.put(EntityTaggerType.Stanford, new StanfordEntityTagger());
	}

	public static EntityTagger getEntityTagger(EntityTaggerType taggerType) {
		return taggerMap.get(checkNotNull(taggerType, "Type is Null"));
	}
}
