/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
   
    TrafficLight Bus = new TrafficLight("OWB");
    TrafficLight fiets1 = new TrafficLight("Fiets");
    TrafficLight fiets2 = new TrafficLight("Fiets");
    TrafficLight fiets3 = new TrafficLight("Fiets");
    
    TrafficLight highestPrior;
    TrafficLight prevLight;    
    TrafficLight ThirdPrior = new TrafficLight("placeHolder");    
    TrafficLight SecondPrior = new TrafficLight("placeHolder");
   
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
        highestPrior = new TrafficLight("PlaceHolder highest");
        prevLight = new TrafficLight("placeHolder prev");
        prevLight.active = false;
        ThirdPrior.active = false;
        SecondPrior.active = false;
        
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
            setStoplights(Bus, message[type],message[amount]);
            highestPrior = Bus;
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
        c_Light.setType(type);
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

            System.out.println(highestPrior.stoplight + " amount : " + highestPrior.getAmount());
        }
        
        /*
        Check highest total AMOUNT zie Whiteboard.
        
        
        int highestAmount,totalAmount,tempAmount;
        totalAmount = 0;
        //Van Noord naar -> WEST
        tempAmount =  NW.amount+NO.amount+NZ.amount+ON.amount+ZN.amount+WN.amount+WO.amount+WZ.amount;
        totalAmount = (tempAmount > totalAmount ? tempAmount : totalAmount);
        //Van Noord naar -> Zuid
        System.out.println("So many : " + totalAmount);     
                */
             
               
    }
     
    public TrafficLight[] CheckPossibilities(TrafficLight c_Light)
    {
        TrafficLight[] possibleLights;
        int done = 0;
        /*
        Mogelijke opties
            NW -> NO NZ ON OZ OW ZNO WN WO WZ
            NZ -> NO NW ON NZ OZ 
            NO -> NZ NW ON WZ
        
            ZNO -> NZ NW ZW WZ
            ZW -> ZNO ON WZ
            
            ON -> NO NZ BW OW ZW WO WZ
            OW -> ON WO WZ
        
            WN -> NW WO WZ
            WO -> ON NW OW WN WZ
            WZ -> NO NW ON OW ZNO ZW WN WO
        */
        
        if(c_Light.stoplight == "OWB")
        {
            possibleLights = new TrafficLight[]{WO,WZ};
            return possibleLights;
        }
        
        if(c_Light.stoplight == "NW")
        {
          possibleLights = new TrafficLight[]{NO,NZ,ON,ZN,WN,WO,WZ};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "NZ")
        {
          possibleLights = new TrafficLight[]{NO,NW,ON,ZN};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "NO")
        {
          possibleLights = new TrafficLight[]{NZ, NW, ON, WZ};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "ZN")
        {
          possibleLights = new TrafficLight[]{NZ, NW, ZW, WZ};                    
          return possibleLights; 
        }
        if(c_Light.stoplight == "ZW")
        {
          possibleLights = new TrafficLight[]{ZN, ON, WZ};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "ON")
        {
          possibleLights = new TrafficLight[]{NO, NZ, NW, OW, ZW, WO, WZ};                    
          return possibleLights;
        }
        if(c_Light.stoplight == "OW")
        {
          possibleLights = new TrafficLight[]{ON, WO, WZ};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "WN")
        {
          possibleLights = new TrafficLight[]{NW, WO, WZ};                     
          return possibleLights;
        }
        if(c_Light.stoplight == "WO")
        {
          possibleLights = new TrafficLight[]{ON, NW, OW, WN, WZ};                    
          return possibleLights;
        }
        if(c_Light.stoplight == "WZ")
        {
          possibleLights = new TrafficLight[]{NO, NW, ON, OW, ZN, ZW, WN, WO};                    
          return possibleLights;
        }  
        return null;
    }
    
    public void SecondLight(TrafficLight[] possibleLights)
    {
        SecondPrior = possibleLights[1];
        for (int i = 0; i < possibleLights.length; i++) 
        {
        System.out.println("From is possible with First as Light: " + possibleLights[i].stoplight);
            if(possibleLights[i].amount > SecondPrior.amount)
            {
                String temp = possibleLights[i].stoplight;
                if(!SecondPrior.stoplight.equals(temp))
                    SecondPrior = possibleLights[i];
            }
        }        
        System.out.println("Second is: " + SecondPrior.stoplight + "\n --------------------------------------------------- \n");
        SecondPrior.active = true;
        SecondPrior.resetAmount(); 
        //ActivateLights();
        ThirdLight(CheckPossibilities(SecondPrior),possibleLights);
    }
    
    public void ThirdLight(TrafficLight[] possibleLights, TrafficLight[] possibleFromSecond)
    {
        System.out.println(possibleLights.length);
        for (int i = 0; i < possibleLights.length; i++) 
        {
            System.out.println("From is possible with Second as Light: " + possibleLights[i].stoplight);
            for (int j = 0; j < possibleFromSecond.length; j++) {
                if(possibleLights[i].stoplight == possibleFromSecond[j].stoplight)
                {
                    String temp = possibleLights[i].stoplight;
                     if(!ThirdPrior.stoplight.equals(temp) && temp != highestPrior.stoplight && temp != SecondPrior.stoplight)
                            ThirdPrior = possibleLights[i];
                }
            }
            //if(possibleLights[i].stoplight != highestPrior.stoplight && possibleLights[i].stoplight != SecondPrior.stoplight )
              //  ThirdPrior = (possibleLights[i].amount > ThirdPrior.amount ? possibleLights[i] : ThirdPrior);
        }    
        System.out.println("Thirdprior is: " + ThirdPrior.stoplight + "\n --------------------------------------------------- \n");
        
        ThirdPrior.active = true;
        ThirdPrior.resetAmount();
        ActivateLights();
    }
    
    public void ActivateLights()
    {
        highestPrior.StartTimer(0);
        SecondPrior.StartTimer(0.5f);        
        ThirdPrior.StartTimer(1);
    }
    
    public void NextLight()
    {
        if(highestPrior.amount > 0)
        {
            System.out.println("Next Light : " + highestPrior.stoplight); 
            highestPrior.active = true;            
            //highestPrior.StartTimer(0);
            //CheckPossibilities(highestPrior);
            SecondLight(CheckPossibilities(highestPrior));
            prevLight = highestPrior;
            prevLight.resetAmount();
        }
    }
    
    
    public void run()
    {  
        while(running)
        {   
            if(prevLight.checkActive() == false && SecondPrior.checkActive() == false && ThirdPrior.checkActive() == false)
            {                             
                NextLight(); 
                CheckHighestAmount(); 
            }
            else
            {                
                
            }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrafficSystem.class.getName()).log(Level.SEVERE, null, ex);
        }            
        }
    }
}
