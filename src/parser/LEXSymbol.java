/****
 * CSC 484 Compiler Theory
 *
 * The following class is used to wrap a token with its location info used for error message printing during
 * semantics analysis.
 *
 ****/
package parser;

import java_cup.runtime.Symbol;

public class LEXSymbol extends Symbol {

	public SrcLoc source_location;
	protected String my_text;
	
	public LEXSymbol(int t,int l,int r,String txt) {
		super(t,l,r);
		source_location = new SrcLoc(l,r);
		my_text = txt;
	}
	
	public LEXSymbol(int t,int l,int r,Object o,String txt) {
		super(t,l,r,o);
		source_location = new SrcLoc(l,r);
		my_text = txt;
	}

	public String getMy_text(){
		return my_text;
	}
	
	public String toString() {
		return "'" +  my_text + "'";
	}
}
