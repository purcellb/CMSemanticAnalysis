package ast;
import sem.SEMscope;
import parser.Settings;

/****
* This class corresponds to an expressions statement node
* 
*****/

public class ASTexprstatement extends ASTnode {

	private ASTnode expression;
	
	public ASTexprstatement(ASTnode given_expression) 	  
	{ 	
		super(); 
		expression = given_expression;
		nodelabel = "exprstatement";
	}
	
	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {

		return expression.traverse(depth) + ";\n";

	}

	public void  buildscopes(SEMscope given_scope)
	{
		/**
		 *
		 * Write code here
		 *--------
		 * I dont understand what scope changes would need to be done here
		 * as far as i can tell from the semantics of C- in the text and the implementation of this class there is no scope
		 * to manage here
		 *
		 * an expression-stmt is an optional expression followed by a semicolon
		 *
		 * I assume im misunderstanding something here.
		 *
		 */
	}

	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing: \n" + traverse(0));
		}
		return expression.analyze();
	}
}