package ast;
import parser.SrcLoc;
import sem.*;
import parser.LEXSymbol;

/****
* This class corresponds to a formal node
* 
*****/

public class ASTformal extends ASTnode {

	boolean isArray;
	private ASTtype type;
	private String name;
	private SrcLoc location;

	public ASTformal(ASTtype given_type, String given_name, boolean a)
	{ 	
		super(); 
		nodelabel = "formal";
		name = given_name;
		type = given_type;
		isArray = a;
	}

	public ASTformal(ASTtype given_type, LEXSymbol given_symbol, boolean a)
	{
		super();
		nodelabel = "formal";
		name = given_symbol.getMy_text();
		location = given_symbol.source_location;
		type = given_type;
		isArray = a;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		String result = type.traverse(0) + " " + name;
		if(isArray) {
			result += "[]";
		}
		return result;

	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		if(name.equals("main") || name.equals("input") || name.equals("output")){
			scope.error_output("Parameter name cannot be main, input, or output. Location: " + source_location);
		}

		if(isArray) {
			type.isArray = true;
			scope.insertFormal(new Formal_Descriptor(type, name, true, location));
		}
		else{
			scope.insertFormal(new Formal_Descriptor(type, name, false, location));
		}
	}

	public ASTtype analyze()
	{
		if (!type.equals(new ASTtype("int")))
		{
			scope.error_output("Parameter type must be integer at location " + source_location);
			return new ASTtype("error");
		}
		return type;
	}

	public boolean equals(ASTnode given_node)
	{
		if (this.nodelabel.equals(given_node.nodelabel))
		{
			return ( this.type.equals(((ASTformal)given_node).type) );
		}
		else return false;
	}
}
