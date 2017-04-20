package ast;

import parser.SrcLoc;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a function type
* 
*****/


public class ASTfunctype extends ASTtype {

	
	public ASTtype return_type;
	public ASTtype formals_type;
		
	public ASTfunctype(ASTtype given_formals_type, ASTtype given_return_type, SrcLoc given_location)
	{ 	
		super("functype");
		nodelabel = "functype";
		return_type = given_return_type;
		formals_type = given_formals_type;
		source_location = given_location;
	}
	
	public boolean equals(ASTtype given_type)
	{
		if(given_type.nodelabel.equals("functype")){
			return this.equals((ASTfunctype)given_type);
		}else
			return false;
	}

	public boolean equals(ASTfunctype given_type)
	{
		return ((given_type).return_type.equals(this.return_type))
			&&((given_type).formals_type.equals(this.formals_type));
	}
	
	public String toString() { return return_type+"  <- (" + formals_type + ")";}
}