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

public void setup ()
{
  frameRate(200);
 
  
  
  print("Game Started!\n");
}

public void draw ()
{
  background(255);
  
  if(menu == true && turnOn == false && waitingLightsOff == false)
     printInitialMenu();
  
  else if(menu == false && turnOn == true && waitingLightsOff == false)
  {
    end = millis();
  
    if(end - start >= 1000)
    {
      light++;
    
      start = millis();
    }
  
    for(int x = 150, lvl = 0, y = 500; x <= 650; x += 125)
    {
      fill(10);
    
      rect(x, y, 75, 300, 15, 15, 15, 15);
    
      if(lvl > light - 1 && lvl < 6)
        fill(100);
      else
        fill(255, 0, 0);
      
      ellipse(x, y + 26, 64, 64);
    
      ellipse(x, y + 100, 64, 64);
    
      lvl++;
      
      //print("lvl = " + lvl + "\n");
    }
    if(light == 5)
    {
      turnOn = !turnOn;
        
      start = millis();
       
      waitingLightsOff = !waitingLightsOff;
    }
  }
  else if(menu == false && turnOn == false && waitingLightsOff == true)
  {
    end = millis();
    
    if(end - start <= finish)
    {
      //print("Time elapsed = " + (end - start) + " ms\n");
      
      for(int x = 150, y =500; x <= 650; x += 125)
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
      
      waitingLightsOff = !waitingLightsOff;
      
      waitingPlayerReaction = !waitingPlayerReaction;
      
      light = 0;
      
      start = millis();
    }
  }
  else if(jump == true)
    jumpStart();
  else if(menu == false && turnOn == false && waitingLightsOff == false && waitingPlayerReaction == true)
  {
    end = millis();
    
    printTimeElapsed();
  }
  else if(menu == false && turnOn == false && waitingLightsOff == false && waitingPlayerReaction == false && finalResult == true)
  {
    printFinalResult();
  }
  //printVariablesState ();
}

public void keyPressed()
{
  if(key == ' ' && menu == true && turnOn == false && waitingLightsOff == false && waitingPlayerReaction == false)
  {
    start = millis();
    
    menu = !menu;
    
    turnOn = !turnOn;
    
    finish = PApplet.parseInt(random(800, 3000));
    
    //print("Finish = " + finish + " ms\n");
  }
  else if(key == ' ' && menu == false && turnOn == false && waitingLightsOff == false && waitingPlayerReaction == true)
  {
    result = diff;
    
    waitingPlayerReaction = !waitingPlayerReaction;
    
    finalResult = !finalResult;
  }
  else if(key == ' ' && (turnOn == true || waitingLightsOff == true))
  {
    jump = !jump;
    
    turnOn = false;
    
    waitingLightsOff = false;
  }
  else if(key == ' ' && jump == true)
  {
    menu = true;

    turnOn = false;

    waitingLightsOff = false;

    waitingPlayerReaction = false;

    finalResult = false;

    jump = !jump;
    
    light = 0;
  }
  else if(key == ' ' && menu == false && turnOn == false && waitingLightsOff == false && waitingPlayerReaction == false && finalResult == true)
  {
    finalResult = !finalResult;
    
    menu = !menu;
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
  
  diff = 1.0f * (end - start) / 1000;
  
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
