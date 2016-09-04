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
package com.tekstosense.stemmer.commandline;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Params {

	@Parameter(names = "-input", description = "Input File Path")
	private String input;

	private Params(){}
	
	public static Params getParams(String[] args) {
		Params cliParams = new Params();
		new JCommander(cliParams, args);
		return cliParams;
	}

	public String getInput() {
		return input;
	}
}
