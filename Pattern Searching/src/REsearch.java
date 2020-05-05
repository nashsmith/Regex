import java.util.*;
import java.io.*;

public class REsearch {

  public static List<String> ch = new ArrayList<String>();
  public static List<Integer> next1 = new ArrayList<Integer>();
  public static List<Integer> next2 = new ArrayList<Integer>();

  public static int startState = 0;
  public static int mark;
  public static int pointer;
  public static int currentState;
  public static Deque states = new Deque();

  public static void main(String[] args) throws IOException{

    String fsmFileName = args[0];
    String textFileName = args[1];
    String line; //read line of text into
    Object tmp;
    int lineNum = 0;

    //read FSM data and initialise ArrayLists
    importFSM(fsmFileName);
    System.out.println("Imported...");
    System.out.println(ch.toString());
    System.out.println(next1.toString());
    System.out.println(next2.toString());

    states.push(startState);
    System.out.println(startState);
    System.out.println(states.toString());

    //get file inputstream from args
    BufferedReader textFile = new BufferedReader(new FileReader(new File(textFileName)));

    System.out.println("Starting search...");
    //read the file line by line into an array of characters
    while((line = textFile.readLine()) != null){
      lineNum++;
      pointer = 0;
      mark = 0;

      //foreach letter in line
        //for all currentStates in deque (states before SCAN)
        while(mark < line.length() && pointer < line.length()){
        while((tmp = states.get()) != null){
          // System.out.println("Start:" + states.toString());
          if(tmp instanceof String){
            states.transferStates();
            // System.out.println("After transfer:" + states.toString());
            if(states.isEmpty()){
              mark++;
              pointer = mark;
            }else{
              pointer++;
            }
            break;
          }else{
            currentState = (int)tmp;
          }
          if(currentState == 0 && pointer > mark){
            System.out.println("Match on line " + lineNum + ": " + line);
          }
          char chr;
          if(ch.get(currentState).length() > 1){
            chr = '.';
          // }else if(ch.get(currentState).equals('\u001b')){
          }else if(ch.get(currentState).equals(" ")){
            states.push(next1.get(currentState));
            states.push(next2.get(currentState));
            // System.out.println("After branch:" + states.toString());
            break;
          }else{
            chr = ch.get(currentState).charAt(0);
          }
          //if the pointer character matches the currentState character
          if(line.charAt(pointer) == chr){
            //add that states nextStates to the end of the deque
            if(next1.get(currentState) == next2.get(currentState)){
              states.put(next1.get(currentState));
            }else{
              states.put(next1.get(currentState));
              states.put(next2.get(currentState));
            }
          }else{
          //else the pointer character doesnt match
          }
        }
        states = new Deque();
        states.push(startState);

      }

    }

    System.out.println("Search Finished.");



    //when there are no more currentStates to check
      //transferStates on the Deque
      //increment pointer

    //if there are nextStates then repeat

    //if there are no nextStates there were no matches

    //if at any point -1 is a currentState then there was a full pattern match
      //output the line


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
