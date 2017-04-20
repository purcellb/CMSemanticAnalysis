package ast;
import parser.SrcLoc;
import sem.SEMscope;

/****
    * CSC 484 Compiler Theory
    * 
	* This is an abstract class that defines a node of an AST
	* it will be extended to a diverse collection of classes
	* that will be used to construct the syntax tree of a grammar.
	* 
	* The tabulator method returns a string with the number of tabs
	* equal to the current depth. This is useful for pretty printing
	* of source programs.
	*
	* 
*****/

public abstract class ASTnode {

	public SrcLoc source_location;

	protected SEMscope scope;

	public int line_number;
	
	public int column_number;
	
	public Object nodevalue;
	
	public String nodelabel;
	
	public abstract String toString();
	
	public abstract String traverse(int depth);

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
	}

	public ASTtype analyze()
	{
		return new ASTtype("void");
	}

	public boolean equals(ASTnode given_node)
	{
		return (given_node.nodelabel == nodelabel);
	}

	public String tabulator(int depth)
	{
		String my_string ="";
		for (int j=0;j<depth;j++) my_string += "\t";
		return my_string;
	}

}
