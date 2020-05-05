import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

class Deque {

  private Stack currentStates;
  private Queue<Integer> nextStates;

  //Constructors
  public Deque(){
    currentStates = new Stack();
    nextStates = new LinkedList<Integer>();

    //set SCAN
    currentStates.push("SCAN");
  }

  public String toString(){
    Object[] nxt = nextStates.toArray();
    String s = "***\n" + currentStates.toString();
    s += "\n---\n";
    for(Object ob: nxt){
      s += " " + ob.toString();
    }
    s += "\n***";
    return s;
  }

  public boolean isEmpty(){
    return (nextStates.peek() == null && currentStates.peek() == "SCAN");
  }

  //Add a state into the current states
  public void push(int state){
    currentStates.push(state);
  }

  //pop off the next state and return it
  public Object get(){
    return currentStates.pop();
  }

  //adds a state to the queue of next states
  public void put(int state){
    nextStates.add(state);
  }

  //puts the states in nextStates into the currentStates
  public void transferStates(){
    currentStates = new Stack();
    currentStates.push("SCAN");
    while(nextStates.peek() != null){
      currentStates.push(nextStates.remove());
    }
  }


}
