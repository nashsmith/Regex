import java.util.*;
import java.io.*;

public class REcompile {
	//Initialize pattern array and FSM 
	public static List<Character> pattern = new ArrayList<Character>();
	public static List<Character> ch = new ArrayList<Character>();
	public static List<Integer> next1 = new ArrayList<Integer>();
	public static List<Integer> next2 = new ArrayList<Integer>();
	public static int state;
	public static int j;
	
	/*Main Method*/
	public static void main (String args[]) throws IOException {
		//Take in pattern P (IO)
		String line = args[0];
		for(int i = 0; i < line.length(); i++) {
			pattern.add(line.charAt(i));
		}
		/*Parsing and Compiling*/
		//initialize state
		state = 0;
		j = 0;
		//Call expression
		int initial = expression();
		//If p[j] != '\0' error()
		if(pattern.get(j) != null) {
			error();
		}
		//set state
		setState(state, ' ', 0, 0);
		//write arrays to file
		FileWriter writer = new FileWriter("ch");
		for(char c: ch) {
			writer.write(c + System.lineSeparator());
		}
		writer.close();
		writer = new FileWriter("next1");
		for (int i: next1) {
			writer.write(i + System.lineSeparator());
		}
		writer.close();
		writer = new FileWriter("next2");
		for (int i: next2) {
			writer.write(i + System.lineSeparator());
		}
		writer.close();
		writer = new FileWriter ("start");
		writer.write(initial);
		writer.close();
		
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
	private static boolean isVocab(char c) {
		if (c == '\\'||c == '?'||c != '('||c == ')'||c == '|'||c == '.'||c == '*'){
			  return false;
		}
		return true;
	}
	
	/*expression method*/
	private static int expression() {
		int r;
		//call term
		r = term();
		//call expression recursively if vocab or open parantheses
		if(isVocab(pattern.get(j))||pattern.get(j)=='('){
			expression();
		}
		//return initial state
		return(r);
	}
	
	/*term method*/
	private static int term() {
		//initialize variables
		int r, t1, t2, f;
		f = state - 1;
		r = t1 = factor();
		//if *
		if(pattern.get(j)=='*') {
			setState(state,' ',state+1,t1);
			j++;
			r=state;
			state++;
		}
		//if |
		if(pattern.get(j)=='|') {
			if(next1.get(f)==next2.get(f)) {
				next2.add(f, state);
			}
			next1.add(f, state);
			f=state-1;
			j++;
			r=state;
			state++;
			t2=term();
			setState(r, ' ', t1, t2);
			if(next1.get(f)==next2.get(f)) {
				next2.add(f,state);
			}
			next1.add(f, state);
		}
		//if ?
		if(pattern.get(j)=='?') {
			
		}
		//if \
		if(pattern.get(j)=='\\') {
			
		}
		//if .
		if(pattern.get(j)=='.') {
			
		}
		//return initial state
		return(r);
	}
	
	/*factor method*/
	private static int factor() {
		int r = 0;
		if(isVocab(pattern.get(j))) {
			setState(state,pattern.get(j),state+1, state+1);
			j++;
			r=state;
			state++;
		}
		else if(pattern.get(j)=='(') {
			j++;
			r=expression();
			if(pattern.get(j)==')'){
				j++;
			}
			else {
				error();
			}
		}
		else {
			error();
		}
		return(r);
	}
}
