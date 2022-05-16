/**
  *Class that builds each object
  *@author Yuhan Wang
  *@version Spring 2022
  */
class Thing{

  /** thing name*/
  String thing;

  /** constructor that builds the character*/
  public Thing(String thing) {
    this.thing = thing;
  }

  /** printing method*/
  public String toString() {
    return this.thing;
  }
  
}