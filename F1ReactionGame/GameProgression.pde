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
  
  void switchPositions (int firstIndex, int secondIndex)
  {
    Player a = players.get(firstIndex);
    
    players.set(firstIndex, players.get(secondIndex));
    
    players.set(secondIndex, a);
  }
  
  void sortPlayers()
  {
    Player a, b;
    
    for(int i  = 0; i < players.size() - 1; i++)
    {
      a = players.get(i);
      
      for(int k = i + 1; i < players.size(); i++)
      {
        b = players.get(k);
        
        if(b.getTime() < a.getTime())
          switchPositions(i, k);
      }
    }
  }
  
  Player getPlayerAt(int index)
  {
    Player a = players.get(index);
    
    return a;
  }
}