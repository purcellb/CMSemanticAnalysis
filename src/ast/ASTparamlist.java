package ast;
import sem.SEMscope;

/****
* This class corresponds to a generic pair node
* 
*****/

public class ASTparamlist extends ASTlist {

	public ASTparamlist(ASTnode p, ASTnode n)
	{
		super(p, n);
		nodelabel = "paramlist";
	}

	public String toString() { return traverse(0); }

	public String traverse(int depth) {
		if (prev == null) {
			return next.traverse(depth);
		}
		return prev.traverse(depth) + ", " + next.traverse(depth);
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		prev.buildscopes(scope);
		next.buildscopes(scope);
	}

	public ASTtype analyze()
	{
		ASTtype prev_type = prev.analyze();
		ASTtype next_type = next.analyze();
		ASTtype error_type = new ASTtype("error");

		if ((prev_type.equals(error_type))||(next_type.equals(error_type)))
		{
			return error_type;
		}
		else
		{

			if (!(next_type.nodelabel.equals("tuple")))
				return new ASTtupletype(prev_type, new ASTtupletype(next_type));
			else
				return new ASTtupletype(prev_type, (ASTtupletype) next_type);
		}
	}

	public boolean equals(ASTnode given_node)
	{
		if (nodelabel == given_node.nodelabel)
		{
			return ( this.prev.equals(((ASTparamlist)given_node).prev) && this.next.equals(((ASTparamlist)given_node).next) );
		}
		else return false;
	}
}