package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer implements IPLPLexer{

	private ArrayList<IPLPToken> tokens;
	private char[] chars;
	private int nextTokenPos = 0;
	static final char EOFchar = 0;
	
	public ArrayList<IPLPToken> SimpleScanner(String inputString)
	{
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars+1);
		
		//input char arry termninated with EOFchar for convenience
		chars[numChars] = EOFchar;
		tokens = new ArrayList<>();
		return tokens;
	}
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		return tokens.get(nextTokenPos);
	}

}
