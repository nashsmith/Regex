import java.util.*;
import java.io.*;

public class REcompile {
	//Initialize pattern array and FSM 
	public static List<Character> pattern = new ArrayList<Character>();
	public static List<String> ch = new ArrayList<String>();
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
		if(isNotEmpty()) {
			error();
		}
		//set state
		setState(state, " ", 0, 0);
		//write arrays to file
		FileWriter writer = new FileWriter("output");
		writer.write(Integer.toString(initial) + System.lineSeparator());
		for(int i = 0; i < ch.size(); i++) {
			writer.write(Integer.toString(i) + ',' + ch.get(i) + ',' + next1.get(i) + ',' + next2.get(i) + System.lineSeparator());
		}
		writer.close();
		
	}
	
	/*set state method*/
	private static void setState(int s, String c, int n1, int n2) {
		//fills empty array with empty characters to index to put in
		if(s >= ch.size()) {
			for(int i = (ch.size()); i < s; i++) {
				if(i >= 0) {
					ch.add(i, " ");
				}
			}
			ch.add(s, c);

		}
		else {
			ch.set(s, c);
		}
		if(s >= next1.size()) {
			for(int i = (next1.size()); i < s; i++) {
				if(i >= 0) {
					next1.add(i, null);
				}
			}
			next1.add(s, n1);
		}
		else {
			next1.set(s, n1);
		}
		if(s >= next2.size()) {
			for(int i = (next2.size()); i < s; i++) {
				if(i >= 0) {
					next2.add(i, null);
				}
			}
			next2.add(s, n2);
		}
		else {

			next2.set(s, n2);
		}
	}
	
	/*error method*/
	private static void error() {
		System.out.println("Uncompilable Pattern");
		System.exit(0);
	}
	
	/*isVocab method - checks char to be not an operator and valid letter or digit*/
	private static boolean isVocab(char c) {
		String s = Character.toString(c);
		String spec="?|*\\()"; 
		return !spec.contains(s);
	}
	
	/*check if empty pattern*/
	private static boolean isNotEmpty() {
		return j < pattern.size();
	}
	
	/*expression method*/
	private static int expression() {
		int r;
		//call term
		r = term();
		//call expression recursively if vocab or open parantheses
		if(isNotEmpty()) {
			if(isVocab(pattern.get(j))||pattern.get(j)=='('||pattern.get(j)=='\\'){
				expression();
			}
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
		//if * sets states appropriately
		if(isNotEmpty() && pattern.get(j)=='*') {
			setState(state," ",state+1,t1);
			j++;
			r=state;
			state++;
		}
		//if | sets states appropriately
		if(isNotEmpty() && pattern.get(j)=='|') {
			if(f > 0) {
				if(next1.get(f)==next2.get(f)) {
					next2.set(f, state);
				}
				next1.set(f, state);
			}
			f=state-1;
			j++;
			r=state;
			state++;
			t2=term();
			setState(r, " ", t1, t2);
			if(next1.get(f)==next2.get(f)) {
				next2.set(f,state);
			}
			next1.set(f, state);
		}
		//if ? sets states appropriately
		if(isNotEmpty() && pattern.get(j)=='?') {
			j++;
			String s = Character.toString(pattern.get(j));
			setState(state,s,state+1, state+1);
			next1.set(f, state);
			next2.set(f, state+1);
			j++;
			r=state;
			state++;
		}
		//return initial state
		return(r);
	}
	
	/*factor method*/
	private static int factor() {
		int r = 0;
		if(isVocab(pattern.get(j))) {
			String s = Character.toString(pattern.get(j));
			setState(state,s,state+1, state+1);
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
		//if \ and dealing with edge case '\.'
		else if(pattern.get(j)=='\\') {
			if(pattern.get(j+1)=='.') {
				j+=2;
				setState(state, "\\.", state+1, state+1);
			}
			else {
				j++;;
				String s1 = Character.toString(pattern.get(j));
				setState(state, s1, state+1, state+1);
				r=state;
				state++;
				j++;
			}
		}
		return(r);
	}
}
