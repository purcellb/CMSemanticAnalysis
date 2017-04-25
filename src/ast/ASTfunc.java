package ast;
import sem.SEMscope;
import parser.LEXSymbol;
import parser.sym;
import sem.Func_Descriptor;

/****
* This class corresponds to a method declaration node
* 
*****/

public class ASTfunc extends ASTnode {

	private ASTtype type;
	private ASTnode formals;
	private ASTtype formalsType;
	private ASTnode body;
	private LEXSymbol name_symbol;
	private String name;

	public ASTfunc(ASTtype given_type, LEXSymbol given_symbol, ASTnode given_formals, ASTnode given_body)
	{
		super();
		type = given_type;
		name_symbol = given_symbol;
		name = name_symbol.getMy_text();
		nodelabel = "func";
		formals = given_formals;
		body = given_body;
		if(formals != null){
			if(formals.nodelabel.equals("paramlist")){
				formalsType = ((ASTparamlist)formals).analyze();
			}else{
				formalsType = formals.analyze();
			}
		}
	}

	public String toString() { return type.traverse(0) + " " + name + "(" + formals.traverse(0) + ")"; }
	
	public String traverse(int depth) {
		return tabulator(depth) + type.traverse(0) + " " + name + "(" + formals.traverse(0) + ")" + "\n"
				+ body.traverse(depth) + "\n"
				+ tabulator(depth);
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		if(scope.main_defined()){
			scope.error_output("main must be the last declaration!");
		}

		if(scope.in_block){
			scope.error_output("Nested function definition is not allowed! Location: " + name_symbol.source_location);
		}

		Func_Descriptor new_func = new Func_Descriptor(name);
  		ASTtype void_type = new ASTtype("void");
		if (name.equals("main")){
			if(!formalsType.nodelabel.equals("type") || !formalsType.equals(void_type)){
				scope.error_output("main function's parameter must be void");
			}else if(!type.equals(void_type)){
				scope.error_output("main function's return type must be void");
			}
			scope.main_found = true;
		}

		if(new_func.overload(formalsType, type, name_symbol.source_location)) {
			scope.insertFunc(new_func);
		}

		SEMscope next_scope = new SEMscope(null);
		next_scope.parent = scope;
		formals.buildscopes(next_scope);
		body.buildscopes(next_scope);

	}

	public ASTtype analyze()
	{
		//Analysis on formals has been done in the constructor

		ASTtype error_type = new ASTtype("error");
		if (formalsType.equals(error_type)){
			return error_type;
		}

		ASTtype bodyReturnType = body.analyze();
		//scope.error_output("bodyReturnType : "+bodyReturnType);
		//scope.error_output("type : "+type);
		if(bodyReturnType == null || bodyReturnType.equals(error_type)) {
			if(!type.equals(new ASTtype(sym.VOID))){
				scope.error_output("Function may not return required type!");
			}else{
				return new ASTtype(sym.VOID);
			}
		}else{
			if(bodyReturnType.equals(type)){
				return type;
			}else{
				scope.error_output("Function "+name+"("+name_symbol.source_location+") does not return the required type!");
			}
		}
		return error_type;
	}
}


