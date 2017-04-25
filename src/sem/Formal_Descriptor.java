package sem;

import ast.ASTtype;
import parser.SrcLoc;

/****
 * Formal_Descriptor class -- used for C- parameters in function definition.
 *****/

public class Formal_Descriptor extends Descriptor {

    protected boolean isArray;

    public Formal_Descriptor(ASTtype given_type, String given_name, boolean isArray, SrcLoc given_location) {
        isemptyflag = false;
        type = given_type;
        name = given_name;
        this.isArray = isArray;
        location = given_location;
    }

    public boolean isParam() {
        return true;
    }

    public boolean isArray() {
        return isArray;
    }
}
