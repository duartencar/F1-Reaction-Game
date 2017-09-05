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
  
  String getName()
  {
    return name;
  }
  
  int getTime()
  {
    return time;
  }
  
  String getDate()
  {
    return date;
  }
  
  void setName(String newName)
  {
    name = newName;
  }
  
  void setTime(int newTime)
  {
    time = newTime;
  }
  
  void setDate(String newDate)
  {
    date = newDate;
  }
}