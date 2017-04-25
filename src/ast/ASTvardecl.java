package ast;

import parser.LEXSymbol;
import parser.SrcLoc;
import parser.sym;
import sem.SEMscope;
import sem.Variable_Descriptor;

/****
 * This class corresponds to an local declaration node
 *****/

public class ASTvardecl extends ASTnode {

    private ASTtype type;
    private String name;
    private SrcLoc location;

    public ASTvardecl(ASTtype given_type, String given_name) {
        super();
        type = given_type;
        name = given_name;
        nodelabel = "vardecl";
    }

    public ASTvardecl(ASTtype given_type, LEXSymbol given_symbol) {
        super();
        type = given_type;
        name = given_symbol.getMy_text();
        location = given_symbol.source_location;
        nodelabel = "vardecl";
    }

    public String toString() {
        return traverse(0);
    }

    public String traverse(int depth) {
        return tabulator(depth) + type.traverse(0) + " " + name + ";\n";
    }

    public void buildscopes(SEMscope given_scope) {

        scope = given_scope;

        if (type.equals(new ASTtype(sym.INT))) {
            //check if another var of same name is declared in scope is done in insertVariable
            scope.insertVariable(new Variable_Descriptor(type, name, location));
        } else
            scope.error_output("Variables can only be integers in C-. Location: " + source_location);
    }

    public ASTtype analyze() {
        if (!type.equals(new ASTtype(sym.INT))) {
            return type;
        }else {
            return new ASTtype("error");
        }
    }
}


