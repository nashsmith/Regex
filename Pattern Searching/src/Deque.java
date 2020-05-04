class Deque {

  private Stack currentStates;
  private Queue nextStates;

  //Constructors
  public Deque(){
    currentStates = new Stack();
    nextStates = new Queue();

    //set SCAN
    currentStates.push("SCAN");
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
