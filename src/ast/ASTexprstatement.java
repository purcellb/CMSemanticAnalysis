package ast;
import sem.SEMscope;
import parser.Settings;

/****
* CSC 484 Compiler Theory
* 
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