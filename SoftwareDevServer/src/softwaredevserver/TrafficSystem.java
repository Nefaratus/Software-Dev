/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Jan
 */
public class TrafficSystem extends Thread {
    
    /*
    N -> O O W Z
    O -> N W
    W -> N Z O 
    Z -> W NO 
    */
    //North
    TrafficLight NW = new TrafficLight("NW");
    TrafficLight NO = new TrafficLight("NO");
    TrafficLight NZ = new TrafficLight("NZ");

    //East
    TrafficLight ON = new TrafficLight("ON");
    TrafficLight OW = new TrafficLight("OW");

    //West
    TrafficLight WN = new TrafficLight("WN"); 
    TrafficLight WZ = new TrafficLight("WZ");
    TrafficLight WO = new TrafficLight("WO");

    //South
    TrafficLight ZW = new TrafficLight("ZW");
    TrafficLight ZN = new TrafficLight("ZN");
    
    TrafficLight currentLight;
    public boolean next = true;
    
    private boolean running = true;
    
    
    String light;
    
    private int from = 1;
    private int to = 2;
    private int type = 3;
    private int amount = 4;
    
    TrafficLock Lock;
    
    List<String> messageList = new ArrayList<String>();
    
    
    public TrafficSystem()
    {

    }
    
    public void addToList(String message)
    {
        messageList.add(message);
    }
    
    public void MessageHandler(char[] message)
    {
        System.out.println(message);
                    if(message[from] == 'N')
                    {
                        if(message[to] == 'W')
                        {   
                            LightHandler(NW, message[type],message[amount]);
                        }
                        if(message[to] == 'O')
                        {                              
                            LightHandler(NO, message[type],message[amount]);
                        }
                        if(message[to] == 'Z')
                        {   
                            LightHandler(NZ, message[type],message[amount]);
                        }
                    }
                        
                    else if(message[from] == 'O')
                    {
                        if(message[to] == 'N')
                        {   
                            LightHandler(ON, message[type],message[amount]);
                        }
                        if(message[to] == 'W')
                        {    
                            LightHandler(OW, message[type],message[amount]);
                        }
                    }
                    
                    else if(message[from] == 'W')
                    {
                        if(message[to] == 'N')
                        {  
                            LightHandler(WN, message[type],message[amount]);
                        }
                        if(message[to] == 'Z')
                        {   
                            LightHandler(WZ, message[type],message[amount]);
                        }
                        if(message[to] == 'O')
                        {   
                            LightHandler(WO, message[type],message[amount]);
                        }
                    }
                    
                    else if(message[from] == 'Z')
                    {
                        if(message[to] == 'W')
                        {                               
                            LightHandler(ZW, message[type], message[amount]);
                        }
                        if(message[to] == 'N' || message[2] == 'O')
                        {   
                            LightHandler(ZN,message[type],message[amount]);
                        }
                    }
        
                
    }
    
    public void LightHandler(TrafficLight light, char type ,char amount)
    {
     //   light.StartTimer(0);
        light.ChangeAmount(amount);
    }
    
    
    public void CheckPossibilities()
    {
    
    }
    
    
    public void run()
    {       
        
        while(running)
        {         
            try
            {
            if(Lock.lock == true)
            {

                Lock.Lock();
                
            }
            }catch(Exception e)
            {
                
            }
            
            try
            {
             if(currentLight.stat == "Done")
                {
                    Lock.Release();
                }
            }catch(Exception e)
            {
                
            }

        }
        /*
        
        if(next == true)
        {
            
            
            Collections.sort(traficLightPriorList, new Comparator<TraficLight>() {
            public int compare(TraficLight o1, TraficLight o2) {
                return o1.prio > o2.prio ? -1 : o1.prio == o2.prio ? 0 : 1;
            }
        });
       /*
       
       Lijst sorteren en kijken welke stoplichten aan en uit kunnen
        
       Logica voor welke prior eerst moet hierin
       
      
       
       current_TraficLight = traficLightPriorList.get(1);  
       current_TraficLight.StartTimer(0);
       traficLightPriorList.remove(current_TraficLight);
                
       } */
    }
    
      
}
