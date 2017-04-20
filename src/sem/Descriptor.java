package sem;

import parser.SrcLoc;
import ast.ASTtype;

/****
    * CSC484 Compiler Theory
    * 
	* This is an abstract class that defines entries to the hashtables
	* it will be extended to a diverse collection of classes
	* that will be used to describe the types and semantics
	* of the various components of a C- program.
	* 
*****/

public abstract class Descriptor {
	
	public boolean isemptyflag;

	/***
	 * Method that returns whether the entry is empty or not
	 */
	public boolean isEmpty() { return isemptyflag; }
	public boolean isFunc() { return false; }
	public boolean isVariable() { return false; }
	public boolean isArray() { return false; }
	public boolean isParam() { return false; }

	public ASTtype type;
	
	public String name;
	
	public SrcLoc location;

}
