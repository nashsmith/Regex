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

    while((line = textFile.readLine()) != null){
      char currentCharacter;
      lineNum++;
      mark = 0;
      pointer = 0;
      // System.out.println("Reading line " + lineNum);
      //for each character on the line
      while(mark < line.length() && pointer < line.length()){
        // currentCharacter = line.charAt(pointer);
        // System.out.println("Mark and pointer fine");
        while((tmp = states.get()) != null){
          // System.out.println("Mark" + mark + " Pointer: " + pointer);
          // System.out.println("Start: " + states.toString());
          if(tmp instanceof String){
            states.transferStates();
            if(states.isEmpty()){
              // mark++;
              // pointer = mark;
              // System.out.println("Empty: " + states.toString());
              break;
            }
            pointer++;
            // System.out.println("End: " + states.toString());
            continue;
          }

          if(pointer >= line.length()){
            break;
          }

          currentState = (int)tmp;
          // System.out.println("currentState: " + currentState);

          //If match finished
          if(currentState == -1){
            //report the match
            System.out.println("Match on line " + lineNum + ": " + matchedChars + " | " + line);
            //increment pointers
            // mark++;
            // pointer = mark;
            //reset deque && go to next character
            continue; //TODO break further
          }

          if(ch.get(currentState).charAt(0) == ' '){
            //add the two next states to the front of the deque
            if(next1.get(currentState).equals(next2.get(currentState))){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }
            //get the next state
            continue;
          }

          char compareChar = ch.get(currentState).charAt(0);
          if(compareChar == '\\'){
            compareChar = '.';
          }

          // System.out.println(line.charAt(pointer) + " " + compareChar);
          if(line.charAt(pointer) == compareChar || ch.get(currentState).charAt(0) == '.'){
            matchedChars += line.charAt(pointer);

            if(next1.get(currentState).equals(next2.get(currentState))){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }

          }


            // System.out.println("End: " + states.toString());
        }
        mark++;
        pointer = mark;
        //no more states
        matchedChars = "";
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
