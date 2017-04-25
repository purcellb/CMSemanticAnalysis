package ast;

import parser.LEXSymbol;
import parser.Settings;
import parser.SrcLoc;
import sem.SEMscope;

/****
 * This class corresponds to an array dereference node
 *****/


public class ASTarrayder extends ASTnode {

    private ASTnode array_name;
    private ASTnode expression;
    private SrcLoc source_location;

    public ASTarrayder(ASTnode given_array_name, ASTnode given_expression) {
        super();
        nodelabel = "arrayder";

        array_name = given_array_name;
        expression = given_expression;
        source_location = given_array_name.source_location;
    }

    public ASTarrayder(ASTnode given_array_name, ASTnode given_expression, LEXSymbol given_symbol) {
        super();
        nodelabel = "arrayder";
        array_name = given_array_name;
        expression = given_expression;
        source_location = given_symbol.source_location;
    }

    /**
     * The scope of an array dereference is the scope its already in.
     * Passes this scope to the array and expression node.
     *
     * @param given_scope
     */
    public void buildscopes(SEMscope given_scope) {
        scope = given_scope;

        array_name.buildscopes(scope);
        expression.buildscopes(scope);
    }

    /**{@inheritDoc}
     * validates that an array dereference is possible at the current position in the code
     * and the dereference call is syntactically correct.
     *
     * @return int_type or an error_type
     */
    public ASTtype analyze() {
        if (Settings.print_debug_msg) {
            System.out.println("Analyzing " + array_name + nodelabel);
        }

        ASTtype array = array_name.analyze();
        ASTtype operand_type = expression.analyze();

        ASTtype error_type = new ASTtype("error");
        ASTtype int_type = new ASTtype("int");

        if ((array.equals(error_type)) || (operand_type.equals(error_type))) {
            return error_type;
        } else if (!array.isArray) {
            scope.error_output(array_name + " (Location: " + array_name.source_location + ") is not an array and tehrefore cannot be subscripted!");
            return error_type;
        } else if (!(operand_type.equals(int_type))) {
            scope.error_output("Type mismatch at location " + source_location.toString() + ": expected int subscript.");
            return error_type;
        } else return int_type;
    }

    public String toString() {
        return array_name.traverse(0) + "[" + expression.traverse(0) + "]";
    }

    /**{@inheritDoc}
     *
     * Adds custom formatting for the return.
     */
    public String traverse(int depth) {
        return array_name.traverse(depth) + "[" + expression.traverse(0) + "]";
    }
}


