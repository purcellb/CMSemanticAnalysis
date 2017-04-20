package ast;
import parser.LEXSymbol;
import parser.sym;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a number node
* 
*****/

public class ASTnumber extends ASTnode {

	public ASTnumber(LEXSymbol number)
	{
		super();
		nodevalue = Integer.valueOf(number.getMy_text());
		nodelabel = "number";
	}

	public ASTnumber(Integer number)
	{ 	
		super(); 
		nodevalue = number;
		nodelabel = "number";
	}

	public ASTtype analyze()
	{
	//theres no check to do here i think?
		return new ASTtype("int");
	}

	public String toString() { return nodevalue.toString(); }

	public String traverse(int depth) {
		return nodevalue.toString();
	}
}