package ast;
import parser.LEXSymbol;
import sem.Func_Descriptor;
import sem.SEMscope;
import parser.Settings;
/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a while statement node
* 
*****/


public class ASTwhile extends ASTnode {

	private ASTnode loop_condition, body;
	
	public ASTwhile(ASTnode given_loop_condition, ASTnode given_body) 	  
	{ 	
		super(); 
		loop_condition = given_loop_condition;
		body = given_body;
		nodelabel = "while";
		source_location = given_loop_condition.source_location;
	}

	public ASTwhile(ASTnode given_loop_condition, ASTnode given_body, LEXSymbol given_symbol)
	{
		super();
		loop_condition = given_loop_condition;
		body = given_body;
		nodelabel = "while";
		source_location = given_symbol.source_location;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return tabulator(depth) + "while ("
		       + loop_condition.traverse(0) + ") \n"
		       + body.traverse(depth);
		 
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;

		loop_condition.buildscopes(scope);

		//Even if there is only one statement without {}, we still create a
		// separate scope for it (mainly for return statement reachability sake).
		//
		//if(body.nodelabel.equals("block")){
			SEMscope next_scope = new SEMscope(null);
			next_scope.parent = scope;
			body.buildscopes(next_scope);
		//}else{
		//	body.buildscopes(scope);
		//}
	}

	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing: \n" + traverse(0));
		}

		ASTtype int_type = new ASTtype("int");
		ASTtype loop_type;
		ASTtype error_type = new ASTtype("error");

		loop_type = loop_condition.analyze();

		if (!(loop_type.equals(int_type)))
		{
			//scope.error_output("Done with analyzing " + loop_condition.traverse(0));
			scope.error_output("Condition of while loop is not integer at location " +
					loop_condition.source_location);
			return error_type;
		}
		else {
			//scope.error_output("Done with analyzing " + loop_condition.traverse(0));
			return body.analyze();
		}
	}
}