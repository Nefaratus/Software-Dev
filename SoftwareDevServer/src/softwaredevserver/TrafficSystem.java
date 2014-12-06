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
    TrafficLight prevLight;
    
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
        prevLight = new TrafficLight("placeHolder");
        prevLight.active = false;
        
    }
    
    public void MessageHandler(char[] message)
    {
        //System.out.println(message);

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
                    setStoplights(NW, message[type],message[amount]);
                }
                if(message[to] == 'O')
                {                              
                    setStoplights(NO, message[type],message[amount]);
                }
                if(message[to] == 'Z')
                {   
                    setStoplights(NZ, message[type],message[amount]);
                }
            }

            else if(message[from] == 'O')
            {
                if(message[to] == 'N')
                {   
                    setStoplights(ON, message[type],message[amount]);
                }
                if(message[to] == 'W')
                {    
                    setStoplights(OW, message[type],message[amount]);
                }
            }

            else if(message[from] == 'W')
            {
                if(message[to] == 'N')
                {  
                    setStoplights(WN, message[type],message[amount]);
                }
                if(message[to] == 'Z')
                {   
                    setStoplights(WZ, message[type],message[amount]);
                }
                if(message[to] == 'O')
                {   
                    setStoplights(WO, message[type],message[amount]);
                }
            }

            else if(message[from] == 'Z')
            {
                if(message[to] == 'W')
                {                               
                    setStoplights(ZW, message[type], message[amount]);
                }
                if(message[to] == 'N' || message[2] == 'O')
                {   
                    setStoplights(ZN,message[type],message[amount]);
                }
            }
        }                
    }
    
    public void setStoplights(TrafficLight c_Light, char type ,char amount)
    {
        c_Light.changeAmount(amount);
        if(highestPrior == null)
            highestPrior = c_Light;
    }
    
    public void CheckHighestAmount()
    {
        if(highestPrior != null);
        {
            highestPrior = (NW.getAmount() > highestPrior.getAmount() ? NW : highestPrior);
            highestPrior = (NO.getAmount() > highestPrior.getAmount() ? NO : highestPrior);
            highestPrior = (NZ.getAmount() > highestPrior.getAmount() ? NZ : highestPrior);

            highestPrior = (ON.getAmount() > highestPrior.getAmount() ? ON : highestPrior);
            highestPrior = (OW.getAmount() > highestPrior.getAmount() ? OW : highestPrior);

            highestPrior = (WN.getAmount() > highestPrior.getAmount() ? WN : highestPrior);
            highestPrior = (WZ.getAmount() > highestPrior.getAmount() ? WZ : highestPrior);
            highestPrior = (WO.getAmount() > highestPrior.getAmount() ? WO : highestPrior);

            highestPrior = (ZW.getAmount() > highestPrior.getAmount() ? ZW : highestPrior);
            highestPrior = (ZN.getAmount() > highestPrior.getAmount() ? ZN : highestPrior);
        }
        
               
    }
    
    public void NextLight()
    {
        if(highestPrior.amount > 0)
        {
            highestPrior.status = 1;
            highestPrior.StartTimer(0);
            System.out.println(highestPrior.stoplight + " " + highestPrior.amount); 
            prevLight = highestPrior;
            //System.out.println(prevLight.stoplight); 
            prevLight.resetAmount();
        }
    }
 
    public void CheckPossibilities()
    {
        
    }
    
    
    public void run()
    {  
        while(running)
        {   
            if(prevLight.checkActive() == false)
            {
                CheckHighestAmount();
                NextLight();                
            }
            else
            {                
                CheckHighestAmount();
            }
        }
    }
}
