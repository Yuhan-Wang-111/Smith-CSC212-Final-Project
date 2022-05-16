import com.google.common.base.Strings;
import com.google.common.graph.*;
import com.google.common.graph.ElementOrder.Type;
import java.util.Scanner;
import java.util.Iterator;
/**
  *Class that sets up the graph for the Three Flower Village and runs the game
  *@inherit MainWorld
  *@author Yuhan Wang
  *@version Spring 2022
  */
class ThreeFlowerVillage extends MainWorld{
  /** public scanner*/
  public Scanner sc = new Scanner(System.in);
  /** String that represents the input*/
  public String in;
  public String character;
  public String thing;
  /** iterator that iterates current adjacent nodes*/
  public Iterator<Character> iterator;
  /** boolean that says whether one has the flower*/
  public Boolean hasFlower = false;

  /** things inthree flower village*/
  public static Thing catTeaser = new Thing("Cat Teaser");
  public static Thing pan = new Thing("pan");
  public static Thing bossSeat = new Thing("Boss Seat");
  public static Thing piggyHimself = new Thing("piggy himself");
  public static Thing flower = new Thing("a beautiful b undle of flowers");
  
  /** main character shadow*/
  public static Character you = new Character("You", null, catTeaser);
  
  /** characters inthree flower village*/
  public static Character sCat = new Character("Smith Cat", "Very intelligent cat", pan);
  public static Character oCat = new Character("Orange Cat", "Enjoy cooking", null);
  public Character vCat = new Character("Valet Cat","always want to be boss",null);
  public Character piggy = new Character("Piggy", "Cutest cat in the world!!!! No one can resist petting him", piggyHimself);
  public Character threeFlower = new Character("Three Cat and Flower Guy","piggy is their only and favorite son", flower);

  /** Three Flower Village graph*/
    public ImmutableGraph<Character> gThree = GraphBuilder.directed()
      .<Character>immutable()
      .putEdge(sCat,vCat)
      .putEdge(sCat,oCat)
      .putEdge(vCat,piggy)
      .putEdge(oCat,piggy)
      .putEdge(piggy,threeFlower)
      .build();

  /** empty constructor*/
  public ThreeFlowerVillage(){
    
  }
  
  /**
    calls all the functions and continues this part
    @return boolean if user passes the game
    */
  public boolean playTFV(){
    this.start();
    this.now(sCat);
    return this.hasFlower;
  }

  /**
    *starts the three flower village
    *@override start method in MainWorld
    */
  public void start(){
    //printing info
    System.out.println("\nWelcome to the Three Flower Village. This is a world where you will have the chance to get the flower for Tuna!");
    super.proceed();
    System.out.println("\nThis space is one-way only. So you need to be careful which way to choose~~~");
    super.proceed();
    System.out.println("\nOkay here we go!");
    // add a thing to sCat because I can't add when intializing
    sCat.addThing(bossSeat);
  }

  /** 
    *each character's interaction
    *@override now method in MainWorld
    *@param character user is at now
    */
  public void now(Character c){
    // print out info of current situation
    System.out.println("\nYou are in front of " + c);
    System.out.println("you has: " + you.getThing());
    if (c == threeFlower){ // endpoint of this world
      System.out.println("\nYou make it to Three Cat and Flower Guy!!!\n");
      if (you.hasThing(piggyHimself)){ 
      //success situation: have piggy and can get flower
        threeFlower.removeThing(flower);
        you.addThing(flower);
        this.hasFlower = true;
        System.out.println("You have the flower! They love piggy so much that if you give them piggy, they will never hold the flowers anymore. So good job!");
      } else {
        // lose this world and get out
        System.out.println("Sorry you miss the chance this time...Plz try next time haha");
      }
    } else { // when not at the end point
      if (c == piggy){ // at character piggy
        System.out.println("Please enter the right words:\n");
        in = sc.nextLine().toLowerCase();
        if(!in.equals("pet piggy's butt")){
          // magic word to pass
          System.out.println("Hint: piggy likes people to pet his butt\n");
          in = sc.nextLine().toLowerCase();
          if(!in.equals("pet piggy's butt")){ // fail to type in the magic word
            System.out.println("You lost the chance. Piggy is rushing you to the next station");
            super.proceed();
            this.now(threeFlower); // continue to end point
          }   
        }
        // if gets right the magic word
        System.out.println("You make Piggy sooooooo comfortable.\nPiggy becomes yours now");
        you.addThing(piggyHimself);
        piggy.removeThing(piggyHimself);
        // get piggy
        System.out.println("He is now sending you to Three Cat and Flower Guy");
        super.proceed();
        this.now(threeFlower); // continue to endpoint
      } else if (c == sCat && (you.hasThing(pan) || you.hasThing(bossSeat))){ // second step after meeting sCat when user has taken one of the objects
        System.out.println("Smith Cat says: Okay. Now pick a cat to go to"); 
        this.now(super.next(c,gThree));
      } else if (c == sCat){ // first step when meeting sCat
        System.out.println("Smith Cat says: I have two things. You can only pick one. And then you need to decide which of the two ways to go. These is only one chance...(Since you are a Smithie, I can tell you, think about what each character want...)");
        super.proceed();
        this.now(super.next(c,gThree));
      } else if (c == oCat){ // situations at oCat
        in = sc.nextLine().toLowerCase();
        if (!in.equals("give pan")||!you.hasThing(pan)){
          // if not given the pan
          System.out.println("Wrong choice! Next Station then. No hint haha.");
          super.proceed();
        } else { // fail
          you.removeThing(pan);
          oCat.addThing(pan);
          System.out.println("Correct Choice! Orange Cat is sharing a secret to you:\nYou need to type in 'pet piggy's butt'...");
          super.proceed();
        }
        // continue to piggy
        this.now(piggy);
      } else if (c == vCat){ // situations at vCat
        in = sc.nextLine().toLowerCase();
        if (!in.equals("give boss seat")&&!in.equals("give seat")||!you.hasThing(bossSeat)){
          // if not given the seat
          System.out.println("Wrong choice! Next Station then. No hint haha.");
          super.proceed();
        } else { // fail
          you.removeThing(pan);
          oCat.addThing(pan);
          System.out.println("Correct Choice! Valet Cat is sharing a secret to you:\nYou need to type in 'pet piggy's butt'...");
          super.proceed();
        }
        // continue to piggy
        this.now(piggy);
      }
    } 
  }

  /** 
    *takes in string and returns the corresponding character
    *@override checkCharacter method in MainWorld
    *@param string to be analyzed
    *@return character to go next
    */
  public Character checkCharacter(String s){
    // analyze which direction to go
      if (s.equals("valet cat")||s.equals("valet")){
        return vCat;
      } else if (s.equals("orange cat")||s.equals("orange")){
        return oCat;
      } else if (s.equals("piggy")){
        return piggy;
      } else{
        // will stay at the same place because null is not in the successors
        return null;
      }
  }

  /** 
    *takes in string and takes the corresponding object
    **@override checkThing method in MainWorld
    */
  public void checkThing (String s){
    // analyze things and print out picking situation
    if (s.equals("pan")){ // remove pan and boss seat because game only allow user to pick one
        you.addThing(pan);
        sCat.removeThing(pan);
        sCat.removeThing(bossSeat);
        System.out.println("\nPan picked.");
    } else if (s.equals("boss seat")||s.equals("seat")){
        you.addThing(bossSeat);
        sCat.removeThing(bossSeat);
        sCat.removeThing(pan);
        System.out.println("\nBoss Seat picked.");
    } else if (s.equals("piggy")||s.equals("piggy himslef")||s.equals("himslef")){
        you.addThing(piggyHimself);
        System.out.println("\nPiggy picked.");
    } else {
      System.out.println("I don't understand. What r u saying???");
    }
  }
  
}