package edu.ufl.cise.plpfa21.assignment1;

public class Token implements IPLPToken{
	
	Kind kind;
	public int pos, length, line, posInLine;
	
	Token(Kind kind, int pos, int length, int line, int posInLine)
	{
		this.kind = kind;
		this.pos = pos;
		this.length = length;
		this.line = line; 
		this.posInLine = posInLine;
	}

	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		return this.kind;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharPositionInLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
