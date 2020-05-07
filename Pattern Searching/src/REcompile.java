/*
  COMPX301 Regex pattern searcher
  Authors:  Konny Brown ()
						Nash Smith (1277758)
*/

import java.util.*;
import java.io.*;

public class REcompile {
	//Initialize pattern array and FSM
	public static List<String> pattern = new ArrayList<String>();
	public static List<String> ch = new ArrayList<String>();
	public static List<Integer> next1 = new ArrayList<Integer>();
	public static List<Integer> next2 = new ArrayList<Integer>();
	public static int state;
	public static int j;

	/*Main Method*/
	public static void main (String args[]) throws IOException {
		//Take in pattern P (IO)
		if(args.length > 1) {
			error();
		}
		String line = args[0];
		for(int i = 0; i < line.length(); i++) {
			pattern.add(line.substring(i,i+1));
		}
		/*Parsing and Compiling*/
		//initialize state
		state = 0;
		j = 0;
		//Check if empty or illegal characters
		String spec ="?|*"; 
		if(!isNotEmpty()|| spec.contains(pattern.get(j))) {
			error();
		}
		//Call expression
		int initial = expression();
		//Check if empty, if not error
		if(isNotEmpty()) {
			error();
		}
		//set state
		setState(state, " ", -1, -1);
		//write arrays to file
		System.out.println(Integer.toString(initial));
		for(int i = 0; i < ch.size(); i++) {
			System.out.println(Integer.toString(i) + ',' + ch.get(i) + ',' + next1.get(i) + ',' + next2.get(i));
		}
		
	}
	
	/*set state method*/
	private static void setState(int s, String c, int n1, int n2) {
		//fills empty array with empty characters up to index to put in and sets state
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
	
	/*isVocab method - checks string to be not an operator and valid character*/
	private static boolean isVocab(String s) {
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
		int f;
		int t1;
		int t2;
		//call term
		f = state-1;
		r = t1 = term();
		//if | sets states appropriately
		if(isNotEmpty() && pattern.get(j).equals("|")) {
			if(f >= 0) {
				if(next1.get(f)==next2.get(f)) {
					next2.set(f, state);
				}
				next1.set(f, state);
			}
			f=state-1;
			j++;
			r=state;
			state++;
			t2=expression();
			setState(r, " ", t1, t2);
			if(next1.get(f)==next2.get(f)) {
				next2.set(f,state);
			}
			next1.set(f, state);
		}
		//return initial state
		return(r);
	}
	
	/*term method*/ 
	private static int term() {
		//initialize variables
		int r, t1, f;
		f = state - 1;
		r = t1 = factor();
		//if * sets states appropriately
		if(isNotEmpty() && pattern.get(j).equals("*")){
			setState(state," ",state+1, t1);
			if(f >= 0) {
				next1.set(f, state);
			}
			j++;
			f=state-1;
			r=state;
			state++;
		}
		//if ? sets states appropriately
		if(isNotEmpty() && isVocab(pattern.get(j)) && pattern.get(j).equals("?")) {
			setState(state," ",r, state+1);
			if(f >= 0) {
				next1.set(f, state);
				next2.set(f, state);
			}
			f=state-1;
			if(f >= 0) {
				next1.set(f, state+1);
				next2.set(f, state+1);
			}
			j++;
			r=state;  
			state++;
		}
		//call expression recursively if vocab or open parantheses or backslash
		if(isNotEmpty() && (isVocab(pattern.get(j)) || pattern.get(j).equals("(") || pattern.get(j).equals("\\"))) {
			term();
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
		else if(pattern.get(j).equals("(")) {
			j++;
			r=expression();
			if(pattern.get(j).equals(")")){
				j++;
			}
			else {
				error();
			}
		}
		//if \ and dealing with edge case '\.'
		else if(pattern.get(j).equals("\\")) {
			j++;
			if(!isNotEmpty()) {
				error();
			}
			if(pattern.get(j).equals(".")) {
				setState(state, "\\.", state+1, state+1);
				r=state;
				state++;
				j++;
			}
			else {
				setState(state, pattern.get(j), state+1, state+1);
				r=state;
				state++;
				j++;
			}
		}
		return(r);
	}
}
