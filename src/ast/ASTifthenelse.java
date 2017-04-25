package ast;
import sem.SEMscope;
import parser.LEXSymbol;
import parser.sym;
import parser.Settings;
/****
* This class corresponds to an ifthenelse statement node
* 
*****/


public class ASTifthenelse extends ASTnode {

	private ASTnode condition, statementif, statementelse;
	
	public ASTifthenelse(ASTnode given_condition, ASTnode given_statementif, ASTnode given_statementelse)
	{ 	
		super(); 
		statementif = given_statementif;  
		statementelse = given_statementelse;
		condition = given_condition;
		nodelabel = "ifthenelse";
	}

	public ASTifthenelse(ASTnode given_condition, ASTnode given_statementif, ASTnode given_statementelse, LEXSymbol given_symbol)
	{
		super();
		statementif = given_statementif;
		statementelse = given_statementelse;
		condition = given_condition;
		nodelabel = "ifthenelse";
		source_location = given_symbol.source_location;
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return  tabulator(depth) + "if(" + condition.traverse(0) + ")" + "\n"+
				statementif.traverse(depth+1) + "\n" +
				tabulator(depth) + "else\n" +
				statementelse.traverse(depth+1);
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		condition.buildscopes(scope);
		//We always view the statementif and statementelse as within a new block even if they are just a single
		//regular statement instead of compound statement. Namely, we need to create a new scope.
		//if(statementif.nodelabel.equals("block")){
			SEMscope next_scope1 = new SEMscope(null);
			next_scope1.parent = scope;
			statementif.buildscopes(next_scope1);
		//}else{
		//	statementif.buildscopes(scope);
		//}

		//if(statementelse.nodelabel.equals("block")){
			SEMscope next_scope2 = new SEMscope(null);
			next_scope2.parent = scope;
			statementelse.buildscopes(next_scope2);
		//}else{
		//	statementelse.buildscopes(scope);
		//}
	}

	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing: \n" + traverse(0));
		}

		ASTtype ct = condition.analyze();
		if(ct == null || !ct.equals(new ASTtype(sym.INT))){
			scope.error_output("Condition expression must be integer...");
		}
		ASTtype error_type = new ASTtype("error");

		ASTtype st = statementif.analyze();

		ASTtype et = statementelse.analyze();

		//scope.error_output("statementif.scope.return_type: "+statementif.scope.return_type);
		//scope.error_output("statementelse.scope.return_type: " + statementelse.scope.return_type);

		if((statementif.scope.return_type != null && !statementif.scope.return_type.equals(error_type)) &&
				(statementelse.scope.return_type != null && !statementelse.scope.return_type.equals(error_type))){
			if(statementif.scope.return_type.equals(statementelse.scope.return_type)){
				statementif.scope.parent.return_type = statementif.scope.return_type;
			}else{
				scope.error_output("Returning different types in if-then-else statement. Location: " + source_location);
			}
		}

		if(ct.equals(error_type) || (st.equals(error_type) || et.equals(error_type))){
			return error_type;
		}else{
			//scope.error_output("Returning type: "+st);
			return st;
		}
	}
}