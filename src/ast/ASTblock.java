package ast;
import sem.SEMscope;
import parser.sym;

/****
* This class corresponds to a body of statements node
* 
*****/


public class ASTblock extends ASTnode {

	private ASTnode declarations;
	private ASTnode statements;

	public ASTblock(ASTnode decls, ASTnode stmts)
	{ 	
		super();
		declarations = decls;
		statements = stmts;
		nodelabel = "block";
	}
	
	public String toString() { return traverse(0); }

	public String traverse(int depth) {
		return 	tabulator(depth) + "{\n" +
				(declarations!=null?declarations.traverse(depth+1):"") +
				(statements!=null?statements.traverse(depth+1):"") +
		        tabulator(depth) + "}\n";
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		scope.in_block = true;

		declarations.buildscopes(given_scope);
		statements.buildscopes(given_scope);
	}

	public ASTtype analyze()
	{
		declarations.analyze();
		statements.analyze();
		ASTtype error_type = new ASTtype("error");
		//no need to use return_exist since if return_type is correct, that implies we have essentially a return value
		if (scope.return_type != null){
			if(!scope.return_type.equals(error_type)) {
				return scope.return_type;
			}else{
				return error_type;
			}
		}
		else{
			return new ASTtype(sym.VOID);
		}
	}
}