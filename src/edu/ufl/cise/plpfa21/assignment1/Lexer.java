package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;


public class Lexer implements IPLPLexer {

	private ArrayList<IPLPToken> tokens;
	private char[] chars; //holds characters with 0 at the end
	private int nextTokenPos = 0;
	static final char EOFchar = 0;
	
	public HashMap<String, Kind> keywords = new HashMap<String, Kind>();
	
	//enums for dfa states
	private enum State{START, HAVE_EQUAL, HAVE_AND, HAVE_OR, HAVE_NOT, INTLITERAL, IDENT_PART}
	
	
	public ArrayList<IPLPToken> SimpleScanner(String inputString) throws LexicalException
	{
		//creating char array from inputString
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars+1);
		
		//indexing setup
		int pos = 0;
		int startPos = 0;
		int line = 1;
		int posInLine = 1;
		
		State state = State.START;
		
		//input char array terminated with EOFchar for convenience
		chars[numChars] = EOFchar;
		tokens = new ArrayList<>();
		
		String digits = "";
		String inProgIdent = "";
		
		//add keywords to HashMap for ident check
		keywords.put("FUN",Kind.KW_FUN);
		keywords.put("DO",Kind.KW_DO);
		keywords.put("END",Kind.KW_END);
		keywords.put("LET",Kind.KW_LET);
		keywords.put("SWITCH",Kind.KW_SWITCH);
		keywords.put("CASE",Kind.KW_CASE);
		keywords.put("DEFAULT",Kind.KW_DEFAULT);
		keywords.put("IF",Kind.KW_IF);
		keywords.put("ELSE",Kind.KW_ELSE);
		keywords.put("WHILE",Kind.KW_WHILE);
		keywords.put("RETURN",Kind.KW_RETURN);
		keywords.put("LIST",Kind.KW_LIST);
		keywords.put("VAR",Kind.KW_VAR);
		keywords.put("VAL",Kind.KW_VAL);
		keywords.put("NIL",Kind.KW_NIL);
		keywords.put("TRUE",Kind.KW_TRUE);
		keywords.put("FALSE",Kind.KW_FALSE);
		keywords.put("INT",Kind.KW_INT);
		keywords.put("STRING",Kind.KW_STRING);
		keywords.put("FLOAT",Kind.KW_FLOAT);
		keywords.put("BOOLEAN",Kind.KW_BOOLEAN);
		
		//loop that scans through every character and builds the token array
		while(pos<chars.length)
		{
			//System.out.println(pos);
			//System.out.println("length: " + chars.length);
			char ch = chars[pos];
			switch(state)
			{
				case START ->
				{
					startPos = pos;
					switch(ch)
					{
						//need to handle other kinds of whitespace
						case ' ', '\t' ->
						{
							pos++;
							posInLine++;
						}
						case '\n' ->
						{
							pos++;
							line++;
							posInLine = 1;
						}
						case '\r' ->
						{
							pos++;
							posInLine = 1;
						}
						case '=' ->
						{
							
							state = State.HAVE_EQUAL;
							//index
							pos++;
							posInLine++;
						}
						case ',' ->
						{
							//add token
							tokens.add(new Token(Kind.COMMA,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case ';' ->
						{
							//add token
							tokens.add(new Token(Kind.SEMI,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case ':' ->
						{
							//add token
							tokens.add(new Token(Kind.COLON,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '(' ->
						{
							//add token
							tokens.add(new Token(Kind.LPAREN,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case ')' ->
						{
							//add token
							tokens.add(new Token(Kind.RPAREN,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '[' ->
						{
							//add token
							tokens.add(new Token(Kind.LSQUARE,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case ']' ->
						{
							//add token
							tokens.add(new Token(Kind.RSQUARE,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						//not correct, how to read double symbols?
						case '&' ->
						{
							//change state for double symbols
							state = State.HAVE_AND;
							//index
							pos++;
							posInLine++;
						}
						case '|' ->
						{
							//change state for double symbols
							state = State.HAVE_OR;
							//index
							pos++;
							posInLine++;
						}
						case '<' ->
						{
							//add token
							tokens.add(new Token(Kind.LT,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '>' ->
						{
							//add token
							tokens.add(new Token(Kind.GT,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '!' ->
						{
							//add token
							tokens.add(new Token(Kind.BANG,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '+' ->
						{
							//add token
							tokens.add(new Token(Kind.PLUS,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '-' ->
						{
							//add token
							tokens.add(new Token(Kind.MINUS,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '*' ->
						{
							//add token
							tokens.add(new Token(Kind.TIMES,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '/' ->
						{
							//add token
							tokens.add(new Token(Kind.DIV,pos,1,line,posInLine));
							//index
							pos++;
							posInLine++;
						}
						case '0','1','2','3','4','5','6','7','8','9' ->
						{
							state = State.INTLITERAL;
							digits += ch;
							pos++;
							posInLine++;
						}
						default -> 
						{
							if(Character.isJavaIdentifierStart(ch))
							{
								inProgIdent += ch;
								pos++;
								posInLine++;
								state = State.IDENT_PART;
								
							}
							else	
							{
								if(ch != EOFchar)
								{
									//handle error
								}
								pos++;
								posInLine++;
							}
						}
					}
				}
				case HAVE_EQUAL ->
				{
					if(ch == '=')
					{
						tokens.add(new Token(Kind.ASSIGN,pos-1,2,line,posInLine));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						tokens.add(new Token(Kind.EQUALS,pos-1,1,line,posInLine));
						
						state = State.START;
					}
				}
				case HAVE_AND ->
				{
					if(ch == '&')
					{
						tokens.add(new Token(Kind.AND,pos-1,2,line,posInLine));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						//throw error
						throw new LexicalException("Unexpected token",line,pos);
					}
				}
				case HAVE_OR ->
				{
					if(ch == '|')
					{
						tokens.add(new Token(Kind.OR,pos-1,2,line,posInLine));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						//throw error
						throw new LexicalException("Unexpected token",line,pos);
					}
				}
				case HAVE_NOT ->
				{
					if(ch == '=')
					{
						tokens.add(new Token(Kind.NOT_EQUALS,pos-1,2,line,posInLine));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						tokens.add(new Token(Kind.BANG, pos-1,1,line,posInLine));
						state = State.START;
					}
				}
				case INTLITERAL ->
				{					
					if(Character.isDigit(ch))
					{
						digits += ch;
						//System.out.println("char: " + Character.toString(ch));
						pos++;
						posInLine++;
					}
					else
					{
						//System.out.println("digits: " + digits);						
						try
						{
							Integer.parseInt(digits);
							tokens.add(new Token(Kind.INT_LITERAL, startPos,pos - startPos,line,posInLine));
						}
						catch(NumberFormatException e)
						{
							throw new LexicalException("Number too large", line, pos);
						}
						
						state = State.START;
						digits = "";
					}
				}
				case IDENT_PART ->
				{
					if(Character.isJavaIdentifierPart(ch) && ch != EOFchar)
					{
						inProgIdent += ch;
						pos++;
						posInLine++;
						//System.out.println("got here");
						//System.out.println("ident: " + inProgIdent);
					}
					else
					{
						//System.out.println("ident: " + inProgIdent);
						//check if inProgIdent is a reserved word
						if(keywords.containsKey(inProgIdent))
						{
							//System.out.println("got here");
							tokens.add(new Token(keywords.get(inProgIdent),startPos, pos- startPos, line, posInLine ));
						}
						else
						{
							tokens.add(new Token(Kind.IDENTIFIER,startPos, pos- startPos, line, posInLine ));
						}
						state = State.START;
						inProgIdent = "";
					}
				}
			}
		}
		tokens.add(new Token(Kind.EOF,pos,0,line,posInLine));
		
		
		return tokens;
	}
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		nextTokenPos++;
		return tokens.get(nextTokenPos-1);
	}

}
