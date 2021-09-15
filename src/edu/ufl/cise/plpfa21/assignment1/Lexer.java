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
	
	
	public Lexer(String inputString) 
	{
		//creating char array from inputString
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars+1);
		
		//indexing setup
		int pos = 0;
		int startPos = 0;
		int line = 1;
		int posInLine = 1;
		int startPosInLine = 1;
		
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
					startPosInLine = posInLine;
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
							//pos = 0;
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
							tokens.add(new Token(Kind.COMMA,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case ';' ->
						{
							//add token
							tokens.add(new Token(Kind.SEMI,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case ':' ->
						{
							//add token
							tokens.add(new Token(Kind.COLON,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '(' ->
						{
							//add token
							tokens.add(new Token(Kind.LPAREN,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case ')' ->
						{
							//add token
							tokens.add(new Token(Kind.RPAREN,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '[' ->
						{
							//add token
							tokens.add(new Token(Kind.LSQUARE,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case ']' ->
						{
							//add token
							tokens.add(new Token(Kind.RSQUARE,startPos,1,line,startPosInLine, inputString));
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
							tokens.add(new Token(Kind.LT,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '>' ->
						{
							//add token
							tokens.add(new Token(Kind.GT,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '!' ->
						{
							state = State.HAVE_NOT;							
							//index
							pos++;
							posInLine++;
						}
						case '+' ->
						{
							//add token
							tokens.add(new Token(Kind.PLUS,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '-' ->
						{
							//add token
							tokens.add(new Token(Kind.MINUS,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '*' ->
						{
							//add token
							tokens.add(new Token(Kind.TIMES,startPos,1,line,startPosInLine, inputString));
							//index
							pos++;
							posInLine++;
						}
						case '/' ->
						{
							//add token
							tokens.add(new Token(Kind.DIV,startPos,1,line,startPosInLine, inputString));
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
									tokens.add(new Token(Kind.ERROR,startPos,pos - startPos,line,startPosInLine, inputString));
									state = State.START;
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
						pos++;
						posInLine++;
						tokens.add(new Token(Kind.EQUALS,startPos,pos - startPos,line,startPosInLine, inputString));
						
						state = State.START;
					}
					else
					{
						tokens.add(new Token(Kind.ASSIGN,startPos,pos - startPos,line,startPosInLine, inputString));
						
						state = State.START;
					}
				}
				case HAVE_AND ->
				{
					if(ch == '&')
					{
						tokens.add(new Token(Kind.AND,startPos,pos - startPos,line,startPosInLine, inputString));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						//throw error
						//throw new LexicalException("Unexpected token",line,pos);
						tokens.add(new Token(Kind.ERROR,startPos,pos - startPos,line,startPosInLine, inputString));
						pos++;
						posInLine++;
						state = State.START;
					}
					
				}
				case HAVE_OR ->
				{
					if(ch == '|')
					{
						tokens.add(new Token(Kind.OR,startPos,pos - startPos,line,startPosInLine, inputString));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						//throw error
						//throw new LexicalException("Unexpected token",line,pos);
						tokens.add(new Token(Kind.ERROR,startPos,pos - startPos,line,startPosInLine, inputString));
						pos++;
						posInLine++;
						state = State.START;
					}
				}
				case HAVE_NOT ->
				{
					if(ch == '=')
					{
						tokens.add(new Token(Kind.NOT_EQUALS,startPos,pos - startPos,line,startPosInLine, inputString));
						pos++;
						posInLine++;
						state = State.START;
					}
					else
					{
						tokens.add(new Token(Kind.BANG, startPos,pos - startPos,line,startPosInLine, inputString));
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
							tokens.add(new Token(Kind.INT_LITERAL, startPos,pos - startPos,line,startPosInLine, inputString));
						}
						catch(NumberFormatException e)
						{
							//throw error
							//throw new LexicalException("Unexpected token",line,pos);
							tokens.add(new Token(Kind.ERROR,startPos,pos - startPos,line,startPosInLine, inputString));
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
							tokens.add(new Token(keywords.get(inProgIdent),startPos, pos- startPos, line, startPosInLine , inputString));
						}
						else
						{
							tokens.add(new Token(Kind.IDENTIFIER,startPos, pos- startPos, line, startPosInLine, inputString ));
						}
						state = State.START;
						inProgIdent = "";
					}
				}
			}
		}
		tokens.add(new Token(Kind.EOF,pos,0,line,startPosInLine, inputString));
		
		
		
	}
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		nextTokenPos++;
		
		if(tokens.get(nextTokenPos-1).getKind() == Kind.ERROR)
		{
			throw new LexicalException("Lexical Error",tokens.get(nextTokenPos-1).getLine(),tokens.get(nextTokenPos-1).getCharPositionInLine() );
		}
		return tokens.get(nextTokenPos-1);
	}

}
