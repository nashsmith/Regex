
public class REsearch {

  public static void main(String[] args){

    public static List<Character> ch = new ArrayList<Character>();
    public static List<Integer> next1 = new ArrayList<Integer>();
    public static List<Integer> next2 = new ArrayList<Integer>();

    public static int startState;
    public static int mark;
    public static int pointer;
    public static Deque states;

    String fsmFileName = args[0];
    String textFileName = args[1];


    //read FSM data and initialise ArrayLists
    importFSM(fsmFileName);

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

  /*
   *  Takes a text file from REcompile and turns it into the three ArrayLists
   *  representing the Finite State Machine.
   */
  public void importFSM(String fileName){
    File file = new File(fileName);
    BufferedReader fsm = new BufferedReader(new FileReader(file));
    String line;
    Boolean firstRead = true;

    while((line = fsm.readLine()) != null){
      if(firstRead){
        startState = Integer.parseInt(line);
        firstRead = false;
      }
      String[] values = line.split(",");
      Integer stateNum = Integer.parseInt(values[0]);
      char c = values[1].charAt(0);
      Integer n1 = Integer.parseInt(values[2]);
      Integer n2 = Integer.parseInt(values[3]);

      ch.add(stateNum, c);
  		next1.add(stateNum, n1);
  		next2.add(stateNum, n2);
    }

  }

}
