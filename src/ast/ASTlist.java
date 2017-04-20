package ast;
import sem.SEMscope;
import parser.SrcLoc;
import parser.Settings;
/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a generic pair node
* 
*****/

public class ASTlist extends ASTnode {

	public ASTnode prev, next;

	public ASTlist(ASTnode p, ASTnode n)
	{
		super();
		prev = p; next = n;
		source_location = new SrcLoc(0,0);
		nodelabel = "list";
	}

	public ASTlist(ASTnode p, ASTnode n, SrcLoc given_location)
	{
		super();
		prev = p; next = n;
		source_location = given_location;
		nodelabel = "list";
	}

	public String traverse(int depth) {
		if (prev == null) {
			return next.traverse(depth);
		}
		return prev.traverse(depth) + next.traverse(depth);
	}

	public String toString() { return traverse(0); }

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		if(prev.nodelabel.equals("block")){
			SEMscope next_scope = new SEMscope(null);
			next_scope.parent = scope;
			prev.buildscopes(next_scope);
		}else{
			prev.buildscopes(scope);
		}

		if(next.nodelabel.equals("block")){
			SEMscope next_scope = new SEMscope(null);
			next_scope.parent = scope;
			next.buildscopes(next_scope);
		}else{
			next.buildscopes(scope);
		}
	}


	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing " + nodelabel);
		}

		ASTtype error_type = new ASTtype("error");

		ASTtype prev_type = null;
		if(prev != null){
			prev_type = prev.analyze();
			if(prev_type.equals(error_type)){
				return error_type;
			}
		}
		ASTtype next_type;
		if(next != null){
			next_type = next.analyze();
			if(next_type.equals(error_type)){
				return error_type;
			}
		}

		return prev_type;
	}

	public boolean equals(ASTnode given_node)
	{
		if (nodelabel == given_node.nodelabel)
		{
			return ( this.prev.equals(((ASTlist)given_node).prev) && this.next.equals(((ASTlist)given_node).next) );
		}
		else return false;
	}
}
