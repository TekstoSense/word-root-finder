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

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author TekstoSense
 */
public class WSDFactory {
    static Map<WSDType, WSD> wsdMap;

    static {

        wsdMap = new HashMap<WSDType, WSD>();
        wsdMap.put(WSDType.JIGSAW, new WSDJigsaw());
        wsdMap.put(WSDType.PPR, new WSDPPR());
    }

    public static WSD getWSD(WSDType type) {
        return wsdMap.get(checkNotNull(type, "Type is Null"));
    }
}
