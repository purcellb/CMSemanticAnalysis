package ast;

import parser.LEXSymbol;
import parser.Settings;
import parser.sym;
import sem.SEMscope;

/****
 * CSC 484 Compiler Theory
 * <p>
 * This class corresponds to an binary operation node
 * it has a flexible constructor corresponding to a number
 * of different binary operations.
 *****/

public class ASTbinexp extends ASTpair {

    protected ASTtype resulting_type;
    protected ASTtype operand_type;
    protected ASTbinop op;

    public ASTbinexp(ASTnode e1, ASTbinop op, ASTnode e2) {
        super(e1, e2);
        this.op = op;
        switch (op.getOp()) {
            case sym.PLUS:
                nodevalue = "+";
                break;

            case sym.TIMES:
                nodevalue = "*";
                break;

            case sym.MINUS:
                nodevalue = "-";
                break;

            case sym.DIVIDE:
                nodevalue = "/";
                break;

            case sym.EQ:
                nodevalue = "==";
                break;

            case sym.GT:
                nodevalue = ">";
                break;

            case sym.GE:
                nodevalue = ">=";
                break;

            case sym.NE:
                nodevalue = "!=";
                break;

            case sym.LT:
                nodevalue = "<";
                break;

            case sym.LE:
                nodevalue = "<=";
                break;

            case sym.COMMA:
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

    public ASTbinexp(ASTnode e1, LEXSymbol given_symbol, ASTnode e2) {
        super(e1, given_symbol.source_location, e2);
        //System.out.println("op: " + given_symbol);
        //System.out.println("2 op.source_location:"+given_symbol.source_location);
        source_location = given_symbol.source_location;
        op = new ASTbinop(given_symbol);
        switch (((Integer) given_symbol.value)) {
            case sym.PLUS:
                nodevalue = "+";
                break;

            case sym.TIMES:
                nodevalue = "*";
                break;

            case sym.MINUS:
                nodevalue = "-";
                break;

            case sym.DIVIDE:
                nodevalue = "/";
                break;

            case sym.EQ:
                nodevalue = "==";
                break;

            case sym.GT:
                nodevalue = ">";
                break;

            case sym.GE:
                nodevalue = ">=";
                break;

            case sym.NE:
                nodevalue = "!=";
                break;

            case sym.LT:
                nodevalue = "<";
                break;

            case sym.LE:
                nodevalue = "<=";
                break;
        }
        nodelabel = "binexp";
        operand_type = new ASTtype("int");
        resulting_type = new ASTtype("int");
    }

    public String toString() {
        return traverse(0);
    }

    public String traverse(int depth) {

        return tabulator(depth) + left.traverse(0) + nodevalue + right.traverse(0);

    }

    public void buildscopes(SEMscope given_scope) {
        //div by zero check
        if (nodevalue == "/") {
            try {
                int rightnum = (int) right.nodevalue;
                if (rightnum == 0) {
                    scope.error_output("Error: Division by zero! Location: " + source_location);
                }
            } catch (NullPointerException ne) {
                System.out.println("Error casting object to integer in ASTbinexp");
            } catch (ClassCastException ch) {
                System.out.println("Error casting object to integer in ASTbinexp");
            }
        }

        scope = given_scope;
        left.buildscopes(scope);
        right.buildscopes(scope);
    }

    public ASTtype analyze() {
        if (Settings.print_debug_msg) {
            System.out.println("Analyzing: \n" + traverse(0));
        }

        ASTtype left_type = left.analyze();
        ASTtype right_type = right.analyze();
        ASTtype error_type = new ASTtype("error");

        if ((left_type.equals(operand_type))
                && (right_type.equals(operand_type))) {
            if (left_type.isArray || right_type.isArray) {
                scope.error_output("Array must dereferenced first before they can be used in binary expresion. Location: " + source_location);
                return error_type;
            }
            return resulting_type;
        } else {
            scope.error_output("Type mismatch at location " + op.source_location +
                    " Expected type: " + operand_type);
            return error_type;
        }

    }
}
