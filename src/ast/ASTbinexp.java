package ast;
import parser.sym;
import parser.LEXSymbol;
import sem.SEMscope;
import parser.Settings;

/****
* CSC 484 Compiler Theory
* 
* This class corresponds to an binary operation node
* it has a flexible constructor corresponding to a number 
* of different binary operations.
* 
*****/

import parser.sym;

public class ASTbinexp extends ASTpair {

	protected ASTtype resulting_type;
	protected ASTtype operand_type;
	protected ASTbinop op;

	public ASTbinexp(ASTnode e1, ASTbinop op, ASTnode e2)
	{ 	
		super(e1, e2);
		this.op = op;
		switch(op.getOp())
		{
			case sym.PLUS  :
				 nodevalue = "+";
				 break;
				 
			case sym.TIMES :
				 nodevalue = "*";
				 break;
			 
			case sym.MINUS :
				 nodevalue = "-";
				 break;
			
			case sym.DIVIDE :
				 nodevalue = "/";
				 break;

			case sym.EQ :
				 nodevalue = "==";
				 break;

			case sym.GT :
				 nodevalue = ">";
				 break;
			
			case sym.GE :
				 nodevalue = ">=";
				 break;
			
			case sym.NE :
				 nodevalue = "!=";
				 break;
			
			case sym.LT :
				 nodevalue = "<";
				 break;
			
			case sym.LE :
				 nodevalue = "<=";
				 break;

			case sym.COMMA :
				 nodevalue = ",";
				 break;
		}
		nodelabel = "binexp";
		operand_type = new ASTtype("int");
		resulting_type = new ASTtype("int");
		//System.out.println("op: " + op);
		//System.out.println("1 op.source_location:"+op.source_location);
		source_location = op.source_location;
	}

	public ASTbinexp(ASTnode e1, LEXSymbol given_symbol, ASTnode e2)
	{
		super(e1, given_symbol.source_location, e2);
		//System.out.println("op: " + given_symbol);
		//System.out.println("2 op.source_location:"+given_symbol.source_location);
		source_location = given_symbol.source_location;
		op = new ASTbinop(given_symbol);
		switch(((Integer)given_symbol.value))
		{
			case sym.PLUS  :
				nodevalue = "+";
				break;

			case sym.TIMES :
				nodevalue = "*";
				break;

			case sym.MINUS :
				nodevalue = "-";
				break;

			case sym.DIVIDE :
				nodevalue = "/";
				break;

			case sym.EQ :
				nodevalue = "==";
				break;

			case sym.GT :
				nodevalue = ">";
				break;

			case sym.GE :
				nodevalue = ">=";
				break;

			case sym.NE :
				nodevalue = "!=";
				break;

			case sym.LT :
				nodevalue = "<";
				break;

			case sym.LE :
				nodevalue = "<=";
				break;
		}
		nodelabel = "binexp";
		operand_type = new ASTtype("int");
		resulting_type = new ASTtype("int");
	}

	public String toString() { return traverse(0); }
	
	public String traverse(int depth) {

		return tabulator(depth) + left.traverse(0) + nodevalue + right.traverse(0);

	}

	public void  buildscopes(SEMscope given_scope)
	{
		/**
		 *
		 * Write code here
		 *
		 */
		scope = given_scope;
		left.buildscopes(scope);
		right.buildscopes(scope);
	}

	public ASTtype analyze()
	{
		if(Settings.print_debug_msg){
			System.out.println("Analyzing: \n" + traverse(0));
		}

		ASTtype left_type = left.analyze();
		ASTtype right_type = right.analyze();
		ASTtype error_type = new ASTtype("error");

		if ((left_type.equals(operand_type))
				&& (right_type.equals(operand_type)))
		{
			if(left_type.isArray || right_type.isArray){
				scope.error_output("Array must dereferenced first before they can be used in binary expresion. Location: " + source_location);
				return error_type;
			}
			return resulting_type;
		}
		else
		{
			scope.error_output("Type mismatch at location " + op.source_location +
					" Expected type: " + operand_type);
			return error_type;
		}
//		if (operand_type == null)
//		{
//			if ((left_type.equals(right_type)))
//				return left_type;
//			else
//				return error_type;
//		}
//		else
//		{
//			if ((left_type.equals(operand_type))
//					&& (right_type.equals(operand_type)))
//				return resulting_type;
//			else
//			{
//				if ((left_type.equals(error_type))||(right_type.equals(error_type)))
//				{
//					return error_type;
//				}
//				else
//				{
//					scope.error_output("Type mismatch at location " + source_location.toString() +
//							" expected type " + operand_type);
//					return error_type;
//				}
//			}
//		}
	}
}
