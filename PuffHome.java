import com.google.common.base.Strings;
import com.google.common.graph.*;
import com.google.common.graph.ElementOrder.Type;
import java.util.Scanner;
import java.util.Iterator;
/**
  *Class that sets up the graph for Puff Home and runs the game
  *@inherit MainWorld
  *@author Yuhan Wang
  *@version Spring 2022
  */
class PuffHome extends MainWorld{

  /** public scanner*/
  public Scanner sc = new Scanner(System.in);
  /** String that represents the input*/
  public String in;
  public String character;
  public String thing;
  /** iterator that iterates current adjacent nodes*/
  public Iterator<Character> iterator;

  /** things in puff home*/
  public static Thing catTeaser = new Thing("Cat Teaser");
  public static Thing food = new Thing("food");
  public static Thing hammer = new Thing("hammer");
  public static Thing screwdriver = new Thing("screwdriver");
  public static Thing song = new Thing("song");
  public static Thing answer = new Thing("Answers to a mysterious challenge");
  
  /** main character shadow*/
  public static Character you = new Character("You", null, catTeaser);
  
  /** characters in puff home*/
  public static Character puff = new Character("Cat Puff", "loves cat teaser", food);
  public static Character rice = new Character("Cat Rice", "Never rejects food", hammer);
  public Character fang = new Character("Fang Fang",null,screwdriver);
  public Character iron = new Character("Ironwoman", "Loves iron-made things", answer);
  public Character mian = new Character("Beauty Mian",null, song);

  /** Puff Home graph*/
    public ImmutableGraph<Character> gPuff = GraphBuilder.directed()
      .<Character>immutable()
      .putEdge(puff,fang)
      .putEdge(fang,puff)
      .putEdge(fang,iron)
      .putEdge(iron,fang)
      .putEdge(puff,rice)
      .putEdge(rice,iron)
      .putEdge(iron,mian)
      .putEdge(mian,puff)
      .build();
  
  /**constructor that builds the graph for MainWorld part*/
  public PuffHome(){
    
  }

  /**calls all the functions and continues this part*/
  public void play(){
    this.start();
    this.now(puff);
  }

  /**
    *starts the puff world
    *@override start method in MainWorld
    */
  public void start(){
    //printing info
    System.out.println("\nWelcome to the Puff Home. This is a world where you will have the chance to get the song for your professing love!");
    super.proceed();
    System.out.println("\nYou can move more freely in this world. Note that some of the objects may not be useful, but good luck! Remember that song is ond of Tuna's favorite things!");
    super.proceed();
    System.out.println("\nOkay here we go!");
  }

  /** 
    *each character's interaction
    *@override now method in MainWorld
    *@param character user is at now
    */
  public void now(Character c){
    if (c == mian){ // ending point in this graph
      this.playGame();
    } else {
      System.out.println("\nYou are in front of " + c);
      System.out.println("you has: " + you.getThing());
      if (c == puff){
        if (!puff.hasThing(catTeaser)){ // force user to give teaser and go
          in = sc.nextLine().toLowerCase();
          while (!in.equals("give teaser")&&!in.equals("give cat teaser")){
          System.out.print(c.getName()+ " " + c.getFeature() + "\n");
          in = sc.nextLine().toLowerCase();
          }
          you.removeThing(catTeaser);
          puff.addThing(catTeaser);
          System.out.println("\nPuff is very happy!");
        }  
      } else if (c == rice){
        if (!rice.hasThing(food)){
          in = sc.nextLine().toLowerCase();
          if (!in.equals("give food")||!you.hasThing(food)){ // force user to have and give food
            System.out.println("Sorry. You need to have food and give him the food.\nI will direct you to puff to start again");
            this.now(puff);
          } else {
            you.removeThing(food);
            rice.addThing(food);
            System.out.println("Rice is happy.");
          }  
        }  
      } else if (c == iron){
        System.out.println("Ironwoman has this answer that might be useful to you. You will need to give her a hammer in trade. Do you want to give hammer?");
        in = sc.nextLine().toLowerCase();
        if (in.equals("yes") && you.hasThing(hammer)){ // have and give hammer
          you.removeThing(hammer);
          iron.addThing(hammer);
          iron.removeThing(answer);
          System.out.println("Iron woman is satisfied. She gives you this note that writes:\n Cats can not have avocado, grape, coffee, milk, and chocolate.");
        } else if (in.equals("yes") && !you.hasThing(hammer)){ // do not have hammer but wnat to give
          System.out.println("Sorry, you do not have the hammer now.");
        } else { 
          System.out.println("Okay. Please continue playing...");
        }
      } 
      this.now(super.next(c,gPuff));
    }   
    System.out.println("You are now leaving puff home...");
  }  

  /** calls game food and end this class*/
  public void playGame(){
    System.out.println("\nCongratulations! You find Beauty Mian, who has the most beautiful song in the world.\nMian asks if you want to take a challenge and if you win, you will have the song.");
    in = sc.nextLine().toLowerCase();
    if (!in.equals("y")||in.equals("yes")){
      System.out.println("\nYou have to play. Plz give the author some respect txs...");
    }
    GameFood gf = new GameFood();
    gf.play();  // pass anyway
    System.out.println("\nYou win! Now you have the song in your pocket!\nI will direct you back to the main world now.\n");
  }

  /** 
    *takes in string and returns the corresponding character
    *@override checkCharacter method in MainWorld
    *@param string to be analyzed
    *@return character as the next place to go
    */
  public Character checkCharacter(String s){
    Character c;
      if (s.equals("puff")||s.equals("cat puff")){
        c = puff;
      } else if (s.equals("rice")||s.equals("cat rice")){
        c = rice;
      } else if (s.equals("fang")||s.equals("fang fang")||s.equals("fangfang")){
        c = fang;
      } else if (s.equals("iron")||s.equals("ironwoman")){
        c = iron;
      } else if (s.equals("mian")||s.equals("beauty mian")){
        c = mian;
      } else {
        c = null;
      }
    return c;
  }

  /** takes in string and takes the corresponding object*/
  public void checkThing (String s){
    if (s.equals("teaser") || s.equals("cat teaser")){
      System.out.println("You can not take that fro puff... This is so cruel.");
    } else if (s.equals("food")){
      if (you.hasThing(food)){
        System.out.println("\nFood already picked.");
      } else {
        you.addThing(food);
        puff.removeThing(food);
        System.out.println("\nFood picked.");
      } 
    } else if (s.equals("hammer")){
      if (you.hasThing(hammer)){
        System.out.println("\nHammer already picked.");
      } else {
        you.addThing(hammer);
        rice.removeThing(hammer);
        System.out.println("\nHammer picked.");
      }
    } else if (s.equals("screwdriver")){
      if (you.hasThing(screwdriver)){
        System.out.println("\nScrewdriver already picked.");
      } else {
        you.addThing(screwdriver);
        fang.removeThing(screwdriver);
        System.out.println("\nScredriver picked.");
      }
    } else {
      System.out.println("I don't understand. What r u saying???");
    }
  }

}