package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;


public class Lexer implements IPLPLexer{

	private ArrayList<IPLPToken> tokens;
	private char[] chars;
	private int nextTokenPos = 0;
	static final char EOFchar = 0;
	
	//enums for dfa states
	private enum State{START, HAVE_EQUAL, DIGITS, IDENT_PART}
	
	public ArrayList<IPLPToken> SimpleScanner(String inputString)
	{
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars+1);
		
		int startingPos = 0;
		int pos = 0;
		
		State state = State.START;
		
		//input char arry termninated with EOFchar for convenience
		chars[numChars] = EOFchar;
		tokens = new ArrayList<>();
		
//		while(pos<chars.length)
//		{
//			char ch = chars[pos];
//			switch(state)
//			{
//				case START ->
//				{
//					
//				}
//				case HAVE_EQUAL ->
//				{
//					
//				}
//				case DIGITS ->
//				{
//					
//				}
//				case IDENT_PART ->
//				{
//					
//				}
//			}
//		}
		tokens.add(new Token(Kind.EOF,pos,0));
		
		
		return tokens;
	}
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		return tokens.get(nextTokenPos);
	}

}
