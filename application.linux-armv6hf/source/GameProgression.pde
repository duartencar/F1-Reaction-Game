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
  
  void loadPlayers()
  {
    String[] components;
    
    for(int i = 0; i < fileLines.length; i++)
    {
      components = split(fileLines[i], " ");
      
      Player a = new Player(components[0], int(components[1]), components[2]);
      
      print("Name: " + components[0] + "\n");
      
      print("Time: " + int(components[1]) / 1000 + " ms\n");
      
      print("Date: " + components[2] + "\n");
      
      append(p, a);
    }
  }
  
  boolean checkState(String toTest)
  {
    for(int i = 0; i < states.length; i++)
      if(toTest == states[i])
        return true;
        
    return false;    
  }
  
  void moreLight()
  {
    light++;
  }
  
  String getState()
  {
    return states[stateIndex];
  }
  
  void nextState()
  {
    if(stateIndex >= 4)
      stateIndex = 0;
    else
      stateIndex++;
  }
  
  int getElapsedTime()
  {
    return end - start;
  }
  
  void getNewEnd()
  {
    end = millis();
  }
  
  void getNewStart()
  {
    start = millis();
  }
  
  void getNewFinish()
  {
    finish = int(random(800, 3000));
  }
  
  int getLights()
  {
    return light;
  }
  
  int getFinish()
  {
    return finish;
  }
  
  void resetLights()
  {
    light = 0;
  }
  
  float getDiff()
  {
    return 1.0 * (end - start) / 1000;
  }
  
  void setJumpStartState()
  {
    stateIndex = 5;
  }
  
  void addPlayer(String name,int time, String date)
  {
    Player a = new Player(name, time, date);
    
    String newLine = name + " " + time + " " + date;
    
    append(fileLines, newLine);
    
    append(p, a);
    
    saveStrings("timmings.txt", fileLines);
  }
  
  void sortPlayers()
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
  
  void printAllPLayers()
  {
    for(int i = 1; i < p.length; i++)
    {
      print("Name: " + p[i].name + "\n");
      
      print("Time: " + p[i].time / 1000 + " ms\n");
      
      print("Date: " + p[i].date + "\n");
    }
  }
}