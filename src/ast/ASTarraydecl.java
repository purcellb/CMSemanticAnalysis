package ast;

import parser.LEXSymbol;
import parser.Settings;
import sem.*;

/****
 * This class corresponds to an array declaration node
 *****/

public class ASTarraydecl extends ASTnode {

    private ASTtype type;
    private String name;
    private int length;

    public ASTarraydecl(ASTtype given_type, String given_name, int l) {
        super();
        type = given_type;
        name = given_name;
        nodelabel = "arraydecl";
        length = l;
    }

    public ASTarraydecl(ASTtype given_type, LEXSymbol given_name_symbol, int l) {
        super();
        type = given_type;
        type.isArray = true;
        name = given_name_symbol.getMy_text();
        source_location = given_name_symbol.source_location;
        nodelabel = "arraydecl";
        length = l;
    }

    public ASTarraydecl(ASTtype given_type, LEXSymbol given_name_symbol, LEXSymbol given_size_symbol) {
        super();
        type = given_type;
        type.isArray = true;
        name = given_name_symbol.getMy_text();
        source_location = given_name_symbol.source_location;
        nodelabel = "arraydecl";
        length = (Integer) given_size_symbol.value;
    }

    public String toString() {
        return nodevalue.toString();
    }

    public String traverse(int depth) {
        return tabulator(depth) + type.traverse(0) + " " + name + "[" + length + "];\n";
    }

    public void buildscopes(SEMscope given_scope) {
        scope = given_scope;
        if (scope.main_defined() && scope.parent == null) {
            scope.error_output("main must be the last declaration!");
        }
        if (name.equals("main") || name.equals("input") || name.equals("output")) {
            scope.error_output("Array name cannot be main, input, or output. Location: " + source_location);
        }
        scope.insertArray(new Array_Descriptor(type, name, length, source_location));
    }

    public ASTtype analyze() {
        if (Settings.print_debug_msg) {
            System.out.println("Analyzing: \n" + traverse(0));
        }
        if (length < 1) {
            scope.error_output("Array Declaration \"" + name + "\" has an invalid length " + length +" !");
            return new ASTtype("error");
        }
        return type;
    }
}


