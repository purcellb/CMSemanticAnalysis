package ast;
import parser.sym;
import parser.LEXSymbol;

/****
* This class corresponds to an identifier node
* 
*****/


public class ASTbinop extends ASTnode {

	private int op;

	public ASTbinop(int s)
	{ 	
		super(); 
		nodelabel = "binop";
		op = s;
	}

	public ASTbinop(LEXSymbol given_symbol)
	{
		super();
		if(given_symbol == null) {

			System.out.println("given_symbol is null");
		}
		nodelabel = "binop";
		op = ((Integer)given_symbol.value);
		source_location = given_symbol.source_location;
	}

	public int getOp(){
		return op;
	}

	public String toString() { return traverse(0); }

	public String traverse(int depth) {
		return sym.terminalNames[op];
	}

}


