import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class F1ReactionGame extends PApplet {

GameProgression a;

int i = 0;

public void setup ()
{
  frameRate(999);
 
  
  
  print("Game Started!\n");
  
  a = new GameProgression();
  
  //a.loadPlayers();
  
  //a.sortPlayers();
  
  //a.printAllPLayers();
}

public void draw ()
{
  background(255);
  
  if(a.getState() == "menu")
     printInitialMenu();
  
  else if(a.getState() == "turnOn")
  {
    a.getNewEnd();
  
    if(a.getElapsedTime() >= 1000)
    {
      a.moreLight();
    
      a.getNewStart();
    }
  
    for(int x = 150, lvl = 0, y = 500; x <= 650; x += 125)
    {
      fill(10);
    
      rect(x, y, 75, 300, 15, 15, 15, 15);
    
      if(lvl > a.light - 1 && lvl < 6)
        fill(100);
      else
        fill(255, 0, 0);
      
      ellipse(x, y + 26, 64, 64);
    
      ellipse(x, y + 100, 64, 64);
    
      lvl++;
      
      //print("lvl = " + lvl + "\n");
    }
    if(a.getLights() == 5)
    {     
      a.getNewStart();
       
      a.nextState();
    }
  }
  else if(a.getState() == "waitingLightsOff")
  {
    a.getNewEnd();
    
    if(a.getElapsedTime() <= a.getFinish())
    {
      //print("Time elapsed = " + (end - start) + " ms\n");
      
      for(int x = 150, y = 500; x <= 650; x += 125)
      {
        fill(10);
    
        rect(x, y, 75, 300, 15, 15, 15, 15);
      
        fill(255, 0, 0);
      
        ellipse(x, y + 26, 64, 64);
    
        ellipse(x, y + 100, 64, 64);
      }
    }
    else
    {
      //print("LIghts OUT!\n");
      
      a.nextState();
      
      a.resetLights();
      
      a.getNewStart();
    }
  }
  else if(a.getState() == "jump")
    jumpStart();
  else if(a.getState() == "waitingPlayerReaction")
  {
    a.getNewEnd();
    
    printTimeElapsed();
  }
  else if(a.getState() == "finalResult")
  {
    printFinalResult();
  }
  //printVariablesState ();
}

public void keyPressed()
{
  if(key == ' ' && a.getState() == "menu")
  {
    a.getNewStart();
    
    a.nextState();
    
    a.getNewFinish();
    
    //print("Finish = " + finish + " ms\n");
  }
  else if(key == ' ' && a.getState() == "waitingPlayerReaction")
  {
    a.result = a.diff;
    
    a.nextState();
  }
  else if(key == ' ' && (a.getState() == "turnOn" || a.getState() == "waitingLightsOff"))
  {
    a.setJumpStartState();
  }
  else if(key == ' ' && a.getState() == "jump")
  {
    a.nextState();
    
    a.resetLights();
  }
  else if(key == ' ' && a.getState() == "finalResult")
  {
    a.nextState();
  }
}

public void printInitialMenu()
{
  //Player[] s = a.getBest();
  
  textSize(32);
  
  textAlign(CENTER, CENTER);
  
  text("Press SPACE to try! Press SPACE again to Reset", 400, 200);
  
  rectMode(CENTER);
  
  for(int x = 150, y =500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
}

public void printTimeElapsed()
{
  textSize(72);
  
  a.diff = a.getDiff();
  
  text(a.diff, width / 2 - 100, 200);
  
  rectMode(CENTER);
  
  for(int x = 150, y =500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  i++;
  print(i + "\n");
  //saveFrame("frame###.jpg");
}

public void printFinalResult()
{
  textSize(48);
  
  textAlign(CENTER, CENTER);
  
  text("You took:\n", width / 2, 200);
  
  text(a.result + "s\n", width / 2, 300);
  
  rectMode(CENTER);
  
  for(int x = 150, y =500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  
  text("Press SPACE again\n", width / 2, height - 48);
  
  i = 0;
}

public void jumpStart()
{
  textSize(48);
  
  textAlign(CENTER, CENTER);
  
  text("JUMP START!\n", width / 2, 200);
  
  rectMode(CENTER);
  
  for(int x = 150, y = 500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  
  text("Press SPACE again\n", width / 2, height - 48);
}

public void printVariablesState ()
{
  print("\n\n\nlight = " + a.light + "\n");
  
  print("start = " + a.start + " ms\n");
  
  print("end = " + a.end + " ms\n");
  
  print("finish = " + a.finish + " ms\n");
}
class GameProgression
{
  int stateIndex;
  
  String[] states = {"menu", "turnOn", "waitingLightsOff", "waitingPlayerReaction", "finalResult", "jump"};
  
  String[] fileLines = loadStrings("timmings.txt");
  
  int light, start, end, finish;
  
  float diff, result;
  
  Player[] p;
  
  GameProgression () 
  {
    stateIndex = 0;
       
    light = 0;
    
    p = new Player[1];
  }
  
  public void loadPlayers()
  {
    String[] components;
    
    for(int i = 0; i < fileLines.length; i++)
    {
      components = split(fileLines[i], " ");
      
      Player a = new Player(components[0], PApplet.parseInt(components[1]), components[2]);
      
      print("Name: " + components[0] + "\n");
      
      print("Time: " + PApplet.parseInt(components[1]) / 1000 + " ms\n");
      
      print("Date: " + components[2] + "\n");
      
      append(p, a);
    }
  }
  
  public boolean checkState(String toTest)
  {
    for(int i = 0; i < states.length; i++)
      if(toTest == states[i])
        return true;
        
    return false;    
  }
  
  public void moreLight()
  {
    light++;
  }
  
  public String getState()
  {
    return states[stateIndex];
  }
  
  public void nextState()
  {
    if(stateIndex >= 4)
      stateIndex = 0;
    else
      stateIndex++;
  }
  
  public int getElapsedTime()
  {
    return end - start;
  }
  
  public void getNewEnd()
  {
    end = millis();
  }
  
  public void getNewStart()
  {
    start = millis();
  }
  
  public void getNewFinish()
  {
    finish = PApplet.parseInt(random(800, 3000));
  }
  
  public int getLights()
  {
    return light;
  }
  
  public int getFinish()
  {
    return finish;
  }
  
  public void resetLights()
  {
    light = 0;
  }
  
  public float getDiff()
  {
    return 1.0f * (end - start) / 1000;
  }
  
  public void setJumpStartState()
  {
    stateIndex = 5;
  }
  
  public void addPlayer(String name,int time, String date)
  {
    Player a = new Player(name, time, date);
    
    String newLine = name + " " + time + " " + date;
    
    append(fileLines, newLine);
    
    append(p, a);
    
    saveStrings("timmings.txt", fileLines);
  }
  
  public void sortPlayers()
  {
    String nameAux, dateAux;
    
    int timeAux;
    
    for(int i = 0; i < p.length - 1; i++)
    {
      for(int j = i + 1; j < p.length - 1; j++)
      {
        if(p[j].getTime() < p[i].getTime())
        {
          nameAux = p[i].getName();
          
          timeAux = p[i].getTime();      //i to aux
          
          dateAux = p[i].getDate();
          
          
          p[i].setName(p[j].getName());
          
          p[i].setTime(p[j].getTime());  //j to i
          
          p[i].setDate(p[j].getDate());
          
          
          p[j].setName(nameAux);
          
          p[j].setTime(timeAux);         //aux to j
          
          p[j].setDate(dateAux);
        }
      }
    }
  }
  
  /*Player[] getBest()
  {
    Player[] v = new Player[3];
    
    append(v, p[0]);
    
    append(v, p[1]);
    
    append(v, p[2]);
    
    return v;
  }*/
  
  public void printAllPLayers()
  {
    for(int i = 1; i < p.length; i++)
    {
      print("Name: " + p[i].name + "\n");
      
      print("Time: " + p[i].time / 1000 + " ms\n");
      
      print("Date: " + p[i].date + "\n");
    }
  }
}
class Player
{
  String name;
  
  int time;
  
  String date;
  
  Player(String n, int t, String d)
  {
    setName(n);
    
    if(t > 0)
      setTime(t);
    else
    {
      print("ERROR: Wrong time detected -> " + time);
      
      exit();
    }
    
    setDate(d);
  }
  
  public String getName()
  {
    return name;
  }
  
  public int getTime()
  {
    return time;
  }
  
  public String getDate()
  {
    return date;
  }
  
  public void setName(String newName)
  {
    name = newName;
  }
  
  public void setTime(int newTime)
  {
    time = newTime;
  }
  
  public void setDate(String newDate)
  {
    date = newDate;
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "F1ReactionGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
