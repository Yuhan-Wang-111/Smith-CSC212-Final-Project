import java.util.Scanner;
/**
  *This game asks the player to answer questions about how to treat alley cats
  *The user needs to answer right all of them
  *@author Yuhan Wang
  */
class GameAlleyCat{
  /**scanner*/
  public Scanner sc = new Scanner(System.in);
  public String in;

  /**count to count how many right guesses*/
  public int count = 0;

  /** empty eonstructor*/
  public GameAlleyCat(){

  }

  /** 
    * plays the game and feedback result
    *@return boolean whether the user passes this game
    */
  public boolean play(){
    System.out.println("\nWelcome to AlleyCat Questions! You are now facing a huge group of alley cats. They are questioning your ability to lead them. You need to answer some questions to become their leader.\n");
    proceed();
    if(question()){
      System.out.println("You are right.");
      System.out.println("You successfully have a crew of 100 alley cats as your singing chorus~~~");
      return true;
    } else {
      System.out.println("You are wrong. Huh. The alley cats think you are so stupid and left just now.");
      return false;
    }
  }

  /** 
    *prints the question and takes result
    *@return boolean whether the answer is correct
    */
  public boolean question(){
    // assign values in case user does not enter number
    int num1 = 0;
    int num2 = 0;
    System.out.println("One of the cat stepped to front and said:\nWe are a group of 100 alley cats. Each of us need 300 calories one day. One fish contains 350 calories; one mouse has 50 calories. To ensure we hunt for exactly the calories we need, how many fish and mice as a Combination should we have?\n ");
    System.out.println("Please enter the number of fish needed:");
    try { // if user does not enter number
      num1 = sc.nextInt();
      System.out.println("Please enter the number of mice needed:");
      num2 = sc.nextInt();
    } catch (Exception e){
      System.out.println("You did not enter numbers.");
    }
    int num = num1*350+num2*50;
    System.out.println(num1 + " * 350 + " + num2 + " * 50 = " + num +"\n");
    return (num==30000);
  }

    /** a stop for the user to press c and continue the game, give the user a time to read through*/
  public void proceed(){
    System.out.print("Press 'c' to continue.");
    in = sc.nextLine().toLowerCase();
    while (!in.equals("c")){
      System.out.print("Press 'c' to continue.");
      in = sc.nextLine().toLowerCase();
    }
  }   
}