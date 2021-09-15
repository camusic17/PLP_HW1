package edu.ufl.cise.plpfa21.assignment1;

public class CompilerComponentFactory {

	static IPLPLexer getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		Lexer l = new Lexer();
		try {
			l.SimpleScanner(input);
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}
	

}
