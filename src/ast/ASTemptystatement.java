package ast;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to an empty statement node
* 
*****/


public class ASTemptystatement extends ASTnode {

	public ASTemptystatement() 	  
	{ 	
		super(); 
		nodevalue = 0;
		nodelabel = "emptystatement";
	}
	
	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return ";";
	}
}