package ast;
import parser.sym;
import parser.LEXSymbol;
import sem.SEMscope;

/****
* This class corresponds to type node
* its constructor accepts  the three different possible 
* types for C-
* 
*****/

public class ASTtype extends ASTnode {

	public boolean isArray = false;

	public ASTtype(int type) 	  
	{ 	
		super();

		nodelabel = "type";
		
		switch(type)
		{
			case sym.INT  :
				 nodevalue = "int";
				 break;
				 
			case sym.VOID :
				 nodevalue = "void";
				 break;
		}
	}

	public ASTtype(String giventype) 	  
	{ 	
		super();
		nodelabel = "type";
		nodevalue = giventype;
	}

	public ASTtype(LEXSymbol given_symbol)
	{
		super();
		if(given_symbol==null){
			System.out.println("given_symbol is null");
		}
		this.nodelabel = "type";
		nodevalue = given_symbol.getMy_text();
		source_location = given_symbol.source_location;
	}

	public boolean equals(ASTtype given_type)
	{
		return (this.nodevalue.equals(given_type.nodevalue)) && (this.isArray == given_type.isArray);
	}

	public String toString() { return nodevalue.toString(); }
	
	public String traverse(int depth) {
		return nodevalue.toString();
	}
}


