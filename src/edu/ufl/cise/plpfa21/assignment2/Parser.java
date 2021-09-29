package edu.ufl.cise.plpfa21.assignment2;


import edu.ufl.cise.plpfa21.assignment1.*;


public class Parser implements IPLPParser{

	Lexer theLexer;
	IPLPToken theToken;
	
	@Override
	public void parse() throws SyntaxException {
		// TODO Auto-generated method stub
		program();
		matchEOF(theToken);
		
	}
	
	public Parser(Lexer l) 
	{
		theLexer = l;
		
		try {
			theToken = l.nextToken();
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void program() throws SyntaxException
	{
		while(isKind(theToken.getKind(),IPLPToken.Kind.KW_VAR))
		{
			match(IPLPToken.Kind.KW_VAR);
			nameDef();
			match(IPLPToken.Kind.SEMI);
			
		}
	}
	
	public void block() throws SyntaxException
	{
		
	}
	
	public void statement() throws SyntaxException
	{
		
	}
	
	public void declaration() throws SyntaxException
	{
		
	}
	
	public void nameDef() throws SyntaxException
	{
		while(isKind(theToken.getKind(),IPLPToken.Kind.IDENTIFIER))
		{
			match(IPLPToken.Kind.IDENTIFIER);
			
		}
	}
	
	//code from example in class
	//expr = term ((+|-) term)*
	public void expression() throws SyntaxException
	{
		term();
		while(isKind(theToken.getKind(),IPLPToken.Kind.PLUS) || isKind(theToken.getKind(),IPLPToken.Kind.MINUS))
		{
			if(isKind(theToken.getKind(),IPLPToken.Kind.PLUS))
			{
				match(IPLPToken.Kind.PLUS);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.MINUS))
			{
				match(IPLPToken.Kind.MINUS);
			}
			term();
		}
	}
	
	//term = factor((*|/) factor)*
	public void term() throws SyntaxException
	{
		//factor();
		while(isKind(theToken.getKind(),IPLPToken.Kind.TIMES) || isKind(theToken.getKind(),IPLPToken.Kind.DIV))
		{
			
			
			switch(theToken.getKind())
			{
				case TIMES ->
				{
					match(IPLPToken.Kind.TIMES);
				}
				case DIV ->
				{
					match(IPLPToken.Kind.DIV);
				}
				
			}
			//factor();
		}
	}
	
	//factor = intLiteral | (expression)
	public void factor() throws SyntaxException
	{
		if(isKind(theToken.getKind(),IPLPToken.Kind.INT_LITERAL))
		{
			match(IPLPToken.Kind.INT_LITERAL);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.LPAREN))
		{
			match(IPLPToken.Kind.LPAREN);
			expression();
			match(IPLPToken.Kind.RPAREN);
		}
		else
		{
			throw new SyntaxException("Thrown in factor() at : ", theToken.getLine(), theToken.getCharPositionInLine());
		}
	}
	
	//checks if theToken has same kind as given token
	public void match(IPLPToken.Kind k) throws SyntaxException 
	{
		if(theToken.getKind() == k)
		{
			consumeToken();
		}
		else
		{
			throw new SyntaxException("Token matching error", theToken.getLine(), theToken.getCharPositionInLine());
		}
	}
	
	public void matchEOF(IPLPToken t) throws SyntaxException 
	{
		if(t.getKind() == IPLPToken.Kind.EOF)
		{
			return;
		}
		else
		{
			throw new SyntaxException("Token matching error", t.getLine(), t.getCharPositionInLine());
		}
	}
	
	public IPLPToken consumeToken() 
	{
		try {
			theToken = theLexer.nextToken();
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theToken;
	}
	
	public boolean isKind(IPLPToken.Kind k1, IPLPToken.Kind k2)	
	{
		if(k1 == k2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}
