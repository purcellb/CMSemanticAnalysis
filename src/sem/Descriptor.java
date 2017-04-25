package sem;

import ast.ASTtype;
import parser.SrcLoc;

/****
 * This is an abstract class that defines entries to the hashtables
 * it will be extended to a diverse collection of classes
 * that will be used to describe the types and semantics
 * of the various components of a C- program.
 *****/

public abstract class Descriptor {

    public boolean isemptyflag;
    public ASTtype type;
    public String name;
    public SrcLoc location;

    /***
     * Method that returns whether the entry is empty or not
     */
    public boolean isEmpty() {
        return isemptyflag;
    }

    public boolean isFunc() {
        return false;
    }

    public boolean isVariable() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isParam() {
        return false;
    }

}
