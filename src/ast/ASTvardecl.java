package ast;
import sem.SEMscope;
import parser.SrcLoc;
import parser.LEXSymbol;
import sem.Variable_Descriptor;
import sem.Descriptor;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to an local declaration node
* 
*****/

public class ASTvardecl extends ASTnode {

	private ASTtype type;
	private String name;
	private SrcLoc location;

	public ASTvardecl(ASTtype given_type, String given_name)
	{ 	
		super(); 
		type = given_type;
		name = given_name; 
		nodelabel = "vardecl";
	}

	public ASTvardecl(ASTtype given_type, LEXSymbol given_symbol)
	{
		super();
		type = given_type;
		name = given_symbol.getMy_text();
		location = given_symbol.source_location;
		nodelabel = "vardecl";
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return tabulator(depth) + type.traverse(0) + " " + name + ";\n";
	}

	public void  buildscopes(SEMscope given_scope)
	{
		//check inf another var of same name is declared in scope
			/**
			 *
			 * Write code here
			 *
			 */
	}

	public ASTtype analyze()
	{
			/**
			 *
			 * Write code here
			 *
			 */
			return new ASTtype("void");
	}
}


