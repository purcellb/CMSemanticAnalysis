package ast;
import sem.SEMscope;
import sem.Descriptor;
import parser.LEXSymbol;
import sem.Variable_Descriptor;
import sem.Func_Descriptor;
import parser.Settings;
/****
* CSC 484 Compiler Theory
* 
* This class corresponds to an identifier node
* 
*****/

public class ASTident extends ASTnode {

	public ASTident(String name) 	  
	{ 	
		super(); 
		nodelabel = "ident";
		nodevalue = name;
	}

	public ASTident(LEXSymbol name)
	{
		super();
		nodelabel = "ident";
		nodevalue = name.getMy_text();
		source_location = name.source_location;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return tabulator(depth) + nodevalue.toString();
	}

	public ASTtype analyze()
	{
		//can not be null or empty string
		if (nodevalue!= null && nodevalue != ""){
			return new ASTtype("void");
		}
		return new ASTtype("error");
	}
}


