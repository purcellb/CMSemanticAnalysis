package sem;

import ast.ASTfunctype;
import ast.ASTtype;
import parser.SrcLoc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/****
 * Func_Descriptor class -- used for C- functions.
 *****/

public class Func_Descriptor extends Descriptor {

    public Set<ASTfunctype> types;

    public Func_Descriptor(String given_name) {
        name = given_name;
        types = new HashSet<ASTfunctype>();
    }

    public boolean overload(ASTtype given_formals_type, ASTtype given_return_type, SrcLoc given_location) {
        location = given_location;
        return types.add(new ASTfunctype(given_formals_type, given_return_type, given_location));
    }

    public ASTfunctype functypeWithFormals(ASTtype given_formals_type) {
        Iterator it = types.iterator();
        ASTfunctype current = null;
        boolean exists = false;
        while (it.hasNext()) {
            current = (ASTfunctype) it.next();
            //System.out.println("Checking parameter types:");
            //System.out.println(current.formals_type);
            //System.out.println(given_formals_type);
            exists |= (current.formals_type).equals(given_formals_type);
            if (exists) break;
        }

        if (!exists) {
            current = null;
        }

        return current;
    }

    public boolean isFunc() {
        return true;
    }

    public String toString() {
        Iterator it = types.iterator();
        ASTfunctype current = null;
        String result = "";
        int count = 1;
        while (it.hasNext()) {
            current = (ASTfunctype) it.next();
            result += count + ": " + current + "\n";
        }

        return result;
    }
}
