package ast;
import parser.LEXSymbol;
/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a generic leaf node
* 
*****/

public class ASTleaf extends ASTnode {

	public ASTleaf(LEXSymbol value)
	{
		super();
		nodevalue = value.getMy_text();
		nodelabel = "leaf";
	}

	public ASTleaf(Integer value)
	{ 	
		super(); 
		nodevalue = value.toString();
		nodelabel = "leaf";
	}
	
	public ASTleaf() 	  
	{ 	
		super(); 
		nodelabel = "leaf";
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		if(nodevalue != null)
			return tabulator(depth) + nodevalue.toString();
		else
			return tabulator(depth);
	}
}


