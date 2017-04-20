package ast;
import sem.SEMscope;
import parser.SrcLoc;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a generic pair node
* 
*****/

public class ASTpair extends ASTnode {

	public SrcLoc source_location;
	public ASTnode left, right;

	public ASTpair(ASTnode e1, ASTnode e2)
	{ 	
		super(); 
		left = e1; right = e2;
		source_location = new SrcLoc(0,0);
		nodelabel = "pair";
	}

	public ASTpair(ASTnode e1, SrcLoc given_location, ASTnode e2)
	{
		super();
		left = e1;  right = e2;
		source_location = given_location;
		nodelabel = "pair";
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {

		return left.traverse(depth) + right.traverse(depth);

	}
	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		left.buildscopes(scope);
		right.buildscopes(scope);
	}


	public ASTtype analyze()
	{
		ASTtype left_type = left.analyze();
		ASTtype right_type = right.analyze();
		ASTtype error_type = new ASTtype("error");

		if ((left_type.equals(error_type))||(right_type.equals(error_type)))
		{
			return error_type;
		}
		else
		{
			if (left_type.equals(right_type)) return left_type;
			else
			{
				scope.error_output("Type mismatch at location " + source_location.toString());
				return error_type;
			}
		}
	}

	public boolean equals(ASTnode given_node)
	{
		if (nodelabel == given_node.nodelabel)
		{
			return ( this.left.equals(((ASTpair)given_node).left) && this.right.equals(((ASTpair)given_node).right) );
		}
		else return false;
	}
}
