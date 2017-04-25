package sem;

import ast.ASTfunctype;
import ast.ASTtype;
import parser.Settings;

import java.util.Hashtable;
import java.util.Iterator;

/****
 * Handles scope for the semantic anaylsis of C Minus
 * it employs a hash table to store and lookup symbols as they are created
 */
public class SEMscope {

    /**
     * The parent scope of the current scope
     */
    public SEMscope parent;
    /**
     * The Symbol table holds a name and its corresponding Descriptor.
     * <p>
     * Its used to lookup symbols to see if they exist in the current scope.
     */
    public Hashtable<String, Descriptor> SymbolTable;
    /**
     * Boolean flag used to prevent nested function declarations
     */
    public boolean in_block = false;
    /**
     * Boolean flag used to prevent multiple return statements within a scope. Is true if there is already a return
     * statement inside this scope
     */
    public boolean return_exist = false;
    /**
     * return_type is used to keep track of the first return statement's expression type to make sure returns in
     * a scope return the same type.
     * <p>
     * ex: if(x==1){ return 5} else {return void;} is invalid because not all paths retunr the same type.
     */
    public ASTtype return_type = null;
    /**
     * Used to remember if main function has been defined and prevent defining main multiple times.
     */
    public boolean main_found = false;

    /**
     * Instantiates a new SEMscope with an empty hastable.
     *
     * @param outerscope the parent scope of the current scope
     */
    public SEMscope(SEMscope outerscope) {
        parent = outerscope;
        SymbolTable = new Hashtable<String, Descriptor>();
    }

    /**
     * Finds the Descriptor for a given name in this scope's symbol table.
     *
     * @param name the name
     * @return the Descriptor
     * @see Descriptor
     */
    public Descriptor find(String name) {
        Descriptor foundsymbol = SymbolTable.get(name);

        if (foundsymbol == null) {
            if (parent != null) return parent.find(name);
            else return new Empty_Descriptor();
        } else
            return foundsymbol;
    }

    /**
     * Checks if main is defined in the current scope.
     *
     * @return <code>true</code> if the main method is NOT defined in this scope;
     * <code>false</code> if the main IS defined in this scope.
     */
    public boolean main_defined() {
        return !find("main").isEmpty();
    }

    /**
     * Gets the descriptor at the top of the scope tree for the given name
     *
     * @param name the name to search for
     * @return the Descriptor
     */
    public Descriptor findtop(String name) {
        SEMscope temp_scope = this;
        while (temp_scope.parent != null) temp_scope = temp_scope.parent;
        Descriptor found = temp_scope.find(name);
        return found;
    }

    /**
     * Returns wether a return statement exists in the scope or any of it's parent scopes
     *
     * @return if the return statement exists
     */
    public boolean return_exist() {
        if (return_exist) {
            return true;
        }
        SEMscope temp_scope = this;
        boolean exist = false;
        while (temp_scope.parent != null) {
            temp_scope = temp_scope.parent;
            exist |= temp_scope.return_exist;
            if (exist) break;
        }
        return exist;
    }

    /**
     * Creates a new entry in the symbol table for a variable
     * <p>
     * validates that the variable's name isn't already in use in this scope, outputs error if it does
     *
     * @param entry the Variable_Descriptor to add to the hashtable
     */
    public void insertVariable(Variable_Descriptor entry) {
        Descriptor foundsymbol = SymbolTable.get(entry.name);

        if (foundsymbol == null)	/* We are clear to insert the given entry */
            SymbolTable.put(entry.name, entry);
        else {
      /*  We have a double declaration; too bad */
            error_output("Name '" + entry.name + "' in location " + entry.location.toString() + " " +
                    "was already declared in current scope in location " + foundsymbol.location.toString());
        }
    }

    /**
     * Creates a new entry in the symbol table for an array
     * validates that the array's name is not already in use in this scope, outputs an error if it does
     *
     * @param entry the Array_Descriptor to add to the hashtable
     */
    public void insertArray(Array_Descriptor entry) {
        Descriptor foundsymbol = SymbolTable.get(entry.name);

        if (foundsymbol == null)	/* We are clear to insert the given entry */
            SymbolTable.put(entry.name, entry);
        else {
      /*  We have a double declaration; too bad */
            error_output("Name '" + entry.name + "' in location " + entry.location.toString() + " " +
                    "was already declared in current scope in location " + foundsymbol.location.toString());
        }
    }

    /**
     * Creates a new entry in the symbol table for a formal
     * validates that the formal's name is not already in use in this scope, outputs an error if it does
     *
     * @param entry the Formal_Descriptor to add to the hashtable
     */
    public void insertFormal(Formal_Descriptor entry) {
        Descriptor foundsymbol = SymbolTable.get(entry.name);

        if (foundsymbol == null)	/* We are clear to insert the given entry */
            SymbolTable.put(entry.name, entry);
        else {
      /*  We have a double declaration; too bad */
            error_output("Parameter Name '" + entry.name + "' in location " + entry.location.toString() + " " +
                    "was already declared in current scope in location " + foundsymbol.location.toString());
        }
    }

    /**
     * Creates a new entry in the symbol table for a function
     * validates that the function's name is not already in use in this scope, outputs a descriptive error of
     * the location of the dupliocate declaration if it is already in use
     *
     * @param entry the Func_Descriptor to add to the hashtable
     */
    public void insertFunc(Func_Descriptor entry) {
        if (Settings.print_debug_msg) {
            System.out.println("Trying to insert function " + entry.name);
        }
        Descriptor foundsymbol = SymbolTable.get(entry.name);

        if (foundsymbol == null) {
            SymbolTable.put(entry.name, entry);
            if (Settings.print_debug_msg) {
                System.out.println("Function " + entry.name + "'s signatures:\n" + entry);
            }
        } else {
            if (!foundsymbol.isFunc()) {
                //SymbolTable.put(entry.name, entry);
                //if(Settings.print_debug_msg){
                //	System.out.println("Function "+entry.name+"'s signatures:\n"+entry);
                //}
                error_output("Name " + entry.name + " at location " + entry.location + " has been bound!");
                return;
            }
            Iterator it = ((Func_Descriptor) foundsymbol).types.iterator();
            ASTfunctype entry_type = entry.types.iterator().next();
            ASTfunctype current = null;

            boolean exists = false;
            while (it.hasNext()) {
                current = (ASTfunctype) it.next();
                exists |= (current.equals(entry_type));
                if (exists) break;
            }

            if (exists) {
                error_output("Function named '" + entry.name + "' in location " + entry_type.source_location.toString() + " " +
                        "was already defined in location " + current.source_location.toString());
            } else
                ((Func_Descriptor) foundsymbol).types.add(entry_type);

            if (Settings.print_debug_msg) {
                System.out.println("Function " + ((Func_Descriptor) foundsymbol).name + "'s signatures:\n" + ((Func_Descriptor) foundsymbol));
            }
        }
    }

    /**
     * Prints formatted error output to System.out.
     *
     * @param message the error message
     */
    public void error_output(String message) {
        System.out.println("[SEM]: " + message);

    }
}

