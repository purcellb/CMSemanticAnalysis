package ast;
import sem.SEMscope;
import sem.Func_Descriptor;
import parser.LEXSymbol;
import parser.sym;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to a return statement node
* 
*****/


public class ASTreturn extends ASTnode {

	private ASTnode expression;
	
	public ASTreturn(ASTnode given_expression) 	  
	{ 	
		super(); 
		expression = given_expression;  
		nodelabel = "return";
	}

	public ASTreturn(ASTnode given_expression, LEXSymbol given_symbol)
	{
		super();
		expression = given_expression;
		nodelabel = "return";
		source_location = given_symbol.source_location;
	}

	public ASTreturn(LEXSymbol given_symbol)
	{
		super();
		nodelabel = "return";
		source_location = given_symbol.source_location;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		if(expression != null) {
			return tabulator(depth) + "return " + expression.traverse(0) +";\n";
		}
		return tabulator(depth) + "return ;\n";
	}

	public void  buildscopes(SEMscope given_scope)
	{
		/**
		 *
		 * Write code here
		 *------
		 * I think i understand what this needs to do. In code, return takes the program OUT of the scope its in
		 * and this should do the same thing for the semantic analysis.
		 * However, i dont understand how to do it in this SemanticAnaylzer code.
		 *
		 */
	}


	public ASTtype analyze()
	{
		ASTtype rtype;
		if (expression != null){
			rtype = expression.analyze();
		}else{
			rtype = new ASTtype(sym.VOID);
		}

		if (!scope.return_exist()) {
			scope.return_exist = true;
			scope.return_type = rtype;
		}
		else{
			scope.error_output("Multiple return statements in current scope or cascade scopes!");
			return new ASTtype("error");
		}
		return rtype;
	}
}