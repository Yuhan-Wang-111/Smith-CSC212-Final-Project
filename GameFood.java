import java.util.Scanner;
/**
  *This game asks the player to choose the foods that are toxic to cats 
  *The total is 15, and toxic ones are 7
  *The user needs to pick out 5 toxic ones
  *The user will pass anyway, it's for them to learn what are things toxic for cats
  *@author Yuhan Wang
  */
class GameFood{
  /**scanner*/
  public Scanner sc = new Scanner(System.in);

  /**count to count how many right guesses*/
  public int count = 0;

  /** empty constructor*/
  public GameFood(){
    
  }

  /** prints out the start and end of the game*/
  public void play(){
    //start
    System.out.println("\nWelcome to Food Challenge! We will have 20 foods here. Some of them are toxic to cats, and your job is to find out the ones that are dangerous to cats! You will need to pick out 5 toxic food! \n");
    System.out.println("The food we have here are:\n1.fish\n2.beef\n3.milk\n4.alcohol\n5.chicken\n6.coffee\n7.grape\n8.avocado\n9.egg\n10.pork\n11.crab\n12.nut\n13.chocolate\n14.turkey\n15.cheese\n");
    System.out.println("Please enter the NUMBER for the ones you think are toxic one at a time.");

    //play
    while (count<5){
      playGame();
    }
    
    //end
    System.out.println("\nYou got five!\n");
    System.out.println("The toxic ones are: \n3.milk\n4.alcohol\n6.coffee\n7.grape\n8.avocado\n12.nut\n13.chocolate");
  }

  /** checks the answer and continues until the user hits 5 correct answer*/
  public void checkAnswer(int ans){
    if(ans==3||ans==4||ans==6||ans==7||ans==8||ans==12||ans==13){
      count+=1;
      System.out.println("Yes you are right. " + count + " already.");
    } else {
      System.out.println("No kitties can have this.");
    }
  }

  /** takes input and proceed*/
  public void playGame(){
    try {
      int ans = sc.nextInt();
      checkAnswer(ans);
    } catch (Exception e){
      System.out.println("Please enter a number");
      String s = sc.nextLine(); // holds the input to allow next input
    }
  }
  
}