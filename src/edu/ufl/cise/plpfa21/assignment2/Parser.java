package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.Lexer;

public class Parser implements IPLPParser{

	Lexer theLexer;
	
	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public Parser(String input)
	{
		theLexer = new Lexer(input);
	}

}
