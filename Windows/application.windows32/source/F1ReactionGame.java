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

int light = 0, start, end, finish;

float diff, result;

boolean menu = true;

boolean turnOn = false;

boolean waitingLightsOff = false;

boolean waitingPlayerReaction = false;

boolean finalResult = false;

boolean jump = false;

GameProgression a;

public void setup ()
{
  frameRate(200);
 
  
  
  print("Game Started!\n");
  
  a = new GameProgression();
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
    result = diff;
    
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
  textSize(32);
  
  text("Press SPACE to try! Press SPACE again to Reset", 40, 200);
  
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
  
  diff = a.getDiff();
  
  text(diff, width / 2 - 100, 200);
  
  rectMode(CENTER);
  
  for(int x = 150, y =500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  
  //saveFrame("frame###.jpg");
}

public void printFinalResult()
{
  textSize(48);
  
  text("You took:\n", width / 2 - 110, 200);
  
  text(result + "ms\n", width / 2 - 100, 300);
  
  rectMode(CENTER);
  
  for(int x = 150, y =500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  
  text("Press SPACE again\n", width / 3 - 100, height - 48);
}

public void jumpStart()
{
  textSize(48);
  
  text("JUMP START!\n", width / 2 - 110, 200);
  
  rectMode(CENTER);
  
  for(int x = 150, y = 500; x <= 650; x += 125)
  {
    fill(10);
    
    rect(x, y, 75, 300, 15, 15, 15, 15);
      
    fill(100);
      
    ellipse(x, y + 26, 64, 64);
    
    ellipse(x, y + 100, 64, 64);
  }
  
  text("Press SPACE again\n", width / 3 - 100, height - 48);
}

public void printVariablesState ()
{
  print("\n\n\nlight = " + light + "\n");
  
  print("start = " + start + " ms\n");
  
  print("end = " + end + " ms\n");
  
  print("finish = " + finish + " ms\n");
  
  print("menu = " + menu + "\n");
  
  print("turnOn = " + turnOn + "\n");
  
  print("waitingLightsOff = " + waitingLightsOff + "\n\n\n\n");
}
class GameProgression
{
  int stateIndex;
  
  String[] states = {"menu", "turnOn", "waitingLightsOff", "waitingPlayerReaction", "finalResult", "jump"};
  
  int light, start, end, finish;
  
  float diff, result;
  
  GameProgression () 
  {
    stateIndex = 0;
       
    light = 0;    
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
