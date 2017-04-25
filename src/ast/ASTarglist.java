package ast;

import parser.Settings;
import sem.SEMscope;

/****
 * Used to represent the actual list of arguments in function call.
 * The list is stored in a linked structure, each node contains a reference to the previous and next nodes.
 ****/

public class ASTarglist extends ASTlist {

    public ASTarglist(ASTnode p, ASTnode n) {
        super(p, n);
        nodelabel = "arglist";
    }

    public String toString() {
        return traverse(0);
    }


    public String traverse(int depth) {
        if (prev == null) {
            return next.traverse(depth);
        }
        return prev.traverse(depth) + ", " + next.traverse(depth);
    }

    public void buildscopes(SEMscope given_scope) {
        scope = given_scope;
        prev.buildscopes(scope);
        next.buildscopes(scope);
    }


    public ASTtype analyze() {
        if (Settings.print_debug_msg) {
            System.out.println("Analyzing arglist: \n" + traverse(0));
        }

        ASTtype prev_type = prev.analyze();
        ASTtype next_type = next.analyze();
        ASTtype error_type = new ASTtype("error");

        if ((prev_type.equals(error_type)) || (next_type.equals(error_type))) {
            return error_type;
        } else {
            if (!(next_type.nodelabel.equals("tuple")))
                return new ASTtupletype(prev_type, new ASTtupletype(next_type));
            else
                return new ASTtupletype(prev_type, (ASTtupletype) next_type);
        }
    }

    public boolean equals(ASTnode given_node) {
        if (nodelabel == given_node.nodelabel) {
            return (this.prev.equals(((ASTarglist) given_node).prev) && this.next.equals(((ASTarglist) given_node).next));
        } else return false;
    }
}
