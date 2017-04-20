package ast;

import sem.SEMscope;

/****
* CSC484 Compiler Theory
* 
* This class corresponds to a tuple type
* 
*****/


public class ASTtupletype extends ASTtype {

	public ASTtype child;
	public ASTtupletype rest;
	
	public ASTtupletype(ASTtype given_type) 	  
	{ 	
		super("tuple");
		nodelabel = "tuple";
		child = given_type;
		rest = null;
	}
	
	public ASTtupletype(ASTtype given_type, ASTtupletype given_rest_type) 	  
	{ 	
		super("tuple");
		nodelabel = "tuple";
		child = given_type;
		rest = given_rest_type;
	}
	
	
	public boolean equals(ASTtype given_type)
	{
		if (given_type.nodelabel=="tuple")
		{
			boolean flag = (((ASTtupletype)given_type).child.equals(this.child));
			if (rest!=null)
				return flag &&(((ASTtupletype)given_type).rest.equals(this.rest));
			else return (flag && ((ASTtupletype)given_type).rest==null);
		}
		else
			return false;
	}
	
	public String toString() { return child + "," + rest; }
}