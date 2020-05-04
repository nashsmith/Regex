
public class REsearch {

  public static void main(String[] args){

    public static List<Character> ch = new ArrayList<Character>();
    public static List<Integer> next1 = new ArrayList<Integer>();
    public static List<Integer> next2 = new ArrayList<Integer>();

    public static int mark;
    public static int pointer;
    public static Deque states;


    //read FSM data and initialise ArrayLists

    //get file inputstream from args
    //read the file line by line into an array of characters
    //for all currentStates in deque (states before SCAN)
      //if the pointer character matches the currentState character
        //add that states nextStates to the end of the deque
        //pop the currentState
      //else the pointer character doesnt match
        //pop the currentState
    //when there are no more currentStates to check
      //transferStates on the Deque
      //increment pointer

    //if there are nextStates then repeat

    //if there are no nextStates there were no matches

    //if at any point -1 is a currentState then there was a full pattern match
      //output the line


  }

}
