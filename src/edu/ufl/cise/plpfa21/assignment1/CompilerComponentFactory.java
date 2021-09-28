package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.Parser;

public class CompilerComponentFactory {

	public static IPLPLexer getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.		
		return new Lexer(input);
	}
	
	@SuppressWarnings("exports")
	public static IPLPParser getParser(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		
		return new Parser(input);
	}
	

}
