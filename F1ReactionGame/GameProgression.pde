class GameProgression
{
  int stateIndex;
  
  String[] states = {"menu", "turnOn", "waitingLightsOff", "waitingPlayerReaction", "finalResult", "jump"};
  
  String[] fileLines = loadStrings("timmings.txt");
  
  int light, start, end, finish;
  
  float diff, result;
  
  ArrayList<Player> players = new ArrayList<Player>();
  
  GameProgression () 
  {
    stateIndex = 0;
       
    light = 0;
  }
  
  void loadPlayers()
  {
    String[] components;
    
    for(int i = 0; i < fileLines.length; i++)
    {
      components = split(fileLines[i], " ");
      
      Player a = new Player(components[0], int(components[1]), components[2]);
            
      players.add(a);
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
    
    players.add(a);
    
    saveStrings("timmings.txt", fileLines);
  }
  
  void printAllPlayers()
  {
    for(Player p : players)
      p.show();
  }
  
  /*void sortPlayers()
  {
    String nameAux, dateAux;
    
    int timeAux;
    
    for(int i = 0; i < p.length - 1; i++)
    {
      for(int j = i + 1; j < p.length - 1; j++)
      {
        if(players[j].getTime() < players[i].getTime())
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
  }*/
  
  /*Player[] getBest()
  {
    Player[] v = new Player[3];
    
    append(v, p[0]);
    
    append(v, p[1]);
    
    append(v, p[2]);
    
    return v;
  }*/
}