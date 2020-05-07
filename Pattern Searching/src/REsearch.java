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

    String textFileName = args[0];
    String line; //read line of text into
    Object tmp; //pop deque into
    String matchedChars = ""; //keep track of the current string matched
    int lineNum = 0; //keep track of which line the match is one


    importFSM();
    // System.out.println("Imported...");

    //Display FSM
    // System.out.println(ch.toString());
    // System.out.println(next1.toString());
    // System.out.println(next2.toString());

    //Add the first state to the deque of states
    states.push(startState);
    // System.out.println("Initial State: " + startState);
    // System.out.println("Initial Deque: " + states.toString());

    //get file inputstream from args
    BufferedReader textFile = new BufferedReader(new FileReader(new File(textFileName)));

    // System.out.println("Starting search...");

    //while there is more lines to read
    while((line = textFile.readLine()) != null){
      //increment the linenumber
      lineNum++;
      //reset marker and pointer to start of line
      mark = 0;
      pointer = 0;
      //for each character on the line
      //labeled loop for a deep break
      character:
      while(mark < line.length() && pointer < line.length()){

        //while there are states in the Deque
        while((tmp = states.get()) != null){
          //if the popped state is the SCAN
          if(tmp instanceof String){
            //move the nextStates to the currentStates in the deque
            states.transferStates();
            //if only the SCAN is in the deque
            if(states.isEmpty()){
              //No match, break out and reset deque/increment mark&pointer
              break;
            }
            //if theres more states they are to be checked on the next character
            pointer++;
            //restart the loop to get the next state, currently has the SCAN
            continue;
          }

          //if the pointer is now past the last character
          if(pointer >= line.length()){
            //ran out of characters, NO MATCH
            break;
          }

          //cast the popped state to an int, it is not the SCAN
          currentState = (int)tmp;

          //If match finished
          if(currentState == -1){
            //report the match
            System.out.println(line);
            //reset the recorded match
            matchedChars = "";
            //re-init the deque with the startState and SCAN
            states = new Deque();
            states.push(startState);
            //break to get a new line of text (only need to report each matched line once)
            break character;
          }

          //if the state is a branching state
          if(ch.get(currentState).charAt(0) == ' '){
            //add the two next states to the front of the deque
            //if the two next states are the same then just add one of them
            if(next1.get(currentState).equals(next2.get(currentState))){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }
            //get the next state, restart the loop
            continue;
          }

          //char to compare is that in the current state
          char compareChar = ch.get(currentState).charAt(0);
          //a period needs to be escaped so if the string is of length 2 its a period
          if(ch.get(currentState).length() > 1){
            compareChar = '.';
          }

          //if the currentCharacter matches the state character or if the state is a wildcard
          if(line.charAt(pointer) == compareChar || ch.get(currentState).charAt(0) == '.'){
            //add the matched char to the matched string so far (not used after submitting)
            matchedChars += line.charAt(pointer);

            //add the next states to the Deque
            //if they are the same then just add one
            if(next1.get(currentState).equals(next2.get(currentState))){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }

          }
        }
        //No More States
        //increment mark and pointer
        mark++;
        pointer = mark;
        //reset matched chars
        matchedChars = "";
        //re-init deque
        states = new Deque();
        states.push(startState);

      }

    }

  }

  /*
   *  Takes a text file from REcompile and turns it into the three ArrayLists
   *  representing the Finite State Machine.
   */
  public static void importFSM() throws IOException{
    BufferedReader fsm = new BufferedReader(new InputStreamReader(System.in));
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
