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
   
    TrafficLight Bus = new TrafficLight("BUS");
    TrafficLight fiets1 = new TrafficLight("Fiets");
    TrafficLight fiets2 = new TrafficLight("Fiets");
    TrafficLight fiets3 = new TrafficLight("Fiets");
    
    
    TrafficLight highestPrior;
    
    public boolean next = true;
    
    private boolean running = true;
    
    String light;
    
    private int from = 1;
    private int to = 2;
    private int type = 3;
    private int amount = 4;
        
    TrafficLock Lock;
    
    public TrafficSystem()
    {

    }
    
    public void MessageHandler(char[] message)
    {
        System.out.println(message);

        /* 
        er moet hier al gekeken worden voor welk type het is.
        Amount is alleen relavant voor de auto's en niet voor de fietsers en bussen
        */
        
        if(message[type] == 'B')
        {
            /*
            hoogste prior
            */
        }        
        else if(message[type] == 'F')
        {
            /*
            Om de zoveel tijd
            */
        }
        
        else if(message[type] == 'V')
        {
            /*
            Ook om de zoveel tijd
            */
        }
        
        
        if(message[type] == 'A')
        {
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
                
    }
    
    public void LightHandler(TrafficLight c_light, char type ,char amount)
    {
        c_light.ChangeAmount(amount);
        if(c_light.amount > highestPrior.amount)                    
             highestPrior = c_light;
        
        highestPrior.StartTimer(0);
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
            }catch(Exception e)
            {
                
            }

        }
    }
    
      
}
