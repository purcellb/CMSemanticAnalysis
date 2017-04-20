package ast;

import sem.SEMscope;
import parser.SrcLoc;
import parser.LEXSymbol;
import parser.Settings;
/****
* CSC 484 Compiler Theory
* 
* This class corresponds to an assignment node
* 
*****/



public class ASTassignment extends ASTnode {

	private ASTnode lvalue, rvalue;
	private SrcLoc source_location;
	
	public ASTassignment(ASTnode given_lvalue, ASTnode given_rvalue) 	  
	{ 	
		super(); 
		rvalue = given_rvalue;
		lvalue = given_lvalue;
		nodelabel = "assignment";
	}

	public ASTassignment(ASTnode given_lvalue, ASTnode given_rvalue, LEXSymbol given_symbol)
	{
		super();
		rvalue = given_rvalue;
		lvalue = given_lvalue;
		source_location = given_symbol.source_location;
		nodelabel = "assignment";;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {

		return lvalue.traverse(depth) + " = " + rvalue.traverse(0);
	}

	public void  buildscopes(SEMscope given_scope)
	{
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
