package ast;

import parser.LEXSymbol;
import parser.sym;
import sem.SEMscope;

/****
 * CSC 484 Compiler Theory
 * <p>
 * This class corresponds to an if then statement node
 *****/


public class ASTifthen extends ASTnode {

    private ASTnode condition, statementif;

    public ASTifthen(ASTnode given_condition, ASTnode given_statementif, LEXSymbol given_symbol) {
        super();
        statementif = given_statementif;
        condition = given_condition;
        nodelabel = "ifthen";
        source_location = given_symbol.source_location;
    }

    public ASTifthen(ASTnode given_condition, ASTnode given_statementif) {
        super();
        statementif = given_statementif;
        condition = given_condition;
        nodelabel = "ifthen";
    }

    public String toString() {
        return traverse(0);
    }

    public String traverse(int depth) {
        return tabulator(depth + 1) + "if(" + condition.traverse(0) + ")" + "\n" +
                tabulator(depth + 1) + statementif.traverse(0) + "\n";
    }

    public void buildscopes(SEMscope given_scope) {
        scope = given_scope;
        condition.buildscopes(scope);
        //Always a new scope even if there is only one regular statement, namely we create a new scope.
        SEMscope next_scope = new SEMscope(null);
        next_scope.parent = scope;
        statementif.buildscopes(next_scope);
    }

    public ASTtype analyze() {

        ASTtype ct = condition.analyze();
        ASTtype st = statementif.analyze();
        ASTtype error_type = new ASTtype("error");
        if (ct == null || !ct.equals(new ASTtype(sym.INT))) {
            scope.error_output("Condition expression must be integer...");
            ct = new ASTtype("error");
        }

        if (ct.equals(error_type) || (st.equals(error_type))) {
            return error_type;
        } else {
            return st;
        }
    }
}
