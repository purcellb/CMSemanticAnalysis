package sem;

import parser.SrcLoc;
import ast.ASTtype;

/****
    * CSC484 Compiler Theory
    * 
	* Array_Descriptor class -- used for C- array variables.
	* 
	*
*****/

public class Array_Descriptor extends Descriptor {

	int arraySize = 0;

	public Array_Descriptor(ASTtype given_type, String given_name, int size, SrcLoc given_location)
	{
		isemptyflag=false;
		type = given_type;
		name = given_name;
		arraySize = size;
		location = given_location;
	}

	public boolean isArray() { return true; }

	public int getSize(){ return arraySize; }

}
