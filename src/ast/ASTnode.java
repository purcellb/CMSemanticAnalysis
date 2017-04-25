package ast;

import parser.SrcLoc;
import sem.SEMscope;

/****
 * This is an abstract class that defines a node of an Abstract Syntax Tree
 * it is extended to a diverse collection of classes that are used
 * to construct the syntax tree for a C Minus program.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Abstract_syntax_tree">Abstract Syntax Tree (Wikipedia)</a>
 */
public abstract class ASTnode {

    /**
     * The location in code that the node refers to
     */
    public SrcLoc source_location;
    /**
     * The line number in the code the node is on
     */
    public int line_number;
    /**
     * The column number in the code the node is on
     */
    public int column_number;
    /**
     * The ASTType or String representation of what this the node represents.
     * <p>
     * EX: "+" could be for a ASTbinop node that represents addition
     * "int" could be for a ASTarray of integers
     */
    public Object nodevalue;
    /**
     * The String representation of a nodes name or identifying type
     * <p>
     * EX: an ASTident has a nodelabel = "ident"
     */
    public String nodelabel;
    /**
     * The scope level of the node
     */
    protected SEMscope scope;

    public abstract String toString();

    /**
     * Builds and returns a string of the current structure.
     *
     * @param depth the depth of the structure
     * @return a string representation of the structure
     */
    public abstract String traverse(int depth);

    /**
     * Constructs the scope of an ASTnode. Scope is used to ensure that all pieces of a program eventually return
     * and to validate that variable/function names are not duplicated within the same scope.
     * <p>
     * ie: You may define an int x in main and within a method as a local var, but you may not define int x
     * multiple times in main or multiple times as a local variable in a method.
     * <p>
     * Scopes each ahve their own symbol table.
     *
     * @param given_scope the given scope
     * @see SEMscope#SymbolTable
     */
    public void buildscopes(SEMscope given_scope) {
        scope = given_scope;
    }

    /**
     * This is the primary point of Semantic Analysis. This validates the code parsed into the nodes based
     * on criteria defined in the lexical conventions and semantics of C Minus. The various child classes of ASTnode are analyzed in
     * their respective ways based on the semantics provided.
     * <p>
     * EX: ASTnumber just needs to verify that its an integer (the only numeric type allowed in C-) whereas ASTcall
     * must verify that the functionn its calling exists, is declared BEFORE the call, and that the paramerters match
     * the function.
     *
     * @return the ASTtype of the node
     * @see <a href="http://www.cs.dartmouth.edu/~cs57/Project/C-%20Spec.pdf">C Minus Specifications
     * (Apologies to Dartmouth for borrowing their hosted file)</a>
     */
    public ASTtype analyze() {
        return new ASTtype("void");
    }

    /**
     * Determines equality of nodes based solely on their label. Method should be overridden
     * by the various ASTnode children to compare based on the child's properties.
     *
     * @param given_node the given node
     * @return the boolean
     */
    public boolean equals(ASTnode given_node) {
        return (given_node.nodelabel == nodelabel);
    }

    /**
     * Generates tab characters to match the depth of the code being parsed.
     * Returns a string with the number of tabs equal to the current depth.
     * <p>
     * This is strictly for pretty code formatting, so the code output is tabulated
     *
     * @param depth the depth of the node calling the tabulator
     * @return string of depth amount of tab characters
     */
    public String tabulator(int depth) {
        String my_string = "";
        for (int j = 0; j < depth; j++) my_string += "\t";
        return my_string;
    }

}
