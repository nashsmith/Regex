import java.util.*;

public class REcompile {
	//Initialize pattern array and FSM 
	public static List<Character> pattern = new ArrayList<Character>();
	public static List<Character> ch = new ArrayList<Character>();
	public static List<Integer> next1 = new ArrayList<Integer>();
	public static List<Integer> next2 = new ArrayList<Integer>();
	public static int state;
	
	/*Main Method*/
	public static void main (String argsp[]) {
		//Take in pattern P (IO)
		String line = argsp[0];
		for(int i = 0; i < line.length(); i++) {
			pattern.add(line.charAt(i));
		}	
		/*Parsing and Compiling*/
		//initialize state
		state = 0;
		//Call expression
		//If p[j] != '\0' error()
		//set state
		setState(state, ' ', 0, 0);
		
	}
	
	/*set state method*/
	private static void setState(int s, char c, int n1, int n2) {
		ch.add(s, c);
		next1.add(s, n1);
		next2.add(s, n2);
	}
	
	/*error method*/
	private static void error() {
		System.out.println("Uncompilable Pattern");
		System.exit(0);
	}
	
	/*isVocab method - checks char to be not an operator*/
	private boolean isVocab(char c) {
		if (c == '\\'||c == '?'||c != '('||c == ')'||c == '|'||c == '.'||c == '*'){
			  return false;
		}
		return true;
	}
	
	/*expression method*/
	private int expression() {
		int r;
		r = term();
		return(r);
	}
	
	/*term method*/
	private int term() {
		int r, t1, t2, f;
		return(r);
	}
	
	/*factor method*/
	private int factor() {
		int r;
		return(r);
		
	}
}
