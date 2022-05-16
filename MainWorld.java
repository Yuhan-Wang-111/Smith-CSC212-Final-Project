import com.google.common.base.Strings;
import com.google.common.graph.*;
import com.google.common.graph.ElementOrder.Type;
import java.util.Scanner;
import java.util.Iterator;
/**
  *Class that sets up and runs the game
  *@author Yuhan Wang
  *@version Spring 2022
  */
class MainWorld{
  /** public scanner*/
  public Scanner sc = new Scanner(System.in);
  /** String that represents the input*/
  public String in;
  /** String that represents the character, thing to be entered by the user and analyzed*/
  public String character;
  public String thing;
  /** iterator that iterates current adjacent nodes*/
  public Iterator<Character> iterator;
  
  /** boolean for build shelter at tyrant's place*/
    public Boolean shelter = false;
  
  /** things in main world*/
  public static Thing catTeaser = new Thing("Cat Teaser");
  public static Thing wood = new Thing("wood");
  public static Thing chorus = new Thing("A group that forms a Kitty Chorus");
  public static Thing song = new Thing("Beauty Mian's beautiful song~~~");
  public static Thing flower = new Thing("a beautiful b undle of flowers");
  
  /** characters in main world*/
  public Character you = new Character("You", null, catTeaser);
  public Character center = new Character("Center", null, null); // representing you's position
  public static Character tuna = new Character("Tuna", "Love cats, song, and flower", null);
  public Character king = new Character("Cat King", "Needs to be served", null);
  public Character tyrant = new Character("Tyrant", "Want to protect the alley cats", wood);
  public Character alley_cat = new Character("Alley Cat", "Want to find a human leader", chorus);

  /** Main World graph*/
  public ImmutableGraph<Character> gMain = GraphBuilder.directed()
    .<Character>immutable()
    .putEdge(center, tuna)
    .putEdge(tuna, center)
    .putEdge(center, king)
    .putEdge(king, center)
    .putEdge(king, tyrant)
    .putEdge(tyrant, alley_cat)
    .putEdge(alley_cat,king)
    .build();

  /** mainworld constructor*/
  public MainWorld(){
    
  }
    /**calls all the functions and continues this part*/
  public void play(){
    this.start();
    this.now(center);
  }

  /**starts the three flower village*/
  public void start(){
    //printing info
    System.out.println("Welcomd to the Kitty World! You are in your 20s, and you have a crush called Tuna. ");
    proceed();
    System.out.println("\nLet's meet Tuna now. Please enter 'To Tuna'");
    
    in = sc.nextLine().toLowerCase();

    //asks the user to input
    while(!in.equals("to tuna")){
      System.out.println("Please enter To Tuna for us to start the game. Come on!");
      in = sc.nextLine().toLowerCase();
    }
    System.out.println(tuna);

    //prints out rule
    System.out.println("\nThings in () means the feature of the character, and things in [] means the object one have. No [] means the character has nothing.");
    System.out.println("\nYou will see the adjacent characters are listed here. You can enter 'To (name)' to go to that character.");
    proceed();
    System.out.println("\nYou will need to take and give things. Plz just type 'take/give (thing name)' because I am lazy :D\nMake sure you see '(item name) picked' to indicate that you successfully have it.");
    proceed();
    System.out.println("\nNow you know Tuna. Your job is to explore this kitty world and collect things that would help you to profess your love to her~~~<3<3\n");
    proceed();
    //provide support if needed
    System.out.println("\nDo you wanna see a small cheat guide before start your own exploration?");
    in = sc.nextLine().toLowerCase();
    if (in.equals("yes")||in.equals("y")){
      System.out.println("\nThis game has three areas: MainWorld(here), Puff Home, and Three Flower Village. In each of the world, you will need to take/give things with characters you meet, and then move to different characters. You can confess anytime when you are in the mainworld, but only when you have flower, song, and a kitty chorus, you can confess successfully.");
      proceed();
      System.out.println("\nIf you don't know what to do, you can enter 'help' and get help most of the times. If you enter help and does not get help, you may want to look at the hints and respond to the hints.\n  Note that you can always exit when you type 'exit'. The process will have no record, but you can just leave ;o;");
      proceed();
    }
    // starts game
    System.out.println("\nOkay. Now let's go back to the starting point and start the game!");
    proceed();
  }
  
  /** 
    * each character's interaction
    *@param the next current character
    */
  public void now(Character c){
    // if statements that count for each character's situation
    if (c == center){
      System.out.println("\nYou are at the starting point.");
      System.out.println("Do you want to enter Puff Home or Three Flower Village? These two are different worlds, and you can not exit easily \n(please type 'yes' if you want to)"); 
      in = sc.nextLine().toLowerCase();
      if (in.equals("y")||in.equals("yes")){ // to another world
        System.out.println("Please enter the world name you want to go:");
        in = sc.nextLine().toLowerCase();
        if (in.equals("puff home")){ // call puff home world
          PuffHome puffHome = new PuffHome();
          puffHome.play();
          this.you.addThing(song); // pass anyway so add the user the song earned from playing
        } else if (in.equals("three flower village")){ // call three flower world
          ThreeFlowerVillage tfV = new ThreeFlowerVillage();
          if(tfV.playTFV()){ // if the user passes the challenge
            this.you.addThing(flower);
          }
        } else { // unrecognizable place
          System.out.println("Please enter a correct name.");
        }
        this.now(center);//back to main world
      } else { // stays at center
        System.out.println("\nOkay. You can explore the main world right now.");
      }
    } else { // if not at center, would print out character the user is interacting with now
    System.out.println("\nYou are in front of " + c);
    }  
    System.out.println("\nyou has: " + you.getThing());
    if (c == tuna){
      System.out.println("Are you ready to confess?");
      in = sc.nextLine().toLowerCase();
      if (in.equals("yes")||in.equals("y")){ // to ending check
        this.confess();
      } else {
        this.now(center); // back to center place
      }
    } else { // other normal characters
      if (c == king){
        in = sc.nextLine().toLowerCase();
        // hints the user to input "serve king"
        while(!in.toLowerCase().equals("serve king")&&!in.toLowerCase().equals("serve cat king")){
          System.out.println(king);
          in = sc.nextLine();
        }
      System.out.println("The king is satisfied.");
      } else if (c == tyrant){ 
        in = sc.nextLine().toLowerCase();
      while (!this.shelter){ // if shelter is not built
        if (this.takeWood(in)){
          this.you.addThing(wood);
          this.tyrant.removeThing(wood);
          System.out.println("\nWood picked.");
        } else if (in.toLowerCase().equals("build shelter") && this.you.hasThing(wood)){ // build shelter
          this.shelter = true;
          break;
        } else if(in.toLowerCase().equals("build shelter") && !this.you.hasThing(wood)){ // if has no wood to build shelter
          System.out.println("\nYou have nothing to build a shelter.");
          this.now(c);
        } else { // force user to build shelter
          System.out.println("\nLocal tyrant needs you to build shelter for the alley cat community");
        }
        in = sc.nextLine().toLowerCase();
      }
      this.you.removeThing(wood);
      System.out.println("\nShelter built. Congratulations. You are now free to go.");
      } else if (c==alley_cat){
        System.out.println("Alley cats are a very impressive crew of singer cats. Do you want to have them as your chorus?");
      in = sc.nextLine().toLowerCase();
        if (in.equals("yes")||in.equals("y")){ // play game
          System.out.println("\nOkay. They ask you to do this small quiz.");
          GameAlleyCat gac = new GameAlleyCat();
          if(gac.play()){
            this.you.addThing(chorus);
          }
        } else { // does not play
          System.out.println("\nOkay. You really are not playing the game huh.");
        }
      }
      this.now(this.next(c,gMain));
    }  
  }
  
  /** see if the user has picked up the wood*/
  public boolean takeWood(String s){
    return(s.toLowerCase().equals("take wood")||s.toLowerCase().equals("pick wood"));
  }

  /** final confession part*/
  public void confess(){
    System.out.println("\nYou are preparing...");
    if(this.you.hasThing(flower)&&this.you.hasThing(song)&&this.you.hasThing(chorus)){ // ending 1: flower, song, chorus
      System.out.println("\nYou take the flower, gently start singing the song by Beauty Mian with the chorus of cats.\nTuna is really happy and says 'yes. I will be your girlfriend~~~'\nCongratulations! Ending 1: Happily living together forever");
    } else if (!this.you.hasThing(chorus) && !this.you.hasThing(flower)){ // ending 2: song
      System.out.println("\nI do not allow you to confess. You have nothing but a song that I allow you to have... What are you doing come on ...\n Ending 2: author is furious");
    }else if (!this.you.hasThing(flower)){ // ending 3: song, chorus
      System.out.println("\nYou gently start singing the song by Beauty Mian with the chorus of cats.\nTuna is really happy and says 'I love your song. I would love to have a date night with you'\nCongratulations! Ending 3: a Maybe? but you are the leader of 100 alley cats!");
    } else if (!this.you.hasThing(chorus)){ // ending 4: flower, song
      System.out.println("\nYou gently start singing the song by Beauty Mian with a flower.\nTuna says 'I love your song and the flower.' and left.\n Ending 4: You need cats! No one can resist cats, but you do not have one.'");
    } else { // force user to go back
      System.out.println("\nYou do not have the song in puff home.\nYou can have it if yo go there. Please go back to get the song'");
      this.now(center);
    } 
  }
  
  /**
    *takes in input and analyzes what user wants to do
    *@param the character user is at now, the graph user is in now
    *@return the next character user chooses to go
    */
  public Character next(Character c, ImmutableGraph<Character> g){
    // assign the next place as current one in case unrecognizable place
    Character nextC = c;
    // display adjacents
    this.adjacents(c,g); // display adjacent characters
    in = sc.nextLine().toLowerCase();
    // analyze the input
    if (in.equals("exit")){ // exit
      System.exit(1);
    } else if (in.equals("help")){ // help
      System.out.println("\nIf you want to go to another character, type 'to (name)'\nIf you want to take things, type 'take (sth)'\nIf you can not to or take at this moment, you may have to type in some special commands. It may be 'give (sth)' or hints will be provided in the program.");
    } else if (in.length()>5){
      if (in.substring(0,3).equals("to ")){ // change direction
        character = in.split(" ",2)[1];
        nextC = this.checkCharacter(character);
        // see if the place is nearby and direct
        if (!g.successors(c).contains(nextC)){
          nextC = c;
          System.out.println("The character is not recognizable/available to go to now.");
        }
      } else if (in.substring(0,5).equals("take ") || in.substring(0,5).equals("pick ")){ // take thing
        thing = in.split(" ",2)[1];
        this.checkThing(thing);
      }
    } else {
      System.out.println("I don't understand. What r u saying???");
    }
    System.out.println();
    return nextC;
  }

  /** takes in string and returns the corresponding character*/
  public Character checkCharacter(String s){
    // analyze which direction to go
      if (s.equals("cat king")||s.equals("king")){
        return king;
      } else if (s.equals("alley cat")){
        return alley_cat;
      } else if (s.equals("tyrant") || s.equals("cat tyrant")){
        return tyrant;
      } else if (s.equals("tuna")){
        return tuna;
      } else if (s.equals("center")){
        return center;
      } else {
        // will stay at the same place because null is not in the successors
        return null;
      }
  }

  /** takes in string and takes the corresponding object*/
  public void checkThing (String s){
    // analyze things and print out picking situation
    if (s.equals("wood")){
        you.addThing(wood);
        tyrant.removeThing(wood);
        System.out.println("\nWood picked.");
    } else {
      System.out.println("I don't understand. What r u saying???");
    }
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

  /** 
    *prints out adjacent locations
    *@param the current character user is at, and the graph user is in
    */
  public void adjacents(Character c,ImmutableGraph<Character> g){
    // iterator to iterate nodes adjacent
    iterator = g.successors(c).iterator();
    System.out.print("Around: ");

    // prints out available characters around
    while (iterator.hasNext()) {
      System.out.print(iterator.next().getName() + ". ");
    }
    System.out.println();
  }

}