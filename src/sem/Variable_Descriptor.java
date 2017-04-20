package sem;

import parser.SrcLoc;
import ast.ASTtype;

/****
    * CSC484 Compiler Theory
    * 
	* Variable_Descriptor class -- used for C- variables.
	* 
	*
*****/

public class Variable_Descriptor extends Descriptor {

	public Variable_Descriptor(ASTtype given_type, String given_name, SrcLoc given_location)
	{
		isemptyflag=false;
		type = given_type;
		name = given_name;
		location = given_location;
	}


	public boolean isVariable() { return true; }
}
