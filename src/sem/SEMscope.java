package sem;

import java.util.Hashtable;
import ast.ASTnode;
import ast.ASTleaf;
import ast.ASTtype;
import parser.SrcLoc;
import parser.Settings;
import java.util.Iterator;
import ast.ASTfunctype;
import java.util.HashSet;
import java.util.Set;

/****
	* CSC 484 Compiler Theory
	*
	* This is the scope class
	* it employs a hash table
	*
*****/

public class SEMscope {
	
	public SEMscope parent;
	public Hashtable<String,Descriptor> SymbolTable;
	//is_block is used to prevent nested function declarations
	public boolean in_block = false;
	//return_exist is used to prevent multiple return statements
	public boolean return_exist = false;
	//return_type is used to keep track of the first return statement's expression type
	public ASTtype return_type = null;
	//main_found is used to remember if main function has been defined
	public boolean main_found = false;

	public SEMscope(SEMscope outerscope)
	{
		parent = outerscope;
		SymbolTable = new Hashtable<String,Descriptor>();
	}
	
	public Descriptor find(String name)
	{
		Descriptor foundsymbol = SymbolTable.get(name);

		if (foundsymbol==null) 
		{
			if (parent!=null) return parent.find(name);
			else return new Empty_Descriptor();
		}
			else 
			return foundsymbol;
	}

	public boolean main_defined(){
		return !find("main").isEmpty();
	}

	public Descriptor findtop(String name)
	{
		SEMscope temp_scope=this;
		while(temp_scope.parent!=null) temp_scope = temp_scope.parent;
		Descriptor found = temp_scope.find(name);
		return found;
	}

	public boolean return_exist(){
		if (return_exist) { return true; }
		SEMscope temp_scope=this;
		boolean exist = false;
		while(temp_scope.parent!=null) {
			temp_scope = temp_scope.parent;
			exist |= temp_scope.return_exist;
			if(exist) break;
		}
		return exist;
	}

	public void insertVariable(Variable_Descriptor entry)
	{
		Descriptor foundsymbol = SymbolTable.get(entry.name);
		
		if (foundsymbol==null)	/* We are clear to insert the given entry */
			SymbolTable.put(entry.name, entry);
		else
		{
			/*  We have a double declaration; too bad */
			error_output("Name '" + entry.name + "' in location " + entry.location.toString() + " " +
		   		"was already declared in current scope in location " + foundsymbol.location.toString());
		}
	}

	public void insertArray(Array_Descriptor entry)
	{
		Descriptor foundsymbol = SymbolTable.get(entry.name);

		if (foundsymbol==null)	/* We are clear to insert the given entry */
			SymbolTable.put(entry.name, entry);
		else
		{
			/*  We have a double declaration; too bad */
			error_output("Name '" + entry.name + "' in location " + entry.location.toString() + " " +
					"was already declared in current scope in location " + foundsymbol.location.toString());
		}
	}

	public void insertFormal(Formal_Descriptor entry)
	{
		Descriptor foundsymbol = SymbolTable.get(entry.name);

		if (foundsymbol==null)	/* We are clear to insert the given entry */
			SymbolTable.put(entry.name, entry);
		else
		{
			/*  We have a double declaration; too bad */
			error_output("Parameter Name '" + entry.name + "' in location " + entry.location.toString() + " " +
					"was already declared in current scope in location " + foundsymbol.location.toString());
		}
	}

	public void insertFunc(Func_Descriptor entry)
	{
		if(Settings.print_debug_msg){
			System.out.println("Trying to insert function "+entry.name);
		}
		Descriptor foundsymbol = SymbolTable.get(entry.name);
			
		if (foundsymbol == null)
		{
			SymbolTable.put(entry.name, entry);
			if(Settings.print_debug_msg){
				System.out.println("Function "+entry.name+"'s signatures:\n"+entry);
			}
		}
		else
		{
			if(!foundsymbol.isFunc()){
				//SymbolTable.put(entry.name, entry);
				//if(Settings.print_debug_msg){
				//	System.out.println("Function "+entry.name+"'s signatures:\n"+entry);
				//}
				error_output("Name "+entry.name+" at location " + entry.location + " has been bound!");
				return;
			}
			Iterator it = ((Func_Descriptor)foundsymbol).types.iterator();
			ASTfunctype entry_type = entry.types.iterator().next();
			ASTfunctype current=null;
			
			boolean exists = false;
			while(it.hasNext()) 
			{ 
				current = (ASTfunctype) it.next();
				exists |= (current.equals(entry_type));
				if (exists) break;
			}
			
			if (exists) 
			{
				error_output("Function named '" + entry.name + "' in location " + entry_type.source_location.toString() + " " +
				            "was already defined in location " + current.source_location.toString());
			}
			else
				((Func_Descriptor)foundsymbol).types.add(entry_type);

			if(Settings.print_debug_msg){
				System.out.println("Function "+((Func_Descriptor)foundsymbol).name+"'s signatures:\n"+((Func_Descriptor)foundsymbol));
			}
		}
	}
	
	public void error_output(String message)
	{
		System.out.println("[SEM]: " + message);

	}
}

