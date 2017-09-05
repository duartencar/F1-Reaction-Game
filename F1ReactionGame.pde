GameProgression a;

void setup ()
{
  frameRate(200);
 
  size(800, 800);
  
  print("Game Started!\n");
  
  a = new GameProgression();
  
  a.loadPlayers();
  
  a.sortPlayers();
}

void draw ()
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

void keyPressed()
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

void printInitialMenu()
{
  textSize(32);
  
  text("Press SPACE to try! Press SPACE again to Reset", 40, 50);
  
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

void printTimeElapsed()
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
  
  //saveFrame("frame###.jpg");
}

void printFinalResult()
{
  textSize(48);
  
  text("You took:\n", width / 2 - 110, 200);
  
  text(a.result + "ms\n", width / 2 - 100, 300);
  
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

void jumpStart()
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

void printVariablesState ()
{
  print("\n\n\nlight = " + a.light + "\n");
  
  print("start = " + a.start + " ms\n");
  
  print("end = " + a.end + " ms\n");
  
  print("finish = " + a.finish + " ms\n");
}