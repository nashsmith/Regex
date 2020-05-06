/*
  COMPX301 Regex pattern searcher
  Authors:  Nash Smith (1277758)
            Konny Brown ()
*/


import java.util.*;
import java.io.*;

public class REsearch {

  //FSM
  public static List<String> ch = new ArrayList<String>();
  public static List<Integer> next1 = new ArrayList<Integer>();
  public static List<Integer> next2 = new ArrayList<Integer>();
  //

  //Initial state of the FSM
  public static int startState = 0;
  //Where in the line we are starting the current search from
  public static int mark;
  //Which character we are comparing
  public static int pointer;
  //Which state we are currently in
  public static int currentState;
  //Deque of current states
  public static Deque states = new Deque();

  public static void main(String[] args) throws IOException{

    // System.setIn(new FileInputStream(new File("output")));

    String fsmFileName = args[0];
    String textFileName = args[1];
    String line; //read line of text into
    Object tmp; //pop deque into
    String matchedChars = ""; //keep track of the current string matched
    int lineNum = 0; //keep track of which line the match is one

    //read FSM data and initialise ArrayLists
    importFSM(fsmFileName);
    System.out.println("Imported...");

    //Display FSM
    System.out.println(ch.toString());
    System.out.println(next1.toString());
    System.out.println(next2.toString());

    //Add the first state to the deque of states
    states.push(startState);
    System.out.println("Initial State: " + startState);
    System.out.println("Initial Deque: " + states.toString());

    //get file inputstream from args
    BufferedReader textFile = new BufferedReader(new FileReader(new File(textFileName)));

    System.out.println("Starting search...");

    //Read the file line by line
    while((line = textFile.readLine()) != null){
      lineNum++; //count which line we are on to report a match
      //pointers at first character on the line
      pointer = 0;
      mark = 0;

      //foreach letter in line
      while(mark < line.length() && pointer < line.length()){
        //for all currentStates in deque
        while((tmp = states.get()) != null && pointer < line.length()){
          //if the state is actually the SCAN
          if(tmp instanceof String){
            //move the next states to the current states
            states.transferStates();
            //if there is nothing or just the SCAN in the deque
            if(states.isEmpty()){
              //increment pointers
              mark++;
              pointer = mark;
              //go to the next letter
              break;
            }else{
              //otherwise you are checking the next character
              pointer++;
            }
            //get the next state (restarts the loop)
            continue;
          }else{
            currentState = (int)tmp;
          }
          //if the final state
          if(currentState == -1){
            //reoport the match
            System.out.println("Match on line " + lineNum + ": " + matchedChars + " | " + line);
            //increment pointers
            mark++;
            pointer = mark;
            //reset deque && go to next character
            break;
          }

          char chr;
          //if char is a space then its a branching state
          if(ch.get(currentState).charAt(0) == ' '){
            //add the two next states to the front of the deque
            states.push(next1.get(currentState));
            states.push(next2.get(currentState));
            //get the next state
            continue;
          }
          //if the char is actually a string then its an escaped period
          if(ch.get(currentState).length() > 1){
            chr = '.';
          }else{
            //otherwise the character is the character
            chr = ch.get(currentState).charAt(0);
          }
          //if the pointer character matches the currentState character OR WILDCARD
          if(line.charAt(pointer) == chr || ch.get(currentState).charAt(0) == '.'){
            matchedChars += line.charAt(pointer);
            //add that states nextStates to the end of the deque
            //if the states are the same then just add one of them
            if(next1.get(currentState).equals(next2.get(currentState))){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }
          }else{
          //else the pointer character doesnt match
          }
        }
        matchedChars = "";
        //reset the deque
        states = new Deque();
        //add the start state
        states.push(startState);
        // resetSeenList();

      }

    }

    System.out.println("Search Finished.");
  }

  /*
   *  Takes a text file from REcompile and turns it into the three ArrayLists
   *  representing the Finite State Machine.
   */
  public static void importFSM(String fileName) throws IOException{
    File file = new File(fileName);
    BufferedReader fsm = new BufferedReader(new FileReader(file));
    String line;
    Boolean firstRead = true;

    while((line = fsm.readLine()) != null){
      //the initial state is outputted on the first line
      if(firstRead){
        startState = Integer.parseInt(line);
        firstRead = false;
      }else{
        //the other lines consist of "stateNumber,character,next1,next2"
        String[] values = line.split(",");
        Integer stateNum = Integer.parseInt(values[0]);
        String c = values[1];
        Integer n1 = Integer.parseInt(values[2]);
        Integer n2 = Integer.parseInt(values[3]);

        //add the entry to the finite state machine
        ch.add(stateNum, c);
    		next1.add(stateNum, n1);
    		next2.add(stateNum, n2);
      }
    }

  }

}
