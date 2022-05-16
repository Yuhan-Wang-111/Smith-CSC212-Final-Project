import java.util.ArrayList;
/**
  *Class that builds each character
  *@author Yuhan Wang
  *@version Spring 2022
  */
class Character{

  /** name of the character*/
  String name;
  /** feature of the character*/
  String feature;
  /** arraylist to store the objects one character has*/
  ArrayList<Thing> thingList = new ArrayList<>();

  /** constructor that builds the character*/
  public Character(String name,String feature,Thing thing) {
    this.name = name;
    this.feature = feature;
    if (thing != null){ // in case one character has no object
      this.thingList.add(thing);
    }  
  }

  /** printing method*/
  public String toString() { 
    if (this.feature == null && this.thingList.isEmpty()){
      return name;
    } else if (this.feature == null){
      return name + " + " + this.getThing();
    } else if (this.thingList.isEmpty()){
      return name + " (" + feature + ")";
    } else {
      return name + " (" + feature + ") + " + this.getThing();
    }
  }
  
  /** print name*/
  public String getName(){
    return this.name;
  }

  /** print feature*/
  public String getFeature(){
    if (feature.equals(null)){
      return "/";
    } else {
      return this.feature;
    }
  }

  /**print object*/
  public String getThing(){
    return thingList.toString();
  }

  /**check if one has the object*/
  public boolean hasThing(Thing thing){
    return(thingList.contains(thing));
  }

  /**add object*/
  public void addThing(Thing thing){
    this.thingList.add(thing);
  }

  /**remove object*/
  public void removeThing(Thing thing){
    this.thingList.remove(thing);
  }
  
}