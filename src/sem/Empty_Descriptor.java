package sem;

/****
    * CSC484 Compiler Theory
    * 
	* SEMempty class -- used to denote an empty entry in the hash table
	*
	* 
*****/

public class Empty_Descriptor extends Descriptor {
	
	public Empty_Descriptor()
	{
		isemptyflag=true;
		name = null;
		location = null;
	}
}
