package ast;

import sem.Descriptor;
import sem.SEMscope;
import parser.LEXSymbol;
import sem.Func_Descriptor;
import java.util.Iterator;
import parser.Settings;
/****
* This class corresponds to an method call node
* 
*****/


public class ASTcall extends ASTnode {

	private ASTnode actuals;
	private String methodname;

	public ASTcall(String givenmethod, ASTnode givenactuals)
	{ 	
		super();
		methodname = givenmethod;
		actuals = givenactuals;
		nodelabel = "call";
	}

	public ASTcall(LEXSymbol givensymbol, ASTnode givenactuals)
	{
		super();
		methodname = givensymbol.getMy_text();
		actuals = givenactuals;
		source_location = givensymbol.source_location;
		nodelabel = "call";
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {
		return tabulator(depth) + methodname + "(" + actuals.traverse(0) + ")";
	}

	public void  buildscopes(SEMscope given_scope)
	{
		scope = given_scope;
		if(methodname.equals("main")){
			scope.error_output("Cannot call function main directly. Location: " + source_location);
		}
		actuals.buildscopes(scope);
	}

	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing: \n" + traverse(0));
		}

		Descriptor desc = scope.find(methodname);
		if(desc.isEmpty() || !desc.isFunc()){
			scope.error_output("Function \"" + methodname + "\" was not declared before it is called at location " + source_location);
		}else{
			ASTtype argstype = null;
			argstype = actuals.analyze();
//			if(actuals.nodelabel.equals("arglist")){
//				argstype = (ASTtupletype)actuals.analyze();
//			}else{
//				argstype = actuals.analyze();
//			}
			ASTfunctype functype = ((Func_Descriptor)desc).functypeWithFormals(argstype);
			if(functype == null) {
				scope.error_output("Function \"" + methodname + "\" with the given params " + actuals.traverse(0) +" was not found!");
				return new ASTtype("error");
			}else{
				if(Settings.print_debug_msg){
					if(nodelabel.equals("input")){
						System.out.println("input function returns \"" + functype.return_type.traverse(0) + "\"");
					}
				}
				return functype.return_type;
			}
		}

		if(Settings.print_debug_msg){
			if(nodelabel.equals("input")){
				System.out.println("input function returns error type");
			}
		}
		return new ASTtype("error");
	}
}