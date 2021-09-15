package edu.ufl.cise.plpfa21.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExampleLexerTests implements PLPTokenKinds {

	IPLPLexer getLexer(String input) {
		return CompilerComponentFactory.getLexer(input);
	}
	
	@Test
	public void test6() throws LexicalException {
		String input = "&&";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
	}
	
	@Test
	public void test7() throws LexicalException {
		String input = "( < )";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.LPAREN);
			
			token = lexer.nextToken();
			kind = token.getKind();
			assertEquals(kind, Kind.LT);
			
			token = lexer.nextToken();
			kind = token.getKind();
			assertEquals(kind, Kind.RPAREN);
		}
	}
	
	@Test
	public void test8() throws LexicalException {
		String input = "+";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			int pos = token.getCharPositionInLine();
			int line = token.getLine();
			assertEquals(kind, Kind.PLUS);
			assertEquals(pos,0);
			assertEquals(line,1);
			
		}
	}
	
	@Test
	public void test9() throws LexicalException {
		String input = "+	-";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			int pos = token.getCharPositionInLine();
			int line = token.getLine();
			assertEquals(kind, Kind.PLUS);
			assertEquals(pos,0);
			assertEquals(line,1);
			
			token = lexer.nextToken();
			kind = token.getKind();
			pos = token.getCharPositionInLine();
			line = token.getLine();
			assertEquals(pos,2);
			assertEquals(line,1);
			
		}
	}
	
	@Test
	public void test10() throws LexicalException {
		String input = "1";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			int pos = token.getCharPositionInLine();
			int line = token.getLine();
			assertEquals(kind, Kind.INT_LITERAL);
			
			
		}
	}
	
	@Test
	public void test11() throws LexicalException {
		String input = "12";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			int pos = token.getCharPositionInLine();
			int line = token.getLine();
			assertEquals(kind, Kind.INT_LITERAL);
			assertEquals(pos, 0);
			
			
		}
	}
	
	@Test
	public void test12() throws LexicalException {
		String input = "99999999999999999999999999999999999999999999999999999999999999999999999";
		IPLPLexer lexer = getLexer(input);
		assertThrows(NumberFormatException.class, () -> {
			Integer.parseInt(input);
		});
	}

	@Test
	public void test0() throws LexicalException {
		String input = """

				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
//
//	@Test
//	public void test1() throws LexicalException {
//		String input = """
//				abc
//				  def
//				     ghi
//
//				""";
//		IPLPLexer lexer = getLexer(input);
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 0);
//			String text = token.getText();
//			assertEquals(text, "abc");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 2);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 2);
//			String text = token.getText();
//			assertEquals(text, "def");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 3);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 5);
//			String text = token.getText();
//			assertEquals(text, "ghi");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.EOF);
//		}
//	}
//
//	@Test
//	public void test2() throws LexicalException {
//		String input = """
//				a123 123a
//				""";
//		IPLPLexer lexer = getLexer(input);
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 0);
//			String text = token.getText();
//			assertEquals(text, "a123");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.INT_LITERAL);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 5);
//			String text = token.getText();
//			assertEquals(text, "123");
//			int val = token.getIntValue();
//			assertEquals(val, 123);
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 8);
//			String text = token.getText();
//			assertEquals(text, "a");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.EOF);
//		}
//	}
//
//	@Test
//	public void test3() throws LexicalException {
//		String input = """
//				= == ===
//				""";
//		IPLPLexer lexer = getLexer(input);
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.ASSIGN);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 0);
//			String text = token.getText();
//			assertEquals(text, "=");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.EQUALS);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 2);
//			String text = token.getText();
//			assertEquals(text, "==");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.EQUALS);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 5);
//			String text = token.getText();
//			assertEquals(text, "==");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.ASSIGN);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 7);
//			String text = token.getText();
//			assertEquals(text, "=");
//		}
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.EOF);
//		}
//	}
//
//	@Test
//	public void test4() throws LexicalException {
//		String input = """
//				a %
//				""";
//		IPLPLexer lexer = getLexer(input);
//		{
//			IPLPToken token = lexer.nextToken();
//			Kind kind = token.getKind();
//			assertEquals(kind, Kind.IDENTIFIER);
//			int line = token.getLine();
//			assertEquals(line, 1);
//			int charPositionInLine = token.getCharPositionInLine();
//			assertEquals(charPositionInLine, 0);
//			String text = token.getText();
//			assertEquals(text, "a");
//		}
//		assertThrows(LexicalException.class, () -> {
//			@SuppressWarnings("unused")
//			IPLPToken token = lexer.nextToken();
//		});
//	}
//
	@Test
	public void test5() throws LexicalException {
		String input = """
				99999999999999999999999999999999999999999999999999999999999999999999999
				""";
		IPLPLexer lexer = getLexer(input);
		assertThrows(NumberFormatException.class, () -> {
			Integer.parseInt(input);
		});
	}

}