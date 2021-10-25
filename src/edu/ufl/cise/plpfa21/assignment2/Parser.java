package edu.ufl.cise.plpfa21.assignment2;


import edu.ufl.cise.plpfa21.assignment1.*;
import edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;


public class Parser implements IPLPParser{

	Lexer theLexer;
	IPLPToken theToken;
	
	@Override
	public IASTNode parse() throws SyntaxException {
		// TODO Auto-generated method stub
		
		program();
		matchEOF(theToken);
		
		throw new UnsupportedOperationException();
		
		//return null;
		
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
	
	//code from example in class
	//expr = term ((+|-) term)*
//	public void expression() throws SyntaxException
//	{
//		term();
//		while(isKind(theToken.getKind(),IPLPToken.Kind.PLUS) || isKind(theToken.getKind(),IPLPToken.Kind.MINUS))
//		{
//			if(isKind(theToken.getKind(),IPLPToken.Kind.PLUS))
//			{
//				match(IPLPToken.Kind.PLUS);
//			}
//			else if(isKind(theToken.getKind(),IPLPToken.Kind.MINUS))
//			{
//				match(IPLPToken.Kind.MINUS);
//			}
//			term();
//		}
//	}
	
	//term = factor((*|/) factor)*
//	public void term() throws SyntaxException
//	{
//		//factor();
//		while(isKind(theToken.getKind(),IPLPToken.Kind.TIMES) || isKind(theToken.getKind(),IPLPToken.Kind.DIV))
//		{
//			
//			
//			switch(theToken.getKind())
//			{
//				case TIMES ->
//				{
//					match(IPLPToken.Kind.TIMES);
//				}
//				case DIV ->
//				{
//					match(IPLPToken.Kind.DIV);
//				}
//				
//			}
//			//factor();
//		}
//	}
	
	//factor = intLiteral | (expression)
//	public void factor() throws SyntaxException
//	{
//		if(isKind(theToken.getKind(),IPLPToken.Kind.INT_LITERAL))
//		{
//			match(IPLPToken.Kind.INT_LITERAL);
//		}
//		else if(isKind(theToken.getKind(),IPLPToken.Kind.LPAREN))
//		{
//			match(IPLPToken.Kind.LPAREN);
//			expression();
//			match(IPLPToken.Kind.RPAREN);
//		}
//		else
//		{
//			throw new SyntaxException("Thrown in factor() at : ", theToken.getLine(), theToken.getCharPositionInLine());
//		}
//	}
	
	public void program() throws SyntaxException
	{
		while(!isKind(theToken.getKind(),IPLPToken.Kind.EOF))
		{
			declaration();
		}
	}
	
	public void block() throws SyntaxException	//while loop?
	{		
		while(!isKind(theToken.getKind(),IPLPToken.Kind.KW_END))
		{
			statement();
		}
	}
	
	public void statement() throws SyntaxException
	{
		//done
		if(isKind(theToken.getKind(),IPLPToken.Kind.KW_LET))
		{
			match(IPLPToken.Kind.KW_LET);
			nameDef();
			if(!isKind(theToken.getKind(),IPLPToken.Kind.SEMI))
			{
				match(IPLPToken.Kind.ASSIGN);
				
				expression();
			}
			match(IPLPToken.Kind.SEMI);
					
		}
		//done?
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_SWITCH))
		{
			match(IPLPToken.Kind.KW_SWITCH);
			expression();
			
			while(isKind(theToken.getKind(),IPLPToken.Kind.KW_CASE))				
			{
				match(IPLPToken.Kind.KW_CASE);
				expression();
				match(IPLPToken.Kind.COLON);
				block();
				
			}
			
			match(IPLPToken.Kind.KW_DEFAULT);
			block();
			match(IPLPToken.Kind.KW_END);
					
		}
		//done
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_IF))
		{
			match(IPLPToken.Kind.KW_IF);
			expression();
			match(IPLPToken.Kind.KW_DO);
			block();
			match(IPLPToken.Kind.KW_END);
					
		}
		//done
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_WHILE))
		{
			match(IPLPToken.Kind.KW_WHILE);
			expression();
			match(IPLPToken.Kind.KW_DO);
			block();
			match(IPLPToken.Kind.KW_END);
					
		}
		//done
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_RETURN))
		{
			match(IPLPToken.Kind.KW_RETURN);
			expression();
			match(IPLPToken.Kind.SEMI);
		}
		//done
		else
		{
			expression();
			if(!isKind(theToken.getKind(),IPLPToken.Kind.SEMI))
			{
				match(IPLPToken.Kind.ASSIGN);
				
				expression();
			}
			match(IPLPToken.Kind.SEMI);
		}
	}
	
	public void declaration() throws SyntaxException
	{
		
		if(isKind(theToken.getKind(),IPLPToken.Kind.KW_VAR))
		{
			match(IPLPToken.Kind.KW_VAR);
			nameDef();
			
			if(isKind(theToken.getKind(),IPLPToken.Kind.ASSIGN))
			{
				match(IPLPToken.Kind.ASSIGN);
				expression();
			}
			match(IPLPToken.Kind.SEMI);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_VAL))
		{
			match(IPLPToken.Kind.KW_VAL);
			nameDef();
			match(IPLPToken.Kind.ASSIGN);
			expression();
			match(IPLPToken.Kind.SEMI);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_FUN))	//NOT FINISHED
		{
			match(IPLPToken.Kind.KW_FUN);
			match(IPLPToken.Kind.IDENTIFIER);
			match(IPLPToken.Kind.LPAREN);
			
			
			
			//optional nameDef
			if(!isKind(theToken.getKind(),IPLPToken.Kind.RPAREN))
			{
				nameDef();
				while(!isKind(theToken.getKind(),IPLPToken.Kind.RPAREN))
				{
					match(IPLPToken.Kind.COMMA);
					nameDef();
				}
				
			}
			match(IPLPToken.Kind.RPAREN);
			
			//optional type
			if(isKind(theToken.getKind(),IPLPToken.Kind.COLON))
			{
				match(IPLPToken.Kind.COLON);
				type();
			}
			System.out.println(theToken.getKind());
			match(IPLPToken.Kind.KW_DO);
			block();
			match(IPLPToken.Kind.KW_END);
		}
	}
	
	public void nameDef() throws SyntaxException
	{
		match(IPLPToken.Kind.IDENTIFIER);
		
		if(isKind(theToken.getKind(),IPLPToken.Kind.COLON))
		{
			match(IPLPToken.Kind.COLON);
			type();
			
		}
		
	}
	
	public void type() throws SyntaxException
	{
		if(isKind(theToken.getKind(),IPLPToken.Kind.KW_INT))
		{
			match(IPLPToken.Kind.KW_INT);
					
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_STRING))
		{
			match(IPLPToken.Kind.KW_STRING);
					
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_BOOLEAN))
		{
			match(IPLPToken.Kind.KW_BOOLEAN);
					
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_LIST))
		{
			match(IPLPToken.Kind.KW_LIST);
			match(IPLPToken.Kind.LSQUARE);
			if(!isKind(theToken.getKind(),IPLPToken.Kind.RSQUARE))
			{
				type();
			}
			
			match(IPLPToken.Kind.RSQUARE);
								
		}
	}
	
	//expression = compExpression ((&& | ||) compExpression)*
	public void expression() throws SyntaxException
	{
		compExpr();
		while(isKind(theToken.getKind(),IPLPToken.Kind.AND) || isKind(theToken.getKind(),IPLPToken.Kind.OR))
		{
			if(isKind(theToken.getKind(),IPLPToken.Kind.AND))
			{
				match(IPLPToken.Kind.AND);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.OR))
			{
				match(IPLPToken.Kind.OR);
			}
			compExpr();
		}
	}
	
	//compExpr = addExpr ((<|>|==|!=) addExpr)*
	public void compExpr() throws SyntaxException
	{
		addExpr();
		while(isKind(theToken.getKind(),IPLPToken.Kind.LT) || isKind(theToken.getKind(),IPLPToken.Kind.GT) || isKind(theToken.getKind(),IPLPToken.Kind.EQUALS) || isKind(theToken.getKind(),IPLPToken.Kind.NOT_EQUALS))
		{
			if(isKind(theToken.getKind(),IPLPToken.Kind.LT))
			{
				match(IPLPToken.Kind.LT);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.GT))
			{
				match(IPLPToken.Kind.GT);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.EQUALS))
			{
				match(IPLPToken.Kind.EQUALS);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.NOT_EQUALS))
			{
				match(IPLPToken.Kind.NOT_EQUALS);
			}
			addExpr();
		}
	}
	
	//addExpr = multExpr ((+|-) multExpr)*
	public void addExpr() throws SyntaxException
	{
		multExpr();
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
			multExpr();
		}
	}
	
	//multExpr = unExpr ((*|/) unExpr)*
	public void multExpr() throws SyntaxException
	{
		unExpr();
		while(isKind(theToken.getKind(),IPLPToken.Kind.TIMES) || isKind(theToken.getKind(),IPLPToken.Kind.DIV))
		{
			if(isKind(theToken.getKind(),IPLPToken.Kind.TIMES))
			{
				match(IPLPToken.Kind.TIMES);
			}
			else if(isKind(theToken.getKind(),IPLPToken.Kind.TIMES))
			{
				match(IPLPToken.Kind.DIV);
			}
			unExpr();
		}
	}	
	
	//unExpr = (!|-)? primeExpr
	public void unExpr() throws SyntaxException
	{
		if(isKind(theToken.getKind(),IPLPToken.Kind.BANG))
		{
			match(IPLPToken.Kind.BANG);
			primeExpr();
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.MINUS))
		{
			match(IPLPToken.Kind.MINUS);
			primeExpr();
		}
		else 
		{		
			primeExpr();
		}
		
	}
	
	public void primeExpr() throws SyntaxException
	{
		if(isKind(theToken.getKind(),IPLPToken.Kind.KW_NIL))
		{
			match(IPLPToken.Kind.KW_NIL);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_TRUE))
		{
			match(IPLPToken.Kind.KW_TRUE);
			
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.KW_FALSE))
		{		
			match(IPLPToken.Kind.KW_FALSE);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.INT_LITERAL))
		{		
			match(IPLPToken.Kind.INT_LITERAL);
		}
		else if(isKind(theToken.getKind(),IPLPToken.Kind.STRING_LITERAL))
		{		
			match(IPLPToken.Kind.STRING_LITERAL);
		}    
		else if(isKind(theToken.getKind(),IPLPToken.Kind.LPAREN))
		{		
			match(IPLPToken.Kind.LPAREN);
			expression();
			match(IPLPToken.Kind.RPAREN);
		}
		//Identifier | Identifier  ( (Expression ( , Expression)* )? ) | Identifier [ Expression ] 
		else if(isKind(theToken.getKind(),IPLPToken.Kind.IDENTIFIER))
		{		
			//first case
			match(IPLPToken.Kind.IDENTIFIER);
			
			//second case
			if(isKind(theToken.getKind(),IPLPToken.Kind.LPAREN))
			{				
				match(IPLPToken.Kind.LPAREN);				
				//(Expression ( , Expression)* )?
				
				if(!isKind(theToken.getKind(),IPLPToken.Kind.RPAREN))
				{
					expression();
					while(!isKind(theToken.getKind(),IPLPToken.Kind.RPAREN))
					{
						match(IPLPToken.Kind.COMMA);
						expression();
					}
				}
				
			}
			//third case
			else if(isKind(theToken.getKind(),IPLPToken.Kind.LSQUARE))
			{
				match(IPLPToken.Kind.LSQUARE);
				expression();
				match(IPLPToken.Kind.RSQUARE);
			}
			
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
