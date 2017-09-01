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
  
}